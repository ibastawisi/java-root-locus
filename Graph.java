import java.awt.*;
import javax.swing.*;

import org.ejml.data.Complex_F64;

import java.awt.geom.*;

public class Graph extends JPanel {
    RootLocus rl;
    int scale = 50;
    int k = 0;

    public Graph(RootLocus rl) {
        this.rl = rl;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        int xFrac = width % scale;
        int yFrac = height % scale;

        // draw x-axis and y-axis
        drawAxis(g2d, width, height);
        drawAxisTiks(g2d, width, height, xFrac, yFrac);

        // draw root locus
        Complex_F64[][] roots = rl.getRoots();
        // draw poles as blue X
        Complex_F64[] poles = roots[k];
        drawRoots(g2d, width, height, poles);
        // draw locus as red dots
        drawLocus(g2d, width, height, roots);
        // animate the root locus
        k = (k + 10) % roots.length;
        repaint();

    }

    private void drawAxis(Graphics2D g2d, int width, int height) {
        g2d.draw(new Line2D.Double(width / 2, 0, width / 2, height));
        g2d.draw(new Line2D.Double(0, height / 2, width, height / 2));
    }

    private void drawAxisTiks(Graphics2D g2d, int width, int height, int xFrac, int yFrac) {
        g2d.setPaint(Color.BLACK);
        // draw tiks on x-axis
        for (int i = 0; i < width; i += scale) {
            g2d.draw(new Line2D.Double(i + xFrac / 2, height / 2 - 5, i + xFrac / 2, height / 2 + 5));
            g2d.drawString(String.valueOf((i - width / 2 + xFrac / 2) / scale), i, height / 2 + 20);
        }
        // draw tiks on y-axis
        for (int i = 0; i < height; i += scale) {
            g2d.draw(new Line2D.Double(width / 2 - 5, i + yFrac / 2, width / 2 + 5, i + yFrac / 2));
            g2d.drawString(String.valueOf((height / 2 - i - yFrac / 2) / scale), width / 2 + 10, i);
        }
    }

    private void drawRoots(Graphics2D g2d, int width, int height, Complex_F64[] poles) {
        g2d.setPaint(Color.BLUE);
        for (int i = 0; i < poles.length; i++) {
            g2d.draw(new Line2D.Double((width / 2) + (poles[i].real * scale) - 5,
                    (height / 2) - (poles[i].imaginary * scale) - 5,
                    (width / 2) + (poles[i].real * scale) + 5,
                    (height / 2) - (poles[i].imaginary * scale) + 5));
            g2d.draw(new Line2D.Double((width / 2) + (poles[i].real * scale) - 5,
                    (height / 2) - (poles[i].imaginary * scale) + 5,
                    (width / 2) + (poles[i].real * scale) + 5,
                    (height / 2) - (poles[i].imaginary * scale) - 5));

        }
    }

    private void drawLocus(Graphics2D g2d, int width, int height, Complex_F64[][] roots) {
        g2d.setPaint(Color.RED);
        for (int i = 0; i < roots.length; i++) {
            for (int j = 0; j < roots[i].length; j++) {
                if (roots[i][j] != null) {
                    g2d.fill(new Ellipse2D.Double((width / 2) + (roots[i][j].real * scale) - 1,
                            (height / 2) - (roots[i][j].imaginary * scale) - 1, 2, 2));
                }
            }
        }
    }

}