// information about us

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class About extends JPanel
{
	// title
	private JPanel titlePanel;
	private JLabel lblTitle;
	// infomation
	private JPanel[] infoPanel;
	private JLabel[] lblSJ, lblJH;

	public About() {

		setBackground(Color.white);
		setLayout(null);

		// title Panel
		titlePanel = new JPanel();
		titlePanel.setBounds(0,0,1000,150);
		titlePanel.setBackground(Color.orange);
		titlePanel.setLayout(null);
		add(titlePanel);

		lblTitle = new JLabel("ABOUT US");
		lblTitle.setBounds(200,20,600,110);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setVerticalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Verdana", Font.BOLD, 70));
		titlePanel.add(lblTitle);

		// infomation Panel
		infoPanel = new JPanel[2];
		for (int i=0; i<2; i++) {
			infoPanel[i] = new JPanel();
			infoPanel[i].setBounds(130+420*i,200,320,450);
			infoPanel[i].setLayout(null);
			add(infoPanel[i]);
		}
		// infomation about JeongHyun
		lblJH = new JLabel[GameConstants.INFO_NUM];
		for (int i=0; i<GameConstants.INFO_NUM; i++) {
			lblJH[i] = new JLabel(GameConstants.JH[i]);
			lblJH[i].setBounds(10,20+50*i,300,50);
			lblJH[i].setHorizontalAlignment(SwingConstants.CENTER);
			lblJH[i].setVerticalAlignment(SwingConstants.CENTER);
			lblJH[i].setFont(new Font("Verdana", Font.BOLD, 20));
			infoPanel[0].add(lblJH[i]);
		}
		// information about SeungJin
		lblSJ = new JLabel[GameConstants.INFO_NUM];
		for (int i=0; i<GameConstants.INFO_NUM; i++) {
			lblSJ[i] = new JLabel(GameConstants.SJ[i]);
			lblSJ[i].setBounds(10,20+50*i,300,50);
			lblSJ[i].setHorizontalAlignment(SwingConstants.CENTER);
			lblSJ[i].setVerticalAlignment(SwingConstants.CENTER);
			lblSJ[i].setFont(new Font("Verdana", Font.BOLD, 20));
			infoPanel[1].add(lblSJ[i]);
		}
	} // About()
} // About class