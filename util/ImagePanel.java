package util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
    
    private Image img;
    
    public ImagePanel(Image img) {
        this(img, img.getWidth(null), img.getHeight(null));
    }
    
    public ImagePanel(Image img, int w, int h) {
        this.img = img;
        Dimension size = new Dimension(w, h);
        
        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}
