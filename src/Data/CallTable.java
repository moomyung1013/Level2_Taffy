package Data;

import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class CallTable {
	Vector <String> header= new Vector<String>();
	Vector <CallService> callInfo;
	DefaultTableModel model;
	JTable table;
	public JScrollPane scrollpane;
	private String row[]=new String[4]; //��ȭ ������ ������ ������ String�迭
	
	public CallTable(Vector <CallService> callInfo)
	{
		this.callInfo=callInfo;
									                    
		header.addElement("��ȣ");
		header.addElement("���� ���� �ð�");
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
		for(int i=0;i<callInfo.size();i++) {
			row[0]=Integer.toString(callInfo.get(i).num);
			row[1]=Integer.toString(callInfo.get(i).serviceStartTime)+"��";
			row[2]=Integer.toString(callInfo.get(i).serviceTime)+"��";

			if(callInfo.get(i).doService==true)
				row[3]="O";
			else
				row[3]="X";
			model.addRow(row);
		}
	}
}