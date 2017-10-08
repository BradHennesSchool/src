package GameClasses;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class gameBoard extends JFrame implements ActionListener {

    Container contentPane;
    JLabel gname;
    JButton startButton;
    JButton rulesButton;
    JLabel rulesH;
    JPanel controls;
    JPanel cards;
    JPanel cardsD;
    JPanel playedcards;
    JPanel playedcardsD;
    
    String[] myCards;

    public gameBoard(String[] UsersCards){
    	
    	System.out.println(UsersCards[0]);
    	
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

        myCards = UsersCards;
        
        gname = new JLabel ("Group Number 1", SwingConstants.CENTER);
        gname.setFont(new Font("Times", 2, 64));
        contentPane.add(gname,BorderLayout.CENTER);
        
        startButton = new JButton("START");
        controls.add(startButton);
        startButton.addActionListener(this);


        
        rulesButton = new JButton("RULES");
        controls.add(rulesButton);
        rulesButton.addActionListener(this);
        
        contentPane.add(controls, BorderLayout.SOUTH); ;

        cards = new JPanel(new CardLayout());
        cardsD = new JPanel();
        cardsD.setBackground(new Color(0,0,0));
        
        playedcards = new JPanel(new BorderLayout());
        playedcardsD = new JPanel();
        playedcardsD.setBackground(new Color(9, 135, 40));
    }

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		
		String playerName;
		
		if (event.getSource() instanceof JButton) {
            JButton clickedButton = (JButton) event.getSource();

            if (clickedButton == startButton) {
            	
            	//playerName = JOptionPane.showInputDialog ("Enter player's name:");
            	controls.setVisible(false);
            	gname.setVisible(false);
            	
            	
                for (int i=0; i<myCards.length; i++){
                	ImageIcon Cimg = new ImageIcon("src/images/" + myCards[i]);
            		System.out.println(myCards[i]);
                	Image imageCimg = Cimg.getImage();
                	imageCimg = imageCimg.getScaledInstance(imageCimg.getWidth(null)/10, imageCimg.getHeight(null)/10, imageCimg.SCALE_SMOOTH);
                	Cimg.setImage(imageCimg);
                    
	                cardsD.add(new JLabel(Cimg));
	                cards.add(cardsD, null);
	                contentPane.add(cards, BorderLayout.SOUTH);
                }
                
                ImageIcon back1 = new ImageIcon("src/images/KC.png");
            	Image imageback1 = back1.getImage();
            	imageback1 = imageback1.getScaledInstance(imageback1.getWidth(null)/10, imageback1.getHeight(null)/10, imageback1.SCALE_SMOOTH);
                back1.setImage(imageback1);
                
                ImageIcon back2 = new ImageIcon("src/images/10C.png");
            	Image imageback2 = back2.getImage();
            	imageback2 = imageback2.getScaledInstance(imageback2.getWidth(null)/10, imageback2.getHeight(null)/10, imageback2.SCALE_SMOOTH);
                back2.setImage(imageback2);
                
                playedcardsD.add(new JLabel(back1));
                playedcardsD.add(new JLabel(back2));
                playedcards.add(playedcardsD, null);
                contentPane.add(playedcards, BorderLayout.CENTER);
                
            	
            } else if (clickedButton == rulesButton) {
            
            	controls.setVisible(false);
            	gname.setVisible(false);
            	
            	rulesH = new JLabel("RULES");
            	contentPane.add(rulesH, BorderLayout.NORTH);
            	            	
            	JTextArea rulesC = new JTextArea("Play:\nP1 leads the first round. \nFor each round each player plays one card in order." 
    	           		+ "The player leading a round leads a card in the hand. \nThe next player plays based on the"
    	           		+ "following rule. \nIf player has cards in the suit lead then plays one in the suit. \nElse "
    	           		+ "plays a card from any remaining suit. \nThe trick/round of cards is won, and belongs, "
    	           		+ "to the player who plays the highest card in the suit that is lead. \nThe winner of a round "
    	           		+ "leads the next round.\n"
    	           		+ "Score and Winning: \nPlayer who wins the highest number of tricks/rounds wins the game. \n"
                		+ "When there is a tie – in the number of tricks/rounds won, \n sum up all the points from the winning rounds for each of the players tied for highest number of tricks. \n"
                		+ "The player with highest points total wins the game. \n"
                		+ "If there is still a tie (in the total points) then the game is tied.");
                rulesC.setColumns(27);
                rulesC.setRows(11);
                rulesC.setEditable(false);
                rulesC.setBackground(new Color(9, 135, 40));
                contentPane.add(rulesC, BorderLayout.CENTER);
                
            }
            
		}
	}
	/**
	public static void main(String[] args){
    	gameBoard frame = new gameBoard();
        frame.setVisible(true);
    }
	**/
}
