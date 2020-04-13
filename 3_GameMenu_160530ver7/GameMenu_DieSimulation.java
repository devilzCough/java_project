import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class GameMenu_DieSimulation extends JPanel implements IGame {
	
	private BufferedReader ranking;
	private PrintWriter pw;
	private String line;
	private int[] nRank;

	private int nSum, nCount, nRandomSum, nFaceValue,nInputCount;
	private int nScore;
	
	private JPanel topPanel,bottomPanel,leftPanel,rightPanel;
	private JLabel lblDieImage;
	private ImageIcon iconDie,iconStart;
	private JLabel lblMessage1,lblMessage2,lblMessage3;
	private JLabel[] lblRank, lblScore;
	
	private JButton btnStart,btnInput;
	private JTextField txtInput;
	private ButtonListener btnL;


	public GameMenu_DieSimulation() {
		setBackground(Color.white);
		setBounds(0,100,1000,700);
		setLayout(null);

		nRank = new int[5];

		try   
    	{  
        	ranking = new BufferedReader(new FileReader("file/DiceSimulationRank.txt"));  // file/DiceSimulationRank.txt
        	for (int i=1; i<6; i++) {
        		line = ranking.readLine();
        		if (line==null) break;

        		switch(i-1) {
        			case 0: case 1: case 2: case 3: case 4:
        				nRank[i-1] = Integer.parseInt(line);
        				break;
        		} // switch
        	} // for
        	ranking.close();
    	}  //try
    	catch (Exception ex)  
    	{  
        	System.out.println(ex.getMessage());  
    	}  //catch

		nSum = nCount = 0;
		nRandomSum = 0;
		nFaceValue = 1;
		nScore = 0;
		nInputCount = 0;
		// init
		
		btnL = new ButtonListener();

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
		leftPanel.setBackground(Color.black);
		leftPanel.setLayout(null);
		leftPanel.setBounds(0,150,650,400);
		add(leftPanel);

		rightPanel = new JPanel();
		rightPanel.setBounds(650,150,350,400);
		rightPanel.setBackground(Color.pink);
		rightPanel.setLayout(null);
		add(rightPanel);

		iconStart = new ImageIcon("image/start.png");
		btnStart = new JButton(iconStart);
		btnStart.setBounds(200,100,220,210); 
		
		btnStart.setBorderPainted(false);
		btnStart.setContentAreaFilled(false);
		btnStart.setFocusPainted(false);
		
		btnStart.addActionListener(btnL); 
		leftPanel.add(btnStart); 

		iconDie = new ImageIcon("image/die.gif");
		lblDieImage = new JLabel(iconDie);
		lblDieImage.setBounds(150,150,300,210);
		lblDieImage.setVisible(false);
		leftPanel.add(lblDieImage);


		lblMessage1 = new JLabel("Dice Simulation");
		lblMessage1.setBounds(10,30,1000,40);
		lblMessage1.setFont(new Font("Verdena",Font.BOLD,30));
		lblMessage1.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage1.setVerticalAlignment(SwingConstants.CENTER);
		lblMessage1.setForeground(Color.black);
		topPanel.add(lblMessage1);

		lblMessage2 = new JLabel("If you press START, it makes Random Number !");
		lblMessage2.setBounds(10,80,1000,40);
		lblMessage2.setFont(new Font("Verdena",Font.BOLD,30));
		lblMessage2.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage2.setVerticalAlignment(SwingConstants.CENTER);
		lblMessage2.setForeground(Color.black);
		topPanel.add(lblMessage2);

		lblMessage3 = new JLabel("Random Number  :  ?  ");
		lblMessage3.setBounds(10,80,300,40);
		lblMessage3.setFont(new Font("Verdena",Font.BOLD,25));
		lblMessage3.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage3.setVerticalAlignment(SwingConstants.CENTER);
		lblMessage3.setForeground(Color.black);
		rightPanel.add(lblMessage3);

		txtInput = new JTextField();
		txtInput.setBounds(50,150,100,40);
		txtInput.setFont(new Font("Verdana",Font.BOLD,16));
		txtInput.setEnabled(false);
		txtInput.addActionListener(btnL);
		rightPanel.add(txtInput);

		btnInput = new JButton("INPUT");
		btnInput.setBounds(180,150,100,40);
		btnInput.setFont(new Font("Verdana",Font.BOLD,16));
		btnInput.setEnabled(false);
		btnInput.addActionListener(btnL);
		rightPanel.add(btnInput);

		lblRank = new JLabel[5];
		for (int i=0; i<5; i++) {
			lblRank[i] = new JLabel("Ranking " + (i+1));
			lblRank[i].setBounds(70+170*i,20,150,50);
			lblRank[i].setHorizontalAlignment(SwingConstants.CENTER);
			lblRank[i].setVerticalAlignment(SwingConstants.CENTER);
			lblRank[i].setFont(new Font("Verdena",Font.BOLD,25));
			bottomPanel.add(lblRank[i]);
		} //for
	
		lblScore = new JLabel[5];
		for (int i=0; i<5; i++) {
			lblScore[i] = new JLabel(nRank[i]+"");
			lblScore[i].setBounds(70+170*i,70,150,50);
			lblScore[i].setHorizontalAlignment(SwingConstants.CENTER);
			lblScore[i].setVerticalAlignment(SwingConstants.CENTER);
			lblScore[i].setFont(new Font("Verdena",Font.BOLD,20));
			lblScore[i].setForeground(Color.red);
			bottomPanel.add(lblScore[i]);
		}//for
		
	}//GameMenu_DieSimulation() 

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event)
		{
			Object obj = event.getSource();
			if(obj == btnStart)
			{
				initDie();
				rollDie();

				btnStart.setVisible(false);
				lblDieImage.setVisible(true);
				txtInput.setEnabled(true);
				btnInput.setEnabled(true);
				lblMessage3.setText("Random Number : "+nRandomSum);
				lblMessage1.setText("How many times you should roll a dice");
				lblMessage2.setText("for makes sum to Random Number? ");

			}
			else if(obj == txtInput || obj == btnInput)
			{
				nInputCount = Integer.parseInt(txtInput.getText());
				txtInput.setText("");
				rank();
			} // if/else if
		} // actionPeformed()
	}// ButtonListener()
	
	private void initDie() {
		nRandomSum = (int)(Math.random()*900) + 100; // 100-999
	} //initDie()
	
	private void rollDie() {
		for (int i=0; i<100000; i++) {
			while(nSum<nRandomSum) {
				nFaceValue = (int)(Math.random()*6) + 1;
				nSum += nFaceValue;
				nCount++;
			} //while
		} //for
	} //rollDie() 

	private void rank() {
		int result = Math.abs(nCount-nInputCount);

		switch(result) {
			case 0:
				nScore += 100;
				break;
			case 1: case 2: case 3: case 4: case 5:
				nScore += 50;
				break;
			case 6: case 7: case 8: case 9: case 10:
				nScore += 10;
				break;
			default:
				nScore += 1;
				break;
		} //switch
		lblMessage1.setText("Answer is "+nCount);
		lblMessage2.setText("Your score is "+nScore);

		int temp = 0;
		// ranking update
		for (int i=0; i<5; i++) {
			if (nScore >= nRank[i]) {
				temp = nRank[i];
				nRank[i] = nScore;
				nScore = temp;
			}//if
		}//for

		int response = JOptionPane.showConfirmDialog(null,"Continue?"); // "Continue ?" 라는 대화상자를 띄워 결과를 정수값으로 받음 
		if(response == JOptionPane.YES_OPTION) {
			fileRewrite();
			reset();
		} //if
	} //rank()

	public void fileRewrite() {
		try {
			PrintWriter pw = new PrintWriter("file/DiceSimulationRank.txt"); //file/DiceSimulationRank.txt
       
	        for (int i=1; i<6; i++) {

	        	switch(i-1) {
	        		case 0: case 1: case 2: case 3: case 4:
	       				pw.println(nRank[i-1]+"");
	       				break;       	
	       		} // switch
	        } // for
	        	pw.close();
		} //try
        catch (Exception ex) {  
        	System.out.println(ex.getMessage());  
    	} //catch
	} // fileRewrite() write score

	public void reset()
	{
		try   
    	{  
        	ranking = new BufferedReader(new FileReader("file/DiceSimulationRank.txt"));  // file/DiceSimulationRank.txt
        	for (int i=1; i<6; i++) {
        		line = ranking.readLine();
        		if (line==null) break;

        		switch(i-1) {
        			case 0: case 1: case 2: case 3: case 4:
        				nRank[i-1] = Integer.parseInt(line);
        				break;
        		} // switch
        	} // for
        	ranking.close();
    	}  //try
    	catch (Exception ex)  
    	{  
        	System.out.println(ex.getMessage());  
    	}//catch
    	
    	nSum=nCount=nScore=0;
		lblDieImage.setVisible(false);
		btnStart.setVisible(true);
		txtInput.setText("");
		txtInput.setEnabled(false);
		btnInput.setEnabled(false);
		lblMessage1.setText("Dice Simulation");
		lblMessage2.setText("If you press START, it makes Random Number !");
		lblMessage3.setText("Random Number : ? ");

		// setting reset

		for (int i=0; i<5; i++) 
			lblScore[i].setText(""+nRank[i]);
	} //reset()
	
	public void execute(ConsoleGame _game) {
		
		reset();
		System.out.println("DICE SIMULATION");
	}// execute() abstract method 
	
}// GameMenu_DieSimulation class()
