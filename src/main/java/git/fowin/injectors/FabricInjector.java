package git.fowin.injectors;

import java.util.*;
import java.lang.reflect.*;

public class FabricInjector extends Thread {
    private final byte[][] modClasses;

    private FabricInjector(byte[][] classes) {
        this.modClasses = classes;
    }
    
    public static void inject(byte[][] classes) {
        new FabricInjector(classes).start();
    }
    
    @Override
    public void run() {
        try {
            ClassLoader fabricLoader = findFabricClassLoader();
            if (fabricLoader == null) return;

            List<Object> modInstances = injectMods(fabricLoader);
            initializeFabricMods(modInstances, fabricLoader);

        } catch (Exception e) {
        }
    }
    
    private ClassLoader findFabricClassLoader() {
        Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();

        for (Thread thread : threads.keySet()) {
            if (thread == null || thread.getContextClassLoader() == null) {
                continue;
            }

            ClassLoader loader = thread.getContextClassLoader();
            String loaderClass = loader.getClass().getName();

            if (loaderClass.contains("FabricLauncher") ||
                loaderClass.contains("KnotClassLoader") ||
                loaderClass.contains("FabricClassLoader")) {
                return loader;
            }
        }

        ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
        try {
            systemLoader.loadClass("net.fabricmc.api.ModInitializer");
            return systemLoader;
        } catch (ClassNotFoundException e) {
        }

        return null;
    }
    
    private List<Object> injectMods(ClassLoader fabricLoader) {
        List<Object> modInstances = new ArrayList<>();
        List<Class<?>> injectedClasses = new ArrayList<>();

        for (int i = 0; i < modClasses.length; i++) {
            byte[] classData = modClasses[i];
            if (classData == null) continue;

            try {
                Class<?> modClass = injectSingleClass(fabricLoader, classData, i);
                if (modClass != null) {
                    injectedClasses.add(modClass);
                }
            } catch (Exception e) {
            }
        }

        List<Object> allInstances = new ArrayList<>();
        for (Class<?> modClass : injectedClasses) {
            try {
                try {
                    Object instance = modClass.getDeclaredConstructor().newInstance();
                    allInstances.add(instance);
                } catch (Exception e) {
                    tryAlternativeInstantiation(modClass, allInstances);
                }
            } catch (Exception e) {
            }
        }
        
        for (Object instance : allInstances) {
            if (instance.getClass() == Object.class) continue;

            Class<?> modClass = instance.getClass();

            boolean isModInitializer = implementsInterface(modClass, "net.fabricmc.api.ModInitializer");
            boolean isClientInitializer = implementsInterface(modClass, "net.fabricmc.api.ClientModInitializer");
            boolean isServerInitializer = implementsInterface(modClass, "net.fabricmc.api.DedicatedServerModInitializer");

            boolean isMainModClass = isLikelyMainModClass(modClass);

            if (isModInitializer || isClientInitializer || isServerInitializer || isMainModClass) {
                modInstances.add(instance);
            }
        }

        return modInstances;
    }
    
    private Class<?> injectSingleClass(ClassLoader fabricLoader, byte[] classData, int classIndex) {
        String className = extractClassName(classData);
        if (className == null) {
            className = "injected.FabricModClass" + classIndex;
        }

        Class<?> result = tryFabricNativeInjection(fabricLoader, className, classData);
        if (result != null) return result;

        result = tryIsolatedClassLoader(fabricLoader, className, classData);
        if (result != null) return result;

        result = tryBytecodeManipulation(fabricLoader, className, classData);
        if (result != null) return result;

        result = tryJVMInjection(fabricLoader, className, classData);
        if (result != null) return result;

        return null;
    }

    private Class<?> tryFabricNativeInjection(ClassLoader fabricLoader, String className, byte[] classData) {
        try {
            String loaderClassName = fabricLoader.getClass().getName();

            if (loaderClassName.contains("KnotClassLoader")) {
                return tryKnotClassLoaderInjection(fabricLoader, className, classData);
            }

            Method[] methods = fabricLoader.getClass().getMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                if (methodName.contains("define") || methodName.contains("transform") || methodName.contains("load")) {
                    if (method.getParameterCount() >= 2) {
                        try {
                            Class<?>[] paramTypes = method.getParameterTypes();
                            if (paramTypes.length >= 2 && paramTypes[0] == String.class && paramTypes[1] == byte[].class) {
                                Object result = method.invoke(fabricLoader, className, classData);
                                if (result instanceof Class<?>) {
                                    return (Class<?>) result;
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }

            return null;

        } catch (Exception e) {
            return null;
        }
    }

    private Class<?> tryKnotClassLoaderInjection(ClassLoader fabricLoader, String className, byte[] classData) {
        try {
            Class<?> knotClass = fabricLoader.getClass();

            Method[] declaredMethods = knotClass.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.getName().contains("define")) {
                    try {
                        method.setAccessible(true);
                        Class<?>[] paramTypes = method.getParameterTypes();

                        if (paramTypes.length == 5 && paramTypes[0] == String.class && paramTypes[1] == byte[].class) {
                            Object result = method.invoke(fabricLoader, className, classData, 0, classData.length, null);
                            if (result instanceof Class<?>) {
                                return (Class<?>) result;
                            }
                        } else if (paramTypes.length == 4 && paramTypes[0] == String.class && paramTypes[1] == byte[].class) {
                            Object result = method.invoke(fabricLoader, className, classData, 0, classData.length);
                            if (result instanceof Class<?>) {
                                return (Class<?>) result;
                            }
                        } else if (paramTypes.length == 3 && paramTypes[0] == String.class && paramTypes[1] == byte[].class) {
                            Object result = method.invoke(fabricLoader, className, classData, classData.length);
                            if (result instanceof Class<?>) {
                                return (Class<?>) result;
                            }
                        } else if (paramTypes.length == 2 && paramTypes[0] == String.class && paramTypes[1] == byte[].class) {
                            Object result = method.invoke(fabricLoader, className, classData);
                            if (result instanceof Class<?>) {
                                return (Class<?>) result;
                            }
                        }

                    } catch (Exception e) {
                    }
                }
            }

            Class<?> parentClass = knotClass.getSuperclass();
            if (parentClass != null && parentClass != ClassLoader.class) {
                Method[] parentMethods = parentClass.getDeclaredMethods();
                for (Method method : parentMethods) {
                    if (method.getName().contains("define")) {
                        try {
                            method.setAccessible(true);
                            Class<?>[] paramTypes = method.getParameterTypes();

                            if (paramTypes.length == 4 && paramTypes[0] == String.class && paramTypes[1] == byte[].class) {
                                Object result = method.invoke(fabricLoader, className, classData, 0, classData.length);
                                if (result instanceof Class<?>) {
                                    return (Class<?>) result;
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }

            return null;

        } catch (Exception e) {
            return null;
        }
    }

    private Class<?> tryIsolatedClassLoader(ClassLoader fabricLoader, String className, byte[] classData) {
        try {
            ClassLoader isolatedLoader = new ClassLoader(null) {
                @Override
                protected Class<?> findClass(String name) throws ClassNotFoundException {
                    if (name.equals(className)) {
                        return defineClass(name, classData, 0, classData.length);
                    }

                    try {
                        return fabricLoader.loadClass(name);
                    } catch (ClassNotFoundException e) {
                        return super.findClass(name);
                    }
                }
            };

            Class<?> loadedClass = isolatedLoader.loadClass(className);
            return loadedClass;

        } catch (Exception e) {
            return null;
        }
    }

    private Class<?> tryBytecodeManipulation(ClassLoader fabricLoader, String className, byte[] classData) {
        try {
            String proxyClassName = className + "$FabricProxy";

            ClassLoader proxyLoader = new ClassLoader(fabricLoader) {
                private final Map<String, Class<?>> definedClasses = new HashMap<>();

                @Override
                protected Class<?> findClass(String name) throws ClassNotFoundException {
                    if (name.equals(proxyClassName)) {
                        if (!definedClasses.containsKey(name)) {
                            byte[] proxyBytecode = createProxyBytecode(className, classData);
                            Class<?> proxyClass = defineClass(name, proxyBytecode, 0, proxyBytecode.length);
                            definedClasses.put(name, proxyClass);
                        }
                        return definedClasses.get(name);
                    }
                    return super.findClass(name);
                }
            };

            Class<?> proxyClass = proxyLoader.loadClass(proxyClassName);
            return proxyClass;

        } catch (Exception e) {
            return null;
        }
    }

    private byte[] createProxyBytecode(String originalClassName, byte[] originalBytecode) {
        return originalBytecode;
    }

    private Class<?> tryJVMInjection(ClassLoader fabricLoader, String className, byte[] classData) {
        try {
            ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
            if (contextLoader != null && contextLoader != fabricLoader) {
                ClassLoader tempLoader = new ClassLoader(contextLoader) {
                    @Override
                    protected Class<?> findClass(String name) throws ClassNotFoundException {
                        if (name.equals(className)) {
                            return defineClass(name, classData, 0, classData.length);
                        }
                        return super.findClass(name);
                    }
                };

                Class<?> loadedClass = tempLoader.loadClass(className);
                return loadedClass;
            }

            return null;

        } catch (Exception e) {
            return null;
        }
    }


    
    private void initializeFabricMods(List<Object> modInstances, ClassLoader fabricLoader) {
        for (Object modInstance : modInstances) {
            try {
                if (modInstance.getClass() == Object.class) {
                    continue;
                }

                Class<?> modClass = modInstance.getClass();

                if (implementsInterface(modClass, "net.fabricmc.api.ModInitializer")) {
                    try {
                        Method onInitializeMethod = modClass.getMethod("onInitialize");
                        onInitializeMethod.invoke(modInstance);
                    } catch (Exception e) {
                        tryAlternativeInitMethods(modInstance, new String[]{"init", "initialize", "onInit"});
                    }
                }

                if (implementsInterface(modClass, "net.fabricmc.api.ClientModInitializer")) {
                    boolean methodCalled = false;
                    try {
                        Method onInitializeClientMethod = modClass.getMethod("onInitializeClient");
                        onInitializeClientMethod.invoke(modInstance);
                        methodCalled = true;
                    } catch (Exception e) {
                    }

                    if (!methodCalled) {
                        tryAlternativeInitMethods(modInstance, new String[]{
                            "initClient", "onClientInit", "clientInit", "init", "initialize",
                            "onInit", "setup", "start", "load", "onEnable", "enable"
                        });
                    }
                }

                if (implementsInterface(modClass, "net.fabricmc.api.DedicatedServerModInitializer")) {
                    try {
                        Method onInitializeServerMethod = modClass.getMethod("onInitializeServer");
                        onInitializeServerMethod.invoke(modInstance);
                    } catch (Exception e) {
                        tryAlternativeInitMethods(modInstance, new String[]{"initServer", "onServerInit", "serverInit"});
                    }
                }
                
                if (!implementsInterface(modClass, "net.fabricmc.api.ModInitializer") &&
                    !implementsInterface(modClass, "net.fabricmc.api.ClientModInitializer") &&
                    !implementsInterface(modClass, "net.fabricmc.api.DedicatedServerModInitializer")) {
                    
                    tryAlternativeInitMethods(modInstance, new String[]{
                        "init", "initialize", "onInit", "setup", "start", "load",
                        "onEnable", "enable", "activate", "run"
                    });
                }

            } catch (Exception e) {
            }
        }
    }
    
    private boolean implementsInterface(Class<?> clazz, String interfaceName) {
        try {
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> iface : interfaces) {
                if (iface.getName().equals(interfaceName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    private String extractClassName(byte[] classData) {
        try {
            if (classData.length < 10) return null;

            int constantPoolCount = ((classData[8] & 0xFF) << 8) | (classData[9] & 0xFF);
            int offset = 10;

            for (int i = 1; i < constantPoolCount && offset < classData.length - 2; i++) {
                int tag = classData[offset] & 0xFF;
                offset++;

                switch (tag) {
                    case 1:
                        int length = ((classData[offset] & 0xFF) << 8) | (classData[offset + 1] & 0xFF);
                        offset += 2;

                        if (length > 10 && offset + length < classData.length) {
                            String str = new String(classData, offset, length);
                            if (str.contains("/") && !str.startsWith("java/") && !str.startsWith("javax/")) {
                                return str.replace('/', '.');
                            }
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
                    case 7: case 8:
                        offset += 2;
                        break;
                    case 9: case 10: case 11: case 12:
                        offset += 4;
                        break;
                    default:
                        offset += 2;
                        break;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
    
    private boolean isLikelyMainModClass(Class<?> modClass) {
        String className = modClass.getSimpleName().toLowerCase();
        String packageName = modClass.getPackage() != null ? modClass.getPackage().getName().toLowerCase() : "";
        
        if (className.contains("mod") || className.contains("main") || 
              className.contains("client") || className.contains("hud") ||
                 className.contains("abc") || className.contains("ab") ||

            className.equals(modClass.getPackage().getName().substring(modClass.getPackage().getName().lastIndexOf('.') + 1).toLowerCase())) {
            
            Method[] methods = modClass.getDeclaredMethods();
            for (Method method : methods) {
                String methodName = method.getName().toLowerCase();
                if (methodName.contains("init") || methodName.contains("setup") || 
                    methodName.contains("start") || methodName.contains("load")) {
                    return true;
                }
            }
            
            try {
                java.lang.reflect.Field[] fields = modClass.getDeclaredFields();
                for (java.lang.reflect.Field field : fields) {
                    if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) && 
                        java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
                        return true;
                    }
                }
            } catch (Exception e) {
            }
        }
        
        return false;
    }
    
    private void tryAlternativeInstantiation(Class<?> modClass, List<Object> modInstances) {
        try {
            Method[] methods = modClass.getDeclaredMethods();
            for (Method method : methods) {
                if (java.lang.reflect.Modifier.isStatic(method.getModifiers()) && 
                    method.getParameterCount() == 0) {
                    String methodName = method.getName().toLowerCase();
                    if (methodName.contains("init") || methodName.contains("setup") || 
                        methodName.contains("start") || methodName.equals("main")) {
                        try {
                            method.setAccessible(true);
                            method.invoke(null);
                            modInstances.add(new Object());
                            return;
                        } catch (Exception e) {
                        }
                    }
                }
            }
            
            Constructor<?>[] constructors = modClass.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors) {
                try {
                    constructor.setAccessible(true);
                    Class<?>[] paramTypes = constructor.getParameterTypes();
                    Object[] params = new Object[paramTypes.length];
                    
                    for (int i = 0; i < paramTypes.length; i++) {
                        if (paramTypes[i].isPrimitive()) {
                            if (paramTypes[i] == boolean.class) params[i] = false;
                            else if (paramTypes[i] == int.class) params[i] = 0;
                            else if (paramTypes[i] == long.class) params[i] = 0L;
                            else if (paramTypes[i] == float.class) params[i] = 0.0f;
                            else if (paramTypes[i] == double.class) params[i] = 0.0;
                            else params[i] = 0;
                        } else {
                            params[i] = null;
                        }
                    }
                    
                    Object instance = constructor.newInstance(params);
                    modInstances.add(instance);
                    return;
                } catch (Exception e) {
                }
            }
            
        } catch (Exception e) {
        }
    }
    
    private boolean tryAlternativeInitMethods(Object modInstance, String[] methodNames) {
        Class<?> modClass = modInstance.getClass();

        for (String methodName : methodNames) {
            try {
                Method method = modClass.getDeclaredMethod(methodName);
                method.setAccessible(true);
                method.invoke(modInstance);
                return true;
            } catch (Exception e) {
            }
        }

        return tryAnyInitMethod(modInstance);
    }
    
    private boolean tryAnyInitMethod(Object modInstance) {
        Class<?> modClass = modInstance.getClass();
        Method[] allMethods = modClass.getDeclaredMethods();

        for (Method method : allMethods) {
            String methodName = method.getName().toLowerCase();

            if ((methodName.contains("init") || methodName.contains("setup") ||
                 methodName.contains("start") || methodName.contains("load") ||
                 methodName.contains("enable") || methodName.contains("activate")) &&
                method.getParameterCount() == 0 &&
                !java.lang.reflect.Modifier.isStatic(method.getModifiers())) {

                try {
                    method.setAccessible(true);
                    method.invoke(modInstance);
                    return true;
                } catch (Exception e) {
                }
            }
        }

        return false;
    }
}
