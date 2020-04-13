// PrimaryPanel class
// get three main Panel, and add class information in each panels
// get some inner classes (listener classes)
// :: SelectDayListener, InputSubjectListener, TimeSelectListener, ChooseColorListener, DeleteListener

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class PrimaryPanel extends JPanel
{
   //data

   // Main 3 Panels
   private JPanel timeTablePanel;      // Panel 1 :: Time Table
   private JPanel inputPanel;          // Panel 2 :: Input Information
   private JPanel outputPanel;         // Panel 3 :: Print Information
   
   // Panel 1 _ TimeTable
   private JLabel lblTitle;    // title label
   private JLabel lblMon, lblTue, lblWed, lblThur, lblFri;     // day label :: mon - fri
   private JLabel lbl09, lbl10, lbl11, lbl12, lbl01, lbl02, lbl03, lbl04, lbl05;     // time label :: AM 9 - PM 5

   // Panel 1 :: DrawPanel
   private ArrayList<DrawPanel> tableList;   // class panel List
   private DrawPanel drawTable, saveTable;   // DrawPanel object
   
   // Panel 1 :: InputTableInfo
   private ArrayList<InputTableInfo> tableInfoList;    // class information label List :: on class Panels in timeTablePanel
   private InputTableInfo inputInfo, saveInfo;         // InputTableInfo object

   
   // Panel 2 _ Input Information
   private JLabel lblNo, lblSubject, lblPro, lblRoom, lblDay, lblTime, lblPoint;   // input contents label
   private JLabel lbldda; // :
   // input class information TextField
   private JTextField noField, subjectField, proField, roomField, hourField, minField, pointField;
   
   private JCheckBox monBtn, tusBtn, wedBtn, thrBtn, friBtn; // CheckBox :: mon - fri
   private JComboBox timeComboBox;  // ComboBox :: class time _ 60M, 90M, 120M, 180M
   private JButton inputBtn;        // INPUT Button
   // Color chooser
   private JPanel colorBack;        // back Panel
   private JPanel colorFront;       // front Panel
   private Color chooseColor;       // chosen Color

   // get input values
   // subject number, subject name, room, professor, day(max 2 days), time(hour, min), class time, credit 
   private int subjectNo;
   private String subjectName, professorName, roomName;
   private String day, day2;
   private int hour, min;
   private String timelong;
   private int point;
   

   // Panel 3 _ Print Information
   private JLabel lblNo2, lblSubject2, lblPro2, lblRoom2, lblDay2, lblTime2, lblPoint2;  // information seperating labels
   private JLabel lblSum, lblCreditSum;   // total credit & crdit value labels
   private int creditSum;                 // credit value
   private JButton[] deleteArray;         // delete button
   private int getIndex;                  // class index

   //  Panel 3 :: PrintPanel
   private ArrayList<PrintPanel> outputList;   // class information label List :: in outputPanel
   private PrintPanel inputline, saveline;     // PrintPanel object


   // Event Listener 
   private SelectDayListener selectDayL;          // when select day in CheckBox
   private InputSubjectListener inputSubjectL;    // when clicked INPUT Button
   private TimeSelectListener timeSelectL;        // when select class time in ComboBox
   private ChooseColorListener chooseColorL;      // when choose Color in ColorChooser
   private DeleteListener deleteL;                // when clicked DEL Button
   
   
   // constructor   
   public PrimaryPanel(){
      
      // PrimaryPanel size & color
      setPreferredSize(new Dimension(1200,700));
      setBackground(Color.gray);
      setLayout(null);
      
      // Event Listener
      selectDayL = new SelectDayListener();
      inputSubjectL = new InputSubjectListener();
      timeSelectL = new TimeSelectListener();
      chooseColorL = new ChooseColorListener();
      deleteL = new DeleteListener();
      

      // Panel 1 _ TimeTable
      timeTablePanel = new JPanel();
      timeTablePanel.setBounds(10,10,435,680);
      timeTablePanel.setBackground(Color.white);
      timeTablePanel.setLayout(null);
      add(timeTablePanel);
      
      // Panel 1 _ Labels setting
      // Title
      lblTitle = new JLabel("Time Table");
      lblTitle.setFont(new Font("Verdana",Font.BOLD,17));
      lblTitle.setBounds(150,10,135,28);
      lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
      lblTitle.setVerticalAlignment(SwingConstants.CENTER);
      timeTablePanel.add(lblTitle);

      // Day _ MON - FRI
      Font fntDay = new Font("Verdana",Font.PLAIN,15);

      lblMon = new JLabel("MON");
      lblMon.setFont(fntDay);
      lblMon.setBounds(DrawConstants.MON,48,80,20);
      lblMon.setHorizontalAlignment(SwingConstants.CENTER);
      lblMon.setVerticalAlignment(SwingConstants.CENTER);
      timeTablePanel.add(lblMon);

      lblTue = new JLabel("TUE");
      lblTue.setFont(fntDay);
      lblTue.setBounds(DrawConstants.TUS,48,80,20);
      lblTue.setHorizontalAlignment(SwingConstants.CENTER);
      lblTue.setVerticalAlignment(SwingConstants.CENTER);
      timeTablePanel.add(lblTue);

      lblWed = new JLabel("WED");
      lblWed.setFont(fntDay);
      lblWed.setBounds(DrawConstants.WED,48,80,20);
      lblWed.setHorizontalAlignment(SwingConstants.CENTER);
      lblWed.setVerticalAlignment(SwingConstants.CENTER);
      timeTablePanel.add(lblWed);

      lblThur = new JLabel("THUR");
      lblThur.setFont(fntDay);
      lblThur.setBounds(DrawConstants.THUR,48,80,20);
      lblThur.setHorizontalAlignment(SwingConstants.CENTER);
      lblThur.setVerticalAlignment(SwingConstants.CENTER);
      timeTablePanel.add(lblThur);

      lblFri = new JLabel("FRI");
      lblFri.setFont(fntDay);
      lblFri.setBounds(DrawConstants.FRI,48,80,20);
      lblFri.setHorizontalAlignment(SwingConstants.CENTER);
      lblFri.setVerticalAlignment(SwingConstants.CENTER);
      timeTablePanel.add(lblFri);

      // Time _ AM 9 - PM 5
      Font fntTime = new Font("Verdana",Font.PLAIN,12);

      lbl09 = new JLabel("09");
      lbl09.setFont(fntTime);
      lbl09.setBounds(15,DrawConstants.HOUR9,20,17);
      lbl09.setHorizontalAlignment(SwingConstants.CENTER);
      lbl09.setVerticalAlignment(SwingConstants.CENTER);
      timeTablePanel.add(lbl09);

      lbl10 = new JLabel("10");
      lbl10.setFont(fntTime);
      lbl10.setBounds(15,DrawConstants.HOUR10,20,17);
      lbl10.setHorizontalAlignment(SwingConstants.CENTER);
      lbl10.setVerticalAlignment(SwingConstants.CENTER);
      timeTablePanel.add(lbl10);

      lbl11 = new JLabel("11");
      lbl11.setFont(fntTime);
      lbl11.setBounds(15,DrawConstants.HOUR11,20,17);
      lbl11.setHorizontalAlignment(SwingConstants.CENTER);
      lbl11.setVerticalAlignment(SwingConstants.CENTER);
      timeTablePanel.add(lbl11);

      lbl12 = new JLabel("12");
      lbl12.setFont(fntTime);
      lbl12.setBounds(15,DrawConstants.HOUR12,20,17);
      lbl12.setHorizontalAlignment(SwingConstants.CENTER);
      lbl12.setVerticalAlignment(SwingConstants.CENTER);
      timeTablePanel.add(lbl12);

      lbl01 = new JLabel("01");
      lbl01.setFont(fntTime);
      lbl01.setBounds(15,DrawConstants.HOUR1,20,17);
      lbl01.setHorizontalAlignment(SwingConstants.CENTER);
      lbl01.setVerticalAlignment(SwingConstants.CENTER);
      timeTablePanel.add(lbl01);

      lbl02 = new JLabel("02");
      lbl02.setFont(fntTime);
      lbl02.setBounds(15,DrawConstants.HOUR2,20,17);
      lbl02.setHorizontalAlignment(SwingConstants.CENTER);
      lbl02.setVerticalAlignment(SwingConstants.CENTER);
      timeTablePanel.add(lbl02);

      lbl03 = new JLabel("03");
      lbl03.setFont(fntTime);
      lbl03.setBounds(15,DrawConstants.HOUR3,20,17);
      lbl03.setHorizontalAlignment(SwingConstants.CENTER);
      lbl03.setVerticalAlignment(SwingConstants.CENTER);
      timeTablePanel.add(lbl03);

      lbl04 = new JLabel("04");
      lbl04.setFont(fntTime);
      lbl04.setBounds(15,DrawConstants.HOUR4,20,17);
      lbl04.setHorizontalAlignment(SwingConstants.CENTER);
      lbl04.setVerticalAlignment(SwingConstants.CENTER);
      timeTablePanel.add(lbl04);

      lbl05 = new JLabel("05");
      lbl05.setFont(fntTime);
      lbl05.setBounds(15,DrawConstants.HOUR5,20,17);
      lbl05.setHorizontalAlignment(SwingConstants.CENTER);
      lbl05.setVerticalAlignment(SwingConstants.CENTER);
      timeTablePanel.add(lbl05);
         
      // DrawPanel :: add in timeTablePanel
      tableList = new ArrayList<DrawPanel>();
      drawTable = new DrawPanel();
      saveTable = new DrawPanel();

      // InputTableInfo :: add in DrawPanel
      tableInfoList = new ArrayList<InputTableInfo>();
      inputInfo = new InputTableInfo();
      saveInfo = new InputTableInfo();
      
      
      // Panel 2 _ Input Information
      inputPanel = new JPanel();
      inputPanel.setBounds(455,10,735,335);
      inputPanel.setBackground(Color.white);
      inputPanel.setLayout(null);
      add(inputPanel);

      // Panel 2 :: Label setting
      Font fnt = new Font("돋음",Font.BOLD,15);
      
      lblNo = new JLabel("Subject No ");
      lblNo.setFont(fnt);
      lblNo.setBounds(10,15,110,20);
      inputPanel.add(lblNo);

      lblSubject = new JLabel("Subject Name ");
      lblSubject.setFont(fnt);
      lblSubject.setBounds(10,60,120,20);
      inputPanel.add(lblSubject);

      lblPro = new JLabel("Professor ");
      lblPro.setFont(fnt);
      lblPro.setBounds(10,100,110,20);
      inputPanel.add(lblPro);

      lblRoom = new JLabel("Class Room ");
      lblRoom.setFont(fnt);
      lblRoom.setBounds(10,145,110,20);
      inputPanel.add(lblRoom);

      lblDay = new JLabel("Day ");
      lblDay.setFont(fnt);
      lblDay.setBounds(10,190,110,20);
      inputPanel.add(lblDay);

      lblTime = new JLabel("Time ");
      lblTime.setFont(fnt);
      lblTime.setBounds(10,235,110,20);
      inputPanel.add(lblTime);

      lblPoint = new JLabel("Credit ");
      lblPoint.setFont(fnt);
      lblPoint.setBounds(10,280,110,20);
      inputPanel.add(lblPoint);
      
      lbldda = new JLabel(":");
      lbldda.setBounds(175,235,10,30);
      inputPanel.add(lbldda);

      // Panel 2 :: TextField, CheckBox, ComboBox Setting
      Font fnt2 = new Font("바탕",Font.ITALIC,20);
      
      // Panel 2 _ TextField
      // InputSubjectListener
      noField = new JTextField();
      noField.setFont(fnt2);
      noField.setBounds(120,10,200,30);
      noField.addActionListener(inputSubjectL);
      inputPanel.add(noField);

      subjectField = new JTextField();
      subjectField.setFont(fnt2);
      subjectField.setBounds(120,55,200,30);
      subjectField.addActionListener(inputSubjectL);  
      inputPanel.add(subjectField);

      proField = new JTextField();
      proField.setFont(fnt2);
      proField.setBounds(120,100,200,30);
      proField.addActionListener(inputSubjectL);
      inputPanel.add(proField);

      roomField = new JTextField();
      roomField.setFont(fnt2);
      roomField.setBounds(120,145,200,30);
      roomField.addActionListener(inputSubjectL);
      inputPanel.add(roomField);

      hourField = new JTextField();
      hourField.setFont(fnt2);
      hourField.setBounds(120,235,50,30);
      hourField.addActionListener(inputSubjectL);
      inputPanel.add(hourField);

      minField = new JTextField();
      minField.setFont(fnt2);
      minField.setBounds(186,235,50,30);
      minField.addActionListener(inputSubjectL);
      inputPanel.add(minField);

      pointField = new JTextField();
      pointField.setFont(fnt2);
      pointField.setBounds(120,275,200,30);
      pointField.addActionListener(inputSubjectL);
      inputPanel.add(pointField);

      // Panel 2 _ Day CheckBox
      // SelectDayListener
      monBtn = new JCheckBox("월");
      monBtn.setBounds(120,190,50,30);
      monBtn.addItemListener(selectDayL);
      inputPanel.add(monBtn);

      tusBtn = new JCheckBox("화");
      tusBtn.setBounds(170,190,50,30);
      tusBtn.addItemListener(selectDayL);
      inputPanel.add(tusBtn);

      wedBtn = new JCheckBox("수");
      wedBtn.setBounds(220,190,50,30);
      wedBtn.addItemListener(selectDayL);
      inputPanel.add(wedBtn);

      thrBtn = new JCheckBox("목");
      thrBtn.setBounds(270,190,50,30);
      thrBtn.addItemListener(selectDayL);
      inputPanel.add(thrBtn);

      friBtn = new JCheckBox("금");
      friBtn.setBounds(320,190,50,30);
      friBtn.addItemListener(selectDayL);
      inputPanel.add(friBtn);

      // Panel 2 _ class Time ComboBox
      // TimeSelectListener
      String _day[] = {"선택","60M","90M","120M","180M"};   
      timeComboBox = new JComboBox();
      for (int i=0; i<5; i++)
         timeComboBox.addItem(_day[i]);

      timeComboBox.setBounds(250,235,90,30);
      timeComboBox.addActionListener(timeSelectL);
      inputPanel.add(timeComboBox);

      
      // INPUT Button
      // InputSubjectListener
      inputBtn = new JButton("I N P U T");
      inputBtn.setBounds(580,275,100,40);
      inputBtn.addActionListener(inputSubjectL);
      inputPanel.add(inputBtn);

      // Color Panel
      // ChooseColorListener
      colorBack = new JPanel();
      colorBack.setBounds(350,10,30,30);
      colorBack.setBorder(BorderFactory.createLoweredBevelBorder());
      colorBack.setLayout(null);
      inputPanel.add(colorBack);

      chooseColor = new Color(0,0,0);

      colorFront = new JPanel();
      colorFront.setBounds(4,4,22,22);
      colorFront.setBackground(chooseColor);
      colorFront.addMouseListener(chooseColorL);
      colorBack.add(colorFront);
      

      // Panel 3 _ Print Information
      outputPanel = new JPanel();
      outputPanel.setBounds(455,355,735,335);
      outputPanel.setBackground(Color.white);
      outputPanel.setLayout(null);
      add(outputPanel);

      Font fnt3 = new Font("Verdana", Font.BOLD, 13);
      
      // Panel 3 _ Label Setting
      lblNo2 = new JLabel("No");
      lblNo2.setBounds(10,0,50,30);
      lblNo2.setFont(fnt3);
      outputPanel.add(lblNo2);
      
      lblSubject2 = new JLabel("Subject");
      lblSubject2.setBounds(60,0,220,30);
      lblSubject2.setFont(fnt3);
      outputPanel.add(lblSubject2);   
      
      lblPro2 = new JLabel("Professor");
      lblPro2.setBounds(280,0,100,30);
      lblPro2.setFont(fnt3);
      outputPanel.add(lblPro2);   
      
      lblRoom2 = new JLabel("Room");
      lblRoom2.setBounds(380,0,60,30);
      lblRoom2.setFont(fnt3);
      outputPanel.add(lblRoom2);
      
      lblDay2 = new JLabel("Day");
      lblDay2.setBounds(440,0,60,30);
      lblDay2.setFont(fnt3);
      outputPanel.add(lblDay2);
      
      lblTime2 = new JLabel("Time");
      lblTime2.setBounds(500,0,100,30);
      lblTime2.setFont(fnt3);
      outputPanel.add(lblTime2);
      
      lblPoint2 = new JLabel("Credit");
      lblPoint2.setBounds(600,0,50,30);
      lblPoint2.setFont(fnt3);
      outputPanel.add(lblPoint2);
      
      lblSum = new JLabel("신청한 학점 : ");
      lblSum.setBounds(500,300,100,30);
      lblSum.setFont(new Font("돋움",Font.BOLD,13));
      outputPanel.add(lblSum);

      creditSum = 0;   // credit value

      lblCreditSum = new JLabel("0");
      lblCreditSum.setBounds(600,300,50,30);
      lblCreditSum.setFont(fnt3);
      outputPanel.add(lblCreditSum);

      // DEL Button Array[] Setting
      // DeleteListener
      deleteArray = new JButton[15];
      for (int i=0; i<15; i++) {
         deleteArray[i] = new JButton("DEL");
         deleteArray[i].setFont(new Font("Verdana",Font.BOLD,11));
         deleteArray[i].addActionListener(deleteL);
         outputPanel.add(deleteArray[i]);
      }

      // PrintPanel :: add in outputPanel    
      outputList = new ArrayList<PrintPanel>();
      inputline = new PrintPanel();
      saveline = new PrintPanel();
      
   } // PrimaryPanel()


   // initiate method
   // initiate all value in inputPanel
   public void initInputPanel() {

      // TextField :: ""
      noField.setText("");
      subjectField.setText("");
      proField.setText("");
      roomField.setText("");

      hourField.setText("");
      minField.setText("");
      pointField.setText("");

      // Color Panel :: black
      colorFront.setBackground(Color.black);

      // ComboBox :: "선택"
      timeComboBox.setSelectedIndex(0);

      // CheckBox :: non check
      monBtn.setSelected(false);
      tusBtn.setSelected(false);
      wedBtn.setSelected(false);
      thrBtn.setSelected(false);
      friBtn.setSelected(false);
           
   } // initInputPanel()

   
   // Event Listener...
   // SelectDayListener class
   // get day values which we select, and set
   private class SelectDayListener implements ItemListener     // :: JCheckBox
   {
      public void itemStateChanged(ItemEvent event) {
       
         // Select 1 Day 
         // if select a day, put the value in 'day' and in day2, put nothing
         // and set x-coordinate depending on selected day
         if (monBtn.isSelected()) {
            day = monBtn.getText();
            day2 = "";
            drawTable.setDayPoint(DrawConstants.MON,0);
         }
         
         if (tusBtn.isSelected()) {
            day = tusBtn.getText();
            day2 = "";
            drawTable.setDayPoint(DrawConstants.TUS,0);
         }
            
         if (wedBtn.isSelected()) {
            day = wedBtn.getText();
            day2 = "";
            drawTable.setDayPoint(DrawConstants.WED,0);
         }
         
         if (thrBtn.isSelected()) {
            day = thrBtn.getText();
            day2 = "";
            drawTable.setDayPoint(DrawConstants.THUR,0);
         }
          
         if (friBtn.isSelected()) {
            day = friBtn.getText();
            day2 = "";
            drawTable.setDayPoint(DrawConstants.FRI,0);
         }
         
         // Select 2 Days
         // if select 2 days, put the 1st value in 'day' and put the 2nd value in 'day2'
         // and set 2 x-coordinate depending on selected day
         if (monBtn.isSelected() && wedBtn.isSelected()) {
            day = monBtn.getText();
            day2 = wedBtn.getText();
            drawTable.setDayPoint(DrawConstants.MON,DrawConstants.WED);
         } 
         
         if (monBtn.isSelected() && friBtn.isSelected()) {
            day = monBtn.getText();
            day2 = friBtn.getText();
            drawTable.setDayPoint(DrawConstants.MON,DrawConstants.FRI);
         } 
         
         if (tusBtn.isSelected() && thrBtn.isSelected()) {
            day = tusBtn.getText();
            day2 = thrBtn.getText();
            drawTable.setDayPoint(DrawConstants.TUS,DrawConstants.THUR);
         }
         
         // set day labels depending on selected day
         inputline.setDayLabel(day, day2);

      } // actionPerformed()
   } // SelectDayListener class
   

   // InputSubjectListener class
   // when we clicked 'INPUT' Button,
   // set the information about the class (subject number, subject name, room, professor, time(hour,min), credit)
   // copy information and add in the ArrayList
   // add data in proper Panel 
   // then initiate the inputPanel
   private class InputSubjectListener implements ActionListener   // :: JButton
   {
      public void actionPerformed(ActionEvent event) {
            
         Object obj = event.getSource();

         // Click Input Button
         if (obj == inputBtn) {

            // to draw table Panel
            // set time information (hour, min)
            hour = Integer.parseInt(hourField.getText());
            min = Integer.parseInt(minField.getText()); 
            drawTable.setHourPoint(hour,min);
            drawTable.putClassTable();

            // to put information in table
            // set subject name, room, professor information
            subjectName = subjectField.getText();
            roomName = roomField.getText();
            professorName = proField.getText();
            inputInfo.setTableInfo(timelong,subjectName,roomName,professorName);
            inputInfo.putTableInfo();

            // to put print panel
            // set subject number, credit information
            subjectNo = Integer.parseInt(noField.getText());
            point = Integer.parseInt(pointField.getText());
            inputline.setPrintPanel(subjectNo,subjectName,professorName,roomName,hour,min,timelong,point);
            inputline.setYPoint(tableList.size());  // to set y-coordinate
            inputline.putPrintPanel();
            
            // calc credit
            creditSum = creditSum + point;
            lblCreditSum.setText(""+creditSum);


            // DrawPanel :: Data Copy and Add in the tableList
            saveTable.setDayPoint(drawTable.getDay1Point(),drawTable.getDay2Point()); 
            saveTable.setHourPoint(hour,min);
            saveTable.setTimeLong(timelong);
            saveTable.putClassTable();
            tableList.add(saveTable);

            saveTable = new DrawPanel();

            // InputTableInfo :: Data Copy and Add in the tableInfoList
            saveInfo.setTableInfo(timelong,subjectName,roomName,professorName);
            saveInfo.putTableInfo();
            tableInfoList.add(saveInfo);
            
            saveInfo = new InputTableInfo();

            // PrintPanel :: Data Copy and Add in the outputList
            saveline.setPrintPanel(subjectNo,subjectName,professorName,roomName,hour,min,timelong,point);
            saveline.setDayLabel(day, day2);
            saveline.setYPoint(tableList.size());
            saveline.putPrintPanel();
            outputList.add(saveline);

            saveline = new PrintPanel();

            outputPanel.add(lblCreditSum); // add total credit value in outputPanel

            // get Data and add in Proper Panel
            for (int i=0; i<tableList.size(); i++) {

               // get class Panel and add in timeTablePanel
               DrawPanel drawTemp = tableList.get(i);

               timeTablePanel.add(drawTemp.getPanel1());
               timeTablePanel.add(drawTemp.getPanel2());

               // get class information labels and add in class panels
               InputTableInfo infoTemp = tableInfoList.get(i);
   
               drawTemp.getPanel1().add(infoTemp.getSubjectLabel1());
               drawTemp.getPanel1().add(infoTemp.getSubjectLabel2());
               drawTemp.getPanel1().add(infoTemp.getRoomLabel());
               drawTemp.getPanel1().add(infoTemp.getProfessorLabel());

               drawTemp.getPanel2().add(infoTemp.getSubjectLabel12());
               drawTemp.getPanel2().add(infoTemp.getSubjectLabel22());
               drawTemp.getPanel2().add(infoTemp.getRoomLabel2());
               drawTemp.getPanel2().add(infoTemp.getProfessorLabel2());

               // get class information labels and add in outputPanel
               PrintPanel printTemp = outputList.get(i);

               outputPanel.add(printTemp.getNoLabel());
               outputPanel.add(printTemp.getSubLabel());
               outputPanel.add(printTemp.getProLabel());
               outputPanel.add(printTemp.getRoomLabel());
               outputPanel.add(printTemp.getDayLabel());
               outputPanel.add(printTemp.getTimeLabel());
               outputPanel.add(printTemp.getCreditLabel());

               // add delete button in outputPanel
               deleteArray[i].setBounds(660,(30+i*PrintConstants.HEIGHT),60,PrintConstants.HEIGHT);

            }

            initInputPanel();          // initiate inputPanel 
            timeTablePanel.repaint();  // repaint timeTablePanel and outputPanel
            outputPanel.repaint();
            
         } // if(inputBtn)
         
      }   // actionPerformed() 
   }   // InputSubjectListener class
   

   // TimeSelectListener class
   // get class time when we choose the time and set
   private class TimeSelectListener implements ActionListener   // :: JComboBox
   {
         public void actionPerformed(ActionEvent event)
         {
            JComboBox jcmbType = (JComboBox)event.getSource();
            String str = "";

            // get class time data and set for class Panel
            str = (String)jcmbType.getSelectedItem();
            timelong = str;
            
            drawTable.setTimeLong(timelong); 

         } // actionPerformed()
   } // TimeSelectListener class


   // ChooseColorListener class
   // clicked color panel
   // after choose color in ColorChooser Dialog,
   // set the color panel in inputPanel and the class Panels in timeTablePanel
   private class ChooseColorListener implements MouseListener
   {
      public void mouseClicked(MouseEvent event) {

         Object obj = event.getSource();

         // if clicked colorFront Panel
         if (obj == colorFront) {
            chooseColor = JColorChooser.showDialog(colorFront,"COLOR CHOOSER",Color.black);
            // change the colorFront Panel background color
            colorFront.setBackground(chooseColor);

            // set the class Panel background color
            drawTable.setColor(chooseColor);
            saveTable.setColor(chooseColor);
         }
      }

      public void mousePressed(MouseEvent event) {}
      public void mouseReleased(MouseEvent event) {}
      public void mouseEntered(MouseEvent event) {} 
      public void mouseExited(MouseEvent event) {}
   } // ChooseColorListener class


   // DeleteListener class
   // when we clicked the button,
   // find the button and get the button index
   // then we remove class Panels and information labels
   private class DeleteListener implements ActionListener 
   {
      public void actionPerformed(ActionEvent event) {

         Object obj = event.getSource();

         for (int i=0; i<outputList.size(); i++) {
            // if find the DEL button that we clicked
            if (obj == deleteArray[i]) {

               getIndex = i;

               // calc decrease credit
               PrintPanel printCreditTemp = outputList.get(i);
               creditSum = creditSum - printCreditTemp.getCredit();
               lblCreditSum.setText(""+creditSum);

               // all next DEL buttons after we clicked 
               // remove from output Panel
               for (int j=getIndex; j<15; j++) {
                  deleteArray[j].removeActionListener(deleteL);
                  outputPanel.remove(deleteArray[j]);
               }

               // all next class Panels and information labels after we clicked
               // remove from each proper panels
               for (int j=getIndex; j<tableList.size(); j++) {

                  // remove class panels from the timeTablePanel
                  DrawPanel drawTemp = tableList.get(j);

                  timeTablePanel.remove(drawTemp.getPanel1());
                  timeTablePanel.remove(drawTemp.getPanel2());

                  // remove information labels from the class panels
                  InputTableInfo infoTemp = tableInfoList.get(j);
      
                  drawTemp.getPanel1().remove(infoTemp.getSubjectLabel1());
                  drawTemp.getPanel1().remove(infoTemp.getSubjectLabel2());
                  drawTemp.getPanel1().remove(infoTemp.getRoomLabel());
                  drawTemp.getPanel1().remove(infoTemp.getProfessorLabel());

                  drawTemp.getPanel2().remove(infoTemp.getSubjectLabel12());
                  drawTemp.getPanel2().remove(infoTemp.getSubjectLabel22());
                  drawTemp.getPanel2().remove(infoTemp.getRoomLabel2());
                  drawTemp.getPanel2().remove(infoTemp.getProfessorLabel2());

                  // remove information labels from the outputPanel
                  PrintPanel printTemp = outputList.get(j);

                  outputPanel.remove(printTemp.getNoLabel());
                  outputPanel.remove(printTemp.getSubLabel());
                  outputPanel.remove(printTemp.getProLabel());
                  outputPanel.remove(printTemp.getRoomLabel());
                  outputPanel.remove(printTemp.getDayLabel());
                  outputPanel.remove(printTemp.getTimeLabel());
                  outputPanel.remove(printTemp.getCreditLabel());
            
               } // for

               // remove the item that we clicked
               // from the each ArrayList
               tableList.remove(i);
               tableInfoList.remove(i);
               outputList.remove(i);

               // add DEL button again in the outputPanel
               // from the index that we clicked
               for (int j=getIndex; j<15; j++) {
                  deleteArray[j] = new JButton("DEL");
                  deleteArray[j].setFont(new Font("Verdana",Font.BOLD,11));
                  deleteArray[j].addActionListener(deleteL);
                  outputPanel.add(deleteArray[j]);
               }

               // set labels y-coordinate again in outputPanel
               // from the index that we clicked
               for (int j=getIndex; j<tableList.size(); j++) {
                  PrintPanel printTemp = outputList.get(j);
                  printTemp.setYPoint(j+1);   // because the parameter is the size, we have to +1 in index
                  printTemp.putPrintPanel();
               }

               // add class Panels and information Labels again in each proper Panels
               // from the index that we clicked
               for (int j=getIndex; j<tableList.size(); j++) {

                  // add class Panels in timeTabelPanel
                  DrawPanel drawTemp = tableList.get(j);

                  timeTablePanel.add(drawTemp.getPanel1());
                  timeTablePanel.add(drawTemp.getPanel2());

                  // add class information in class Panels 
                  InputTableInfo infoTemp = tableInfoList.get(j);
      
                  drawTemp.getPanel1().add(infoTemp.getSubjectLabel1());
                  drawTemp.getPanel1().add(infoTemp.getSubjectLabel2());
                  drawTemp.getPanel1().add(infoTemp.getRoomLabel());
                  drawTemp.getPanel1().add(infoTemp.getProfessorLabel());

                  drawTemp.getPanel2().add(infoTemp.getSubjectLabel12());
                  drawTemp.getPanel2().add(infoTemp.getSubjectLabel22());
                  drawTemp.getPanel2().add(infoTemp.getRoomLabel2());
                  drawTemp.getPanel2().add(infoTemp.getProfessorLabel2());

                  // add class information in outputPanel
                  PrintPanel printTemp = outputList.get(j);

                  outputPanel.add(printTemp.getNoLabel());
                  outputPanel.add(printTemp.getSubLabel());
                  outputPanel.add(printTemp.getProLabel());
                  outputPanel.add(printTemp.getRoomLabel());
                  outputPanel.add(printTemp.getDayLabel());
                  outputPanel.add(printTemp.getTimeLabel());
                  outputPanel.add(printTemp.getCreditLabel());

                  // set DEL Button on the proper Position
                  deleteArray[j].setBounds(660,(30+j*PrintConstants.HEIGHT),60,PrintConstants.HEIGHT);
               } // for

               timeTablePanel.repaint();
               outputPanel.repaint();

               break;   // if the clicked button found, BREAK  :: escape condition
            } // if
         } // for

      } // actionPerformed()
   } // DeleteListener class
   
}   // PrimaryPanel class
