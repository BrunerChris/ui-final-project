/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radardisplay;

import java.awt.Color;
import javax.swing.JWindow;
import javax.swing.border.LineBorder;

/**
 *
 * @author Chris
 */
public class AircraftWindow extends JWindow{
    
    private final javax.swing.JLabel altitudeLabel;
    private final javax.swing.JSlider altitudeSlider;
    private final javax.swing.JLabel callsign;
    private final javax.swing.Box.Filler filler1;
    private final javax.swing.Box.Filler filler2;
    private final javax.swing.Box.Filler filler3;
    private final javax.swing.Box.Filler filler4;
    private final javax.swing.JLabel headingLabel;
    private final javax.swing.JSpinner headingSpinner;
    private final javax.swing.JSeparator jSeparator1;
    private final javax.swing.JButton landButton;
    private final javax.swing.JLabel speedLabel;
    private final javax.swing.JSlider speedSlider;
    
    public AircraftWindow(){
        
        this.getRootPane().setBorder(new LineBorder(new Color(50,50,130)));
        
        setAlwaysOnTop(true);
        java.awt.GridBagConstraints gridBagConstraints;
        
        callsign = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        altitudeSlider = new javax.swing.JSlider();
        altitudeLabel = new javax.swing.JLabel();
        speedLabel = new javax.swing.JLabel();
        speedSlider = new javax.swing.JSlider();
        headingLabel = new javax.swing.JLabel();
        headingSpinner = new javax.swing.JSpinner();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(450, 10), new java.awt.Dimension(32767, 32767));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(450, 10), new java.awt.Dimension(32767, 32767));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(450, 10), new java.awt.Dimension(32767, 32767));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(450, 10), new java.awt.Dimension(32767, 32767));
        landButton = new javax.swing.JButton();

        getContentPane().setBackground(new java.awt.Color(0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        setForeground(new java.awt.Color(0, 0, 0));
        setMaximumSize(new java.awt.Dimension(400, 160));
        setMinimumSize(null);
        setName("aircraftDialog"); // NOI18N
        setPreferredSize(null);
        setType(java.awt.Window.Type.UTILITY);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        callsign.setBackground(new java.awt.Color(0, 0, 0));
        callsign.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        callsign.setForeground(new java.awt.Color(0, 139, 0));
        callsign.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        callsign.setText("CALLSIGN");
        callsign.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 299;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 10);
        getContentPane().add(callsign, gridBagConstraints);

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator1.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 379;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 10);
        getContentPane().add(jSeparator1, gridBagConstraints);

        altitudeSlider.setBackground(new java.awt.Color(0, 0, 0));
        altitudeSlider.setForeground(new java.awt.Color(0, 139, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        getContentPane().add(altitudeSlider, gridBagConstraints);

        altitudeLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        altitudeLabel.setForeground(new java.awt.Color(0, 139, 0));
        altitudeLabel.setText("Altitude");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        getContentPane().add(altitudeLabel, gridBagConstraints);

        speedLabel.setBackground(new java.awt.Color(0, 0, 0));
        speedLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        speedLabel.setForeground(new java.awt.Color(0, 139, 0));
        speedLabel.setText("Speed");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        getContentPane().add(speedLabel, gridBagConstraints);

        speedSlider.setBackground(new java.awt.Color(0, 0, 0));
        speedSlider.setForeground(new java.awt.Color(0, 139, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        getContentPane().add(speedSlider, gridBagConstraints);

        headingLabel.setBackground(new java.awt.Color(0, 0, 0));
        headingLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        headingLabel.setForeground(new java.awt.Color(0, 139, 0));
        headingLabel.setText("Heading");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        getContentPane().add(headingLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        getContentPane().add(headingSpinner, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(filler1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        getContentPane().add(filler2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        getContentPane().add(filler3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        getContentPane().add(filler4, gridBagConstraints);

        landButton.setBackground(new java.awt.Color(40, 40, 110));
        landButton.setForeground(new java.awt.Color(255, 255, 255));
        landButton.setText("LAND");
        landButton.setFocusPainted(false);
        landButton.setFocusable(false);
        landButton.setMinimumSize(new java.awt.Dimension(100, 30));
        landButton.setPreferredSize(new java.awt.Dimension(100, 30));
        landButton.setRequestFocusEnabled(false);
        landButton.setRolloverEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        getContentPane().add(landButton, gridBagConstraints);
        
        pack();

    }

    
}
