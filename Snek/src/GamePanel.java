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
    Direction direction = Direction.Right;
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

    public void draw(Graphics graphics) {
        if (running) {
            graphics.setColor(Color.red);
            graphics.fillOval(appleXCoordinate, appleYCoordinate, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    graphics.setColor(Color.green);
                    graphics.fillRect(XBODYPARTS[i], YBODYPARTS[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    graphics.setColor(new Color(45, 180, 0));
                    graphics.fillRect(XBODYPARTS[i], YBODYPARTS[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        }
        else{
            gameOver(graphics);
        }
    }

    public void newApple(){
        appleXCoordinate = random.nextInt((int)(DIMENSION.width/UNIT_SIZE))*UNIT_SIZE;
        appleYCoordinate = random.nextInt((int)(DIMENSION.height/UNIT_SIZE))*UNIT_SIZE;
    }

    public void move(){
        for(int i = bodyParts; i > 0; i--) {
            XBODYPARTS[i] = XBODYPARTS[i-1];
            YBODYPARTS[i] = YBODYPARTS[i-1];
        }
        switch (direction) {
            case Up -> YBODYPARTS[0] -= UNIT_SIZE;
            case Down -> YBODYPARTS[0] += UNIT_SIZE;
            case Left -> XBODYPARTS[0] -= UNIT_SIZE;
            case Right -> XBODYPARTS[0] += UNIT_SIZE;
        }
    }

    public void checkApple(){
        if((XBODYPARTS[0] == appleXCoordinate) && YBODYPARTS[0] == appleYCoordinate){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions(){
        for(int i = bodyParts; i > 0; i--){
            if((XBODYPARTS[0] == XBODYPARTS[i]) && (YBODYPARTS[0]) == YBODYPARTS[i]){
                running = false;
            }
        }
        if((XBODYPARTS[0] < 0 || XBODYPARTS[0] > DIMENSION.width) ||
           (YBODYPARTS[0] < 0 || YBODYPARTS[0] > DIMENSION.height)){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }

    public void gameOver(Graphics graphics){
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over", (DIMENSION.width - metrics.stringWidth("Game Over"))/2, DIMENSION.height/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent event){
            switch (event.getExtendedKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    if (direction != Direction.Right) {
                        direction = Direction.Left;
                    }
                }
                case KeyEvent.VK_RIGHT -> {
                    if (direction != Direction.Left) {
                        direction = Direction.Right;
                    }
                }
                case KeyEvent.VK_UP -> {
                    if (direction != Direction.Down) {
                        direction = Direction.Up;
                    }
                }
                case KeyEvent.VK_DOWN -> {
                    if (direction != Direction.Up) {
                        direction = Direction.Down;
                    }
                }
            }
        }
    }
}
