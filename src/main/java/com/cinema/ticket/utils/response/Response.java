package com.cinema.ticket.utils.response;

import com.cinema.ticket.utils.constant.ResponseCode;

public class Response {

    private Integer code;

    private String data;

    private String msg;

    public Response(){}

    public Response(Integer code, String data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Response(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setMag(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static Response success(String msg){
        return new Response(ResponseCode.SUCCESS,msg);
    }

    public static Response success(String data,String msg){
        return new Response(ResponseCode.SUCCESS,data,msg);
    }
    public static Response error(String msg){
        return new Response(ResponseCode.ERROR,msg);
    }

    public static Response error(String data,String msg){
        return new Response(ResponseCode.ERROR,data,msg);
    }

    public static Response getResponse(boolean flag){
        if(flag){
            return new Response(ResponseCode.SUCCESS,"操作成功");
        }else{
            return new Response(ResponseCode.ERROR,"操作失败");
        }
    }
}
