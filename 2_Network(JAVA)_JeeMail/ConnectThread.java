/*** This is a 'ConnectThread' class   ***/
/*** This thread get 'Server' response ***/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;


public class ConnectThread extends Thread 
{
    private BufferedReader readBuf;
    private int loginFlag;
   
    private WriteMail writeMail;

    // ConnectThread constructor
    // this is made in SMTPSender class. And made with connectSocket(SSLSocket) input stream
    public ConnectThread(BufferedReader read) {

        readBuf = read;
        loginFlag = 0;
    } // ConnectThread()
   
    // get login flag to inform to the SMTPSender if it is login success.
    public int getLoginFlag() {

        return loginFlag;
    } // getLoginFlag()
    
    // receive an object of WriteMail class to access its stateArea
    public void setWriteMail(WriteMail writeM) {

        writeMail = writeM;
    } // setWriteMail()

    public void run() {

        try {
            String line;

            // read a message in buffer
            while ((line = readBuf.readLine()) != null) {
                
                // if the status code is '235', it is login success.
                // then change the login flag to '1'
                if ((line.substring(0,3)).equals("235")) 
                    loginFlag = 1;
    
                if (writeMail != null) {
                    try {
                        // append the server's response message to stateArea
                        writeMail.setStateAreaText("SERVER: " + line + "\n");    
                    } catch (Exception e) {}; 
                }
                System.out.println("SERVER: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
   } // run()

} // ConnectThread class

