import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PrimaryPanel extends JPanel{
	private GameMenuPanel panel;
	private About aboutPanel;
	private IGame game;
	
	public JButton btnBack,btnAbout, btnExit;
	private ButtonListener btnL;
	private int gameNum,flag;
	private ImageIcon iconBack,iconAbout,iconExit;

	public PrimaryPanel() {
		
		setPreferredSize(new Dimension(1000,800));
		setBackground(new Color(198,140,47));
		setLayout(null);

		btnL = new ButtonListener();

		iconBack = new ImageIcon("image/back.png");

		btnBack = new JButton(iconBack);
		btnBack.setBounds(20,20,70,70);
		btnBack.setBorderPainted(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setFocusPainted(false);

		btnBack.setVisible(false);
		btnBack.addActionListener(btnL);
		add(btnBack);

		iconAbout = new ImageIcon("image/about.png");

		btnAbout = new JButton(iconAbout);
		btnAbout.setBounds(790,30,70,70);
		btnAbout.setBorderPainted(false);
		btnAbout.setContentAreaFilled(false);
		btnAbout.setFocusPainted(false);
		btnAbout.addActionListener(btnL);
		add(btnAbout);

		iconExit = new ImageIcon("image/exit.png");

		btnExit = new JButton(iconExit);
		btnExit.setBounds(880,30,70,70);
		btnExit.setBorderPainted(false);
		btnExit.setContentAreaFilled(false);
		btnExit.setFocusPainted(false);
		btnExit.addActionListener(btnL);
		add(btnExit);

		panel = new GameMenuPanel(this);
		panel.setBounds(0,100,1000,700);
		add(panel);	

		aboutPanel = new About();
		aboutPanel.setBounds(0,100,1000,700);
		aboutPanel.setVisible(false);
		add(aboutPanel);
	} // PrimaryPanel()

	public void addSet(IGame _game,int i){
		game = _game;
		gameNum =i;
		add((JPanel)game);
		btnExit.setVisible(false);
		btnAbout.setVisible(false);
	} //addSet() add Gaeme Panel at PrimaryPanel

	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			Object obj = event.getSource();
			if(obj == btnBack) {
				if(flag==1){
					aboutPanel.setVisible(false);
					btnBack.setVisible(false);
					btnExit.setVisible(true);
					btnAbout.setVisible(true);
					panel.setVisible(true);
					flag=0;
				} // in About Panel  
				else{
					remove((JPanel)game);
					btnBack.setVisible(false);
					btnExit.setVisible(true);
					btnAbout.setVisible(true);
					panel.setVisible(true);
				} // in Game Panel
			}// if 
			else if(obj == btnExit) {
				int result = JOptionPane.showConfirmDialog(null,"Finish?");
                if(result == JOptionPane.YES_OPTION)  System.exit(0);
			}//else if
			else if(obj == btnAbout) {
				 flag=1;
				 aboutPanel.setVisible(true);
				 panel.setVisible(false);
				 btnBack.setVisible(true);
				 btnExit.setVisible(false);
				 btnAbout.setVisible(false);
				 System.out.println("test");
			}//else if
		}//actionPerformed
	}//ButtonListener
}//PrimaryPanel class