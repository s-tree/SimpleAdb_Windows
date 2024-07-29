package com.jingxi.officetest.util;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JFrame;

public class JFrameManager {

	public static HashMap<Long, JFrame> jframeMap = new HashMap<>();
	public static LinkedList<Long> jFrameIds = new LinkedList<>();
	
	public static synchronized void showJFrame(JFrame jFrame){
		long id = System.currentTimeMillis();
		if(!jFrameIds.isEmpty()){
			jframeMap.get(jFrameIds.getLast()).setEnabled(false);
		}
		jframeMap.put(id, jFrame);
		jFrameIds.add(id);
		jFrame.addWindowListener(listener);
		jFrame.setVisible(true);
	}
	
	private static WindowListener listener = new WindowListener() {
		
		@Override
		public void windowOpened(WindowEvent e) {
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
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub

			long id = jFrameIds.getLast();
			JFrame frame = jframeMap.get(id);
			frame.removeWindowListener(listener);
			jframeMap.remove(id);
			jFrameIds.remove(id);
			if(!jFrameIds.isEmpty()){
				jframeMap.get(jFrameIds.getLast()).setEnabled(true);
			}
		}
		
		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
}
