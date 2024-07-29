package com.jingxi.officetest.ui;

import com.jingxi.officetest.util.RuntimeUtil;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.TimeUnit;

public class RSSIPanel implements WindowListener {
    JFrame jFrame;
    JTextArea textArea;
    JScrollPane scrollPane;
    String deviceName;

    public RSSIPanel createPanel(){
        initStyle();
        newFrame();
        return this;
    }

    public void initStyle(){
        try{
            UIManager.setLookAndFeel(UIConstants.lookAndFeelLocal);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void newFrame(){
        jFrame = new JFrame();
        jFrame.setLayout(null);
        jFrame.setBounds(0, 0, UIConstants.panelWidth, UIConstants.panelHeight);
        jFrame.setLocation(500, 200);
        jFrame.setTitle("RSSI");
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        jFrame.addWindowListener(this);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.white);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(UIConstants.tv_rssi_message_x, UIConstants.tv_rssi_message_y, UIConstants.tv_rssi_message_width,UIConstants.tv_rssi_message_height);

        jFrame.add(scrollPane);
    }

    public void show(String deviceName){
        this.deviceName = deviceName;
        jFrame.setVisible(true);
        startCheck();
    }

    public void setMessage(String message){
        textArea.setText(message);
    }

    Subscription subscription;
    private void startCheck(){
        subscription = Observable.interval(1,TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .subscribe(new Action1() {
                    public void call(Object o) {
                        try {
                            readRSSI();
                        } catch (Exception e) {
                        }
                    }
                });
    }

    private void readRSSI(){
        String[] result = RuntimeUtil.exec("adb -s " + deviceName + " shell cat /sdcard/btRSSI");
        if(result[0] == null){
            setMessage(result[1]);
        }
        else{
            setMessage(result[1]);
        }
    }

    public void windowOpened(WindowEvent e) {

    }

    public void windowClosing(WindowEvent e) {
        if(subscription != null && !subscription.isUnsubscribed()){
            try {
                subscription.unsubscribe();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void windowClosed(WindowEvent e) {

    }

    public void windowIconified(WindowEvent e) {

    }

    public void windowDeiconified(WindowEvent e) {

    }

    public void windowActivated(WindowEvent e) {

    }

    public void windowDeactivated(WindowEvent e) {

    }
}
