import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GamePanel extends JPanel implements ActionListener {

    enum Direction{
        Left, Up, Right, Down
    }

    static final Dimension DIMENSION = new Dimension(600,600);
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (DIMENSION.height * DIMENSION.width)/UNIT_SIZE;
    static final int DELAY = 75;
    int[] xBodyParts = new int[GAME_UNITS];
    int[] yBodyParts = new int[GAME_UNITS];
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
        try {
            draw(graphics);
        } catch (InterruptedException e) {
            System.out.println("Interrupted while sleeping");
            gameOver(graphics);
        }
    }

    public void draw(Graphics graphics) throws InterruptedException {
        if (running) {
            graphics.setColor(Color.red);
            graphics.fillOval(appleXCoordinate, appleYCoordinate, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    graphics.setColor(Color.green);
                    graphics.fillRect(xBodyParts[i], yBodyParts[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    graphics.setColor(new Color(45, 180, 0));
                    graphics.fillRect(xBodyParts[i], yBodyParts[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            graphics.setColor(Color.red);
            graphics.setFont(new Font("Ink Free", Font.PLAIN, 30));
            graphics.drawString("Score " + applesEaten, graphics.getFont().getSize(), graphics.getFont().getSize());

        }
        else{
            TimeUnit.SECONDS.sleep(2); // try using repaint
            gameOver(graphics);
        }
    }

    public void newApple(){
        do {
            appleXCoordinate = random.nextInt((DIMENSION.width / UNIT_SIZE)) * UNIT_SIZE;
            appleYCoordinate = random.nextInt((DIMENSION.height / UNIT_SIZE)) * UNIT_SIZE;
        }while ((Arrays.stream(xBodyParts).anyMatch(x -> x == appleXCoordinate)) &&
                (Arrays.stream(yBodyParts).anyMatch(y -> y == appleYCoordinate)));
    }

    public void move(){
        for(int i = bodyParts; i > 0; i--) {
            xBodyParts[i] = xBodyParts[i-1];
            yBodyParts[i] = yBodyParts[i-1];
        }
        switch (direction) {
            case Up -> yBodyParts[0] -= UNIT_SIZE;
            case Down -> yBodyParts[0] += UNIT_SIZE;
            case Left -> xBodyParts[0] -= UNIT_SIZE;
            case Right -> xBodyParts[0] += UNIT_SIZE;
        }
    }

    public void checkApple(){
        if((xBodyParts[0] == appleXCoordinate) && yBodyParts[0] == appleYCoordinate){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions(){
        for(int i = bodyParts; i > 0; i--){
            if ((xBodyParts[0] == xBodyParts[i]) && (yBodyParts[0]) == yBodyParts[i]) {
                running = false;
                break;
            }
        }
        if((xBodyParts[0] < 0 || xBodyParts[0] > DIMENSION.width) ||
           (yBodyParts[0] < 0 || yBodyParts[0] > DIMENSION.height)){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }

    public void gameOver(Graphics graphics){
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over", (DIMENSION.width - metrics1.stringWidth("Game Over"))/2, DIMENSION.height/2);
        graphics.setFont(new Font("Ink Free", Font.PLAIN, 30));
        FontMetrics metrics2 = getFontMetrics(graphics.getFont());
        graphics.drawString("Score " + applesEaten, (DIMENSION.width - metrics2.stringWidth("Score " + applesEaten))/2, graphics.getFont().getSize());
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
                case KeyEvent.VK_Q -> bodyParts+= 15;
            }
        }
    }
}
