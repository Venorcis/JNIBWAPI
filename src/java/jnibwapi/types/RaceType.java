package jnibwapi.types;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a StarCraft race type.
 *
 * For a description of fields see: http://code.google.com/p/bwapi/wiki/Race
 */
public class RaceType {
	private static Map<Integer, RaceType> idToRaceType = new HashMap<>();

	public static class RaceTypes {
		public static final RaceType Zerg = new RaceType(0);
		public static final RaceType Terran = new RaceType(1);
		public static final RaceType Protoss = new RaceType(2);
		// NOTE: Changes in BWAPI4 to:
		// Unused = 3,4,5, Random = 6, None = 7, Unknown = 8
		public static final RaceType Random = new RaceType(3);
		public static final RaceType Other = new RaceType(4);
		public static final RaceType None = new RaceType(5);
		public static final RaceType Unknown = new RaceType(6);

		public static RaceType getRaceType(int id) {
			RaceType type = idToRaceType.get(id);
			return (type == null) ? Unknown : type;
		}

		public static Collection<RaceType> getAllRaceTypes() {
			return Collections.unmodifiableCollection(idToRaceType.values());
		}
	}

	public static final int numAttributes = 6;
	private final int ID;
	private String name;
	private int workerID;
	private int centerID;
	private int refineryID;
	private int transportID;
	private int supplyProviderID;

	private RaceType(int ID) {
		this.ID = ID;
		idToRaceType.put(ID, this);
	}

	public void initialize(int[] data, int index, String name) {
		if (this.ID != data[index++]) {
			throw new IllegalArgumentException();
		}
		this.workerID = data[index++];
		this.centerID = data[index++];
		this.refineryID = data[index++];
		this.transportID = data[index++];
		this.supplyProviderID = data[index++];
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getID() {
		return this.ID;
	}

	public int getWorkerID() {
		return this.workerID;
	}

	public int getCenterID() {
		return this.centerID;
	}

	public int getRefineryID() {
		return this.refineryID;
	}

	public int getTransportID() {
		return this.transportID;
	}

	public int getSupplyProviderID() {
		return this.supplyProviderID;
	}

	@Override
	public String toString() {
		return getName() + " (" + getID() + ")";
	}
}
