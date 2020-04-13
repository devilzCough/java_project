// 2. MontyHall Game Show

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class GameMenu_GameShow extends JPanel implements IGame 
{
	// about file
	private BufferedReader br;
	private PrintWriter pw;
	private String line; 
	private int nTotal, nTotalWin, nChoice, nChoiceWins, nSwitch, nSwitchWins;

	// message Panel
	private JPanel messagePanel, gamePanel, resultPanel;
	private JLabel message1, message2;

	// game Panel
	private int nCar, nPick, nOpen, nChange;
	private ImageIcon start;
	private JButton startBtn, yesBtn, noBtn;
	private ImageIcon[] door;
	private JButton[] doorBtn;
	private ImageIcon goat, car;

	// result Panel
	private JLabel lbTotal, lbChoice, lbSwitch;
	private JLabel lbnTotal, lbnChoiceWins, lbnSwitchWins;
	private JLabel lbTotalRatio, lbChoiceRatio, lbSwitchRatio;
	private double nTotalRatio, nChoiceRatio, nSwitchRatio;
	private String strTotalRatio, strChoiceRatio, strSwitchRatio;

	// Listeners
	private StartGameListener startGameL;
	private PickTheDoorListener pickTheDoorL;
	private ChangeTheDoorListener changeTheDoorL;

	// constructor
	public GameMenu_GameShow() {

		setBackground(Color.white);
		setBounds(0,100,1000,700);
		setLayout(null);

		startGameL = new StartGameListener();
		pickTheDoorL = new PickTheDoorListener();
		changeTheDoorL = new ChangeTheDoorListener();

		// read file and save values
		try   
    	{  
        	br = new BufferedReader(new FileReader("file/montyHallResult.txt"));
        	for (int i=1; i<7; i++) {
        		line = br.readLine();
        		if (line==null) break;

        		switch(i) {
        			case 1:
        				nTotal = Integer.parseInt(line);
        				break;
        			case 2:
        				nTotalWin = Integer.parseInt(line);
        				break;
        			case 3:
        				nChoice = Integer.parseInt(line);
        				break;
        			case 4:
        				nChoiceWins = Integer.parseInt(line);
        				break;
        			case 5:
        				nSwitch = Integer.parseInt(line);
        				break;
        			case 6:
        				nSwitchWins = Integer.parseInt(line);
        				break;
        		} // switch
        	} // for
        	br.close();
    	}  
    	catch (Exception ex)  
    	{  
        	System.out.println(ex.getMessage());  
    	}  

    	// calculate win ratio
    	if (nTotal == 0)
    		nTotalRatio = 0;
    	else 
    		nTotalRatio = (double)nTotalWin / nTotal;

    	if (nChoice == 0)
    		nChoiceRatio = 0;
    	else
    		nChoiceRatio = (double)nChoiceWins / nChoice;

    	if (nSwitch == 0)
    		nSwitchRatio = 0;
    	else
    		nSwitchRatio = (double)nSwitchWins / nSwitch;

    	// to show ratio to two decimal places
    	strTotalRatio  = "" + nTotalRatio;
    	strChoiceRatio = "" + nChoiceRatio;
    	strSwitchRatio = "" + nSwitchRatio;
    	strTotalRatio = strTotalRatio.substring(0,4);
    	strChoiceRatio =strChoiceRatio.substring(0,4);
    	strSwitchRatio = strSwitchRatio.substring(0,4);

    	//
		// message Panel
		messagePanel = new JPanel();
		messagePanel.setBounds(0,0,1000,150);
		messagePanel.setBackground(Color.orange);
		messagePanel.setLayout(null);
		add(messagePanel);
		// messages
		message1 = new JLabel("Welcome to the Monty Hall Game Show!");
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
		// game Panel
		gamePanel = new JPanel();
		gamePanel.setBounds(0,150,1000,400);
		gamePanel.setBackground(Color.white);
		gamePanel.setLayout(null);
		add(gamePanel);
		// start Button
		start = new ImageIcon("image/start.png");
		startBtn = new JButton(start);
		startBtn.setBounds(400,100,220,210);
		startBtn.setBorderPainted(false);
		startBtn.setContentAreaFilled(false);
		startBtn.setFocusPainted(false);
		startBtn.addActionListener(startGameL);
		gamePanel.add(startBtn);
		// door image
		door = new ImageIcon[3];
		doorBtn = new JButton[3];
		for (int i=0; i<3; i++) {
			door[i] = new ImageIcon("image/door"+(i+1)+".png");
			doorBtn[i] = new JButton(door[i]);
			doorBtn[i].setBounds(170+(245*i),75,170,250);
			doorBtn[i].setBorderPainted(false);
			doorBtn[i].setContentAreaFilled(false);
			doorBtn[i].setFocusPainted(false);
			// 170 170 75 170 75 170 170
			doorBtn[i].setVisible(false);
			doorBtn[i].addActionListener(pickTheDoorL);
			gamePanel.add(doorBtn[i]);
		}

		// goat & car image icon
		goat = new ImageIcon("image/goat.png");
		car = new ImageIcon("image/car.png");

		// yes & no Button
		yesBtn = new JButton("YES!");
		yesBtn.setBounds(35,160,100,80);
		yesBtn.setFont(new Font("Verdana", Font.BOLD, 20));
		yesBtn.setForeground(Color.red);
		yesBtn.setVisible(false);
		yesBtn.addActionListener(changeTheDoorL);
		gamePanel.add(yesBtn);

		noBtn = new JButton("NO~");
		noBtn.setBounds(865,160,100,80);
		noBtn.setFont(new Font("Verdana", Font.BOLD, 20));
		noBtn.setForeground(Color.blue);
		noBtn.setVisible(false);
		noBtn.addActionListener(changeTheDoorL);
		gamePanel.add(noBtn);

		//
		// result Panel
		resultPanel = new JPanel();
		resultPanel.setBounds(0,550,1000,150);
		resultPanel.setBackground(Color.darkGray);
		resultPanel.setLayout(null);
		add(resultPanel);

		// title
		lbTotal = new JLabel("Total Wins/Total Game");
		lbTotal.setBounds(30,20,300,30);
		lbTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lbTotal.setVerticalAlignment(SwingConstants.CENTER);
		lbTotal.setFont(new Font("Verdana", Font.BOLD, 20));
		resultPanel.add(lbTotal);

		lbChoice = new JLabel("First Choice Wins");
		lbChoice.setBounds(30,60,300,30);
		lbChoice.setHorizontalAlignment(SwingConstants.RIGHT);
		lbChoice.setVerticalAlignment(SwingConstants.CENTER);
		lbChoice.setFont(new Font("Verdana", Font.BOLD, 20));
		resultPanel.add(lbChoice);

		lbSwitch = new JLabel("Switch Wins");
		lbSwitch.setBounds(30,100,300,30);
		lbSwitch.setHorizontalAlignment(SwingConstants.RIGHT);
		lbSwitch.setVerticalAlignment(SwingConstants.CENTER);
		lbSwitch.setFont(new Font("Verdana", Font.BOLD, 20));
		resultPanel.add(lbSwitch);

		// value
		lbnTotal = new JLabel(nTotalWin + " / " + nTotal);
		lbnTotal.setBounds(450,20,120,30);
		lbnTotal.setHorizontalAlignment(SwingConstants.CENTER);
		lbnTotal.setVerticalAlignment(SwingConstants.CENTER);
		lbnTotal.setFont(new Font("Verdana", Font.BOLD, 20));
		resultPanel.add(lbnTotal);

		lbnChoiceWins = new JLabel(nChoiceWins + " / " + nChoice);
		lbnChoiceWins.setBounds(450,60,120,30);
		lbnChoiceWins.setHorizontalAlignment(SwingConstants.CENTER);
		lbnChoiceWins.setVerticalAlignment(SwingConstants.CENTER);
		lbnChoiceWins.setFont(new Font("Verdana", Font.BOLD, 20));
		resultPanel.add(lbnChoiceWins);

		lbnSwitchWins = new JLabel(nSwitchWins + " / " + nSwitch);
		lbnSwitchWins.setBounds(450,100,120,30);
		lbnSwitchWins.setHorizontalAlignment(SwingConstants.CENTER);
		lbnSwitchWins.setVerticalAlignment(SwingConstants.CENTER);
		lbnSwitchWins.setFont(new Font("Verdana", Font.BOLD, 20));
		resultPanel.add(lbnSwitchWins);

		// ratio
		lbTotalRatio = new JLabel(strTotalRatio+ " %");
		lbTotalRatio.setBounds(650,20,100,30);
		lbTotalRatio.setHorizontalAlignment(SwingConstants.CENTER);
		lbTotalRatio.setVerticalAlignment(SwingConstants.CENTER);
		lbTotalRatio.setFont(new Font("Verdana", Font.BOLD, 20));
		resultPanel.add(lbTotalRatio);

		lbChoiceRatio = new JLabel(strChoiceRatio+" %");
		lbChoiceRatio.setBounds(650,60,100,30);
		lbChoiceRatio.setHorizontalAlignment(SwingConstants.CENTER);
		lbChoiceRatio.setVerticalAlignment(SwingConstants.CENTER);
		lbChoiceRatio.setFont(new Font("Verdana", Font.BOLD, 20));
		resultPanel.add(lbChoiceRatio);

		lbSwitchRatio = new JLabel(strSwitchRatio + " %");
		lbSwitchRatio.setBounds(650,100,100,30);
		lbSwitchRatio.setHorizontalAlignment(SwingConstants.CENTER);
		lbSwitchRatio.setVerticalAlignment(SwingConstants.CENTER);
		lbSwitchRatio.setFont(new Font("Verdana", Font.BOLD, 20));
		resultPanel.add(lbSwitchRatio);

	} // GameShow()

	// after update values, rewrite values in the txt file
	public void fileRewrite() {

		try {
			PrintWriter pw = new PrintWriter("file/montyHallResult.txt");
       
	        for (int i=1; i<7; i++) {

	        	switch(i) {
	        		case 1:
	       				pw.println(nTotal+"");
	       				break;
	       			case 2:
	       				pw.println(nTotalWin+"");
	       				break;
	       			case 3:
	       				pw.println(nChoice+"");
	       				break;
	       			case 4:
	       				pw.println(nChoiceWins+"");
	       				break;
	       			case 5:
	       				pw.println(nSwitch+"");
	       				break;
	       			case 6:
	       				pw.println(nSwitchWins+"");
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
	public void initMontyHallGame() {

		// re-read the file values
		try   
    	{  
        	BufferedReader br = new BufferedReader(new FileReader("file/montyHallResult.txt"));
        	for (int i=1; i<7; i++) {
        		String line = br.readLine();
        		if (line==null) break;

        		switch(i) {
        			case 1:
        				nTotal = Integer.parseInt(line);
        				break;
        			case 2:
        				nTotalWin = Integer.parseInt(line);
        				break;
        			case 3:
        				nChoice = Integer.parseInt(line);
        				break;
        			case 4:
        				nChoiceWins = Integer.parseInt(line);
        				break;
        			case 5:
        				nSwitch = Integer.parseInt(line);
        				break;
        			case 6:
        				nSwitchWins = Integer.parseInt(line);
        				break;
        		} // switch
        	} // for
        	br.close(); 
        	// pw.close();
    	}  
    	catch (Exception ex)  
    	{  
        	System.out.println(ex.getMessage());  
    	}  

    	// calculate win ratio
    	if (nTotal == 0)
    		nTotalRatio = 0;
    	else 
    		nTotalRatio = (double)nTotalWin / nTotal;

    	if (nChoice == 0)
    		nChoiceRatio = 0;
    	else
    		nChoiceRatio = (double)nChoiceWins / nChoice;

    	if (nSwitch == 0)
    		nSwitchRatio = 0;
    	else
    		nSwitchRatio = (double)nSwitchWins / nSwitch;

    	strTotalRatio  = "" + nTotalRatio;
    	strChoiceRatio = "" + nChoiceRatio;
    	strSwitchRatio = "" + nSwitchRatio;

    	strTotalRatio = strTotalRatio.substring(0,4);
    	strChoiceRatio =strChoiceRatio.substring(0,4);
    	strSwitchRatio = strSwitchRatio.substring(0,4);

    	// to door Button invisible
		for (int i=0; i<3; i++)
			doorBtn[i].setVisible(false);

		// init message
		message1.setText("Behind one door is a Car, behind the others, Goats");
		message2.setText("You pick a Door!!");

		// generate random door number
		nCar = (int)(Math.random()*3) + 1; // 1 - 3
		nOpen = (int)(Math.random()*3) + 1;

		// create door button again
		for (int i=0; i<3; i++) {
			door[i] = new ImageIcon("image/door"+(i+1)+".png");
			doorBtn[i] = new JButton(door[i]);
			doorBtn[i].setBounds(170+(245*i),75,170,250);
			doorBtn[i].setBorderPainted(false);
			doorBtn[i].setContentAreaFilled(false);
			doorBtn[i].setFocusPainted(false);
			doorBtn[i].addActionListener(pickTheDoorL);
			gamePanel.add(doorBtn[i]);
		}

		// show updated result
		lbnTotal.setText(nTotalWin + " / " + nTotal);
		lbnChoiceWins.setText(nChoiceWins + " / " + nChoice);
		lbnSwitchWins.setText(nSwitchWins + " / " + nSwitch);

		lbTotalRatio.setText(strTotalRatio + " %");
		lbChoiceRatio.setText(strChoiceRatio+ " %");
		lbSwitchRatio.setText(strSwitchRatio+ " %");

	} // initMontyHallGame()

	// after click 'back' button, when you start again
	// initialize game setting
	public void reset() {

		initMontyHallGame();

		message1.setText("Welcome to the Monty Hall Game Show!");
		message2.setText("If you want to game start, press START Button");

		startBtn.setVisible(true);
		yesBtn.setVisible(false);
		noBtn.setVisible(false);
		
		for (int i=0; i<3; i++)
			doorBtn[i].setVisible(false);

	} // reset()

	// when you click 'START' Button
	// game start
	private class StartGameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {

			Object obj = event.getSource();

			if (obj == startBtn) {
				// to start button invisible
				startBtn.setVisible(false);
				// to visible door button
				for (int i=0; i<3; i++)
					doorBtn[i].setVisible(true);
				// change messages
				message1.setText("Behind one door is a Car, behind the others, Goats");
				message2.setText("You pick a Door!!");
				// generate door random number
				nCar = (int)(Math.random()*3) + 1; // 1 - 3
				nOpen = (int)(Math.random()*3) + 1;
				
				lbnTotal.setText(nTotalWin + " / " + nTotal);
			}
		} // actionPerformed()
	} // StartGameListener class

	// when you pick door first
	// open one door, which has a goat AND ask 'Do you want to change?'
	private class PickTheDoorListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {

			Object obj = event.getSource();

			for (int i=0; i<3; i++) {
				// find door which you click
				if (obj == doorBtn[i]) {

					nPick = i + 1;  // door number which you click

					// decide open door
					while (nCar == nOpen || nPick == nOpen)
						nOpen = (int)(Math.random()*3) + 1;

					// set door Button disable, to cannot change the choice
					for (int j=0; j<3; j++)
						doorBtn[j].setEnabled(false);

					// show one door, which has a goat
					doorBtn[nOpen-1].setVisible(false);

					doorBtn[nOpen-1] = new JButton(goat);
					doorBtn[nOpen-1].setBounds(170+(245*(nOpen-1)),75,170,250);
					doorBtn[nOpen-1].setBorderPainted(false);
					doorBtn[nOpen-1].setContentAreaFilled(false);
					doorBtn[nOpen-1].setFocusPainted(false);
					gamePanel.add(doorBtn[nOpen-1]);
		
					// ask, do you to change
					message1.setText("You pick Door No." + nPick);
					message2.setText("Behind Door No." + nOpen + " is a Goat. Do you want to Change?");
					// you can click 'YES' or 'NO'
					yesBtn.setVisible(true);
					noBtn.setVisible(true);
				} // if
			} // for
		} // actionPerformed()
	} // PickTheDoorListener class

	// when you click 'YES' or 'NO' Button
	// check if you choose correct door
	private class ChangeTheDoorListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {

			Object obj = event.getSource();

			// if you want to change
			if (obj == yesBtn) {
				// total game number and switch number increase
				nTotal++;
				nSwitch ++;
				// changed door number is not a picked one, and not a opened door
				for (int i=1; i<=3; i++) {
					if (i != nPick && i != nOpen) {
						nChange = i;
						break;
					}
				} // for

				// set message, you change door
				message1.setText("You Change Door to No." + nChange);
				// if changed door has a Car
				// show Car image
				doorBtn[nChange-1].setVisible(false);
				if (nChange == nCar) {
					// total win number and switch win number increase
					nTotalWin++;
					nSwitchWins++;
					message2.setText("You got it!! Congratulation~!");
					doorBtn[nChange-1] = new JButton(car);
				}
				// if changed door has a Goat
				// show Goat image
				else {
					message2.setText("Oh.. Car is behind Door No." + nCar + ". Maybe next time~");
					doorBtn[nChange-1] = new JButton(goat);
				}
				doorBtn[nChange-1].setBounds(170+(245*(nChange-1)),75,170,250);
				doorBtn[nChange-1].setBorderPainted(false);
				doorBtn[nChange-1].setContentAreaFilled(false);
				doorBtn[nChange-1].setFocusPainted(false);
				gamePanel.add(doorBtn[nChange-1]);
			} // if
			// if you don't want to change
			else {
				// total game number and first choice number increase
				nTotal++;
				nChoice ++;
				message1.setText("You stay your Choice~");
				doorBtn[nPick-1].setVisible(false);
				// if first picked door has a Car
				// show Car image
				if (nPick == nCar) {
					// total win number and choice win number increase
					nTotalWin++;
					nChoiceWins++;
					message2.setText("You got it!! Congratulation~!");
					doorBtn[nPick-1] = new JButton(car);
				}
				// if first picked door has a Goat
				// show Goat image
				else {
					message2.setText("Oh.. Car is behind Door No." + nCar + ". Maybe next time~");
					doorBtn[nPick-1] = new JButton(goat);
				}
				doorBtn[nPick-1].setBounds(170+(245*(nPick-1)),75,170,250);
				doorBtn[nPick-1].setBorderPainted(false);
				doorBtn[nPick-1].setContentAreaFilled(false);
				doorBtn[nPick-1].setFocusPainted(false);
				gamePanel.add(doorBtn[nPick-1]);
			}
			// to show YES and NO Button invisible
			yesBtn.setVisible(false);
			noBtn.setVisible(false);


			// Ask 'do you want one more Game?''
			int result = JOptionPane.showConfirmDialog(null, "Do you want One more Game?");  // show pop-up :: asking continue

			// if you click 'YES' 
			if (result == JOptionPane.YES_OPTION) {

				fileRewrite();  		// undate values
				initMontyHallGame();    // init a game
			}
		} // actionPerformed()
	} // ChangeTheDoorListener class

	public void execute(ConsoleGame _game) {
		reset();
		System.out.println("Game Show");
	}//execute() abstract method

} // GameMenu_GameShow class
