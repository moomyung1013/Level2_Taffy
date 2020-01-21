package Simulate;

import java.util.*;

import Data.CallService;
import Data.MakeQueue;
import Data.Person;

/*
  *.2 �� ��� �ι�° â������ ����
 state=6 : ��ȭ���� ����
 state=5 : ��ȭ���� ����
 state=4 : �մ�����
 state=3 : ��������
 state=2 : ������
 state=1 : ���񽺽���
 state=0 : �����
 */

public class Simulate2 {
	private int today=0;
	public double state; //�������
	public double state2;
	public int timeS=0; //ù��° ���� â�������� ���� �ð�
	public int timeC=0;//ù��° ���� â�������� ��ȭ �ð�
	public int timeS2=0;//�ι�° ���� â�������� ���񽺽ð�
	public int timeC2=0;//�ι�° ���� â�������� ��ȭ �ð�
	public int person;
	public int totalPerson=0;
	public int personNum=1;
	public int totalServiceTime=0;
	public int totalWaitTime=0;
	public int time;
	public int callServiceTime[]=new int[2];
	public int waitServiceTime=0; //���� ��� ������ �� ���񽺽ð�
	public int QueueTotalPerson[]=new int[2];	//�� ť�� �ִ� �մ��� ��
	public MakeQueue<Person>  [] waitPerson=new MakeQueue [2];
	public Vector<Person> personInfo;
	public Vector<CallService> callInfo;
	public int isCall; //��ȭ�� �Դ°�?
	public int callCount;
	public boolean serviceDo;
	public boolean serviceDo2;
	public boolean callDo;
	public boolean callDo2;
	
	public int dalbok;
	
	public Simulate2(int day) {
		today=day;
		for(int i=0;i<2;i++)
			waitPerson[i]=new MakeQueue<Person>();
		QueueTotalPerson[0]=0;
		QueueTotalPerson[1]=0;
		callServiceTime[0]=0;
		callServiceTime[1]=0;
		personInfo=new Vector<Person>();
		callInfo=new Vector<CallService>();
		time=0;
		serviceDo=false;
		serviceDo2=false;
		callDo=false;
		callDo2=false;
		state=-1;
		state2=-1;
		callCount=0;
		
		dalbok=0;
	}
	
	synchronized public void Start() {
		if(time<470)
		{
			person=(int)(Math.random()*4+1);
			if(callDo==false || callDo2==false)
				isCall=(int)(Math.random()*17+1);	// isCall�� 1�̸� ��ȭ�� �� ������ �Ѵ�.
			else
				isCall=0;
		}
		else
		{
			isCall=-1;
			person=(int)(Math.random()*3+1);
		}
		state=-10;
		state2=-10;
		/* ��ȭ�� ���� �ʾҰ�, ��ȭ ���񽺵� �������� �ƴ϶�� */
		if(isCall != 1 && (callDo!=true && callDo2!=true))
		{
			/* ���մ��� �����ϰ�, person�� 4�̸� */
			if((waitPerson!=null) && (person==4)) {
				Person client=new Person(time, personNum);	//client ��ü ����
				// �������� ���񽺰� �Ϸ�Ǿ��ٸ� -> ���� ���� //
				if((serviceDo==true) && (waitPerson[0].peek().serviceTime==timeS))
				{
					state=3;
					serviceDo=false;
					waitPerson[0].peek().ServiceEnd(time);
					waitServiceTime-=waitPerson[0].peek().serviceTime;
				//	System.out.println("ù��° â������ "+(waitPerson[0].peek().personNum)+"��° �մ��� �����̽��ϴ�.");
					waitPerson[0].dequeue();
				//	System.out.println("---ù��° ���ǹ� : ù��° â������ "+(++dalbok)+"��° �մ��� �����̽��ϴ�.---");
				}
				if((serviceDo2==true) && (waitPerson[1].peek().serviceTime==timeS2))
				{
					state2=3.2;
					serviceDo2=false;
					waitPerson[1].peek().ServiceEnd(time);
					waitServiceTime-=waitPerson[1].peek().serviceTime;
				//	System.out.println("�ι�° â������ "+(waitPerson[1].peek().personNum)+"��° �մ��� �����̽��ϴ�.");
					waitPerson[1].dequeue();
				//	System.out.println("---ù��° ���ǹ� : �ι�° â������ "+(++dalbok)+"��° �մ��� �����̽��ϴ�.---");
				}
				// �����ð��� �ƴ϶�� -> �մ����� //
				else
				{
					personInfo.add(client);
					totalPerson=personNum;
					waitServiceTime+=client.serviceTime;
					totalServiceTime+=client.serviceTime;
					if(QueueTotalPerson[0] <= QueueTotalPerson[1])
					{	
						state=4;
						waitPerson[0].enqueue(client);
					//	System.out.println("ù��° ť�� "+totalPerson+"��° �մ� ����"+" ���� ���� �ð��� "+client.serviceTime+"�Դϴ�.");
						personNum++;
						QueueTotalPerson[0]++;
					}
					else
					{
						state2=4.2;
						waitPerson[1].enqueue(client);
					//	System.out.println("�ι�° ť�� "+totalPerson+"��° �մ� ����"+" ���� ���� �ð��� "+client.serviceTime+"�Դϴ�.");
						personNum++;
						QueueTotalPerson[1]++;
					}
				}
			}
			
			/* ���񽺰� �������̶�� */
			else if(serviceDo==true || serviceDo2==true) 
			{
				// ���� �ð��� �����ߴٸ� -> �մ� ���� //
				if(serviceDo==true)
				{
					if(waitPerson[0].peek().serviceTime==timeS) 
					{
						state=3;
						serviceDo=false;
						waitPerson[0].peek().ServiceEnd(time);
						waitServiceTime-=waitPerson[0].peek().serviceTime;
					//	System.out.println("ù��° â������ " + (waitPerson[0].peek().personNum)+"��° �մ��� �����̽��ϴ�.");
						waitPerson[0].dequeue();
					//	System.out.println("---�ι�° ���ǹ� : ù��° â������ "+(++dalbok)+"��° �մ��� �����̽��ϴ�.---");
					}
					else
					{
						state=2;
					//	System.out.println("ù��° â���� ���� ���Դϴ�.");
					}
				}
				if(serviceDo2==true)
				{
					if(waitPerson[1].peek().serviceTime==timeS2) 
					{
						state2=3.2;
						serviceDo2=false;
						waitPerson[1].peek().ServiceEnd(time);
						waitServiceTime-=waitPerson[1].peek().serviceTime;
					//	System.out.println("�ι�° â������ "+(waitPerson[1].peek().personNum)+"��° �մ��� �����̽��ϴ�.");
						waitPerson[1].dequeue();
					//	System.out.println("---�ι�° ���ǹ� : �ι�° â������ "+(++dalbok)+"��° �մ��� �����̽��ϴ�.---");
					}
					else
					{
						state2=2;
					//	System.out.println("�ι�° â���� ���� ���Դϴ�.");
					}
				}
				// ���� �ð��� �������� �ʾҴٸ� -> ���� �� ��� //
				else if(serviceDo==false || serviceDo2==false)
				{
					if(serviceDo==false && !waitPerson[0].isEmpty()) 
					{
						timeS=0;
						state=1;
						waitPerson[0].peek().ServiceStart(time);
						totalWaitTime+=waitPerson[0].peek().waitTime;
					//	System.out.println("ù��° â������ "+(waitPerson[0].peek().personNum)+"��° �մ��� ���񽺸� �����մϴ�.");
						serviceDo=true;
					}
					//�ι�° ť�� ��� �մ��� �����ϴ� ��� -> ���� ���� //
					if(serviceDo2==false && !waitPerson[1].isEmpty()) 
					{
						timeS2=0;
						state2=1.2;
						waitPerson[1].peek().ServiceStart(time);
						totalWaitTime+=waitPerson[1].peek().waitTime;
					//	System.out.println("�ι�° â������ "+(waitPerson[1].peek().personNum)+"��° �մ��� ���񽺸� �����մϴ�.");
						serviceDo2=true;
					}
				}

			}
			/* ���񽺰� ���������� �ʴ� ��� */
			else if(serviceDo==false || serviceDo2==false) {
				//ù��° ť�� ��� �մ��� �����ϴ� ��� -> ���� ���� //
				if(serviceDo==false && !waitPerson[0].isEmpty()) 
				{
					timeS=0;
					state=1;
					waitPerson[0].peek().ServiceStart(time);
					totalWaitTime+=waitPerson[0].peek().waitTime;
				//	System.out.println("ù��° â������ "+(waitPerson[0].peek().personNum)+"��° �մ��� ���񽺸� �����մϴ�.");
					serviceDo=true;
				}
				//�ι�° ť�� ��� �մ��� �����ϴ� ��� -> ���� ���� //
				if(serviceDo2==false && !waitPerson[1].isEmpty()) 
				{
					timeS2=0;
					state2=1.2;
					waitPerson[1].peek().ServiceStart(time);
					totalWaitTime+=waitPerson[1].peek().waitTime;
				//	System.out.println("�ι�° â������ "+(waitPerson[1].peek().personNum)+"��° �մ��� ���񽺸� �����մϴ�.");
					serviceDo2=true;
				}

				// ��� �մ��� ���� ��� //
				else if(waitPerson[0].isEmpty() && waitPerson[1].isEmpty())
				{
					state=0;
					state2=0;
				//	System.out.println(waitPerson[0].size()+", "+waitPerson[1].size());
				//	System.out.println("�մ��� ���� ������Դϴ�.");
				}
			}
		}
		
		else
		{
			if((serviceDo==true) && (waitPerson[0].peek().serviceTime==timeS))
			{
				state=3;
				serviceDo=false;
				waitPerson[0].peek().ServiceEnd(time);
				waitServiceTime-=waitPerson[0].peek().serviceTime;
			//	System.out.println("ù��° â������ "+(waitPerson[0].peek().personNum)+"��° �մ��� �����̽��ϴ�.");
				waitPerson[0].dequeue();
			//	System.out.println("---����° ���ǹ� : ù��° â������ "+(++dalbok)+"��° �մ��� �����̽��ϴ�.---");
			}
			if((serviceDo2==true) && (waitPerson[1].peek().serviceTime==timeS2))
			{
				state2=3.2;
				serviceDo2=false;
				waitPerson[1].peek().ServiceEnd(time);
				waitServiceTime-=waitPerson[1].peek().serviceTime;
			//	System.out.println("�ι�° â������ "+(waitPerson[1].peek().personNum)+"��° �մ��� �����̽��ϴ�.");
				waitPerson[1].dequeue();
			//	System.out.println("---����° ���ǹ� : �ι�° â������ "+(++dalbok)+"��° �մ��� �����̽��ϴ�.---");
			}
			
			CallService callService=new CallService();
			CallService callService2=new CallService();
			if(isCall==1)
			{
				if(state!=3 && callDo==false)
				{
					state=5;
					timeC=0;
					callServiceTime[0]=callService.serviceTime;
				//	System.out.println("��ȭ�� �Խ��ϴ�. ù��° â������ ��ȭ���񽺸� �ǽ��մϴ�.");
				//	System.out.println("���񽺽ð��� "+callService.serviceTime+"�Դϴ�.");
					callService.CallServiceStart(time, callCount);
					callService.doService=true;
					callDo=true;
					callCount++;
					callInfo.add(callService);
				}
				else if(state2!=3.2 && callDo2==false)
				{
					state2=5.2;
					timeC2=0;
					callServiceTime[1]=callService2.serviceTime;
				//	System.out.println("��ȭ�� �Խ��ϴ�. �ι�° â������ ��ȭ���񽺸� �ǽ��մϴ�.");
				//	System.out.println("���񽺽ð��� "+callService2.serviceTime+"�Դϴ�.");
					callService2.CallServiceStart(time, callCount);
					callService2.doService=true;
					callDo2=true;
					callCount++;
					callInfo.add(callService2);
				}
			}
			if(callServiceTime[0]==timeC)
			{
				state=6;
			//	System.out.println("ù��° â�� ��ȭ���� �Ϸ�");
				callService.CallServiceEnd();
			//	callInfo.get(callCount-1).doService=true;
				callDo=false;
				timeC=0;	
				callServiceTime[0]=-1;
			}
			if(callServiceTime[1]==timeC2)
			{
				state2=6.2;
				//System.out.println("�ι�° â�� ��ȭ���� �Ϸ�");
				callService2.CallServiceEnd();
			//	callInfo.get(callCount-1).doService=true;
				callDo2=false;
				timeC2=0;
				callServiceTime[1]=-1;
			}
		}
		
		if(callDo!=true)
			timeS++;
		if(callDo2!=true)
			timeS2++;

		timeC++;
		timeC2++;
		time++;
	/*	System.out.println("*****����ð��� "+time+"*****");
		System.out.println((waitPerson[0].size())+(waitPerson[1].size()));
		System.out.println("state : "+state+" state2 : "+state2);
		System.out.println("ù��° â�������� ���� �ð� : "+timeS);
		System.out.println("�ι�° â�������� ���� �ð� : "+timeS2);
		System.out.println("ù��° â�������� ��ȭ���� �ð� : "+timeC);
		System.out.println("�ι�° â�������� ��ȭ���� �ð� : "+timeC2);*/
	}
	
	public void ServiceEnd() 
	{
		while(!waitPerson[0].isEmpty()) 
		{
			waitPerson[0].dequeue().ServiceFail(time);
			while(!waitPerson[1].isEmpty())
			{
				waitPerson[1].dequeue().ServiceFail(time);
			}
		}
	/*	System.out.println("��ü ���� �� : "+totalPerson);
		System.out.println("��ü ���� �ð� : "+totalServiceTime);
		System.out.println("��� ���� �ð� : "+totalServiceTime/totalPerson);
		System.out.println("��� ��� �ð� : "+totalWaitTime/totalPerson);*/
	}
}
