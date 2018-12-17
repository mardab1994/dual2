package main.pack;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class main {

	public static void main(String[] args) {
		
		try {
			int i=1;
			ServerSocket s = new ServerSocket(8199);
			
			while(true) {
				Socket incoming = s.accept();
				Runnable r = new ThreadEchoHandler(incoming);			
				Thread t = new Thread(r);						
				t.start(); 
				i++;  
			}
		}catch(IOException e) {
			e.printStackTrace(); 
		}
	}

}
