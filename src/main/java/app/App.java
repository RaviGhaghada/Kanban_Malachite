package app;

import java.awt.*;
import javax.swing.*;

/**
 * The class to be executed in order to run the app.
 * Currently, it creates a basic window.
 */
public class App {

    public static void main(String[] args) {

        // Create a primary/root frame. 
        JFrame frame = new JFrame("Malachello");
        frame.setMinimumSize(new Dimension(800, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create some content 
        JLabel myLabel = new JLabel("so empty :(" , SwingConstants.CENTER);
        myLabel.setFont(new Font("Serif", Font.BOLD, 22));
        myLabel.setBackground(Color.blue);
        myLabel.setOpaque(true);
        myLabel.setPreferredSize(new Dimension(100, 80));

        // Add the content to the primary/root frame 
        frame.getContentPane().add(myLabel, BorderLayout.NORTH);

        // Size the frame
        frame.pack();

        // Show it
        frame.setVisible(true);
    }
}
