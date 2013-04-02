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
	private MapObject grass11, city10, currentMap;
	/*
	 * Create the image and spritesheets.
	 */
	Image bigSpriteSheet;
	SpriteSheet sheety;
	Image charDown, charLeft, charRight, charUp, charCurr, whiteSourse, white,
			whiteTriangleRight, whiteTriangleLeft;
	/*
	 * Create variables to hold the player and map coordinates (even though
	 * they're both related and can be inferred from each other) using playerX =
	 * ((mapX * -1) / SIZE) + 12; playerY = ((mapY * -1) / SIZE) + 8;
	 */
	float mapX, mapY;
	// Boolean to declare if the map's moving and if the map is changing.
	boolean mapMoving = false, mapChange = false;
	// Size of the tiles to adjust the Tiled map syncing with collision
	// detection and interaction arrays.
	private static final int SIZE = 32;
	float playerX = ((mapX * -1) / SIZE) + 12,
			playerY = ((mapY * -1) / SIZE) + 8;
	// ints to store which direction the map is moving and which way the player
	// is facing, along with what the player just interacted with.
	public int mapDirection = 2, facing = 8, interactNum;

	// float array to store the alpha values of moonlight

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

		bigSpriteSheet = new Image("data/maps/tileSetWIP.png");
		sheety = new SpriteSheet(bigSpriteSheet, 32, 32);
		charDown = new Image("data/charDown.png");
		charUp = sheety.getSubImage(1, 3);
		charLeft = sheety.getSubImage(2, 3);
		charRight = sheety.getSubImage(3, 3);
		white = new Image("data/white.png");
		whiteTriangleRight = new Image("data/45slopeWhite.png");
		whiteTriangleLeft = whiteTriangleRight.getFlippedCopy(true, false);

		charCurr = charDown;
		// Maps
		grass11 = new MapObject(new TiledMap("data/maps/grass11.tmx"));
		city10 = new MapObject(new TiledMap("data/maps/city10.tmx"));
		changeMap(10);

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		currentMap.getThisMap().render((int) mapX, (int) mapY, 0);
		currentMap.getThisMap().render((int) mapX, (int) mapY, 1);
		charCurr.draw(384, 256);

		for (int i = 2; i < currentMap.getThisMap().getLayerCount() - 5; i++) {
			// The -1 in layerCount is to compensate for spawn layer.
			currentMap.getThisMap().render((int) mapX, (int) mapY, i);
		}
		// grassMap.setTileId(13, 10, 0, 896);

		// Draw moonlight
		drawMoonlight(g, currentMap.moonlightSpawn, currentMap.moonAlpha);

		// Debuging info:
		Color color = Color.red;
		g.setColor(color);
		g.drawString(Integer.toString((int) mapX), 36, 36);
		g.drawString(Integer.toString((int) mapY), 36, 72);
		g.setColor(Color.yellow);
		g.drawString(Integer.toString((int) ((mapX * -1) / SIZE) + 12), 36, 108);
		g.drawString(Integer.toString((int) ((mapY * -1) / SIZE) + 8), 36, 144);
		g.drawString(Float.toString(currentMap.moonAlpha[0]), 36, 160);
		g.drawString(Float.toString(currentMap.moonAlpha[1]), 36, 180);
		g.drawString(Float.toString(currentMap.moonAlpha[2]), 36, 200);

	}

	/**
	 * Changes the map and moves the player to the spawn based on the ID given
	 * in.
	 * 
	 * @param ID
	 */
	private void changeMap(int ID) {
		switch (ID) {
		case 10:
			currentMap = city10;
			break;
		case 11:
			currentMap = grass11;
			break;
		}
		for (int xAxis = 0; xAxis < currentMap.getThisMap().getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < currentMap.getThisMap().getHeight(); yAxis++) {
				if (currentMap.mapPoint[currentMap.getThisMap().getLayerCount() - 1][xAxis][yAxis] == 1) {
					playerX = xAxis;
					playerY = yAxis;
					mapX = ((playerX - 12) * SIZE) / -1;
					mapY = ((playerY - 8) * SIZE) / -1;
				}
			}
		}
		// currentMap;
	}

	/**
	 * Interpret the moonlightSpawn data to create moonlight at the right
	 * places.
	 * 
	 * @param g
	 */
	private void drawMoonlight(Graphics g, int moonlightSpawn[][][],
			float moonAlpha[]) {
		/*
		 * currentMap.alphaMoonlight(g, currentMap.interactActive,
		 * currentMap.moonAlpha);
		 */
		for (int layerCount = 5; layerCount < (currentMap.getThisMap()
				.getLayerCount()); layerCount++) {
			int[][] spawnPosition = new int[4][2];
			white.setAlpha(moonAlpha[layerCount]);
			whiteTriangleLeft.setAlpha(moonAlpha[layerCount]);
			whiteTriangleRight.setAlpha(moonAlpha[layerCount]);
			// count is used for debug
			for (int xAxis = 0; xAxis < currentMap.getThisMap().getWidth(); xAxis++) {
				for (int yAxis = 0; yAxis < currentMap.getThisMap().getHeight(); yAxis++) {
					// TODO Rewrite this as switch.
					if (moonlightSpawn[layerCount][xAxis][yAxis] == 7) {
						spawnPosition[0][0] = xAxis * SIZE;
						spawnPosition[0][1] = yAxis * SIZE;
					} else if (moonlightSpawn[layerCount][xAxis][yAxis] == 9) {
						spawnPosition[1][0] = xAxis * SIZE;
						spawnPosition[1][1] = yAxis * SIZE;
					} else if (moonlightSpawn[layerCount][xAxis][yAxis] == 1) {
						spawnPosition[2][0] = xAxis * SIZE;
						spawnPosition[2][1] = yAxis * SIZE;
					} else if (moonlightSpawn[layerCount][xAxis][yAxis] == 3) {
						spawnPosition[3][0] = xAxis * SIZE;
						spawnPosition[3][1] = yAxis * SIZE;
					} else if (moonlightSpawn[layerCount][xAxis][yAxis] == 8) {
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
		updatePlayerPosition();
		if (mapMoving) {
			moveMap(mapDirection);
			if (mapX % 32 == 0 && mapY % 32 == 0) {
				mapMoving = false;
			}
		} else {
			// input returns 2,4,6,8 if movement or 52,54,56,58 if interaction
			int t = checkInput(input, sbg);
			if (t != 0)/* Input given */{
				if (t < 10)/* if movement */{
					charFacing(t);
					boolean collDect = collDect(t, currentMap.blocked);
					if (!collDect) {
						mapMoving = true;
						mapDirection = t;
					}
				} else/* interaction */{
					intDect(t, currentMap.interact, sbg);
				}
			}
		}
		// Update tile info
		/*
		 * updateTileInfo(currentMap.interactActive, currentMap.interact,
		 * currentMap.initialTileID);
		 */
		// landed on a change map tile
		if (currentMap.mapPoint[currentMap.getThisMap().getLayerCount() - 1][(int) playerX][(int) playerY] != 0
				&& currentMap.mapPoint[currentMap.getThisMap().getLayerCount() - 1][(int) playerX][(int) playerY] != 1) {
			changeMap(currentMap.mapPoint[currentMap.getThisMap()
					.getLayerCount() - 1][(int) playerX][(int) playerY]);
		}
		// if player finished in moonlight, die.
		Display.sync(120);
	}

	/**
	 * Returns a MapObject based on currentMapInt.
	 * 
	 * @return
	 */
	// TODO TileID needs sorting out
	private void updateTileInfo(boolean interactActive[], int interact[][],
			int initialTileID[][]) {
		for (int i = 0; i < currentMap.getThisMap().getLayerCount(); i++) {
			for (int xAxis = 0; xAxis < currentMap.getThisMap().getWidth(); xAxis++) {
				for (int yAxis = 0; yAxis < currentMap.getThisMap().getHeight(); yAxis++) {
					switch (interact[xAxis][yAxis]) {
					case 1:
						if (interactActive[interact[xAxis][yAxis]])
							currentMap.getThisMap().setTileId(xAxis, yAxis, 0,
									896);
						else
							currentMap.getThisMap().setTileId(xAxis, yAxis, 0,
									initialTileID[xAxis][yAxis]);
						break;
					case 2:
						if (interactActive[interact[xAxis][yAxis]])
							currentMap.getThisMap().setTileId(xAxis, yAxis, 0,
									897);
						else
							currentMap.getThisMap().setTileId(xAxis, yAxis, 0,
									initialTileID[xAxis][yAxis]);
						break;
					}
				}
			}

		}
	}

	/**
	 * Checks to see if a collision will occur given the intended direction.
	 * 
	 * @param dir
	 *            the direction of intended movement
	 * @return true if collision would occur, false if space is free.
	 */
	private boolean collDect(int dir, boolean blocked[][][]) {

		switch (dir) {
		case 4:
			for (int layerCount = 0; layerCount < currentMap.getThisMap()
					.getLayerCount(); layerCount++) {
				if (blocked[layerCount][(int) (playerX - 1)][(int) playerY])
					return true;
			}
			break;
		case 6:
			for (int layerCount = 0; layerCount < currentMap.getThisMap()
					.getLayerCount(); layerCount++) {
				if (blocked[layerCount][(int) (playerX + 1)][(int) playerY])
					return true;
			}
			break;
		case 2:
			for (int layerCount = 0; layerCount < currentMap.getThisMap()
					.getLayerCount(); layerCount++) {
				if (blocked[layerCount][(int) (playerX)][(int) playerY + 1])
					return true;
			}
			break;
		case 8:
			for (int layerCount = 0; layerCount < currentMap.getThisMap()
					.getLayerCount(); layerCount++) {
				if (blocked[layerCount][(int) (playerX)][(int) playerY - 1])
					return true;
			}
			break;
		}
		return false;
	}

	private void updatePlayerPosition() {
		playerX = ((mapX * -1) / SIZE) + 12;
		playerY = ((mapY * -1) / SIZE) + 8;
	}

	private void intDect(int dir, int interact[][][], StateBasedGame sbg) {
		switch (dir) {
		case 54:
			for (int layerCount = 0; layerCount < currentMap.getThisMap()
					.getLayerCount(); layerCount++) {
				int interactNumber4 = interact[layerCount][(int) (playerX - 1)][(int) playerY];
				if (interactNumber4>=1) {
					interactNum = interactNumber4;
					displayText(interactNumber4, sbg);
				}
			}
			break;
		case 56:
			for (int layerCount = 0; layerCount < currentMap.getThisMap()
					.getLayerCount(); layerCount++) {
				int interactNumber6 = interact[layerCount][(int) (playerX + 1)][(int) playerY];
				if (interactNumber6>=1) {
					interactNum = interactNumber6;
					displayText(interactNumber6, sbg);
				}
			}
			break;
		case 52:
			for (int layerCount = 0; layerCount < currentMap.getThisMap()
					.getLayerCount(); layerCount++) {
				int interactNumber2 = interact[layerCount][(int) (playerX)][(int) playerY + 1];
				if (interactNumber2>=1) {
					interactNum = interactNumber2;
					displayText(interactNumber2, sbg);
				}
			}
			break;
		case 58:
			for (int layerCount = 0; layerCount < currentMap.getThisMap()
					.getLayerCount(); layerCount++) {
				int interactNumber8 = interact[layerCount][(int) (playerX)][(int) playerY - 1];
				if (interactNumber8>=1) {
					interactNum = interactNumber8;
					displayText(interactNumber8, sbg);
				}
			}
			break;
		}
	}

	/**
	 * Display text based on interact number.
	 * 
	 * @param interactNumber
	 */
	public void displayText(int interactNumber, StateBasedGame sbg) {
		sbg.enterState(3);
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
	private int checkInput(Input input, StateBasedGame sbg) {
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
		}/*
		 * else if (input.isKeyPressed(Input.KEY_2)) { sbg.enterState(3, new
		 * FadeOutTransition(), new FadeInTransition()); } else if
		 * (input.isKeyPressed(Input.KEY_1)) { sbg.enterState(2, new
		 * FadeOutTransition(), new FadeInTransition()); } else if
		 * (input.isKeyPressed(Input.KEY_0)) { sbg.enterState(1, new
		 * FadeOutTransition(), new FadeInTransition()); }
		 */

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
