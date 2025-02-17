﻿package com.jingxi.officetest.ui;

import javax.swing.*;

public class UIConstants {
    /**
     * Metal风格 (默认)
     */
    public static String lookAndFeelMetal = "javax.swing.plaf.metal.MetalLookAndFeel";

    /**
     * Windows风格
     */
    public static String lookAndFeelWindows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

    /**
     * Windows Classic风格
     */
    public static String lookAndFeelClassic = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";

    /**
     * Motif风格
     */
    public static String lookAndFeelMotif = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";

    /**
     * Mac风格 (需要在相关的操作系统上方可实现)
     */
    public static String lookAndFeelMac = "com.sun.java.swing.plaf.mac.MacLookAndFeel";

    /**
     * GTK风格 (需要在相关的操作系统上方可实现)
     */
    public static String lookAndFeelGTK = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";

    /**
     * 可跨平台的默认风格
     */
    public static String lookAndFeelAll = UIManager.getCrossPlatformLookAndFeelClassName();

    /**
     * 当前系统的风格
     */
    public static String lookAndFeelLocal = UIManager.getSystemLookAndFeelClassName();


    public static final int panelWidth = 600;
    public static final int panelHeight = 900;

    public static final int padding = 20;
    public static final int modelPadding = 40;
    public static final int itemHeight = 40;

    /**
     * 选择设备标题
     */
    public static final int tv_device_title_x = padding;
    public static final int tv_device_title_y = padding;
    public static final int tv_device_title_width = 100;
    public static final int tv_device_title_height = itemHeight * 2;

    /**
     * 选择设备的列表
     */
    public static final int list_device_x = tv_device_title_x + tv_device_title_width + padding;
    public static final int list_device_y = padding;
    public static final int list_device_width = 340 - padding - tv_device_title_width;
    public static final int list_device_height = itemHeight * 2;

    /**
     * 选择的设备
     */
    public static final int tv_device_x = list_device_x + list_device_width + padding;
    public static final int tv_device_y = padding;
    public static final int tv_device_width = panelWidth - tv_device_x - padding;
    public static final int tv_device_height = itemHeight * 2;

    /**
     * 连接设备标题
     */
    public static final int tv_connect_device_title_x = padding;
    public static final int tv_connect_device_title_y = tv_device_title_y + tv_device_title_height + modelPadding;
    public static final int tv_connect_device_title_width = 100;
    public static final int tv_connect_device_title_height = itemHeight;

    /**
     * 连接设备的按钮
     */
    public static final int connect_device_x = tv_connect_device_title_x + tv_connect_device_title_width + padding;
    public static final int connect_device_y = tv_connect_device_title_y;
    public static final int connect_device_width = 340 - padding - tv_connect_device_title_width;
    public static final int connect_device_height = itemHeight;

    /**
     * 断开连接的按钮
     */
    public static final int tv_disconnect_device_x = connect_device_x + connect_device_width + padding;
    public static final int tv_disconnect_device_y = tv_connect_device_title_y;
    public static final int tv_disconnect_device_width = panelWidth - tv_disconnect_device_x - padding;
    public static final int tv_disconnect_device_height = itemHeight;

    /**
     * 选择要安装的apk路径
     */
    public static final int text_apk_x = padding;
    public static final int text_apk_y = tv_connect_device_title_y + tv_connect_device_title_height + modelPadding;
    public static final int text_apk_width = 340;
    public static final int text_apk_height = itemHeight;

    /**
     * 选择apk的按钮
     */
    public static final int bt_apk_x = text_apk_x + padding + text_apk_width;
    public static final int bt_apk_y = text_apk_y;
    public static final int bt_apk_width = panelWidth - padding - bt_apk_x;
    public static final int bt_apk_height = text_apk_height;

    /**
     * 开始安装apk的按钮
     */
    public static final int bt_apk_install_x = padding;
    public static final int bt_apk_install_y = text_apk_y + text_apk_height + padding;
    public static final int bt_apk_install_width = panelWidth - padding - text_apk_x;
    public static final int bt_apk_install_height = text_apk_height;

    /**
     * 选择要升级的ota路径
     */
    public static final int text_ota_x = padding;
    public static final int text_ota_y = bt_apk_install_y + bt_apk_install_height + modelPadding;
    public static final int text_ota_width = text_apk_width;
    public static final int text_ota_height = text_apk_height;

    /**
     * 选择ota的按钮
     */
    public static final int bt_ota_x = text_apk_x + padding + text_apk_width;
    public static final int bt_ota_y = text_ota_y;
    public static final int bt_ota_width = panelWidth - padding - bt_ota_x;
    public static final int bt_ota_height = text_apk_height;

    /**
     * 开始安装ota的按钮
     */
    public static final int bt_ota_install_x = padding;
    public static final int bt_ota_install_y = text_ota_y + text_ota_height + padding;
    public static final int bt_ota_install_width = panelWidth - padding - text_apk_x;
    public static final int bt_ota_install_height = text_apk_height;

    /**
     * 选择要升级的固件路径
     */
    public static final int text_img_x = padding;
    public static final int text_img_y = bt_ota_install_y + bt_apk_install_height + modelPadding;
    public static final int text_img_width = text_apk_width;
    public static final int text_img_height = text_apk_height;

    /**
     * 选择固件的按钮
     */
    public static final int bt_img_x = text_apk_x + padding + text_apk_width;
    public static final int bt_img_y = text_img_y;
    public static final int bt_img_width = panelWidth - padding - bt_img_x;
    public static final int bt_img_height = text_apk_height;

    /**
     * 开始安装固件的按钮
     */
    public static final int bt_img_install_x = padding;
    public static final int bt_img_install_y = text_img_y + text_ota_height + padding;
    public static final int bt_img_install_width = panelWidth - padding - text_apk_x;
    public static final int bt_img_install_height = text_apk_height;
    /**
     * 前往rssi测试按钮
     */
    public static final int bt_rssi_x = padding;
    public static final int bt_rssi_y = bt_img_install_y + bt_img_install_height + modelPadding;
    public static final int bt_rssi_width = panelWidth - padding - bt_rssi_x;
    public static final int bt_rssi_height = text_apk_height;

    /**
     * 文本
     */
    public static final int tv_message_x = padding;
    public static final int tv_message_y = bt_rssi_y + bt_rssi_height + modelPadding;
    public static final int tv_message_width = panelWidth - padding - tv_message_x;
    public static final int tv_message_height = panelHeight - tv_message_y - padding - padding;

    /**
     * RSSI文本页面
     */
    public static final int tv_rssi_message_x = padding;
    public static final int tv_rssi_message_y = padding;
    public static final int tv_rssi_message_width = panelWidth - padding - padding;
    public static final int tv_rssi_message_height = panelHeight - padding - itemHeight * 2 - padding - padding;


    /**
     * 开始按钮
     */
    public static final int tv_start_x = padding;
    public static final int tv_start_y = tv_rssi_message_y + tv_rssi_message_height + padding;
    public static final int tv_start_width = panelWidth - padding - padding;
    public static final int tv_start_height = itemHeight;
}
