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
	private String row[]=new String[6]; //���� ������ ������ String�迭
	public PersonTable(Vector<Person> personInfo){
		
		this.personInfo=personInfo;
		
		header.addElement("������ȣ");
		header.addElement("�����ð�");
		header.addElement("���ð�");
		header.addElement("���񽺽��۽ð�");
		header.addElement("���񽺽ð�");
		header.addElement("���񽺿Ϸ�");
		
		model=new DefaultTableModel(header, 0);
		table=new JTable(model); //���̺� ����
		scrollpane=new JScrollPane(table);
		
		DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER); //DefaultTableCellRenderer �� ������ ��� ���ķ� ����
		TableColumnModel tcmSchedule=table.getColumnModel(); //������ ���̺��� ColumnModel�� ������
		
		//�ݺ����� �̿��Ͽ� ���̺��� ��� ����
		for(int i=0;i<tcmSchedule.getColumnCount();i++) {
			tcmSchedule.getColumn(i).setCellRenderer(renderer);
		}
		
		addInfo();
	}
	
	void addInfo() {
		for(int i=0;i<personInfo.size();i++) {
			row[0]=Integer.toString(personInfo.get(i).personNum);
			row[1]=Integer.toString(personInfo.get(i).arriveTime)+"��";
			row[2]=Integer.toString(personInfo.get(i).waitTime)+"��";
			row[3]=Integer.toString(personInfo.get(i).startService)+"��";
			row[4]=Integer.toString(personInfo.get(i).serviceTime)+"��";
			if(personInfo.get(i).doService==true)
				row[5]="O";
			else
				row[5]="X";
			model.addRow(row);
		}
	}
}
