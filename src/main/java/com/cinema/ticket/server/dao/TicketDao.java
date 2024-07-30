package com.cinema.ticket.server.dao;

import com.cinema.ticket.server.domain.Ticket;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 对票信息查询的对象
 */
public class TicketDao {

    /**
     * 票ID
     */
    int ticketId = 1000;

    /**
     * 存放所有票数据的集合
     */
    private List<Ticket> ticketList = new ArrayList<>();

    /**
     * 下面两个保证只有一个ticketDao对象
     */
    private final static TicketDao ticketDao = new TicketDao();

    public static TicketDao getTicketDao() {
        return ticketDao;
    }

    private TicketDao() {
        // 初始化票数据
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 8; j++) {
                this.ticketList.add(new Ticket(this.ticketId++, i, j, Ticket.VACANT, 34.2));
            }
        }
        this.ticketList.add(new Ticket(this.ticketId++,8,8,Ticket.VACANT,38.9));
        this.ticketList.add(new Ticket(this.ticketId++,7,8,Ticket.VACANT,38.9));
        this.ticketList.add(new Ticket(this.ticketId++,6,8,Ticket.VACANT,38.9));
        this.ticketList.add(new Ticket(this.ticketId++,5,8,Ticket.VACANT,38.9));
    }

    /**
     * 获取所有的票数据
     *
     * @return 返回结果
     */
    public List<Ticket> selectAllTicket() {
        return this.ticketList;
    }

    /**
     * 插入一个票数据
     *
     * @param ticket 票数据
     * @return 返回结果
     */
    public synchronized boolean insertTicket(Ticket ticket) {
        this.ticketId++;
        System.out.println("添加票:" + this.ticketId);
        // 设置ID
        ticket.setTicketId(ticketId);
        // 检查是否有重复添加
        for (Ticket tmpTicket : this.ticketList) {
            if(tmpTicket.getLocX().equals(ticket.getLocX())&&tmpTicket.getLocY().equals(ticket.getLocY())){
                // 重复添加,返回
                return false;
            }
        }
        // 设置状态
        ticket.setState(Ticket.VACANT);
        return this.ticketList.add(ticket);
    }

    /**
     * 添加多条数据，这个暂时没用到
     *
     * @param tickets 待添加的票数据
     * @return 返回是否添加成功
     */
    public synchronized boolean insertTickets(List<Ticket> tickets) {
        return this.ticketList.addAll(tickets);
    }

    /**
     * 更新票的状态
     *
     * @param ticketId 待更新票的ID
     * @param state    状态
     * @return 返回是否更新成功
     */
    public synchronized boolean updateTicketState(Integer ticketId, Integer state, double price) {
        System.out.println("更新票:" + ticketId);
        boolean flag = false;
        for (Ticket tempTicket : this.ticketList) {
            if (tempTicket.getTicketId().equals(ticketId)) {
                tempTicket.setState(state);
                tempTicket.setPrice(price);
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 删除票
     *
     * @param ticketId 待删除票的ID
     * @return 返回是否删除成功
     */
    public synchronized boolean deleteTicket(Integer ticketId) {
        boolean flag = false;
        // 迭代器方式遍历
        Iterator<Ticket> iterator = this.ticketList.iterator();
        while (iterator.hasNext()) {
            Ticket tempTicket = iterator.next();
            if (tempTicket.getTicketId().equals(ticketId)) {
                // 判断是否票已被选,若未选则直接删除
                if (tempTicket.getUserId()==null&&tempTicket.getState() == 0) {
                    iterator.remove();
                    flag = true;
                }
                break;
            }
        }
        System.out.println("删除票: " + ticketId);
        return flag;
    }

    /**
     * 选票
     *
     * @param ticketId 待选票的ID
     * @param userId   选票人的ID
     * @return 返回是否选票成功
     */
    public synchronized boolean chooseTicket(Integer ticketId, Integer userId) {
        System.out.println("选票:" + ticketId + "-" + userId);
        boolean flag = false;
        for (Ticket ticket : this.ticketList) {
            if (ticket.getUserId()==null&&ticket.getTicketId().equals(ticketId)) {
                ticket.setUserId(userId);
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 查看选票的历史选座
     *
     * @param userId 用户ID
     * @return 返回查询结果
     */
    public synchronized List<Ticket> selectHistory(Integer userId) {
        System.out.println("查询历史记录:" + userId);
        List<Ticket> list = new ArrayList<>();
        for (Ticket ticket : this.ticketList) {
            if (userId.equals(ticket.getUserId())) {
                list.add(ticket);
            }
        }
        return list;
    }

    /**
     * 取消选票
     *
     * @param ticketId 待取消选中的票ID
     * @param userId   取消选票的用户
     * @return 返回是否取消成功
     */
    public synchronized boolean cancelChooseTicket(Integer ticketId, Integer userId) {
        boolean flag = false;
        System.out.println("取消选票:" + userId + "-" + ticketId);
        for (Ticket ticket : this.ticketList) {
            if (ticket.getTicketId().equals(ticketId) && ticket.getUserId().equals(userId)) {
                ticket.setUserId(null);
                ticket.setState(Ticket.VACANT);// 设置选票的状态
                flag = true;
                break;
            }
        }
        return flag;
    }
}
