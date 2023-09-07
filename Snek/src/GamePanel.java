import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    enum Direction{
        Left, Up, Right, Down
    };

    static final Dimension DIMENSION = new Dimension(600,600);
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (DIMENSION.height+DIMENSION.width)/UNIT_SIZE;
    static final int DELAY = 75;
    final int XBODYPARTS[] = new int[GAME_UNITS];
    final int YBODYPARTS[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleXCoordinate;
    int appleYCoordinate;
    int direction = Direction.Right.ordinal();
    boolean running = false;
    Timer timer;
    Random random;
    public GamePanel(){
        random = new Random();
        this.setPreferredSize(DIMENSION);
        this.setBackground(Color.lightGray);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics){
        graphics.setColor(Color.red);
        graphics.fillOval(appleXCoordinate, appleYCoordinate, UNIT_SIZE, UNIT_SIZE);
    }

    public void newApple(){
        appleXCoordinate = random.nextInt((int)(DIMENSION.width/UNIT_SIZE))*UNIT_SIZE;
        appleYCoordinate = random.nextInt((int)(DIMENSION.height/UNIT_SIZE))*UNIT_SIZE;
    }

    public void move(){

    }

    public void checkApple(){

    }

    public void checkCollisions(){

    }

    public void gameOver(Graphics graphics){

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent event){

        }
    }
}
