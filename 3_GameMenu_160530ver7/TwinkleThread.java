// Twinkle Star Game Thread

import java.awt.*;
import javax.swing.*;

public class TwinkleThread extends JLabel implements Runnable
{
	// case 1 :: count down thread
	// case 2 :: star thread
	// x, y coordinatae
	// nCount :: twinkle time
	private int nCase, nX, nY, nCount;
	private Thread countThread;

	// method
	// constructor
	public TwinkleThread() {

		countThread = null;
	}

	// n :: twinkle time
	public TwinkleThread(int n) {
		nCase = n;
		countThread = null;
	}

	// set how many times twinkle
	public void setCount(int c) { nCount = c; }

	// start run()
	public void start() {
		if (countThread == null) {
			countThread = new Thread(this);
		}
		countThread.start();
	}

	// stop run()
	public void stop() {
		if (countThread != null) {
			countThread.stop();
		}
	}

	public void run() {

		// exception handling
		try {
			switch (nCase) {
				// count down thread
				case 1:
					for (int i=5; i>=1; i--) {

						setText(""+i);
						countThread.sleep(1000); // 1 sec pause
					}
					setVisible(false);
					countThread.sleep(4100); // after 4.1 sec pause, show "?"
					setText("?");
					setVisible(true);
					break;
				// twinkle star thread
				case 2:
					countThread.sleep(5000); // after 5 sec pause\
					// twinkle star thread
					for (int i=0; i<nCount; i++) {
						// set random x, y coordinate
						nX = (int)(Math.random()*660) + 30;
						nY = (int)(Math.random()*490) + 30;
						setBounds(nX,nY,30,30);
						setText("★");
						countThread.sleep(250); // inteval time is 0.25 sec
					}
					setVisible(false);
					break;
			}
		}catch(Exception e) {}

	} // run()

} // LabelThread class