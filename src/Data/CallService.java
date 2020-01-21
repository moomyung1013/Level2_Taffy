package Data;

public class CallService
{
	public int serviceTime; //서비스 시간
	public int serviceStartTime; //서비스 시작 시간
	public int num;
	public boolean doService; //서비스가 수행되었는가
	
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
