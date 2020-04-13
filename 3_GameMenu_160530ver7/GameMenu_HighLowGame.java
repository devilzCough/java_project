import java.awt.*;  
import javax.swing.*; 
import java.awt.event.*; 

public class GameMenu_HighLowGame extends JPanel implements IGame {

	private JPanel topPanel,bottomPanel,middlePanel; 
	private JLabel lblMessage1,lblMessage2,lblHighLow; 
	private JPanel menuPanel; 
	private JButton btnRandom,btnInput; 
	private JTextField txtInput;  
	private JLabel lblRange, lblCount; 
	private int nRandom,nInput,nMin,nMax,nCount; 
	private ImageIcon iconStart;
	private LabelThread lblMark; 
	private GameListener gameL; 


	public GameMenu_HighLowGame(){

		setBounds(0,100,1000,700); 
		setBackground(Color.white); 
		setLayout(null); 
		gameL = new GameListener();
		
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

		middlePanel = new JPanel();   
		middlePanel.setBackground(Color.white);  
		middlePanel.setLayout(null);  
		middlePanel.setBounds(0,150,1000,400); 
		middlePanel.setVisible(false);
		add(middlePanel); 

		Font fnt = new Font ("Verdena",Font.BOLD,30); 

		lblMessage1 = new JLabel("HIGH-LOW-GAME ");
		lblMessage1.setBounds(10,30,1000,40); 
		lblMessage1.setFont(fnt); 
		lblMessage1.setHorizontalAlignment(SwingConstants.CENTER); 
		lblMessage1.setVerticalAlignment(SwingConstants.CENTER); 
		lblMessage1.setForeground(Color.darkGray); 
		topPanel.add(lblMessage1); 

		lblMessage2 = new JLabel("If you press START button, it makes a random number");  
		lblMessage2.setBounds(10,80,1000,40);
		lblMessage2.setFont(fnt); 
		lblMessage2.setHorizontalAlignment(SwingConstants.CENTER); 
		lblMessage2.setVerticalAlignment(SwingConstants.CENTER); 
		lblMessage2.setForeground(Color.black); 
		topPanel.add(lblMessage2); 

		lblMark = new LabelThread("?"); 

		lblMark.setBounds(50,100,300,150); 
		lblMark.setFont(new Font("Verdena",Font.BOLD,100)); 
		lblMark.setHorizontalAlignment(SwingConstants.CENTER);
		lblMark.setVerticalAlignment(SwingConstants.CENTER); 
		lblMark.setVisible(false); 
		middlePanel.add(lblMark); 

		lblHighLow = new JLabel(); 
		lblHighLow.setBounds(50,300,300,40); 
		lblHighLow.setFont(fnt); 
		lblHighLow.setHorizontalAlignment(SwingConstants.CENTER); 
		lblHighLow.setVerticalAlignment(SwingConstants.CENTER); 
		lblHighLow.setForeground(Color.darkGray); 
		lblHighLow.setVisible(false); 
		middlePanel.add(lblHighLow); 
	
		iconStart = new ImageIcon("image/start.png");
		btnRandom = new JButton(iconStart); 
		btnRandom.setBounds(400,250,220,210); 
		
		btnRandom.setBorderPainted(false);
		btnRandom.setContentAreaFilled(false);
		btnRandom.setFocusPainted(false);
		
		btnRandom.addActionListener(gameL); 
		add(btnRandom); 

		Font fnt2 = new Font("Verdena",Font.BOLD,30); 
		
		txtInput = new JTextField(); 
		txtInput.setBounds(600,50,100,50); 
		txtInput.setFont(fnt2); 
		txtInput.addActionListener(gameL); 
		middlePanel.add(txtInput);

		btnInput = new JButton("INPUT"); 
		btnInput.setFont(fnt2);	
		btnInput.setBounds(750,50,150,50); 
		btnInput.addActionListener(gameL);
		middlePanel.add(btnInput); 

		nMin = 1;
		nMax = 100; 

		lblRange = new JLabel("("+nMin+"-"+nMax+")");
		lblRange.setFont(fnt2); 
		lblRange.setBounds(600,180,300,50); 
		middlePanel.add(lblRange); 

		nCount=0; 

		lblCount= new JLabel("COUNT : "+nCount);
		lblCount.setFont(fnt2); 
		lblCount.setBounds(600,300,300,50); 
		middlePanel.add(lblCount); 
	} // GameMenu_HighLowGame()

	private class GameListener implements ActionListener {

		public void actionPerformed(ActionEvent event){
			Object obj = event.getSource(); 
			if(obj == btnRandom ){
				nRandom = (int)(Math.random()*100)+1; 
					
				lblMark.setVisible(true); 
				middlePanel.setVisible(true);
				btnRandom.setEnabled(false); 
				btnRandom.setVisible(false);
				lblMessage2.setText("Input a number ");
			}//if
				
			else if(obj == txtInput || obj == btnInput){ 

				nInput = Integer.parseInt(txtInput.getText()); 
				txtInput.setText(""); 

				if(nRandom < nInput) { 
					lblHighLow.setText("HIGH"); 
					lblMessage2.setText("Your number is HIGHER than a random number.");
					nMax= nInput -1; 
					nCount++; 
				}//if
				else if(nRandom > nInput){ 
					lblHighLow.setText("LOW"); 
					lblMessage2.setText("Your number is LOWER than a random number.");
					nMin = nInput +1; 
					nCount++; 
				}//else if

				else {
					lblHighLow.setText("RIGHT");
					lblMessage2.setText("You are right!");
					lblCount.setText("COUNT : "+(++nCount)); 
						
					lblMark.setStart(1); 
					lblMark.setEnd(nRandom);
					lblMark.start(); 
						
					int result = JOptionPane.showConfirmDialog(null,"Continue?"); 
					if(result == JOptionPane.YES_OPTION)	reset();
				}//else	

					lblHighLow.setVisible(true);
					lblRange.setText("("+nMin+"-"+nMax+")"); 
					lblCount.setText("COUNT : "+(nCount)); 
			} //else if
		} // actionPeformed()
	} // GameListener class
	
	public void reset(){
		lblMark.stop(); 
		lblMark.setVisible(false); 

		lblMark = new LabelThread("?"); 
		lblMark.setBounds(50,100,300,150); 
		lblMark.setFont(new Font("Verdena",Font.BOLD,100));
		lblMark.setHorizontalAlignment(SwingConstants.CENTER);
		lblMark.setVerticalAlignment(SwingConstants.CENTER);
		lblMark.setVisible(false);
		middlePanel.add(lblMark);
		lblMessage2.setText("");
		btnRandom.setEnabled(true);
		btnRandom.setVisible(true);
		middlePanel.setVisible(false); 
		lblMessage2.setText("If you press Generates button, it makes a random number");

		nCount=0; 
		nMin=1; 
		nMax=100; 
	} //reset()
	
	public void execute(ConsoleGame _game) {	
		reset();				 
		System.out.println("HighLow");
	}//execute() abstract method
} //GameMenu_HighLowGame class
	
