import javax.swing.JFrame;

public class GameMenuEx
{
	public static void main(String[] args) {
		JFrame frame = new JFrame("CONSOLE MENU");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		PrimaryPanel primary = new PrimaryPanel();
		frame.getContentPane().add(primary);
		frame.pack();
		frame.setVisible(true);
	} //main
}// GameMenuEx class contain main method