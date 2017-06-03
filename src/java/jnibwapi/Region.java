package jnibwapi;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Represents a region in a StarCraft map.
 *
 * For a description of fields see: http://code.google.com/p/bwta/wiki/Region
 */
public class Region {
	public static final int numAttributes = 3;
	private final int ID;
	private final Position center;
	private final Position[] polygon;
	private final Set<Region> connectedRegions = new HashSet<>();
	private final Set<ChokePoint> chokePoints = new HashSet<>();
	private Set<Region> allConnectedRegions = null;

	public Region(int[] data, int index, int[] coordinates) {
		this.ID = data[index++];
		int centerX = data[index++];
		int centerY = data[index++];
		this.center = new Position(centerX, centerY);
		this.polygon = new Position[coordinates.length / 2];
		for (int i = 0; i < coordinates.length; i += 2) {
			this.polygon[i / 2] = new Position(coordinates[i], coordinates[i + 1]);
		}
	}

	public int getID() {
		return this.ID;
	}

	public Position getCenter() {
		return this.center;
	}

	public Position[] getPolygon() {
		return Arrays.copyOf(this.polygon, this.polygon.length);
	}

	protected void addChokePoint(ChokePoint chokePoint) {
		this.chokePoints.add(chokePoint);
	}

	public Set<ChokePoint> getChokePoints() {
		return Collections.unmodifiableSet(this.chokePoints);
	}

	protected void addConnectedRegion(Region other) {
		this.connectedRegions.add(other);
	}

	public Set<Region> getConnectedRegions() {
		return Collections.unmodifiableSet(this.connectedRegions);
	}

	/** Get all transitively connected regions for a given region */
	public Set<Region> getAllConnectedRegions() {
		// Evaluate on first call
		if (this.allConnectedRegions == null) {
			this.allConnectedRegions = new HashSet<>();
			LinkedList<Region> unexplored = new LinkedList<>();
			unexplored.add(this);
			while (!unexplored.isEmpty()) {
				Region current = unexplored.remove();
				if (this.allConnectedRegions.add(current)) {
					unexplored.addAll(current.getConnectedRegions());
				}
			}
		}
		return Collections.unmodifiableSet(this.allConnectedRegions);
	}
}
