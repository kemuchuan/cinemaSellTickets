package com.cinema.ticket.server;

import com.cinema.ticket.server.annotation.Servlet;
import com.cinema.ticket.server.service.ServerService;
import com.cinema.ticket.server.service.pac.PackageScan;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

// 后台启动类
public class ServerMain {

    public static void main(String[] args) {
        ServerService serverService = new ServerService();
        System.out.println("服务启动成功~~~~~");
        serverService.run();
    }

//    public static void main(String[] args) {
//
//        List<String> strings = PackageScan.packageScan();
//
//    }


}
