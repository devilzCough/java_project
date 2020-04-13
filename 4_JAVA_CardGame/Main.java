// Main class
// action class

import javax.swing.JFrame;

public class Main
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("MINERAL SUPER TRUMPS CARD GAME");        // frame title
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // to close normally
		frame.setResizable(false);                              // can't control Frame Size

		CardGame cardGame = new CardGame();
		frame.getContentPane().add(cardGame);                    // add PrimaryPanel to frame

		frame.pack();                                           // packing Frame
		frame.setVisible(true);
	} // main()
} // Main class
