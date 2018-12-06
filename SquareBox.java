import javax.swing.*;
import java.awt.*;
/**
 * This SquareBox Class represents the each box having JPanel of the snake and ladder game including the snakes and ladders
 * 
 * @author      Sujit Ghosh
 * @ID          102233329
 * @email       sujit.bit.0329@gmail.com
 * @version     2.0.0
 */
public class SquareBox extends JPanel
{
    JLabel boxPlaceholder; // Box place holder of number snakes or ladders icons
    Player player; // Player placed into the square box
    boolean snakeBoxStart; // If this is a snake head
    boolean snakeBoxEnd; // If this is a snake tail
    boolean ladderBoxStart; // If this is a ladder bottom
    boolean ladderBoxEnd; // If this is a ladder top
    int boxNumber; // Number of the box
    
    /**
     * This is the constructor of SquareBox class initializing the details of the box having the box bumber
     * 
     * @params boxNumber is a int which determines which number it carries
     */
    public SquareBox(int boxNumber)
    {
        boxPlaceholder = new JLabel(Integer.toString(boxNumber),SwingConstants.CENTER); // Numbering the boxes
        boxPlaceholder.setPreferredSize(new Dimension(20, 20)); // Defining the size of the number label
        this.setLayout(new BorderLayout()); // Setting Border layout for items to put easily
        this.setBorder(BorderFactory.createLineBorder(Color.black)); // Setting black border
        this.setPreferredSize(new Dimension(40, 40)); // Defining the size of the box
        this.boxNumber = boxNumber;
        this.add(boxPlaceholder,BorderLayout.NORTH); // Putting the label at the top of the box
        this.setOpaque(true);
        this.setBackground(Color.WHITE); // Setting background color as white
    }
    
    /**
     * setBoxPlaceholder() method sets the custom placeholder label into the box 
     * 
     * @params placeholder is a JLabel which is put to the square box
     */
    public void setBoxPlaceholder(JLabel placeholder)
    {
        this.boxPlaceholder = placeholder;
    }
    
    /**
     * isSnakeHead() method sets the information that the box is a snake head
     * 
     * @params snakeDestination is an integer setting the information that what should be the tail position of that snake
     */
    public void isSnakeHead(int snakeDestination)
    {
        this.snakeBoxStart = true;
        setBoxImageIcon("snake_head.png"); // Setting the image of snake head to the placeholder
        boxPlaceholder.setText(Integer.toString(boxNumber)+"->"+Integer.toString(snakeDestination)); // Adding information about the destination
    }
    
    /**
     * isSnakeTail() method sets the information that the box is a snake tail
     * 
     */
    public void isSnakeTail()
    {
        this.snakeBoxEnd = true;
        setBoxImageIcon("snake_tail.png"); // Setting the image of snake tail to the placeholder
    }
    
    /**
     * isLadderTop() method sets the information that the box is a ladder top
     * 
     */
    public void isLadderTop()
    {
        this.ladderBoxStart = true;
        setBoxImageIcon("ladder_end.png");// Setting the image of ladder top to the placeholder
    }
    
    /**
     * isLadderBottom() method sets the information that the box is a ladder bottom
     * 
     * @params ladderDestination is an integer setting the information that what should be the top position of that ladder
     */
    public void isLadderBottom(int ladderDestination)
    {
        this.ladderBoxEnd = true;
        setBoxImageIcon("ladder_start.png"); // Setting the image of ladder bottom to the placeholder
        boxPlaceholder.setText(Integer.toString(boxNumber)+"->"+Integer.toString(ladderDestination)); // Adding information about the destination
    }
    
    /**
     * setBoxImageIcon() method sets the Image Icon to the placeholder of the square box
     * 
     * @params imageSource is the source file path of the image icon what should be set into the placeholder
     */
    private void setBoxImageIcon(String imageSource)
    {
        ImageIcon imageIcon = new ImageIcon(imageSource);
        Image image = imageIcon.getImage(); // Transform image 
        Image itemImageIcon = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // Scale image the smooth way   
        imageIcon = new ImageIcon(itemImageIcon);  // Transform it back
        boxPlaceholder.setIcon(imageIcon); // Setting the imageIcon
    }
    
    /**
     * setPlayer() method sets the player to the box
     * 
     * @params player is the Player object which sets to the square box
     */
    public void setPlayer(Player player)
    {
        this.player = player;
        if(player.getPlayerIsHuman())
        {
            this.add(this.player,BorderLayout.EAST); // Setting player in the EAST if its human player
        }else
        {
            this.add(this.player,BorderLayout.WEST); // Setting player in the WEST if its computer player
        }
        this.setBackground(Color.GRAY); // Setting the background color 
        this.repaint();
        this.validate();
    }
    
    /**
     * removeHumnPlayer() method removes the human player from the square box
     *
     */
    public void removeHumnPlayer()
    {
        BorderLayout layout = (BorderLayout)this.getLayout(); // Get the layout
        if(layout.getLayoutComponent(BorderLayout.EAST)!=null) // If the human is found in the layout then remove the player
            this.remove(layout.getLayoutComponent(BorderLayout.EAST));
        if(layout.getLayoutComponent(BorderLayout.WEST)==null) // Don't change the background color if the other player is in the box
            this.setBackground(Color.WHITE);  
        this.repaint();
        this.validate();
    }
    
    /**
     * removeComputerPlayer() method removes the computer player from the square box
     *
     */
    public void removeComputerPlayer()
    {
        BorderLayout layout = (BorderLayout)this.getLayout(); // Get the layout
        if(layout.getLayoutComponent(BorderLayout.WEST)!=null) // If the computer is found in the layout then remove the player
            this.remove(layout.getLayoutComponent(BorderLayout.WEST));
        if(layout.getLayoutComponent(BorderLayout.EAST)==null) // Don't change the background color if the other player is in the box
            this.setBackground(Color.WHITE);  
        this.repaint();
        this.validate();
    }
    
    /**
     * getBoxNumber() method returns the box number
     * 
     * @returns boxNumber is returned as an int 
     *
     */
    public int getBoxNumber()
    {
        return this.boxNumber;
    }
}
