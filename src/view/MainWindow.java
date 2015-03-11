package view;

import controller.ButtonListener;
import controller.KeyController;
import controller.Main;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainWindow extends JFrame {

    public static JTextField message;
    public static KeyController keyListener = new KeyController(); //moved to here for endgame condition
    public static JButton startButton;
    public static JButton quitButton;
    public static Container c;
    public static boolean notOver;


    public MainWindow() {
        c = getContentPane();

        message = new JTextField("<Start>, then Shoot or Drag to kill enemies");
        message.setEditable(false);
        c.add(message, "North");

        c.add(Main.gamePanel, "Center");

        JPanel southPanel = new JPanel();
        startButton = new JButton("Start");
        southPanel.add(startButton);
        quitButton = new JButton("Quit");
        southPanel.add(quitButton);
        c.add(southPanel, "South");

        ButtonListener buttonListener = new ButtonListener();

        startButton.addActionListener(buttonListener);
        quitButton.addActionListener(buttonListener);

        startButton.addKeyListener(keyListener);
        //Main.gamePanel.addKeyListener(keyListener);
        startButton.setFocusable(true);
        quitButton.setFocusable(false);
        notOver = true;
    }
    
    public static void startGame(){
        //startButton.setEnabled(false);
        //startButton.setFocusable(false);
        //Main.gamePanel.setFocusable(true);
        startButton.setFocusable(true);
    }
    
    public static void stopGame(){
        startButton.removeKeyListener(keyListener);
        startButton.setEnabled(false);
        keyListener.goLeft = false;
        keyListener.goRight = false;
        notOver = false;
        message.setText("GAME OVER!     Score: " + Main.gameData.score);
    }
}
