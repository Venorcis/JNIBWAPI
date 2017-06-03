package jnibwapi.types;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a StarCraft unit size type.
 *
 * For a description of fields see:
 * http://code.google.com/p/bwapi/wiki/UnitSizeType
 */
public class UnitSizeType {
	private static Map<Integer, UnitSizeType> idToUnitSizeType = new HashMap<>();

	public static class UnitSizeTypes {
		public static final UnitSizeType Independent = new UnitSizeType(0);
		public static final UnitSizeType Small = new UnitSizeType(1);
		public static final UnitSizeType Medium = new UnitSizeType(2);
		public static final UnitSizeType Large = new UnitSizeType(3);
		public static final UnitSizeType None = new UnitSizeType(4);
		public static final UnitSizeType Unknown = new UnitSizeType(5);

		public static UnitSizeType getUnitSizeType(int id) {
			UnitSizeType type = idToUnitSizeType.get(id);
			return (type == null) ? Unknown : type;
		}

		public static Collection<UnitSizeType> getAllUnitSizeTypes() {
			return Collections.unmodifiableCollection(idToUnitSizeType.values());
		}
	}

	public static final int numAttributes = 1;
	private final int ID;
	private String name;

	private UnitSizeType(int ID) {
		this.ID = ID;
		idToUnitSizeType.put(ID, this);
	}

	public void initialize(int[] data, int index, String name) {
		if (this.ID != data[index++]) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getID() {
		return this.ID;
	}

	@Override
	public String toString() {
		return getName() + " (" + getID() + ")";
	}
}
