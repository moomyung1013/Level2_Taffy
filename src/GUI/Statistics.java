package GUI;


import java.awt.*;
import javax.swing.*;

import Data.CallService;
import Data.CallTable;
import Data.Person;
import Data.PersonTable;
import Simulate.*;

import java.awt.event.*;
import java.util.Vector;


public class Statistics extends JFrame {
	private MyThread th;
	private MyThread2 th2;
	private MyThread3 th3;
	private Vector<Person> personInfo;
	private Vector<CallService> callInfo;
	private PersonTable mtablePerson;
	private CallTable mtableCall;
	private JPanel tablePerson;
	private JPanel tableCall;
	private int today=0;
	private int serviceNum=0;
	private String day="";
	private String person="";
	private String tst="";
	private String as="";
	private String aw="";
	private String cl="";
	Container c;
	
	public Statistics(int serviceNum, int thisday, int totalPerson, int totalServiceTime, int averageService, int averageWait, int callCount, Vector<Person>personInfo, Vector<CallService>callInfo) {
		this.personInfo=personInfo;
		this.callInfo=callInfo;
		this.serviceNum=serviceNum;
		mtablePerson=new PersonTable(this.personInfo);
		mtableCall=new CallTable(this.callInfo);
		today=thisday;
		day="Day - "+Integer.toString(thisday);
		person="전체 고객 수 : "+Integer.toString(totalPerson)+"명";
		tst="전체 서비스 시간 : "+Integer.toString(totalServiceTime)+"분";
		as="평균 서비스 시간 : "+Integer.toString(averageService)+"분";
		aw="평균 대기 시간 : "+Integer.toString(averageWait)+"분";
		cl="전화 서비스 횟수 : "+Integer.toString(callCount)+"번";
		
		setTitle("일일 통계");
		c=getContentPane();
		c.setBackground(Color.PINK);
		c.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 429, 1112, 40);
		c.add(panel);
		
		JButton exit = new JButton("Exit");
		panel.add(exit);
		exit.addActionListener(new MyActionListener());
		
		JButton next = new JButton("Next Day>>");
		panel.add(next);
		next.addActionListener(new MyActionListener());
		
		tablePerson = new JPanel();
		tablePerson.setBounds(480, 12, 618, 407);
		tablePerson.setLayout(new BorderLayout());
		tablePerson.add(mtablePerson.scrollpane,BorderLayout.CENTER);
		c.add(tablePerson);
		
		tableCall = new JPanel();
		tableCall.setBounds(14, 261, 454, 158);
		tableCall.setLayout(new BorderLayout());
		tableCall.add(mtableCall.scrollpane,BorderLayout.CENTER);
		c.add(tableCall);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(14, 12, 454, 245);
		c.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel Sun=new JLabel("------------------------------------------");
		Sun.setBounds(0, 0, 454, 102);
		Sun.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(Sun);
		
		JLabel DayLabel = new JLabel(day);
		DayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		DayLabel.setBounds(0, 0, 454, 52);
		panel_1.add(DayLabel);
		
		JLabel PersonLabel = new JLabel(person);
		PersonLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PersonLabel.setBounds(0, 50, 454, 52);
		panel_1.add(PersonLabel);
		
		JLabel tSLabel = new JLabel(tst);
		tSLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tSLabel.setBounds(0, 92, 454, 39);
		panel_1.add(tSLabel);
		
		JLabel aSLabel = new JLabel(as);
		aSLabel.setHorizontalAlignment(SwingConstants.CENTER);
		aSLabel.setBounds(0, 130, 454, 38);
		panel_1.add(aSLabel);
		
		JLabel aWLabel = new JLabel(aw);
		aWLabel.setHorizontalAlignment(SwingConstants.CENTER);
		aWLabel.setBounds(0, 167, 454, 39);
		panel_1.add(aWLabel);
		
		JLabel callLabel = new JLabel(cl);
		callLabel.setHorizontalAlignment(SwingConstants.CENTER);
		callLabel.setBounds(0, 200, 454, 39);
		panel_1.add(callLabel);		
		
		
		setBounds(500,150,1125,516);
		setSize(1125,516);
		setVisible(true);
	}
	
	class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand()=="Exit") {
				System.exit(0);
			}
			else if(e.getActionCommand()=="Next Day>>") {
				setVisible(false);
				if (serviceNum==1)
				{
					th=new MyThread(++today);
					th.start();
				}
				else if(serviceNum==2)
				{
					th2=new MyThread2(++today);
					th2.start();
				}
				else if(serviceNum==3)
				{
					th3=new MyThread3(++today);
					th3.start();
				}
			}
		}
	}
}