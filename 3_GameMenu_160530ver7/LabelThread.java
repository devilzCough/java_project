import javax.swing.*; 
import java.awt.*; 

//LabelThread for HighLowGame

public class LabelThread extends JLabel implements Runnable{

	private int nStart,nEnd; 
	private Thread numberThread; 
	
	private void initData(int s, int e){ 
		nStart = s;
		nEnd = e;
		numberThread = null;
	}

	public LabelThread(){ 
		initData(1,100); 
	}

	public LabelThread(String str){ 
		super(str);
		initData(1,100); 
	}
	
	public LabelThread(int s, int e){
		initData(s,e); 
	}
	
	public LabelThread(String str, int s, int e){ 
		super(str); 
		initData(s,e);
	}

	public int getStart(){return nStart;} 
	public int getEnd(){return nEnd;} 

	public void setStart(int s){nStart = s;}
	public void setEnd(int e){nEnd =e;} 
	

	public void start(){

		if(numberThread == null) 
			numberThread  = new Thread(this); 
		
		numberThread.start(); 
	}

	public void stop(){
		if(numberThread != null) 
			numberThread.stop(); 
	}

	public void run(){

		try{

			setForeground(Color.gray); 

			for(int i = nStart; i <= nEnd ; i++){
				setText(""+i); 
				numberThread.sleep(100); 
			}
			setForeground(Color.red); 

			
			for(int j = 0; j <5 ;j++){
				setVisible(false);
				numberThread.sleep(200);
				setVisible(true);
				numberThread.sleep(200);
			} 
		} catch (Exception e){ } 
	} // run()
} //  LabelThread class