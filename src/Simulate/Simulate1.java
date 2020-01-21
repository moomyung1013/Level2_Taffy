package Simulate;

import java.util.*;

import Data.CallService;
import Data.MakeQueue;
import Data.Person;

/*
 state=6 : ��ȭ���� ����
 state=5 : ��ȭ���� ����
 state=4 : �մ�����
 state=3 : ��������
 state=2 : ������
 state=1 : ���񽺽���
 state=0 : �����
 */

public class Simulate1 {
	private int today=0;
	public int state; //�������
	public int timeS=0;
	public int timeC=0;
	public int person;
	public int totalPerson=0;
	public int personNum=1;
	public int totalServiceTime=0;
	public int totalWaitTime=0;
	public int time;
	public int waitServiceTime=0; //���� ��� ������ �� ���񽺽ð�
	public MakeQueue<Person> waitPerson;
	public Vector<Person> personInfo;
	public Vector<CallService> callInfo;
	public int isCall; //��ȭ�� �Դ°�?
	public int callCount;
	public boolean serviceDo;
	public boolean callDo;
	
	public Simulate1(int day) {
		today=day;
		waitPerson=new MakeQueue<Person>();
		personInfo=new Vector<Person>();
		callInfo=new Vector<CallService>();
		time=0;
		serviceDo=false;
		callDo=false;
		state=-1;
		callCount=0;
	}
	
	synchronized public void Start() {
		if(time<470) {
			person=(int)(Math.random()*4+1);
			if(callDo==false)
				isCall=(int)(Math.random()*17+1);	// isCall�� 1�̸� ��ȭ�� �� ������ �Ѵ�.
			else
				isCall=0;
		}
		else {
			isCall=-1;
			person=(int)(Math.random()*3+1);
		}
		
		/* ��ȭ�� ���� �ʾҰ�, ��ȭ ���񽺵� �������� �ƴ϶�� */
		if(isCall != 1 && callDo!=true)
		{
			/* ���մ��� �����ϰ�, person�� 4�̸� */
			if((waitPerson!=null) && (person==4)) {
				Person client=new Person(time, personNum);	//client ��ü ����
				// �������� ���񽺰� �Ϸ�Ǿ��ٸ� -> ���� ���� //
				if((serviceDo==true) && (waitPerson.peek().serviceTime==timeS)) {
					state=3;
					serviceDo=false;
					waitPerson.peek().ServiceEnd(time);
					waitServiceTime-=waitPerson.peek().serviceTime;
				//	System.out.println((waitPerson.peek().personNum)+"��° �մ��� �����̽��ϴ�.");
					waitPerson.dequeue();
				}
				// �����ð��� �ƴ϶�� -> �մ����� //
				else if(Deadline(client.serviceTime)) {
					state=4;
					personInfo.add(client);
					totalPerson=personNum;
					waitServiceTime+=client.serviceTime;
					totalServiceTime+=client.serviceTime;
					waitPerson.enqueue(client);
			//		System.out.println(totalPerson+"��° �մ� ����"+" ���� ���� �ð��� "+client.serviceTime+"�Դϴ�.");
					personNum++;
				}
				// �����ð��̴� -> �����ð����� ��� //
				else
				{
					state=-1;
				//	System.out.println("�����ð��� �ٵǰ��� �մ��� ���� �� �����ϴ�.");
				}
			}
			
			/* ���񽺰� �������̶�� */
			else if(serviceDo==true) {
				// ���� �ð��� �����ߴٸ� -> �մ� ���� //
				if(waitPerson.peek().serviceTime==timeS) {
					state=3;
					serviceDo=false;
					waitPerson.peek().ServiceEnd(time);
					waitServiceTime-=waitPerson.peek().serviceTime;
				//	System.out.println((waitPerson.peek().personNum)+"��° �մ��� �����̽��ϴ�.");
					waitPerson.dequeue();
				}
				// ���� �ð��� �������� �ʾҴٸ� -> ���� �� ��� //
				else {
					state=2;
				//	System.out.println("���� ���Դϴ�.");
				}
			}
			/* ���񽺰� ���������� �ʴ� ��� */
			else {
				// ��� �մ��� �����ϴ� ��� -> ���� ���� //
				if(!waitPerson.isEmpty()) {
					timeS=0;
					state=1;
					waitPerson.peek().ServiceStart(time);
					totalWaitTime+=waitPerson.peek().waitTime;
				//	System.out.println((waitPerson.peek().personNum)+"��° �մ��� ���񽺸� �����մϴ�.");
					serviceDo=true;
				}
				// ��� �մ��� ���� ��� //
				else{
					state=0;
				//	System.out.println("�մ��� ���� ������Դϴ�.");
				}
			}
			timeS++;
		}
		
		else if(isCall==1 || isCall==0)
		{
			CallService callService=new CallService();
			if(isCall==1)
			{
				timeC=0;
			//	System.out.println("��ȭ�� �Խ��ϴ�. ��ȭ���񽺸� �ǽ��մϴ�.");
			//	System.out.println("���񽺽ð��� "+callService.serviceTime+"�Դϴ�.");
				callService.CallServiceStart(time, callCount);
				callDo=true;
				callCount++;
				callInfo.add(callService);
				state=5;
			}
			else if(isCall==0)
			{
				if(callInfo.get(callCount-1).serviceTime==timeC)
				{
					state=6;
				//	System.out.println("��ȭ���� �Ϸ�");
					callService.CallServiceEnd();
					callInfo.get(callCount-1).doService=true;
					callDo=false;
					timeC=0;
				}
			//	System.out.println("��ȭ���� �� : "+timeC);
			}
			timeC++;
			
			
		}
		time++;
	}

	public boolean Deadline(int time) {
		if(470>(this.time+waitServiceTime+time)) {
			return true;
		}
		else
			return false;
	}
	
	public void ServiceEnd() {
		while(!waitPerson.isEmpty()) {
			waitPerson.dequeue().ServiceFail(time);
		}
	/*	System.out.println("��ü ���� �� : "+totalPerson);
		System.out.println("��ü ���� �ð� : "+totalServiceTime);
		System.out.println("��� ���� �ð� : "+totalServiceTime/totalPerson);
		System.out.println("��� ��� �ð� : "+totalWaitTime/totalPerson);*/
	}
}
