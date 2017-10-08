package GameClasses;

import java.net.*;
import java.io.*;

public class EchoClient
{
	public static void main(String[] args) throws IOException 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//for user input
				
		//connect to server
		Socket sock = new Socket("127.0.0.1",5557);
		System.out.println("Socket has been established.");
		
		System.out.println("Enter String, type close to exit");
		String input = br.readLine();
		//get user input. In the program, we wouldn't be using the console for this.

		
		//variable declarations
		PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
		InputStreamReader in = new InputStreamReader(sock.getInputStream());
		
		//when user types Exit, the program stops and the server throws an error, but the server keeps running
        while(!input.toUpperCase().equals("EXIT"))
		{
			try 
			{			
				out.println(input);
				
				int ServerEcho;
				System.out.println("Receiving echo from the server …");
				
				
				
				while ((ServerEcho = in.read()) != 10) 
				{
					//print what the server says to the console
					//in reality, this is where we would process what the other clients are saying
					byte SEtoByte = (byte)ServerEcho;
			        System.out.print((char)SEtoByte);
			    }
				
				
				//not sure why I have this hear. probably not needed in real program
				System.out.println();
				
			}
			catch (IOException ioe) 
			{
				System.err.println(ioe);
				//sock.close();
			}
			
			//the user can type 'poll' to get info off the queue from the server.
			System.out.println("Enter String, type 'EXIT' to to quit the program");
	        input = br.readLine();
	        
		}
		
		sock.close();
	}
}
