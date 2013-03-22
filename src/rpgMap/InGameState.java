package rpgMap;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Color;

public class InGameState extends BasicGameState {
	/*
	 * Create a variable to hold stateID - a way of storing numerically what
	 * state is being run at any time. Initially this can be anything, as on
	 * creation in the main class it is allocated in the constructor below.
	 */
	int stateID = -1;
	private TiledMap grassMap;
	Image greenGrass;
	Image bigSpriteSheet;
	SpriteSheet sheety;
	Image charDown, charLeft, charRight, charUp, charCurr, textBox, player;
	static float mapX = 0, mapY = 0, playerX = 0, playerY = 0;
	boolean mapMoving = false, textOn = false, blocked[][], interact[][];
	private static final int SIZE = 32;
	int mapDirection = 2, facing = 8;

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
		// Images
		bigSpriteSheet = new Image("data/tileSetWIP.png");
		sheety = new SpriteSheet(bigSpriteSheet, 32, 32);
		charDown = sheety.getSubImage(0, 3);
		charUp = sheety.getSubImage(1, 3);
		charLeft = sheety.getSubImage(2, 3);
		charRight = sheety.getSubImage(3, 3);
		charCurr = charDown;

		// Maps
		grassMap = new TiledMap("data/grass1.tmx");

		// Blocked
		blocked = new boolean[grassMap.getWidth()][grassMap.getHeight()];
		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				int tileID = grassMap.getTileId(xAxis, yAxis, 0);
				String value = grassMap.getTileProperty(tileID, "blocked",
						"false");
				if ("true".equals(value)) {
					blocked[xAxis][yAxis] = true;
				}
			}
		}
		// Interact
		interact = new boolean[grassMap.getWidth()][grassMap.getHeight()];
		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				int tileID = grassMap.getTileId(xAxis, yAxis, 0);
				String value = grassMap.getTileProperty(tileID, "interact",
						"false");
				if ("true".equals(value)) {
					interact[xAxis][yAxis] = true;
				}
			}
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		grassMap.render((int) mapX, (int) mapY);
		// set this to draw at something better than 400,400
		charCurr.draw(384, 256);

		// How to draw text to screen:

		if (textOn) {
			Color color = Color.red;
			g.setColor(color);
			g.drawString("Lever on", 20, 420);
		}

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		Input input = gc.getInput();
		if (mapMoving) {
			moveMap(mapDirection);
			if (mapX % 32 == 0 && mapY % 32 == 0) {
				mapMoving = false;
			}
		} else {
			// input returns 2,4,6,8 if movement or 52,54,56,58 if interaction
			int t = checkInput(input);
			if (t != 0)/* Input given */{
				if (t < 10)/* if movement */{
					charFacing(t);
					boolean collDect = collDect(t);
					if (!collDect) {
						mapMoving = true;
						mapDirection = t;
					}
				} else/* interaction */{
					if(intDect(t)){
						textOn=!textOn;
					}
					
				}
			}
			// if interaction buttons pressed, handle interaction
			// if player finished in moonlight, die.
		}
		Display.sync(120);
	}

	/**
	 * Checks to see if a collision will occur given the intended direction.
	 * 
	 * @param dir
	 *            the direction of intended movement
	 * @return true if collision would occur, false if space is free.
	 */
	private boolean collDect(int dir) {
		playerX = ((mapX * -1) / SIZE) + 12;
		playerY = ((mapY * -1) / SIZE) + 8;
		switch (dir) {
		case 4:
			if (blocked[(int) (playerX - 1)][(int) playerY])
				return true;
			break;
		case 6:
			if (blocked[(int) (playerX + 1)][(int) playerY])
				return true;
			break;
		case 2:
			if (blocked[(int) (playerX)][(int) playerY + 1])
				return true;
			break;
		case 8:
			if (blocked[(int) (playerX)][(int) playerY - 1])
				return true;
			break;
		}
		return false;
	}

	private boolean intDect(int dir) {
		playerX = ((mapX * -1) / SIZE) + 12;
		playerY = ((mapY * -1) / SIZE) + 8;
		switch (dir) {
		case 54:
			if (interact[(int) (playerX - 1)][(int) playerY])
				return true;
			break;
		case 56:
			if (interact[(int) (playerX + 1)][(int) playerY])
				return true;
			break;
		case 52:
			if (interact[(int) (playerX)][(int) playerY + 1])
				return true;
			break;
		case 58:
			if (interact[(int) (playerX)][(int) playerY - 1])
				return true;
			break;
		}
		return false;
	}

	/**
	 * Updates the direction of char facing.
	 * 
	 * @param dir
	 */
	private void charFacing(int dir) {
		switch (dir) {
		case 2:
			charCurr = charDown;
			break;
		case 4:
			charCurr = charLeft;
			break;
		case 6:
			charCurr = charRight;
			break;
		case 8:
			charCurr = charUp;
			break;
		}
	}

	/**
	 * Outputs the user input as an int that can be interpreted by the system.
	 * 
	 * @param input
	 *            Up, Down, Left, Right, Spacebar.
	 * @return int representing the key.
	 */
	private int checkInput(Input input) {
		if (input.isKeyDown(Input.KEY_LEFT)) {
			facing = 4;
			return 4;
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			facing = 6;
			return 6;
		} else if (input.isKeyDown(Input.KEY_UP)) {
			facing = 8;
			return 8;
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			facing = 2;
			return 2;
		} else if (input.isKeyPressed(Input.KEY_A)) {
			switch (facing) {
			case 4:
				return 54;
			case 6:
				return 56;
			case 8:
				return 58;
			case 2:
				return 52;
			default:
				break;
			}
		}
		return 0;
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
