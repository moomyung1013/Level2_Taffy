package Data;

public class CallService
{
	public int serviceTime; //���� �ð�
	public int serviceStartTime; //���� ���� �ð�
	public int num;
	public boolean doService; //���񽺰� ����Ǿ��°�
	
	public CallService() {
		this.serviceTime=(int)(Math.random()*10+1);
		this.doService=false;
	}
	
	public void CallServiceStart(int currentTime, int num)
	{
		this.serviceStartTime=currentTime;
		this.num=num+1;
	}
	
	public void CallServiceEnd()
	{
		this.doService=true;
	}
	
}
