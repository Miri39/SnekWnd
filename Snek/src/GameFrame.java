import com.sun.tools.javac.Main;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame(){
//        this.add(new GamePanel());
        this.add(new MainMenu());
        this.setTitle("Snek");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void MainMenuStartGame(){
        this.removeAll();
        this.add(new GamePanel());
    }
}
