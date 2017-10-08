package GameClasses;

import java.net.*;
import java.io.*;

public class GameServer
{
	public static void main(String[] args) {
		
		InputStreamReader in = null;
		PrintWriter out = null;
		
		try {
			
			ServerSocket sock = new ServerSocket(6013);
			System.out.println("Echo server v2 is up …");

			// now listen for connections
			
			System.out.println("Server is listening …");
		
			Socket client = sock.accept();
			//System.out.println("Client socket has been established.");
			
			in = new InputStreamReader(client.getInputStream());
			
			out= new PrintWriter(client.getOutputStream(), true);
			
			while (!client.isClosed()) 
			{						
				int ClientInput;
				String Input = "";
				
				while ((ClientInput = in.read()) != 10) 
				{					
					byte CItoByte = (byte)ClientInput;
			        Input += (char)CItoByte;
			    }
				
				//System.out.println("Server received: " + Input);
				out.println(Input);
			}
			sock.close();
		}
		catch (IOException ioe) {
				System.err.println(ioe);
		}
	}
}
