package controller.pack;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import cipher.pack.Cesar;
import cipher.pack.XOR;
import connection.pack.Session;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AppController {
	/**Random generator*/Random generator = new Random(); 
	
	private final int CIPHER_MODE_NONE 	= 0;
	private final int CIPHER_MODE_XOR   = 1;
	private final int CIPHER_MODE_CESAR = 2;

	
	/**User`s nickname*/private String nick="NoName";
	/**User`s cipher mode*/private int cipherMode=0;
	/**Clients session*/private Session mySession = new Session();
	/**Main controller*/private MainController mainController;
	private String lines="";

	/**Cesar object for encypt/decypt*/private Cesar cesar = new Cesar();
	/**XOR object for enrcytp/decypt*/ private XOR xor = new XOR();
	
	private int myId;
	/**Private P number received from server*/private int myPrivateP;
	/**Private G number received from server*/private int myPrivateG;
	/**Secret b number */private int mySecret_b;
	/**Calculated B number to send to server*/private int B_2send2Server;
	
	/**Session key*/private int mySessionKey;
	
    @FXML
    private TextArea chatField;

    @FXML
    private TextField messageBox;
    
    /**Thread to communicate with server. Received message from sever and decypt and show in window app*/
    Task task = new Task<Void>() {
	    @Override public Void call() {
	    	
	       while(!isCancelled()) {
	        	String receivedMsg = mySession.communication();
	        	String plainMsg;
	        	if(receivedMsg!="") {
	        		if(cipherMode == CIPHER_MODE_XOR) {
	        			plainMsg = xor.encryptDecrypt(receivedMsg);
	        		}else if(cipherMode == CIPHER_MODE_CESAR) {
	        			plainMsg = cesar.decrypt(receivedMsg);
	        		}else {
	        			plainMsg = receivedMsg;
	        		}
	        		lines=lines + plainMsg+"\n";
	        		chatField.setText(lines);
	        	}
	       }
	        return null;
	    }
	};
	/**Function <code>sendMessage</code> get message from message box and encrypt with users cipher algorithm. After encryption message is send to server.*/
	@FXML	//works fine
    void sendMessage() {
		String msg=messageBox.getText();
		String cipherMsg = this.nick +": "+msg;
		
		if(this.cipherMode == CIPHER_MODE_XOR) {
			cipherMsg = xor.encryptDecrypt(cipherMsg);
		}else if(this.cipherMode == CIPHER_MODE_CESAR) {
			cipherMsg = cesar.encrypt(cipherMsg);
		}
		
		mySession.sendMessage(cipherMsg);
		messageBox.clear();
		lines = lines +"You: "+msg+"\n";
		chatField.setText(lines);
    }

	public void setMainController(MainController mainController) {
		this.mainController=mainController;
	}
	/**Function <code>setParam</code> initialize parameter for Diffie-hellman (get P, G and A number from server), calculate own B number 
	 * and send to server, calculate key session, getting key session from server and compare session keys, if they are different the connection 
	 * is breaking and app is getting close. But if session keys are identical, function start running thread for session and communication with server.  
	 * @param Nick which is user nickname
	 * @param CipherMode which is selected cipher mode
	 * */
	public void setParam(final String Nick, final int CipherMode) {
		if(Nick!="") {
			this.nick = Nick;
		}
		this.cipherMode = CipherMode;

		mySession.init();
		this.myId = Integer.parseInt(mySession.communication());		//getting my id from server
		this.myPrivateP = Integer.parseInt(mySession.communication());	//getting random P number from server 
		this.myPrivateG = Integer.parseInt(mySession.communication());	//getting random G number from server
		int A_fromServer = Integer.parseInt(mySession.communication());	//getting A from server
		
		System.out.println("myId="+myId);								//print my Id
		
		this.mySecret_b = generator.nextInt(10)+3;						//get random my secret b number
		Long tmp =((long)Math.pow(this.myPrivateG, this.mySecret_b));
		
		this.B_2send2Server = (int) (tmp%this.myPrivateP);				//calculate B number to send to server 
		
		mySession.sendMessage(Integer.toString(this.B_2send2Server));	//send B number to server
		
		System.out.println("C L I E N T");										//print some data to debug
		System.out.println("P = "+this.myPrivateP);
		System.out.println("G = "+this.myPrivateG);
		System.out.println("Secret b = "+this.mySecret_b);
		System.out.println("B send to serwer = "+this.B_2send2Server);
		System.out.println("A from serwer = "+A_fromServer);

		this.mySessionKey = (int)(Math.pow(A_fromServer, this.mySecret_b)%this.myPrivateP);	//calculate this session key 
		System.out.println("Session key calculate on client app = "+ this.mySessionKey);
		int sessionKeyFromServer = Integer.parseInt(mySession.communication());				//get session key from server
		if(sessionKeyFromServer != this.mySessionKey) {										//compare this session key with session key from server
			System.exit(0);																	//close app if keys are diffrent
		}
		mySession.sendMessage(Integer.toString(this.cipherMode));
		new Thread(task).start();
	}
}

