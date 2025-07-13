package git.fowin.injectors;

import java.util.*;
import java.lang.reflect.*;

public class ForgeInjector extends Thread {
    
    private final byte[][] modClasses;
    
    private ForgeInjector(byte[][] classes) {
        this.modClasses = classes;
    }
    
    public static void inject(byte[][] classes) {
        new ForgeInjector(classes).start();
    }
    
    @Override
    public void run() {
        try {
            ClassLoader forgeLoader = findForgeClassLoader();
            if (forgeLoader == null) return;

            List<Class<?>> injectedClasses = new ArrayList<>();
            for (int i = 0; i < modClasses.length; i++) {
                byte[] classData = modClasses[i];
                if (classData == null) continue;
                
                try {
                    Class<?> modClass = injectModClass(forgeLoader, classData);
                    if (modClass != null) {
                        injectedClasses.add(modClass);
                    }
                } catch (Exception e) {
                }
            }

            for (Class<?> modClass : injectedClasses) {
                try {
                    if (hasModAnnotation(modClass, forgeLoader)) {
                        Object modInstance = modClass.getDeclaredConstructor().newInstance();
                        registerForgeMod(modInstance, forgeLoader);
                    }
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
        }
    }
    
    private ClassLoader findForgeClassLoader() {
        Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
        
        for (Thread thread : threads.keySet()) {
            if (thread == null || thread.getContextClassLoader() == null) {
                continue;
            }
            
            ClassLoader loader = thread.getContextClassLoader();
            String loaderClass = loader.getClass().getName();
            
            if (loaderClass.contains("ModClassLoader") ||
                loaderClass.contains("LaunchClassLoader") ||
                loaderClass.contains("TransformingClassLoader") ||
                loaderClass.contains("ForgeClassLoader")) {
                return loader;
            }
        }
        
        ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
        if (canLoadForgeClasses(systemLoader)) {
            return systemLoader;
        }
        
        return null;
    }
    
    private boolean canLoadForgeClasses(ClassLoader loader) {
        try {
            loader.loadClass("net.minecraftforge.fml.common.Mod");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    
    private Class<?> injectModClass(ClassLoader forgeLoader, byte[] classData) {
        try {
            String className = extractClassName(classData);
            if (className == null) return null;
            
            Method defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass", 
                String.class, byte[].class, int.class, int.class);
            defineClassMethod.setAccessible(true);
            
            Class<?> definedClass = (Class<?>) defineClassMethod.invoke(forgeLoader, 
                className, classData, 0, classData.length);
            
            return definedClass;
            
        } catch (Exception e) {
            return null;
        }
    }
    
    private void registerForgeMod(Object modInstance, ClassLoader forgeLoader) {
        try {
            callForgeInitMethods(modInstance);
            registerWithEventBus(modInstance, forgeLoader);
        } catch (Exception e) {
        }
    }
    
    private void callForgeInitMethods(Object modInstance) {
        try {
            Class<?> modClass = modInstance.getClass();
            Method[] methods = modClass.getDeclaredMethods();
            
            for (Method method : methods) {
                String methodName = method.getName();
                
                if (methodName.equals("setup") || 
                    methodName.equals("init") || 
                    methodName.equals("preInit") ||
                    methodName.equals("postInit") ||
                    methodName.equals("onCommonSetup") ||
                    methodName.equals("onClientSetup") ||
                    methodName.contains("Init")) {
                    
                    try {
                        method.setAccessible(true);
                        if (method.getParameterCount() == 0) {
                            method.invoke(modInstance);
                        } else if (method.getParameterCount() == 1) {
                            method.invoke(modInstance, (Object) null);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
        }
    }
    
    private void registerWithEventBus(Object modInstance, ClassLoader forgeLoader) {
        try {
            Class<?> minecraftForgeClass = forgeLoader.loadClass("net.minecraftforge.common.MinecraftForge");
            java.lang.reflect.Field eventBusField = minecraftForgeClass.getField("EVENT_BUS");
            Object eventBus = eventBusField.get(null);
            
            if (eventBus != null) {
                Method registerMethod = eventBus.getClass().getMethod("register", Object.class);
                registerMethod.invoke(eventBus, modInstance);
            }
            
        } catch (Exception e) {
            try {
                Class<?> fmlJavaModLoadingContextClass = forgeLoader.loadClass("net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext");
                Method getMethod = fmlJavaModLoadingContextClass.getMethod("get");
                Object context = getMethod.invoke(null);
                
                Method getModEventBusMethod = context.getClass().getMethod("getModEventBus");
                Object modEventBus = getModEventBusMethod.invoke(context);
                
                Method registerMethod = modEventBus.getClass().getMethod("register", Object.class);
                registerMethod.invoke(modEventBus, modInstance);
                
            } catch (Exception e2) {
            }
        }
    }
    
    private String extractClassName(byte[] classData) {
        try {
            if (classData.length < 10) return null;
            
            int magic = ((classData[0] & 0xFF) << 24) | ((classData[1] & 0xFF) << 16) | 
                       ((classData[2] & 0xFF) << 8) | (classData[3] & 0xFF);
            if (magic != 0xCAFEBABE) return null;
            
            int offset = 8;
            int constantPoolCount = ((classData[offset] & 0xFF) << 8) | (classData[offset + 1] & 0xFF);
            offset += 2;
            
            String[] constantPool = new String[constantPoolCount];
            int[] classRefs = new int[constantPoolCount];
            
            for (int i = 1; i < constantPoolCount && offset < classData.length; i++) {
                int tag = classData[offset] & 0xFF;
                offset++;
                
                switch (tag) {
                    case 1:
                        int length = ((classData[offset] & 0xFF) << 8) | (classData[offset + 1] & 0xFF);
                        offset += 2;
                        if (offset + length <= classData.length) {
                            constantPool[i] = new String(classData, offset, length);
                        }
                        offset += length;
                        break;
                    case 3: case 4:
                        offset += 4;
                        break;
                    case 5: case 6:
                        offset += 8;
                        i++;
                        break;
                    case 7:
                        classRefs[i] = ((classData[offset] & 0xFF) << 8) | (classData[offset + 1] & 0xFF);
                        offset += 2;
                        break;
                    case 8:
                        offset += 2;
                        break;
                    case 9: case 10: case 11: case 12:
                        offset += 4;
                        break;
                    case 15:
                        offset += 3;
                        break;
                    case 16:
                        offset += 2;
                        break;
                    case 18:
                        offset += 4;
                        break;
                    default:
                        offset += 2;
                        break;
                }
            }
            
            if (offset + 6 > classData.length) return null;
            
            offset += 2;
            int thisClassIndex = ((classData[offset] & 0xFF) << 8) | (classData[offset + 1] & 0xFF);
            
            if (thisClassIndex > 0 && thisClassIndex < classRefs.length && classRefs[thisClassIndex] > 0) {
                int nameIndex = classRefs[thisClassIndex];
                if (nameIndex < constantPool.length && constantPool[nameIndex] != null) {
                    return constantPool[nameIndex].replace('/', '.');
                }
            }
            
        } catch (Exception e) {
        }
        
        return null;
    }
    
    private boolean hasModAnnotation(Class<?> modClass, ClassLoader forgeLoader) {
        try {
            Class<?> modAnnotationClass = forgeLoader.loadClass("net.minecraftforge.fml.common.Mod");
            return modClass.isAnnotationPresent((Class<? extends java.lang.annotation.Annotation>) modAnnotationClass);
        } catch (Exception e) {
            return false;
        }
    }
}
