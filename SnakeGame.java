import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class SnakeGame extends JFrame {

    private final int TILE_SIZE = 25;
    private final int GRID_WIDTH = 20;
    private final int GRID_HEIGHT = 20;

    private LinkedList<Point> snake;
    private Point food;
    private String direction;
    private boolean gameRunning;

    public SnakeGame() {
        initGame();
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT && !direction.equals("RIGHT")) {
                    direction = "LEFT";
                } else if (key == KeyEvent.VK_RIGHT && !direction.equals("LEFT")) {
                    direction = "RIGHT";
                } else if (key == KeyEvent.VK_UP && !direction.equals("DOWN")) {
                    direction = "UP";
                } else if (key == KeyEvent.VK_DOWN && !direction.equals("UP")) {
                    direction = "DOWN";
                }
            }
        });
        setFocusable(true);
        Timer timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameRunning) {
                    moveSnake();
                    checkCollision();
                    repaint();
                }
            }
        });
        timer.start();
    }

    private void initGame() {
        snake = new LinkedList<>();
        snake.add(new Point(5, 5));
        snake.add(new Point(5, 6));
        snake.add(new Point(5, 7));
        direction = "UP";
        spawnFood();
        gameRunning = true;
        setTitle("Snake Game");
        setSize(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void moveSnake() {
        Point head = snake.getFirst();
        Point newHead = new Point(head.x, head.y);
        switch (direction) {
            case "LEFT":
                newHead.x--;
                break;
            case "RIGHT":
                newHead.x++;
                break;
            case "UP":
                newHead.y--;
                break;
            case "DOWN":
                newHead.y++;
                break;
        }
        snake.addFirst(newHead);
        if (newHead.equals(food)) {
            spawnFood();
        } else {
            snake.removeLast();
        }
    }

    private void checkCollision() {
        Point head = snake.getFirst();
        if (head.x < 0 || head.x >= GRID_WIDTH || head.y < 0 || head.y >= GRID_HEIGHT) {
            gameRunning = false;
        }
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                gameRunning = false;
            }
        }
    }

    private void spawnFood() {
        int x = (int) (Math.random() * GRID_WIDTH);
        int y = (int) (Math.random() * GRID_HEIGHT);
        food = new Point(x, y);
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (gameRunning) {
            g.setColor(Color.RED);
            g.fillRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            g.setColor(Color.GREEN);
            for (Point p : snake) {
                g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        } else {
            g.setColor(Color.BLACK);
            g.drawString("Game Over", getWidth() / 2 - 30, getHeight() / 2);
        }
    }

    public static void main(String[] args) {
        new SnakeGame();
    }
}

