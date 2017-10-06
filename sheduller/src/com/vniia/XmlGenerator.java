package com.vniia;

import sun.misc.BASE64Encoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class XmlGenerator {
    public static final String XML_FILE_NAME = "update.xml";

    //TODO use void generate(File xml, String startupExeName, String appName)
    public static String generate(String path, String startupExeName, String appName) throws IOException {
        File updateDir = new File(path);
        //TODO use nio: 
        /*  String path = updateDir.getPath();
            Path dir = Paths.get(path);
            DirectoryStream<Path> files = Files.newDirectoryStream(dir);
            for (Path path_ : files) {
                File file = path_.toFile();
            }
        */
        if (!updateDir.exists() || !updateDir.isDirectory())
            throw new IOException("Директория не существует");

        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append(
                String.format(
                        "<AppUpdater UpdatePath=\"%s\" StartUpExe=\"%s\" Version=\"%s\" AppName=\"%s\" Critical=\"0\">",
                        updateDir.getPath(),
                        startupExeName,
                        getVersion(updateDir) + 1,
                        appName
                )
        );

        List<File> files = getFilesRecursive(updateDir);
        for (File file : files) {
            if (file.getName().equals(XML_FILE_NAME))
                continue;

            xmlBuilder.append(
                    String.format(
                            "\r\n  <File Name=\"%s\" Path=\"%s\">%s</File>",
                            file.getName(),
                            file.getParent().replace(updateDir.getPath(), ""),
                            getHashString(file)
                    )
            );
        }

        xmlBuilder.append("\r\n</AppUpdater>");

        return xmlBuilder.toString();
    }

    private static List<File> getFilesRecursive(File dir) {
        List<File> result = new LinkedList<File>();
        List<File> files = Arrays.asList(dir.listFiles());
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                if (file1.isDirectory() && file2.isFile())
                    return 1;
                else if (file1.isFile() && file2.isDirectory())
                    return -1;
                else
                    return file1.getName().compareTo(file2.getName());
            }
        });
        for (File file : files) {
            if (file.isFile())
                result.add(file);
            else
                result.addAll(getFilesRecursive(file));
        }
        return result;
    }

    public static String getHashString(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[fis.available()];
        try {
            fis.read(bytes);
            byte[] md5Bytes = MessageDigest.getInstance("SHA").digest(bytes);
            return new BASE64Encoder().encode(md5Bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } finally {
            fis.close();
        }
    }

    public static int getVersion(File xmlFile) throws IOException {
        //File xmlFile = new File(dir.getPath() + File.separator + XML_FILE_NAME);
        if (xmlFile.exists()) {
            FileInputStream fis = new FileInputStream(xmlFile);
            try {
                byte[] bytes = new byte[fis.available()];
                fis.read(bytes);
                String xmlString = new String(bytes).toLowerCase();
                xmlString = xmlString.substring(xmlString.indexOf("version=\"") + 9);
                xmlString = xmlString.substring(0, xmlString.indexOf("\""));
                return Integer.valueOf(xmlString);
            } finally {
                fis.close();
            }
        }
        return 0;
    }
}
