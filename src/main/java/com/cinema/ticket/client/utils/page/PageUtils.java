package com.cinema.ticket.client.utils.page;

import javax.swing.*;
import java.awt.*;

public final class PageUtils {

    private PageUtils(){}


    /**
     * 设置窗口居中的方法
     * @param jFrame 待居中的窗口对象
     */
    public static void setJFrameCenterAndNotResizeable(JFrame jFrame){
        // 设置窗口屏幕居中
        int width = jFrame.getWidth();
        int height = jFrame.getHeight();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        jFrame.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        // 设置窗口不可伸缩
        jFrame.setResizable(false);
    }

}
