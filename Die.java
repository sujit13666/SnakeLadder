import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
/**
 * This Die Class is a JButton which represents the die to roll in the snake ladder game
 * 
 * @author      Sujit Ghosh
 * @ID          102233329
 * @email       sujit.bit.0329@gmail.com
 * @version     2.0.0
 */
public class Die extends JButton
{
    int dieValue = 0; // Die value after a roll
    boolean enabled = true; // Enability to click the die
    
    /**
     * This is the constructor of Die class initializing the button text and size
     */
    public Die()
    {
        super("Roll Die");
        this.setEnabled(true);
        this.setPreferredSize(new Dimension(20, 20));
    }
    
    /**
     * rollTheDie() method generates the die face value from 1 to 6
     * 
     * @return dieValue is returned as an integer from 1 to 6
     */
    public int rollTheDie()
    {
        this.dieValue = ThreadLocalRandom.current().nextInt(1, 7);
        return this.dieValue;
    }
    
    /**
     * getDieValue() method returns the current die value
     * 
     * @return dieValue is returned
     */
    public int getDieValue()
    {
        return this.dieValue;
    }
    
    /**
     * setDieValue() method sets the die value
     * 
     * @params dieValue is set
     */
    public void setDieValue(int dieValue)
    {
        this.dieValue = dieValue;
    }
    
    /**
     * setEnable() method sets the enability of the die buttton
     * 
     * @params enabled is a boolean provided to set the enability of the button
     */
    public void setEnable(boolean enabled)
    {
        this.enabled = enabled;
        this.setEnabled(enabled);
    }
}
