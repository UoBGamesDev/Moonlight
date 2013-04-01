package rpgMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

public class MapObject {
	TiledMap thisMap;
	boolean blocked[][];
	boolean interactActive[];
	int[][] interact, initialTileID;
	int[][][] moonlightSpawn;
	float[] moonAlpha;

	MapObject(TiledMap may) {
		thisMap = may;
		/* Blocked - used in collision detection. */
		blocked = new boolean[thisMap.getWidth()][thisMap.getHeight()];
		for (int xAxis = 0; xAxis < thisMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < thisMap.getHeight(); yAxis++) {
				int tileID = thisMap.getTileId(xAxis, yAxis, 0);
				String value = thisMap.getTileProperty(tileID, "blocked",
						"false");
				if ("true".equals(value)) {
					blocked[xAxis][yAxis] = true;
				}
			}
		}
		/* initialTileID - used to store the values of tile ID at start. */
		initialTileID = new int[thisMap.getWidth()][thisMap.getHeight()];
		for (int xAxis = 0; xAxis < thisMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < thisMap.getHeight(); yAxis++) {
				initialTileID[xAxis][yAxis] = thisMap
						.getTileId(xAxis, yAxis, 0);
			}
		}
		/*
		 * interact - reads the int values and positions of all of the interact
		 * tiles.
		 */
		interact = new int[thisMap.getWidth()][thisMap.getHeight()];
		for (int xAxis = 0; xAxis < thisMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < thisMap.getHeight(); yAxis++) {
				int tileID = thisMap.getTileId(xAxis, yAxis, 0);
				interact[xAxis][yAxis] = Integer.parseInt(thisMap
						.getTileProperty(tileID, "interact", "0"));
			}
		}
		interactActive = new boolean[interact.length];
		moonAlpha = new float[interactActive.length];
		// TODO change interactActive and MoonAlpha to construct themselves
		// based on how many values are needed
		moonlightSpawn = new int[thisMap.getLayerCount()][thisMap.getWidth()][thisMap
				.getHeight()];
		/*
		 * moonlightSpawn - reads the layer, location and type of moonlight
		 * tile.
		 */
		for (int layerCount = 0; layerCount < thisMap.getLayerCount(); layerCount++) {
			for (int xAxis = 0; xAxis < thisMap.getWidth(); xAxis++) {
				for (int yAxis = 0; yAxis < thisMap.getHeight(); yAxis++) {
					int tileID = thisMap.getTileId(xAxis, yAxis, layerCount);
					moonlightSpawn[layerCount][xAxis][yAxis] = Integer
							.parseInt(thisMap.getTileProperty(tileID,
									"moonlight", "0"));
				}
			}
		}
	}// End constuctor

	public TiledMap getThisMap() {
		return thisMap;
	}
	/**
	 * Updates the moonAlpha array with accurate alpha float values based on
	 * their value last time alphaMoonlight was run.
	 * 
	 * @param g
	 */
	public void alphaMoonlight(Graphics g, boolean interactActive[],float moonAlpha[]) {
		if (interactActive[1]) {
			if (moonAlpha[1] > 0f) {
				moonAlpha[1] -= 0.01f;

			}
			Color color = Color.red;
			g.setColor(color);
			g.drawString("Interact1", 20, 420);

		} else if (moonAlpha[1] < 1f) {
			moonAlpha[1] += 0.01f;
		}

		if (interactActive[2]) {
			if (moonAlpha[2] > 0f) {
				moonAlpha[2] -= 0.01f;
			}
			Color color = Color.red;
			g.setColor(color);
			g.drawString("Interact2", 20, 420);
		} else if (moonAlpha[2] < 1f) {
			moonAlpha[2] += 0.01f;
		}
	}
}
