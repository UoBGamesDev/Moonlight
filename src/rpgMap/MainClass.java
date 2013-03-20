package rpgMap;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MainClass extends StateBasedGame {

	public MainClass() {
		super("JRPG Main Class");
	}

	/**
	 * Creates a window of dimensions given below, and fills it with MainClass,
	 * then starts showing it.
	 * 
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new MainClass());

		app.setDisplayMode(960, 640, false);
		app.start();
	}

	@Override
	/**
	 * Adds BasicGameStates to the StateBasedGame though 
	 * "this.addState(new BasicGameState);" where BasicGameState
	 *  is the basic game state object being added.
	 */
	public void initStatesList(GameContainer arg0) throws SlickException {
		this.addState(new MainMenuState(1));
		this.addState(new InGameState(2));
	}

}
