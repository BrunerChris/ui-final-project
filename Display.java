/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radardisplay;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author Chris
 */
public class Display extends javax.swing.JPanel {
    
    private final Color BOUNDARY = new Color(140, 140, 140);
    private final Color VIDEO_MAP = new Color(79, 79, 79); //gray
    private final Color TEXT = new Color(0, 139, 0); //green
    public double SCALE; 
    private ArrayList<Airplane> aircraftList = new ArrayList<>();

    public Display(){
        
        this.setBackground(Color.BLACK);
        
        this.aircraftList.add(new Airplane(0, 0));
        this.aircraftList.add(new Airplane(0, 300));
        
    }
    
    public void sweep() throws InterruptedException{

        for(Airplane a: aircraftList){
            a.speed = 200.0;

            double distance = (double) (a.speed/3600)*3.0;
            double pxTravelled = (double) distance*this.SCALE;
            
            a.updateState(a.x+pxTravelled, a.y);
        }
        
        repaint();
        Thread.sleep(3000); //3 seconds
    }
    
    @Override
    public void paint(Graphics g){
        
        super.paint(g);
        
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
        
        //runway extended centerline
        g2d.setColor(VIDEO_MAP);
        g2d.setStroke(dashStroke);
        g2d.drawLine(0, 0, this.getWidth()/-4, 0);
        g2d.setStroke(defaultStroke);

        //range rings
        int posFactor = 5;
        int dimFactor = 10;
        for(int i=1; i<=9; i++){
            g2d.drawOval((int) (0-this.getHeight()/2+(posFactor*this.SCALE)), (int) (0-this.getHeight()/2+(posFactor*this.SCALE)), (int) (this.getHeight()-(dimFactor*this.SCALE)), (int) (this.getHeight()-(dimFactor*this.SCALE)));
            posFactor+=5;
            dimFactor+=10;
        }
        
        //create fixes
        ArrayList<Fix> fixes = new ArrayList<>();
        fixes.add(new Fix(0, (double)this.getHeight()/-2.25, "COLIN", true)); //north departure gate
        fixes.add(new Fix(0, (double)this.getHeight()/2.25, "NOBLE", true)); //south departure gate
        fixes.add(new Fix((double)this.getHeight()/1.75, -200, "POLAR", false)); //northeast arrival gate
        fixes.add(new Fix((double)this.getHeight()/-1.75, 200, "SHAWN", false)); //southwest arrival gate
        for(Fix f : fixes){
            f.paint(g2d);
        }
        
        //repaint target symbols
        for(Airplane a: aircraftList){
            a.paint(g2d);
            
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
        

        
        while(true){
            
            display.sweep();
        }
        
    }
    
}
