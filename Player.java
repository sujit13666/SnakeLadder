import javax.swing.*;
import java.awt.*;
/**
 * This Player Class represents the piece of the player which traverse from box to box in the snake and ladder board
 * 
 * @author      Sujit Ghosh
 * @ID          102233329
 * @email       sujit.bit.0329@gmail.com
 * @version     2.0.0
 */
public class Player extends JLabel
{
    ImageIcon playerImageIcon; // Image icon of the player
    int playerPosition = 0; // Player position
    boolean humanPlayer = false; // If its the human player
    
    /**
     * This is the constructor of Player class initializing the details of player
     * 
     * @params imageIconSource is a source path of image icon
     * @params humanPlayer is the boolean to set if this is the human player or not
     */
    public Player(String imageIconSource,boolean humanPlayer)
    {
        super("",SwingConstants.CENTER); // Calling parent class to put the image in middle
        this.humanPlayer = humanPlayer;
        ImageIcon imageIcon = new ImageIcon(imageIconSource);
        Image image = imageIcon.getImage(); // Transform image 
        Image playerImage = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way  
        playerImageIcon = new ImageIcon(playerImage);  // Transform the image back
        this.setPreferredSize(new Dimension(20, 20));        
        this.setIcon(playerImageIcon); // Setting the icon
    }
    
    /**
     * setPlayerPosition() method sets the position of the player
     * 
     * @params position is provided as integer to set the current position of the player
     */
    public void setPlayerPosition(int position)
    {
        this.playerPosition = position;
    }
    
    /**
     * getPlayerPosition() method returns the position of the player
     * 
     * @retun playerPosition is returned as integer
     */
    public int getPlayerPosition()
    {
        return this.playerPosition;
    }
    
    /**
     * getPlayerIsHuman() method returns if the player is human or not
     * 
     * @retun humanPlayer is returned as boolean
     */
    public boolean getPlayerIsHuman()
    {
        return humanPlayer?true:false;
    }
    
}
