import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GameMenuPanel extends JPanel
{
	private GameMenu gameMenu;
	private JLabel lblGameTitle;
	private JButton[] btnMenu;
	
	
	private IGame[] iGame;
	private ConsoleGame consoleGame;
	
	private MenuListener menuL;
	
	private PrimaryPanel primary;

	private ImageIcon backGroundImage;
	private Image backImg;

	private ImageIcon iconLabel;
	private Image imageLabel;
	
	public GameMenuPanel(PrimaryPanel _primary) {
		
		setBackground(Color.white);
		setPreferredSize(new Dimension(1000,700));
		setLayout(null);

		backGroundImage = new ImageIcon("image/background.png");
		backImg = backGroundImage.getImage();

		iconLabel = new ImageIcon("image/label.png");
		imageLabel = iconLabel.getImage();

		
		primary = _primary;
		
		menuL = new MenuListener();
		consoleGame = new ConsoleGame();
		gameMenu = new GameMenu(GameConstants.TITLE, GameConstants.MENU, GameConstants.MENU_COUNT);
		iGame = new IGame[GameConstants.MENU_COUNT];
		
		iGame[0] = new GameMenu_DieSimulation();
		iGame[1] = new GameMenu_GameShow();
		iGame[2] = new GameMenu_HighLowGame();
		iGame[3] = new GameMenu_WordGame();
		iGame[4] = new GameMenu_OXGame();
		iGame[5] = new GameMenu_StarGame();
		
		lblGameTitle = new JLabel(gameMenu.getTitle());
		lblGameTitle.setBounds(0,100,1000,150);
		lblGameTitle.setBackground(Color.white);
		lblGameTitle.setFont(new Font("Annelies with Tomkitomki",Font.BOLD,100));
		lblGameTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblGameTitle.setVerticalAlignment(SwingConstants.CENTER);
		add(lblGameTitle);

		btnMenu = new JButton[gameMenu.getMenuCount()];
		
		for (int i=0; i<gameMenu.getMenuCount(); i++) {
			btnMenu[i] = new JButton((i+1)+" . "+gameMenu.getMenu(i));
			btnMenu[i].setBounds(300,250+(50*i),400,50);
			btnMenu[i].setBackground(Color.red);
			btnMenu[i].setFont(new Font("Annelies with Tomkitomki", Font.BOLD,30));

			btnMenu[i].setBorderPainted(false);
			btnMenu[i].setContentAreaFilled(false);
			btnMenu[i].setFocusPainted(false);


			btnMenu[i].addActionListener(menuL);
			add(btnMenu[i]);
		}//for
	}//GameMenuPanel()

	private class MenuListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {
			Object obj = event.getSource();
			setVisible(false);
			primary.btnBack.setVisible(true);
			for (int i=0; i<gameMenu.getMenuCount(); i++) {
				if (obj == btnMenu[i]) {
					consoleGame.setGameMenu(iGame[i]);
					consoleGame.execute();
					primary.addSet(iGame[i],i);
					break;
				} //if
			}//for
		}// actionPeformed()	
	}//MenuListener class

	public void paintComponent(Graphics page) 
	{
        super.paintComponent(page);
        page.drawImage(backImg,0,0,1000,700,this);
        setOpaque(true);

        for(int i=0; i<gameMenu.getMenuCount(); i++){
       		page.drawImage(imageLabel,300,250+(50*i),400,40,this);
       		setOpaque(true);
       	} //for
    } // paintComponent for draw button image
}//GameMenuPanel class