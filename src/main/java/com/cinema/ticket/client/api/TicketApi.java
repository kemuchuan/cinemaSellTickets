package com.cinema.ticket.client.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.cinema.ticket.client.utils.request.RequestUtils;
import com.cinema.ticket.client.domain.Ticket;
import com.cinema.ticket.utils.constant.ResponseCode;
import com.cinema.ticket.utils.request.Request;
import com.cinema.ticket.utils.request.RequestMethod;
import com.cinema.ticket.utils.response.Response;

import java.util.List;

/**
 * 前端操作票的api
 */
public class TicketApi {


    /**
     * 获取所有的票数据
     * @return 所有的票数据结果
     */
    public List<Ticket> selectAllTicket(){
        // 封装请求对象，RequestMethod.SELECT_ALL_TICKET为请求方法
        Request request = new Request(RequestMethod.SELECT_ALL_TICKET);
        // 通过RequestUtils发送请求，并得到响应体
        Response response = RequestUtils.send(request);
        // 判断相应是否成功，成功则返回list集合否则返回空
        if (response != null && response.getCode().equals(ResponseCode.SUCCESS)) {
            // JSON.parseArray(response.getData(), Ticket.class)方法表示，将字符串，按照Ticket类的格式转换成List<Ticket>集合
            return JSON.parseArray(response.getData(), Ticket.class);
        }
        return null;
    }

    /**
     * 添加单张票
     * @param ticket 待添加票的数据
     * @return 返回是否添加成功
     */
    public boolean insertTicket(Ticket ticket) {
        // JSON.toJSONString(ticket)表示将Ticket转换为json类型的字符串
        Request request = new Request(RequestMethod.INSERT_TICKET, JSON.toJSONString(ticket));
        Response response = RequestUtils.send(request);
        // 返回是否插入成功
        return response != null && response.getCode().equals(ResponseCode.SUCCESS);
    }

    /**
     * 添加多条票数据（没用到）
     * @param ticketList 待添加的票数据
     * @return 返回添加是否成功
     */
    public boolean insertTickets(List<Ticket> ticketList){
        // JSON.toJSONString(ticket)表示将Ticket转换为json类型的字符串
        Request request = new Request(RequestMethod.INSERT_TICKETS,JSON.toJSONString(ticketList));
        Response response = RequestUtils.send(request);
        return response != null && response.getCode().equals(ResponseCode.SUCCESS);
    }

    /**
     * 更新票数据
     * @param ticket 待更新的票数据
     * @return 返回是否更新成功
     */
    public boolean updateTicket(Ticket ticket){
        Request request = new Request(RequestMethod.UPDATE_TICKET,JSON.toJSONString(ticket));
        Response response = RequestUtils.send(request);
        return response != null && response.getCode().equals(ResponseCode.SUCCESS);
    }

    /**
     * 删除票
     * @param ticketId 待删除票的ID
     * @return 返回是否删除成功
     */
    public boolean deleteTicket(Integer ticketId){
        Request request = new Request(RequestMethod.DELETE_TICKET,JSON.toJSONString(ticketId));
        Response response = RequestUtils.send(request);
        return response != null && response.getCode().equals(ResponseCode.SUCCESS);
    }

    /**
     * 取消选片
     * @param userId 取消选票的用户ID
     * @param ticketId 待取消票的ID
     * @return 返回是否取消选票成功
     */
    public boolean cancelChooseTicket(Integer userId,Integer ticketId){
        // 下面的jsonObject表示是将userId和ticketId封装为json字符串对象，
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId",userId);
        jsonObject.put("ticketId",ticketId);
        // jsonObject.toString()将对象转换为json字符串
        Request request = new Request(RequestMethod.CANCEL_CHOOSE_TICKET,jsonObject.toString());
        Response response = RequestUtils.send(request);
        return response != null && response.getCode().equals(ResponseCode.SUCCESS);
    }

    /**
     * 查看选票历史
     * @param userId 待查找的用户ID
     * @return 返回查找结果
     */
    public List<Ticket> selectHistoryTicket(Integer userId){
        Request request = new Request(RequestMethod.SELECT_HISTORY_TICKET,JSON.toJSONString(userId));
        Response response = RequestUtils.send(request);
        if (response != null && response.getCode().equals(ResponseCode.SUCCESS)) {
            return JSON.parseArray(response.getData(), Ticket.class);
        }
        return null;
    }


    /**
     * 选票操作
     * @param userId 选票用户ID
     * @param ticketId 票ID
     * @return 返回结果
     */
    public boolean chooseTicket(Integer userId,Integer ticketId){
        Request request = new Request(RequestMethod.CHOOSE_TICKET);
        // 下面的jsonObject表示是将userId和ticketId封装为json字符串对象，
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId",userId);
        jsonObject.put("ticketId",ticketId);
        // jsonObject.toJSONString()将对象转换为json字符串
        request.setData(jsonObject.toJSONString());
        Response response = RequestUtils.send(request);
        return response != null && response.getCode().equals(ResponseCode.SUCCESS);
    }

}
