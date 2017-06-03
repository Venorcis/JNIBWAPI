package jnibwapi;

import java.util.LinkedList;
import java.util.List;

import jnibwapi.Position.PosType;
import jnibwapi.types.OrderType;
import jnibwapi.types.OrderType.OrderTypes;
import jnibwapi.types.TechType;
import jnibwapi.types.TechType.TechTypes;
import jnibwapi.types.UnitCommandType;
import jnibwapi.types.UnitCommandType.UnitCommandTypes;
import jnibwapi.types.UnitType;
import jnibwapi.types.UnitType.UnitTypes;
import jnibwapi.types.UpgradeType;
import jnibwapi.types.UpgradeType.UpgradeTypes;

/**
 * Represents a StarCraft unit.
 *
 * For a description of fields see: http://code.google.com/p/bwapi/wiki/Unit
 */
public class Unit implements Cloneable {
	public static final int numAttributes = 123;
	public static final double TO_DEGREES = 180.0 / Math.PI;
	public static final double fixedScale = 100.0;

	private final JNIBWAPI bwapi;
	private final int ID;
	private int replayID;
	private int playerID;
	private int typeID;
	private int x;
	private int y;
	private int tileX;
	private int tileY;
	private double angle;
	private double velocityX;
	private double velocityY;
	private int hitPoints;
	private int shield;
	private int energy;
	private int resources;
	private int resourceGroup;
	private int lastCommandFrame;
	private int lastCommandID;
	private int lastAttackingPlayerID;
	private int initialTypeID;
	private int initialX;
	private int initialY;
	private int initialHitPoints;
	private int initialResources;
	private int killCount;
	private int acidSporeCount;
	private int interceptorCount;
	private int scarabCount;
	private int spiderMineCount;
	private int groundWeaponCooldown;
	private int airWeaponCooldown;
	private int spellCooldown;
	private int defenseMatrixPoints;
	private int defenseMatrixTimer;
	private int ensnareTimer;
	private int irradiateTimer;
	private int lockdownTimer;
	private int maelstromTimer;
	private int orderTimer;
	private int plagueTimer;
	private int removeTimer;
	private int stasisTimer;
	private int stimTimer;
	private int buildTypeID;
	private int trainingQueueSize;
	private int researchingTechID;
	private int upgradingUpgradeID;
	private int remainingBuildTimer;
	private int remainingTrainTime;
	private int remainingResearchTime;
	private int remainingUpgradeTime;
	private int buildUnitID;
	private int targetUnitID;
	private int targetX;
	private int targetY;
	private int orderID;
	private int orderTargetID;
	private int secondaryOrderID;
	private int rallyX;
	private int rallyY;
	private int rallyUnitID;
	private int addOnID;
	private int nydusExitUnitID;
	private int transportID;
	private int carrierUnitID;
	private int larvaCount;
	private int hatcheryUnitID;
	private int powerUpUnitID;
	private boolean exists;
	private boolean nukeReady;
	private boolean accelerating;
	private boolean attacking;
	private boolean attackFrame;
	private boolean beingConstructed;
	private boolean beingGathered;
	private boolean beingHealed;
	private boolean blind;
	private boolean braking;
	private boolean burrowed;
	private boolean carryingGas;
	private boolean carryingMinerals;
	private boolean cloaked;
	private boolean completed;
	private boolean constructing;
	private boolean defenseMatrixed;
	private boolean detected;
	private boolean ensnared;
	private boolean following;
	private boolean gatheringGas;
	private boolean gatheringMinerals;
	private boolean hallucination;
	private boolean holdingPosition;
	private boolean idle;
	private boolean interruptable;
	private boolean invincible;
	private boolean irradiated;
	private boolean lifted;
	private boolean loaded;
	private boolean lockedDown;
	private boolean maelstrommed;
	private boolean morphing;
	private boolean moving;
	private boolean parasited;
	private boolean patrolling;
	private boolean plagued;
	private boolean repairing;
	private boolean selected;
	private boolean sieged;
	private boolean startingAttack;
	private boolean stasised;
	private boolean stimmed;
	private boolean stuck;
	private boolean training;
	private boolean underAttack;
	private boolean underDarkSwarm;
	private boolean underDisruptionWeb;
	private boolean underStorm;
	private boolean unpowered;
	private boolean upgrading;
	private boolean visible;

	public Unit(int ID, JNIBWAPI bwapi) {
		this.ID = ID;
		this.bwapi = bwapi;
	}

	public void setDestroyed() {
		this.exists = false;
	}

	public void update(int[] data, int index) {
		if (this.ID != data[index++]) {
			throw new IllegalArgumentException();
		}
		this.replayID = data[index++];
		this.playerID = data[index++];
		this.typeID = data[index++];
		this.x = data[index++];
		this.y = data[index++];
		this.tileX = data[index++];
		this.tileY = data[index++];
		this.angle = (data[index++] / TO_DEGREES);
		this.velocityX = (data[index++] / fixedScale);
		this.velocityY = (data[index++] / fixedScale);
		this.hitPoints = data[index++];
		this.shield = data[index++];
		this.energy = data[index++];
		this.resources = data[index++];
		this.resourceGroup = data[index++];
		this.lastCommandFrame = data[index++];
		this.lastCommandID = data[index++];
		this.lastAttackingPlayerID = data[index++];
		this.initialTypeID = data[index++];
		this.initialX = data[index++];
		this.initialY = data[index++];
		index++; // initialTileX
		index++; // initialTileY
		this.initialHitPoints = data[index++];
		this.initialResources = data[index++];
		this.killCount = data[index++];
		this.acidSporeCount = data[index++];
		this.interceptorCount = data[index++];
		this.scarabCount = data[index++];
		this.spiderMineCount = data[index++];
		this.groundWeaponCooldown = data[index++];
		this.airWeaponCooldown = data[index++];
		this.spellCooldown = data[index++];
		this.defenseMatrixPoints = data[index++];
		this.defenseMatrixTimer = data[index++];
		this.ensnareTimer = data[index++];
		this.irradiateTimer = data[index++];
		this.lockdownTimer = data[index++];
		this.maelstromTimer = data[index++];
		this.orderTimer = data[index++];
		this.plagueTimer = data[index++];
		this.removeTimer = data[index++];
		this.stasisTimer = data[index++];
		this.stimTimer = data[index++];
		this.buildTypeID = data[index++];
		this.trainingQueueSize = data[index++];
		this.researchingTechID = data[index++];
		this.upgradingUpgradeID = data[index++];
		this.remainingBuildTimer = data[index++];
		this.remainingTrainTime = data[index++];
		this.remainingResearchTime = data[index++];
		this.remainingUpgradeTime = data[index++];
		this.buildUnitID = data[index++];
		this.targetUnitID = data[index++];
		this.targetX = data[index++];
		this.targetY = data[index++];
		this.orderID = data[index++];
		this.orderTargetID = data[index++];
		this.secondaryOrderID = data[index++];
		this.rallyX = data[index++];
		this.rallyY = data[index++];
		this.rallyUnitID = data[index++];
		this.addOnID = data[index++];
		this.nydusExitUnitID = data[index++];
		this.transportID = data[index++];
		index++; // loadedUnitsCount
		this.carrierUnitID = data[index++];
		this.hatcheryUnitID = data[index++];
		this.larvaCount = data[index++];
		this.powerUpUnitID = data[index++];
		this.exists = (data[index++] == 1);
		this.nukeReady = (data[index++] == 1);
		this.accelerating = (data[index++] == 1);
		this.attacking = (data[index++] == 1);
		this.attackFrame = (data[index++] == 1);
		this.beingConstructed = (data[index++] == 1);
		this.beingGathered = (data[index++] == 1);
		this.beingHealed = (data[index++] == 1);
		this.blind = (data[index++] == 1);
		this.braking = (data[index++] == 1);
		this.burrowed = (data[index++] == 1);
		this.carryingGas = (data[index++] == 1);
		this.carryingMinerals = (data[index++] == 1);
		this.cloaked = (data[index++] == 1);
		this.completed = (data[index++] == 1);
		this.constructing = (data[index++] == 1);
		this.defenseMatrixed = (data[index++] == 1);
		this.detected = (data[index++] == 1);
		this.ensnared = (data[index++] == 1);
		this.following = (data[index++] == 1);
		this.gatheringGas = (data[index++] == 1);
		this.gatheringMinerals = (data[index++] == 1);
		this.hallucination = (data[index++] == 1);
		this.holdingPosition = (data[index++] == 1);
		this.idle = (data[index++] == 1);
		this.interruptable = (data[index++] == 1);
		this.invincible = (data[index++] == 1);
		this.irradiated = (data[index++] == 1);
		this.lifted = (data[index++] == 1);
		this.loaded = (data[index++] == 1);
		this.lockedDown = (data[index++] == 1);
		this.maelstrommed = (data[index++] == 1);
		this.morphing = (data[index++] == 1);
		this.moving = (data[index++] == 1);
		this.parasited = (data[index++] == 1);
		this.patrolling = (data[index++] == 1);
		this.plagued = (data[index++] == 1);
		this.repairing = (data[index++] == 1);
		this.selected = (data[index++] == 1);
		this.sieged = (data[index++] == 1);
		this.startingAttack = (data[index++] == 1);
		this.stasised = (data[index++] == 1);
		this.stimmed = (data[index++] == 1);
		this.stuck = (data[index++] == 1);
		this.training = (data[index++] == 1);
		this.underAttack = (data[index++] == 1);
		this.underDarkSwarm = (data[index++] == 1);
		this.underDisruptionWeb = (data[index++] == 1);
		this.underStorm = (data[index++] == 1);
		this.unpowered = (data[index++] == 1);
		this.upgrading = (data[index++] == 1);
		this.visible = (data[index++] == 1);
	}

	@Override
	public Unit clone() {
		/*
		 * Safe to use clone for this class because it has only primitive fields
		 * and a reference to BWAPI, which should be shallow-copied. Beware when
		 * using equals or == with cloned Units as they will be considered equal
		 * (and not ==) regardless of any changes in their properties over time.
		 */
		try {
			return (Unit) super.clone();
		} catch (CloneNotSupportedException e) {
			// Should never happen, as this implements Cloneable and extends
			// Object
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the edge-to-edge distance between the current unit and the target
	 * unit.
	 */
	public double getDistance(Unit target) {
		if (!isExists() || target == null || !target.isExists()) {
			return Integer.MAX_VALUE;
		}

		if (this == target) {
			return 0;
		}

		int xDist = getLeft() - (target.getRight() + 1);
		if (xDist < 0) {
			xDist = target.getLeft() - (getRight() + 1);
			if (xDist < 0) {
				xDist = 0;
			}
		}
		int yDist = getTop() - (target.getBottom() + 1);
		if (yDist < 0) {
			yDist = target.getTop() - (getBottom() + 1);
			if (yDist < 0) {
				yDist = 0;
			}
		}
		return new Position(0, 0).getPDistance(new Position(xDist, yDist));
	}

	/**
	 * Returns the distance from the edge of the current unit to the target
	 * position.
	 */
	public double getDistance(Position target) {
		if (!isExists()) {
			return Integer.MAX_VALUE;
		}
		int xDist = getLeft() - (target.getPX() + 1);
		if (xDist < 0) {
			xDist = target.getPX() - (getRight() + 1);
			if (xDist < 0) {
				xDist = 0;
			}
		}
		int yDist = getTop() - (target.getPY() + 1);
		if (yDist < 0) {
			yDist = target.getPY() - (getBottom() + 1);
			if (yDist < 0) {
				yDist = 0;
			}
		}
		return new Position(0, 0).getPDistance(new Position(xDist, yDist));
	}

	/** The top left corner of the unit's collision boundary. */
	public Position getTopLeft() {
		return new Position(getLeft(), getTop());
	}

	/** The bottom right corner of the unit's collision boundary. */
	public Position getBottomRight() {
		return new Position(getRight(), getBottom());
	}

	private int getLeft() {
		return this.x - getType().getDimensionLeft();
	}

	private int getTop() {
		return this.y - getType().getDimensionUp();
	}

	private int getRight() {
		return this.x + getType().getDimensionRight();
	}

	private int getBottom() {
		return this.y + getType().getDimensionDown();
	}

	// ------------------------------ FIELD ACCESSOR METHODS
	// ------------------------------ //

	public int getID() {
		return this.ID;
	}

	public int getReplayID() {
		return this.replayID;
	}

	public Player getPlayer() {
		return this.bwapi.getPlayer(this.playerID);
	}

	public UnitType getType() {
		return UnitTypes.getUnitType(this.typeID);
	}

	/** Gives the position of the <b>center</b> of the unit. */
	public Position getPosition() {
		return new Position(this.x, this.y);
	}

	/**
	 * Returns the position of the top-left build tile occupied by the unit.
	 * Most useful for buildings. Always above-left of {@link #getPosition()}
	 * and above-left or equal to {@link #getTopLeft()}
	 */
	public Position getTilePosition() {
		return new Position(this.tileX, this.tileY, PosType.BUILD);
	}

	public double getAngle() {
		return this.angle;
	}

	public double getVelocityX() {
		return this.velocityX;
	}

	public double getVelocityY() {
		return this.velocityY;
	}

	public int getHitPoints() {
		return this.hitPoints;
	}

	public int getShields() {
		return this.shield;
	}

	public int getEnergy() {
		return this.energy;
	}

	public int getResources() {
		return this.resources;
	}

	public int getResourceGroup() {
		return this.resourceGroup;
	}

	public boolean hasPath(Unit target) {
		return this.bwapi.hasPath(getID(), target.getID());
	}

	public boolean hasPath(Position target) {
		return this.bwapi.hasPath(getPosition(), target);
	}

	public int getLastCommandFrame() {
		return this.lastCommandFrame;
	}

	public UnitCommandType getLastCommand() {
		return UnitCommandTypes.getUnitCommandType(this.lastCommandID);
	}

	public Player getLastAttackingPlayer() {
		return this.bwapi.getPlayer(this.lastAttackingPlayerID);
	}

	public UnitType getInitialType() {
		return UnitTypes.getUnitType(this.initialTypeID);
	}

	public Position getInitialPosition() {
		return new Position(this.initialX, this.initialY);
	}

	public int getInitialHitPoints() {
		return this.initialHitPoints;
	}

	public int getInitialResources() {
		return this.initialResources;
	}

	public int getKillCount() {
		return this.killCount;
	}

	public int getAcidSporeCount() {
		return this.acidSporeCount;
	}

	public int getInterceptorCount() {
		return this.interceptorCount;
	}

	public List<Unit> getInterceptors() {
		List<Unit> interceptors = new LinkedList<>();
		for (int id : this.bwapi.getInterceptors(this.ID)) {
			interceptors.add(this.bwapi.getUnit(id));
		}
		return interceptors;
	}

	public int getScarabCount() {
		return this.scarabCount;
	}

	public int getSpiderMineCount() {
		return this.spiderMineCount;
	}

	public int getGroundWeaponCooldown() {
		return this.groundWeaponCooldown;
	}

	public int getAirWeaponCooldown() {
		return this.airWeaponCooldown;
	}

	public int getSpellCooldown() {
		return this.spellCooldown;
	}

	public int getDefenseMatrixPoints() {
		return this.defenseMatrixPoints;
	}

	public int getDefenseMatrixTimer() {
		return this.defenseMatrixTimer;
	}

	public int getEnsnareTimer() {
		return this.ensnareTimer;
	}

	public int getIrradiateTimer() {
		return this.irradiateTimer;
	}

	public int getLockdownTimer() {
		return this.lockdownTimer;
	}

	public int getMaelstromTimer() {
		return this.maelstromTimer;
	}

	public int getOrderTimer() {
		return this.orderTimer;
	}

	public int getPlagueTimer() {
		return this.plagueTimer;
	}

	public int getRemoveTimer() {
		return this.removeTimer;
	}

	public int getStasisTimer() {
		return this.stasisTimer;
	}

	public int getStimTimer() {
		return this.stimTimer;
	}

	public UnitType getBuildType() {
		return UnitTypes.getUnitType(this.buildTypeID);
	}

	public int getTrainingQueueSize() {
		return this.trainingQueueSize;
	}

	public TechType getTech() {
		return TechTypes.getTechType(this.researchingTechID);
	}

	public UpgradeType getUpgrade() {
		return UpgradeTypes.getUpgradeType(this.upgradingUpgradeID);
	}

	public int getRemainingBuildTimer() {
		return this.remainingBuildTimer;
	}

	public int getRemainingTrainTime() {
		return this.remainingTrainTime;
	}

	public int getRemainingResearchTime() {
		return this.remainingResearchTime;
	}

	public int getRemainingUpgradeTime() {
		return this.remainingUpgradeTime;
	}

	public Unit getBuildUnit() {
		return this.bwapi.getUnit(this.buildUnitID);
	}

	public Unit getTarget() {
		return this.bwapi.getUnit(this.targetUnitID);
	}

	public Position getTargetPosition() {
		return new Position(this.targetX, this.targetY);
	}

	public OrderType getOrder() {
		return OrderTypes.getOrderType(this.orderID);
	}

	public Unit getOrderTarget() {
		return this.bwapi.getUnit(this.orderTargetID);
	}

	public OrderType getSecondaryOrder() {
		return OrderTypes.getOrderType(this.secondaryOrderID);
	}

	public Position getRallyPosition() {
		return new Position(this.rallyX, this.rallyY);
	}

	public Unit getRallyUnit() {
		return this.bwapi.getUnit(this.rallyUnitID);
	}

	public Unit getAddon() {
		return this.bwapi.getUnit(this.addOnID);
	}

	public Unit getNydusExit() {
		return this.bwapi.getUnit(this.nydusExitUnitID);
	}

	public Unit getTransport() {
		return this.bwapi.getUnit(this.transportID);
	}

	public List<Unit> getLoadedUnits() {
		List<Unit> units = new LinkedList<>();
		for (int id : this.bwapi.getLoadedUnits(this.ID)) {
			units.add(this.bwapi.getUnit(id));
		}
		return units;
	}

	public Unit getCarrier() {
		return this.bwapi.getUnit(this.carrierUnitID);
	}

	public Unit getHatchery() {
		return this.bwapi.getUnit(this.hatcheryUnitID);
	}

	public int getLarvaCount() {
		return this.larvaCount;
	}

	public List<Unit> getLarva() {
		List<Unit> larva = new LinkedList<>();
		for (int id : this.bwapi.getLarva(this.ID)) {
			larva.add(this.bwapi.getUnit(id));
		}
		return larva;
	}

	public Unit getPowerUp() {
		return this.bwapi.getUnit(this.powerUpUnitID);
	}

	public boolean isExists() {
		return this.exists;
	}

	public boolean isNukeReady() {
		return this.nukeReady;
	}

	public boolean isAccelerating() {
		return this.accelerating;
	}

	public boolean isAttacking() {
		return this.attacking;
	}

	public boolean isAttackFrame() {
		return this.attackFrame;
	}

	public boolean isBeingConstructed() {
		return this.beingConstructed;
	}

	public boolean isBeingGathered() {
		return this.beingGathered;
	}

	public boolean isBeingHealed() {
		return this.beingHealed;
	}

	public boolean isBlind() {
		return this.blind;
	}

	public boolean isBraking() {
		return this.braking;
	}

	public boolean isBurrowed() {
		return this.burrowed;
	}

	public boolean isCarryingGas() {
		return this.carryingGas;
	}

	public boolean isCarryingMinerals() {
		return this.carryingMinerals;
	}

	public boolean isCloaked() {
		return this.cloaked;
	}

	public boolean isCompleted() {
		return this.completed;
	}

	public boolean isConstructing() {
		return this.constructing;
	}

	public boolean isDefenseMatrixed() {
		return this.defenseMatrixed;
	}

	public boolean isDetected() {
		return this.detected;
	}

	public boolean isEnsnared() {
		return this.ensnared;
	}

	public boolean isFollowing() {
		return this.following;
	}

	public boolean isGatheringGas() {
		return this.gatheringGas;
	}

	public boolean isGatheringMinerals() {
		return this.gatheringMinerals;
	}

	public boolean isHallucination() {
		return this.hallucination;
	}

	public boolean isHoldingPosition() {
		return this.holdingPosition;
	}

	public boolean isIdle() {
		return this.idle;
	}

	public boolean isInterruptable() {
		return this.interruptable;
	}

	public boolean isInvincible() {
		return this.invincible;
	}

	public boolean isIrradiated() {
		return this.irradiated;
	}

	public boolean isLifted() {
		return this.lifted;
	}

	public boolean isLoaded() {
		return this.loaded;
	}

	public boolean isLockedDown() {
		return this.lockedDown;
	}

	public boolean isMaelstrommed() {
		return this.maelstrommed;
	}

	public boolean isMorphing() {
		return this.morphing;
	}

	public boolean isMoving() {
		return this.moving;
	}

	public boolean isParasited() {
		return this.parasited;
	}

	public boolean isPatrolling() {
		return this.patrolling;
	}

	public boolean isPlagued() {
		return this.plagued;
	}

	public boolean isRepairing() {
		return this.repairing;
	}

	public boolean isSelected() {
		return this.selected;
	}

	public boolean isSieged() {
		return this.sieged;
	}

	public boolean isStartingAttack() {
		return this.startingAttack;
	}

	public boolean isStasised() {
		return this.stasised;
	}

	public boolean isStimmed() {
		return this.stimmed;
	}

	public boolean isStuck() {
		return this.stuck;
	}

	public boolean isTraining() {
		return this.training;
	}

	public boolean isUnderAttack() {
		return this.underAttack;
	}

	public boolean isUnderDarkSwarm() {
		return this.underDarkSwarm;
	}

	public boolean isUnderDisruptionWeb() {
		return this.underDisruptionWeb;
	}

	public boolean isUnderStorm() {
		return this.underStorm;
	}

	public boolean isUnpowered() {
		return this.unpowered;
	}

	public boolean isUpgrading() {
		return this.upgrading;
	}

	public boolean isVisible() {
		return this.visible;
	}

	public boolean isVisible(Player p) {
		return this.bwapi.isVisibleToPlayer(this.getID(), p.getID());
	}

	// ------------------------------ UNIT COMMANDS
	// ------------------------------ //
	/**
	 * Included for completeness to match BWAPI. Preferable to use
	 * {@link JNIBWAPI#canIssueCommand(UnitCommand)} directly.
	 */
	public boolean canIssueCommand(UnitCommand cmd) {
		if (!this.equals(cmd.getUnit())) {
			throw new IllegalArgumentException("Unit Command is for a different Unit");
		}
		return this.bwapi.canIssueCommand(cmd);
	}

	/**
	 * Included for completeness to match BWAPI. Preferable to use other unit
	 * commands or {@link JNIBWAPI#issueCommand(UnitCommand)} directly.
	 */
	public boolean issueCommand(UnitCommand cmd) {
		if (!this.equals(cmd.getUnit())) {
			throw new IllegalArgumentException("Unit Command is for a different Unit");
		}
		return this.bwapi.issueCommand(cmd);
	}

	public boolean attack(Position p, boolean queued) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Attack_Move, p, queued));
	}

	public boolean attack(Unit target, boolean queued) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Attack_Unit, target, queued));
	}

	public boolean build(Position p, UnitType type) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Build, p, type.getID()));
	}

	public boolean buildAddon(UnitType type) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Build_Addon, type.getID()));
	}

	public boolean train(UnitType type) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Train, type.getID()));
	}

	public boolean morph(UnitType type) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Morph, type.getID()));
	}

	public boolean research(TechType tech) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Research, tech.getID()));
	}

	public boolean upgrade(UpgradeType upgrade) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Upgrade, upgrade.getID()));
	}

	public boolean setRallyPoint(Position p) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Set_Rally_Position, p));
	}

	public boolean setRallyPoint(Unit target) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Set_Rally_Unit, target));
	}

	public boolean move(Position p, boolean queued) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Move, p, queued));
	}

	public boolean patrol(Position p, boolean queued) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Patrol, p, queued));
	}

	public boolean holdPosition(boolean queued) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Hold_Position, queued));
	}

	public boolean stop(boolean queued) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Stop, queued));
	}

	public boolean follow(Unit target, boolean queued) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Follow, target, queued));
	}

	public boolean gather(Unit target, boolean queued) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Gather, target, queued));
	}

	public boolean returnCargo(boolean queued) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Return_Cargo, queued));
	}

	public boolean repair(Unit target, boolean queued) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Repair, target, queued));
	}

	public boolean burrow() {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Burrow));
	}

	public boolean unburrow() {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Unburrow));
	}

	public boolean cloak() {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Cloak));
	}

	public boolean decloak() {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Decloak));
	}

	public boolean siege() {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Siege));
	}

	public boolean unsiege() {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Unsiege));
	}

	public boolean lift() {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Lift));
	}

	public boolean land(Position p) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Land, p));
	}

	public boolean load(Unit target, boolean queued) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Load, target, queued));
	}

	public boolean unload(Unit target) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Unload, target));
	}

	public boolean unloadAll(boolean queued) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Unload_All, queued));
	}

	public boolean unloadAll(Position p, boolean queued) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Unload_All_Position, p, queued));
	}

	public boolean rightClick(Position p, boolean queued) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Right_Click_Position, p, queued));
	}

	public boolean rightClick(Unit target, boolean queued) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Right_Click_Unit, target, queued));
	}

	public boolean haltConstruction() {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Halt_Construction));
	}

	public boolean cancelConstruction() {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Cancel_Construction));
	}

	public boolean cancelAddon() {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Cancel_Addon));
	}

	public boolean cancelTrain(int slot) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Cancel_Train_Slot, slot));
	}

	/** Remove the last unit from the training queue. */
	public boolean cancelTrain() {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Cancel_Train, -2));
	}

	public boolean cancelMorph() {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Cancel_Morph));
	}

	public boolean cancelResearch() {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Cancel_Research));
	}

	public boolean cancelUpgrade() {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Cancel_Upgrade));
	}

	public boolean useTech(TechType tech) {
		UnitCommandType uct = UnitCommandTypes.Use_Tech;
		if (tech == TechTypes.Burrowing) {
			if (isBurrowed()) {
				uct = UnitCommandTypes.Unburrow;
			} else {
				uct = UnitCommandTypes.Burrow;
			}
		} else if (tech == TechTypes.Cloaking_Field || tech == TechTypes.Personnel_Cloaking) {
			if (isCloaked()) {
				uct = UnitCommandTypes.Decloak;
			} else {
				uct = UnitCommandTypes.Cloak;
			}
		} else if (tech == TechTypes.Tank_Siege_Mode) {
			if (isSieged()) {
				uct = UnitCommandTypes.Unsiege;
			} else {
				uct = UnitCommandTypes.Siege;
			}
		}
		return this.bwapi.issueCommand(new UnitCommand(this, uct, tech.getID()));
	}

	public boolean useTech(TechType tech, Position p) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Use_Tech_Position, p, tech.getID()));
	}

	public boolean useTech(TechType tech, Unit target) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Use_Tech_Unit, target, tech.getID()));
	}

	public boolean placeCOP(Position p) {
		return this.bwapi.issueCommand(new UnitCommand(this, UnitCommandTypes.Place_COP, p));
	}

	// ------------------------------ HASHCODE & EQUALS
	// ------------------------------ //

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
		Unit other = (Unit) obj;
		if (this.ID != other.ID) {
			return false;
		}
		return true;
	}
}
