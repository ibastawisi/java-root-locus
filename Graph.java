import java.awt.*;
import javax.swing.*;
import org.ejml.data.Complex_F64;

public class Graph extends JPanel {
    RootLocus[] rlArray;
    int scale;
    int index = 0;
    int slide = 0;
    RootLocus rl;

    public Graph(RootLocus[] rlArray) {
        this.rlArray = rlArray;
        this.rl = rlArray[0];
        updateScale();
    }

    private void updateScale() {
        double step = rl.getStep();
        this.scale = (int) Math.abs(Math.log10(step) * 20);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        // draw x-axis and y-axis
        drawAxis(g, width, height);
        drawAxisTicks(g, width, height);

        Complex_F64[][] roots = rl.getRoots();

        // draw zeros
        drawZeros(g, width, height);
        // draw poles
        Complex_F64[] poles = roots[index];
        drawRoots(g, width, height, poles);
        // draw asymptotes
        drawAsymptotes(g, width, height);
        // draw departure angles
        drawDepartureAngles(g, width, height);
        // draw arrival angles
        drawArrivalAngles(g, width, height);
        // draw locus
        drawLocus(g, width, height, roots);

        // animate the root locus
        index = (int) Math.round(index * 1.1 + 1);
        double step = rl.getStep();
        double k = Math.round(Math.pow(index * step, 3) * 100.0) / 100.0;
        g.drawString("k = " + k, 10, 10);

        if (index >= 5000) {
            index = 0;
            slide = (slide + 1) % rlArray.length;
            rl = rlArray[slide];
            updateScale();
        }
        repaint();

    }

    private void drawAxis(Graphics g, int width, int height) {
        g.drawLine(width / 2, 0, width / 2, height);
        g.drawLine(0, height / 2, width, height / 2);
    }

    private void drawAxisTicks(Graphics g, int width, int height) {
        g.setColor(Color.BLACK);
        // draw ticks on x-axis
        for (int i = width / 2 + scale; i < width; i += scale) {
            g.drawLine(i, height / 2 - 5, i, height / 2 + 5);
            g.drawString(String.valueOf((i - width / 2) / scale), i, height / 2 + 20);
        }
        for (int i = width / 2 - scale; i > 0; i -= scale) {
            g.drawLine(i, height / 2 - 5, i, height / 2 + 5);
            g.drawString(String.valueOf((i - width / 2) / scale), i, height / 2 + 20);
        }
        // draw ticks on y-axis
        for (int i = height / 2 + scale; i < height; i += scale) {
            g.drawLine(width / 2 - 5, i, width / 2 + 5, i);
            g.drawString(String.valueOf((height / 2 - i) / scale), width / 2 + 10, i);
        }
        for (int i = height / 2 - scale; i > 0; i -= scale) {
            g.drawLine(width / 2 - 5, i, width / 2 + 5, i);
            g.drawString(String.valueOf((height / 2 - i) / scale), width / 2 + 10, i);
        }
    }

    private void drawRoots(Graphics g, int width, int height, Complex_F64[] poles) {
        g.setColor(Color.BLUE);
        for (int i = 0; i < poles.length; i++) {
            if (poles[i] == null) {
                continue;
            }
            g.drawLine(
                    (int) ((width / 2) + (poles[i].real * scale) - 5),
                    (int) ((height / 2) - (poles[i].imaginary * scale) - 5),
                    (int) ((width / 2) + (poles[i].real * scale) + 5),
                    (int) ((height / 2) - (poles[i].imaginary * scale) + 5));
            g.drawLine(
                    (int) ((width / 2) + (poles[i].real * scale) - 5),
                    (int) ((height / 2) - (poles[i].imaginary * scale) + 5),
                    (int) ((width / 2) + (poles[i].real * scale) + 5),
                    (int) ((height / 2) - (poles[i].imaginary * scale) - 5));

        }
    }

    private void drawZeros(Graphics g, int width, int height) {
        Complex_F64[] zeros = rl.getZeros();
        g.setColor(Color.RED);
        for (int i = 0; i < zeros.length; i++) {
            g.drawOval(
                    (int) ((width / 2) + (zeros[i].real * scale) - 3),
                    (int) ((height / 2) - (zeros[i].imaginary * scale) - 3), 6, 6);
        }
    }

    private void drawLocus(Graphics g, int width, int height, Complex_F64[][] roots) {
        g.setColor(Color.RED);
        for (int i = 0; i < roots.length; i++) {
            for (int j = 0; j < roots[i].length; j++) {
                if (roots[i][j] == null) {
                    continue;
                }
                g.fillOval(
                        (int) ((width / 2) + (roots[i][j].real * scale) - 1),
                        (int) ((height / 2) - (roots[i][j].imaginary * scale) - 1),
                        2, 2);
            }
        }
    }

    private void drawAsymptotes(Graphics g, int width, int height) {
        Complex_F64 centroid = rl.getAsymptotesCentroid();
        double[] angles = rl.getAsymptotesAngles();
        g.setColor(Color.GREEN);
        for (int i = 0; i < angles.length; i++) {
            if (angles[i] == 0 || angles[i] == 180 || angles[i] == -180) {
                continue;
            }
            double x = Math.cos(angles[i] * Math.PI / 180);
            double y = Math.sin(angles[i] * Math.PI / 180);
            g.drawLine(
                    (int) ((width / 2) + (centroid.real * scale)),
                    (int) ((height / 2) - (centroid.imaginary * scale)),
                    (int) ((width / 2) + (centroid.real * scale) + x * 1000),
                    (int) ((height / 2) - (centroid.imaginary * scale) - y * 1000));
        }
    }

    private void drawDepartureAngles(Graphics g, int width, int height) {
        Complex_F64[] poles = rl.getPoles();
        double[] departureAngles = rl.getDepartureAngles();
        g.setColor(Color.GRAY);
        for (int i = 0; i < poles.length; i++) {
            if (poles[i].imaginary == 0) {
                continue;
            }
            double x = Math.cos(departureAngles[i] * Math.PI / 180);
            double y = Math.sin(departureAngles[i] * Math.PI / 180);
            g.drawLine(
                    (int) ((width / 2) + (poles[i].real * scale) - x * 50),
                    (int) ((height / 2) - (poles[i].imaginary * scale) + y * 50),
                    (int) ((width / 2) + (poles[i].real * scale) + x * 50),
                    (int) ((height / 2) - (poles[i].imaginary * scale) - y * 50));
            g.drawString(String.valueOf(Math.round(departureAngles[i] * 100.0) / 100.0),
                    (int) ((width / 2) + (poles[i].real * scale) + 5),
                    (int) ((height / 2) - (poles[i].imaginary * scale) - 5));
        }
    }

    private void drawArrivalAngles(Graphics g, int width, int height) {
        Complex_F64[] zeros = rl.getZeros();
        double[] arrivalAngles = rl.getArrivalAngles();
        g.setColor(Color.GRAY);
        for (int i = 0; i < zeros.length; i++) {
            if (zeros[i].imaginary == 0) {
                continue;
            }
            double x = Math.cos(arrivalAngles[i] * Math.PI / 180);
            double y = Math.sin(arrivalAngles[i] * Math.PI / 180);
            g.drawLine(
                    (int) ((width / 2) + (zeros[i].real * scale) - x * 50),
                    (int) ((height / 2) - (zeros[i].imaginary * scale) + y * 50),
                    (int) ((width / 2) + (zeros[i].real * scale) + x * 50),
                    (int) ((height / 2) - (zeros[i].imaginary * scale) - y * 50));
            g.drawString(String.valueOf(Math.round(arrivalAngles[i] * 100.0) / 100.0),
                    (int) ((width / 2) + (zeros[i].real * scale) + 5),
                    (int) ((height / 2) - (zeros[i].imaginary * scale) - 5));
        }
    }
}