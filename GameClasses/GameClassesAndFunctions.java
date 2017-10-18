package GameClasses;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.swing.*;
import java.awt.*;

public class GameClassesAndFunctions 
{
	//converts a list of cards to a list of filenames
	public static String[] CardsToFilenames(String[] cards)
	{
		String[] filenames = new String[17];
		
		for(int i = 0; i < 17; ++i)
		{
			filenames[i] = cards[i + 1].substring(1);
			
			if(filenames[i].equals("11"))
				filenames[i] = "J";
			if(filenames[i].equals("12"))
				filenames[i] = "Q";
			if(filenames[i].equals("13"))
				filenames[i] = "K";
			if(filenames[i].equals("14"))
				filenames[i] = "A";
			
			filenames[i] += cards[i + 1].substring(0, 1) + ".png";
		
			System.out.println(cards[i+1] + " to " + filenames[i]);
		}
		
		return filenames;
	}
	
	//converts card to a filename
	public static String CardToFilename(String card)
	{
		String filename = "";
	
		filename = card.substring(1);
		
		if(filename.equals("11"))
			filename = "J";
		if(filename.equals("12"))
			filename = "Q";
		if(filename.equals("13"))
			filename = "K";
		if(filename.equals("14"))
			filename = "A";
		
		filename += card.substring(0, 1) + ".png";
	
		System.out.println(card + " to " + filename);
	
		
		return filename;
	}

	//tallies points of a trick
	public static int TallyPoints(String p1card, String p2card, String p3card)
	{
		return
			Integer.parseInt(p1card.substring(1)) + 
			Integer.parseInt(p2card.substring(1)) +
			Integer.parseInt(p3card.substring(1));
	}
	
	//finds the winner of a trick
	public static int FindRoundWinner(String p1card, String p2card, String p3card, String SuitLed)
	{
		int p1 = 0;
		int p2 = 0;
		int p3 = 0;
		
		if(p1card.substring(0,1).equalsIgnoreCase(SuitLed))
			p1 = Integer.parseInt(p1card.substring(1));
		if(p2card.substring(0,1).equalsIgnoreCase(SuitLed))
			p2 = Integer.parseInt(p2card.substring(1));	
		if(p3card.substring(0,1).equalsIgnoreCase(SuitLed))
			p3 = Integer.parseInt(p3card.substring(1));
		
		if(p1 > p2 && p1 > p3)
			return 1;
		else if(p2 > p1 && p2 > p3)
			return 2;
		else 
			return 3;
	}
	
	//reads from queue
	public static String ReadFromServer(InputStreamReader in, PrintWriter out) throws IOException
	{
		out.println("poll");
		int ServerEcho;
		String fromserver = "";
		while ((ServerEcho = in.read()) != 10) //the first time we read, the player numbers are on the queue
		{
			byte SEtoByte = (byte)ServerEcho;
	        fromserver += (char)SEtoByte;
	    }
		
		return fromserver.trim();
	}
	
	//clears the queue
	public static void ClearQueue(InputStreamReader in, PrintWriter out) throws IOException
	{
		String fromserver = ReadFromServer(in, out);
		
		while(!fromserver.contains("null"))
		{
			System.out.println(fromserver);
			fromserver = ReadFromServer(in, out);
		}
		
		out.println("clear");
	}
	
	//peeks at the queue
	public static String PeekServer(InputStreamReader in, PrintWriter out) throws IOException
	{
		out.println("peek");
		int ServerEcho;
		String fromserver = "";
		while ((ServerEcho = in.read()) != 10) //the first time we read, the player numbers are on the queue
		{
			byte SEtoByte = (byte)ServerEcho;
	        fromserver += (char)SEtoByte;
	    }
		
		return fromserver.trim();
	}
	
	//waits until all players are connected
	public static void waitForPlayers(InputStreamReader in, PrintWriter out) throws IOException, InterruptedException
	{
		String fromserver = "";
		
		while(!fromserver.contains("all players connected")) //wait for the all players connected message
		{
			int wait = (int)(Math.random() * 100);
			
			Thread.sleep(wait);
			fromserver = PeekServer(in, out);
		}
		
		System.out.println(ReadFromServer(in, out));
	}
	
	//waits until the queue is empty
	public static void waitTillNull(InputStreamReader in, PrintWriter out) throws IOException
	{
		String fromserver = "";
		
		while(!fromserver.contains("null"))
			fromserver = PeekServer(in, out).trim();
	}
	
	//gets cards from other players, makes sure it doesn't have that card already.
	public static String waitForNewCard(InputStreamReader in, PrintWriter out, String[] CardsHave, String CardCheck) throws IOException, InterruptedException
	{
		String fromserver = "";
		
		while(true)
		{
			int wait = (int)(Math.random() * 100);
			Thread.sleep(wait);
			
			fromserver = PeekServer(in, out);
			//System.out.println("Peek :" + fromserver);
			if(!fromserver.contains("null"))
			{
				//System.out.println("Peeked at: " + fromserver);
				if(CardsHave[Integer.parseInt(fromserver.substring(0, 1))].trim() == "")
				{
					fromserver = ReadFromServer(in, out).trim();
					
					if(!fromserver.contains("null") && !fromserver.contains(CardCheck))
						return fromserver;
				}
			}
		}
	}
	
	//gets card played from other players
	public static String getCardPlayed(InputStreamReader in, PrintWriter out) throws IOException
	{
		System.out.println("getting card from server.");
		
		String fromserver = "null";
		
		while(fromserver.contains("null"))
		{
			fromserver = ReadFromServer(in, out);
		}
		
		System.out.println("Recieved: " + fromserver);
		
		return fromserver.trim();
	}

	//gets starting cards from server
	public static String[] GetMyCards(InputStreamReader in, PrintWriter out, int playernumber) throws IOException
	{
		String fromserver = "";
		
		while(!fromserver.contains("p" + Integer.toString(playernumber))) //wait for the all players connected message
		{
			fromserver = PeekServer(in, out);
		}
		
		return ReadFromServer(in, out).trim().split(":");
	}

//	
// Find the winner in the case of a tie based on player points
// Used in findWinner method
//	
	public static String tie(int[]points) {
		String winner;
		int maxPoints = Math.max(Math.max(points[1], points[2]), points[3]);
		
		if(points[1] == maxPoints) {
			winner = "Player 1";
		} else if (points[2] == maxPoints){
			winner = "Player 2";
		} else if (points[3] == maxPoints){
			winner = "Player 3";
		} else {
			winner = "Error";
		}
		
		return winner;
	}


//	
// Finds the winner base on points and score
// Utilizes tie method
//
	public static String findWinner(int[]scores, int[]points) {
		String winner;
		int maxScore = Math.max(Math.max(scores[1], scores[2]), scores[3]);
		
		if(scores[1] == maxScore) {
			winner = "Player 1";
			if (scores[1] == scores[2]) {
				winner = tie(points);
			} else if (scores[1] == scores[3]) {
				winner = tie(points);
			}
		} else if (scores[2] == maxScore){
			winner = "Player 2";
			if (scores[2] == scores[3]) {
				winner = tie(points);
			} else if (scores[2] == scores[1]) {
				winner = tie(points);
			}
			
		} else if(scores[2] == maxScore){
			winner = "Player 3";
			if (scores[3] == scores[1]) {
				winner = tie(points);
			} else if (scores[3] == scores[2]) {
				winner = tie(points);
			}
		} else {
			winner = "Error";
		}
		
		return winner;
	}
	
	
//
// Displays message of the winner
//
	public static String displayWinner(String winner) {
		String msg;
		
		if (winner == "Player 1") {
			msg = "Player 1 Wins!";
		} else if (winner == "Player 2") {
			msg = "Player 2 Wins!";
		} else if (winner == "Player 3"){
			msg = "Player 3 Wins!";
		} else {
			msg = "Error";
		}			
		
		return msg;
	}
	
//
// displays the inputed text
//
	public static void displayText(String s) {
		
		Component frame = null;
		JOptionPane.showMessageDialog(frame, s);
	}
	
}
