package Simulate;

import javax.swing.*;

import GUI.GUIBuild_1;

import java.awt.*;
public class MyThread extends Thread {
	private int today;
	public Simulate1 su;
	public GUIBuild_1 gui;
	public MyThread(int day) {
		this.today=day;
		su=new Simulate1(today);
		gui=new GUIBuild_1(today);
	}

	public int i=0;
	public void run() {
		while(su.time!=480) {
			try {
				su.Start();
				if(su.state==4) { //¼Õ´Ô ÀÔÀå
					gui.makeButton(i++);
					gui.ServiceState(su.state);
				}
				else if(su.state==3) {
					gui.ServiceEnd();
					gui.ServiceState(su.state);
				}
				else if(su.state==2) {
					gui.ServiceState(su.state);
				}
				else if(su.state==1) {
					gui.ServiceStart();
					gui.deleteButton(--i);
					gui.ServiceState(su.state);
				}
				else if(su.state==0) {
					gui.ServiceState(su.state);
				}
				else if(su.state==5) {
					gui.callServiceStart();
					gui.ServiceState(su.state);
				}
				else if(su.state==6) {
					gui.callServiceEnd();
					gui.ServiceState(su.state);
				}
				
				gui.currentTime(su.time);
				
				if(su.time>=470) {
					gui.DeadLineMsg();
				}
				sleep(100);

			}
			catch(InterruptedException e) {
				return;
			}
		}
		su.ServiceEnd();
		gui.storeEnd(today, su.totalPerson, su.totalServiceTime, (su.totalServiceTime)/(su.totalPerson), (su.totalWaitTime)/(su.totalPerson), su.callCount, su.personInfo, su.callInfo);
	}
}
