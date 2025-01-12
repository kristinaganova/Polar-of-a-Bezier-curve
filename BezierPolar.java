import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class BezierPolar extends JPanel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private ArrayList<double[]> controlPoints = new ArrayList<>();
    private double t1 = 0.5;
    private int polarLevel = 1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Interactive Bezier Curve and Polars");
        BezierPolar panel = new BezierPolar();
        frame.add(panel);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public BezierPolar() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controlPoints.add(new double[]{e.getX(), e.getY()});
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    t1 = Math.min(1, t1 + 0.1);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    t1 = Math.max(0, t1 - 0.1);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    polarLevel = Math.max(1, polarLevel - 1);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    polarLevel++;
                } else if (e.getKeyCode() == KeyEvent.VK_C) {
                    controlPoints.clear();
                }
                repaint();
            }
        });

        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.RED);
        for (double[] point : controlPoints) {
            g2d.fillOval((int) point[0] - 5, (int) point[1] - 5, 10, 10);
        }

        if (controlPoints.size() < 2) {
            return;
        }

        g2d.setColor(Color.BLUE);
        drawBezierCurve(g2d, controlPoints.toArray(new double[0][]));

        double[][] points = controlPoints.toArray(new double[0][]);
        for (int i = 1; i <= polarLevel; i++) {
            points = calculatePolar(points, t1);
            if (points.length < 2) {
                break;
            }
            g2d.setColor(getPolarColor(i));
            drawBezierCurve(g2d, points);
        }

        g2d.setColor(Color.BLACK);
        g2d.drawString("t1 = " + String.format("%.2f", t1) + " (Use UP/DOWN keys to adjust)", 10, 20);
        g2d.drawString("Polar Level: " + polarLevel + " (Use LEFT/RIGHT keys to adjust)", 10, 40);
        g2d.drawString("Press 'C' to clear points", 10, 60);
    }

    private void drawBezierCurve(Graphics2D g2d, double[][] points) {
        double prevX = points[0][0];
        double prevY = points[0][1];

        for (double t = 0; t <= 1; t += 0.01) {
            double[] current = calculateBezierPoint(points, t);
            g2d.drawLine((int) prevX, (int) prevY, (int) current[0], (int) current[1]);
            prevX = current[0];
            prevY = current[1];
        }
    }

    private double[] calculateBezierPoint(double[][] points, double t) {
        int n = points.length - 1;
        double[] result = {0, 0};

        for (int i = 0; i <= n; i++) {
            double coeff = bernstein(n, i, t);
            result[0] += coeff * points[i][0];
            result[1] += coeff * points[i][1];
        }
        return result;
    }

    private double bernstein(int n, int i, double t) {
        return combination(n, i) * Math.pow(t, i) * Math.pow(1 - t, n - i);
    }

    private int combination(int n, int k) {
        int result = 1;
        for (int i = 1; i <= k; i++) {
            result = result * (n - i + 1) / i;
        }
        return result;
    }

    private double[][] calculatePolar(double[][] points, double t1) {
        int n = points.length - 1;
        double[][] polarPoints = new double[n][2];

        for (int i = 0; i < n; i++) {
            polarPoints[i][0] = (1 - t1) * points[i][0] + t1 * points[i + 1][0];
            polarPoints[i][1] = (1 - t1) * points[i][1] + t1 * points[i + 1][1];
        }



        return polarPoints;
    }

    private Color getPolarColor(int level) {
        switch (level) {
            case 1: return Color.GREEN;
            case 2: return Color.MAGENTA;
            case 3: return Color.ORANGE;
            default:
                return new Color((level * 40) % 256, (level * 70) % 256, (level * 100) % 256);
        }
    }
}