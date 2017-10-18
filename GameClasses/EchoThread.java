package GameClasses;

import java.net.*;
import java.util.concurrent.BlockingQueue;
import java.io.*;

public class EchoThread extends Thread 
{
    protected Socket socket;

    BlockingQueue<String> queue; 
    
    public EchoThread(Socket clientSocket) 
    {
        this.socket = clientSocket;
    }
    
    public void SetQueue(BlockingQueue<String> QueuePointer) 
    {
    	queue = QueuePointer;
    }

    public void run() 
    {
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        
        try 
        {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException e) 
        {
            return;
        }
        
        String fromClient;
        String fromServer;
        
        while (true) 
        {
            try 
            {
            	fromClient = brinp.readLine();
                //System.out.println("FromClient:" + fromClient);
                if (fromClient.toLowerCase().contains("poll"))
                {
                	fromServer = queue.poll();
	                if(fromServer != null)
	                {
	                	//read message from queue and send to client
	                	out.writeBytes(fromServer + "\n\r");
	                	out.flush();
	                	System.out.println("polled from Queue: " + fromServer + "\n\r");
	                }
	                else
	                {
	                	System.out.println("polled from Queue: null");
	                	out.writeBytes("null" + "\n\r");
	                }
                }
                else if (fromClient.toLowerCase().contains("peek"))
                {
                	fromServer = queue.peek();
	                if(fromServer != null)
	                {
	                	//read message from queue and send to client
	                	out.writeBytes(fromServer + "\n\r");
	                	out.flush();
	                }
	                else
	                {
	                	out.writeBytes("null" + "\n\r");
	                }
                }                
                else if (fromClient.toLowerCase().contains("clear"))
                {
                	queue.clear();
                	if(queue.isEmpty())
                		System.out.println("The queue is not the issue");
                	else
                		System.out.println("Queue is problem");
                }
                else
                {
                	//put client message on the queue
                	queue.put(fromClient);
                	queue.put(fromClient);
                	
                	System.out.println("Put in Queue: " + fromClient + "\n\r");
                	out.writeBytes(fromClient + "\n\r");
                	out.flush();
                }
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
                return;
            } 
            catch (InterruptedException e) 
            {
				e.printStackTrace();
			}
        }
    }
}