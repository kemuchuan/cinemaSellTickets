package com.cinema.ticket.server.service;

import com.cinema.ticket.server.service.handler.HandlerTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务设置的对象
 */
public class ServerService {

    /**
     * 创建一个固定大小的线程池，包含10个线程
     */
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    /**
     * 声明所需的ServerSocket对象
     */
    private ServerSocket serverSocket;


    // 默认端口号
    private Integer port = 8090;

    public ServerService(Integer port) {
        this.port = port;
    }

    public ServerService() {
    }

    public void run() {
        try {
            // 初始化ServerSocket对象，并监听8090端口，接收连接
            serverSocket = new ServerSocket(this.port);
//            HandlerTask.init();
            // 一直接收
            while (true) {
                // 等待连接，若连接则返回对象
                Socket socket = serverSocket.accept();
                // 交予线程池去处理任务
                executor.execute(new HandlerTask(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
