package GameClasses;

import java.net.*;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.*;

public class PlayGame 
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		//we hardcode ip for now
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
	        
			//GameClassesAndFunctions.waitForPlayers(in, out);
			
		}
		else if(fromserver.contains("two")) 
		{
			//you are player two
			playernumber = 2; 
			myCards = fromserver.split(":");
			
			//GameClassesAndFunctions.waitForPlayers(in, out);
		}
		else if(fromserver.contains("three"))
		{
			//you are player three
			playernumber = 3; 
			myCards = fromserver.split(":");
			
			//out.println("all players connected");
			//GameClassesAndFunctions.waitForPlayers(in, out);
		}
		
		GameClassesAndFunctions.waitTillNull(in, out);
		
		gameBoard frame = new gameBoard(GameClassesAndFunctions.CardsToFilenames(myCards));
        frame.setVisible(true);
		
		//score is number of tricks taken
		int[] scores = {0, 0, 0, 0};
		
		//points are the value of all cards taken. Used during a tie
		int[] points = {0, 0, 0, 0};
 		
		int cardcounter = 3;
		
		boolean myTurn = false;
		
		if(playernumber == 1)
			myTurn = true;
		
		
		//while the game is not over
		while(cardcounter > 0)
		{
			frame.setTurn(myTurn);
			
			frame.SelectedCard = -1;
			
			String[] cardsplayed = {"", "", "", ""};
			
			String suitLed = "";
			
			if(myTurn)
			{
				int i = -1;
				boolean validcardfound = false;
				System.out.println("Waiting for a card to be selected");
				
				while(!validcardfound) 
				{
					Thread.sleep(1); // for some reason this keeps the program from getting stuck in this loop. not sure why
					if(frame.SelectedCard != -1)
					{
						i = frame.SelectedCard;
						if(!myCards[i + 1].equals(""))
							validcardfound = true;
					}
				}
				
				System.out.println("Selected Card Index:" + Integer.toString(i));
				System.out.println(Integer.toString(playernumber) + myCards[i + 1]);

				cardsplayed[playernumber] = myCards[i + 1];
				
				//flips over selected card
				System.out.println("flipping over selected card");
				ImageIcon Cimg = new ImageIcon("src/images/blue_back.png");
                Image imageCimg = Cimg.getImage();
                imageCimg = imageCimg.getScaledInstance(imageCimg.getWidth(null)/10, imageCimg.getHeight(null)/10, imageCimg.SCALE_SMOOTH);
                Cimg.setImage(imageCimg);
				frame.cardbuttons[i].setIcon(Cimg);
				
				//displays card
				ImageIcon playedCard = new ImageIcon("src/images/" + GameClassesAndFunctions.CardToFilename(myCards[i + 1]));
                Image cardImage = playedCard.getImage();
                cardImage = cardImage.getScaledInstance(cardImage.getWidth(null)/10, cardImage.getHeight(null)/10, cardImage.SCALE_SMOOTH);
                frame.back1.setImage(cardImage);
				frame.displaycardsD.repaint();
				
				
				//put played card on server
				out.println(Integer.toString(playernumber) + myCards[i + 1]); //send selected card to others
				suitLed = myCards[i + 1].substring(0, 1); //set the suit led
				
				//wait before reading again
				Thread.sleep(100);
				
				//wait for new card
				String cardPlayed = GameClassesAndFunctions.waitForNewCard(in, out, cardsplayed, myCards[i + 1]);
				System.out.println("AfterWait: " + cardPlayed);
				
				cardsplayed[Integer.parseInt(cardPlayed.substring(0, 1))] = cardPlayed.substring(1);
				
				//display card
				playedCard = new ImageIcon("src/images/" + GameClassesAndFunctions.CardToFilename(cardPlayed.substring(1)));
                cardImage = playedCard.getImage();
                cardImage = cardImage.getScaledInstance(cardImage.getWidth(null)/10, cardImage.getHeight(null)/10, cardImage.SCALE_SMOOTH);
                frame.back2.setImage(cardImage);
				frame.displaycardsD.repaint();
				
				//wait for new card
				cardPlayed = GameClassesAndFunctions.waitForNewCard(in, out, cardsplayed, "NONE");
				cardsplayed[Integer.parseInt(cardPlayed.substring(0, 1))] = cardPlayed.substring(1);
				
				//display card
				playedCard = new ImageIcon("src/images/" + GameClassesAndFunctions.CardToFilename(cardPlayed.substring(1)));
                cardImage = playedCard.getImage();
                cardImage = cardImage.getScaledInstance(cardImage.getWidth(null)/10, cardImage.getHeight(null)/10, cardImage.SCALE_SMOOTH);
                frame.back3.setImage(cardImage);
				frame.displaycardsD.repaint();
				
				
				//set the card you played to "" to prevent use again
				myCards[i + 1] = "";
			}
			else
			{
				//gets card led
				String CardLed = GameClassesAndFunctions.waitForNewCard(in, out, cardsplayed, "NONE");
				suitLed = CardLed.substring(1,2);
				System.out.println(CardLed);
				System.out.println("Suit Led: " + suitLed);
				cardsplayed[Integer.parseInt(CardLed.substring(0, 1))] = CardLed.substring(1);
				
				//displays card led
				ImageIcon playedCard = new ImageIcon("src/images/" + GameClassesAndFunctions.CardToFilename(CardLed.substring(1)));
                Image cardImage = playedCard.getImage();
                cardImage = cardImage.getScaledInstance(cardImage.getWidth(null)/10, cardImage.getHeight(null)/10, cardImage.SCALE_SMOOTH);
                frame.back2.setImage(cardImage);
				frame.displaycardsD.repaint();
				
				
				int i = -1;
				boolean validcardfound = false;
				boolean havesuitled = false;
				
				//checks if you have the suit led
				for(int j = 1; i <= 17; ++i)
				{
					if(!myCards[j].equals(""))
						if(myCards[j].contains(suitLed))
							havesuitled = true;
				}
				
				System.out.println("Waiting for a card to be selected");
				
				frame.SelectedCard = -1;
				
				//while the user is selecting a card
				while(!validcardfound) 
				{
					Thread.sleep(1); // for some reason this keeps the program from getting stuck in this loop. not sure why
					if(frame.SelectedCard != -1)
					{
						i = frame.SelectedCard;
						
						System.out.println("Card Picked:" + myCards[i + 1]);
						
						//set valid to true if that card hasn't been played, and you either follow suit or don't have suit
						if(!havesuitled && !myCards[i + 1].equals(""))
						{
							validcardfound = true;
						}
						else if(!myCards[i + 1].equals("") && myCards[i + 1].substring(0,1).equals(suitLed))
						{
							validcardfound = true;
						}
					}
				}
				
				//card selected
				System.out.println("Selected Card Index:" + Integer.toString(i));
				System.out.println(Integer.toString(playernumber) + myCards[i + 1]);
				cardsplayed[playernumber] = myCards[i + 1];
				
				//flip over card selected
				System.out.println("flipping over selected card");
				ImageIcon Cimg = new ImageIcon("src/images/blue_back.png");
                Image imageCimg = Cimg.getImage();
                imageCimg = imageCimg.getScaledInstance(imageCimg.getWidth(null)/10, imageCimg.getHeight(null)/10, imageCimg.SCALE_SMOOTH);
                Cimg.setImage(imageCimg);
				frame.cardbuttons[i].setIcon(Cimg);
				
				//display card played
				playedCard = new ImageIcon("src/images/" + GameClassesAndFunctions.CardToFilename(myCards[i + 1]));
                cardImage = playedCard.getImage();
                cardImage = cardImage.getScaledInstance(cardImage.getWidth(null)/10, cardImage.getHeight(null)/10, cardImage.SCALE_SMOOTH);
                frame.back1.setImage(cardImage);
				frame.displaycardsD.repaint();
					
				//tell server what card was played
				out.println(Integer.toString(playernumber) + myCards[i + 1]); //tell others what card you played
				myCards[i + 1] = ""; //set the card played to "" so it can't be reused.
				
			
				//get and display the last card played
				String cardPlayed = GameClassesAndFunctions.waitForNewCard(in, out, cardsplayed, "NONE");
				cardsplayed[Integer.parseInt(cardPlayed.substring(0, 1))] = cardPlayed.substring(1);
				//display card
				playedCard = new ImageIcon("src/images/" + GameClassesAndFunctions.CardToFilename(cardPlayed.substring(1)));
                cardImage = playedCard.getImage();
                cardImage = cardImage.getScaledInstance(cardImage.getWidth(null)/10, cardImage.getHeight(null)/10, cardImage.SCALE_SMOOTH);
                frame.back3.setImage(cardImage);
				frame.displaycardsD.repaint();
			}
			
			//decide who won that trick
			int winner = GameClassesAndFunctions.FindRoundWinner(cardsplayed[1], cardsplayed[2], cardsplayed[3], suitLed);
			System.out.println("Winner:" + Integer.toString(winner));
			
			
			//display who won that trick
			frame.showRoundWinner(winner);
			
			//update winner points
			++scores[winner];
			points[winner] += GameClassesAndFunctions.TallyPoints(cardsplayed[1],cardsplayed[2],cardsplayed[3]);
			
			//update scoreboard
			frame.updateScore(scores);
			
			//reset board
			ImageIcon playedCard = new ImageIcon("src/images/blue_back.png");
            Image cardImage = playedCard.getImage();
            cardImage = cardImage.getScaledInstance(cardImage.getWidth(null)/10, cardImage.getHeight(null)/10, cardImage.SCALE_SMOOTH);
            frame.back1.setImage(cardImage);
            frame.back2.setImage(cardImage);
            frame.back3.setImage(cardImage);
			frame.displaycardsD.repaint();
			
			
			//set turn if winner
			if(playernumber == winner)
				myTurn = true;
			else 
				myTurn = false;
			
			
			//make sure nothing is left over on the queue
			GameClassesAndFunctions.ClearQueue(in, out);
			
			//dec card counter
			--cardcounter;
		}
		
		//display the winner
		GameClassesAndFunctions.displayText(GameClassesAndFunctions.displayWinner(GameClassesAndFunctions.findWinner(scores, points))); //Decides the winner, if tie finds winner, displays winner
		
		//close connection
		sock.close();
	}
			
}
