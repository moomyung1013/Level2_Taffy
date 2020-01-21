package Data;

import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class PersonTable {
	Vector <String> header= new Vector<String>();
	Vector <Person> personInfo;
	DefaultTableModel model;
	JTable table;
	public JScrollPane scrollpane;
	private String row[]=new String[6]; //고객의 정보를 저장할 String배열
	public PersonTable(Vector<Person> personInfo){
		
		this.personInfo=personInfo;
		
		header.addElement("접수번호");
		header.addElement("도착시간");
		header.addElement("대기시간");
		header.addElement("서비스시작시간");
		header.addElement("서비스시간");
		header.addElement("서비스완료");
		
		model=new DefaultTableModel(header, 0);
		table=new JTable(model); //테이블 생성
		scrollpane=new JScrollPane(table);
		
		DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER); //DefaultTableCellRenderer 의 정렬을 가운데 정렬로 지정
		TableColumnModel tcmSchedule=table.getColumnModel(); //정렬할 테이블의 ColumnModel을 가져옴
		
		//반복문을 이용하여 테이블을 가운데 정렬
		for(int i=0;i<tcmSchedule.getColumnCount();i++) {
			tcmSchedule.getColumn(i).setCellRenderer(renderer);
		}
		
		addInfo();
	}
	
	void addInfo() {
		for(int i=0;i<personInfo.size();i++) {
			row[0]=Integer.toString(personInfo.get(i).personNum);
			row[1]=Integer.toString(personInfo.get(i).arriveTime)+"분";
			row[2]=Integer.toString(personInfo.get(i).waitTime)+"분";
			row[3]=Integer.toString(personInfo.get(i).startService)+"분";
			row[4]=Integer.toString(personInfo.get(i).serviceTime)+"분";
			if(personInfo.get(i).doService==true)
				row[5]="O";
			else
				row[5]="X";
			model.addRow(row);
		}
	}
}
