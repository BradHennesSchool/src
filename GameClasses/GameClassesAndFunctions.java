package GameClasses;

import java.awt.Component;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GameClassesAndFunctions 
{
	public static String[] CardsToFilenames(String[] cards)
	{
		String[] filenames = new String[18];
		
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
	

	public static int TallyPoints(String p1card, String p2card, String p3card)
	{
		return
			Integer.parseInt(p1card.substring(1)) + 
			Integer.parseInt(p2card.substring(1)) +
			Integer.parseInt(p3card.substring(1));
	}
	

	static int FindRoundWinner(String p1card, String p2card, String p3card, String SuitLed)
	{
		int p1 = 0;
		int p2 = 0;
		int p3 = 0;
		
		if(p1card.substring(0,0) == SuitLed)
			p1 = Integer.parseInt(p1card.substring(0));
		if(p1card.substring(0,0) == SuitLed)
			p2 = Integer.parseInt(p2card.substring(0));	
		if(p1card.substring(0,0) == SuitLed)
			p3 = Integer.parseInt(p3card.substring(0));
		
		if(p1 > p2 && p1 > p3)
			return 1;
		else if(p2 > p1 && p2 > p3)
			return 2;
		else 
			return 3;
	}
	
	static String ReadFromServer(InputStreamReader in, PrintWriter out) throws IOException
	{
		out.println("poll");
		int ServerEcho;
		String fromserver = "";
		while ((ServerEcho = in.read()) != 10) //the first time we read, the player numbers are on the queue
		{
			byte SEtoByte = (byte)ServerEcho;
	        fromserver += (char)SEtoByte;
	    }
		
		return fromserver;
	}
	
	static String PeekServer(InputStreamReader in, PrintWriter out) throws IOException
	{
		out.println("peek");
		int ServerEcho;
		String fromserver = "";
		while ((ServerEcho = in.read()) != 10) //the first time we read, the player numbers are on the queue
		{
			byte SEtoByte = (byte)ServerEcho;
	        fromserver += (char)SEtoByte;
	    }
		
		return fromserver;
	}
	
	static void waitForPlayers(InputStreamReader in, PrintWriter out) throws IOException
	{
		String fromserver = "";
		
		while(!fromserver.contains("all players connected")) //wait for the all players connected message
		{
			fromserver = PeekServer(in, out);
		}
		
		ReadFromServer(in, out);
	}
	
	static void waitTillNull(InputStreamReader in, PrintWriter out) throws IOException
	{
		String fromserver = "";
		
		while(fromserver != "null")
			fromserver = PeekServer(in, out);
	}
	
	static String waitForNewCard(InputStreamReader in, PrintWriter out, String[] CardsHave) throws IOException
	{
		String fromserver = "";
		
		while(true)
		{
			fromserver = PeekServer(in, out);
			if(!fromserver.contains("null"))
				if(CardsHave[Integer.parseInt(fromserver.substring(0, 0))] != "")
					return ReadFromServer(in, out);
		}
	}
	
	static String getCardPlayed(InputStreamReader in, PrintWriter out) throws IOException
	{
		String fromserver = "null";
		
		while(fromserver.contains("null"))
		{
			fromserver = ReadFromServer(in, out);
		}
		
		return fromserver;
	}

	static String[] GetMyCards(InputStreamReader in, PrintWriter out, int playernumber) throws IOException
	{
		String fromserver = "";
		
		while(!fromserver.contains("p" + Integer.toString(playernumber))) //wait for the all players connected message
		{
			fromserver = PeekServer(in, out);
		}
		
		return ReadFromServer(in, out).split(":");
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
// Updates a players score label 
//
	public static void updateScore(int[]scores, int player, JLabel p1, JLabel p2, JLabel p3) {
		
		if (player == 1) {
			scores[1]++;
			p1.setText(Integer.toString(scores[1]));
		} else if (player == 2) {
			scores[2]++;
			p2.setText(Integer.toString(scores[2]));
		} else if (player == 2) {
			scores[3]++;
			p3.setText(Integer.toString(scores[3]));
		}
	}
	
	
//
// displays the inputed text
//
	public static void displayText(String s) {
		
		Component frame = null;
		JOptionPane.showMessageDialog(frame, s);
	}
}
