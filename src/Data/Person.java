package Data;

/*���� ������ ��� �ִ� Ŭ����*/
public class Person {
	public int arriveTime; //�����ð�
	public int startService; //���� ���� �ð�
	public int endService; //���� ���� �ð�
	public int serviceTime; //���� �ð�
	public int waitTime; //��� �ð�
	public int personNum; //���� ��ȣ
	boolean doService; //���񽺰� ����Ǿ��°�
	
	public Person(int currentTime, int number) {
		this.arriveTime=currentTime;
		this.personNum=number;
		this.waitTime=0;
		this.startService=0;
		this.endService=0;
		this.serviceTime=(int)(Math.random()*10+1);
		this.doService=false;
	}
	
	public void ServiceStart(int currentTime) {
		this.waitTime=currentTime-this.arriveTime;
		this.startService=currentTime;
	}
	public void ServiceEnd(int currentTime) {
		this.endService=currentTime;
		doService=true;
	}
	
	public void ServiceFail(int currentTime) {
		this.waitTime=currentTime-this.arriveTime;
	}
}
