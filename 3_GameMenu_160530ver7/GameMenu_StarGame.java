// 6. Twinkle Star Game

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class GameMenu_StarGame extends JPanel implements IGame 
{
	// about file
	private BufferedReader result;
	private PrintWriter pw;
	private String resultLine,strWinRatio;

	// message Panel
	private JPanel messagePanel, gamePanel, inputPanel;
	private JLabel message1, message2;
	private int nTwinkle, nX_coord, nY_coord;
	private int nTotal, nWins;
	private double nWinRatio;

	// game Panel
	private ImageIcon start;
	private JButton startBtn;

	// input Panel
	private JTextField inputNum;
	private JButton inputBtn;
	private JLabel lblRange, lblTotal, lblWins;
	// count thread & star thread
	private TwinkleThread lblCount, lblStar;

	// Listeners
	private StartGameListener startGameL;
	private CheckAnswerListener checkAnswerL;

	// constructor
	public GameMenu_StarGame () {

		setBackground(Color.white);
		setBounds(0,100,1000,700);
		setLayout(null);

		startGameL = new StartGameListener();
		checkAnswerL = new CheckAnswerListener();

		// read file and save values
		try   
    	{  
        	result = new BufferedReader(new FileReader("file/TwinkleGameResult.txt"));

        	for (int i=1; i<3; i++) {
        		resultLine = result.readLine();
        		if (resultLine==null) break;

        		switch(i) {
        			case 1:
        				nTotal = Integer.parseInt(resultLine);
        				break;
        			case 2:
        				nWins = Integer.parseInt(resultLine);
        				break;
        		} // switch
        	} // for

        	result.close(); 
    	}  
    	catch (Exception ex)  
    	{  
        	System.out.println(ex.getMessage());  
    	}  

    	// calculate win ratio
    	if (nTotal == 0)
    		nWinRatio = 0;
    	else 
    		nWinRatio = (double)nWins / nTotal;
    	// to show ratio to two decimal places
    	strWinRatio = ""+nWinRatio;
    	strWinRatio = strWinRatio.substring(0,4);

    	// generate twinkle random number & random x, y coordinate
		nTwinkle = (int)(Math.random()*11) + 5;  // 5 - 15
		nX_coord = (int)(Math.random()*660) + 30;
		nY_coord = (int)(Math.random()*490) + 30;

		//
		// messagePanel
		messagePanel = new JPanel();
		messagePanel.setBounds(0,0,1000,150);
		messagePanel.setBackground(Color.orange);
		messagePanel.setLayout(null);
		add(messagePanel);
		// messages
		message1 = new JLabel("Hey~ fine night :) It's Twinkle Star Game ~");
		message1.setBounds(50,25,900,50);
		message1.setHorizontalAlignment(SwingConstants.CENTER);
        message1.setVerticalAlignment(SwingConstants.CENTER);
        message1.setFont(new Font("Verdana", Font.BOLD, 30));
		messagePanel.add(message1);

		message2 = new JLabel("If you want to game start, press START Button");
		message2.setBounds(50,75,900,50);
		message2.setHorizontalAlignment(SwingConstants.CENTER);
        message2.setVerticalAlignment(SwingConstants.CENTER);
        message2.setFont(new Font("Verdana", Font.BOLD, 30));
		messagePanel.add(message2);

		//
		// gamePanel
		gamePanel = new JPanel();
		gamePanel.setBounds(0,150,720,550);
		gamePanel.setBackground(Color.black);
		gamePanel.setLayout(null);
		add(gamePanel);
		// start Button
		start = new ImageIcon("image/start.png");
		startBtn = new JButton(start);
		startBtn.setBounds(250,170,220,210);
		startBtn.setBorderPainted(false);
		startBtn.setContentAreaFilled(false);
		startBtn.setFocusPainted(false);
		startBtn.addActionListener(startGameL);
		gamePanel.add(startBtn);
		// Count down thread
		lblCount = new TwinkleThread(1);  // parameter :: thread case 1
		lblCount.setBounds(260,175,200,200);
		lblCount.setFont(new Font("Verdana",Font.BOLD,120));
		lblCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblCount.setVerticalAlignment(SwingConstants.CENTER);
		lblCount.setForeground(Color.white);
		lblCount.setVisible(false);  // initiate visible state false (non-visible)
		gamePanel.add(lblCount);
		// twinkle star thread
		lblStar = new TwinkleThread(2);   // parameter :: thread case 2
		lblStar.setBounds(nX_coord,nY_coord,30,30);
		lblStar.setFont(new Font("Verdana",Font.BOLD,25));
		lblStar.setHorizontalAlignment(SwingConstants.CENTER);
		lblStar.setVerticalAlignment(SwingConstants.CENTER);
		lblStar.setForeground(Color.white);
		lblStar.setVisible(false);
		gamePanel.add(lblStar);

		//
		// inputPanel
		inputPanel = new JPanel();
		inputPanel.setBounds(720,150,280,550);
		inputPanel.setBackground(Color.pink);
		inputPanel.setLayout(null);
		add(inputPanel);
		// input textfield
		inputNum = new JTextField();
		inputNum.setBounds(10,50,170,40);
		inputNum.setEnabled(false);
		inputNum.setFont(new Font("Verdana",Font.BOLD,16));
		inputNum.addActionListener(checkAnswerL);
		inputPanel.add(inputNum);
		// input button
		inputBtn = new JButton("INPUT");
		inputBtn.setBounds(190,50,80,40);
		inputBtn.setFont(new Font("Verdana",Font.BOLD,16));
		inputBtn.setEnabled(false);
		inputBtn.addActionListener(checkAnswerL);
		inputPanel.add(inputBtn);
		// range label
		lblRange = new JLabel("Range : 5 - 15");
		lblRange.setBounds(20,90,150,40);
		lblRange.setFont(new Font("Verdana",Font.BOLD,16));
		lblRange.setForeground(Color.gray);
		inputPanel.add(lblRange);
		// title
		lblTotal = new JLabel("Total : " + nTotal);
		lblTotal.setBounds(20,340,300,40);
		lblTotal.setFont(new Font("Verdana",Font.BOLD,20));
		inputPanel.add(lblTotal);

		lblWins = new JLabel("Wins : " + nWins + " (" + strWinRatio + "%)");
		lblWins.setBounds(20,380,300,40);
		lblWins.setFont(new Font("Verdana",Font.BOLD,20));
		inputPanel.add(lblWins);

	} // TwinkleGame()

	// to thread run
	public void runThread() {

		lblCount.setVisible(true);
		lblCount.start();
				
		lblStar.setCount(nTwinkle);
		lblStar.setVisible(true);
		lblStar.start();		
	} // runThread()

	// after update values, rewrite values in the txt file
	public void fileRewrite() {

		try {
			PrintWriter pw = new PrintWriter("file/TwinkleGameResult.txt");
       
	        for (int i=1; i<3; i++) {

	        	switch(i) {
	        		case 1:
	       				pw.println(nTotal+"");
	       				break;
	       			case 2:
	       				pw.println(nWins+"");
	       				break;       	
	       		} // switch
	        } // for
	        pw.close();
		}
        catch (Exception ex) {  
        	System.out.println(ex.getMessage());  
    	}
	} // fileRewrite()

	// when pop up ask 'Do you want One more Game?', if you click 'Yes'
	// initialize game setting to after press start button
	public void initTwinkleGame() {

		// before thread set invisible
		lblCount.setVisible(false);
		lblStar.setVisible(false);

		// re-read the file values
		try   
    	{  
        	result = new BufferedReader(new FileReader("file/TwinkleGameResult.txt"));

        	for (int i=1; i<3; i++) {
        		resultLine = result.readLine();
        		if (resultLine==null) break;

        		switch(i) {
        			case 1:
        				nTotal = Integer.parseInt(resultLine);
        				break;
        			case 2:
        				nWins = Integer.parseInt(resultLine);
        				break;
        		} // switch
        	} // for

        	result.close(); 
    	}  
    	catch (Exception ex)  
    	{  
        	System.out.println(ex.getMessage());  
    	}  
    	// calculate win ratio
    	if (nTotal == 0)
    		nWinRatio = 0;
    	else 
    		nWinRatio = (double)nWins / nTotal;
    	// to show ratio to two decimal places
    	strWinRatio = ""+nWinRatio;
    	strWinRatio = strWinRatio.substring(0,4);
    	// generate twinkle random number & random x, y coordinate
		nTwinkle = (int)(Math.random()*11) + 5;  // 5 - 15
		nX_coord = (int)(Math.random()*660) + 30;
		nY_coord = (int)(Math.random()*490) + 30;

		// create new label thread
		lblCount = new TwinkleThread(1);
		lblCount.setBounds(260,175,200,200);
		lblCount.setFont(new Font("Verdana",Font.BOLD,120));
		lblCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblCount.setVerticalAlignment(SwingConstants.CENTER);
		lblCount.setForeground(Color.white);
		lblCount.setVisible(false); 
		gamePanel.add(lblCount);

		lblStar = new TwinkleThread(2);
		lblStar.setBounds(nX_coord,nY_coord,30,30);
		lblStar.setFont(new Font("Verdana",Font.BOLD,25));
		lblStar.setHorizontalAlignment(SwingConstants.CENTER);
		lblStar.setVerticalAlignment(SwingConstants.CENTER);
		lblStar.setForeground(Color.white);
		lblStar.setVisible(false);
		gamePanel.add(lblStar);

		// set messages
		message1.setText("You have to catch STARs, how many times Twinkle");
		message2.setText("Input The NUMBER, you guess~ (5 - 15)");

		lblTotal.setText("Total : " + nTotal);
		lblWins.setText("Wins : " + nWins + " (" + strWinRatio + "%)");

	} // initTwinkleGame()

	// after click 'back' button, when you start again
	// initialize game setting
	public void reset() {

		// to thread stop
		lblCount.stop();
		lblStar.stop();
		initTwinkleGame();
		message1.setText("Hey~ fine night :) It's Twinkle Star Game ~");
		message2.setText("If you want to game start, press START Button");
		startBtn.setVisible(true);
		inputNum.setEnabled(false);
		inputBtn.setEnabled(false);

	} // reset()

	// when you click 'START' Button
	// game start
	private class StartGameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {

			Object obj = event.getSource();

			if (obj == startBtn) {
				startBtn.setVisible(false); // start Button invisible
				// set messages
				message1.setText("You have to catch STARs, how many times Twinkle");
				message2.setText("Input The NUMBER, you guess~ (5 - 15)");
				// after click start button, you can input the value
				inputNum.setEnabled(true);
				inputBtn.setEnabled(true);	

				runThread(); // count down start THEN stars are twinkle
			}
		} // actionPerformed()
	} // StartGameListener class

	// if you input a value, check if it's correct
	private class CheckAnswerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {

			Object obj = event.getSource();
			int nInput;

			// if you press enter button or click input button, after input value
			if (obj == inputNum || obj == inputBtn) {
				nTotal ++; // total game number increase
				nInput = Integer.parseInt(inputNum.getText());  // change value to integer
				// if twinkle times and input number is equal, you win the game
				if (nTwinkle == nInput) {
					nWins ++;  // win number increase
					message1.setText("You Are Right!!!");
					
				} // if you input wrong number, you lose the game
				else {
					message1.setText("Oh, maybe you have to get more Good EYES :(");
				}
				message2.setText("STARS Twinkle  " + nTwinkle + " Times~!");

				inputNum.setText(""); // set textfield clear
				// show pop up
				int nResult = JOptionPane.showConfirmDialog(null, "Do you want One more Game?");  // show pop-up :: asking continue
				// if you click YES,
				if (nResult == JOptionPane.YES_OPTION) {

					fileRewrite();     // update value
					initTwinkleGame(); // init a game
					runThread(); // run thread
				}
			}
		} // actionPerformed()
	} // CheckAnswerListener class

	public void execute(ConsoleGame _game) {
		reset();
		System.out.println("Star GAME");
	}//execute() abstract method
}// GameMenu_StarGame class
