package Simulate;

import java.util.*;

import Data.CallService;
import Data.MakeQueue;
import Data.Person;

/*
  *.2 일 경우 두번째 창구에서 수행
 state=6 : 전화서비스 종료
 state=5 : 전화서비스 시작
 state=4 : 손님입장
 state=3 : 서비스종료
 state=2 : 서비스중
 state=1 : 서비스시작
 state=0 : 대기중
 */

public class Simulate2 {
	private int today=0;
	public double state; //현재상태
	public double state2;
	public int timeS=0; //첫번째 서비스 창구에서의 서비스 시간
	public int timeC=0;//첫번째 서비스 창구에서의 통화 시간
	public int timeS2=0;//두번째 서비스 창구에서의 서비스시간
	public int timeC2=0;//두번째 서비스 창구에서의 통화 시간
	public int person;
	public int totalPerson=0;
	public int personNum=1;
	public int totalServiceTime=0;
	public int totalWaitTime=0;
	public int time;
	public int callServiceTime[]=new int[2];
	public int waitServiceTime=0; //현재 대기 고객들의 총 서비스시간
	public int QueueTotalPerson[]=new int[2];	//각 큐에 있는 손님의 수
	public MakeQueue<Person>  [] waitPerson=new MakeQueue [2];
	public Vector<Person> personInfo;
	public Vector<CallService> callInfo;
	public int isCall; //전화가 왔는가?
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
				isCall=(int)(Math.random()*17+1);	// isCall이 1이면 전화가 온 것으로 한다.
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
		/* 전화가 오지 않았고, 전화 서비스도 수행중이 아니라면 */
		if(isCall != 1 && (callDo!=true && callDo2!=true))
		{
			/* 대기손님이 존재하고, person이 4이면 */
			if((waitPerson!=null) && (person==4)) {
				Person client=new Person(time, personNum);	//client 객체 생성
				// 수행중인 서비스가 완료되었다면 -> 서비스 종료 //
				if((serviceDo==true) && (waitPerson[0].peek().serviceTime==timeS))
				{
					state=3;
					serviceDo=false;
					waitPerson[0].peek().ServiceEnd(time);
					waitServiceTime-=waitPerson[0].peek().serviceTime;
				//	System.out.println("첫번째 창구에서 "+(waitPerson[0].peek().personNum)+"번째 손님이 나가셨습니다.");
					waitPerson[0].dequeue();
				//	System.out.println("---첫번째 조건문 : 첫번째 창구에서 "+(++dalbok)+"번째 손님이 나가셨습니다.---");
				}
				if((serviceDo2==true) && (waitPerson[1].peek().serviceTime==timeS2))
				{
					state2=3.2;
					serviceDo2=false;
					waitPerson[1].peek().ServiceEnd(time);
					waitServiceTime-=waitPerson[1].peek().serviceTime;
				//	System.out.println("두번째 창구에서 "+(waitPerson[1].peek().personNum)+"번째 손님이 나가셨습니다.");
					waitPerson[1].dequeue();
				//	System.out.println("---첫번째 조건문 : 두번째 창구에서 "+(++dalbok)+"번째 손님이 나가셨습니다.---");
				}
				// 마감시간이 아니라면 -> 손님입장 //
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
					//	System.out.println("첫번째 큐에 "+totalPerson+"번째 손님 입장"+" 고객의 서비스 시간은 "+client.serviceTime+"입니다.");
						personNum++;
						QueueTotalPerson[0]++;
					}
					else
					{
						state2=4.2;
						waitPerson[1].enqueue(client);
					//	System.out.println("두번째 큐에 "+totalPerson+"번째 손님 입장"+" 고객의 서비스 시간은 "+client.serviceTime+"입니다.");
						personNum++;
						QueueTotalPerson[1]++;
					}
				}
			}
			
			/* 서비스가 수행중이라면 */
			else if(serviceDo==true || serviceDo2==true) 
			{
				// 서비스 시간을 충족했다면 -> 손님 퇴장 //
				if(serviceDo==true)
				{
					if(waitPerson[0].peek().serviceTime==timeS) 
					{
						state=3;
						serviceDo=false;
						waitPerson[0].peek().ServiceEnd(time);
						waitServiceTime-=waitPerson[0].peek().serviceTime;
					//	System.out.println("첫번째 창구에서 " + (waitPerson[0].peek().personNum)+"번째 손님이 나가셨습니다.");
						waitPerson[0].dequeue();
					//	System.out.println("---두번째 조건문 : 첫번째 창구에서 "+(++dalbok)+"번째 손님이 나가셨습니다.---");
					}
					else
					{
						state=2;
					//	System.out.println("첫번째 창구는 서비스 중입니다.");
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
					//	System.out.println("두번째 창구에서 "+(waitPerson[1].peek().personNum)+"번째 손님이 나가셨습니다.");
						waitPerson[1].dequeue();
					//	System.out.println("---두번째 조건문 : 두번째 창구에서 "+(++dalbok)+"번째 손님이 나가셨습니다.---");
					}
					else
					{
						state2=2;
					//	System.out.println("두번째 창구는 서비스 중입니다.");
					}
				}
				// 서비스 시간을 충족하지 않았다면 -> 서비스 중 출력 //
				else if(serviceDo==false || serviceDo2==false)
				{
					if(serviceDo==false && !waitPerson[0].isEmpty()) 
					{
						timeS=0;
						state=1;
						waitPerson[0].peek().ServiceStart(time);
						totalWaitTime+=waitPerson[0].peek().waitTime;
					//	System.out.println("첫번째 창구에서 "+(waitPerson[0].peek().personNum)+"번째 손님의 서비스를 시작합니다.");
						serviceDo=true;
					}
					//두번째 큐에 대기 손님이 존재하는 경우 -> 서비스 시작 //
					if(serviceDo2==false && !waitPerson[1].isEmpty()) 
					{
						timeS2=0;
						state2=1.2;
						waitPerson[1].peek().ServiceStart(time);
						totalWaitTime+=waitPerson[1].peek().waitTime;
					//	System.out.println("두번째 창구에서 "+(waitPerson[1].peek().personNum)+"번째 손님의 서비스를 시작합니다.");
						serviceDo2=true;
					}
				}

			}
			/* 서비스가 수행중이지 않는 경우 */
			else if(serviceDo==false || serviceDo2==false) {
				//첫번째 큐에 대기 손님이 존재하는 경우 -> 서비스 시작 //
				if(serviceDo==false && !waitPerson[0].isEmpty()) 
				{
					timeS=0;
					state=1;
					waitPerson[0].peek().ServiceStart(time);
					totalWaitTime+=waitPerson[0].peek().waitTime;
				//	System.out.println("첫번째 창구에서 "+(waitPerson[0].peek().personNum)+"번째 손님의 서비스를 시작합니다.");
					serviceDo=true;
				}
				//두번째 큐에 대기 손님이 존재하는 경우 -> 서비스 시작 //
				if(serviceDo2==false && !waitPerson[1].isEmpty()) 
				{
					timeS2=0;
					state2=1.2;
					waitPerson[1].peek().ServiceStart(time);
					totalWaitTime+=waitPerson[1].peek().waitTime;
				//	System.out.println("두번째 창구에서 "+(waitPerson[1].peek().personNum)+"번째 손님의 서비스를 시작합니다.");
					serviceDo2=true;
				}

				// 대기 손님이 없는 경우 //
				else if(waitPerson[0].isEmpty() && waitPerson[1].isEmpty())
				{
					state=0;
					state2=0;
				//	System.out.println(waitPerson[0].size()+", "+waitPerson[1].size());
				//	System.out.println("손님이 없어 대기중입니다.");
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
			//	System.out.println("첫번째 창구에서 "+(waitPerson[0].peek().personNum)+"번째 손님이 나가셨습니다.");
				waitPerson[0].dequeue();
			//	System.out.println("---세번째 조건문 : 첫번째 창구에서 "+(++dalbok)+"번째 손님이 나가셨습니다.---");
			}
			if((serviceDo2==true) && (waitPerson[1].peek().serviceTime==timeS2))
			{
				state2=3.2;
				serviceDo2=false;
				waitPerson[1].peek().ServiceEnd(time);
				waitServiceTime-=waitPerson[1].peek().serviceTime;
			//	System.out.println("두번째 창구에서 "+(waitPerson[1].peek().personNum)+"번째 손님이 나가셨습니다.");
				waitPerson[1].dequeue();
			//	System.out.println("---세번째 조건문 : 두번째 창구에서 "+(++dalbok)+"번째 손님이 나가셨습니다.---");
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
				//	System.out.println("전화가 왔습니다. 첫번째 창구에서 전화서비스를 실시합니다.");
				//	System.out.println("서비스시간은 "+callService.serviceTime+"입니다.");
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
				//	System.out.println("전화가 왔습니다. 두번째 창구에서 전화서비스를 실시합니다.");
				//	System.out.println("서비스시간은 "+callService2.serviceTime+"입니다.");
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
			//	System.out.println("첫번째 창구 전화서비스 완료");
				callService.CallServiceEnd();
			//	callInfo.get(callCount-1).doService=true;
				callDo=false;
				timeC=0;	
				callServiceTime[0]=-1;
			}
			if(callServiceTime[1]==timeC2)
			{
				state2=6.2;
				//System.out.println("두번째 창구 전화서비스 완료");
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
	/*	System.out.println("*****현재시간은 "+time+"*****");
		System.out.println((waitPerson[0].size())+(waitPerson[1].size()));
		System.out.println("state : "+state+" state2 : "+state2);
		System.out.println("첫번째 창구에서의 서비스 시간 : "+timeS);
		System.out.println("두번째 창구에서의 서비스 시간 : "+timeS2);
		System.out.println("첫번째 창구에서의 전화서비스 시간 : "+timeC);
		System.out.println("두번째 창구에서의 전화서비스 시간 : "+timeC2);*/
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
	/*	System.out.println("전체 고객의 수 : "+totalPerson);
		System.out.println("전체 서비스 시간 : "+totalServiceTime);
		System.out.println("평균 서비스 시간 : "+totalServiceTime/totalPerson);
		System.out.println("평균 대기 시간 : "+totalWaitTime/totalPerson);*/
	}
}
