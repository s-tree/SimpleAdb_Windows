package com.jingxi.officetest.helper;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import com.jingxi.officetest.network.NetworkManager;
import com.jingxi.officetest.network.ScriptBean;
import com.jingxi.officetest.ui.UIConstants;
import com.jingxi.officetest.util.ExecUtil;
import com.jingxi.officetest.util.JFrameManager;

public class ScriptHelper implements WindowListener {
	JFrame jFrame;
    JList jList;
    JScrollPane scrollPane;
    String deviceName;
    String scriptUrl;
	
    public static final String NAME_NAME = "name";
	public static final String NAME_SCRIPT = "script";
	private String scriptName;
    String[] scripts,showNames;
    List<String> scriptList;
    JButton[] jButtons;
    JButton start;
    JTextField[] textFields;
    String title;

	public ScriptHelper(String scriptUrl,String title){
		this.title = title;
		initStyle();
		newFrame();
		this.scriptUrl = scriptUrl;
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
        jFrame.setTitle(title);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        jFrame.addWindowListener(this);

        jList = new JList();
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        jList.setCellRenderer(renderer);
        scrollPane = new JScrollPane(jList);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(UIConstants.tv_rssi_message_x, UIConstants.tv_rssi_message_y, UIConstants.tv_rssi_message_width,UIConstants.tv_rssi_message_height);

        jFrame.add(scrollPane);
    }

    public void show(String deviceName){
        this.deviceName = deviceName;
//        jFrame.setVisible(true);
        JFrameManager.showJFrame(jFrame);
        queryScrept();
    }

    private void queryScrept(){
    	String data = NetworkManager.getUrl(scriptUrl);
    	String[] temp = data.split("\n");
    	if(temp == null || temp.length == 0){
    		return;
    	}
    	for(int i = 0 ; i < temp.length; i++){
    		temp[i] = temp[i].trim();
    	}
    	scriptName = temp[0];
    	scripts = new String[temp.length - 1];
    	for(int i = 1 ; i < temp.length; i++){
    		scripts[i - 1 ] = temp[i];
    	}
    	showNames = new String[scripts.length];
        if(scriptName.equals(ScriptBean.NAME_NAME)){
        	for(int i = 0 ; i < scripts.length; i++){
        		showNames[i] = scripts[i].split("\\s+")[0];
        	}
        }else if(scriptName.equals(ScriptBean.NAME_SCRIPT)){
        	for(int i = 0 ; i < scripts.length; i++){
        		showNames[i] = scripts[i];
        	}
        }
        jList.removeAll();
        jList.setLayout(new GridLayout(2*showNames.length+1,1));
        
    	if(scriptName.equals(ScriptBean.NAME_NAME)){
            jButtons = new JButton[showNames.length];
    		for(int i = 0 ; i < showNames.length; i++){
    			jButtons[i] = createButtonItem(showNames[i]);
    			jButtons[i].addActionListener(new MenuAction(deviceName,scripts[i],showNames[i]));
    			jList.add(jButtons[i]);
    		}
    	}
    	else if(scriptName.equals(ScriptBean.NAME_SCRIPT)){
    		textFields = new JTextField[showNames.length];
    		for(int i = 0 ; i < showNames.length; i++){
    			textFields[i] = createItem(showNames[i]);
    			jList.add(textFields[i]);
    		}
    		scriptList = Arrays.asList(scripts);
    		start = new JButton("¿ªÊ¼Ö´ÐÐ");
    		start.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					start.setEnabled(false);
					startDid();
				}
			});
    		start.setBounds(UIConstants.tv_start_x, UIConstants.tv_start_y,
    				UIConstants.tv_start_width,UIConstants.tv_start_height);
            jFrame.add(start);
    	}
    }
    
    Subscription subscription;
    private void startDid(){
    	if(subscription != null && !subscription.isUnsubscribed()){
    		return;
    	}
    	subscription = Observable.from(scripts)
		.subscribeOn(Schedulers.newThread())
		.observeOn(Schedulers.newThread())
		.subscribe(new Action1<String>() {

			@Override
			public void call(String arg0) {
				// TODO Auto-generated method stub
				boolean result = ExecUtil.exec(deviceName, arg0);
				int index = scriptList.indexOf(arg0);
				textFields[index].setBackground(result ? Color.GREEN : Color.RED);
				if(index == scripts.length - 1){
					start.setEnabled(true);
				}
			}
		});
    }
    
    private JButton createButtonItem(String text){
		JButton jTextField = new JButton();
		jTextField.setText(text);
		jTextField.setHorizontalAlignment(JTextField.CENTER);
		return jTextField;
    }
    
    private JTextField createItem(String text){
    	String result = text;
    	if(text.length() > 60){
    		result = text.substring(0,30) + "..." + text.substring(text.length() - 30);
    	}
		JTextField jTextField = new JTextField();
		jTextField.setText(result);
		jTextField.setEditable(false);
		jTextField.setEnabled(true);
		jTextField.setHorizontalAlignment(JTextField.CENTER);
		return jTextField;
    }
    
    public static class MenuAction implements ActionListener{
    	String deviceName,script,showName;
    	
    	public MenuAction(String deviceName,String script,String showName){
    		this.deviceName = deviceName;
    		this.script = script;
    		this.showName = showName;
    	}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
        	String url = script.split("\\s+")[1];
        	new ScriptHelper(url,showName).show(deviceName);
		}
    	
    }
    
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		if(subscription != null && !subscription.isUnsubscribed()){
			try{
				subscription.unsubscribe();
			}catch(Exception ee){
				ee.printStackTrace();
			}
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
	}
}
