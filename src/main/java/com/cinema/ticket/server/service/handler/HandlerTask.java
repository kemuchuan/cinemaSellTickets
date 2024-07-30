package com.cinema.ticket.server.service.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.cinema.ticket.server.annotation.PathMapping;
import com.cinema.ticket.server.annotation.Servlet;
import com.cinema.ticket.server.domain.User;
import com.cinema.ticket.server.service.pac.PackageScan;
import com.cinema.ticket.server.service.task.TicketTask;
import com.cinema.ticket.server.service.task.UserTask;
import com.cinema.ticket.utils.request.Request;
import com.cinema.ticket.utils.request.RequestMethod;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分发请求的线程对象
 */
public class HandlerTask implements Runnable {

    // 路径映射
    private final static Map<String, Class<?>> pathMap = new HashMap<>();

    // 对象映射
    private final static Map<Class<?>, Object> objectMap = new HashMap<>();

    // 方法映射
    private final static Map<String, Method> methodMap = new HashMap<>();

    static {
        init();
    }

    private final Socket socket;

    private final UserTask userTask = UserTask.getUserTask();

    private final TicketTask ticketTask = TicketTask.getTicketTask();

    public HandlerTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // 声明输入流和输出流对象
        InputStream is = null;
        OutputStream os = null;
        try {
            // 初始化输入流和输出流对象
            is = socket.getInputStream();
            os = socket.getOutputStream();
            // 分发业务代码
            this.distributeTask(is, os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭输入流和输出流对象
            this.close(is, os);
        }
    }

    /**
     * 分发task的方法
     *
     * @param is 输入流对象
     * @param os 输出流对象
     */
    private void distributeTask(InputStream is, OutputStream os) {
        try {
            // 获取请求信息字符串
            byte[] bytes = new byte[1024];
            int len = is.read(bytes);
            // 转为Request对象
            Request request = JSON.parseObject(new String(bytes, 0, len), Request.class);
            String method = request.getMethod();
            // 从methodMap中获取请求方法对应的映射方法
            Method targetMethod = methodMap.get(method);
            // 若存在映射方法，则调用映射方法
            if (targetMethod != null) {
                // 获取请求方法对应的类
                Class<?> targetClass = pathMap.get(method);
                // 获取请求方法对应的对象
                Object targetObject = objectMap.get(targetClass);
                try {
                    // 若对象不存在，则调用类的get方法获取对象（这是因为之前转发写死的时候这么写了，懒得改了）
                    if (targetObject == null) {
                        // 获取类的getxxxx方法
                        Method declaredMethod = targetClass.getDeclaredMethod("get" + targetClass.getName().substring(targetClass.getName().lastIndexOf(".")+1));
                        // 调用方法获取对象
                        targetObject = declaredMethod.invoke(null);
                        // 将对象存入映射中
                        objectMap.put(targetClass, targetObject);
                    }
                    // 调用对应的方法
                    targetMethod.invoke(targetObject, request, os);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭输入输出流对象方法
     *
     * @param is 输入流对象
     * @param os 输出流对象
     */
    private void close(InputStream is, OutputStream os) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化方法，扫描根路径下所有的类，并获取注解信息，并将其存入映射中
     */
    private static void init() {
        // 调用包扫描工具类，扫描所有类，拿到所有的类名
        List<String> classNames = PackageScan.packageScan();
        // 遍历所有类，获取注解信息，并存入映射中
        for (String className : classNames) {
            try {
                // 加载类
                // false, HandlerTask.class.getClassLoader() 不写这个参数容易造成线程阻塞。具体我也不太了解。
                Class<?> clazz = Class.forName(className, false, HandlerTask.class.getClassLoader());
                // 获取注解信息
                Servlet annotation = clazz.getAnnotation(Servlet.class);
                // 若存在注解信息，则存入映射中
                if (annotation != null) {
                    // 拿到类的所有的成员方法
                    Method[] declaredMethods = clazz.getDeclaredMethods();
                    // 遍历成员方法，获取注解信息
                    for (Method method : declaredMethods) {
                        // 获取注解信息
                        PathMapping pathMapping = method.getDeclaredAnnotation(PathMapping.class);
                        // 若存在注解信息，则存入映射中
                        if (pathMapping != null) {
                            // 获取value值，存入映射中
                            String[] value = pathMapping.value();
                            for (String path : value) {
                                // 若路径重复，则抛出异常
                                if (pathMap.containsKey(path)) {
                                    throw new RuntimeException("路径重复：" + path);
                                }
                                // 存入路径与类和路径和方法的映射
                                pathMap.put(path, clazz);
                                methodMap.put(path, method);
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
