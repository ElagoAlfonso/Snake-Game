package ProgrammingPractice;

import javax.swing.JFrame;

public class GameFrame extends JFrame { //extends from SnakeGame
	
	// Constructor for the GameFrame class
	GameFrame(){
		// Create a new instance of the GamePanel class
		GamePanel panel = new GamePanel();
		
		// Add the GamePanel to the GameFrame
		// Note: It seems you are adding a new instance here, not the 'panel' created earlier
		this.add(new GamePanel());
		
		// Set the title of the GameFrame
		this.setTitle("Snake Game");
		
		// Set the default close operation to exit the application when the frame is closed
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Disable frame resizing
		this.setResizable(false);
		
		// Automatically adjust the size of the frame to fit its components
		this.pack();
		
		// Set the frame as visible
		this.setVisible(true);
		
		// Center the frame on the screen
		this.setLocationRelativeTo(null);
	}
	// End of the constructor
}
// End of the GameFrame class

