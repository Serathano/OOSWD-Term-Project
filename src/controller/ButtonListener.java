package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.MainWindow;

public class ButtonListener implements ActionListener {

    boolean started;

    public ButtonListener() {
        this.started = false;
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == MainWindow.startButton) {
            if(!started){
                Thread t = new Thread(Main.animator);
                t.start();
                MainWindow.startGame();
                MainWindow.message.setText("Enemies Killed: 0");
                Main.gamePanel.setBackground(Color.black);
                started = true;
            }
        } //else if (ae.getSource() == MainWindow.addButton) {
            //Main.gameData.add(10);
           // Main.gamePanel.requestFocusInWindow();
         //}
         else if (ae.getSource() == MainWindow.quitButton) {
            if (Main.animator.running) {
                Main.animator.running = false;
            } else {
                System.exit(0);
            }
        }
    }

}
