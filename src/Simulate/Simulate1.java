package Simulate;

import java.util.*;

import Data.CallService;
import Data.MakeQueue;
import Data.Person;

/*
 state=6 : 전화서비스 종료
 state=5 : 전화서비스 시작
 state=4 : 손님입장
 state=3 : 서비스종료
 state=2 : 서비스중
 state=1 : 서비스시작
 state=0 : 대기중
 */

public class Simulate1 {
	private int today=0;
	public int state; //현재상태
	public int timeS=0;
	public int timeC=0;
	public int person;
	public int totalPerson=0;
	public int personNum=1;
	public int totalServiceTime=0;
	public int totalWaitTime=0;
	public int time;
	public int waitServiceTime=0; //현재 대기 고객들의 총 서비스시간
	public MakeQueue<Person> waitPerson;
	public Vector<Person> personInfo;
	public Vector<CallService> callInfo;
	public int isCall; //전화가 왔는가?
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
				isCall=(int)(Math.random()*17+1);	// isCall이 1이면 전화가 온 것으로 한다.
			else
				isCall=0;
		}
		else {
			isCall=-1;
			person=(int)(Math.random()*3+1);
		}
		
		/* 전화가 오지 않았고, 전화 서비스도 수행중이 아니라면 */
		if(isCall != 1 && callDo!=true)
		{
			/* 대기손님이 존재하고, person이 4이면 */
			if((waitPerson!=null) && (person==4)) {
				Person client=new Person(time, personNum);	//client 객체 생성
				// 수행중인 서비스가 완료되었다면 -> 서비스 종료 //
				if((serviceDo==true) && (waitPerson.peek().serviceTime==timeS)) {
					state=3;
					serviceDo=false;
					waitPerson.peek().ServiceEnd(time);
					waitServiceTime-=waitPerson.peek().serviceTime;
				//	System.out.println((waitPerson.peek().personNum)+"번째 손님이 나가셨습니다.");
					waitPerson.dequeue();
				}
				// 마감시간이 아니라면 -> 손님입장 //
				else if(Deadline(client.serviceTime)) {
					state=4;
					personInfo.add(client);
					totalPerson=personNum;
					waitServiceTime+=client.serviceTime;
					totalServiceTime+=client.serviceTime;
					waitPerson.enqueue(client);
			//		System.out.println(totalPerson+"번째 손님 입장"+" 고객의 서비스 시간은 "+client.serviceTime+"입니다.");
					personNum++;
				}
				// 마감시간이다 -> 마감시간임을 출력 //
				else
				{
					state=-1;
				//	System.out.println("마감시간이 다되가서 손님을 받을 수 없습니다.");
				}
			}
			
			/* 서비스가 수행중이라면 */
			else if(serviceDo==true) {
				// 서비스 시간을 충족했다면 -> 손님 퇴장 //
				if(waitPerson.peek().serviceTime==timeS) {
					state=3;
					serviceDo=false;
					waitPerson.peek().ServiceEnd(time);
					waitServiceTime-=waitPerson.peek().serviceTime;
				//	System.out.println((waitPerson.peek().personNum)+"번째 손님이 나가셨습니다.");
					waitPerson.dequeue();
				}
				// 서비스 시간을 충족하지 않았다면 -> 서비스 중 출력 //
				else {
					state=2;
				//	System.out.println("서비스 중입니다.");
				}
			}
			/* 서비스가 수행중이지 않는 경우 */
			else {
				// 대기 손님이 존재하는 경우 -> 서비스 시작 //
				if(!waitPerson.isEmpty()) {
					timeS=0;
					state=1;
					waitPerson.peek().ServiceStart(time);
					totalWaitTime+=waitPerson.peek().waitTime;
				//	System.out.println((waitPerson.peek().personNum)+"번째 손님의 서비스를 시작합니다.");
					serviceDo=true;
				}
				// 대기 손님이 없는 경우 //
				else{
					state=0;
				//	System.out.println("손님이 없어 대기중입니다.");
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
			//	System.out.println("전화가 왔습니다. 전화서비스를 실시합니다.");
			//	System.out.println("서비스시간은 "+callService.serviceTime+"입니다.");
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
				//	System.out.println("전화서비스 완료");
					callService.CallServiceEnd();
					callInfo.get(callCount-1).doService=true;
					callDo=false;
					timeC=0;
				}
			//	System.out.println("전화서비스 중 : "+timeC);
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
	/*	System.out.println("전체 고객의 수 : "+totalPerson);
		System.out.println("전체 서비스 시간 : "+totalServiceTime);
		System.out.println("평균 서비스 시간 : "+totalServiceTime/totalPerson);
		System.out.println("평균 대기 시간 : "+totalWaitTime/totalPerson);*/
	}
}
