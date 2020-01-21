package GUI;


import javax.swing.*;

import Data.CallService;
import Data.FileWrite;
import Data.Person;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Vector;

public class GUIBuild_1 extends JFrame{
	Container c;
	JPanel jp2=new JPanel();
	JPanel waitPanel=new JPanel();
	JButton btn[] = new JButton[35];
	JButton ServicePerson;
	JButton callIcon;
	private final JPanel panel_1 = new JPanel();
	private final JPanel panel_2 = new JPanel();
	private final JPanel panel_3 = new JPanel();
	private JLabel dayLabel;
	private JLabel timeLabel;
	private JLabel dLabel;
	public String state="영업을 시작합니다!";
	public String today="Day-1";
	public String Time="Time-0";
	public String msg=" ";
	private final JLabel service = new JLabel(state);
	
	public GUIBuild_1(int day){
		today="Day-"+Integer.toString(day)+" / ";
		
		setTitle("태피상점 시뮬레이션");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c=getContentPane();
		jp2.setBounds(0, 0, 931, 456);
		

		jp2.setBackground(Color.PINK);
		waitPanel.setLayout(new FlowLayout(5,5,FlowLayout.LEFT));
		waitPanel.setLocation(189, 103);
		
		waitPanel.setSize(551,250);
		waitPanel.setBackground(SystemColor.controlHighlight);
		waitPanel.setAlignmentX(FlowLayout.LEFT);
		
		ServicePerson=new JButton(new ImageIcon("icon.png"));
		ServicePerson.setSize(30, 40);
		ServicePerson.setLocation(442, 360);
		ServicePerson.setBorderPainted(false); //버튼 테두리 설정
		ServicePerson.setContentAreaFilled(false); //버튼 영역 배경 표시 설정
		ServicePerson.setVisible(false);
		

		panel_3.setBounds(189, 55, 551, 31);
		jp2.add(panel_3);
		service.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		
		panel_3.add(service);
		
		jp2.add(ServicePerson);
		
		ImageIcon per=new ImageIcon("icon.png");
		for(int i=0;i<35;i++) {
			btn[i]=new JButton(per);
			btn[i].setBorderPainted(false); //버튼 테두리 설정
			btn[i].setContentAreaFilled(false); //버튼 영역 배경 표시 설정
			btn[i].setVisible(false);
			waitPanel.add(btn[i]);
		}
		jp2.setLayout(null);
		jp2.add(waitPanel);
		getContentPane().setLayout(null);
		c.add(jp2);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(188, 102, 553, 252);
		jp2.add(panel);
		panel_1.setBackground(new Color(255, 153, 153));
		panel_1.setToolTipText("");
		panel_1.setBounds(335, 399, 242, 57);
		
		jp2.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("\uC11C\uBE44\uC2A4\uCC3D\uAD6C");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		label.setForeground(new Color(255, 255, 255));
		label.setBounds(63, 8, 120, 40);
		panel_1.add(label);
		
		callIcon=new JButton(new ImageIcon("call.png"));
		callIcon.setBounds(12, 8, 39, 40);
		panel_1.add(callIcon);
		callIcon.setBorderPainted(false); //버튼 테두리 설정
		callIcon.setContentAreaFilled(false); //버튼 영역 배경 표시 설정
		callIcon.setVisible(false);
		panel_2.setBounds(333, 397, 246, 56);
		
		jp2.add(panel_2);
		
		dayLabel = new JLabel(today);
		dayLabel.setForeground(Color.WHITE);
		dayLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		dayLabel.setBounds(568, 18, 86, 38);
		jp2.add(dayLabel);
		
		timeLabel = new JLabel(Time);
		timeLabel.setForeground(Color.WHITE);
		timeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		timeLabel.setBounds(649, 18, 95, 38);
		jp2.add(timeLabel);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.GRAY);
		panel_4.setBounds(187, 54, 555, 34);
		jp2.add(panel_4);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(14, 0, 18, 456);
		jp2.add(panel_5);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(39, 1, 10, 456);
		jp2.add(panel_6);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBounds(899, 0, 18, 456);
		jp2.add(panel_7);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBounds(881, 0, 10, 456);
		jp2.add(panel_8);
		
		dLabel = new JLabel(msg);
		dLabel.setForeground(new Color(255, 102, 102));
		dLabel.setFont(new Font("굴림", Font.BOLD, 11));
		dLabel.setBounds(189, 31, 366, 25);
		jp2.add(dLabel);

		setBounds(500,150,949,503);
		setVisible(true);
		setSize(949,503);
	}
	
	class MenuActionListener implements ActionListener { 
		synchronized public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand(); 
			if(cmd=="EXIT") {
				System.exit(0);
			}
			else if(cmd=="START") {
				c.notify();
			}
			else if(cmd=="STOP") {
		
				}
			}
		}
	

	public synchronized void currentTime(int i) {
		Time="Time-"+Integer.toString(i);
		timeLabel.setText(Time);
	}
	public synchronized void DeadLineMsg() {
		msg="* 마감시간입니다. 더 이상 손님을 받지 않습니다 *";
		dLabel.setText(msg);
	}
	public synchronized void ServiceState(int i) {
		if(i==0) {
			state="손님이 없어 대기중입니다.";
			service.setText(state);
		}
		else if(i==1) {
			state="서비스를 시작합니다.";
			service.setText(state);
		}
		else if(i==2) {
			state="서비스 중입니다....";
			service.setText(state);
		}
		else if(i==3) {
			state="서비스가 종료되었습니다. 손님이 퇴장하셨습니다.";
			service.setText(state);
		}
		else if(i==4) {
			state="손님이 입장하셨습니다.";
			service.setText(state);
		}
		else if(i==5) {
			state="전화가 왔습니다. 전화서비스를 시작합니다.";
			service.setText(state);
		}
		else if(i==6) {
			state="전화서비스를 종료합니다.";
			service.setText(state);
		}
	}
	
	public synchronized void makeButton(int i) {
		btn[i].setVisible(true);
	}
	
	public synchronized void deleteButton(int i) {
		btn[i].setVisible(false);
	}
	public synchronized void ServiceStart() {
		ServicePerson.setVisible(true);
	}
	public synchronized void ServiceEnd() {
		ServicePerson.setVisible(false);
	}
	public synchronized void callServiceStart() {
		callIcon.setVisible(true);
	}
	public synchronized void callServiceEnd() {
		callIcon.setVisible(false);
	}
	public synchronized void storeEnd(int day, int totalPerson, int totalServiceTime, int averageService, int averageWait, int callCount, Vector<Person> personInfo, Vector<CallService> callInfo) {
	
		try {
			FileWrite fw=new FileWrite(personInfo);
			state="영업을 종료합니다.";
			service.setText(state);
			Thread.sleep(1000);
		} 
		catch (InterruptedException e) {}
		setVisible(false);
		new Statistics(1,day,totalPerson, totalServiceTime, averageService, averageWait, callCount, personInfo, callInfo);
	}
}
