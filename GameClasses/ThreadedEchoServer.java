package GameClasses;

import java.net.*;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;

public class ThreadedEchoServer 
{

    static final int PORT = 5557;


    static final BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
    
    @SuppressWarnings("resource")
	public static void main(String args[]) 
    {
        ServerSocket serverSocket = null;
        Socket socket = null;
        
        String[] deck = GetShuffledDeck();
		String hand = "";
		
		hand = "one";
		for(int i = 1; i <= 17; ++i)
		{
			hand += ":" + deck[i];
		}
		queue.add(hand);
		
		hand = "two";
		for(int i = 18; i <= 34; ++i)
		{
			hand += ":" + deck[i];
		}
		queue.add(hand);
		
		hand = "three";
		for(int i = 35; i <= 51; ++i)
		{
			hand += ":" + deck[i];
		}
		queue.add(hand);
        
        try 
        {
            serverSocket = new ServerSocket(PORT);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        while (true) 
        {
            try 
            {
                socket = serverSocket.accept();
            } 
            catch (IOException e) 
            {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            EchoThread NewThread = new EchoThread(socket);
            
            NewThread.SetQueue(queue);
            NewThread.start();
        }
    }
    static String[] GetShuffledDeck()
	{
		String[] cardtypes = new String[4];
		cardtypes[0] = "D";
		cardtypes[1] = "C";
		cardtypes[2] = "S";
		cardtypes[3] = "H";
		
		String[] cards = new String[52];
		int k = 0;
		
		for(int i = 0; i <= 3; ++i)
		{
			for(int j=2; j <= 14; ++j)
			{
				cards[k] = cardtypes[i] + Integer.toString(j);
				++k;
			}
		}
		
		return shuffleArray(cards);
	}
    

	static String[] shuffleArray(String[] ar)
	{
		String[] ret = ar;
		Random rnd = ThreadLocalRandom.current();
		
		for (int i = ret.length - 1; i > 0; i--)
		{
			int index = rnd.nextInt(i + 1);
			String a = ret[index];
			ret[index] = ret[i];
			ret[i] = a;
		}
		
		return ret;
	}
    
}