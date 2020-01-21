package Simulate;

import javax.swing.*;

import GUI.GUIBuild_2;

import java.awt.*;
public class MyThread2 extends Thread {
	private int today;
	public Simulate2 su;
	public GUIBuild_2 gui;
	public MyThread2(int day) {
		this.today=day;
		su=new Simulate2(today);
		gui=new GUIBuild_2(today);
	}

	public int i=0;
	public void run() {
		while(su.time!=480) {
			try {
				su.Start();
				if(su.state==4) 
				{ //�մ� ����
					gui.makeButton(i++);
					gui.ServiceState(su.state);
				}
				else if(su.state==3) 
				{ //���� ����
					gui.ServiceEnd1();
					gui.ServiceState(su.state);
				}
				else if(su.state==1) 
				{ //���� ����
					gui.ServiceStart1();
					gui.deleteButton(--i);
					gui.ServiceState(su.state);
				}
				else if(su.state==0 && su.state2==0) 
				{ //�����
					gui.ServiceState(su.state);
				}
				else if(su.state==5) 
				{ //��ȭ���񽺽���
					gui.callServiceStart1();
					gui.ServiceState(su.state);
				}
				else if(su.state==6) 
				{ //��ȭ ���� ����
					gui.callServiceEnd1();
					gui.ServiceState(su.state);
				}
				else if(su.state==2 || su.state2==2)
				{ //���� ��
					gui.ServiceState(su.state);
				}
				
				if(su.state2==1.2) 
				{ 
					gui.ServiceStart2();
					gui.deleteButton(--i);
					gui.ServiceState(su.state2);
				}
				else if(su.state2==3.2) 
				{
					gui.ServiceEnd2();
					gui.ServiceState(su.state2);
				}
				else if(su.state2==4.2)
				{
					gui.makeButton(i++);
					gui.ServiceState(su.state2);
				}
				else if(su.state2==5.2) 
				{
					gui.callServiceStart2();
					gui.ServiceState(su.state2);
				}
				else if(su.state2==6.2) 
				{
					gui.callServiceEnd2();
					gui.ServiceState(su.state2);
				}
				
				gui.currentTime(su.time);
				
				if(su.time>=470) 
				{
					gui.DeadLineMsg();
				}
				sleep(100);
				System.out.println();

			}
			catch(InterruptedException e)
			{
				return;
			}
		}
		su.ServiceEnd();
		gui.storeEnd(today, su.totalPerson, su.totalServiceTime, (su.totalServiceTime)/(su.totalPerson), (su.totalWaitTime)/(su.totalPerson), su.callCount, su.personInfo, su.callInfo);
	}
}
