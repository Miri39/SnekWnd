import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel implements ActionListener {
    static final Dimension DIMENSION = new Dimension(600,600);
    private final JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;

    public MainMenuPanel(){
        this.setPreferredSize(DIMENSION);
        this.setBackground(Color.lightGray);
        this.setFocusable(true);

        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();

        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);

        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.add(button4);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button1){
            SnekGame.gameFrame.MainMenuStartGame();
        }
    }
}
