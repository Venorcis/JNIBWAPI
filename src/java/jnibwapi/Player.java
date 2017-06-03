package jnibwapi;

import java.util.ArrayList;
import java.util.List;

import jnibwapi.Position.PosType;
import jnibwapi.types.PlayerType;
import jnibwapi.types.RaceType;
import jnibwapi.types.RaceType.RaceTypes;
import jnibwapi.types.TechType;
import jnibwapi.types.TechType.TechTypes;
import jnibwapi.types.UpgradeType;
import jnibwapi.types.UpgradeType.UpgradeTypes;

/**
 * Represents a StarCraft player.
 *
 * For a description of fields see: http://code.google.com/p/bwapi/wiki/Player
 */
public class Player {
	public static final int numAttributes = 11;

	private final int ID;
	private final int raceID;
	private final int typeID;
	private final int startLocationX;
	private final int startLocationY;
	private final boolean self;
	private final boolean ally;
	private final boolean enemy;
	private final boolean neutral;
	private final boolean observer;
	private final int color;
	private final String name;

	private int minerals;
	private int gas;
	private int supplyUsed;
	private int supplyTotal;
	private int cumulativeMinerals;
	private int cumulativeGas;
	private int unitScore;
	private int killScore;
	private int buildingScore;
	private int razingScore;

	private final boolean[] researching;
	private final boolean[] researched;
	private final boolean[] upgrading;
	private final int[] upgradeLevel;

	public Player(int[] data, int index, String name) {
		this.ID = data[index++];
		this.raceID = data[index++];
		this.typeID = data[index++];
		this.startLocationX = data[index++];
		this.startLocationY = data[index++];
		this.self = (data[index++] == 1);
		this.ally = (data[index++] == 1);
		this.enemy = (data[index++] == 1);
		this.neutral = (data[index++] == 1);
		this.observer = (data[index++] == 1);
		this.color = data[index++];
		this.name = name;
		// Initialise technology records
		int highestIDTechType = 0;
		for (TechType t : TechTypes.getAllTechTypes()) {
			highestIDTechType = Math.max(highestIDTechType, t.getID());
		}
		this.researching = new boolean[highestIDTechType + 1];
		this.researched = new boolean[highestIDTechType + 1];
		int highestIDUpgradeType = 0;
		for (UpgradeType ut : UpgradeTypes.getAllUpgradeTypes()) {
			highestIDUpgradeType = Math.max(highestIDUpgradeType, ut.getID());
		}
		this.upgrading = new boolean[highestIDUpgradeType + 1];
		this.upgradeLevel = new int[highestIDUpgradeType + 1];
	}

	public void update(int[] data) {
		int index = 0;
		this.minerals = data[index++];
		this.gas = data[index++];
		this.supplyUsed = data[index++];
		this.supplyTotal = data[index++];
		this.cumulativeMinerals = data[index++];
		this.cumulativeGas = data[index++];
		this.unitScore = data[index++];
		this.killScore = data[index++];
		this.buildingScore = data[index++];
		this.razingScore = data[index++];
	}

	public void updateResearch(int[] techData, int[] upgradeData) {
		for (int i = 0; i < techData.length; i += 3) {
			int techTypeID = techData[i];
			if (techTypeID < this.researched.length) { // HACK FIX
				this.researched[techTypeID] = (techData[i + 1] == 1);
				this.researching[techTypeID] = (techData[i + 2] == 1);
			}
		}
		for (int i = 0; i < upgradeData.length; i += 3) {
			int upgradeTypeID = upgradeData[i];
			if (upgradeTypeID < this.upgradeLevel.length) { // HACK FIX
				this.upgradeLevel[upgradeTypeID] = upgradeData[i + 1];
				this.upgrading[upgradeTypeID] = (upgradeData[i + 2] == 1);
			}
		}
	}

	public int getID() {
		return this.ID;
	}

	public RaceType getRace() {
		return RaceTypes.getRaceType(this.raceID);
	}

	public PlayerType getTypeID() {
		return PlayerType.getPlayerType(this.typeID);
	}

	/**
	 * Returns the starting tile position of the Player. Note: the position may
	 * be equal to Positions.Invalid / Positions.None / Positions.Unknown.
	 */
	public Position getStartLocation() {
		return new Position(this.startLocationX, this.startLocationY, PosType.BUILD);
	}

	public boolean isSelf() {
		return this.self;
	}

	public boolean isAlly() {
		return this.ally;
	}

	public boolean isEnemy() {
		return this.enemy;
	}

	public boolean isNeutral() {
		return this.neutral;
	}

	public boolean isObserver() {
		return this.observer;
	}

	public int getColor() {
		return this.color;
	}

	public String getName() {
		return this.name;
	}

	public List<Unit> getUnits() {
		List<Unit> units = new ArrayList<>();
		for (Unit u : JNIBWAPI.getInstance().getAllUnits()) {
			if (u.getPlayer() == this) {
				units.add(u);
			}
		}
		return units;
	}

	public int getMinerals() {
		return this.minerals;
	}

	public int getGas() {
		return this.gas;
	}

	public int getSupplyUsed() {
		return this.supplyUsed;
	}

	public int getSupplyTotal() {
		return this.supplyTotal;
	}

	public int getCumulativeMinerals() {
		return this.cumulativeMinerals;
	}

	public int getCumulativeGas() {
		return this.cumulativeGas;
	}

	public int getUnitScore() {
		return this.unitScore;
	}

	public int getKillScore() {
		return this.killScore;
	}

	public int getBuildingScore() {
		return this.buildingScore;
	}

	public int getRazingScore() {
		return this.razingScore;
	}

	public boolean isResearched(TechType tech) {
		return this.researched[tech.getID()];
	}

	public boolean isResearching(TechType tech) {
		return this.researching[tech.getID()];
	}

	public int getUpgradeLevel(UpgradeType upgrade) {
		return this.upgradeLevel[upgrade.getID()];
	}

	public boolean isUpgrading(UpgradeType upgrade) {
		return this.upgrading[upgrade.getID()];
	}

	@Override
	public int hashCode() {
		return this.ID;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Player other = (Player) obj;
		if (this.ID != other.ID) {
			return false;
		}
		return true;
	}
}
