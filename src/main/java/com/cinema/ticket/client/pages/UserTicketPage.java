/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.cinema.ticket.client.pages;

import com.cinema.ticket.client.api.TicketApi;
import com.cinema.ticket.client.domain.Ticket;
import com.cinema.ticket.client.domain.User;

import javax.swing.*;

/**
 * 用户购买票的窗口类
 * @author 22805
 */
public class UserTicketPage extends javax.swing.JFrame {

    // 票数据对象
    private Ticket ticket;

    // 当前登录用户
    private User user;

    // 保证只有一个对象
    private static final UserTicketPage userTicket = new UserTicketPage();

    public static UserTicketPage getUserTicket(Ticket ticket, User user, int x, int y) {
        userTicket.setVisible(false);
        userTicket.ticket = ticket;
        userTicket.user = user;
        userTicket.setLocation(x, y);
        // 设置票信息
        userTicket.ticketLabel.setText("该座位坐标位: 第 " + (ticket.getLocX() + 1) + " 排, 第 " + (ticket.getLocY() + 1) + " 列 " + "价格为：" + ticket.getPrice());
        // 判断票是否被选
        if (ticket.getUserId() == null) {
            // 未
            userTicket.msgLabel.setText("该票现在空闲中，您要买这张票嘛？");
        } else {
            // 已
            // 判断是否是当前登录用户选的票
            if (user.getUserId().equals(ticket.getUserId())) {
                // 是
                // 根据票的状态提示信息
                if (ticket.getState().equals(Ticket.SELECTED)) {
                    userTicket.msgLabel.setText("该票已被选中，请及时付费（点击确认付费）");
                } else if (ticket.getState().equals(Ticket.PENDING_PAYMENT)) {
                    userTicket.msgLabel.setText("该票已付费正在出票");
                } else {
                    userTicket.msgLabel.setText("恭喜您，出票成功");
                }
            } else {
                // 否
                userTicket.msgLabel.setText("该票已被其他人选中，无法购买");
            }
        }
        return userTicket;
    }

    /**
     * Creates new form UserTicket
     */
    private UserTicketPage() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ticketLabel = new javax.swing.JLabel();
        msgLabel = new javax.swing.JLabel();
        confirmBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        msgLabel.setText("亲爱地用户您确认选这张座位嘛？");

        confirmBtn.setText("确认");
        confirmBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmBtnActionPerformed(evt);
            }
        });

        cancelBtn.setText("取消");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(55, 55, 55)
                                                .addComponent(confirmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(42, 42, 42)
                                                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(19, 19, 19)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(msgLabel)
                                                        .addComponent(ticketLabel))))
                                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(ticketLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(msgLabel)
                                .addContainerGap(68, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(confirmBtn)
                                        .addComponent(cancelBtn))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void confirmBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmBtnActionPerformed
        // TODO add your handling code here:
        // 判断票是否是当前用户所选
        if (ticket.getUserId() == null||ticket.getUserId().equals(this.user.getUserId())) {
            // 是
            if (ticket.getState().equals(Ticket.VACANT)) {
                // 更新状态
                ticket.setState(Ticket.SELECTED);
                // 提示信息
                this.msgLabel.setText("该票已被选中，请及时付费（点击确认付费）");
                // 请求人选票（将票与人绑定）
                boolean flag = this.ticketApi.chooseTicket(this.user.getUserId(), ticket.getTicketId());
                // 判断是否成功
                if(!flag){
                    // 不成功则提示信息，返回结果
                    this.msgLabel.setText("该票已被其他人所选，请购买其他票");
                    // 设置虚拟id，为了下次点击事件走最下面的else语句
                    this.ticket.setUserId(1);
                    return;
                }

            } else if (ticket.getState().equals(Ticket.SELECTED)) {
                // 更新状态
                ticket.setState(Ticket.PENDING_PAYMENT);
                this.msgLabel.setText("该票已付费正在出票");
            } else if (ticket.getState().equals(Ticket.PENDING_PAYMENT)) {
                // 更新状态
                ticket.setState(Ticket.ACCOUNT_PAID);
                this.msgLabel.setText("恭喜您，出票成功");
            } else {
                // 关闭当前页面
                this.setVisible(false);
                return;
            }
            // 更新票的状态
            this.ticketApi.updateTicket(ticket);
        }else{
            this.setVisible(false);
        }
    }//GEN-LAST:event_confirmBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        // TODO add your handling code here:
        // 取消按钮点击事件
        this.setVisible(false);

    }//GEN-LAST:event_cancelBtnActionPerformed

    private final TicketApi ticketApi = new TicketApi();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton confirmBtn;
    private javax.swing.JLabel msgLabel;
    private javax.swing.JLabel ticketLabel;
    // End of variables declaration//GEN-END:variables
}
