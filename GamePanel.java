package ProgrammingPractice;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

	// Constants for the screen dimensions, unit size, game units, and delay between updates
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 20;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int DELAY = 35;
	// Arrays to store the x and y coordinates of snake body parts
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	// Initial number of body parts, apples eaten, and initial positions of the apple
	int bodyParts = 5;
	int applesEaten;
	int appleX;
	int appleY;
	// Initial direction of the snake ('R' for right)
	char direction = 'R';
	// Variable to track if the game is currently running
	boolean running = false;
	// Timer for controlling the game update cycle
	Timer timer;
	//Random number generator for placing apples in random positions
	Random random;

	
	// Constructor for the GamePanel class
	GamePanel() {
	    // Initialize a Random object for generating random values
	    random = new Random();
	    // Set the preferred size of the panel to match the screen dimensions
	    this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
   	    // Set the background color of the panel to black
	    this.setBackground(Color.BLACK);
	    // Allow the panel to receive keyboard input focus
	    this.setFocusable(true);
	    // Add a KeyListener to the panel to handle keyboard input
	    this.addKeyListener(new MyKeyAdapter());
   	    // Start the game by calling the startGame method
	    startGame();
	}

	// Start the game by initializing a new apple, setting the running flag to true,
	// and starting a timer for periodic updates
	public void startGame() {
	    // Initialize a new apple on the game panel
	    newApple();	    
	    // Set the running flag to true, indicating that the game is in progress
	    running = true;	    
	    // Create a new timer for periodic updates, using the DELAY value and
	    // registering the current instance of GamePanel as an ActionListener
	    timer = new Timer(DELAY, this);	    
	    // Start the timer to begin the game loop
	    timer.start();
	}
	// Override the paintComponent method to draw the game elements on the panel
	public void paintComponent(Graphics g) {
	    // Call the superclass paintComponent method to ensure proper rendering
	    super.paintComponent(g);	    
	    // Call the custom draw method to render the game elements on the panel
	    draw(g);
	}
	// Draw method responsible for rendering game elements on the panel
	public void draw(Graphics g) {
	    // Check if the game is currently running
	    if (running) {
	        // Draw the grid lines (commented out in this version)
	        /*
	        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
	            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
	            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
	        }
	        */
	    	
	        // Draw the apple in red at its specified coordinates
	        g.setColor(Color.RED);
	        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
	        // Draw the snake body parts
	        for (int i = 0; i < bodyParts; i++) {
	            if (i == 0) {
	                // Head of the snake is drawn in green
	                g.setColor(Color.GREEN);
	                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
	            } else {
	                // Body parts of the snake are drawn in a shade of green
	                g.setColor(new Color(45, 180, 0));
	                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
	            }
	        }
	        // Display the score on the screen in white, using a specified font
	        g.setColor(Color.WHITE);
	        g.setFont(new Font("Roboto Sans", Font.BOLD, 40));
	        FontMetrics met = getFontMetrics(g.getFont());
	        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - met.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
	    } else {
	        // If the game is not running, display the game over message
	        gameOver(g);
	    }
	}	
	// Generate a new random position for the apple within the game grid
	public void newApple() {
	    // Calculate random X and Y coordinates for the apple, ensuring they align with grid cells
	    appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
	    appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}

	// Move the snake based on its current direction
	public void move() {
	    // Update the positions of the snake body parts
	    for (int i = bodyParts; i > 0; i--) {
	        x[i] = x[i - 1];
	        y[i] = y[i - 1];
	    }

	    // Move the head of the snake based on the current direction
	    switch (direction) {
	        case 'U':
	            y[0] = y[0] - UNIT_SIZE;
	            break;
	            
	        case 'D':
	            y[0] = y[0] + UNIT_SIZE;
	            break;
	            
	        case 'L':
	            x[0] = x[0] - UNIT_SIZE;
	            break;
	            
	        case 'R':
	            x[0] = x[0] + UNIT_SIZE;
	            break;
	    }
	}
	
	// Check if the head of the snake has collided with the apple
	public void checkApple() {
	    // If the head of the snake is at the same position as the apple
	    if ((x[0] == appleX) && (y[0] == appleY)) {
	        // Increase the number of body parts
	        bodyParts++;
	        
	        // Increment the count of apples eaten
	        applesEaten++;
	        
	        // Generate a new random position for the apple
	        newApple();
	    }
	}

	// Check for collisions with the snake's body and game borders
	public void checkCollisions() {
	    // Check if the head collides with the body
	    for (int i = bodyParts; i > 0; i--) {
	        if ((x[0] == x[i]) && (y[0] == y[i])) {
	            // If the head collides with the body, stop the game
	            running = false;
	        }
	    }

	    // Check if the head touches the Left Border
	    if (x[0] < 0) {
	        // If the head touches the Left Border, stop the game
	        running = false;
	    }

	    // Check if the head touches the Right Border
	    if (x[0] > SCREEN_WIDTH) {
	        // If the head touches the Right Border, stop the game
	        running = false;
	    }

	    // Check if the head touches the Top Border
	    if (y[0] < 0) {
	        // If the head touches the Top Border, stop the game
	        running = false;
	    }

	    // Check if the head touches the Bottom Border
	    if (y[0] > SCREEN_HEIGHT) {
	        // If the head touches the Bottom Border, stop the game
	        running = false;
	    }

	    // If the game is not running, stop the timer
	    if (!running) {
	        timer.stop();
	    }
	}

	// Display the game over message along with the final score
	public void gameOver(Graphics g) {
	    // Display the final score in white, using a specified font
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Roboto Sans", Font.BOLD, 40));
	    FontMetrics met1 = getFontMetrics(g.getFont());
	    g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - met1.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

	    // Display the "Game Over!" message in red, using a larger font
	    g.setColor(Color.RED);
	    g.setFont(new Font("Roboto Sans", Font.BOLD, 80));
	    FontMetrics met2 = getFontMetrics(g.getFont());
	    g.drawString("Game Over!", (SCREEN_WIDTH - met2.stringWidth("Game Over!")) / 2, SCREEN_HEIGHT / 2);
	}

	// ActionListener method that handles actions triggered by the timer
	public void actionPerformed(ActionEvent e) {
	    // Check if the game is currently running
	    if (running) {
	        // Move the snake, check for apple collisions, and check for other collisions
	        move();
	        checkApple();
	        checkCollisions();
	    }
	    // Repaint the panel to update the visual representation of the game
	    repaint();
	}

	// Custom KeyAdapter class to handle keyboard input for controlling the snake direction
	public class MyKeyAdapter extends KeyAdapter {
	    // Override the keyPressed method to handle key events
	    @Override
	    public void keyPressed(KeyEvent e) {
	        // Switch on the pressed key code to determine the new direction of the snake
	        switch (e.getKeyCode()) {
	            case KeyEvent.VK_LEFT:
	                // Change direction to left if not currently moving right
	                if (direction != 'R') {
	                    direction = 'L';
	                }
	                break;
	            case KeyEvent.VK_RIGHT:
	                // Change direction to right if not currently moving left
	                if (direction != 'L') {
	                    direction = 'R';
	                }
	                break;
	            case KeyEvent.VK_UP:
	                // Change direction upwards if not currently moving down
	                if (direction != 'D') {
	                    direction = 'U';
	                }
	                break;
	            case KeyEvent.VK_DOWN:
	                // Change direction downwards if not currently moving up
	                if (direction != 'U') {
	                    direction = 'D';
	                }
	                break;
	        }
	    }
	}
}


