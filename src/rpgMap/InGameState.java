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
	/*
	 * Create the TiledMaps to be used in the game.
	 */
	private TiledMap grassMap;
	/*
	 * Create the image and spritesheets.
	 */
	Image bigSpriteSheet;
	SpriteSheet sheety;
	Image charDown, charLeft, charRight, charUp, charCurr, whiteSourse, white,
			whiteTriangleRight, whiteTriangleLeft;
	/*
	 * Create variables to hold the player and map cooridantes (even though
	 * they're both related and can be inferred from eachother) using playerX =
	 * ((mapX * -1) / SIZE) + 12; playerY = ((mapY * -1) / SIZE) + 8;
	 */
	static float mapX = 0, mapY = 0, playerX = 0, playerY = 0;
	// Boolean to declare if the map's moving.
	boolean mapMoving = false;
	// arrays to store collision detection information and which interacts are
	// active.
	boolean blocked[][], interactActive[];
	int[][] interact;
	int[][][] moonlightSpawn;// Holds info on the spawnpoints of moonlight
	// Size of the tiles to adjust the Tiled map syncing with collision
	// detection and interaction arrays.
	private static final int SIZE = 32;
	// ints to store which direction the map is moving and which way the player
	// is facing.
	int mapDirection = 2, facing = 8;
	// float array to store the alpha values of moonlight
	float[] moonAlpha;

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
		white = new Image("data/white.png");
		whiteTriangleRight = new Image("data/45slopeWhite.png");
		whiteTriangleLeft = whiteTriangleRight.getFlippedCopy(true, false);

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
		// Interact. Different int values can be stored to link to different
		// actions. Be sure to explain what each int stands for and to increase
		// the interactActive[] array, leaving it +1 of the final value (the
		// default value for a tile without interaction is set as 0, so the
		// array needs to count that as well).
		interact = new int[grassMap.getWidth()][grassMap.getHeight()];
		for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
				int tileID = grassMap.getTileId(xAxis, yAxis, 0);
				interact[xAxis][yAxis] = Integer.parseInt(grassMap
						.getTileProperty(tileID, "interact", "0"));
			}
		}
		interactActive = new boolean[3];
		moonAlpha = new float[] { 0f, 0f };
		moonlightSpawn = new int[grassMap.getLayerCount() - 1][grassMap
				.getWidth()][grassMap.getHeight()];
		for (int layerCount = 0; layerCount < (grassMap.getLayerCount() - 1); layerCount++) {
			for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
				for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
					int tileID = grassMap.getTileId(xAxis, yAxis, 1);
					// Searching for tileID in 2nd layer (x,y,"1"); (see above)
					moonlightSpawn[layerCount][xAxis][yAxis] = Integer
							.parseInt(grassMap.getTileProperty(tileID,
									"moonlight", "0"));
				}
			}
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		grassMap.render((int) mapX, (int) mapY, 0);

		if (interactActive[1]) {
			if (moonAlpha[0] > 0f) {
				moonAlpha[0] -= 0.01f;
			}
			Color color = Color.red;
			g.setColor(color);
			g.drawString("Interact1", 20, 420);

		} else if (moonAlpha[0] < 1f) {
			moonAlpha[0] += 0.01f;
		}

		if (interactActive[2]) {
			if (moonAlpha[1] > 0f) {
				moonAlpha[1] -= 0.01f;
			}
			Color color = Color.red;
			g.setColor(color);
			g.drawString("Interact2", 20, 420);
		} else if (moonAlpha[1] < 1f) {
			moonAlpha[1] += 0.01f;
		}

		// Draw moonlight
		drawMoonlight(g);

		// drawChar
		charCurr.draw(384, 256);

		// Debuging info:
		Color color = Color.red;
		g.setColor(color);
		g.drawString(Integer.toString((int) mapX), 36, 36);
		g.drawString(Integer.toString((int) mapY), 36, 72);
		g.setColor(Color.yellow);
		g.drawString(Integer.toString((int) ((mapX * -1) / SIZE) + 12), 36, 108);
		g.drawString(Integer.toString((int) ((mapY * -1) / SIZE) + 8), 36, 144);
		g.drawString(Float.toString(moonAlpha[1]), 36, 160);

	}

	/**
	 * Interpret the moonlightSpawn data to create moonlight at the right
	 * places.
	 * 
	 * @param g
	 */
	private void drawMoonlight(Graphics g) {
		// TODO Auto-generated method stub

		/*
		 * 
		 * */
		for (int layerCount = 0; layerCount < (grassMap.getLayerCount() - 1); layerCount++) {
			int[][] spawnPosition = new int[4][2];
			white.setAlpha(moonAlpha[layerCount]);
			whiteTriangleLeft.setAlpha(moonAlpha[layerCount]);
			whiteTriangleRight.setAlpha(moonAlpha[layerCount]);
			for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
				for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
					// TODO Rewrite this as switch?
					if (moonlightSpawn[layerCount][xAxis][yAxis] == 7) {
						spawnPosition[0][0] = xAxis * SIZE;
						spawnPosition[0][1] = yAxis * SIZE;
					}
					if (moonlightSpawn[layerCount][xAxis][yAxis] == 9) {
						spawnPosition[1][0] = xAxis * SIZE;
						spawnPosition[1][1] = yAxis * SIZE;
					}
					if (moonlightSpawn[layerCount][xAxis][yAxis] == 1) {
						spawnPosition[2][0] = xAxis * SIZE;
						spawnPosition[2][1] = yAxis * SIZE;
					}
					if (moonlightSpawn[layerCount][xAxis][yAxis] == 3) {
						spawnPosition[3][0] = xAxis * SIZE;
						spawnPosition[3][1] = yAxis * SIZE;
					}
					if (moonlightSpawn[layerCount][xAxis][yAxis] == 8) {
						white.draw(mapX + (xAxis * SIZE),
								mapY + (yAxis * SIZE), SIZE, SIZE);
					}
				}
			}
			// draw(x,y,width,height)
			whiteTriangleLeft.draw(mapX + spawnPosition[0][0], mapY
					+ spawnPosition[0][1], SIZE, spawnPosition[2][1]
					- (spawnPosition[0][1] - SIZE));
			whiteTriangleRight.draw(mapX + spawnPosition[1][0], mapY
					+ spawnPosition[0][1], SIZE, spawnPosition[3][1]
					- (spawnPosition[0][1] - SIZE));
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
					intDect(t);
				}
			}
		}
		// if player finished in moonlight, die.
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

	private void intDect(int dir) {
		playerX = ((mapX * -1) / SIZE) + 12;
		playerY = ((mapY * -1) / SIZE) + 8;
		switch (dir) {
		case 54:
			int interactNumber4 = interact[(int) (playerX - 1)][(int) playerY];
			interactActive[interactNumber4] = !interactActive[interactNumber4];
			break;
		case 56:
			int interactNumber6 = interact[(int) (playerX + 1)][(int) playerY];
			interactActive[interactNumber6] = !interactActive[interactNumber6];

			break;
		case 52:
			int interactNumber2 = interact[(int) (playerX)][(int) playerY + 1];
			interactActive[interactNumber2] = !interactActive[interactNumber2];

			break;
		case 58:
			int interactNumber8 = interact[(int) (playerX)][(int) playerY - 1];
			interactActive[interactNumber8] = !interactActive[interactNumber8];

			break;
		}
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
