/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radardisplay;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author Chris
 */
public class Fix{
    
    private final double x,y;
    private final int SIZE;
    private final boolean departureFix;
    public final String name;
    
    public Point2D pt;
    public GeneralPath symbol;

    public Fix(double x, double y, String name, boolean departure){
        
        this.x = x;
        this.y = y;
        this.name = name;
        this.departureFix = departure;
        
        this.SIZE = 7;
        
        this.pt = new Point2D.Double(this.x, this.y);
        double ptX = pt.getX();
        double ptY = pt.getY();
        double xPoints[] = {ptX-SIZE, ptX+SIZE, ptX+SIZE, ptX-SIZE};
        double yPoints[] = {ptY-SIZE, ptY+SIZE, ptY-SIZE, ptY+SIZE};
        this.symbol = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
        
        symbol.moveTo(xPoints[0], yPoints[0]);
        for(int i=1; i<xPoints.length; i++){
            
            if(i==2){
                symbol.moveTo(xPoints[i], yPoints[i]);
            }
            symbol.lineTo(xPoints[i], yPoints[i]);
            
        }
        
    }
    
    public double getX(){
        return this.x;
    }
    
    public double getY(){
        return this.y;
    }
    
    public boolean isDepartureFix(){
        return this.departureFix;
    }
    
    public void spawnAirplane(Airplane a, ArrayList<Airplane> list){
        
    }
    
    public void paint(Graphics2D g){
        g.draw(symbol);
    }
    
}
