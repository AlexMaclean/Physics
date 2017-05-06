import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Project: Physics
 * Created by Alex on 5/3/2017.
 */
public class UniverseFrame extends JPanel {

    private Timer _timer;

    private ArrayList<Body> bodies;
    private static final double RATIO = 1000 / (5.9 * Math.pow(10, 9));
    double uWidth;
    double uHeight;
    int windowHeight = 1000;
    int windowWidth = 1000;

    public UniverseFrame(){
        setBackground(Color.BLACK);
        bodies = new ArrayList<Body>();

        uWidth = windowHeight / RATIO;
        uHeight = windowWidth / RATIO;
       // bodies.add(new Body(new Body.Point(0, 0),5.97 * Math.pow(10, 25)));
      //  bodies.add(new Body( new Body.Point(0, 1.8 * Math.pow(10, 8)), 7.34 * Math.pow(10, 24)));
        populateUniverse(100,2 * Math.pow(10, 26),2.0 * Math.pow(10, 10));
        _timer = new Timer(2, e -> paintInterval());
        _timer.start();
    }

    public void populateUniverse(int bodies, double max, double min){
        for (int i = 0; i < bodies; i++){
            double mass = min + Math.random() * (max - min);
            double x = Math.random() * uWidth - uWidth / 2;
            double y = Math.random() * uHeight - uHeight / 2;
            Body b = new Body(new Body.Point(x, y), mass);
            this.bodies.add(b);
        }
    }

    private void paintInterval() {
        for (int i = 0; i < bodies.size(); i++){
            for(int j = i; j < bodies.size(); j++){
                if(i == j)continue;
                Body.ApplyFg(bodies.get(i), bodies.get(j));
            }
        }
        for (Body b : bodies.toArray(new Body[bodies.size()])) {
            b.RespondToFnet();
            if(!b.exists){
                bodies.remove(b);
            }
        }
        this.repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        g2d.setColor(Color.cyan);
        for (Body b : bodies) {
            drawBody(b, g2d);
        }
    }

    public void drawBody(Body b, Graphics2D g2d){
        int wx = toPixels(b.Position.x) + windowWidth / 2;
        int wy = toPixels(b.Position.y) + windowHeight / 2;
        int wRadius =  toPixels(b.getRadius());
        g2d.fillOval(wx-wRadius, wy-wRadius, wRadius*2, wRadius*2);
    }

    public int toPixels(double meters){
        return (int) Math.round(meters * RATIO);
    }
}
