import java.awt.Color;
import java.util.HashSet;
import java.util.Random;

/**
 * Class BallDemo - a short demonstration showing animation with the 
 * Canvas class. 
 *
 * @authoy Anthony Tiongson
 * @version 2018.10.22
 * @author Bill Crosbie
 * @version 2015-March-BB
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class BallDemo   
{
    private Canvas myCanvas;
    private int width = 600;
    private int height = 500;
    private int frameWidth = 50;
    private Color color;

    /**
     * Create a BallDemo object. Creates a fresh canvas and makes it visible.
     */
    public BallDemo()
    {
        myCanvas = new Canvas("Ball Demo", width, height);
    }

    /**
     * Simulate two bouncing balls
     */
    public void bounce()
    {
        int ground = 400;   // position of the ground line

        myCanvas.setVisible(true);

        // draw the ground
        myCanvas.drawLine(50, ground, 550, ground);

        // create and show the balls
        BouncingBall ball = new BouncingBall(50, 50, 16, Color.BLUE, ground, myCanvas);
        ball.draw();
        BouncingBall ball2 = new BouncingBall(70, 80, 20, Color.RED, ground, myCanvas);
        ball2.draw();

        // make them bounce
        boolean finished =  false;
        while(!finished) {
            myCanvas.wait(50);           // small delay
            ball.move();
            ball2.move();
            // stop once ball has travelled a certain distance on x axis
            if(ball.getXPosition() >= 550 || ball2.getXPosition() >= 550) {
                finished = true;
            }
        }
    }
    
    /**
     * Draws a rectangle (the box) on screen and a user defined amount of balls to move around it.
     */
    public void boxBounce(int ballCount)
    {
        Random random = new Random();
        int ground = height - frameWidth;       // position of the ground line
        int ceiling = frameWidth;               // position of the ceiling line
        int leftWall = frameWidth;              // position of the left wall
        int rightWall = width - frameWidth;     // position of the right wall

        myCanvas.setVisible(true);

        // boxBounce draws a box inside the canvas
        // draw the ground
        myCanvas.drawLine(leftWall, ground, rightWall, ground);
        // draw the ceiling
        myCanvas.drawLine(leftWall, ceiling, rightWall, ceiling);
        // draw the left wall
        myCanvas.drawLine(leftWall, ceiling, leftWall, ground);
        // draw the right wall
        myCanvas.drawLine(rightWall, ceiling, rightWall, ground);
        
        // create a set of balls based on user defined amount with a random starting location
        // based on canvas size and frame width
        HashSet<BoxBall> ballSet = new HashSet<BoxBall>();
        for (int count= 0; count < ballCount; count++)
        {
            BoxBall ball = new BoxBall((random.nextInt(width - (2*frameWidth) - 49)) + (frameWidth + 50),
                                        (random.nextInt(height - (2*frameWidth) - 49)) + (frameWidth + 50),
                                        ground, ceiling, leftWall, rightWall, myCanvas);
            ballSet.add(ball);
            ball.draw();
        }
        
        // make them bounce
        boolean finished =  false;
        while(!finished) {
            myCanvas.wait(50);           // small delay
            
            for (BoxBall ball : ballSet) {
                
                ball.move();
            }
        }
        
    }
}
