package Main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("NULL VITALS");

        new Main().setIcon(window);  // pass JFrame instance

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        gamePanel.config.loadConfig();

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }

    public void setIcon(JFrame window) {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("objects/heart_full.png"));
        window.setIconImage(icon.getImage());
    }
}
