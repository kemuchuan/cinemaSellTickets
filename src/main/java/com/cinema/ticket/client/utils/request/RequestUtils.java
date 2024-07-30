package com.cinema.ticket.client.utils.request;

import com.alibaba.fastjson.JSON;
import com.cinema.ticket.client.domain.User;
import com.cinema.ticket.utils.constant.Computer;
import com.cinema.ticket.utils.request.Request;
import com.cinema.ticket.utils.request.RequestMethod;
import com.cinema.ticket.utils.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public final class RequestUtils {

    private RequestUtils(){}

    /**
     * 发送请求的对象
     * @param request 请求对象
     * @return 返回相应对象
     */
    public static Response send(Request request){
        // 定义，方便关闭
        Socket socket = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            // 建立连接
            socket = new Socket(Computer.host,Computer.port);
            // 获取输入、输出流对象
            os = socket.getOutputStream();
            is = socket.getInputStream();
            // 写出数据（发送请求信息）
            os.write(JSON.toJSONBytes(request));
            // 刷新
            os.flush();
            // 接收数据的数组
            byte[] bytes = new byte[1024];
            int len = 0;
            // 拼接对象
            StringBuilder sb = new StringBuilder();
            while((len = is.read(bytes))!=-1){
                sb.append(new String(bytes,0,len));
            }
            // 将得到的字符串转为Response对象并返回
            return JSON.parseObject(sb.toString(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 关闭资源
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null; // 若出现异常则返回null
    }

}
