package jnibwapi;

import java.util.Map;

import jnibwapi.Position.PosType;

/**
 * Represents a StarCraft base location.
 *
 * For a description of fields see:
 * http://code.google.com/p/bwta/wiki/BaseLocation
 */
public class BaseLocation {
	public static final int numAttributes = 10;
	private final Position center;
	private final Position position;
	private final Region region;
	private final int minerals;
	private final int gas;
	private final boolean island;
	private final boolean mineralOnly;
	private final boolean startLocation;

	public BaseLocation(int[] data, int index, Map<Integer, Region> idToRegion) {
		int x = data[index++];
		int y = data[index++];
		this.center = new Position(x, y);
		int tx = data[index++];
		int ty = data[index++];
		this.position = new Position(tx, ty, PosType.BUILD);
		int regionID = data[index++];
		this.region = idToRegion.get(regionID);
		this.minerals = data[index++];
		this.gas = data[index++];
		this.island = (data[index++] == 1);
		this.mineralOnly = (data[index++] == 1);
		this.startLocation = (data[index++] == 1);
	}

	/** The Position of the center of the BaseLocation */
	public Position getCenter() {
		return this.center;
	}

	/** The Position of the top left of the BaseLocation */
	public Position getPosition() {
		return this.position;
	}

	public Region getRegion() {
		return this.region;
	}

	public int getMinerals() {
		return this.minerals;
	}

	public int getGas() {
		return this.gas;
	}

	public boolean isIsland() {
		return this.island;
	}

	public boolean isMineralOnly() {
		return this.mineralOnly;
	}

	public boolean isStartLocation() {
		return this.startLocation;
	}
}
