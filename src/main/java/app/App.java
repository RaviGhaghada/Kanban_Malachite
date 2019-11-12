package app;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * The class to be executed in order to run the app.
 * Currently, it creates a basic window.
 */
public class App {

    public static void main(String[] args) {

        // Create a primary/root frame. 
        JFrame frame = new JFrame("Mello");
        frame.setMinimumSize(new Dimension(1000, 700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Board creation button
        JButton make_button =new JButton("Make Board");
        make_button.setBounds(130,100,100, 40);//x axis, y axis, width, height
        frame.add(make_button);//adding button in JFrame
        make_button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // new board
            }
        });

        // Create some content 
        JLabel myLabel = new JLabel("Welcome to Mello" , SwingConstants.CENTER);
        myLabel.setFont(new Font("Roboto", Font.BOLD, 22));
        myLabel.setBackground(Color.gray);
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
