import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.IntStream;
/**
 * This SnakeAndLadderGame Class consists of main frame and the all functionality regarding the game Implementing ActionListener and MouseListener
 * 
 * @author      Sujit Ghosh
 * @ID          102233329
 * @email       sujit.bit.0329@gmail.com
 * @version     2.0.0
 */
public class SnakeAndLadderGame implements ActionListener, MouseListener
{
    ArrayList<SquareBox> squareBoxList; // ArrayList for 50 squares
    
    boolean firstTurn = true; // Flag to check the first turn to determine who will go first
    boolean humanTurn = true; // Flag to check if it is human player's turn or not
    boolean dieRolled = false; // Flag to check if the die is just rolled or yet to be rolled
    
    int computerPlayerPosition = 0; // Computer player's position
    int humanPlayerPosition = 0; // Human player's position
    int dieValue = 0; // Value of rolled die
    
    JFrame mainGameFrame; // Main game board frame
    JTextArea textArea; // Action details text area
    JButton rollDieButton; // Die button
    
    Player humanPlayer; // Human player
    Player computerPlayer; // Computer player
    
    JLabel humanPlayerPositionLabel; // Label for the position of the human player
    JLabel computerPlayerPositionLabel; // Label for the postition of the computer player
    JLabel humanPlayerNameLabel; // Label for the name of the human player
    JLabel computerPlayerNameLabel; // label for the name of the computer player

    JPanel squarePanel; // JPanel for all 50 squares
    JLabel dieCounter; // Counter label for die values
    
    // Snake and ladder start and end positions
    private int[] ladderStarts = { 4, 15, 22, 30, 38 };
    private int[] ladderEnds = {14, 36, 42, 35, 42 };
    private int[] snakeStarts = {47, 34, 25, 18, 12 };
    private int[] snakeEnds = {20, 24, 17, 8, 5 };
    
    /**
     * This is the constructor of SnakeAndLadderGame class initializing the empty square list, players and die to roll
     */
    public SnakeAndLadderGame()
    {
        squareBoxList = new ArrayList<SquareBox>(); // Initializing the empty arraylist for data packets

        // Initializing player pieces
        humanPlayer = new Player("human.png",true);
        computerPlayer = new Player("computer.png",false);

        // Initializing die counter and adding ActionListener
        rollDieButton = new Die();
        rollDieButton.addActionListener(this);
    }
    
    /**
     * playNewGame() method is the start point of the snake and ladder game
     * 
     * @param newGame boolean to check if this is a complete new game or not
     */
    public void playNewGame(boolean newGame)
    {
        // Check if its a complete new game or replaying a game
        if(newGame)
        {
            createBoard(); // Creating the game board of snake ladder
            JOptionPane.showMessageDialog(null,"Welcome to Snake and Ladder.\nRoll the dice to determine who will play the first turn. \nLarger number would play the first turn.");
        }else
        {
            // Initializing all the values to its original starting position to play the game again
            // Removing the pieces of the players from the board
            squareBoxList.get(humanPlayerPosition-1).removeHumnPlayer();
            squareBoxList.get(computerPlayerPosition-1).removeComputerPlayer();
            JOptionPane.showMessageDialog(null,"Roll the dice to play");
            
            // Initializing the positions
            computerPlayerPosition = 0;
            humanPlayerPosition = 0;   
            
            // Initializing the labels of the position
            humanPlayerPositionLabel.setText(Integer.toString(0));
            computerPlayerPositionLabel.setText(Integer.toString(0));
            
            // Initializing the die counter
            dieCounter.setText(Integer.toString(0)); 
            dieValue = 0;
            rollDieButton.setEnabled(true);
            
            // Initializing flags
            firstTurn=true;
            humanTurn = true;
            dieRolled = false;
            
            textArea.setText(null); // Initializing the action description texts as empty
            
            // Coloring the player turns
            humanPlayerNameLabel.setBackground(Color.CYAN);
            computerPlayerNameLabel.setBackground(Color.WHITE);
        }  
    }
    
    /**
     * createBoard() method is responsible to create the whole game board from top panel to center and the end panel where all components are added
     */
    public void createBoard()
    {
        // Creating Game Frame
        mainGameFrame = new JFrame("Snake & Ladder");
        mainGameFrame.setResizable(false);
        mainGameFrame.setLayout(new BorderLayout());
        
        // Board center panel for squares
        squarePanel = new JPanel();
        squarePanel.setLayout(new GridLayout(10, 5));
        squarePanel.setPreferredSize(new Dimension(100, 600));
        squarePanel.setMaximumSize(new Dimension(100, 600));

        // Creating squares
        for(int i = 1;i<=50; i++)
        {
            SquareBox squareBox = new SquareBox(i);
            squareBoxList.add(squareBox); // added squares into square array list     
        }
        
        //Adding Snake and ladders into squares
        for(int i=0;i<5;i++)
        {
            squareBoxList.get(ladderStarts[i]-1).isLadderBottom(ladderEnds[i]);
            squareBoxList.get(ladderEnds[i]-1).isLadderTop();
            squareBoxList.get(snakeStarts[i]-1).isSnakeHead(snakeEnds[i]);
            squareBoxList.get(snakeEnds[i]-1).isSnakeTail();
        }
        
        // Adding squares into center panel
        for ( int i=(squareBoxList.size()-1);i>=0;i--) 
        {
            squarePanel.add(squareBoxList.get(i)); 
            squareBoxList.get(i).addMouseListener(this); // Adding mouse click listeners
        }
        
        mainGameFrame.add(squarePanel, BorderLayout.CENTER);
        
        // Creaye Board Top Panel
        createTopInfoPanel();
        
        // Board Die and Player bottom panel
        createBottomInfoPanel();
        
        // Finishing frame configuration
        mainGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGameFrame.pack();
        mainGameFrame.setLocationRelativeTo(null);
        mainGameFrame.setVisible(true);
        mainGameFrame.setSize(700, 700);
    }
    
    /**
     * positionAfterOnLadderOrSnake() method checks if the player has been eaten by snake or climbed by the ladders
     * 
     * @params playerPosition initial position of the player 
     * 
     * @return final position of the player after snake and ladder
     */
    public int positionAfterOnLadderOrSnake(int playerPosition)
    {
        for(int l = 0; l<ladderStarts.length;l++)
        {
            // Checking if the player is stepped into a ladder start
            if(playerPosition==ladderStarts[l])
            {
                textArea.append((humanTurn?"You are climbing":"Computer is climbing")+" ladder \n");
                textArea.append((humanTurn?"Your":"Computer")+" position: "+ladderEnds[l]+"\n");
                return ladderEnds[l]; // Returning the ladder top position
            }
        }
        
        for(int s = 0; s<snakeStarts.length;s++)
        {
            // Checking if the player is stepped into a snake head
            if(playerPosition==snakeStarts[s])
            {
                textArea.append((humanTurn?"You have been eated":"Computer has been eaten")+" by snake \n");
                textArea.append((humanTurn?"Your":"Computer")+" position: "+snakeEnds[s]+"\n");
                return snakeEnds[s]; // Returning the snake tail position
            }
        }
        textArea.append((humanTurn?"Your":"Computer")+" position: "+playerPosition+"\n"); 
        return playerPosition; // Returning original position if no snake or ladder being intercepted
    }
    
    /**
     * createTopInfoPanel() method creates the top information panel where all the information regarding the developer and application being shown 
     * 
     */
    private void createTopInfoPanel()
    {
        JPanel winningPanel = new JPanel(new GridLayout(3,1));
        JLabel gameName = new JLabel("Snake & Ladder",SwingConstants.CENTER);
        gameName.setFont(gameName.getFont().deriveFont(24f)); 
        winningPanel.add(new JLabel("Welcome to",SwingConstants.CENTER));
        winningPanel.add(gameName);
        winningPanel.add(new JLabel("By Sujit Ghosh, ID: 102233329, Email: sujit.bit.0329@gmail.com",SwingConstants.CENTER));
        winningPanel.setPreferredSize(new Dimension(800, 80));
        mainGameFrame.add(winningPanel, BorderLayout.NORTH);
    }
    
    /**
     * createBottomInfoPanel() method creates all the necessary panels and buttons and text areas for the detailed description and actions of the game
     * 
     */
    private void createBottomInfoPanel()
    {
        // Creating main bottom panel
        JPanel bottomPanel = new JPanel(new GridLayout(1,2));
        bottomPanel.setPreferredSize(new Dimension(800, 150));
        
        // Creating Action Details Panel
        JPanel actionDetailsPanel = new JPanel(new BorderLayout());
        actionDetailsPanel.add(new JLabel("Action Details",SwingConstants.CENTER), BorderLayout.NORTH);
        
        // Adding the detailed descriptive message panel
        textArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(textArea); 
        textArea.setEditable(false);
        actionDetailsPanel.add(scrollPane,BorderLayout.CENTER);
        bottomPanel.add(actionDetailsPanel);
        
        // Adding players in to the bottom panel
        JPanel playerPanel = new JPanel(new GridLayout(2,4));
        humanPlayerNameLabel = new JLabel("   You : ");
        computerPlayerNameLabel = new JLabel("   Computer : ");
        
        // Adding background color of the players to specify the turn
        humanPlayerNameLabel.setOpaque(true);
        humanPlayerNameLabel.setBackground(Color.WHITE);
        computerPlayerNameLabel.setOpaque(true);
        computerPlayerNameLabel.setBackground(Color.WHITE);
        if(humanTurn)
        {
            humanPlayerNameLabel.setBackground(Color.CYAN);
        }

        // Adding player information into the bottom panel
        playerPanel.add(humanPlayerNameLabel);
        playerPanel.add(srinkedImageLabel("human.png"));
        humanPlayerPositionLabel = new JLabel(Integer.toString(humanPlayer.getPlayerPosition()),SwingConstants.CENTER);
        playerPanel.add(humanPlayerPositionLabel);

        // Adding die counter and die button into the bottom panel
        dieCounter = new JLabel("0",SwingConstants.CENTER);
        dieCounter.setFont(dieCounter.getFont().deriveFont(35f));
        playerPanel.add(dieCounter);
        playerPanel.add(computerPlayerNameLabel);
        playerPanel.add(srinkedImageLabel("computer.png"));
        computerPlayerPositionLabel = new JLabel(Integer.toString(computerPlayer.getPlayerPosition()),SwingConstants.CENTER);
        playerPanel.add(computerPlayerPositionLabel);
        
        playerPanel.add(rollDieButton);
        bottomPanel.add(playerPanel);
        mainGameFrame.add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * actionPerformed() method is the implemented method of the ActionListener interface where rolling dice button is performed
     * 
     * @params e is the ActionEvent of the button press
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Rolling the die
        dieValue = ((Die)e.getSource()).rollTheDie();
        textArea.append("Your Die Rolled: " + dieValue+"\n");
        dieCounter.setText(Integer.toString(dieValue));
        
        // Check if this is the first turn to determine who would play first
        if(firstTurn)
        {
            humanPlayerPosition = dieValue; // human position sould be the die face value
            computerPlayerPosition = ((Die)rollDieButton).rollTheDie(); // getting computer player position 
            textArea.append("Computer Die Rolled: " + computerPlayerPosition+"\n");
            
            // Determine who will play first depending on whose die face value is greater
            if(humanPlayerPosition>computerPlayerPosition)
            {
                JOptionPane.showMessageDialog(null,"You have the higher number.\nSo, you will play first.\nPress box: "+humanPlayerPosition+ " to go to that position\n");
                textArea.append("You have the higher number.\nSo, you will play first.\nPress box: "+humanPlayerPosition+" to go to that position\n");
                computerPlayerPosition=0; // If human value is greater then make computer value zero 
                dieRolled = true;
                humanTurn=true;
                rollDieButton.setEnabled(false); // disable die button for user to click on the square box
            }else if(computerPlayerPosition>humanPlayerPosition)
            {
                JOptionPane.showMessageDialog(null,"Computer has the higher number.\nSo, computer will play first.\n");
                textArea.append("Computer has the higher number.\nSo, computer will play first\n");
                humanPlayerPosition=0; // If computer value is greater then make human value zero 
                humanTurn=false;
                moveComputerPlayer(); // Initiale the computer move autometically for the first time
            }else
            {
                // If both of the values are the same then show messages and do nothing so that the user can roll the dice again
                JOptionPane.showMessageDialog(null,"Both of you and computer's numbers are same.\nPlease roll the dice again");
                textArea.append("Both of your numbers are same.\nPlease roll the dice again");
            }
        }else
        {
            // If crossing out of the board then just let the function handle the exception or if not then wait to user to press the square box
            if((dieValue+humanPlayerPosition)>50)
            {
                moveHumanPlayer();
            }else{
                ((Die)e.getSource()).setEnabled(false);
                dieRolled = true;
            }  
        }              
    }
    
    /**
     * mouseClicked() method is the implemented method of the MouseListener interface where action after clicking into the square box is performed
     * 
     * @params e is the MouseEvent of the mouse click
     */
    public void mouseClicked(MouseEvent e) {
        // Check if human player rolled the die first
        if(dieRolled)
        {
            // Check if its correct choice of box place and time to move the human player piece
            if((((SquareBox)e.getSource()).getBoxNumber()==(humanPlayerPosition+dieValue)) ||
                (firstTurn && (((SquareBox)e.getSource()).getBoxNumber()==(humanPlayerPosition))))
            {
                moveHumanPlayer(); // Moving the human player piece
                dieRolled = false; 
            }else
            {
                // Check if its the first time selecting the square
                if(firstTurn)
                {
                    JOptionPane.showMessageDialog(null,"Wrong Choice, please click correct box no: "+(humanPlayerPosition));
                }else
                {
                    JOptionPane.showMessageDialog(null,"Wrong Choice, please click correct box no: "+(humanPlayerPosition+dieValue));
                }
                textArea.append("Wrong Choice Try clicking into correct box \n");
            }
        }else
        {
            // Showing message to roll the die first them select the destination square
            JOptionPane.showMessageDialog(null,"Roll the die first");
        }
    }
    
    /**
     * mousePressed() method is the implemented method of the MouseListener interface
     * 
     * @params e is the MouseEvent of the mouse press
     */
    public void mousePressed(MouseEvent e) {}

    /**
     * mouseReleased() method is the implemented method of the MouseListener interface
     * 
     * @params e is the MouseEvent of the mouse release
     */
    public void mouseReleased(MouseEvent e) {}

    /**
     * mouseEntered() method is the implemented method of the MouseListener interface
     * 
     * @params e is the MouseEvent of the mouse enter
     */
    public void mouseEntered(MouseEvent e) {}

    /**
     * mouseExited() method is the implemented method of the MouseListener interface
     * 
     * @params e is the MouseEvent of the mouse exit
     */
    public void mouseExited(MouseEvent e) {}

    /**
     * moveHumanPlayer() method is responsible to move the human player piece from one place to another after the perfect box has been selected by the player
     * 
     */
    public void moveHumanPlayer()
    {
        if(firstTurn)
        {
            humanPlayerPosition = positionAfterOnLadderOrSnake(humanPlayerPosition); // Check the final position after snake and ladder
            humanPlayer.setPlayerPosition(humanPlayerPosition); // Setting player position into player
            humanPlayerPositionLabel.setText(Integer.toString(humanPlayerPosition)); // Setting player position into the frame label
            squareBoxList.get(humanPlayerPosition-1).setPlayer(humanPlayer); // Set human player to the new place
            firstTurn=false;
        }else if(humanTurn && (humanPlayerPosition+dieValue<=50)) // Check if its human's turn or not
        {
            if(humanPlayerPosition>0)squareBoxList.get(humanPlayerPosition-1).removeHumnPlayer(); // remove human player from old place
            
            // calculate new position for human player
            humanPlayerPosition+=dieValue;
            humanPlayerPosition = positionAfterOnLadderOrSnake(humanPlayerPosition);
            humanPlayer.setPlayerPosition(humanPlayerPosition);
            humanPlayerPositionLabel.setText(Integer.toString(humanPlayerPosition));
            squareBoxList.get(humanPlayerPosition-1).setPlayer(humanPlayer); // Set human player to the new place
        }else{
            // If the total value if more than 50 then the turn is skipped
            JOptionPane.showMessageDialog(null,"Your Chance has been skipped. Try next time to win.");
            textArea.append("Your Chance has been skipped"+"\n");
        }
        
        // Check if human won or got a six to play again
        if(humanPlayerPosition==50)
        {
            JOptionPane.showMessageDialog(null,"Congratulations, YOU WON");
            rollDieButton.setEnabled(false);
            humanPlayerNameLabel.setBackground(Color.WHITE);
            computerPlayerNameLabel.setBackground(Color.WHITE);
            
            // Prompting if want to play again
            int choice=JOptionPane.showConfirmDialog(null,"Want to play the game again?");
            if(choice==JOptionPane.YES_OPTION)
            {
                playNewGame(false);
            }
            
        }else if(dieValue==6 && humanPlayerPosition!=50){
            // If its a six then die can be rolled again by user
            JOptionPane.showMessageDialog(null,"You got a 6, Roll dice again");
            humanPlayerNameLabel.setBackground(Color.CYAN);
            computerPlayerNameLabel.setBackground(Color.WHITE);
            rollDieButton.setEnabled(true);
        }else
        {
            // Else just move on the turn to the computer
            humanPlayerNameLabel.setBackground(Color.WHITE);
            computerPlayerNameLabel.setBackground(Color.CYAN);
            humanTurn=false;
            moveComputerPlayer();
        }
    }
    
    /**
     * moveComputerPlayer() method is responsible to move the computer player piece from one place to another whenever its computer's turn
     * 
     */
    public void moveComputerPlayer()
    {
        // If not the first turn just normally roll the die
        if(!firstTurn)
        {
            dieValue = ((Die)rollDieButton).rollTheDie();
            textArea.append("Computer Die Rolled: " + dieValue+"\n");
        }
        
        if(firstTurn)
        {
            dieValue = computerPlayerPosition; // Die value is the computerPlayerPosition on the first turn
            computerPlayerPosition = positionAfterOnLadderOrSnake(computerPlayerPosition); // Final player position after snake and ladders
            computerPlayer.setPlayerPosition(computerPlayerPosition); // Set player position into the player
            computerPlayerPositionLabel.setText(Integer.toString(computerPlayerPosition)); // Set player position into the frame label
            squareBoxList.get(computerPlayerPosition-1).setPlayer(computerPlayer); // Set computer player to the new place
            firstTurn=false;
        }else if(!humanTurn && ((computerPlayerPosition+dieValue<=50)))
        {
            if(computerPlayerPosition>0)squareBoxList.get(computerPlayerPosition-1).removeComputerPlayer(); // remove computer player from old place
            
            // calculate new position for computer player
            computerPlayerPosition+=dieValue;
            computerPlayerPosition = positionAfterOnLadderOrSnake(computerPlayerPosition);
            computerPlayer.setPlayerPosition(computerPlayerPosition);
            computerPlayerPositionLabel.setText(Integer.toString(computerPlayerPosition));
            squareBoxList.get(computerPlayerPosition-1).setPlayer(computerPlayer); // Set computer player to the new place
        }else{
            // If the total value if more than 50 then the turn is skipped
            textArea.append("Computer's Chance has been skipped"+"\n");
            JOptionPane.showMessageDialog(null,"Computer's Chance has been skipped. Your turn now.");
        }
        
        if(computerPlayerPosition==50)
        {
            JOptionPane.showMessageDialog(null,"Sorry, YOU LOST");
            rollDieButton.setEnabled(false);
            humanPlayerNameLabel.setBackground(Color.WHITE);
            computerPlayerNameLabel.setBackground(Color.WHITE);
            
            // Prompting if want to play again
            int choice=JOptionPane.showConfirmDialog(null,"Want to play the game again?");
            if(choice==JOptionPane.YES_OPTION)
            {
                playNewGame(false);
            }
        }else if(dieValue==6 && computerPlayerPosition!=50)
        {
            // If its a six then its again computers turn
            JOptionPane.showMessageDialog(null,"Computer got a 6, It going to play again");
            rollDieButton.setEnabled(false);
            moveComputerPlayer(); // Again computer is moving
        }else
        {
            // Else turn is sent to the human player
            rollDieButton.setEnabled(true);
            humanTurn=true;
            humanPlayerNameLabel.setBackground(Color.CYAN);
            computerPlayerNameLabel.setBackground(Color.WHITE);
        }
    }
    
    /**
     * srinkedImageLabel() method creates JLalbels with image specified via parameters
     * 
     * @params imageIconSource is a string having the source path of image to add into JLabels
     * 
     * @return imageLabel is returned as JLabel which has the image as icon
     */
    public JLabel srinkedImageLabel(String imageIconSource)
    {
        ImageIcon imageIcon = new ImageIcon(imageIconSource);
        Image image = imageIcon.getImage(); // Transform image 
        Image playerImage = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // Scale image the smooth way  
        ImageIcon playerImageIcon = new ImageIcon(playerImage);  // Transform the image back
        JLabel imageLabel = new JLabel("",SwingConstants.CENTER); // Initializing the JLabel
        imageLabel.setBackground(Color.WHITE);
        imageLabel.setOpaque(true);
        imageLabel.setIcon(playerImageIcon); // Setting the imageIcon
        return imageLabel;
    }
}
