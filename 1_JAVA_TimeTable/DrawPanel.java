// DrawPanel class
// setting class Panels which put on timeTablePanel

import java.awt.*;
import javax.swing.*;

public class DrawPanel extends JPanel
{
	private JPanel class1, class2;  // class Panel :: need max 2 panel
	private int x1, x2, yHour;      // for class panel x,y-coordinate, x-coordinate need max 2
	private int height;             // for class panel height

	private int r,g,b;              // for panel background color
	private Color tableColor;

	// constructor
	public DrawPanel() {

		class1 = new JPanel();
		class2 = new JPanel();

		x1 = x2 = yHour = 0;

		height = 0;

		// initiate Panel background color Random
		r = (int)(Math.random()*255)+1;
		g = (int)(Math.random()*255)+1;
		b = (int)(Math.random()*255)+1;

		tableColor = new Color(r,g,b);
	} // DrawPanel()

	// get method
	public int getDay1Point() { return x1; }       // return x-coordinate about 1st class panel
	public int getDay2Point() { return x2; }       // return x-coordinate about 2nd class panel
	public JPanel getPanel1() { return class1; }   // return 1st class panel
	public JPanel getPanel2() { return class2; }   // return 2nd class panel

	// set method
	// set x-coordinate about 2 panel
	// if need just 1 panel, day2's x-coordinate gets 0 
	public void setDayPoint(int day1, int day2) {
		x1 = day1;
		x2 = day2;
	} // setDayPoint()

	// set y-coordinate about 2 panel
	// input hour 9 to 5, and min 00 or 30
	public void setHourPoint(int hour, int min) {

		switch(hour) {

			case 9 :
				yHour = DrawConstants.HOUR9;
				break;
			case 10 : 
				yHour = DrawConstants.HOUR10;
				break;
			case 11 :
				yHour = DrawConstants.HOUR11;
				break;
			case 12 :
				yHour = DrawConstants.HOUR12;
				break;
			case 1 :
				yHour = DrawConstants.HOUR1;
				break;
			case 2 :
				yHour = DrawConstants.HOUR2;
				break;
			case 3 :
				yHour = DrawConstants.HOUR3;
				break;
			case 4 :
				yHour = DrawConstants.HOUR4;
				break;
			case 5 :
				yHour = DrawConstants.HOUR5;
		} // switch

		// add height, if the class need additional 30 minutes
		if (min == 30)
		 	yHour = yHour + DrawConstants.MIN30;
	} // setHourPoint()

	// set class panel height, depending on class time
	public void setTimeLong(String timeLong) {

		if (timeLong == "60M")
			height = DrawConstants.TIME60;
		else if (timeLong == "90M")
			height = DrawConstants.TIME90;
		else if (timeLong == "120M")
			height = DrawConstants.TIME120;
		else if (timeLong == "180M")
			height = DrawConstants.TIME180;
		
	} // setTimeLong()

	// if user choose the color, table color is changed
	public void setColor(Color chooseColor) {

		tableColor = chooseColor;
	}

	// setting class panel
	public void putClassTable() {

		class1.setBackground(tableColor);
		class1.setBounds(x1,yHour,DrawConstants.WIDTH,height);
		class1.setLayout(null);  // because we set the label on the Panel

		// if x2 gets some coordinate, 2nd panel also puts in timeTablePanel
		if (x2 != 0) {
			class2.setBackground(tableColor);
			class2.setBounds(x2,yHour,DrawConstants.WIDTH,height);
			class2.setLayout(null);
		} 
	} // putClassTable()
	
} // DrawPanel class
	