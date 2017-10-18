package GameClasses;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class gameBoard extends JFrame implements ActionListener {
    
	//delcare components
    Container contentPane;
    JLabel gname;
    JButton startButton;
    JButton rulesButton;
    JButton backButton;
    JLabel rulesH;
    JTextArea rulesC;
    JPanel controls;
    JPanel cards;
    JPanel cardsD;
    JPanel displaycards;
    JPanel displaycardsD;
    JPanel playerScore;
    JLabel cardName;
    String lastsuit;
    
    JLabel roundWinner;
    JLabel turnLabel;
    JLabel p1score;
    JLabel p2score;
    JLabel p3score;
    
    int SelectedCard;
    
    JButton[] cardbuttons = new JButton[17];
    
    ImageIcon back1;
    ImageIcon back2;
    ImageIcon back3;
    
    String[] myCards;
    
    
    public gameBoard(String[] UsersCards){
        contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(new Color(9, 135, 40));
        
        controls = new JPanel();
        controls.setLayout(new FlowLayout());
        controls.setBackground(new Color(9, 135, 40));
        
        setTitle("Group Number 1");
        setSize(1350,800);
        setLocation(50,50);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        gname = new JLabel ("Group Number 1", SwingConstants.CENTER);
        gname.setFont(new Font("Times", 2, 64));
        contentPane.add(gname,BorderLayout.CENTER);
        
        startButton = new JButton("START");
        controls.add(startButton);
        startButton.addActionListener(this);
        
        rulesButton = new JButton("RULES");
        controls.add(rulesButton);
        rulesButton.addActionListener(this);
        
        contentPane.add(controls, BorderLayout.SOUTH);
        
        cards = new JPanel(new CardLayout());
        cardsD = new JPanel();
        cardsD.setBackground(new Color(0,0,0));
        
        displaycards = new JPanel(new BorderLayout());
        displaycardsD = new JPanel();
        displaycardsD.setBackground(new Color(9, 135, 40));
        
        myCards = UsersCards;
        
        roundWinner = new JLabel(" ", SwingConstants.CENTER);
		roundWinner.setFont(new Font("Times", 2, 44));
		roundWinner.setVisible(false);

        
		turnLabel = new JLabel("It's your turn!", SwingConstants.CENTER);
		turnLabel.setFont(new Font("Times", 2, 44));
		turnLabel.setVisible(false);

		contentPane.add(turnLabel, BorderLayout.NORTH);
    }
    
    public void selectCards(){
        if (lastsuit == "C"){
            //player can choose that suit only
            
        } else if (lastsuit == "H"){
            
        } else if (lastsuit == "S"){
            
        } else if (lastsuit == "D"){
            
        } else {
            JOptionPane.showMessageDialog(null, "Choose another card to play", "Wrong Card", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //
 // Updates a players score label 
 //
 	public void updateScore(int[]scores) {
	
		p1score.setText(Integer.toString(scores[1]));
	
		p2score.setText(Integer.toString(scores[2]));
	
		p3score.setText(Integer.toString(scores[3]));
 		
 	}
 	
    //cleans the game board
    public void cleanBoard() {
    	back1 = new ImageIcon("src/images/blue_back.png");
        Image imageback1 = back1.getImage();
        imageback1 = imageback1.getScaledInstance(imageback1.getWidth(null)/10, imageback1.getHeight(null)/10, imageback1.SCALE_SMOOTH);
        back1.setImage(imageback1);
        
        back2 = new ImageIcon("src/images/blue_back.png");
        Image imageback2 = back2.getImage();
        imageback2 = imageback2.getScaledInstance(imageback2.getWidth(null)/10, imageback2.getHeight(null)/10, imageback2.SCALE_SMOOTH);
        back2.setImage(imageback2);
        
        back3 = new ImageIcon("src/images/blue_back.png");
        Image imageback3 = back3.getImage();
        imageback3 = imageback3.getScaledInstance(imageback3.getWidth(null)/10, imageback3.getHeight(null)/10, imageback3.SCALE_SMOOTH);
        back3.setImage(imageback3);
        
        displaycardsD.repaint();
    }
    
    //displays myturn text
	public void setTurn(boolean myTurn) {
		
		if (myTurn) {
			turnLabel.setVisible(true);
		} else {
			turnLabel.setVisible(false);
		}
		
	}
	
	//shows who wins a trick
	public void showRoundWinner(int player) throws InterruptedException {
	 	
 		if (player == 1) {
 			roundWinner.setText("Player One wins the round!");
 		} else if (player == 2) {
 			roundWinner.setText("Player Two wins the round!");
 		} else if (player == 3) {
 			roundWinner.setText("Player Three wins the round!");
 		}
 		roundWinner.setVisible(true);
 		Thread.sleep(5000);
 		roundWinner.setVisible(false);
 	}
	

	
    @Override
    public void actionPerformed(ActionEvent event) {
        // TODO Auto-generated method stub
    
    	
        if (event.getSource() instanceof JButton) {
            JButton clickedButton = (JButton) event.getSource();
            
            System.out.println("Button pressed");
            
            if (clickedButton == startButton) {
            	//show board and stuff
            	
                controls.setVisible(false);
                gname.setVisible(false);
                
                for (int i=0; i<myCards.length; i++){
                    ImageIcon Cimg = new ImageIcon("src/images/" + myCards[i]);
                    Image imageCimg = Cimg.getImage();
                    imageCimg = imageCimg.getScaledInstance(imageCimg.getWidth(null)/10, imageCimg.getHeight(null)/10, imageCimg.SCALE_SMOOTH);
                    Cimg.setImage(imageCimg);
                    
                    cardbuttons[i] = new JButton(Cimg);
                    controls.add(cardbuttons[i]);
                    cardbuttons[i].addActionListener(this);
                    
                    cardsD.add(cardbuttons[i]);
                    cards.add(cardsD, null);
                    contentPane.add(cards, BorderLayout.SOUTH);
                }
                
                //three cards on board
                back1 = new ImageIcon("src/images/blue_back.png");
                Image imageback1 = back1.getImage();
                imageback1 = imageback1.getScaledInstance(imageback1.getWidth(null)/10, imageback1.getHeight(null)/10, imageback1.SCALE_SMOOTH);
                back1.setImage(imageback1);
                
                back2 = new ImageIcon("src/images/blue_back.png");
                Image imageback2 = back2.getImage();
                imageback2 = imageback2.getScaledInstance(imageback2.getWidth(null)/10, imageback2.getHeight(null)/10, imageback2.SCALE_SMOOTH);
                back2.setImage(imageback2);
                
                back3 = new ImageIcon("src/images/blue_back.png");
                Image imageback3 = back3.getImage();
                imageback3 = imageback3.getScaledInstance(imageback3.getWidth(null)/10, imageback3.getHeight(null)/10, imageback3.SCALE_SMOOTH);
                back3.setImage(imageback3);
                
                displaycardsD.add(new JLabel(back1));
                displaycardsD.add(new JLabel(back2));
                displaycardsD.add(new JLabel(back3));
                
                displaycards.add(displaycardsD, null);
                contentPane.add(displaycards, BorderLayout.CENTER);
                
                //the area that show the players' score
                playerScore = new JPanel();
                playerScore.setBackground(new Color (255,255,255));
                playerScore.setPreferredSize(new Dimension(120, 800));
                
                JLabel p1 = new JLabel ("P1");
                JLabel p2 = new JLabel ("P2");
                JLabel p3 = new JLabel ("P3");
                p1.setPreferredSize(new Dimension(35,20));
                p2.setPreferredSize(new Dimension(35,20));
                p3.setPreferredSize(new Dimension(35,20));
                
                playerScore.add(p1,null);
                playerScore.add(p2,null);
                playerScore.add(p3,null);
                
                p1score = new JLabel ("0");
                p2score = new JLabel ("0");
                p3score = new JLabel ("0");
                
                p1score.setPreferredSize(new Dimension(35,30));
                p2score.setPreferredSize(new Dimension(35,30));
                p3score.setPreferredSize(new Dimension(35,30));
                
                playerScore.add(p1score,null);
                playerScore.add(p2score,null);
                playerScore.add(p3score,null);
                
                contentPane.add(playerScore, BorderLayout.EAST);
                
                
            } else if (clickedButton == rulesButton) {
                //show rules
            	
                controls.setVisible(false);
                gname.setVisible(false);
                
                rulesH = new JLabel("RULES", SwingConstants.CENTER);
                rulesH.setFont(new Font("Times", 2, 50));
                
                contentPane.add(rulesH, BorderLayout.NORTH);
                
                rulesC = new JTextArea("Play:\nP1 leads the first round. \nFor each round each player plays one card in order."
                                       + "The player leading a round leads a card in the hand. \nThe next player plays based on the"
                                       + "following rule. \nIf player has cards in the suit lead then plays one in the suit. \nElse "
                                       + "plays a card from any remaining suit. \nThe trick/round of cards is won, and belongs, "
                                       + "to the player who plays the highest card in the suit that is lead. \nThe winner of a round "
                                       + "leads the next round.\n"
                                       + "Score and Winning: \nPlayer who wins the highest number of tricks/rounds wins the game. \n\n"
                                       + "When there is a tie – in the number of tricks/rounds won, \n sum up all the points from the winning rounds for each of the players tied for highest number of tricks. \n"
                                       + "The player with highest points total wins the game. \n"
                                       + "If there is still a tie (in the total points) then the game is tied.");
                rulesC.setColumns(27);
                rulesC.setRows(11);
                rulesC.setEditable(false);
                rulesC.setBackground(new Color(9, 135, 40));
                contentPane.add(rulesC, BorderLayout.CENTER);
                
                backButton = new JButton("BACK");
                backButton.setSize(80, 20);
                backButton.addActionListener(this);
                rulesH.add(backButton, BorderLayout.SOUTH);
                
            } else if(clickedButton == backButton) {
                //close out of rules page
                contentPane.remove(rulesH);
                contentPane.remove(rulesC);
                controls.setVisible(true);
                gname.setVisible(true);
            } else {
	            for(int i = 0; i < 17; ++i)
	            {
	            	//check which card was clicked
	            	System.out.println("Checking card button:" + Integer.toString(i));
	            	if(clickedButton == cardbuttons[i])
	            	{
	            		System.out.println("Card button identified");
	            		SelectedCard = i;
	            	}
	            }
            }
            
        }
    }
    
}

