# TaffyStoreSimulate

# 개요   
해변에 위치한 태피상점에서는 방문한 고객들에게 순차적으로 서비스를 제공한다. 자신의 서비스가 시작되기 전까지 고객들을 앞사람의 서비스가 수행됨을 기다리고 있어야하며, 개발자는 방문한 모든 고객의 정보를 저장해야 한다. 태피상점에서 순차적으로 발생하는 서비스를 시뮬레이션 시키도록 한다.   

### 제한조건   
1. 먼저 들어온 사람이 서비스를 먼저 받는 선입선출 구조로 큐 모델을 사용하여 고객의 데이터를 관리한다.
1. 하루 영업 시간은 8시간(=480분)이다.
1. 이벤트는 1분단위로 발생한다.
  - 손님 입장
  - 서비스 시작
  - 서비스 수행 중
  - 전화 서비스 시작
  - 전화 서비스 수행 중
1. 1분에 1개를 초과하는 이벤트가 발생해서는 안된다.
1. 하루 영업이 종료되면, 해당 일의 시뮬레이션에 대한 통계자료를 출력한다.

### 확장기능   
1. 1개 이상의 서비스 창구를 제공한다.
1. GUI 환경에서 동작시킨다.
1. 전화 서비스를 제공한다.

<br><br><br>
# 개발 목표   
태피상점에서 순차적으로 발생하는 이벤트를 컴퓨터로 시뮬레이션 하는 프로그램을 제작한다. 각 고객의 정보를 모두 저장할 수 있어야 하며, 이를 통해 통계 정보를 수집하고 사용자가 볼 수 있도록 GUI를 활용하여 화면에 출력한다. 방문한 모든 고객의 정보 또한 통계 자료에 출력할 수 있도록 한다.   

### 개발 도구   
**JAVA-Eclipse IDE 2019-06(jdk1.8.0_211, jre1.8.0_211)**

<br><br><br>
# 자료구조  
- **Person.class**: 고객의 정보를 저장하는 클래스
```java
public class Person
{
  int arriveTime;
  int startService;
  int endService;
  int serviceTime;
  int waitTime;
  int personNum;
  boolean doService;
}
```

|변수명|자료형|설명|
|:---:|:---:|:---:|
|arriveTime|`int`|고객이 도착한 시간|
|startService|`int` |고객의 서비스가 시작된 시간|
|endService |`int`|고객의 서비스가 종료된 시간|
|serviceTime|`int`|고객의 서비스 시간. **1~10 사이의 난수**|
|waitTime|`int`|고객이 서비스를 받기 전까지 대기한 시간|
|personNum|`int`|고객의 접수번호|
|doService|`boolean`|고객의 서비스가 수행되었는가?|

<br><br>
- **CallService.class**: 전화 서비스와 관련된 정보를 저장하는 클래스
```java
public class CallService
{
	public int serviceTime;
	public int serviceStartTime;
	public int num;
	public boolean doService;
}
```

|변수명|자료형|설명|
|:---:|:---:|:---:|
|serviceTime|`int`|전화 서비스 시간. **1~10 사이의 난수**|
|serviceStartTime|`int` |전화 서비스가 시작된 시간|
|num|`int`|전화 서비스 접수번호|
|doService|`boolean`|전화 서비스가 수행되었는가?|

<br><br>
- **MyQueue.class**: 큐를 만드는 클래스
```java
public class MakeQueue <T>
{
	Vector<T> Queue;
}
```

|변수명|자료형|설명|
|:---:|:---:|:---:|
|Queue|`Vector<T>`|큐의 기능을 수행|

<br><br>
- **MyThread.class**: 쓰레드 클래스. *한 개의 창구일 경우 MyThread, 두 개의 창구일 경우 MyThread2, 세 개의 창구일 경우 MyThread3 클래스를 사용*
```java
public class MyThread extends Thread
{
	private int today;
	public Simulate1 su;
	public GUIBuild_1 gui;
}
```

|변수명|자료형|설명|
|:---:|:---:|:---:|
|today|`int`|<dfn info="1일차, 2일차 등">현재 일</dfn> 수를 저장|
|su|`Simulate1`|Simulate 객체|
|gui|`GUIBuild_1`|GUI 객체|

<br><br>
- **Simulate1.class**: 쓰레드 클래스. *한 개의 창구일 경우 Simulate1, 두 개의 창구일 경우 Simulate2, 세 개의 창구일 경우 Simulate3 클래스를 사용*
```java
public class Simulate1
{
	private int today=0;
	public int state;
	public int timeS=0;
	public int timeC=0;
	public int person;
	public int totalPerson=0;
	public int personNum=1;
	public int totalServiceTime=0;
	public int totalWaitTime=0;
	public int time;
	public int waitServiceTime=0;
	public MakeQueue<Person> waitPerson;
	public Vector<Person> personInfo;
	public Vector<CallService> callInfo;
	public int isCall;
	public int callCount;
	public boolean serviceDo;
	public boolean callDo;
}
```

|변수명|자료형|설명|
|:---:|:---:|:---:|
|today|`int`|<dfn info="1일차, 2일차 등">현재 일 수</dfn>를 저장|
|state|`int`또는`double`|<dfn info="state=0: 대기중, state=1: 서비스시작, state=2: 서비스중">현재 상태</dfn>|
|timeS|`int`|현재 수행중인 서비스 시간|
|timeC|`int`|현재 수행중인 전화 서비스 시간|
|person|`int`|1~4 난수 값을 발생시켜 값이 4일 경우 손님이 입장|
|totalPerson|`int`|전체 고객 수|
|personNum|`int`|고객 접수 순번|
|totalServiceTime|`int`|전체 서비스 시간|
|totalWaitTime|`int`|전체 대기시간|
|time|`int`|현재 시간|
|waitPerson|`MakeQueue<Person>`|대기 고객들의 정보가 담긴 큐|
|personInfo|`Vector<Person>`|방문한 고객들의 정보를 담는 벡터|
|callInfo|`Vector<CallService>`|수행된 전화 서비스의 정보를 담는 벡터|
|isCall|`int`|전화가 왔는지 <dfn info="1~17사이의 난수를 발생시켜 값이 1일 때만 전화가 온 것으로 함">확인</dfn>|
|callCount|`int`|전화가 온 횟수|
|serviceDo|`boolean`|서비스가 수행 중인가?|
|callDo|`boolean`|전화 서비스가 수행 중인가?|


<br><br><br>
# 알고리즘   
1. Person.class
  - Person 생성자
```java
public Person(int currentTime, int number)
{
	this.arriveTime=currentTime;
	this.personNum=number;
	this.waitTime=0;
	this.startService=0;
	this.endService=0;
	this.serviceTime=(int)(Math.random()*10+1);
	this.doService=false;
}
```
  - 서비스 시작
```java
public void ServiceStart(int currentTime)
{
	this.waitTime=currentTime-this.arriveTime;
	this.startService=currentTime;
}
```
  - 서비스 종료
```java
public void ServiceEnd(int currentTime)
{
	this.endService=currentTime;
	doService=true;
}
```
1. Simulate1.class
  - 손님 입장
```java
/* 대기손님이 존재하고, person이 4이면 */
if((waitPerson!=null) && (person==4))
{
	Person client=new Person(time, personNum);	//client 객체 생성
	// 마감시간이 아니라면 -> 손님입장 //
	else if(Deadline(client.serviceTime))
	{
		state=4;
		personInfo.add(client);
		totalPerson=personNum;
		waitServiceTime+=client.serviceTime;
		totalServiceTime+=client.serviceTime;
		waitPerson.enqueue(client);
		personNum++;
	}
}
```
  - 손님 퇴장 및 서비스 종료
```java
// 수행중인 서비스가 완료되었다면 -> 서비스 종료 //
if((serviceDo==true) && (waitPerson.peek().serviceTime==timeS))
{
	state=3;
	serviceDo=false;
	waitPerson.peek().ServiceEnd(time);
	waitServiceTime-=waitPerson.peek().serviceTime;
	waitPerson.dequeue();
}
```
1. Simulate2.class
  - 손님 입장
```java
/* 전화가 오지 않았고, 전화 서비스도 수행중이 아니라면 */
if(isCall != 1 && (callDo!=true && callDo2!=true))
{
	/* 대기손님이 존재하고, person이 4이면 */
	if((waitPerson!=null) && (person==4))
	{
		Person client=new Person(time, personNum);	//client 객체 생성
		// 마감시간이 아니라면 -> 손님입장 //
		personInfo.add(client);
		totalPerson=personNum;
		waitServiceTime+=client.serviceTime;
		totalServiceTime+=client.serviceTime;
		if(QueueTotalPerson[0] <= QueueTotalPerson[1])
		{
		 state=4;
		 waitPerson[0].enqueue(client);
		 personNum++;
		 QueueTotalPerson[0]++;
		}
		else
		{
		 state2=4.2;
		 waitPerson[1].enqueue(client);
		 personNum++;
		 QueueTotalPerson[1]++;
		}
	}
}
```

<br><br><br>
# 실행 화면   
1. 기본화면   
![basicScreen](https://github.com/moomyung1013/Level2_Taffy/blob/master/img/basicScreen.png?raw=true)   
프로그램을 실행하면 위와 같은 화면이 뜬다. START 버튼을 누르면 다음 프레임으로 이동하고, EXIT 버튼을 누르면 프로그램이 종료된다.   
<br><br>
1. 창구 수 선택   
![selectScreen](https://github.com/moomyung1013/Level2_Taffy/blob/master/img/select.png?raw=true)   
창구 수를 선택하라는 문구와 함께 창구 수를 선택할 수 있는 버튼 세 개가 생성된다. 클릭한 버튼에 맞는 창구가 생성된 시뮬레이션이 실행된다.   
<br><br>
1. 한 개의 창구   
![simulationOne](https://github.com/moomyung1013/Level2_Taffy/blob/master/img/one.png?raw=true)   
창구가 한 개일 경우 보이는 화면이다. 맨 아래의 직사각형이 서비스 창구이며, 고객의 서비스가 시작될 때 창구 앞에 고객 아이콘이 생성된다.   
<br><br>
1. 두 개의 창구   
![simulationTwo](https://github.com/moomyung1013/Level2_Taffy/blob/master/img/two.png?raw=true)   
창구가 두 개일 경우 보이는 화면이다. 맨 아래의 서비스 창구가 2개임을 확인할 수 있다.   
<br><br>
1. 세 개의 창구   
![simulationThree](https://github.com/moomyung1013/Level2_Taffy/blob/master/img/three.png?raw=true)   
창구가 세 개일 경우 보이는 화면이다. 맨 아래의 서비스 창구가 3개임을 확인할 수 있다.   
 - 손님 입장
 ![clientCome](https://github.com/moomyung1013/Level2_Taffy/blob/master/img/clientCome.png?raw=true)   
 손님이 입장할 시, 가운데 대기 패널에 고객 아이콘이 생성된다.   
 - 서비스 중   
 ![service](https://github.com/moomyung1013/Level2_Taffy/blob/master/img/service.png?raw=true)   
 창구 1과 창구 3에서 고객의 서비스가 수행 중임을 확인할 수 있다.   
 - 대기 중   
 ![wait](https://github.com/moomyung1013/Level2_Taffy/blob/master/img/waitScreen.png?raw=true)   
 아무런 서비스가 수행 중이지 않고, 대기 고객 역시 존재하지 않아 손님이 없어 대기 중임을 JLabel로 출력하고 있다.   
 - 서비스 종료   
 ![serviceEnd](https://github.com/moomyung1013/Level2_Taffy/blob/master/img/serviceEnd.png?raw=true)   
 첫 번째 창구에서 수행 중이던 서비스가 종료되어 창구 앞에 있던 손님이 퇴장함을 확인할 수 있다. *창구 3에선 전화 서비스가 수행되고 있다.*   
<br><br>
1. 일일 통계   
![statistics](https://github.com/moomyung1013/Level2_Taffy/blob/master/img/statistics.png?raw=true)   
하루 영업이 종료될 때마다 위와 같은 통계정보가 출력된다. 왼쪽 상단 패널은 하루 전체 통계량을 나타내며, 왼쪽 하단의 JTable은 전화 서비스 정보를 의미한다. 또한, 오른쪽의 JTable은 모든 고객의 정보를 출력하며 하단의 버튼 두개 중 EXIT 버튼을 클릭하면 프로그램이 종료되고, NEXTDAY>>를 클릭하면 다음 날의 시뮬레이션이 진행된다.<br><br>

