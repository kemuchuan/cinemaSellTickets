package com.cinema.ticket.client;

import com.cinema.ticket.client.pages.LoginPage;
import com.cinema.ticket.client.pages.TicketPage;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

// 客户端启动类（client的都是）
public class ClientMain {

    public static void main(String[] args) {
        // 加载窗口美化包
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Failed to initialize LaF");
        }
        // 开启登录页面
        new LoginPage().setVisible(true);
    }

}
