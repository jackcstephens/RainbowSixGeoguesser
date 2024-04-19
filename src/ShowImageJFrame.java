import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShowImageJFrame extends JFrame{
    private Point mousePt;
    private static int W = 2644;
    private static int H = 1161;
    private Point origin = new Point(W / 2, H / 2);

    public ShowImageJFrame(String imagePath){

      /*
       * Panel and image generation
       *
       * Create a panel and then paint the image, given by main, on the panel based on the current origin
       */
      JPanel panel = new JPanel() 
      {
        @Override
        protected void paintComponent(Graphics g) {
            // Paint the panel with the image given in main
            super.paintComponent(g);
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image image = imageIcon.getImage();
            if (image != null) {
                // Calculate image location based on origin, allows mouse dragging to work properly
                int x = origin.x - image.getWidth(null) / 2; 
                int y = origin.y - image.getHeight(null) / 2; 
                g.drawImage(image, x, y, W, H, this);
            }
        }
      };

      /*
       * Mouse Panning Functionality
       * 
       * Detect cursor presses and drags, update origin based on movements and repaint the image on the panel based on new origin
       */

       Toolkit toolkit = Toolkit.getDefaultToolkit();
       Image image = toolkit.getImage("lib/cursors/cursor.png");
       Cursor c = toolkit.createCustomCursor(image , new Point(this.getX(), 
                  this.getY()), "img");
       this.setCursor (c);
      this.addMouseListener(new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            mousePt = e.getPoint();
            repaint();
          }
      });
      this.addMouseMotionListener(new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
          int dx = e.getX() - mousePt.x;
          int dy = e.getY() - mousePt.y;
          origin.setLocation(origin.x + dx, origin.y + dy);
          mousePt = e.getPoint();
          repaint();
        }
      });

      this.addMouseWheelListener(new MouseWheelListener() {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
          double amount = Math.pow(1.1, e.getScrollAmount());
          if(e.getWheelRotation() < 0) {
            // zoom in
            double temp = (double)W * amount;
            W = (int)temp;
            temp = (double)H * amount;
            H = (int)temp;
            repaint();
          } else {
            // zoom out
            double temp = (double)W / amount;
            W = (int)temp;
            temp = (double)H / amount;
            H = (int)temp;
            repaint();
          }
        }
      });

      this.getContentPane().add(panel);

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      pack();
      setVisible(true);
  }

  @Override
    public Dimension getPreferredSize() {
      return new Dimension(W, H);
    }
    public static void main(String[] args){
        new ShowImageJFrame("test2.jpg");
    }
}
