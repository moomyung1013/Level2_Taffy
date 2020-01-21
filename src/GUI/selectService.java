package GUI;


import javax.swing.*;

import Simulate.*;

import java.awt.*;
import java.awt.event.*;

public class selectService
{
	private JFrame selectFrame;

	public selectService()
	{
		selectFrame=new JFrame();
		selectFrame.getContentPane().setBackground(new Color(255, 102, 102));
		selectFrame.setBounds(500, 150, 947, 511);
		selectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		selectFrame.getContentPane().setLayout(null);
		
		JLabel selectNum = new JLabel("\uCC3D\uAD6C\uC758 \uC218\uB97C \uC120\uD0DD\uD558\uC2DC\uC624");
		selectNum.setHorizontalAlignment(SwingConstants.CENTER);
		selectNum.setForeground(Color.WHITE);
		selectNum.setFont(new Font("³ª´®°íµñ", Font.BOLD, 38));
		selectNum.setBounds(237, 144, 463, 85);
		selectFrame.getContentPane().add(selectNum);
		
		JButton Btn1 = new JButton("1\uAC1C");
		Btn1.setFont(new Font("³ª´®°íµñ", Font.BOLD, 15));
		Btn1.setBounds(142, 295, 162, 48);
		Btn1.addActionListener(new SwingAction());
		selectFrame.getContentPane().add(Btn1);
		
		JButton Btn2 = new JButton("2\uAC1C");
		Btn2.setFont(new Font("³ª´®°íµñ", Font.BOLD, 15));
		Btn2.setBounds(385, 295, 162, 48);
		Btn2.addActionListener(new SwingAction());
		selectFrame.getContentPane().add(Btn2);
		
		JButton Btn3 = new JButton("3\uAC1C");
		Btn3.setFont(new Font("³ª´®°íµñ", Font.BOLD, 15));
		Btn3.setBounds(626, 295, 162, 48);
		Btn3.addActionListener(new SwingAction());
		selectFrame.getContentPane().add(Btn3);
		
		selectFrame.setVisible(true);
	}
	private class SwingAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand()=="1°³") {
				selectFrame.setVisible(false);
				MyThread th=new MyThread(1);
				th.start();
			}
			else if(e.getActionCommand()=="2°³") {
				selectFrame.setVisible(false);
				MyThread2 th=new MyThread2(1);
				th.start();
			}
			else if(e.getActionCommand()=="3°³") {
				selectFrame.setVisible(false);
				MyThread3 th=new MyThread3(1);
				th.start();
			}
		}
	}
}
