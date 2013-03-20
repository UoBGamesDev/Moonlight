package rpgMap;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class InGameState extends BasicGameState {
	/*
	 * Create a variable to hold stateID - a way of storing numerically what
	 * state is being run at any time. Initially this can be anything, as on
	 * creation in the main class it is allocated in the constructor below.
	 */
	int stateID = -1;
	Image greenGrass = null;
	Image charDown, charLeft, charRight, charUp, charCurr;
	float mapX = 0, mapY = 0;
	boolean mapMoving = false;
	int mapDirection = 2;

	/**
	 * Constructor to create this object.
	 * 
	 * @param stateID
	 *            The stateID you want it to hold.
	 */
	InGameState(int stateID) {
		this.stateID = stateID;
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		greenGrass = new Image("data/greenGrass.png");
		charDown = new Image("data/charDown.png");
		charUp = new Image("data/charUp.png");
		charLeft = new Image("data/charLeft.png");
		charRight = new Image("data/charRight.png");
		charCurr = charDown;

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		greenGrass.draw(mapX, mapY);
		//set this to draw at something better than 400,400
		charCurr.draw(400, 400);

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = gc.getInput();
		if (mapMoving) {
			moveMap(mapDirection);
			if (mapX % 64 == 0 && mapY % 64 == 0) {
				mapMoving = false;
			}
		} else {
			checkInput(input);
		}
		Display.sync(120);
	}

	private void checkInput(Input input) {
		// TODO collision detection.
		if (input.isKeyDown(Input.KEY_LEFT)) {
			charCurr = charLeft;
			mapMoving = true;
			mapDirection = 4;
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			charCurr = charRight;
			mapMoving = true;
			mapDirection = 6;
		} else if (input.isKeyDown(Input.KEY_UP)) {
			charCurr = charUp;
			mapMoving = true;
			mapDirection = 8;
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			charCurr = charDown;
			mapMoving = true;
			mapDirection = 2;
		}
	}

	/**
	 * Moves the map based on a direction input from checkInput. 4=left, 8=up,
	 * 2=down, 6=right.
	 * 
	 * @param i
	 */
	private void moveMap(int i) {
		switch (i) {
		case 2:
			mapY = mapY - 1;
			break;
		case 4:
			mapX = mapX + 1;
			break;
		case 6:
			mapX = mapX - 1;
			break;
		case 8:
			mapY = mapY + 1;
			break;
		default:
			break;
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}
