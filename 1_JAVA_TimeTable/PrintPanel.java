// PrintPanel class
// setting labels about class information which put on output Panel

import java.awt.*;
import javax.swing.*;

public class PrintPanel extends JPanel
{
	private JLabel nolbl, sublbl, prolbl, roomlbl, daylbl, timelbl, creditlbl;  // class information labels
	private int plusHour;       // to calculate class ending time :: adding value
	private String minStr;      // to set minutes value to string
	private int nCredit;        // to return credit value
	private int y;              // to set y-coordinate

	// constructor
	public PrintPanel() {

		nolbl = new JLabel("");      
		sublbl = new JLabel("");
		prolbl = new JLabel("");
		roomlbl = new JLabel("");
		daylbl = new JLabel("");
		timelbl = new JLabel("");
		creditlbl = new JLabel("");

		plusHour = 0;

		minStr = "00";

		y = 30;  // 1st label y-coordinate
	} // PrintPanel()

	// get method
	// return class information labels
	public JLabel getNoLabel() { return nolbl;}
	public JLabel getSubLabel() { return sublbl; }
	public JLabel getProLabel() { return prolbl; }
	public JLabel getRoomLabel() { return roomlbl; }
	public JLabel getDayLabel() { return daylbl; }
	public JLabel getTimeLabel() { return timelbl; }
	public JLabel getCreditLabel() { return creditlbl; }
	// return credit value to calculate total credit
	public int getCredit() { return nCredit; }

	// set method
	// set class label information
	public void setPrintPanel(int no, String subject, String professor, String room, int hour, int min, String timeLong, int credit) {

		nolbl.setText(""+no);
		sublbl.setText(subject);
		prolbl.setText(professor);
		roomlbl.setText(room);

		creditlbl.setText(""+credit);
		// put credit value in nCredit to return
		nCredit = credit;

		// if min value has 30, change minStr "30"
		if (min == 30)
			minStr = "30";

		// set timelbl in every class time cases
		if (timeLong == "60M") {    // ex. 1:00 - 2:00  :: just adding 1 hour
			plusHour = 1;
			timelbl.setText(hour+":"+minStr+"-"+(hour+plusHour)+":"+minStr);
		} else if (timeLong == "90M") {
			if (min == 0) {     // ex. 1:00 - 2:30  :: add 1hour and setting minutes to "30"
				plusHour = 1;
				timelbl.setText(hour+":"+minStr+"-"+(hour+plusHour)+":"+"30");
			} else {            // ex. 1:30 - 3:00  :: add 2 hour and setting minutes to "00"
				plusHour = 2;
				timelbl.setText(hour+":"+minStr+"-"+(hour+plusHour)+":"+"00");
			}
		} else if (timeLong == "120M") {    // ex. 1:00 - 3:00  :: just adding 2 hour
			plusHour = 2;
			timelbl.setText(hour+":"+minStr+"-"+(hour+plusHour)+":"+minStr);
		} else if (timeLong == "180M") {    // ex. 1:00 - 4:00  :: just adding 3 hour 
			plusHour = 3;
			timelbl.setText(hour+":"+minStr+"-"+(hour+plusHour)+":"+minStr);
		} // if ... else
	} // setPrintPanel()

	// set day label
	public void setDayLabel(String day1, String day2) {

		// if the class just one day, just set day1
		// else set both days
		if(day2 == "") 
			daylbl.setText(day1);
		else
			daylbl.setText(day1+","+day2);
			
	} // setDayLabel()

	// set y-coordinate
	// we get a ArrayList size as a parameter when we input information
	public void setYPoint(int yIndex) {
		// so, if the 1st and 2nd label, we get a y-coordinate like this
		// 30(1st label y-coordinate) + (1-1)*height(20) = 30+ 0 = 30   :: 1st label y-coordinate
		// 30(1st label y-coordinate) + (2-1)*height(20) = 30+20 = 50   :: 2nd label y-coordinate
		y = 30 + (yIndex-1)*PrintConstants.HEIGHT; 
	} // setYPoint()

	// setting information labels
	// about subject number, subject name, professor, room, time, day, credit
	public void putPrintPanel() {

		nolbl.setFont(new Font("Verdana",Font.PLAIN,10));
		nolbl.setBounds(PrintConstants.SUBNO,y,50,PrintConstants.HEIGHT);
       	
       	Font pfnt = new Font("바탕",Font.PLAIN,13);

       	sublbl.setFont(pfnt);
		sublbl.setBounds(PrintConstants.SUBJECT,y,220,PrintConstants.HEIGHT);

		prolbl.setFont(pfnt);
		prolbl.setBounds(PrintConstants.PROFESSOR,y,100,PrintConstants.HEIGHT);
		
		roomlbl.setFont(pfnt);
      	roomlbl.setBounds(PrintConstants.ROOM,y,60,PrintConstants.HEIGHT);

      	timelbl.setFont(pfnt);
      	timelbl.setBounds(PrintConstants.TIME,y,100,PrintConstants.HEIGHT);

      	daylbl.setFont(pfnt);
      	daylbl.setBounds(PrintConstants.DAY,y,60,PrintConstants.HEIGHT);

		creditlbl.setFont(pfnt);
		creditlbl.setBounds(PrintConstants.CREDIT,y,50,PrintConstants.HEIGHT);
		creditlbl.setHorizontalAlignment(SwingConstants.CENTER);
		creditlbl.setVerticalAlignment(SwingConstants.CENTER);

	} // putPrintPanel()

} // PrintPanel class
