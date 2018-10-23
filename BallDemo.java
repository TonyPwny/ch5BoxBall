import java.awt.Color;
import java.util.Random;

/**
 * Class BallDemo - a short demonstration showing animation with the 
 * Canvas class. 
 *
 * @authoy Anthony Tiongson
 * @version 2018.10.22
 * 
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
     * 
     * @param ballCount how many balls to render
     */
    public void boxBounce(int ballCount)
    {
        Random random = new Random();
        int ground = height - frameWidth;       // position of the ground line
        int ceiling = frameWidth;               // position of the ceiling line
        int leftWall = frameWidth;              // position of the left wall
        int rightWall = width - frameWidth;     // position of the right wall

        myCanvas.setVisible(true);

        // fulfills: boxBounce draws a box inside the canvas
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
        BoxBall ball[] = new BoxBall[ballCount];
        for (int i= 0; i < ballCount; i++)
        {
            ball[i] = new BoxBall((random.nextInt(width - (2*frameWidth) - 52)) + (frameWidth + 26),
                                        (random.nextInt(height - (2*frameWidth) - 52)) + (frameWidth + 26),
                                        ground, ceiling, leftWall, rightWall, myCanvas);
            ball[i].draw();
        }
        
        // make them bounce
        boolean finished =  false;
        while(!finished) {
            myCanvas.wait(50);           // small delay
            
            boolean noCollision;
            for (int i=0; i < ballCount; i++) {
                
                noCollision = true;
                for (int j = i + 1; (j < ballCount) && noCollision; j++) {
                    
                    // got algorithm for collision detection from:
                    // https://gamedevelopment.tutsplus.com/tutorials/when-worlds-collide-simulating-circle-circle-collisions--gamedev-769
                    if (ball[i].getXCenter() + ball[i].getRadius() + ball[j].getRadius() > ball[j].getXCenter() 
                        && ball[i].getXCenter() < ball[j].getXCenter() + ball[i].getRadius() + ball[j].getRadius()
                        && ball[i].getYCenter() + ball[i].getRadius() + ball[j].getRadius() > ball[j].getYCenter() 
                        && ball[i].getYCenter() < ball[j].getYCenter() + ball[i].getRadius() + ball[j].getRadius()
                        && noCollision)
                    {
                        double distance = Math.sqrt(((ball[i].getXCenter() - ball[j].getXCenter())
                                    * (ball[i].getXCenter() - ball[j].getXCenter()))
                                    + ((ball[i].getYCenter() - ball[j].getYCenter())
                                    * (ball[i].getYCenter() - ball[j].getYCenter())));
                        if (distance < ball[i].getRadius() + ball[j].getRadius())
                        {
                            //balls have collided
                            
                            if (ball[i].getXCenter() < ball[j].getXCenter()) {
                                ball[i].collideRight();
                                ball[j].collideLeft();
                            }
                            else {
                                ball[i].collideLeft();
                                ball[j].collideRight();
                            }
                            
                            if (ball[i].getYCenter() < ball[j].getYCenter()) {
                                ball[i].collideBottom();
                                ball[j].collideTop();
                            }
                            else {
                                ball[i].collideTop();
                                ball[j].collideBottom();
                            }
                            noCollision = false;
                        }
                    }
                }
                
                ball[i].move();
            }
        }
    }
}
