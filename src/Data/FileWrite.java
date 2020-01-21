package Data;

import java.io.*;
import java.util.Vector;

public class FileWrite 
{
	String string[]=new String[6];
	Vector<Person> PersonInfo=new Vector<Person>();
	
	public FileWrite(Vector<Person> person) 
	{
		this.PersonInfo=person;
		
		
		try 
		{
			FileWriter fwrite=new FileWriter("�Ϸ� ��跮.txt");
			for(int i=0;i<(this.PersonInfo.size());i++) {
				string[0]=Integer.toString(PersonInfo.get(i).personNum)+"��";
				string[1]=Integer.toString(PersonInfo.get(i).arriveTime)+"��";
				string[2]=Integer.toString(PersonInfo.get(i).waitTime)+"��";
				string[3]=Integer.toString(PersonInfo.get(i).startService)+"��";
				string[4]=Integer.toString(PersonInfo.get(i).serviceTime)+"��";
				if(PersonInfo.get(i).doService==false)
					string[5]="X";
				else
					string[5]="O";
			
				for(int j=0;j<string.length;j++) 
				{
					fwrite.write(string[j],0,string[j].length());
					fwrite.write('\t');
				}
				fwrite.write("\r\n");
			}
			fwrite.close();
		}
		catch(IOException e)
		{
			System.out.println("���� ����� ����");
		}
	}
}
