package GameClasses;

import java.net.*;
import java.io.*;

public class PlayGame 
{
	public static void main(String[] args) throws IOException
	{
		Socket sock = new Socket("127.0.0.1",5557);
		System.out.println("Socket has been established.");
		
		int playernumber = 0;
		
		//variable declarations
		PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
		InputStreamReader in = new InputStreamReader(sock.getInputStream());
		
		String fromserver = "";
		String[] myCards = new String[18];//18 because myCards[0] isn't storing a card
		
		fromserver = GameClassesAndFunctions.ReadFromServer(in, out);
		
		if(fromserver.contains("one"))
		{
			//you are player one
			playernumber = 1; 
			myCards = fromserver.split(":");	
			
			gameBoard frame = new gameBoard(GameClassesAndFunctions.CardsToFilenames(myCards));
	        frame.setVisible(true);
	        
			System.out.println(fromserver);
			GameClassesAndFunctions.waitForPlayers(in, out);
			
		}
		else if(fromserver.contains("two")) 
		{
			//you are player two
			playernumber = 2; 
			myCards = fromserver.split(":");
			
			System.out.println(fromserver);
			GameClassesAndFunctions.waitForPlayers(in, out);
		}
		else if(fromserver.contains("three"))
		{
			//you are player three
			playernumber = 3; 
			myCards = fromserver.split(":");
			System.out.println(fromserver);
			
			out.println("all players connected");
		}
		
		//score is number of tricks taken
		int[] scores = new int[4];
		
		//points are the value of all cards taken. Used during a tie
		int[] points = new int[4];
 		
		int cardcounter = 17;
		
		boolean myTurn = false;
		
		if(playernumber == 1)
			myTurn = true;
		
		while(cardcounter > 0)
		{
			String[] cardsplayed = new String[4];
			
			String suitLed = "";
			
			if(myTurn)
			{
				int i = 4; //user selects a card to play, returns index of that card
				
				out.println(Integer.toString(playernumber) + myCards[i]); //get index from card user chose
				suitLed = myCards[i].substring(0, 1);
				
				cardsplayed[playernumber] = myCards[i];
				
				GameClassesAndFunctions.waitTillNull(in, out); //wait till all message read off queue
				
				String cardPlayed = GameClassesAndFunctions.waitForNewCard(in, out, cardsplayed);
				cardsplayed[Integer.parseInt(cardPlayed.substring(0, 1))] = cardPlayed.substring(1);
				//display card
				
				
				cardPlayed = GameClassesAndFunctions.waitForNewCard(in, out, cardsplayed);
				cardsplayed[Integer.parseInt(cardPlayed.substring(0, 1))] = cardPlayed.substring(1);
				//display card
				
			}
			else
			{
				String CardLead = GameClassesAndFunctions.getCardPlayed(in, out);
				suitLed = CardLead.substring(1,1);
				
				cardsplayed[Integer.parseInt(CardLead.substring(0, 1))] = CardLead.substring(1);
				
				//select and play a card
					//make sure to follow suit and stuff
				int i = 4; //index of card chosen to play
				cardsplayed[playernumber] = myCards[i];
				
				out.println(myCards[i]);
				//display card
				
				String cardPlayed = GameClassesAndFunctions.waitForNewCard(in, out, cardsplayed);
				cardsplayed[Integer.parseInt(cardPlayed.substring(0, 1))] = cardPlayed.substring(1);
				//display card
			
			}
			
			
			int winner = GameClassesAndFunctions.FindRoundWinner(cardsplayed[1], cardsplayed[2], cardsplayed[3], suitLed);
			
			//display who won that round
			
			
			++scores[winner];
			points[winner] += GameClassesAndFunctions.TallyPoints(cardsplayed[1],cardsplayed[2],cardsplayed[3]);
			
			//update scoreboard
			
			if(playernumber == winner)
				myTurn = true;
			else 
				myTurn = false;
			
			--cardcounter;
		}
		
		//decide winner
		//if tie, tie breaker
		
		sock.close();
	}
			
}
