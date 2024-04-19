import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShowImageJFrame extends JFrame{
    private Point mousePt;
    private static final int W = 616;
    private static final int H = 353;
    private Point origin = new Point(W / 2, H / 2);

    public ShowImageJFrame(String imagePath){
      JPanel panel = new JPanel()
      {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image image = imageIcon.getImage();
            if (image != null) {
                int x = origin.x - image.getWidth(null) / 2; 
                int y = origin.y - image.getHeight(null) / 2; 
                g.drawImage(image, x, y, this);
            }
        }
      };

      this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
        new ShowImageJFrame("test.jpg");
    }
}
