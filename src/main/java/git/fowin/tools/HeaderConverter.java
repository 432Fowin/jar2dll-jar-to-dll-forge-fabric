package git.fowin.tools;

import java.io.*;
import java.util.jar.*;

public class HeaderConverter {
    
    public static void main(String[] args) {
        if (args.length != 3) {
            System.exit(1);
        }
        
        String type = args[0];
        String inputPath = args[1];
        String outputPath = args[2];
        
        try {
            if ("injector".equals(type)) {
                convertClassFile(inputPath, outputPath, "injector_class_data");
            } else if ("input-jar".equals(type)) {
                convertJarFile(inputPath, outputPath);
            }
        } catch (Exception e) {
            System.exit(1);
        }
    }
    
    private static void convertClassFile(String inputPath, String outputPath, String arrayName) throws IOException {
        byte[] classData = readFile(inputPath);
        writeHeader(outputPath, arrayName, classData);
    }
    
    private static void convertJarFile(String inputPath, String outputPath) throws IOException {
        try (JarFile jarFile = new JarFile(inputPath)) {
            java.util.List<byte[]> classes = new java.util.ArrayList<>();
            
            java.util.Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    try (InputStream is = jarFile.getInputStream(entry)) {
                        byte[] classData = readStream(is);
                        classes.add(classData);
                    }
                }
            }
            
            writeJarHeader(outputPath, classes);
        }
    }
    
    private static byte[] readFile(String path) throws IOException {
        try (FileInputStream fis = new FileInputStream(path);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        }
    }
    
    private static byte[] readStream(InputStream is) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        }
    }
    
    private static void writeHeader(String outputPath, String arrayName, byte[] data) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println("#pragma once");
            writer.println();
            writer.println("static const unsigned char " + arrayName + "[] = {");
            
            for (int i = 0; i < data.length; i++) {
                if (i % 16 == 0) {
                    writer.print("  ");
                }
                writer.printf("0x%02X", data[i] & 0xFF);
                if (i < data.length - 1) {
                    writer.print(",");
                }
                if (i % 16 == 15 || i == data.length - 1) {
                    writer.println();
                }
            }
            
            writer.println("};");
            writer.println();
            writer.println("static const size_t " + arrayName + "_size = " + data.length + ";");
        }
    }
    
    private static void writeJarHeader(String outputPath, java.util.List<byte[]> classes) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println("#pragma once");
            writer.println();
            
            for (int i = 0; i < classes.size(); i++) {
                byte[] classData = classes.get(i);
                String arrayName = "jar_class_" + i + "_data";
                
                writer.println("static const unsigned char " + arrayName + "[] = {");
                
                for (int j = 0; j < classData.length; j++) {
                    if (j % 16 == 0) {
                        writer.print("  ");
                    }
                    writer.printf("0x%02X", classData[j] & 0xFF);
                    if (j < classData.length - 1) {
                        writer.print(",");
                    }
                    if (j % 16 == 15 || j == classData.length - 1) {
                        writer.println();
                    }
                }
                
                writer.println("};");
                writer.println();
            }
            
            writer.println("static const unsigned char* jar_classes[] = {");
            for (int i = 0; i < classes.size(); i++) {
                writer.println("  jar_class_" + i + "_data,");
            }
            writer.println("};");
            writer.println();
            writer.println("static const size_t jar_class_sizes[] = {");
            for (int i = 0; i < classes.size(); i++) {
                writer.println("  " + classes.get(i).length + ",");
            }
            writer.println("};");
            writer.println();
            writer.println("static const size_t jar_classes_count = " + classes.size() + ";");
        }
    }
}
