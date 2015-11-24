/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radardisplay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;


/**
 *
 * @author Chris
 */
public class Airplane {
    
    public double x;
    public double y;
    public double speed;
    public double altitude;
    public double heading;
    
    private double assignedSpeed;
    private double assignedAltitude;
    private double assignedHeading;
    
    private final int feetPerMinute = 1000;
    private final int speedChange = 30; // 30kts/ 3sec
    
    private String callsign;
    private String arrival;
    private String departure;
    
    private boolean owned;
    private boolean landing;

    private final int SYMBOL_SIZE = 8;
    public Color currentColor;
    private final Color DATABLOCK_OWNED = new Color(255, 255, 255); //white
    private final Color DATABLOCK_ALERT = new Color(255, 0, 0); //red
    private final Color DATABLOCK_UNOWNED = new Color(0, 139, 0); //green
    
    public Image SYMBOL_UNOWNED = Toolkit.getDefaultToolkit().getImage("target_symbol.gif");
    public Image SYMBOL_OWNED = Toolkit.getDefaultToolkit().getImage("");
    public Image SYMBOL_ALERT1 = Toolkit.getDefaultToolkit().getImage("");
    public Image SYMBOL_ALERT2 = Toolkit.getDefaultToolkit().getImage("");
    
    public Airplane(int initialX, int initialY){
        this.currentColor = DATABLOCK_UNOWNED;
        this.x = initialX;
        this.y = initialY;

    }
    
    public void assignSpeed(int newSpeed){
        this.assignedSpeed = newSpeed;
    }
    
    public void assignHeading(int newHeading){
        this.assignedHeading = newHeading;
    }
    
    public void assignAltitude(int newAltitude){
        this.assignedAltitude = newAltitude;
    }
    
    public void ascend(){
        
        if(this.altitude < this.assignedAltitude)
            this.altitude += this.feetPerMinute/180;
        
    }
    
    public void descend(){
        
        if(this.altitude > this.assignedAltitude)
            this.altitude -= this.feetPerMinute/180;
        
    }
    
    public void reduceSpeed(){
        
        if(this.speed > this.assignedSpeed)
            this.speed -= this.speedChange;
    }
    
    public void increaseSpeed(){
        
        if(this.speed < this.assignedSpeed)
            this.speed += this.speedChange;
    }
    
    public void turn(){
        
    }
    
    public void updateState(double newX, double newY){
        
        //heading 
        this.x = newX;
        this.y = newY;
        
        //altitude
        if(this.altitude < this.assignedAltitude)
            this.ascend();
        else if(this.altitude > this.assignedAltitude)
            this.descend();
        
        //speed
        if(this.speed < this.assignedSpeed)
            this.increaseSpeed();
        else if(this.speed > this.assignedSpeed)
            this.reduceSpeed();
        
    }
    
    public void takeoff(int initialHeading){
        
        this.assignedAltitude = 5000;
        this.assignedSpeed = 250;
        this.assignedHeading = initialHeading;
        
    }
    
    public void land(){
        
    }
    
    public Rectangle getTargetBounds(){

        return new Rectangle( (int) this.x, (int) this.y, this.SYMBOL_SIZE, this.SYMBOL_SIZE);
    }
    
    public Ellipse2D getJRing(){
        return new Ellipse2D.Double(this.x, this.y, 10*this.SYMBOL_SIZE, 10*this.SYMBOL_SIZE);
    }
    
    public void paint(Graphics2D g){
        
        g.drawImage(SYMBOL_UNOWNED, (int)this.x, (int)this.y, null);
        
        //g.setColor(this.currentColor);
        //g.fillOval( (int) this.x-this.SYMBOL_SIZE, (int) this.y-this.SYMBOL_SIZE, 2*this.SYMBOL_SIZE, 2*this.SYMBOL_SIZE);

    }
    
}
