import javax.swing.JFrame;

public class App {
    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RootLocus rl1 = new RootLocus(new double[] { 32, -8, 1 }, new double[] { 0, 32, 8, 1 }, 0.01);
        RootLocus rl2 = new RootLocus(new double[] { 9, 1 }, new double[] { 0, 11, 4, 1 }, 0.05);
        RootLocus rl3 = new RootLocus(new double[] { 1 }, new double[] { 0, 6, 5, 1 }, 0.01);
        RootLocus rl4 = new RootLocus(new double[] { 1 }, new double[] { 0, 10, 17, 8, 1 }, 0.01);
        RootLocus rl5 = new RootLocus(new double[] { 100, 20, 1 }, new double[] { 0, 0, 0, 1 }, 0.3);
        RootLocus[] rl = { rl1, rl2, rl3, rl4, rl5 };
        frame.add(new Graph(rl));
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
