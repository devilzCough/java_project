/*** This is a 'JeeMail' class ***/
/***     Main Action class     ***/

import javax.swing.JFrame;


public class JeeMail 
{
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Jee MAIL");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  	// to close normal
		frame.setResizable(false);								// can't control Frame Size
		
		PrimaryPanel primary = new PrimaryPanel();
		frame.getContentPane().add(primary);					// add PrimaryPanel to frame
		
		frame.pack();											// packing Frame
		frame.setVisible(true);

	} // main()

} // JeeMail class