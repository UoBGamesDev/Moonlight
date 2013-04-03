package rpgMap;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class SignState extends BasicGameState {
	int stateID = -1;
	int interactNumber;
	String[] stringy;
	XMLReader mi;

	/**
	 * Constructor to create this object.
	 * 
	 * @param stateID
	 *            The stateID you want it to hold.
	 */
	SignState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		mi = new XMLReader(MainClass.staticInGameState.currentMap.ID);
		interactNumber = MainClass.staticInGameState.interactNum;
		stringy = mi.signText;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		MainClass.staticInGameState.render(gc, sbg, g);
		g.drawImage(new Image("data/textBackground.png"), 0, 0);
		g.drawString(stringy[interactNumber-1], 30, 420);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		interactNumber = MainClass.staticInGameState.interactNum;
		Input input = gc.getInput();
		checkInput(input, sbg);

		Display.sync(120);
	}

	private void checkInput(Input input, StateBasedGame sbg) {

		if (input.isKeyPressed(Input.KEY_A)) {
			sbg.enterState(2);

		}/*
		 * else if (input.isKeyPressed(Input.KEY_2)) { sbg.enterState(3, new
		 * FadeOutTransition(), new FadeInTransition()); } else if
		 * (input.isKeyPressed(Input.KEY_1)) { sbg.enterState(2, new
		 * FadeOutTransition(), new FadeInTransition()); } else if
		 * (input.isKeyPressed(Input.KEY_0)) { sbg.enterState(1, new
		 * FadeOutTransition(), new FadeInTransition()); }
		 */

	}

	@Override
	public int getID() {
		return stateID;
	}

}
