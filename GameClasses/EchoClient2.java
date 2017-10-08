package GameClasses;

import java.net.*;
import java.io.*;

public class EchoClient2
{
	public static void main(String[] args) throws IOException 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//for user input
				
		Socket sock = new Socket("127.0.0.1",5557);
		System.out.println("Socket has been established.");
		
		System.out.println("Enter String, type close to exit");
		String input = br.readLine();

		PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
		
		InputStreamReader in = new InputStreamReader(sock.getInputStream());
		
        while(!input.toUpperCase().equals("EXIT"))
		{
			try 
			{			
				out.println(input);
				
				int ServerEcho;
				System.out.println("Receiving echo from the server …");
				while ((ServerEcho = in.read()) != 10) 
				{
					byte SEtoByte = (byte)ServerEcho;
			        System.out.print((char)SEtoByte);
			    }
				System.out.println();
				
			}
			catch (IOException ioe) 
			{
				System.err.println(ioe);
				//sock.close();
			}
			
			System.out.println("Enter String, type 'EXIT' to to quit the program");
	        input = br.readLine();
	        
		}
		
		sock.close();
	}
}
