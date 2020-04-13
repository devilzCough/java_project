// 4. Word Game

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class GameMenu_WordGame extends JPanel implements IGame 
{
	// about files
	private BufferedReader wordList, result;
	private PrintWriter pw;
	private int nWord, nPickedWord, nIndex, nTotal, nWins;
	private String listLine, resultLine, strWord,strWinRatio;
	private double nWinRatio;

	// message Panel
	private JPanel messagePanel, gamePanel, statePanel, inputPanel;
	private JLabel message1, message2;

	// Game Panel
	private ImageIcon start;
	private JButton startBtn;
	private int nWordLength, nInterval;
	private JLabel[] lblAlphabet;
	private String inputStr;

	// State Panel
	private ImageIcon tom, jerry;
	private JButton tomBtn, jerryBtn;

	// Input & Result Panel
	private JTextField wordInput;
	private JButton inputBtn;
	private JLabel[] alphabet;
	private int nCount, nWrong, nRight;
	private JLabel lblTotal, lblWins;

	// Listeners
	private StartGameListener startGameL;
	private CheckWordListener checkWordL;

	// constructor
	public GameMenu_WordGame() {

		setBackground(Color.white);
		setBounds(0,100,1000,700);
		setLayout(null);

		startGameL = new StartGameListener();
		checkWordL = new CheckWordListener();

		nWord = 20;  // number of words
		nPickedWord = (int)(Math.random()* nWord) + 1;  // random number for selecting a word
		nIndex = 1;  // to find a word, counting line number

		try   
    	{  
        	wordList = new BufferedReader(new FileReader("file/wordList.txt"));
        	result = new BufferedReader(new FileReader("file/wordGameResult.txt"));

        	// fine a word
        	while (true) {
        		listLine = wordList.readLine();
        		if (listLine==null) break;

        		if (nIndex == nPickedWord) {
        			strWord = listLine;
        			break;
        		}
        		nIndex ++;
        	} // while

        	// read game results
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
        	wordList.close();
        	result.close(); 
    	}  
    	catch (Exception ex)  
    	{  
        	System.out.println(ex.getMessage());  
    	}  


    	nWordLength = strWord.length(); // get word length
    	// calcultate win ratio
    	if (nTotal == 0)
    		nWinRatio = 0;
    	else 
    		nWinRatio = (double)nWins / nTotal;
		// to show ratio to two decimal places
    	strWinRatio = ""+nWinRatio;
    	strWinRatio = strWinRatio.substring(0,4);

    	//
		// messagePanel
		messagePanel = new JPanel();
		messagePanel.setBounds(0,0,1000,150);
		messagePanel.setBackground(Color.orange);
		messagePanel.setLayout(null);
		add(messagePanel);
		// messages
		message1 = new JLabel("Hi~ It's Tom & Jerry Word Game!");
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
		gamePanel.setBounds(0,150,720,350);
		gamePanel.setBackground(Color.white);
		gamePanel.setLayout(null);
		add(gamePanel);
		// start Button
		start = new ImageIcon("image/start.png");
		startBtn = new JButton(start);
		startBtn.setBounds(250,70,220,210);
		startBtn.setBorderPainted(false);
		startBtn.setContentAreaFilled(false);
		startBtn.setFocusPainted(false);
		startBtn.addActionListener(startGameL);
		gamePanel.add(startBtn);
		// to show blank("?"), using wordlength decide labels interval
		nInterval = 660 / nWordLength;
		lblAlphabet = new JLabel[nWordLength];
		for (int i=0; i<nWordLength; i++) {
			lblAlphabet[i] = new JLabel("?");
			lblAlphabet[i].setBounds(30+nInterval*i,100,nInterval-6,nInterval+20);
			lblAlphabet[i].setHorizontalAlignment(SwingConstants.CENTER);
        	lblAlphabet[i].setVerticalAlignment(SwingConstants.TOP);
			lblAlphabet[i].setFont(new Font("Verdana", Font.BOLD, nInterval-20));
			lblAlphabet[i].setVisible(false);
			gamePanel.add(lblAlphabet[i]);
		}

		//
		// statePanel
		statePanel = new JPanel();
		statePanel.setBounds(0,500,720,200);
		statePanel.setBackground(Color.lightGray);
		statePanel.setLayout(null);
		add(statePanel);
		// tom & jerry image
		tom = new ImageIcon("image/tom.png");
		tomBtn = new JButton(tom);
		tomBtn.setBounds(500,20,170,170);
		tomBtn.setBorderPainted(false);
		tomBtn.setContentAreaFilled(false);
		tomBtn.setFocusPainted(false);
		statePanel.add(tomBtn);

		jerry = new ImageIcon("image/jerry.png");
		jerryBtn = new JButton(jerry);
		jerryBtn.setBounds(20,20,150,150);
		jerryBtn.setBorderPainted(false);
		jerryBtn.setContentAreaFilled(false);
		jerryBtn.setFocusPainted(false);
		statePanel.add(jerryBtn);

		// 
		// inputPanel & result Panel
		inputPanel = new JPanel();
		inputPanel.setBounds(720,150,280,550);
		inputPanel.setBackground(Color.pink);
		inputPanel.setLayout(null);
		add(inputPanel);
		// testfield which inputs values
		wordInput = new JTextField();
		wordInput.setBounds(10,50,170,40);
		wordInput.setEnabled(false);  // you can't input a values in textfield, if you don't start game
		wordInput.setFont(new Font("Verdana",Font.BOLD,16));
		wordInput.addActionListener(checkWordL);
		inputPanel.add(wordInput);
		// input button
		inputBtn = new JButton("INPUT");
		inputBtn.setBounds(190,50,80,40);
		inputBtn.setFont(new Font("Verdana",Font.BOLD,16));
		inputBtn.setEnabled(false);// you can't click the input Button, if you don't start game
		inputBtn.addActionListener(checkWordL);
		inputPanel.add(inputBtn);
		// to show Alphabets, A to Z
		int x=0, y=0;
		alphabet = new JLabel[26];
		for (int i=0; i<26; i++) {
			alphabet[i] = new JLabel(GameConstants.ALPHABET[i]);
			if (i%8 == 0) {
				x = 0;
				y ++;
			}
			alphabet[i].setBounds(20+30*x,80+30*y,25,25);
			alphabet[i].setFont(new Font("Verdana",Font.BOLD,20));
			inputPanel.add(alphabet[i]);
			x ++;
		}
		// counting number, how many times you try, how many times you right, how many times you wrong..
		nCount = nRight = nWrong = 0;
		// result title
		lblTotal = new JLabel("Total : " + nTotal);
		lblTotal.setBounds(20,340,300,40);
		lblTotal.setFont(new Font("Verdana",Font.BOLD,20));
		inputPanel.add(lblTotal);

		lblWins = new JLabel("Wins : " + nWins + " (" + strWinRatio + "%)");
		lblWins.setBounds(20,380,300,40);
		lblWins.setFont(new Font("Verdana",Font.BOLD,20));
		inputPanel.add(lblWins);

	} // WordGame()

	// after update values, rewrite values in the txt file
	public void fileRewrite() {

		try {
			PrintWriter pw = new PrintWriter("file/wordGameResult.txt");
       
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
	public void initWordGame() {

		// to Alphabet labels invisible (in Game Panel)
		for (int i=0; i<nWordLength; i++)
			lblAlphabet[i].setVisible(false);

		// select another random number
		nPickedWord = (int)(Math.random()* nWord) + 1;
		nIndex = 1;

		// re-read the file values
		try   
    	{  
        	wordList = new BufferedReader(new FileReader("file/wordList.txt"));
        	result = new BufferedReader(new FileReader("file/wordGameResult.txt"));

        	// find a word
        	while (true) {
        		listLine = wordList.readLine();
        		if (listLine==null) break;

        		if (nIndex == nPickedWord) {
        			strWord = listLine;
        			break;
        		}
        		nIndex ++;
        	} // while

        	// read results
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
        	wordList.close();
        	result.close(); 
    	}  
    	catch (Exception ex)  
    	{  
        	System.out.println(ex.getMessage());  
    	}  

    	nWordLength = strWord.length(); // word length
    	// calculate win ratio
    	if (nTotal == 0)
    		nWinRatio = 0;
    	else 
    		nWinRatio = (double)nWins / nTotal;

    	strWinRatio = ""+nWinRatio;
    	strWinRatio = strWinRatio.substring(0,4);

    	nCount = nRight = nWrong = 0;

    	message1.setText("Word Length is " + nWordLength);
		message2.setText("Input Alphabet or Entire Word!");

		nInterval = 660 / nWordLength;
		lblAlphabet = new JLabel[nWordLength];
		for (int i=0; i<nWordLength; i++) {
			lblAlphabet[i] = new JLabel("?");
			lblAlphabet[i].setBounds(30+nInterval*i,100,nInterval-6,nInterval+20);
			lblAlphabet[i].setHorizontalAlignment(SwingConstants.CENTER);
        	lblAlphabet[i].setVerticalAlignment(SwingConstants.TOP);
			lblAlphabet[i].setFont(new Font("Verdana", Font.BOLD, nInterval-20));
			gamePanel.add(lblAlphabet[i]);
		}

		// alphabet labels Color set black (in inputPanel)
		// because gray color means 'it's already input'
		for (int i=0; i<26; i++)
			alphabet[i].setForeground(Color.black);

		// tom's position has to move original coordinate
		tomBtn.setBounds(500,20,170,170);
		lblTotal.setText("Total : " + nTotal);
		lblWins.setText("Wins : " + nWins + " (" + strWinRatio + "%)");

	} // initWordGame()

	// after click 'back' button, when you start again
	// initialize game setting
	public void reset() {

		initWordGame();
		message1.setText("Hi~ It's Tom & Jerry Word Game!");
		message2.setText("If you want to game start, press START Button");

		startBtn.setVisible(true);
		wordInput.setEnabled(false);
		inputBtn.setEnabled(false);

		for (int i=0; i<nWordLength; i++)
			lblAlphabet[i].setVisible(false);

	} // reset()

	// when you click 'START' Button
	// game start
	private class StartGameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {

			Object obj = event.getSource();

			if (obj == startBtn) {
				// to start Button invisible
				startBtn.setVisible(false);
				// to alphabet labels visible (in gamePanel)
				for (int i=0; i<nWordLength; i++) 
					lblAlphabet[i].setVisible(true);
				// set messages
				message1.setText("Word Length is " + nWordLength);
				message2.setText("Input Alphabet or Entire Word!");
				// now you can put the values in textfield
				wordInput.setEnabled(true);
				inputBtn.setEnabled(true);				
			}
		} // actionPerformed()
	} // StartGameListener class

	// if you input a value, check if it's correct
	private class CheckWordListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {

			Object obj = event.getSource();
			int flag = 0;

			// if you press enter button or click input button, after input value
			if (obj == wordInput || obj == inputBtn) {
				// get value in textfield
				inputStr = wordInput.getText();
				// increase try number
				nCount ++;

				// change alphabet color to gray, which is input
				for (int i=0; i<26; i++) {
					if (GameConstants.ALPHABET[i].equals(inputStr.toUpperCase()))
						alphabet[i].setForeground(Color.lightGray);
				}

				// if you input a alphabet, check alphabet in the word
				if (inputStr.length() == 1) {
					message1.setText("Oh~ You input Alphabet " + "\'" + inputStr.toUpperCase() + "\'");
					// find alphabet in the word
					for (int i=0; i<nWordLength; i++) {
						if (strWord.substring(i, i+1).equals(inputStr)) {
							flag = 1;
							nRight++;
							lblAlphabet[i].setText(inputStr);
							message2.setText("\'" + inputStr.toUpperCase() + "\'" + " is in the Word!");
						} else {
							// because all alphabets are checked, I have a flag
							// if it's correct before or is completely incorrect
							if (flag == 1) 
								message2.setText("\'" + inputStr.toUpperCase() + "\'" + " is in the Word!");
							else 
								message2.setText("\'" + inputStr.toUpperCase() + "\'" + " is not in the Word :(");
						}
					} // for
					if (flag == 1) {
						nRight ++;
					}
					else {
						nWrong++;
						// if you wrong, tom move to front
						tomBtn.setBounds(500-35*nWrong,20,170,170);
					}

				} // if you input a word, check if the word is correct
				else {
					message1.setText("Oh~ You input a Word " + inputStr.toUpperCase());
					// if input word is correct
					if(strWord.equals(inputStr)) {
						for (int i=0; i<nWordLength; i++) 
							lblAlphabet[i].setText(strWord.substring(i,i+1));
						
						flag = 1;
						nRight = nWordLength;  // it means all blanks is fulled
						message2.setText("You Are Right!!!");

					} // if input word is incorrect
					else {
						if (flag == 1) { 
							message2.setText("You Are Right!!!");
						}
						// if it's incorrect
						else {
							message2.setText("Maybe try another Word :(");
							nWrong ++;
							tomBtn.setBounds(500-35*nWrong,20,170,170);
						}
					}
				} // if-else

				wordInput.setText("");  // to textfield clear

				// check if all blanks is changed
				int nCheck = 0;  // flag
				// if any label do not changed, change nCheck value to 1
				// if nCheck value is 0 until for loop end, it means all alphabets is changed
				for (int i=0; i<nWordLength; i++) {
					if (lblAlphabet[i].getText().equals("?"))
						nCheck = 1;
				}

				// if you try 15 times, you lose the game
				if (nCount == 15) {
					message1.setText("Tom has caught Jerry.. You Lose the Game :(");
					message2.setText("The Answer is " + strWord.toUpperCase());
					nTotal ++; // total game number increase
					// show pop up
					int nResult = JOptionPane.showConfirmDialog(null, "Do you want One more Game?");  // show pop-up :: asking continue
					// if you click YES,
					if (nResult == JOptionPane.YES_OPTION) {

						fileRewrite();   // update values
						initWordGame();  // init a game
					}

				} // if all alphabets is changed, you win the game
				else if (nCheck == 0) {
					message1.setText("You Win the Game!!!");
					message2.setText("You got an Answer in " + nCount + " Times!!");
					// increase total game number and win number
					nTotal ++;
					nWins ++;
					// show pop up
					int nResult = JOptionPane.showConfirmDialog(null, "Do you want One more Game?");  // show pop-up :: asking continue
					// if you click YES,
					if (nResult == JOptionPane.YES_OPTION) {

						fileRewrite();   // update values
						initWordGame();  // init a game
					}
				}
			} // if
		} // actionPerformed()
	} // CheckWordListener class

	public void execute(ConsoleGame _game) {
		reset();
		System.out.println("WORD GAME");
	}//execute() abstract method
}//GameMenu_WordGame class
