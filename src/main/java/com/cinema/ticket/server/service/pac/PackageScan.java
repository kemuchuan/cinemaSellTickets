package com.cinema.ticket.server.service.pac;

import com.cinema.ticket.server.ServerMain;
import com.cinema.ticket.server.annotation.Servlet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PackageScan {

    private PackageScan() {}

    // 扫描包路径下的所有class文件
    public static List<String> packageScan() {
        List<File> files = new ArrayList<>();
        String rooTPath = ClassLoader.getSystemResource("").getPath();
        File file = new File(rooTPath);
        List<String> classNames = new ArrayList<>();
        getFiles(file, files);
        for (File f : files) {
            String path = f.getPath();
            path = path.substring(ClassLoader.getSystemResource("").getPath().length() - 1).replace(".class", "").replace("\\", ".");
            classNames.add(path);
        }
        return classNames;
    }

    // 递归获取所有class文件
    private static void getFiles(File file, List<File> files) {
        if (file.isFile() && file.getName().endsWith(".class")) {
            files.add(file);
        }
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File f : listFiles) {
                    getFiles(f, files);
                }
            }
        }
    }

}
