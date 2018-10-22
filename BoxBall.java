import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

/**
 * Class BoxBall - Graphical balls that bounces within a frame of an area. The balls
 * have the ability to move. The intial position and speed of the balls are random.
 *
 * 
 * @author Anthony Tiongson
 * @version 2018.10.22
 * 
 * @author Michael Kölling (mik)
 * @author David J. Barnes
 * @author Bruce Quig
 *
 * @version 2011.07.31
 */

public class BoxBall
{
    private Random random = new Random();
    private Ellipse2D.Double circle;
    private Color color;
    private int diameter;
    private int xPosition;
    private int yPosition;
    private final int groundPosition;      // y position of ground
    private final int ceilingPosition;     // y position of ceiling
    private final int leftWallPosition;    // x postion of left wall
    private final int rightWallPosition;   // x position of right wall
    private Canvas canvas;
    private int xSpeed;                    // horizontal speed
    private int ySpeed;                    // vertical speed
    
    /**
     * Constructor for objects of class BoxBall
     *
     * @param xPosition  the horizontal coordinate of the ball
     * @param yPosition  the vertical coordinate of the ball
     * @param groundPosition  location of ground y-axis
     * @param ceilingPosition  location of ceiling y-axis
     * @param leftWallPosition  location of left wall x-axis
     * @param rightWallPosition  location of right wall x-axis
     * @param canvas  the canvas to draw this ball on
     */
    public BoxBall (int xPosition, int yPosition, int groundPosition,int ceilingPosition, int leftWallPosition,
                        int rightWallPosition, Canvas canvas)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.groundPosition = groundPosition;
        this.ceilingPosition = ceilingPosition;
        this.leftWallPosition = leftWallPosition;
        this.rightWallPosition = rightWallPosition;
        this.canvas = canvas;
        // extra credit: Each ball is given a random size from 10-25 pixels
        diameter = (random.nextInt(16) + 10);
        // fulfills: give each ball a unique Random color using rgb values, modified so that are not too close
        // to white (and are therefore, invisible)
        color = new Color(random.nextInt(226), random.nextInt(226), random.nextInt(226));
        // fulfills: BoxBall motion is based on random distance moved in x and y for each 'tick' of the
        // simulation. change in distance is +/- 7 on x and y axis, and cannot be either directly vertical or
        // horizontal motion (can't have a zero value)
        xSpeed = (random.nextInt(7) + 1) * (random.nextBoolean() ? -1 : 1);
        ySpeed = (random.nextInt(7) + 1) * (random.nextBoolean() ? -1 : 1);
    }

    /**
     * Draw this ball at its current position onto the canvas.
     **/
    public void draw()
    {
        canvas.setForegroundColor(color);
        canvas.fillCircle(xPosition, yPosition, diameter);
    }

    /**
     * Erase this ball at its current position.
     **/
    public void erase()
    {
        canvas.eraseCircle(xPosition, yPosition, diameter);
    }    

    /**
     * Move this ball according to its position and speed and redraw.
     **/
    public void move()
    {
        // remove from canvas at the current position
        erase();
            
        // compute new position
        yPosition += ySpeed;
        xPosition += xSpeed;

        // check if it has hit the ground
        if(yPosition >= (groundPosition - diameter)) {
            yPosition = (int)(groundPosition - diameter);
            ySpeed = -ySpeed; 
        }
        
        // check if it has hit the ceiling
        if(yPosition <= ceilingPosition) {
            yPosition = (int)(ceilingPosition);
            ySpeed = -ySpeed; 
        }
        
        // check if it has hit the left wall
        if(xPosition <= leftWallPosition) {
            if (diameter % 2 == 0) {
                xPosition = (int)(leftWallPosition + 1); // +1 prevents chipping
                xSpeed = -xSpeed;
            }
            else {
                xPosition = (int)(leftWallPosition);
                xSpeed = -xSpeed; 
            }
        }
        
        // check if it has hit the right wall
        if(xPosition >= (rightWallPosition - diameter)) {
            xPosition = (int)(rightWallPosition - diameter);
            xSpeed = -xSpeed; 
        }
        
        // draw again at new position
        draw();
    }

    /**
     * return the horizontal position of this ball
     */
    public int getXPosition()
    {
        return xPosition;
    }

    /**
     * return the vertical position of this ball
     */
    public int getYPosition()
    {
        return yPosition;
    }
    
    public int getRadius()
    {
        return (diameter/2);
    }
    
    public int getXCenter()
    {
        return (xPosition + getRadius());
    }
    
    public int getYCenter()
    {
        return (yPosition + getRadius());
    }
}
