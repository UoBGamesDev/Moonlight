package rpgMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

public class MapObject {
	int ID;
	TiledMap thisMap;
	boolean blocked[][][];
	boolean interactActive[];
	int[][][] interact, initialTileID, talk, mapPoint;
	int[][][] moonlightSpawn;
	float[] moonAlpha;
	float mapX, mapY, playerX, playerY;

	MapObject(TiledMap may) {
		thisMap = may;
		ID = Integer.parseInt(thisMap.getMapProperty("mapNum", "0"));
		blocked = new boolean[thisMap.getLayerCount()][thisMap.getWidth()][thisMap
				.getHeight()];
		initialTileID = new int[thisMap.getLayerCount()][thisMap.getWidth()][thisMap.getHeight()];
		interact = new int[thisMap.getLayerCount()][thisMap.getWidth()][thisMap.getHeight()];
		talk = new int[thisMap.getLayerCount()][thisMap.getWidth()][thisMap.getHeight()];
		moonAlpha = new float[thisMap.getLayerCount()];
		moonlightSpawn = new int[thisMap.getLayerCount()][thisMap.getWidth()][thisMap
				.getHeight()];
		mapPoint = new int[thisMap.getLayerCount()][thisMap.getWidth()][thisMap.getHeight()];
		// Get all the info for the arrays here.
		for (int layerCount = 0; layerCount < thisMap.getLayerCount(); layerCount++) {
			for (int xAxis = 0; xAxis < thisMap.getWidth(); xAxis++) {
				for (int yAxis = 0; yAxis < thisMap.getHeight(); yAxis++) {
					int tileID = thisMap.getTileId(xAxis, yAxis, layerCount);
					initialTileID[layerCount][xAxis][yAxis] = thisMap.getTileId(xAxis,
							yAxis, 0);
					interact[layerCount][xAxis][yAxis] = Integer.parseInt(thisMap
							.getTileProperty(tileID, "interact", "0"));
					talk[layerCount][xAxis][yAxis] = Integer.parseInt(thisMap
							.getTileProperty(tileID, "talk", "0"));
					mapPoint[layerCount][xAxis][yAxis] = Integer.parseInt(thisMap
							.getTileProperty(tileID, "map", "0"));
					moonlightSpawn[layerCount][xAxis][yAxis] = Integer
							.parseInt(thisMap.getTileProperty(tileID,
									"moonlight", "0"));
					if ("true".equals(thisMap.getTileProperty(tileID,
							"blocked", "false"))) {
						blocked[layerCount][xAxis][yAxis] = true;
					}
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
	public void alphaMoonlight(Graphics g, boolean interactActive[],
			float moonAlpha[]) {
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
