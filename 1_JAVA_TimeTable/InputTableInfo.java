// InputTableInfo Panel
// setting labels about class information which put on class panels

import java.awt.*;
import javax.swing.*;

public class InputTableInfo
{
	private int stringSize;  // to seperate Subject Name String

	// each label needs max 2, because class panels are made max 2
	// subject name label need max 2, because that can be seprated
	// 3 kind labels :: subject name label, room label, professor label
	private JLabel lblSubName1, lblSubName2, lblRoom, lblProName;
	private JLabel lblSubName12, lblSubName22, lblRoom2, lblProName2;

	private int y; // for label y-coordinate

	// constructor _ initiating
	public InputTableInfo() {

		stringSize = 0;

		lblSubName1 = new JLabel("subject");
		lblSubName2 = new JLabel("");
		lblRoom = new JLabel("room");
		lblProName = new JLabel("professor");

		lblSubName12 = new JLabel("subject");
		lblSubName22 = new JLabel("");
		lblRoom2 = new JLabel("room");
		lblProName2 = new JLabel("professor");

		y = 0;
	} // InputTableInfo()

	// get method
	// return subject name label, room label, professor label
	// class panel 1
	public JLabel getSubjectLabel1() { return lblSubName1; }
	public JLabel getSubjectLabel2() { return lblSubName2; }
	public JLabel getRoomLabel() { return lblRoom; }
	public JLabel getProfessorLabel() { return lblProName; }
	// class panel 2
	public JLabel getSubjectLabel12() { return lblSubName12; }
	public JLabel getSubjectLabel22() { return lblSubName22; }
	public JLabel getRoomLabel2() { return lblRoom2; }
	public JLabel getProfessorLabel2() { return lblProName2; }

	// set method
	// setting labels about class information
	public void setTableInfo(String timeLong, String subject, String room, String professor) {

		// get subject string length
		stringSize = subject.length();

		// if string size is under 10, just use 1 label per 1 class panel
		if (stringSize < 10) {
			lblSubName1.setText(subject);
			lblSubName12.setText(subject);
		// if string size is over 10, use 2 labels per 1 class panel
		} else {
			// seperate string 10 byte in 1st label, and the remains put on 2nd label
			lblSubName1.setText(subject.substring(0,10));
			lblSubName2.setText(subject.substring(10));
			lblSubName12.setText(subject.substring(0,10));
			lblSubName22.setText(subject.substring(10));
		} // if - else

		// set room and professor information in each label
		lblRoom.setText(room);
		lblProName.setText(professor);
		lblRoom2.setText(room);
		lblProName2.setText(professor);

		// each class time, labels get different y-coordinate
		if (timeLong == "60M") {
			y = DrawConstants.L60M;

		} else if (timeLong == "90M") {
			y = DrawConstants.L90M;

		} else if (timeLong == "120M") {
			y = DrawConstants.L120M;

		} else if (timeLong == "180M") {
			y = DrawConstants.L180M;
		} // if ... else
	} // setTableInfo()

	// setting information labels
	public void putTableInfo() {

		Font infoFnt = new Font("Verdana",Font.PLAIN,10);

		// labels part gets total 44 height
		// subject name label 1 :: y 
		// subject name label 2 :: y + 11
		// room label           :: y + 22
		// professor label      :: y + 33
		
		// labels for 1st class panel
		lblSubName1.setForeground(Color.white);
		lblSubName1.setFont(infoFnt);
		lblSubName1.setBounds(DrawConstants.XL,y,DrawConstants.WIDTHL,DrawConstants.HEIGHTL);

		lblRoom.setForeground(Color.white);
		lblRoom.setFont(infoFnt);
		lblRoom.setBounds(DrawConstants.XL,y+22,DrawConstants.WIDTHL,DrawConstants.HEIGHTL);

		lblProName.setForeground(Color.white);
		lblProName.setFont(infoFnt);
		lblProName.setBounds(DrawConstants.XL,y+33,DrawConstants.WIDTHL,DrawConstants.HEIGHTL);

		// labels for 2nd class panel
		lblSubName12.setFont(infoFnt);
		lblSubName12.setForeground(Color.white);
		lblSubName12.setBounds(DrawConstants.XL,y,DrawConstants.WIDTHL,DrawConstants.HEIGHTL);

		lblRoom2.setForeground(Color.white);
		lblRoom2.setFont(infoFnt);
		lblRoom2.setBounds(DrawConstants.XL,y+22,DrawConstants.WIDTHL,DrawConstants.HEIGHTL);

		lblProName2.setForeground(Color.white);
		lblProName2.setFont(infoFnt);
		lblProName2.setBounds(DrawConstants.XL,y+33,DrawConstants.WIDTHL,DrawConstants.HEIGHTL);
		
		// if subject name string OVER 10 byte, 2nd label gets some string
		if (lblSubName2.getText() != "") {
			// label for 1st class panel
			lblSubName2.setForeground(Color.white);
			lblSubName2.setFont(infoFnt);
			lblSubName2.setBounds(DrawConstants.XL,y+11,DrawConstants.WIDTHL,DrawConstants.HEIGHTL);
			// label for 2nd class panel
			lblSubName22.setForeground(Color.white);
			lblSubName22.setFont(infoFnt);
			lblSubName22.setBounds(DrawConstants.XL,y+11,DrawConstants.WIDTHL,DrawConstants.HEIGHTL);
		} // if
	} // putTableInfo()

} // InputTableInfo class
