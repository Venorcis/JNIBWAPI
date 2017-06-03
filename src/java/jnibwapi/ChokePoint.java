package jnibwapi;

import java.util.Map;

/**
 * Represents a choke point in a StarCraft map.
 *
 * For a description of fields see:
 * http://code.google.com/p/bwta/wiki/Chokepoint
 */
public class ChokePoint {
	public static final int numAttributes = 9;
	public static final double fixedScale = 100.0;
	private final Position center;
	private final double radius;
	private final int firstRegionID;
	private final int secondRegionID;
	private final Position firstSide;
	private final Position secondSide;
	private final Region firstRegion;
	private final Region secondRegion;

	public ChokePoint(int[] data, int index, Map<Integer, Region> idToRegion) {
		int centerX = data[index++];
		int centerY = data[index++];
		this.center = new Position(centerX, centerY);
		this.radius = data[index++] / fixedScale;
		this.firstRegionID = data[index++];
		this.secondRegionID = data[index++];
		int firstSideX = data[index++];
		int firstSideY = data[index++];
		this.firstSide = new Position(firstSideX, firstSideY);
		int secondSideX = data[index++];
		int secondSideY = data[index++];
		this.secondSide = new Position(secondSideX, secondSideY);
		this.firstRegion = idToRegion.get(this.firstRegionID);
		this.secondRegion = idToRegion.get(this.secondRegionID);
	}

	public Region getOtherRegion(Region region) {
		return region.equals(this.firstRegion) ? this.secondRegion : this.firstRegion;
	}

	public Region getFirstRegion() {
		return this.firstRegion;
	}

	public Region getSecondRegion() {
		return this.secondRegion;
	}

	public Position getCenter() {
		return this.center;
	}

	public double getRadius() {
		return this.radius;
	}

	public Position getFirstSide() {
		return this.firstSide;
	}

	public Position getSecondSide() {
		return this.secondSide;
	}
}
