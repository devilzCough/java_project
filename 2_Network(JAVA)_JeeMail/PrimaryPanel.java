/*** This is a 'PrimaryPanel' class      ***/
/*** it is main panel and get login form ***/

import java.io.BufferedReader;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class PrimaryPanel extends JPanel
{
	private JPanel loginPanel;
	private JLabel lblID, lblPass, lblAt;
	private JTextField txtID;
	private JPasswordField txtPass;
	private JButton btnLogin;
	private JComboBox cmbMail;
	private String[] strMail = {"gmail.com", "naver.com", "daum.net", "sejong.ac.kr"};

	private JButton btnLogo;
	private ImageIcon imgLogo, imgLogo_small;
	private String strUser, strPass, strSmtpServer;
	private int loginFlag = 0;

	private InputListener inputL;
	private BtnListener btnL;

	private SMTPSender smtpSender;
	private WriteMail writeMail;

	public PrimaryPanel() {

		// primary panel
		setBackground(Color.white);
		setPreferredSize(new Dimension(1000,640));
		setLayout(null);

		// action listener for the logo button
		btnL = new BtnListener();

		// buttons with logo images
		imgLogo = new ImageIcon("logo.png");
		imgLogo_small = new ImageIcon("logo_small.png");

		btnLogo = new JButton(imgLogo);
		btnLogo.setBounds(305,100,400,100);
		btnLogo.setLayout(null);
		btnLogo.addActionListener(btnL);
		btnLogo.setBorderPainted(false);
		btnLogo.setContentAreaFilled(false);
		btnLogo.setFocusPainted(false);
		add(btnLogo);

		// set other components on primary panel
		setPage();

	} // PrimaryPanel()

	// it because we have to change Panel between LoginPanel & WriteMailPanel
	public void setPage() {

		// action listener to handle input events
		inputL = new InputListener();
		
		// login panel
		loginPanel = new JPanel();
		loginPanel.setBounds(250,240,500,300);
		loginPanel.setBackground(new Color(245,245,245));
		loginPanel.setLayout(null);
		add(loginPanel);

		lblID = new JLabel("EMAIL ");
		lblID.setBounds(50,70,70,40);
		loginPanel.add(lblID);

		lblAt = new JLabel("@");
		lblAt.setFont(new Font("Verdana",Font.PLAIN,20));
		lblAt.setBounds(280,70,20,40);
		loginPanel.add(lblAt);

		lblPass = new JLabel("PASSWORD ");
		lblPass.setBounds(50,130,80,40);
		loginPanel.add(lblPass);
		
		// text fields to input email and password
		txtID = new JTextField();
		txtID.setBounds(130,70,140,40);
		txtID.addActionListener(inputL);
		loginPanel.add(txtID);

		txtPass = new JPasswordField();
		txtPass.setBounds(130,130,320,40);
		txtPass.addActionListener(inputL);
		loginPanel.add(txtPass);

		// combo box for choosing server
		cmbMail = new JComboBox();
		
		// add items to combo box
		for (String str : strMail)	
			cmbMail.addItem(str);

		cmbMail.setBounds(310,65,140,50);
		loginPanel.add(cmbMail);

		// button to try login
		btnLogin = new JButton("LOGIN");	
		btnLogin.setBounds(50,200,400,50);
		btnLogin.addActionListener(inputL);
		loginPanel.add(btnLogin);
	
	} // setPage()

	// if you click 'login' Button, this Listener class operate
	// first. if you not fill 'id field', warning message show up
	// second. if you not fill 'password field', warining message show up
	// then make SMTPSender object and send information to it.
	// if it login success, then chagne login Panel to WriteMail Panel
	// or, just pop up login fail message and clear the fields
	private class InputListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {
			
			Object obj = event.getSource();

			// get information from text fields
			// user id, user password, server name
			strUser = txtID.getText();
			strPass = txtPass.getText();
			strSmtpServer = "smtp." + strMail[cmbMail.getSelectedIndex()];

			try {
				// check null state
				// if you click login button when you do not input any alphabet in the text field(id & pw),
				// the error message pop up.
				if (strUser.equals(""))
					JOptionPane.showMessageDialog(null,"Input ID!","WARNING_MESSAGE",JOptionPane.WARNING_MESSAGE);
				else if (strPass.equals(""))
					JOptionPane.showMessageDialog(null,"Input PASSWORD!","WARNING_MESSAGE",JOptionPane.WARNING_MESSAGE);
				else {
					// create SMTPSender object
					// send information to an object of SMTPSender class
					smtpSender = new SMTPSender(strUser,strPass,strSmtpServer);
					
					// if we success to login,
					// pop up login success message and remove login Panel
					// to change panel, change big logo to small one then, add writeMail Panel
					if(smtpSender.login() == 1) {
						// it is state of login success
						loginFlag = 1;
						JOptionPane.showMessageDialog(null,"LOGIN SUCCESS!");
						// change logo of primary panel
						remove(loginPanel);
						btnLogo.setIcon(imgLogo_small);
						btnLogo.setBounds(800,0,200,50);
						btnLogo.setBorderPainted(false);
						btnLogo.setContentAreaFilled(false);
						btnLogo.setFocusPainted(false);
						
						// add writeMail Panel instead of login Panel
						// when you create writeMail, the smtpSender object is also delivered.
						writeMail = new WriteMail(smtpSender);
						writeMail.setBounds(0,50,1000,590);
						add(writeMail);
						validate();
						repaint();
					} 
					// if we fail to login
					// pop up login fail maessage and clear text field
					else {
						JOptionPane.showMessageDialog(null,"LOGIN FAIL!","WARNING_MESSAGE",JOptionPane.WARNING_MESSAGE);
						// reset text fields
						txtID.setText("");
						txtPass.setText("");
					}
				}
			// exception about I/O
			} catch (IOException e){
           		// TODO Auto-generated catch block
           		// find error message resource and print error message step by step
           		e.printStackTrace();
           	// exception about everything except I/O
            } catch (Exception e){
           		 // TODO Auto-generated catch block
           		e.printStackTrace();
           	} // try-catch
		} // actionPerformed()

	} // InputListener class

	// action listener that operates when the logo button pressed
	// if you click 'logo' Button, this Listener class operate
	// it make pop up message show up about 'do you want to go back to login page'.
	// if you click 'yes' option, then remove writeMail Panel and make login Panel again.
	// of course, login flag change to '0'
	// else, just clear the mail form.
	private class BtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {

			Object obj = event.getSource();

			// this listener just operate in login state.
			if (loginFlag == 1) {
				int result = JOptionPane.showConfirmDialog(null, "Do you want to go Login Page?", "", JOptionPane.YES_NO_OPTION);
				// if you click 'yes' option, remove writeMail Panel and change logo to big one.
				// then set login Panel in Primary.
				// also loginFlag get '0'
				if (result == JOptionPane.YES_OPTION) {
					remove(writeMail);
					btnLogo.setIcon(imgLogo);
					btnLogo.setBounds(305,100,400,100);
					btnLogo.setBorderPainted(false);
					btnLogo.setContentAreaFilled(false);
					btnLogo.setFocusPainted(false);

					setPage();

					loginFlag = 0;
					repaint();
				} 
				// if you click 'no' option,
				// just clear the mail form.
				else {
					writeMail.setMailForm();
				}
			} // if
		} // actionPerformed()

	} // BtnListener class

} // PrimaryPanel class

