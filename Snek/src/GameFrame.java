import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    Container c = getContentPane();
    CardLayout panels = new CardLayout();

    MainMenuPanel mainMenuPanel = new MainMenuPanel();
    public GameFrame(){
        c.setLayout(panels);
        c.add("Main Menu", mainMenuPanel);
//        this.add(c.getComponent(1));

//        panels.addLayoutComponent(mainMenuPanel, "Main Menu");
//        panels.addLayoutComponent(gamePanel, "Game Panel");
//        this.add(new GamePanel());
//        this.add(panels.first());


        this.setTitle("Snek");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void MainMenuStartGame(){
        GamePanel gamePanel = new GamePanel();
        c.add("GamePanel", gamePanel);
        panels.next(c);
        gamePanel.requestFocusInWindow();
        System.out.println("Pula");
    }
}
