/**
 * This App Class consists of main method as well as the start of the game access
 * 
 * @author      Sujit Ghosh
 * @ID          102233329
 * @email       sujit.bit.0329@gmail.com
 * @version     2.0.0
 */
public class App
{
    public static void main(String args[])
    {
        // Creating object of the snake and ladder game  
        SnakeAndLadderGame snakeAndLadderGame = new SnakeAndLadderGame();
        snakeAndLadderGame.playNewGame(true); // staring the game
    }
}
