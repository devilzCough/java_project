import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CardGame extends JPanel
{
	private ArrayList<Player> playerList;
	private ArrayList<Card> deck;
	private Font font1;

	private int currentCategoryNum;
	private int nPlayingPlayer, selectedCardNum;
	private int nTakeCard;
	private  Player nowPlayer;

	private JPanel dashboardPanel, infoPanel, catePanel, gamePanel, myCardPanel;

	// dashboardPanel
	private JLabel[] lblPlayerArray, lblCardNumArray;

	// infoPanel
	private JLabel lblPlayers, lblDealerNum, lblPlayerNum, lblCategory;
	private int nPlayers, dealerNum, currentPlayerNum;
	private Font infoFont;

	// catePanel
	private JLabel lblCateTitle;
	private JButton[] btnCategory;

	private JPanel loginPanel;
	private JLabel lblTitle, lblStart, lblAlert;
	private JTextField tfMembers;

	// gamePanel
	private JLabel lblPrevScore;
	private JButton btnPrevCard, btnTakeCard;
	private ImageIcon prevCardIcon, takeCardIcon;

	// myCardPanel
	private JButton btnShowCard;
	private JButton[] btnMyCardArray;
	private ImageIcon showCardIcon;
	private ImageIcon[] myCardArrayIcon;

	// Listener
	private CheckNumOfMembersListener checkNumOfMemL;
	private SelectCategoryListener selectCategoryL;
	private ShowCardInfoListener showCardInfoL;
	private SelectCardListener selectCardL;
	private TakeOneCardListener takeOneCardL;

	public CardGame() {

		setPreferredSize(new Dimension(1100,800));
		setBackground(Color.white);
		setLayout(null);

		font1 = new Font("Verdana", Font.BOLD, 20);
		nTakeCard = 0;

		// Listener
		checkNumOfMemL = new CheckNumOfMembersListener();
		selectCategoryL = new SelectCategoryListener();
		showCardInfoL = new ShowCardInfoListener();
		selectCardL = new SelectCardListener();
		takeOneCardL = new TakeOneCardListener();

		//
		// DashBoard Panel
		dashboardPanel = new JPanel();
		dashboardPanel.setBackground(Color.white);
		dashboardPanel.setBounds(0, 0, 850, 150);
		dashboardPanel.setLayout(null);
		add(dashboardPanel);

		//
		// Infomation Panel
		infoPanel = new JPanel();
		infoPanel.setBackground(new Color(94, 168, 167));
		infoPanel.setBounds(850, 0, 250, 150);
		infoPanel.setLayout(null);
		add(infoPanel);

		infoFont = new Font("Verdana", Font.BOLD, 15);

		lblPlayers = new JLabel("Num of Players : --");
		lblPlayers.setBounds(10, 0, 250, 35);
		lblPlayers.setFont(infoFont);
		lblPlayers.setVerticalAlignment(SwingConstants.CENTER);
		infoPanel.add(lblPlayers);

		lblDealerNum = new JLabel("Dealer No. : --");
		lblDealerNum.setBounds(10, 35, 250, 35);
		lblDealerNum.setFont(infoFont);
		lblDealerNum.setVerticalAlignment(SwingConstants.CENTER);
		infoPanel.add(lblDealerNum);

		lblPlayerNum = new JLabel("Player No. : --");
		lblPlayerNum.setBounds(10, 70, 250, 35);
		lblPlayerNum.setFont(infoFont);
		lblPlayerNum.setVerticalAlignment(SwingConstants.CENTER);
		infoPanel.add(lblPlayerNum);

		lblCategory = new JLabel("Category : --");
		lblCategory.setBounds(10, 105, 250, 35);
		lblCategory.setFont(infoFont);
		lblCategory.setVerticalAlignment(SwingConstants.CENTER);
		infoPanel.add(lblCategory);

		nPlayers = dealerNum = currentPlayerNum = 0;

		//
		// Category Panel
		catePanel = new JPanel();
		catePanel.setBackground(new Color(37, 121, 133));
		catePanel.setBounds(0, 150, 1100, 350);
		catePanel.setLayout(null);
		add(catePanel);

		lblCateTitle = new JLabel("CATEGORY");
		lblCateTitle.setBounds(0, 30, 1100, 30);
		lblCateTitle.setFont(new Font("Verdana", Font.BOLD, 30));
		lblCateTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblCateTitle.setVerticalAlignment(SwingConstants.CENTER);
		lblCateTitle.setVisible(false);
		catePanel.add(lblCateTitle);

		btnCategory = new JButton[GameConstants.NUM_OF_CATE];
		for (int i = 0; i < GameConstants.NUM_OF_CATE; i++) {
			btnCategory[i] = new JButton(GameConstants.CATEGORY[i]);
			btnCategory[i].setBounds(145 + 165 * i, 140, 150, 100);
			btnCategory[i].setVisible(false);
			btnCategory[i].addActionListener(selectCategoryL);
			catePanel.add(btnCategory[i]);
		}

		// Login Panel
		loginPanel = new JPanel();
		loginPanel.setBackground(Color.lightGray);
		loginPanel.setBounds(300, 25, 500, 300);
		loginPanel.setLayout(null);
		catePanel.add(loginPanel);

		lblTitle = new JLabel("MINERAL SUPER TRUMPS CARD GAME");
		lblTitle.setFont(font1);
		lblTitle.setBounds(0, 10, 500, 30);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setVerticalAlignment(SwingConstants.CENTER);
		loginPanel.add(lblTitle);

		lblStart = new JLabel("Enter the number of PLAYERS!!");
		lblStart.setFont(font1);
		lblStart.setBounds(0, 90, 500, 30);
		lblStart.setHorizontalAlignment(SwingConstants.CENTER);
		lblStart.setVerticalAlignment(SwingConstants.CENTER);
		loginPanel.add(lblStart);

		tfMembers = new JTextField();
		tfMembers.setBounds(100, 150, 300, 40);
		tfMembers.addActionListener(checkNumOfMemL);
		loginPanel.add(tfMembers);

		lblAlert = new JLabel();
		lblAlert.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblAlert.setBounds(0, 190, 500, 30);
		lblAlert.setForeground(Color.red);
		lblAlert.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlert.setVerticalAlignment(SwingConstants.CENTER);
		lblAlert.setVisible(false);
		loginPanel.add(lblAlert);

		//
		// Game Panel
		gamePanel = new JPanel();
		gamePanel.setBackground(new Color(37, 121, 133));
		gamePanel.setBounds(0, 150, 1100, 350);
		gamePanel.setLayout(null);
		gamePanel.setVisible(false);
		add(gamePanel);

		lblPrevScore = new JLabel("--");
		lblPrevScore.setBounds(400, 150, 300, 50);
		lblPrevScore.setFont(new Font("Verdana", Font.BOLD, 20));
		lblPrevScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrevScore.setVerticalAlignment(SwingConstants.CENTER);
		gamePanel.add(lblPrevScore);

		takeCardIcon = new ImageIcon("image/Backside.jpg");
		btnTakeCard = new JButton(takeCardIcon);
		btnTakeCard.setBounds(750,35,200,280);
		btnTakeCard.setBorderPainted(false);
		btnTakeCard.setContentAreaFilled(false);
		btnTakeCard.setFocusPainted(false);
		btnTakeCard.addActionListener(takeOneCardL);
		gamePanel.add(btnTakeCard);

		//
		// MyCard Panel
		myCardPanel = new JPanel();
		myCardPanel.setBackground(new Color(255, 68, 71));
		myCardPanel.setBounds(0, 500, 1100, 300);
		myCardPanel.setLayout(null);
		add(myCardPanel);

	} // CardGame()

	public void initCardGame() {

		lblCateTitle.setVisible(true);

		for (int i = 0; i < GameConstants.NUM_OF_CATE; i++)
			btnCategory[i].setVisible(true);

		lblPlayerArray = new JLabel[nPlayers];
		for (int i = 0; i < nPlayers; i++) {
			lblPlayerArray[i] = new JLabel("Player " + (i + 1));
			lblPlayerArray[i].setBounds(170 * i, 20, 170, 40);
			lblPlayerArray[i].setFont(font1);
			lblPlayerArray[i].setHorizontalAlignment(SwingConstants.CENTER);
			lblPlayerArray[i].setVerticalAlignment(SwingConstants.CENTER);
			dashboardPanel.add(lblPlayerArray[i]);
		}

		lblCardNumArray = new JLabel[nPlayers];
		for (int i = 0; i < nPlayers; i++) {
			lblCardNumArray[i] = new JLabel("8");
			lblCardNumArray[i].setBounds(170 * i, 70, 170, 60);
			lblCardNumArray[i].setFont(new Font("Verdana", Font.BOLD, 60));
			lblCardNumArray[i].setHorizontalAlignment(SwingConstants.CENTER);
			dashboardPanel.add(lblCardNumArray[i]);
		}

		playerList = new ArrayList<>();
		for (int i = 0; i < nPlayers; i++) {
			Player player = new Player(i + 1);
			playerList.add(player);
		}

		// make Deck.
		deck = new ArrayList<>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader("cards.txt"));
			String line;
			while ((line = bufferedReader.readLine()) != null) {

				String[] cardInformation = line.split(",");
				Card card;

				if (line.split(",").length > 2) {
					card = new Card(cardInformation[0], cardInformation[1], cardInformation[2], cardInformation[3],
							cardInformation[4], cardInformation[5]);
				}
				else{
					card = new Card(cardInformation[0], cardInformation[1]);
				}
				deck.add(card);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Collections.shuffle(deck);

		// Share cards to each Players
		for (Player player : playerList) {
			for (int i = 0; i < 8; i++) {
				player.setPresentCards(deck.get(0));
				deck.remove(0);
			}
		}
		repaint();

		dealerNum = (int)(Math.random() * nPlayers);
		currentPlayerNum = (dealerNum + 1) % nPlayers+ 1;
		lblPlayers.setText("Num of Players : " + nPlayers + " / " + nPlayers);
		lblDealerNum.setText("Dealer No. : " + (dealerNum + 1));
		lblPlayerNum.setText("Player No. : " + currentPlayerNum);

		nPlayingPlayer = countPlayingPlayers(playerList);
		nowPlayer = playerList.get(currentPlayerNum - 1);

		setPlayerCardList();
		btnShowCard.setEnabled(false);
		for (int i = 0; i < nowPlayer.getPresentCards().size(); i++)
			btnMyCardArray[i].setEnabled(false);

	} // initCardGame()

	public void setPlayerCardList() {

		btnShowCard = null;
		btnMyCardArray = null;

		System.out.println(nowPlayer.getCard(0).getCardName());
		showCardIcon = new ImageIcon("image/" + nowPlayer.getCard(0).getCardName() + ".jpg");
		btnShowCard = new JButton(showCardIcon);
		btnShowCard.setBounds(75,10,200,280);
		btnShowCard.setBorderPainted(false);
		btnShowCard.setContentAreaFilled(false);
		btnShowCard.setFocusPainted(false);
		btnShowCard.addActionListener(selectCardL);
		myCardPanel.add(btnShowCard);

		int nNowCards = nowPlayer.getPresentCards().size();
		myCardArrayIcon = new ImageIcon[nNowCards];
		btnMyCardArray = new JButton[nNowCards];
		for (int i = 0; i < nNowCards; i++) {
			myCardArrayIcon[i] = new ImageIcon("image/Backside.jpg");
			btnMyCardArray[i] = new JButton(myCardArrayIcon[i]);
			btnMyCardArray[i].setBounds(350+20*i,10,200,280);
			btnMyCardArray[i].setBorderPainted(false);
			btnMyCardArray[i].setContentAreaFilled(false);
			btnMyCardArray[i].setFocusPainted(false);
			btnMyCardArray[i].addActionListener(showCardInfoL);
			myCardPanel.add(btnMyCardArray[i]);
		}

		repaint();

	} // setPlayerCardList()

	public int countPlayingPlayers (ArrayList<Player> players) {

		int nPlaying = 0;

		for (Player player : players) {
			if (player.getIsPlaying())
				nPlaying ++;
		}

		return nPlaying;
	} // countPlayingPlayers()

	public int setCurrentPlayerNum (int nPlayer, int prevPlayerNum, ArrayList<Player> players) {

		int nextPlayerNum = prevPlayerNum % nPlayers; // : player index, prevPlayerNum : player number
		Player tmpPlayer = players.get(nextPlayerNum);

		while (!tmpPlayer.getIsPlaying()) {
			nextPlayerNum = (nextPlayerNum + 1) % nPlayers;
			tmpPlayer = players.get(nextPlayerNum);
		}

		return nextPlayerNum + 1;
	} // setCurrentPlayerNum()

	public boolean compareWithPrevCard (int cateNum, String prev, String current) {

		double prevScore = 0;
		double currentScore = 0;

		boolean result = true;

		switch (cateNum) {
			case GameConstants.HARDNESS:
			case GameConstants.SPECIFIC_GRAVITY:
				prevScore = Double.parseDouble(prev);
				currentScore = Double.parseDouble(current);

				break;

			case GameConstants.CLEAVAGE:
				// get Previous Value
				for (String value : GameConstants.CLEAVAGE_VALUES) {
					prevScore += 1;
					if (prev.equals(value))
						break;
				}

				// get Current Value
				for (String value : GameConstants.CLEAVAGE_VALUES) {
					currentScore += 1;
					if (current.equals(value))
						break;
				}
				break;

			case GameConstants.CRUSTAL_ABUNDANCE:
				for (String value : GameConstants.CRUSTALABUNDANCE_VALUES) {
					prevScore += 1;
					if (prev.equals(value))
						break;
				}

				for (String value : GameConstants.CRUSTALABUNDANCE_VALUES) {
					currentScore += 1;
					if (current.equals(value))
						break;
				}
				break;

			case GameConstants.ECONOMIC_VALUE:
				for (String value : GameConstants.ECONOMIC_VALUES) {
					prevScore += 1;
					if (prev.equals(value))
						break;
				}

				for (String value : GameConstants.ECONOMIC_VALUES) {
					currentScore += 1;
					if (current.equals(value))
						break;
				}
				break;
		}

		// prev < current : true
		// prev > current : false
		if (prevScore >= currentScore)
			result = false;

		return result;
	} // compareWithPrevCard()

	public int changeCategory(String cardName) {

		int changedCateNum = GameConstants.THE_GEOLOGIST; // The Geologist : pick Category

		if (cardName.equals("The Miner"))
			changedCateNum = GameConstants.ECONOMIC_VALUE;
		else if (cardName.equals("The Petrologist"))
			changedCateNum = GameConstants.CRUSTAL_ABUNDANCE;
		else if (cardName.equals("The Gemmologist"))
			changedCateNum = GameConstants.HARDNESS;
		else if (cardName.equals("The Mineralogist"))
			changedCateNum = GameConstants.CLEAVAGE;
		else if (cardName.equals("The Geophysicist"))
			changedCateNum = GameConstants.SPECIFIC_GRAVITY;

		return changedCateNum;
	} // changeCategory()

	public void whenOnePlayerWin() {

		nowPlayer.setPlayingState(false);
		nPlayingPlayer = countPlayingPlayers(playerList);
		if (nPlayingPlayer == 1) {
			JOptionPane.showMessageDialog(null, "GAME OVER");
		} else {
			lblPlayers.setText("Num of Players : " + nPlayingPlayer + " / " + nPlayers);

			if (currentPlayerNum == dealerNum) {
				dealerNum = setCurrentPlayerNum (nPlayers, dealerNum, playerList);
				lblDealerNum.setText("Dealer No. : " + dealerNum);
			}
			currentPlayerNum = setCurrentPlayerNum(nPlayers, currentPlayerNum, playerList);
			lblPlayerNum.setText("Player No. : " + currentPlayerNum);
			nowPlayer = playerList.get(currentPlayerNum - 1);
		}

	} // whenOnePlayerWin()

	public void setIfSuperTrump(Card selectedCard) {

		currentCategoryNum = changeCategory(selectedCard.getCardName());

		btnShowCard.setVisible(false);
		for (int i = 0; i < nowPlayer.getPresentCards().size(); i++)
			btnMyCardArray[i].setVisible(false);

		nTakeCard = 0;
		nowPlayer.removeCard(selectedCardNum);
		lblCardNumArray[currentPlayerNum - 1].setText("" + nowPlayer.getPresentCards().size());

		if (nowPlayer.getPresentCards().size() == 0) {
			whenOnePlayerWin();
			// setPlayerCardList();
		}

		if (currentCategoryNum == GameConstants.THE_GEOLOGIST) {
			lblCardNumArray[currentPlayerNum - 1].setText("" + nowPlayer.getPresentCards().size());
			gamePanel.setVisible(false);
			btnShowCard.setVisible(false);
			btnShowCard = null;
			lblPrevScore.setText("--");
			catePanel.setVisible(true);

			setPlayerCardList();
			btnShowCard.setEnabled(false);
			for (int i = 0; i < nowPlayer.getPresentCards().size(); i++)
				btnMyCardArray[i].setEnabled(false);

		} else if (currentCategoryNum == GameConstants.SPECIFIC_GRAVITY && nowPlayer.hasMagnetite()) {

			lblCardNumArray[currentPlayerNum - 1].setText("0");
			whenOnePlayerWin();
			setPlayerCardList();
		} else {
			lblCardNumArray[currentPlayerNum - 1].setText("" + nowPlayer.getPresentCards().size());
			lblCategory.setText("Category : " + GameConstants.CATEGORY[currentCategoryNum]);
			lblPrevScore.setText("--");
			btnPrevCard = null;
			setPlayerCardList();
		}
	} // setIfSuperTrump()

	public void setIfGeneralCard(Card selectedCard) {

		prevCardIcon = new ImageIcon("image/" + selectedCard.getCardName() + ".jpg");
		btnPrevCard = new JButton(prevCardIcon);
		btnPrevCard.setBounds(150,35,200,280);
		btnPrevCard.setBorderPainted(false);
		btnPrevCard.setContentAreaFilled(false);
		btnPrevCard.setFocusPainted(false);
		gamePanel.add(btnPrevCard);

		lblPrevScore.setText(selectedCard.getCardInfo(currentCategoryNum));
		btnShowCard.setVisible(false);
		for (int i = 0; i < nowPlayer.getPresentCards().size(); i++)
			btnMyCardArray[i].setVisible(false);

		nTakeCard = 0;
		nowPlayer.removeCard(selectedCardNum);
		lblCardNumArray[currentPlayerNum - 1].setText("" + nowPlayer.getPresentCards().size());

		if (nowPlayer.getPresentCards().size() == 0) {
			whenOnePlayerWin();
		} else {
			currentPlayerNum = setCurrentPlayerNum(nPlayers, currentPlayerNum, playerList);
			lblPlayerNum.setText("Player No. : " + currentPlayerNum);
			nowPlayer = playerList.get(currentPlayerNum - 1);
		}

		setPlayerCardList();

	} // setIfGeneralCard()

	private class CheckNumOfMembersListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {

			nPlayers = Integer.parseInt(tfMembers.getText());

			if (nPlayers < 3 || nPlayers > 5) {
				lblAlert.setVisible(true);
				lblAlert.setText("The number of players must be between 3 and 5.");

				tfMembers.setText("");
			} else {
				loginPanel.setVisible(false);

				initCardGame();
			}

		} // actionPerformed()
	} // CheckNumOfMembersListener Class

	private class SelectCategoryListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {

			Object obj = event.getSource();

			for (int i = 0; i < GameConstants.NUM_OF_CATE; i++) {
				if (obj == btnCategory[i]) {
					currentCategoryNum = i;
					lblCategory.setText("Category : " + GameConstants.CATEGORY[i]);

					catePanel.setVisible(false);
					gamePanel.setVisible(true);

					btnShowCard.setEnabled(true);
					for (int j = 0; j < nowPlayer.getPresentCards().size(); j++)
						btnMyCardArray[j].setEnabled(true);
				}
			}
		} // actionPerformed()
	} // SelectCategoryListener Class

	private class ShowCardInfoListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {

			Object obj = event.getSource();

			for (int i = 0; i < nowPlayer.getPresentCards().size(); i++) {
				if (obj == btnMyCardArray[i]) {

					btnShowCard.setVisible(false);
					btnShowCard = null;

					selectedCardNum = i;
					showCardIcon = new ImageIcon("image/" + nowPlayer.getCard(i).getCardName() + ".jpg");

					btnShowCard = new JButton(showCardIcon);
					btnShowCard.setBounds(75,10,200,280);
					btnShowCard.addActionListener(selectCardL);
					myCardPanel.add(btnShowCard);

					btnShowCard.setVisible(true);
				}
			}
		} // actionPerformed()
	} // ShowCardInfoListener Class

	private class SelectCardListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {
			// System.out.println("test!!");
			Card tmpCard = nowPlayer.getCard(selectedCardNum);
  			System.out.println(tmpCard.getCardName());
			if (lblPrevScore.getText().equals("--")) {
				// System.out.println("test!!");
				int result = JOptionPane.showConfirmDialog(null, "Do you want to pick this card?");  // show pop-up
				if (result == JOptionPane.YES_OPTION) {
					if (tmpCard.getCardInfo(currentCategoryNum).equals("Super Trump")) {
						//
						setIfSuperTrump(tmpCard);
						//
					} else {
						//
						setIfGeneralCard(tmpCard);
						//
					}
				}

			} else {
				int result = JOptionPane.showConfirmDialog(null, "Do you want to pick this card?");  // show pop-up
				if (result == JOptionPane.YES_OPTION) {
					if (tmpCard.getCardInfo(currentCategoryNum).equals("Super Trump")) {
						btnPrevCard.setVisible(false);
						//
						setIfSuperTrump(tmpCard);
						//
					} else {
						if (!compareWithPrevCard(currentCategoryNum, lblPrevScore.getText(), tmpCard.getCardInfo(currentCategoryNum))) {
							JOptionPane.showMessageDialog(null, "Please select a card Larger than the Previous Value.");
						} else {
							btnPrevCard.setVisible(false);
							//
							setIfGeneralCard(tmpCard);
							//
						}
					}
				}

			}
		} // actionPerformed()
	} // SelectCardListener Class

	private class TakeOneCardListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {

			int result = JOptionPane.showConfirmDialog(null, "Do you really take a Card?");  // show pop-up
			if (result == JOptionPane.YES_OPTION) {

				btnShowCard.setVisible(false);
				for (int i = 0; i < nowPlayer.getPresentCards().size(); i++)
					btnMyCardArray[i].setVisible(false);

				nowPlayer.setPresentCards(deck.get(0));
				deck.remove(0);
				nTakeCard ++;

				lblCardNumArray[currentPlayerNum - 1].setText("" + nowPlayer.getPresentCards().size());
				System.out.println("prev : " + currentPlayerNum);
				currentPlayerNum = setCurrentPlayerNum(nPlayers, currentPlayerNum, playerList);
				nowPlayer = playerList.get(currentPlayerNum - 1);
				lblPlayerNum.setText("Player No. : " + currentPlayerNum);
				System.out.println("next : " + currentPlayerNum);

				if (nTakeCard == nPlayingPlayer) {
					gamePanel.setVisible(false);
					btnPrevCard.setVisible(false);
					btnShowCard.setVisible(false);
					btnShowCard = null;
					lblPrevScore.setText("--");
					catePanel.setVisible(true);

					setPlayerCardList();
					btnShowCard.setEnabled(false);
					for (int i = 0; i < nowPlayer.getPresentCards().size(); i++)
						btnMyCardArray[i].setEnabled(false);
				} else {
					setPlayerCardList();
				}
			}
		} // actionPerformed()
	} // TakeOneCardListener Class
} // CardGame class
