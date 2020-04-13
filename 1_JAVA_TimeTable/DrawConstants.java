// DrawConstants class
// define constants about timeTablePanel

import java.awt.*;

public class DrawConstants
{
	// DrawPanel _ Panel
	// x - coordinate :: timeTablePanel
	public static final int MON = 35;
	public static final int TUS = 115;
	public static final int WED = 195;
	public static final int THUR = 275;
	public static final int FRI = 355;

	// y - coordinate :: timeTablePanel
	public static final int HOUR9 = 68;
	public static final int HOUR10 = 136;
	public static final int HOUR11 = 204;
	public static final int HOUR12 = 272;
	public static final int HOUR1 = 340;
	public static final int HOUR2 = 408;
	public static final int HOUR3 = 476;
	public static final int HOUR4 = 544;
	public static final int HOUR5 = 612;

	// if class has plus 30 min, need adding that height
	public static final int MIN30 = 34;

	// class panel height for 1h, 1.5h, 2h, 3h class
	public static final int TIME60 = 68;
	public static final int TIME90 = 102;
	public static final int TIME120 = 136;
	public static final int TIME180 = 204;
	// class panel width
	public static final int WIDTH = 80;


	// InputTableInfo _ Label 
	public static final int XL = 10;       // x - coordinate, which label start
	public static final int WIDTHL = 60;   // width, that labels are written
	public static final int HEIGHTL = 11;  // height, about one line label >> total : 4 line labels are written

	// height, class panel edge to class label
	public static final int L60M = 12;
	public static final int L90M = 29;
	public static final int L120M = 46;
	public static final int L180M = 80;
} // DrawConstants class
