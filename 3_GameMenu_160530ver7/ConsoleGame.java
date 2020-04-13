import java.awt.*;
import javax.swing.*;

public class ConsoleGame {
	private IGame game;
	
	public ConsoleGame() {
		
		game = new GameMenu_DieSimulation();
	}
	
	public void execute() {
		game.execute(this);
	}
	
	public void setGameMenu(IGame _game) {
		game = _game;
	}
} // ConsoleGame class