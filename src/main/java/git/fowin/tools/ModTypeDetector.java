package git.fowin.tools;

import java.io.*;
import java.util.jar.*;
import java.util.zip.*;

/**
 * Utility to detect mod type (Fabric or Forge) from JAR files
 */
public class ModTypeDetector {
    
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ModTypeDetector <jar-file>");
            System.exit(1);
        }
        
        String jarPath = args[0];
        String modType = detectModType(jarPath);
        System.out.println(modType);
    }
    
    public static String detectModType(String jarPath) {
        try (JarFile jarFile = new JarFile(jarPath)) {
            ZipEntry fabricEntry = jarFile.getEntry("fabric.mod.json");
            if (fabricEntry != null) {
                return "FABRIC";
            }

            ZipEntry forgeEntry = jarFile.getEntry("META-INF/mods.toml");
            if (forgeEntry != null) {
                return "FORGE";
            }

            ZipEntry legacyForgeEntry = jarFile.getEntry("mcmod.info");
            if (legacyForgeEntry != null) {
                return "FORGE";
            }

            return analyzeClassFiles(jarFile);
            
        } catch (Exception e) {
            System.err.println("Error detecting mod type: " + e.getMessage());
            return "FORGE";
        }
    }
    
    private static String analyzeClassFiles(JarFile jarFile) {
        int fabricScore = 0;
        int forgeScore = 0;
        
        try {
            java.util.Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    try (InputStream is = jarFile.getInputStream(entry)) {
                        byte[] classData = readAllBytes(is);
                        String analysis = analyzeClassData(classData);
                        
                        if (analysis.contains("FABRIC")) {
                            fabricScore++;
                        }
                        if (analysis.contains("FORGE")) {
                            forgeScore++;
                        }
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
        }
        
        if (fabricScore > forgeScore && fabricScore > 0) {
            return "FABRIC";
        } else if (forgeScore > 0) {
            return "FORGE";
        }
        
        return "FORGE"; // Default fallback
    }
    
    private static String analyzeClassData(byte[] classData) {
        try {
            String classDataStr = new String(classData, "ISO-8859-1");

            // Strong Forge indicators (higher priority)
            if (classDataStr.contains("net.minecraftforge") ||
                classDataStr.contains("MinecraftForge") ||
                classDataStr.contains("fml.common.Mod") ||
                classDataStr.contains("ForgeModContainer") ||
                classDataStr.contains("ModLoadingContext")) {
                return "FORGE";
            }

            // Strong Fabric indicators
            if (classDataStr.contains("net.fabricmc") ||
                classDataStr.contains("FabricLoader") ||
                classDataStr.contains("fabric.api")) {
                return "FABRIC";
            }

            // Weaker indicators - check for interfaces/annotations
            if (classDataStr.contains("ModInitializer") ||
                classDataStr.contains("ClientModInitializer")) {
                // Could be Fabric, but check for Forge context
                if (classDataStr.contains("forge") || classDataStr.contains("Forge")) {
                    return "FORGE"; // Probably a Forge mod with similar naming
                }
                return "FABRIC";
            }

            // Forge-specific annotations and patterns
            if (classDataStr.contains("@Mod") ||
                classDataStr.contains("EventHandler") ||
                classDataStr.contains("SubscribeEvent") ||
                classDataStr.contains("EventBusSubscriber")) {
                return "FORGE";
            }

        } catch (Exception e) {
            // Continue
        }

        return "UNKNOWN";
    }
    
    private static byte[] readAllBytes(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }
}
