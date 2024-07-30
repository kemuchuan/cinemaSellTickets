package com.cinema.ticket.server.service.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cinema.ticket.server.annotation.PathMapping;
import com.cinema.ticket.server.annotation.Servlet;
import com.cinema.ticket.server.dao.TicketDao;
import com.cinema.ticket.server.domain.Ticket;
import com.cinema.ticket.utils.request.Request;
import com.cinema.ticket.utils.response.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 处理票请求的类
 */
@Servlet
public class TicketTask {

    /**
     * 数据操作对象
     */
    private final TicketDao ticketDao = TicketDao.getTicketDao();

    /**
     * 任务服务对象
     */
    private static final TicketTask ticketTask = new TicketTask();

    /**
     * 模拟工厂类，返回全局统一的一个任务服务对象
     * @return 返回TicketTask对象
     */
    public static TicketTask getTicketTask(){
        return ticketTask;
    }

    private TicketTask(){}

    @PathMapping(value = "selectAllTicket")
    public void selectAllTicket(Request request,OutputStream os){
        // 查询
        List<Ticket> list = this.ticketDao.selectAllTicket();

        // 封装后返回
        this.osResponse(os,Response.success(JSON.toJSONString(list),"查询成功"));
    }

    /**
     * 添加票数据
     * @param request 请求参数
     * @param os 输出流
     */
    @PathMapping(value = "insertTicket")
    public void insertTicket(Request request, OutputStream os){
        // 将data转为票数据对象
        Ticket ticket = JSON.parseObject(request.getData(), Ticket.class);
        // 添加
        boolean flag = this.ticketDao.insertTicket(ticket);
        // 根据是否成功，创建返回对象
        Response response = Response.getResponse(flag);
        this.osResponse(os,response);// 写出
    }

    /**
     * 添加多条数据
     * @param request 请求对象参数
     * @param os 输出流兑现
     */
    @PathMapping(value = "insertTickets")
    public void insertTickets(Request request, OutputStream os){
        List<Ticket> list = JSON.parseArray(request.getData(), Ticket.class);
        boolean flag = this.ticketDao.insertTickets(list);
        this.osResponse(os,Response.getResponse(flag));
    }

    /**
     * 更新票数据
     * @param request 请求的数据
     * @param os 输出流对象
     */
    @PathMapping(value = "updateTicket")
    public void updateTicket(Request request, OutputStream os){
        Ticket ticket = JSON.parseObject(request.getData(), Ticket.class);
        boolean flag = this.ticketDao.updateTicketState(ticket.getTicketId(),ticket.getState(),ticket.getPrice());
        Response response = Response.getResponse(flag);
        this.osResponse(os,response);
    }

    /**
     * 查询历史选座
     * @param request 查询所需的用户ID
     * @param os 输出流对象
     *           data{userId}
     */
    @PathMapping(value = "selectHistoryTicket")
    public void selectHistoryTicket(Request request, OutputStream os){
        Integer userId = JSON.parseObject(request.getData(), Integer.class);
        List<Ticket> list = this.ticketDao.selectHistory(userId);
        this.osResponse(os,Response.success(JSON.toJSONString(list),"查询成功"));
    }

    /**
     * 选票操作
     * @param request 请求的封装对象
     * @param os 输出流对象
     *           data:{ticketId,userId}
     */
    @PathMapping(value = "chooseTicket")
    public void chooseTicket(Request request,OutputStream os){
        // 将data转为jsonObject，再从jsonObject内拿到ticketID和userId
        JSONObject jsonObject = JSON.parseObject(request.getData());
        Integer ticketId = (Integer)jsonObject.get("ticketId");
        Integer userId = (Integer)jsonObject.get("userId");
        // 选票
        boolean flag = this.ticketDao.chooseTicket(ticketId, userId);
        this.osResponse(os,Response.getResponse(flag));
    }

    /**
     * 删除票数据
     * @param request 请求的封装对象
     * @param os 输出流对象
     */
    @PathMapping(value = "deleteTicket")
    public void deleteTicket(Request request,OutputStream os){
        System.out.println("请求处理中");
        Integer ticketId = JSON.parseObject(request.getData(), Integer.class);
        boolean flag = this.ticketDao.deleteTicket(ticketId);
        this.osResponse(os,Response.getResponse(flag));
    }

    /**
     * 取消选票操作
     * @param request 封装票数据的请求对象
     * @param os 输出流对象
     */
    @PathMapping(value = "cancelChooseTicket")
    public void cancelChooseTicket(Request request,OutputStream os){
        JSONObject jsonObject = JSON.parseObject(request.getData());
        Integer ticketId = (Integer)jsonObject.get("ticketId");
        Integer userId = (Integer)jsonObject.get("userId");
        boolean flag = this.ticketDao.cancelChooseTicket(ticketId, userId);
        this.osResponse(os,Response.getResponse(flag));
    }

    /**
     * 封装后的返回对象的方法（提取重复代码封装为一个函数）
     * @param os 输出流对象
     * @param response 返回信息对象
     */
    private void osResponse(OutputStream os,Response response){
        try {
            // 写出数据（给前端返回响应数据）
            os.write(JSON.toJSONBytes(response));
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
