package com.jingxi.officetest.ui;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import com.jingxi.officetest.helper.ScriptHelper;
import com.jingxi.officetest.network.NetworkConstant;
import com.jingxi.officetest.observers.MessageEvent;
import com.jingxi.officetest.util.OTAUpdate;
import com.jingxi.officetest.util.PropertiesUtil;
import com.jingxi.officetest.util.RuntimeUtil;

public class TestUI implements MessageEvent.OnMessageAppend {
    JFrame jFrame;
    JTextField apkPathField,otaPathField,imgPathField,deviceName,connectDeviceTitle;
    JButton chooseApkBt,chooseOTABt,chooseImageBt,startInstall,startOta,startImg,rssiBt,connectBt;
    JTextArea textArea;
    JScrollPane scrollPane,listScrollPane;
    JList<String> jList;
    String[] allDevices = new String[]{""};
    String selectDevice;

    String apkFilePath = "";
    String otaFilePath = "";
    String imageFilePath = "";

    RSSIPanel rssiPanel;

    public TestUI() {
        MessageEvent.setOnMessageAppend(this);
    }

    public TestUI createPanel(){
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
    
    Subscription connectSub;

    public void newFrame(){
        jFrame = new JFrame();
        jFrame.setLayout(null);
        jFrame.setBounds(0, 0, UIConstants.panelWidth, UIConstants.panelHeight);
        jFrame.setLocation(500, 50);
        jFrame.setTitle("京希平板 维护工具");
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField deviceTitle = new JTextField();
        deviceTitle.setHorizontalAlignment(JTextField.CENTER);
        deviceTitle.setText("选择1个设备");
        deviceTitle.setEditable(false);
        deviceTitle.setBounds(UIConstants.tv_device_title_x, UIConstants.tv_device_title_y, UIConstants.tv_device_title_width, UIConstants.tv_device_title_height);

        jList = new JList<String>(allDevices);
        jList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    return;
                }
                if(allDevices == null){
                    return;
                }
                String select = allDevices[e.getFirstIndex()];
                if(select == null || select.isEmpty()){
                    return;
                }
                if(select.equals(selectDevice)){
                    return;
                }
                selectDevice = select;
                appendMessage("选择了设备 " + selectDevice);
                deviceName.setText(selectDevice);
            }
        });
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        jList.setCellRenderer(renderer);
        listScrollPane = new JScrollPane(jList);
        listScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        listScrollPane.setBackground(Color.RED);
        listScrollPane.setBounds(UIConstants.list_device_x, UIConstants.list_device_y, UIConstants.list_device_width, UIConstants.list_device_height);

        deviceName = new JTextField();
        deviceName.setEditable(false);
        deviceName.setText("暂未选择设备");
        deviceName.setHorizontalAlignment(JTextField.CENTER);
        deviceName.setBounds(UIConstants.tv_device_x, UIConstants.tv_device_y, UIConstants.tv_device_width, UIConstants.tv_device_height);

        connectDeviceTitle = new JTextField();
        connectDeviceTitle.setHorizontalAlignment(JTextField.CENTER);
        connectDeviceTitle.setEditable(true);
        connectDeviceTitle.setBounds(UIConstants.tv_connect_device_title_x, UIConstants.tv_connect_device_title_y, 
        		UIConstants.tv_connect_device_title_width, UIConstants.tv_connect_device_title_height);
        
        connectBt = new JButton("连接设备");
        connectBt.setBounds(UIConstants.connect_device_x, UIConstants.connect_device_y, UIConstants.connect_device_width, UIConstants.connect_device_height);
        connectBt.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	String data = connectDeviceTitle.getText();
            	if(data == null || data.isEmpty()){
                	appendMessage("请输入要连接的设备ip");
            		return;
            	}
            	appendMessage("开始连接 " + data);
            	if(connectSub != null && !connectSub.isUnsubscribed()){
            		try{
            			connectSub.unsubscribe();
            		}catch(Exception e1){
            			e1.printStackTrace();
            		}
            	}
            	Observable.just(data)
            	.observeOn(Schedulers.newThread())
            	.subscribe(new Action1<String>() {

					@Override
					public void call(String arg0) {
						// TODO Auto-generated method stub
		            	String[] result = RuntimeUtil.exec("adb connect " + arg0,true);
	            		appendMessage("连接已执行");
					}
				});
            }
        });

        JButton disconnectBt = new JButton("断开所有wifi连接");
        disconnectBt.setBounds(UIConstants.tv_disconnect_device_x, UIConstants.tv_disconnect_device_y, 
        		UIConstants.tv_disconnect_device_width, UIConstants.tv_disconnect_device_height);
        disconnectBt.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	appendMessage("正在断开所有的wifi连接");
            	RuntimeUtil.exec("adb disconnect ",true);
            }
        });
        
        apkPathField = new JTextField();
        apkPathField.setText("");
        apkPathField.setEditable(false);
        apkPathField.setBounds(UIConstants.text_apk_x, UIConstants.text_apk_y, UIConstants.text_apk_width, UIConstants.text_apk_height);
        chooseApkBt = new JButton("选择要安装的apk文件");
        chooseApkBt.setBounds(UIConstants.bt_apk_x, UIConstants.bt_apk_y, UIConstants.bt_apk_width, UIConstants.bt_apk_height);
        chooseApkBt.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String oldDir = PropertiesUtil.read(PropertiesUtil.CURRENT_APK_PATH);
                File file = showChooseFile("APK文件",oldDir,"apk");
                if(file == null){
                	return;
                }
                String path = file.getAbsolutePath();
                apkPathField.setText(path);
                apkFilePath = path;
                String parent = file.getParentFile().getAbsolutePath();
                if(!parent.equals(oldDir)){
                    PropertiesUtil.update(PropertiesUtil.CURRENT_APK_PATH,parent);
                }
            }
        });
        startInstall = new JButton("安装apk");
        startInstall.setBounds(UIConstants.bt_apk_install_x, UIConstants.bt_apk_install_y, UIConstants.bt_apk_install_width, UIConstants.bt_apk_install_height);
        startInstall.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(!checkDeviceSelect()){
                    return;
                }
                if(apkFilePath == null || apkFilePath.isEmpty()){
                    appendMessage("请选择要安装的apk文件");
                    return;
                }
                if(!apkFilePath.endsWith(".apk")){
                    appendMessage("请选择正确的apk?");
                    return;
                }
                startInstall.setEnabled(false);
                startInstallApk();
            }
        });


        otaPathField = new JTextField();
        otaPathField.setText("");
        otaPathField.setEditable(false);
        otaPathField.setBounds(UIConstants.text_ota_x, UIConstants.text_ota_y, UIConstants.text_ota_width, UIConstants.text_ota_height);
        chooseOTABt = new JButton("选择要更新的ota文件");
        chooseOTABt.setBounds(UIConstants.bt_ota_x, UIConstants.bt_ota_y, UIConstants.bt_ota_width, UIConstants.bt_ota_height);
        chooseOTABt.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String oldDir = PropertiesUtil.read(PropertiesUtil.CURRENT_OTA_PATH);
                File file = showChooseFile("OTA文件",oldDir,"zip");
                String path = file.getAbsolutePath();
                otaPathField.setText(path);
                otaFilePath = path;
                String parent = file.getParentFile().getAbsolutePath();
                if(!parent.equals(oldDir)){
                    PropertiesUtil.update(PropertiesUtil.CURRENT_OTA_PATH,parent);
                }
            }
        });
        startOta = new JButton("ota 升级");
        startOta.setBounds(UIConstants.bt_ota_install_x, UIConstants.bt_ota_install_y, UIConstants.bt_ota_install_width, UIConstants.bt_ota_install_height);
        startOta.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(!checkDeviceSelect()){
                    return;
                }
                appendMessage("正在 " + selectDevice + "升级ota " + otaFilePath);
                startOta.setEnabled(false);
                Observable.just(otaFilePath)
                        .observeOn(Schedulers.io())
                        .subscribe(new Subscriber<String>() {
                            public void onCompleted() {
                                startOta.setEnabled(true);
                            }

                            public void onError(Throwable throwable) {
                                startOta.setEnabled(true);
                            }

                            public void onNext(String s) {
                                String result = OTAUpdate.update(selectDevice,otaFilePath);
                                appendMessage(result);
                            }
                        });
            }
        });
        
        buildOtaImage();

        rssiBt = new JButton("脚本功能");
        rssiBt.setBounds(UIConstants.bt_rssi_x, UIConstants.bt_rssi_y, UIConstants.bt_rssi_width, UIConstants.bt_rssi_height);
        rssiBt.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(!checkDeviceSelect()){
                    return;
                }
                new ScriptHelper(NetworkConstant.BASE_URL,"可用的脚本列表").show(selectDevice);
            }
        });

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.white);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(UIConstants.tv_message_x, UIConstants.tv_message_y, UIConstants.tv_message_width,UIConstants.tv_message_height);

        jFrame.add(deviceTitle);
        jFrame.add(listScrollPane);
        jFrame.add(deviceName);
        jFrame.add(connectDeviceTitle);
        jFrame.add(connectBt);
        jFrame.add(disconnectBt);
        jFrame.add(apkPathField);
        jFrame.add(chooseApkBt);
        jFrame.add(startInstall);
        jFrame.add(otaPathField);
        jFrame.add(chooseOTABt);
        jFrame.add(startOta);
        jFrame.add(imgPathField);
        jFrame.add(chooseImageBt);
        jFrame.add(startImg);
        jFrame.add(rssiBt);
        jFrame.add(scrollPane);
    }
    
    private void buildOtaImage(){
        imgPathField = new JTextField();
        imgPathField.setText("");
        imgPathField.setEditable(false);
        imgPathField.setBounds(UIConstants.text_img_x, UIConstants.text_img_y, UIConstants.text_img_width, UIConstants.text_img_height);
        chooseImageBt = new JButton("选择要更新的固件包");
        chooseImageBt.setBounds(UIConstants.bt_img_x, UIConstants.bt_img_y, UIConstants.bt_img_width, UIConstants.bt_img_height);
        chooseImageBt.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String oldDir = PropertiesUtil.read(PropertiesUtil.CURRENT_IMG_PATH);
                File file = showChooseFile("Img文件",oldDir,"img");
                String path = file.getAbsolutePath();
                imgPathField.setText(path);
                imageFilePath = path;
                String parent = file.getParentFile().getAbsolutePath();
                if(!parent.equals(oldDir)){
                    PropertiesUtil.update(PropertiesUtil.CURRENT_IMG_PATH,parent);
                }
            }
        });
        startImg = new JButton("固件 升级");
        startImg.setBounds(UIConstants.bt_img_install_x, UIConstants.bt_img_install_y, UIConstants.bt_img_install_width, UIConstants.bt_img_install_height);
        startImg.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(!checkDeviceSelect()){
                    return;
                }
                appendMessage("正在 " + selectDevice + "升级固件 " + imageFilePath);
                startImg.setEnabled(false);
                Observable.just(imageFilePath)
                        .observeOn(Schedulers.io())
                        .subscribe(new Subscriber<String>() {
                            public void onCompleted() {
                            	startImg.setEnabled(true);
                            }

                            public void onError(Throwable throwable) {
                            	startImg.setEnabled(true);
                            }

                            public void onNext(String s) {
                                String result = OTAUpdate.updateImage(selectDevice,imageFilePath);
                                appendMessage(result);
                            }
                        });
            }
        });
    }

    public void show(){
        jFrame.setVisible(true);
        queryAdbDevices();
    }

    public void queryAdbDevices(){
        Observable.interval(2,TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .subscribe(new Action1<Long>() {
                    public void call(Long aLong) {
                        List<String> result = new ArrayList<String>();
                        String[] message = RuntimeUtil.exec("adb devices");
                        if(message[0] != null){
                            return;
                        }
                        String data = message[1];
                        String[] devices = data.split("\n");
                        if(devices == null || devices.length == 0){
                            return;
                        }
                        for(String str : devices){
                            if(str.startsWith("List of devices attached")){
                                continue;
                            }
                            if(!str.contains("device")){
                                continue;
                            }
                            String[] items = str.split("\\s+");
                            if(items.length < 2){
                                continue;
                            }
                            result.add(items[0]);
                        }
                        if(result.isEmpty()){
                            allDevices = new String[]{""};
                        }else{
                            allDevices = result.toArray(new String[result.size()]);
                        }
                        if(selectDevice != null && !selectDevice.isEmpty() && !result.contains(selectDevice)){
                            selectDevice = "";
                            deviceName.setText("");
                        }
                        jList.setListData(allDevices);
                    }
                });
    }

    public File showChooseFile(String description,String currentDir,String filterStr){
        JFileChooser jfc = new JFileChooser(currentDir);
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
        jfc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(description,new String[]{filterStr});
        jfc.setFileFilter(filter);
        jfc.showDialog(new JLabel(), "选择文件");
        File file=jfc.getSelectedFile();
        return file;
    }

    private boolean checkDeviceSelect(){
        if(selectDevice == null || selectDevice.isEmpty()){
            appendMessage("请先选择一个设备");
            return false;
        }
        return true;
    }

    public void appendMessage(String message){
        if(textArea == null){
            return;
        }
        textArea.append(message);
        textArea.append("\n");
        scrollMessageToBottom();
    }

    public void scrollMessageToBottom(){
        int height=10;
        Point p = new Point();
        p.setLocation(0,this.textArea.getLineCount()*height);
        scrollPane.getViewport().setViewPosition(p);
    }

    private void startInstallApk(){
        Observable.just(apkFilePath)
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    public void onCompleted() {
                        startInstall.setEnabled(true);
                    }

                    public void onError(Throwable throwable) {
                        startInstall.setEnabled(true);
                    }

                    public void onNext(String s) {
                        appendMessage("正在 " + selectDevice + "安装 " + apkFilePath);
                        String[] datas = RuntimeUtil.execWithMessage("adb -s " + selectDevice + " install -r -d -t " + apkFilePath);
                        appendMessage(datas[1]);
                    }
                });
    }

    public void onMessage(String message) {
        appendMessage(message);
    }

    public void updateMessage(String oldMessage, String newMessage) {
        String text = textArea.getText();
        Document document = textArea.getDocument();
        int index = text.lastIndexOf(oldMessage);
        if(index != -1){
            try {
                document.remove(index,oldMessage.length());
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            textArea.insert(newMessage,index);
        }
    }
}
