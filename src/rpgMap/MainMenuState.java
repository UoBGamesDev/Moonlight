package rpgMap;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {
	/*
	 * Create a variable to hold stateID - a way of storing numerically what
	 * state is being run at any time. Initially this can be anything, as on
	 * creation in the main class it is allocated in the constructor below.
	 */
	int stateID = -1;
	Image mainMenuScreen = null;

	/**
	 * Constructor to create this object.
	 * 
	 * @param stateID
	 *            The stateID you want it to hold.
	 */
	MainMenuState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		mainMenuScreen = new Image("data/MainMenuScreen.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		mainMenuScreen.draw();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// Create an Input reader called input.
		Input input = gc.getInput();
		// If the A key is pressed, then StateBasedGame sbg should enter state
		// 2.
		if (input.isKeyDown(Input.KEY_A)) {
			sbg.enterState(2);
		}
		Display.sync(120);
	}

	@Override
	public int getID() {
		return stateID;
	}

}
