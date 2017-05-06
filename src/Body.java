/**
 * Project: Physics
 * Created by Alex on 5/3/2017.
 */
public class Body {
    Point Position;
    Point Velocity;
    Point Fnet;
    double Mass;
    boolean exists;

    private static final double G = 6.67 * Math.pow(10, -8);
    private static final double DENSITY = (Math.pow((6.37 * Math.pow(10, 6)), 3)) / (5.97 * Math.pow(10, 24));

    public Body(Point position, double mass){
        Position = position;
        Velocity = new Point();
        Fnet = new Point();
        Mass = mass;
        exists = true;
    }

    public void Collide(Body b1){
        b1.exists = false;
        Point i = new Point();
        i.Add(b1.Velocity, b1.Mass);
        i.Add(Velocity, Mass);
        Mass += b1.Mass;
        i.Multiply(1/(Mass));
        Velocity = i;
    }

    public static void ApplyFg(Body b1, Body b2){
        double dx = b1.Position.x - b2.Position.x;
        double dy = b1.Position.y - b2.Position.y;
        double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        if(distance <= b1.getRadius() + b2.getRadius()){
            if (b1.Mass>b2.Mass)
                b1.Collide(b2);
            else
                b2.Collide(b1);
        }
        else {
            double Fg = (G * b1.Mass * b2.Mass) / Math.pow(distance, 2);
            double Fgx = Fg * (dx / distance);
            double Fgy = Fg * (dy / distance);
            Point FgVector = new Point(Fgx, Fgy);
            b1.Fnet.Add(FgVector, -1);
            b2.Fnet.Add(FgVector, 1);
        }
    }

    public double getRadius(){
        double vol = (Mass * DENSITY);
        double rad =  Math.cbrt(vol);
        return rad;
    }

    public void RespondToFnet(){
       Velocity.Add(Fnet, 1/Mass);
       Position.Add(Velocity);
       Fnet = new Point();
    }

    public static class Point{
        double x;
        double y;

        public Point(){
            this(0,0);
        }

        public Point(double x, double y){
            this.x = x;
            this.y = y;
        }

        public void Add(Point p){
            Add(p, 1);
        }

        public void Add(Point p, double factor){
            x += p.x * factor;
            y += p.y * factor;
        }

        public void Multiply(double d){
            x *= d;
            y *= d;
        }

        public double getDistance(Point p){
            double dx = x - p.x;
            double dy = y - p.y;
            return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        }
    }
}
