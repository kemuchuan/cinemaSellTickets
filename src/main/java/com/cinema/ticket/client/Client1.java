package com.cinema.ticket.client;

import com.cinema.ticket.client.pages.LoginPage;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

// 客户端1
public class Client1 {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Failed to initialize LaF");
        }
        new LoginPage().setVisible(true);
    }
}
