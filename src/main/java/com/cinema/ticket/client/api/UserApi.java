package com.cinema.ticket.client.api;

import com.alibaba.fastjson.JSON;
import com.cinema.ticket.client.domain.User;
import com.cinema.ticket.client.utils.request.RequestUtils;
import com.cinema.ticket.utils.constant.ResponseCode;
import com.cinema.ticket.utils.request.Request;
import com.cinema.ticket.utils.request.RequestMethod;
import com.cinema.ticket.utils.response.Response;

/**
 * 前端操作User的api
 */
public class UserApi {

    /**
     * 登录方法
     * @param userName 用户名
     * @param password 密码
     * @return 登录后的对象
     */
    public User login(String userName,String password){
        User user = new User(userName,password);
        Request request = new Request(RequestMethod.LOGIN,JSON.toJSONString(user));
        Response response = RequestUtils.send(request);
        if (response != null && response.getCode().equals(ResponseCode.SUCCESS)) {
            return JSON.parseObject(response.getData(), User.class);
        }else{
            return null;
        }
    }

}
