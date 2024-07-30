package com.cinema.ticket.utils.request;

public class Request {

    private String method;

    private String data;

    public Request() {
    }

    public Request(String method) {
        this.method = method;
    }

    public Request(String method, String data) {
        this.method = method;
        this.data = data;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMethod() {
        return method;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
