/*** This is a 'WriteMail' class     ***/
/*** to write mail in the mail form  ***/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.*;
import javax.swing.filechooser.*;


public class WriteMail extends JPanel
{
	private JPanel writePanel, statePanel;
	private JLabel lblRecp, lblTitle, lblFile;
	private JTextField txtRecp,txtTitle;
	private JButton btnAttach, btnSend;
	private JTextArea fileArea, textArea, stateArea;
	private JScrollPane fileScroll, textScroll, stateScroll;

	private String strTitle, strRecp, strContents;
	private String[] filePath, fileName;
	private int nFile = 0;

	// SMTPSender class object
	private SMTPSender smtpSender;

	// Listener
	private InputListener inputL;
	private ChooseFileListener chooseFileL;


	public WriteMail(SMTPSender sender) {
		
		setBackground(Color.white);
		setPreferredSize(new Dimension(1000,590));
		setLayout(null);

		smtpSender = sender; // it's because to use same object
		
		// Listener
		inputL = new InputListener();
		chooseFileL = new ChooseFileListener();

		// two Large Panel
		// writePanel : left Panel, statePanel : right Panel
		writePanel = new JPanel();
		writePanel.setBackground(Color.white);
		writePanel.setBounds(0,0,650,590);
		writePanel.setLayout(null);
		add(writePanel);

		statePanel = new JPanel();
		statePanel.setBackground(Color.white);
		statePanel.setBounds(650,0,350,590);
		statePanel.setLayout(null);
		add(statePanel);

		// writePanel
		// Recipient
		lblRecp = new JLabel("  Recipient");
		lblRecp.setBounds(0,0,100,40);
		lblRecp.setFont(new Font("Verdana", Font.PLAIN,17));
		writePanel.add(lblRecp);

		txtRecp = new JTextField();
		txtRecp.setBounds(100,0,450,40);
		writePanel.add(txtRecp);

		// Title
		lblTitle = new JLabel("  Title");
		lblTitle.setBounds(0,40,100,40);
		lblTitle.setFont(new Font("Verdana", Font.PLAIN,17));
		writePanel.add(lblTitle);

		txtTitle = new JTextField();
		txtTitle.setBounds(100,40,450,40);
		writePanel.add(txtTitle);

		// File Attach
		lblFile = new JLabel("  File");
		lblFile.setBounds(0,80,100,40);
		lblFile.setFont(new Font("Verdana", Font.PLAIN,17));
		writePanel.add(lblFile);

		btnAttach = new JButton("+");
		btnAttach.setBounds(560,80,80,40);
		btnAttach.setFont(new Font("Verdana", Font.PLAIN,17));
		btnAttach.addActionListener(chooseFileL);
		writePanel.add(btnAttach);

		fileArea = new JTextArea(100,80);
		fileArea.setEditable(false);
		writePanel.add(fileArea);

		fileScroll = new JScrollPane(fileArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		fileScroll.setBounds(100,80,450,80);
		writePanel.add(fileScroll);

		filePath = new String[10];
		fileName = new String[10];

		//
		btnSend = new JButton("SEND");
		btnSend.setBounds(560,0,80,80);
		btnSend.addActionListener(inputL);
		writePanel.add(btnSend);

		textArea = new JTextArea(20,130);
		textArea.setFont(new Font("Verdana",Font.PLAIN,17));
		writePanel.add(textArea);

		textScroll = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		textScroll.setBounds(20,170,610,410);
		writePanel.add(textScroll);


		// statePanel
		stateArea = new JTextArea(10,0);
		stateArea.setEditable(false);
		statePanel.add(stateArea);

		stateScroll = new JScrollPane(stateArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		stateScroll.setBounds(10,0,330,580);
		statePanel.add(stateScroll);

		// set this WriteMail object
		smtpSender.setWriteMail(this);

	} // WriteMail()

	// if you want to init mail form, click logo and click 'No' option
	// you can send mail in same account
	public void setMailForm() {

	 	txtRecp.setText(""); 
	 	txtTitle.setText("");
		textArea.setText("");
		fileArea.setText("");
		stateArea.setText("");

		nFile = 0;
	} // setMailForm()
	
	// to show client-server communication in 'stateArea'
	public void setStateAreaText(String contents) {

		stateArea.setText(stateArea.getText()+"\n"+contents);
		validate(); // ..check..
		// repaint();
	} // setStateAreaText()
	
	// if you click 'send' Button, this Listener class operate
	// first. if you not fill 'Recipient field', warning message show up
	// second. if you not fill 'Title field', warining message show up
	// then send all data through 'smtpSender class'
	private class InputListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {
			
			Object obj = event.getSource();

			try {
				// check if the field is empty
				if (txtRecp.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"Input Recipient!","WARNING_MESSAGE",JOptionPane.WARNING_MESSAGE);
				} 
				else if (txtTitle.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"Input Title!","WARNING_MESSAGE",JOptionPane.WARNING_MESSAGE);
				} else {
					// get information from the text field
					strTitle = txtTitle.getText();
					strRecp = txtRecp.getText();
					strContents = textArea.getText();

					// send mail with mail information
					smtpSender.mailing(strTitle, strRecp, strContents, filePath, fileName, nFile);
				}
			} catch (Exception e) {
          		// TODO Auto-generated catch block
           		e.printStackTrace();
           	} // try-catch
		} // actionPerformed()
	} // InputListener class

	// if you click '+' Button, this Listener class operate
	// open file chooser and filter 'jpg'&'png' images 
	// get filepath and filename and set the filename in the 'fileArea'
	// you can send max. 10 files
	private class ChooseFileListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {
			
			Object obj = event.getSource();

			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images","jpg","png");
			chooser.setFileFilter(filter);
			
			// chooser.showOpenDialog(parent) Return Value
			// : JFileChooser.CANCEL_OPTION, JFileChooser.APPROVE_OPTION, JFileChooser.ERROR_OPTION
			// after open filechooser, if you click 'open' Button or 'save' Button, this return 'APPROVE_OPTION'
			// if you click 'cancel' Button, this function return 'CANCEL_OPTION'
			// If the parent is 'null', then the dialog depends on no visible window, 
			// and it's placed in a look-and-feel-dependent position such as the center of the screen.
			int ret = chooser.showOpenDialog(null);
			if (ret == JFileChooser.APPROVE_OPTION)
			{
				filePath[nFile] = chooser.getSelectedFile().getPath();
				fileName[nFile] = chooser.getSelectedFile().getName();
				fileArea.setText(fileArea.getText() + fileName[nFile] + "\n");
				nFile++;
			}
		} // actionPerformed()
	} // ChooseFileListener class
	
} // WriteMail class

