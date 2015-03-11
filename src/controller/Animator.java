package controller;

public class Animator implements Runnable {

    public boolean running;
        private final int FRAMES_PER_SECOND = 40;

    @Override
    public void run() {
        
        running = true;
        
        while (running) {
            long startTime = System.currentTimeMillis();
            
            Main.gameData.update();
            Main.gamePanel.gameRender();
            Main.gamePanel.printScreen();
            
            long endTime = System.currentTimeMillis();
            int sleepTime = (int) (1.0 / FRAMES_PER_SECOND * 1000)
                    - (int) (endTime - startTime);

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime); // ms
                } catch (InterruptedException e) {

                }
            }
        }
        System.exit(0);
    }

}
