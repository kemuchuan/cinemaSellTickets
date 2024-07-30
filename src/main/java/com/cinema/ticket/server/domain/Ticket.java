package com.cinema.ticket.server.domain;

/**
 * 票的信息类
 */
public class Ticket {


    // 静态Integer表示票的状态
    /**
     * 空闲
     */
    public final static Integer VACANT = 0;
    /**
     * 被选中
     */
    public final static Integer SELECTED = 1;
    /**
     * 待付款
     */
    public final static Integer PENDING_PAYMENT = 2;
    /**
     *
     */
    public final static Integer ACCOUNT_PAID = 3;

    /**
     * 票所属的用户ID
     */
    private Integer userId;

    /**
     * 票唯一标识
     */
    private Integer ticketId;

    /**
     * 票的x坐标
     */
    private Integer locX;

    /**
     * 票的y坐标（0：空闲，1：被选中，2：待付费，3：已出票）
     */
    private Integer locY;

    /**
     * 票的状态
     */
    private Integer state;

    /**
     * 票价
     */
    private double price;

    public Ticket() {
    }

    public Ticket(Integer ticketId, Integer locX, Integer locY, Integer state, double price) {
        this.ticketId = ticketId;
        this.locX = locX;
        this.locY = locY;
        this.state = state;
        this.price = price;
    }

    public Integer getLocX() {
        return locX;
    }

    public void setLocX(Integer locX) {
        this.locX = locX;
    }

    public Integer getLocY() {
        return locY;
    }

    public void setLocY(Integer locY) {
        this.locY = locY;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "userId=" + userId +
                ", ticketId=" + ticketId +
                ", locX=" + locX +
                ", locY=" + locY +
                ", state='" + state + '\'' +
                ", price=" + price +
                '}';
    }
}
