package GUI;


import javax.swing.*;


import java.awt.*;
import java.awt.event.*;

public class startFrame {

	private JFrame frame;

	public startFrame() {

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 102, 102));
		frame.setBounds(500, 150, 947, 511);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uD0DC\uD53C\uC0C1\uC810 \uC2DC\uBBAC\uB808\uC774\uC158");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 67));
		lblNewLabel.setBounds(143, 149, 649, 109);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(230, 270, 475, 42);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("\uCEF4\uD4E8\uD130\uACF5\uD559\uACFC | 20180909 | \uC774\uC815\uD604");
		label.setForeground(new Color(255, 102, 102));
		label.setBounds(31, 0, 419, 42);
		label.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 26));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label);
		
		JButton btnStart = new JButton("START");
		btnStart.addActionListener(new SwingAction());
		btnStart.setForeground(new Color(102, 51, 51));
		btnStart.setBackground(new Color(255, 255, 255));
		btnStart.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 20));
		btnStart.setBounds(339, 343, 108, 52);
		frame.getContentPane().add(btnStart);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new SwingAction());
		btnExit.setForeground(new Color(102, 51, 51));
		btnExit.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 20));
		btnExit.setBackground(Color.WHITE);
		btnExit.setBounds(473, 343, 108, 52);
		frame.getContentPane().add(btnExit);

		frame.setVisible(true);
	}

	private class SwingAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand()=="EXIT") {
				System.exit(0);
			}
			else if(e.getActionCommand()=="START") {
				frame.setVisible(false);
				selectService sS=new selectService();
			}
		}
	}
}
