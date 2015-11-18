/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radardisplay;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author Chris
 */
public class Airplane {
    
    public int x;
    public int y;
    public int speed;
    public int altitude;
    public int heading;
    
    private int assignedSpeed;
    private int assignedAltitude;
    private int assignedHeading;
    private int feetPerMinute = 1000;
    public Color currentColor;
    
    private String callsign;
    private String arrival;
    private String departure;
    
    private boolean owned;
    private boolean landing;
    
    private final Display display;
    private final int SYMBOL_SIZE = 10;
    private final Color DATABLOCK_OWNED = new Color(255, 255, 255); //white
    private final Color DATABLOCK_ALERT = new Color(255, 0, 0); //red
    private final Color DATABLOCK_UNOWNED = new Color(0, 139, 0); //green
    
    public Airplane(Display d, int initialX, int initialY){
        this.display = d;
        this.currentColor = DATABLOCK_UNOWNED;
        this.x = initialX;
        this.y = initialY;
    }
    
    public void assignSpeed(int newSpeed){
        
    }
    
    public void assignHeading(int newHeading){
        
    }
    
    public void assignAltitude(int newAltitude){
        
    }
    
    public void ascend(){
        
    }
    
    public void descend(){
        
    }
    
    public void reduceSpeed(){
        
    }
    
    public void increaseSpeed(){
        
    }
    
    public void turn(){
        
    }
    
    public void speed(){
        
        
        
    }
    
    public void changePosition(int newX, int newY){
        
        this.x = newX;
        this.y = newY;
        
    }
    
    public void takeoff(){
        
    }
    
    public void land(){
        
    }
    
    public Rectangle getTargetBounds(){
        return new Rectangle(this.x, this.y, this.SYMBOL_SIZE, this.SYMBOL_SIZE);
    }
    
    public Ellipse2D getJRing(){
        return new Ellipse2D.Double(this.x, this.y, 10*this.SYMBOL_SIZE, 10*this.SYMBOL_SIZE);
    }
    
    public void paint(Graphics2D g){
        
        g.setColor(DATABLOCK_UNOWNED);
        g.fillOval(this.x-this.SYMBOL_SIZE, this.y-this.SYMBOL_SIZE, 2*this.SYMBOL_SIZE, 2*this.SYMBOL_SIZE);

    }
    
}
