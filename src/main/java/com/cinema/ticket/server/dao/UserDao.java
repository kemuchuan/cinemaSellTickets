package com.cinema.ticket.server.dao;

import com.cinema.ticket.server.domain.User;

import java.util.List;

/**
 * 对User信息进行管理的类
 */
public class UserDao {

    /**
     * 存放User对象d的List集合（1：管理员，0：普通用户）
     */
    private final List<User> userList =
            List.of(
                    new User(1001,"admin","123456",1),
                    new User(1002,"zhangsan","123456",0),
                    new User(1003,"lisi","123456",0),
                    new User(1004,"wangwu","123456",0),
                    new User(1005,"lihua","123456",0)
            );

    private final static UserDao userDao = new UserDao();

    public static UserDao getUserDao(){
        return userDao;
    }

    private UserDao(){}

    public User selectUserByUserNameAndPassword(User user){
        User resultUser = null;
        for (User temUser : this.userList) {
            if(temUser.getUserName().equals(user.getUserName())&&temUser.getPassword().equals(user.getPassword())){
                resultUser = temUser;
                break;
            }
        }
        return resultUser;
    }

}
