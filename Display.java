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
        
    private final Color VIDEO_MAP = new Color(96, 96, 96); //gray
    private final Color TEXT = new Color(0, 139, 0); //green
    
    //private Timer sweepTimer;
    public final int SCALE = this.getHeight()/100; // px/nm
    private ArrayList<Airplane> aircraftList = new ArrayList<>();
    
    public Display(){
        this.setBackground(Color.BLACK);
        
        this.aircraftList.add(new Airplane(this, 0, 0));
        this.aircraftList.add(new Airplane(this, 0, 300));
        
    }
    
    public void sweep() throws InterruptedException{
        
        for(Airplane a: aircraftList){
            //int newX = a.x+(this.getHeight()/50);
            //a.changePosition(newX, a.y);
            a.speed = 200;
            
            int speed = a.speed;
            int distance = (speed/3600)*3;
            int pxTravelled = distance*SCALE;
            
            a.changePosition(a.x+pxTravelled, a.y);
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
        g2d.setColor(VIDEO_MAP);
        
        Stroke defaultStroke = g2d.getStroke();
        Stroke dashStroke = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, new float[] {20f, 20f}, 0.0f);
        
        //airspace boundary
        g.drawOval(0-this.getHeight()/2, 0-this.getHeight()/2, this.getHeight(), this.getHeight());
        
        //runway extended centerline
        g2d.setStroke(dashStroke);
        g2d.drawLine(0, 0, this.getWidth()/-4, 0);
        g2d.setStroke(defaultStroke);
        
        
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
        
        while(true){
            
            display.sweep();
        }
        
    }
    
}