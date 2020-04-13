import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameMenu_OXGame extends JPanel implements IGame {

	private JPanel topPanel,bottomPanel,leftPanel,middlePanel,rightPanel,coverPanel;
	private JLabel lblMessage1,lblMessage2;
	private JLabel lblTurn;
	private JButton[] btnNumber;
	private JButton btnStart;
	private ButtonListener btnL;
	private int flag,count;
	private char[] strBtnNumber;
	private ImageIcon iconStart;


	public GameMenu_OXGame()
	{
		setBounds(0,100,1000,700); 
		setBackground(Color.white); 
		setLayout(null); 
		
		btnL = new ButtonListener();
		flag = 0;
		count = 0;

		topPanel = new JPanel();
		topPanel.setBackground(Color.orange);
		topPanel.setLayout(null);
		topPanel.setBounds(0,0,1000,150);
		add(topPanel);

		bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.darkGray);
		bottomPanel.setLayout(null);
		bottomPanel.setBounds(0,550,1000,150);
		add(bottomPanel);

		leftPanel = new JPanel();
		leftPanel.setBackground(Color.white);
		leftPanel.setLayout(null);
		leftPanel.setBounds(0,150,300,400);
		leftPanel.setVisible(false);
		add(leftPanel);

		middlePanel = new JPanel();
		middlePanel.setBackground(Color.lightGray);
		middlePanel.setLayout(new GridLayout (3,3));
		middlePanel.setBounds(300,150,400,400);
		middlePanel.setVisible(false);
		add(middlePanel);

		rightPanel = new JPanel();
		rightPanel.setBackground(Color.white);
		rightPanel.setLayout(null);
		rightPanel.setBounds(700,150,300,400);
		rightPanel.setVisible(false);
		add(rightPanel);

		coverPanel = new JPanel();
		coverPanel.setBackground(Color.white);
		coverPanel.setLayout(null);
		coverPanel.setBounds(0,150,1000,400);
		add(coverPanel);

		iconStart = new ImageIcon("image/start.png");
		btnStart = new JButton(iconStart);
		btnStart.setBounds(400,100,220,210); 
		
		btnStart.setBorderPainted(false);
		btnStart.setContentAreaFilled(false);
		btnStart.setFocusPainted(false);
		btnStart.addActionListener(btnL); 
		coverPanel.add(btnStart); 

		lblTurn = new JLabel("O's turn >> ");
		lblTurn.setBounds(50,50,250,50);
		lblTurn.setFont(new Font("Verdena",Font.BOLD,30));
		lblTurn.setForeground(Color.blue);
		leftPanel.add(lblTurn);

		lblMessage1 = new JLabel("OX Three Bingo Game");
		lblMessage1.setBounds(10,30,1000,40);
		lblMessage1.setFont(new Font("Verdena",Font.BOLD,30));
		lblMessage1.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage1.setVerticalAlignment(SwingConstants.CENTER);
		lblMessage1.setForeground(Color.black);
		topPanel.add(lblMessage1);

		lblMessage2 = new JLabel("If you want to start, press START button !");
		lblMessage2.setBounds(10,80,1000,40);
		lblMessage2.setFont(new Font("Verdena",Font.BOLD,30));
		lblMessage2.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage2.setVerticalAlignment(SwingConstants.CENTER);
		lblMessage2.setForeground(Color.black);
		topPanel.add(lblMessage2);


		btnNumber = new JButton[10];
		strBtnNumber = new char[10];

		for(int i=1 ;i<10;i++){
			btnNumber[i] = new JButton(""+i);
			btnNumber[i].setFont(new Font("Verdena",Font.BOLD,30));
			btnNumber[i].addActionListener(btnL);
			middlePanel.add(btnNumber[i]);
		} //for()
	} // GameMenu_OXGame()

	public void copyStr(){

		for(int i=1;i<10;i++)	strBtnNumber[i]=btnNumber[i].getText().charAt(0);
	} //copyStr() copy button data 

	public void finish(){
		for(int i=1;i<10;i++)	btnNumber[i].setEnabled(false);
	
		lblTurn.setVisible(false);	
		int result = JOptionPane.showConfirmDialog(null,"Continue?"); // "Continue ?" 라는 대화상자를 띄워 결과를 정수값으로 받음 
		if(result == JOptionPane.YES_OPTION)	reset(); 
	} //finish() 

	public char winCheck(){    
		copyStr();
		
		char cReturn = '\0';   

		if (strBtnNumber[1]==strBtnNumber[2] && strBtnNumber[2]==strBtnNumber[3])
			cReturn = strBtnNumber[1];
		else if (strBtnNumber[4]==strBtnNumber[5] && strBtnNumber[5]==strBtnNumber[6])
			cReturn = strBtnNumber[4];
		else if (strBtnNumber[7]==strBtnNumber[8] && strBtnNumber[8]==strBtnNumber[9])
			cReturn = strBtnNumber[7];
		else if (strBtnNumber[1]==strBtnNumber[4] && strBtnNumber[4]==strBtnNumber[7])
			cReturn = strBtnNumber[1];
		else if (strBtnNumber[2]==strBtnNumber[5] && strBtnNumber[5]==strBtnNumber[8])
			cReturn = strBtnNumber[2];
		else if (strBtnNumber[3]==strBtnNumber[6] && strBtnNumber[6]==strBtnNumber[9])
			cReturn = strBtnNumber[3];
		else if (strBtnNumber[1]==strBtnNumber[5] && strBtnNumber[5]==strBtnNumber[9])
			cReturn = strBtnNumber[1];
		else if (strBtnNumber[3]==strBtnNumber[5] && strBtnNumber[5]==strBtnNumber[7])
			cReturn = strBtnNumber[3];

		return cReturn;       

	} // winCheck()


	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event)
		{
			Object obj = event.getSource();
			
			for(int i=1; i<10;i++){
				if(obj==btnNumber[i]){
					if(btnNumber[i].getText().equals("X") == true || btnNumber[i].getText().equals("O") == true){
						lblMessage2.setText("It has been already chosen !");
					} //if
					else if(flag==0 ){
						btnNumber[i].setText("O");
						lblTurn.setText("<< X's turn");
						lblTurn.setForeground(Color.red);
						lblMessage2.setText("If you want to start, press START button !");
						rightPanel.add(lblTurn);
						count++;
						flag =1;
						repaint();
					}//else if

					else if(flag==1 ){
						btnNumber[i].setText("X");
						lblTurn.setText("O's turn >> ");
						lblTurn.setForeground(Color.blue);
						lblMessage2.setText("If you want to start, press START button !");
						leftPanel.add(lblTurn);
						count++;
						flag = 0;
						repaint();
					} //else if 
				} //if
				else if(obj == btnStart)
				{
					coverPanel.setVisible(false);
					leftPanel.setVisible(true);
					middlePanel.setVisible(true);
					rightPanel.setVisible(true);
				}//else if
			}//for
		
			char cWin = winCheck();

			if (cWin=='O' || cWin=='X') {
				lblMessage2.setText(""+cWin+"'s Win!");
				finish();
			} // 'O' or 'X' win
			else if (count==9) {
				lblMessage2.setText("End in a tie !");
				finish();
			} //tie
		}//actionPeformed()
	}// ButtonListener 

	public void reset(){
		coverPanel.setVisible(true);
		leftPanel.setVisible(false);
		middlePanel.setVisible(false);
		rightPanel.setVisible(false);
		
		for(int i=1;i<10;i++){
			btnNumber[i].setEnabled(true);
			btnNumber[i].setText(""+i);
		}
			count = 0;
			lblTurn.setVisible(true);
			lblTurn.setText("O's turn >> ");
			leftPanel.add(lblTurn);
			lblMessage2.setText("If you want to start, press START button !");
	} //reset()
	public void execute(ConsoleGame _game){
		System.out.println("OXGame");
		reset();
	}//execute() abstract method
}//GameMenu_OXGame class
