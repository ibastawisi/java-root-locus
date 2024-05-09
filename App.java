import java.awt.*;
import javax.swing.JFrame;

public class App {
    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        double[] numerator = { 32, -8, 1 };
        double[] denominator = { 0, 32, 8, 1 };
        RootLocus rl = new RootLocus(numerator, denominator);
        frame.add(new Graph(rl));
        frame.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
