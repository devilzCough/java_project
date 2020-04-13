import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.net.ssl.SSLSocket;                 // it's for SSLSocket
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JPanel;
import org.apache.commons.codec.binary.Base64;  // encode id & pw to Base64
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;

/* SSLSocket
    : extends Sockets
    : provieds secure socket using protocols 
    such as 'Secure Socket Layer'(SSL) or IETE 'Transport Layer Security'(TLS)

    Such sockets are normal stream sockets, 
    but they add a layer of security protections over the underlying network transport protocol, such as TCP. 

    Those protections include:
    
    1. Integrity Protection. SSL protects against modification of messages by an active wiretapper.
    2. Authentication. In most modes, SSL provides peer authentication. 
       Servers are usually authenticated, and clients may be authenticated as requested by servers.
    3. Confidentiality (Privacy Protection). In most modes, SSL encrypts data being sent between client and server. 
       This protects the confidentiality of data, so that passive wiretappers won't see sensitive data 
       such as financial information or personal information of many kinds.
*/


public class SMTPSender
{
    private static SSLSocket connectSocket;
    private static DataOutputStream outputStream;
    private static BufferedReader readBuf;

    private static int delayTime = 500;   
    private String strUserName;
    private String strPassword;
    private String strSmtpServer;
    private String userID;

    private String format;
    private boolean last = true;    // to know it is the last attach file (if it's last file : --boundary--, or not : --boundary)
    private String[] formatarray;   // to know file name & type
    private String file_name;

    private ConnectThread connectThread;
    private static WriteMail wmMailing;

    static String boundary = "KkK170891tpbkKk__FV_KKKkkkjjwq";

    // SMTPSender constructor
    // this is made in PrimaryPanel class, when you click login button
    public SMTPSender(String user, String pass, String smtpServer) throws UnknownHostException, IOException {   

        // make e-mail address combine with server name and id
        userID = user + "@" + smtpServer.substring(5);

        // to send message to server, encode username & password 
        // encode in Base64 : email address & password
        // getBytes(string character set name : encoding type) : convert string to byte then encode to encoding type(UTF-8)
        // every string length is different, so get just values and save as byte : bytestream => serialized
        // string is basically ASCII code 
        // encodeBase64String : after encoding the bytestream, return to string type
        strUserName = Base64.encodeBase64String(user.getBytes(StandardCharsets.UTF_8));
        strPassword = Base64.encodeBase64String(pass.getBytes(StandardCharsets.UTF_8));    
        strSmtpServer = smtpServer;  

        // we use this method to connect again.
        makeSocket();

    } // SMTPSender()

    // make SSL Socket
    // these exceptions are for createSocket(), SSLSocket
    public void makeSocket() throws UnknownHostException, IOException {

        // connect SSLSocket with port number 465
        /*  SSLSocketFactory : abstract class
                getDefault() : Returns factory object
                               return type is just 'SocketFactory'. not SSLSocketFactory
                               so, we have to cast in 'SSLSocketFactory'
                createSocket() : create Socket. if you input smtpServer & port number, this also connect with it.
                                 return type is just 'Socket'. not SSLSocket
                                 so, we have to cast in 'SSLSocket'
        */
        // SSLSocket : protected (String host, int port) throws IOException, UnknownHostException
        connectSocket = (SSLSocket)((SSLSocketFactory)SSLSocketFactory.getDefault()).createSocket(strSmtpServer, 465);
        
        // connect inputstream
        // getInputStream() throws IOException : Returns an input stream for this socket.
        // An InputStreamReader is a bridge from byte streams to character streams
        // :: it reads bytes and decodes them into characters using a specified charset.
        // BufferedReader : Creates a buffering character-input stream that uses a default-sized input buffer.
        readBuf = new BufferedReader(new InputStreamReader(connectSocket.getInputStream())); 
        // connect outputstream
        outputStream = new DataOutputStream(connectSocket.getOutputStream());
          
        // creat ConnectThread object and thread start
        connectThread = new ConnectThread(readBuf);
        connectThread.start();

    } // makeSocket()

    // this method check the account is valid
    public int login() throws Exception {

        // send client domain information
        send("EHLO " + strSmtpServer + "\r\n");
        // static method
        // sleep : stop process for 'delayTime'
        // it's not good way to wait. We have to wait ack sign.
        // but, some server cannot send ask sign. that case we have to sleep
        Thread.sleep(delayTime);

        // if we send this message, input sign is show up. we have to input encoded id & pw with Base64
        send("AUTH LOGIN\r\n");
        Thread.sleep(delayTime);
                     
        send(strUserName + "\r\n");
        Thread.sleep(delayTime);
               
        send(strPassword + "\r\n");
        Thread.sleep(delayTime);

        // we close connection. it because we do not send mail immediately after login
        // especially.. naver server connection time is too short..!
        send("QUIT");
        Thread.sleep(delayTime);

        int i = 3;
        int flag = 0;

        // it's for check login is success
        // but it has a problem. this while function take a very short time.
        // at least we have to sleep 500 millisecond.
        while (i > 0) {
            // in ConnectThread class, if you got auth. success, login flag become '1'
            flag = connectThread.getLoginFlag();
            if (flag == 1) break;  // login success
            i--;
        }

        return flag;

    } // login()

    // after login, notice to Thread : it is mailing stage
    // we get WriteMail object 
    public void setWriteMail(WriteMail wmTemp) {

        wmMailing = wmTemp;

    } // setWriteMail

    public void mailing(String title,String receipt,String contents, String[] filepath, String[] filename, int nFile) throws Exception {

        // thread stop and make socket again
        // but, it also has a problem.. we do not remove thread object.
        // it make new connection and new thread..
        connectThread.stop();
        // by the way.. connect again.
        makeSocket();

        // WriteMail <-- SMTPSender <-- ConnectThread
        // after login success, we can send writeMail object to ConnectThread 
        // it because we set the server message in WriteMail state panel.
        connectThread.setWriteMail(wmMailing);

        // login again
        // it because, naver connection time it too short!!!
        send("EHLO " + strSmtpServer + "\r\n");
        Thread.sleep(delayTime);

        send("AUTH LOGIN\r\n");
        Thread.sleep(delayTime);

        send(strUserName + "\r\n");
        Thread.sleep(delayTime);

        send(strPassword + "\r\n");
        Thread.sleep(delayTime);

        // who send message
        send("MAIL FROM: <" + userID + ">\r\n");
        Thread.sleep(delayTime);

        // who receive message
        send("RCPT TO: <" + receipt + ">\r\n");
        Thread.sleep(delayTime);

        // this message mean
        // : we will send data, then please preparing.
        send("DATA\r\n");   
        Thread.sleep(delayTime);

        // we have to send this message. if you want to see 'from ...' & 'to ...' & 'subject ...'
        // it's real show up data.
        // headers after sending data
        send("FROM: " + userID + "\r\n");
        Thread.sleep(delayTime);

        send("TO: " + receipt + "\r\n");
        Thread.sleep(delayTime);

        // expression : =?char_set?encoding_type?data?= 
        // char_set : UTF-8, encoding_type : Base64
        send("Subject: =?UTF-8?b?"+Base64.encodeBase64String(title.getBytes(StandardCharsets.UTF_8))+"?=\r\n");
        Thread.sleep(delayTime);

        ////////////////////////////////////////////
        // to send attach files, we need MIME header
        send("MIME-Version: 1.0\r\n");
        Thread.sleep(delayTime);
        // multipart/mixed : input various types of contents(txt, attach file)
        // boundary="..." : ... is our boundary
        send("Content-Type: multipart/mixed; boundary=\"" + boundary + "\"\r\n\r\n");
        Thread.sleep(delayTime);

        // add boundary to classify the contents.
        send("--"+boundary+"\r\n");
        Thread.sleep(delayTime);
         
        // Message Body content 
        send("Content-type:text/plain; charset=\"EUC-KR\"\r\n");
        Thread.sleep(delayTime);

        send("Content-Transfer-Encoding: base64\r\n");
        Thread.sleep(delayTime);

        // to send Korean message.
        // get contents to 'EUC_KR' and input in the euckr_contents
        byte[] euckr_contents = contents.getBytes("EUC_KR");
        // then encode to Base64
        send("\r\n" + Base64.encodeBase64String(euckr_contents) + "\r\n");
        Thread.sleep(delayTime);
         
        send("--" + boundary + "\r\n");
        Thread.sleep(delayTime);

        // file attach
        for (int i=0; i<nFile; i++) {

            // to know it's time to send last file
            if (i == nFile-1) 
                last = true;
            else 
                last = false;

            // read file name, classify name & format
            formatarray = filename[i].split("\\.");
            file_name = formatarray[0];
            format = formatarray[1];
         
            attachment_send(filepath[i],filename[i],format,file_name,last);
            System.out.println(i + ":" + last);
        }

        send(".\r\n");
        Thread.sleep(delayTime);

        send("QUIT\r\n");
    } // mailing()

    // send file attachment
    public static void attachment_send(String filepath,String filename ,String format,String file_name ,boolean last) throws Exception {
      
        File file = new File(filepath); 
      
        // set file types(txt, jpg, png) and encoding
        // if it '.txt' file
        if (format.equals("txt")) {
            send("Content-type:text/plain; charset=\"UTF-8\"\r\n");
            Thread.sleep(delayTime);
        }
        else {
            // if it image file
            send("Content-Type: application/octet-stream; name=\"" + file_name + "\"" + "\r\n");
            Thread.sleep(delayTime); 
            send("Content-Transfer-Encoding: base64\r\n");
            Thread.sleep(delayTime);
        }
 
        // send message with attach form
        send("Content-Disposition: attachment; " + "filename=\"" + filename + "\"" + "\r\n");
        Thread.sleep(delayTime);

        // depending on format type, send mail after encoding
        if (format.equals("txt")) {
            send("\r\n" + readTxt(file) + "\r\n");
            Thread.sleep(delayTime);
        }
        else {
            // System.out.println("else");
            send("\r\n" + fileToString(file, format) + "\r\n");
            Thread.sleep(delayTime);
        }
        
        // check it is last file, and send --Boundary--
        if (last == true) {
            send("--" + boundary + "--\r\n");
            Thread.sleep(delayTime);
        }
        else {
            send("--" + boundary + "\r\n");
            Thread.sleep(delayTime);
        }

    }
   
  
    // it encodes image files with Base64
    public static String fileToString(File file, String format) throws Exception {
        
        String image = null;
        BufferedImage buffImage = ImageIO.read(file);

        if(buffImage != null) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(buffImage, format , os);
            byte[] data = os.toByteArray();
            image = Base64.encodeBase64String(data);
        }
        
        return image;
    }

    // read txt file
    public static String readTxt(File file) throws Exception {

        FileReader textFileReader = new FileReader(file);
        char[] buf = new char[1024]; 
        // StringBuilder : it can modify string. 
        // it use to add string.
        StringBuilder contentReceiver = new StringBuilder();

        while (textFileReader.read(buf) > 0)
            contentReceiver.append(buf);

        return contentReceiver.toString();

    }

    // it send messages to mail server
    // print client messages
    private static void send(String strSend) throws Exception {

        outputStream.writeBytes(strSend);
        System.out.println("CLIENT: " + strSend);
        if (wmMailing != null)   
            wmMailing.setStateAreaText("CLIENT: " + strSend);
    } // send()

} // SMTPSender class

