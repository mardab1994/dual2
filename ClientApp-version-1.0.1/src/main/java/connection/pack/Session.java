package connection.pack;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javafx.scene.control.TextField;

public class Session {
	/**my socket*/private Socket s;						
	/**input stream to read message from server*/private InputStream inStream = null;	
	/**output stream to write to server*/private OutputStream outStream = null;	
	private PrintWriter out = null;			
	private Scanner in = null;
	
	/**Constructor for <code>Session</code>*/
	public Session( ) {
		System.out.println("Session contructor");
	}
	
	/**Function <code>init</code> initial connection with server, and init streams to read and write to communicate with server*/
	public void init() {
		try {
			s = new Socket("localhost",8199);
			inStream = (InputStream) s.getInputStream();
			outStream = s.getOutputStream();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		 out = new PrintWriter(outStream, true);
		 in = new Scanner(inStream);
		// out.println("Hello I am new Client");	//say hello to other clients
	}
	
	/**Function <code>sendMessage</code> takes string as a message and send them to server
	 * @param message message to send to server*/
	public void sendMessage(String message) {
		out.println(message);
	}
	
	/**Function <code>communication</code> checks whether a message from the server has arrived.
	 * @return message from server (if arrived) or empty string*/
	public String communication() {
		if(in.hasNextLine()) {
			return in.nextLine();
		}
		return "";
	}
}


			

