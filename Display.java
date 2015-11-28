package radardisplay;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Chris
 */
public class Display extends javax.swing.JPanel{
    
    private final Color BOUNDARY = new Color(140, 140, 140);
    private final Color VIDEO_MAP = new Color(79, 79, 79); //gray
    private final Color TEXT = new Color(0, 139, 0); //green
    public double SCALE; 
    private final ArrayList<Airplane> aircraftList = new ArrayList<>();
    private final Timer sweepTimer;
    public MouseListener slewListener;

    public Display(){
        
        this.setBackground(Color.BLACK);

        //initial aircraft on display
        this.aircraftList.add(new Airplane(0, 0, 5000, "FLG3631", "MSP"));
        this.aircraftList.add(new Airplane(0, 300, 5000, "FDX1544", "MSP"));
        
        ActionListener radarSweep = (ActionEvent ae) -> {
            sweep();
        };        
        this.sweepTimer = new Timer(3000, radarSweep);
        sweepTimer.start();
                
    }
    
    private void sweep() {
        
        for(Airplane a: aircraftList){
            a.speed = 200.0;
            a.heading = 360;
            
            a.assignAltitude(50000);
            a.assignSpeed(200);
            
            double distance = (double) (a.speed/3600)*3.0;
            double pxTravelled = (double) distance*this.SCALE;
            
            double heading = Math.toRadians(a.heading) - 90;
            
            double newX = pxTravelled*Math.cos(heading);
            double newY = pxTravelled*Math.sin(heading);
            
            a.updateState(a.x+newX, a.y+newY);
            
            for(int i = this.aircraftList.size(); i==0; i--){
                Airplane b = this.aircraftList.get(i);
                //check for conflict alerts
                if(a.getJRing(6).getBounds().intersects(b.getJRing(6).getBounds()) && Math.abs(a.altitude - b.altitude) < 1000 ){
                    a.inConflict = b.inConflict = true;
                }
                else{
                    a.inConflict = b.inConflict = false;
                }
                //check for collisions
                if( a.getTargetBounds().intersects(b.getTargetBounds()) && a.altitude == b.altitude){
                    this.aircraftList.remove(b);
                    this.aircraftList.remove(a);
                }
                
            }
        }
        
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(Color.BLACK);
        g2d.translate(this.getWidth()/2, this.getHeight()/2);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Stroke defaultStroke = g2d.getStroke();
        Stroke thickStroke = new BasicStroke(2);
        Stroke dashStroke = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, new float[] {10f, 10f}, 10f);
        
        //airspace boundary
        g2d.setColor(BOUNDARY);
        g2d.setStroke(thickStroke);
        g2d.drawOval(0-this.getHeight()/2, 0-this.getHeight()/2, this.getHeight(), this.getHeight());

        //runway
        g2d.drawLine(0, 0, 20, 0);
        
        //range rings
        g2d.setColor(VIDEO_MAP);
        g2d.setStroke(defaultStroke);
        int posFactor = 5;
        int dimFactor = 10;
        for(int i=1; i<=9; i++){
            g2d.drawOval((int) (0-this.getHeight()/2+(posFactor*this.SCALE)), (int) (0-this.getHeight()/2+(posFactor*this.SCALE)), (int) (this.getHeight()-(dimFactor*this.SCALE)), (int) (this.getHeight()-(dimFactor*this.SCALE)));
            posFactor+=5;
            dimFactor+=10;
        }
        
        //runway extended centerline
        g2d.setStroke(dashStroke);
        g2d.drawLine(0, 0, this.getWidth()/-4, 0);
        g2d.setStroke(defaultStroke);
        
        //create fixes
        ArrayList<Fix> fixes = new ArrayList<>();
        fixes.add(new Fix(0, (double)this.getHeight()/-2.25, "COLIN", true)); //north departure gate
        fixes.add(new Fix(0, (double)this.getHeight()/2.25, "NOBLE", true)); //south departure gate
        fixes.add(new Fix((double)this.getHeight()/1.75, -200, "POLAR", false)); //northeast arrival gate
        fixes.add(new Fix((double)this.getHeight()/-1.75, 200, "SHAWN", false)); //southwest arrival gate
        for(Fix f : fixes){
            f.paintComponent(g2d);
        }
        
        //repaint target symbols
        for(Airplane a: aircraftList){
            a.paintComponent(g2d);
            
        }

    }

    public static void main(String[] args) throws InterruptedException{

                JFrame frame = new JFrame("Radar Display");
                Display display = new Display();        

                frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                frame.setUndecorated(true);
                frame.setExtendedState(MAXIMIZED_BOTH);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                frame.setBackground(Color.BLACK);

                frame.add(display);
                frame.setVisible(true);

                AircraftWindow aircraftWindow = new AircraftWindow();
                aircraftWindow.setVisible(true);
                aircraftWindow.setLocation(display.getWidth()-aircraftWindow.getWidth(), frame.getHeight()-aircraftWindow.getHeight());
                
                display.SCALE = (double)display.getHeight() / 100.0;
                display.addMouseListener(new MouseAdapter(){
                    
                    @Override
                    public void mousePressed(MouseEvent me){
                        
                        //System.out.println( me.getX()-(display.getWidth()/2)+", "+(me.getY()-(display.getHeight()/2)) );
                        for(Airplane a : display.aircraftList)
                            if(a.getTargetBounds().contains( me.getX()-(display.getWidth()/2), me.getY()-(display.getHeight()/2) ))
                                a.slew();
                        
                            }
                    
                });
    }
    
}
