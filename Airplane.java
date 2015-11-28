package radardisplay;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import javax.swing.Timer;

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
    private final int turnRate = 3; // degrees/second
    
    private String callsign;
    private String arrival;
    private String departure;
    
    private boolean owned;
    public boolean landing;
    public boolean isDeparture;
    public boolean inConflict;
    
    public final Timer conflictTimer;
    public final int timerSpeed = 250; //ms
    private final int SYMBOL_SIZE = 8;
    public Color currentColor;
    private final Color DATABLOCK_OWNED = new Color(255, 255, 255); //white
    private final Color DATABLOCK_ALERT1 = new Color(255, 0, 0); //red
    private final Color DATABLOCK_ALERT2 = new Color(152, 0, 0); //dark red
    private final Color DATABLOCK_UNOWNED = new Color(0, 139, 0); //green
    
    private final Image SYMBOL_UNOWNED = Toolkit.getDefaultToolkit().getImage("target_symbol_G.png");
    private final Image SYMBOL_OWNED = Toolkit.getDefaultToolkit().getImage("target_symbol_W.png");
    private final Image SYMBOL_ALERT1 = Toolkit.getDefaultToolkit().getImage("target_symbol_R.png");
    private final Image SYMBOL_ALERT2 = Toolkit.getDefaultToolkit().getImage("target_symbol_RDark.png");
    public AffineTransform transform = new AffineTransform();
    
    public Image currentSymbol;
    
    public Airplane(int initialX, int initialY, int initAlt, String callsign, String arrival){
        
        ActionListener conflictAlert = (ActionEvent ae) -> {
            
            if(!this.currentSymbol.equals(this.SYMBOL_ALERT1) || !this.currentColor.equals(this.DATABLOCK_ALERT1)){
                this.currentSymbol = this.SYMBOL_ALERT2;
                this.currentColor = this.DATABLOCK_ALERT2;
            }
            else if(this.currentSymbol.equals(this.SYMBOL_ALERT2) || this.currentColor.equals(this.DATABLOCK_ALERT2)){
                this.currentSymbol = this.SYMBOL_ALERT1;
                this.currentColor = this.DATABLOCK_ALERT1;
            }
            
        };
        this.conflictTimer = new Timer(this.timerSpeed, conflictAlert);
        this.conflictTimer.setInitialDelay(0);
        
        this.currentColor = DATABLOCK_UNOWNED;
        this.currentSymbol = SYMBOL_UNOWNED;
        
        this.x = initialX;
        this.y = initialY;
        this.altitude = 5000;
        this.callsign = callsign;
        this.arrival = arrival;
        
    }
    
    public void slew(){
        
        if(this.owned)
            this.termTrack();
        
        else
            this.initTrack();
        
    }
    
    public void initTrack(){
        this.owned = true;
        this.currentColor = this.DATABLOCK_OWNED;
        this.currentSymbol = this.SYMBOL_OWNED;
    }
    
    public void termTrack(){
        this.owned = false;
        this.currentColor = this.DATABLOCK_UNOWNED;
        this.currentSymbol = this.SYMBOL_UNOWNED;
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
        
        //conflict alerts
        if(this.inConflict){
            this.conflictTimer.start();
        }
        else{
            this.conflictTimer.stop();
            
            if(this.owned){
                this.currentColor = this.DATABLOCK_OWNED;
                this.currentSymbol = this.SYMBOL_OWNED;
            }
            else{
                this.currentColor = this.DATABLOCK_UNOWNED;
                this.currentSymbol = this.SYMBOL_UNOWNED;
            }
        }
        
        //heading
        if(this.heading != this.assignedHeading)
            this.turn();
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
    
    public Ellipse2D getJRing(int diameter){
        return new Ellipse2D.Double(this.x, this.y, (10*diameter)*this.SYMBOL_SIZE, (10*diameter)*this.SYMBOL_SIZE);
    }
    
    public String tagLine1(){
        
        String datatag = this.callsign+" "+this.arrival;
        if(this.owned)
            if(this.isDeparture)
                datatag+= " D";
            else
                datatag+= " A";
        
        return datatag;
    }
    public String tagLine2(){
        
        int alt = (int)(this.altitude/100);
        
        return "\n"+String.format("%03d", (int)this.altitude/100)+" "+(int)this.speed+" "+(int)this.heading;
    }
    
    public void paintComponent(Graphics2D g){
        
        AffineTransform old = g.getTransform();

        g.drawImage(this.currentSymbol, (int)this.x, (int)this.y, 20, 20, null);

        g.setColor(this.currentColor);
        g.drawString(tagLine1(), (int)this.x+35, (int)this.y+10);
        g.drawString(tagLine2(), (int)this.x+35, (int)this.y+25);
        
        //g.setColor(this.currentColor);
        //g.fillOval( (int) this.x-this.SYMBOL_SIZE, (int) this.y-this.SYMBOL_SIZE, 2*this.SYMBOL_SIZE, 2*this.SYMBOL_SIZE);

    }
    
}
