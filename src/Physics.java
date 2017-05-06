import javax.swing.*;

/**
 * Project: Physics
 * Created by Alex on 5/3/2017.
 */
public class Physics  extends JFrame {

    UniverseFrame universe = new UniverseFrame();

    public Physics() {
        add(universe);
        setTitle("Asteroids");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setUndecorated(false);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        new Physics();
    }
}
