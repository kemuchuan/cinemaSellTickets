package com.cinema.ticket.server.service.task;

import com.alibaba.fastjson2.JSON;
import com.cinema.ticket.server.annotation.PathMapping;
import com.cinema.ticket.server.annotation.Servlet;
import com.cinema.ticket.server.domain.User;
import com.cinema.ticket.server.dao.UserDao;
import com.cinema.ticket.utils.request.Request;
import com.cinema.ticket.utils.response.Response;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 处理用户请求的；类
 */
@Servlet
public class UserTask {

    private final UserDao userDao = UserDao.getUserDao();

    private final static UserTask userTask = new UserTask();

    private UserTask(){}

    public static UserTask getUserTask(){
        return userTask;
    }

    @PathMapping(value = "login")
    public void login(Request request, OutputStream os){
        // 获取user对象
        User user = JSON.parseObject(request.getData(),User.class);
        // 查询是否匹配
        user = this.userDao.selectUserByUserNameAndPassword(user);
        Response response = null;
        // 判断是否为空
        if(user==null){
            // 空则返回错误
            response = Response.error("用户名或密码错误");
        }else{
            // 否则返回登录的对象
            response = Response.success(JSON.toJSONString(user),"登录成功");
        }
        try {
            // 写出对象
            os.write(JSON.toJSONString(response).getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
