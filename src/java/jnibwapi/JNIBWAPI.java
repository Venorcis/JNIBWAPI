package jnibwapi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import jnibwapi.types.BulletType;
import jnibwapi.types.BulletType.BulletTypes;
import jnibwapi.types.DamageType;
import jnibwapi.types.DamageType.DamageTypes;
import jnibwapi.types.EventType;
import jnibwapi.types.ExplosionType;
import jnibwapi.types.ExplosionType.ExplosionTypes;
import jnibwapi.types.OrderType;
import jnibwapi.types.OrderType.OrderTypes;
import jnibwapi.types.RaceType;
import jnibwapi.types.RaceType.RaceTypes;
import jnibwapi.types.TechType;
import jnibwapi.types.TechType.TechTypes;
import jnibwapi.types.UnitCommandType;
import jnibwapi.types.UnitCommandType.UnitCommandTypes;
import jnibwapi.types.UnitSizeType;
import jnibwapi.types.UnitSizeType.UnitSizeTypes;
import jnibwapi.types.UnitType;
import jnibwapi.types.UnitType.UnitTypes;
import jnibwapi.types.UpgradeType;
import jnibwapi.types.UpgradeType.UpgradeTypes;
import jnibwapi.types.WeaponType;
import jnibwapi.types.WeaponType.WeaponTypes;
import jnibwapi.util.BWColor;
import jnibwapi.util.ErrorCode;

/**
 * JNI interface for the Brood War API.<br>
 *
 * This focus of this interface is to provide the callback and game state query
 * functionality in BWAPI.<br>
 *
 * Note: for thread safety and game state sanity, all native calls should be
 * invoked from the callback methods.<br>
 *
 * For BWAPI documentation see: {@link http://code.google.com/p/bwapi/}<br>
 *
 * API Pages<br>
 * Game: {@link http://code.google.com/p/bwapi/wiki/Game}<br>
 * Unit: {@link http://code.google.com/p/bwapi/wiki/Unit}<br>
 */
public class JNIBWAPI {
	// load the BWAPI client library
	static {
		try {
			System.loadLibrary("client-bridge-" + System.getProperty("os.arch"));
			System.out.println("Loaded client bridge library.");
		} catch (UnsatisfiedLinkError e) {
			// Help beginners put the DLL in the correct place (although
			// anywhere on the path will
			// work)
			File dll = new File("client-bridge-" + System.getProperty("os.arch") + ".dll");
			if (!dll.exists()) {
				System.err.println("Native code library not found: " + dll.getAbsolutePath());
			}
			System.err.println("Native code library failed to load." + e);
		}
	}

	private static JNIBWAPI instance = null;

	/**
	 * Get a reference to the JNIBWAPI object. Note it will be unusable until
	 * the {@link #connected()} callback, and all game-related fields may be
	 * undefined until the {@link #gameStarted()} callback.
	 */
	public static JNIBWAPI getInstance() {
		return instance;
	}

	/** callback listener for BWAPI events */
	private final BWAPIEventListener listener;
	/** use BWTA for map analysis if not null */
	private final File BWTAdir;
	/** used charset (Korean usually) */
	private final Charset charset;

	/**
	 * Instantiates a BWAPI instance, but does not connect to the bridge. To
	 * connect, the start method must be invoked.
	 *
	 * @param listener
	 *            - listener for BWAPI callback events.
	 * @param BWTAdir
	 *            - use BWTA (with this folder) for map analysis if not null
	 */
	public JNIBWAPI(BWAPIEventListener listener, File BWTAdir) {
		instance = this;
		this.listener = listener;
		this.BWTAdir = BWTAdir;
		Charset charset = StandardCharsets.ISO_8859_1;
		try {
			// Using the Korean character set for decoding byte[]s into Strings
			// will allow Korean characters to be parsed correctly.
			charset = Charset.forName("Cp949");
		} catch (UnsupportedCharsetException e) {
			System.out.println("Korean character set not available. Some characters may not be read properly");
		}
		this.charset = charset;
	}

	/**
	 * Invokes the native library which will connect to the bridge and then
	 * invoke callback functions.
	 *
	 * Note: this method never returns, it should be invoked from a separate
	 * thread if concurrent java processing is needed.
	 */
	public void start() {
		startClient(this);
	}

	// game state
	private int gameFrame = 0;
	private Map map;
	private final HashMap<Integer, Unit> units = new HashMap<>();
	private final List<Unit> staticNeutralUnits = new LinkedList<>();
	private List<Unit> playerUnits = new LinkedList<>();
	private List<Unit> alliedUnits = new LinkedList<>();
	private List<Unit> enemyUnits = new LinkedList<>();
	private List<Unit> neutralUnits = new LinkedList<>();

	// player lists
	private Player self;
	private Player neutralPlayer;
	private final HashMap<Integer, Player> players = new HashMap<>();
	private final HashSet<Player> allies = new HashSet<>();
	private final HashSet<Player> enemies = new HashSet<>();

	// invokes the main native method
	private native void startClient(JNIBWAPI jniBWAPI);

	// query methods
	private native int getFrame();

	/** See https://code.google.com/p/bwapi/wiki/Game#getReplayFrameCount */
	public native int getReplayFrameTotal();

	private native int[] getPlayersData();

	private native int[] getPlayerUpdate(int playerID);

	/**
	 * Returns string as a byte[] to properly handle ASCII-extended characters
	 */
	private native byte[] getPlayerName(int playerID);

	private native int[] getResearchStatus(int playerID);

	private native int[] getUpgradeStatus(int playerID);

	private native int[] getAllUnitsData();

	private native int[] getStaticNeutralUnitsData();

	private native int[] getRaceTypes();

	private native String getRaceTypeName(int raceID);

	private native int[] getUnitTypes();

	private native String getUnitTypeName(int unitTypeID);

	private native int[] getRequiredUnits(int unitTypeID);

	private native int[] getTechTypes();

	private native String getTechTypeName(int techID);

	private native int[] getUpgradeTypes();

	private native String getUpgradeTypeName(int upgradeID);

	private native int[] getWeaponTypes();

	private native String getWeaponTypeName(int weaponID);

	private native int[] getUnitSizeTypes();

	private native String getUnitSizeTypeName(int sizeID);

	private native int[] getBulletTypes();

	private native String getBulletTypeName(int bulletID);

	private native int[] getDamageTypes();

	private native String getDamageTypeName(int damageID);

	private native int[] getExplosionTypes();

	private native String getExplosionTypeName(int explosionID);

	private native int[] getUnitCommandTypes();

	private native String getUnitCommandTypeName(int unitCommandID);

	private native int[] getOrderTypes();

	private native String getOrderTypeName(int unitCommandID);

	private native int[] getUnitIdsOnTile(int tx, int ty);

	// map data
	private native void analyzeTerrain();

	private native int getMapWidth();

	private native int getMapHeight();

	/**
	 * Returns string as a byte[] to properly handle ASCII-extended characters
	 */
	private native byte[] getMapName();

	private native String getMapFileName();

	private native String getMapHash();

	private native int[] getHeightData();

	/** Returns the regionId for each map tile */
	private native int[] getRegionMap();

	private native int[] getWalkableData();

	private native int[] getBuildableData();

	private native int[] getChokePoints();

	private native int[] getRegions();

	private native int[] getPolygon(int regionID);

	private native int[] getBaseLocations();

	// Unit commands. These should generally be accessed via the Unit class now.
	private native boolean canIssueCommand(int unitID, int unitCommandTypeID, int targetUnitID, int x, int y,
			int extra);

	/**
	 * Differs from the BWAPI command in that the Unit receiving the command is
	 * encapsulated in the UnitCommand. Otherwise matches:
	 * https://code.google.com/p/bwapi/wiki/Unit#canIssueCommand
	 */
	public boolean canIssueCommand(UnitCommand cmd) {
		return canIssueCommand(cmd.getUnit().getID(), cmd.getType().getID(), cmd.getTargetUnitID(), cmd.getX(),
				cmd.getY(), cmd.getExtra());
	}

	private native boolean issueCommand(int unitID, int unitCommandTypeID, int targetUnitID, int x, int y, int extra);

	/**
	 * Differs from the BWAPI command in that the Unit receiving the command is
	 * encapsulated in the UnitCommand. Otherwise matches:
	 * https://code.google.com/p/bwapi/wiki/Unit#issueCommand<br>
	 * It is preferable to use the command methods on the unit object instead.
	 */
	public boolean issueCommand(UnitCommand cmd) {
		return issueCommand(cmd.getUnit().getID(), cmd.getType().getID(), cmd.getTargetUnitID(), cmd.getX(), cmd.getY(),
				cmd.getExtra());
	}

	// utility commands
	/** Draw health boxes above units */
	public native void drawHealth(boolean enable);

	/** Draw the targets of each unit */
	public native void drawTargets(boolean enable);

	/** Draw the IDs of each unit */
	public native void drawIDs(boolean enable);

	/**
	 * Enable user input so the game can get information from the user (what
	 * units are selected, chat messages the user enters, etc). Note that this
	 * can only be enabled at the beginning of a match, during the
	 * {@link BWAPIEventListener#matchStart()} callback, or during the
	 * {@link BWAPIEventListener#matchFrame()} callback on the first frame
	 * (frame 0).
	 */
	public native void enableUserInput();

	/**
	 * Enable complete map information so all units are accessible, not just
	 * visible units. Note that this can only be enabled at the beginning of a
	 * match, during the {@link BWAPIEventListener#matchStart()} callback, or
	 * during the {@link BWAPIEventListener#matchFrame()} callback on the first
	 * frame (frame 0).
	 */
	public native void enablePerfectInformation();

	/** See https://code.google.com/p/bwapi/wiki/Game#setLocalSpeed */
	public native void setGameSpeed(int speed);

	/** See https://code.google.com/p/bwapi/wiki/Game#setFrameSkip */
	public native void setFrameSkip(int frameSkip);

	/** See https://code.google.com/p/bwapi/wiki/Game#leaveGame */
	public native void leaveGame();

	// draw commands (if screenCoords is false, draws at map pixel coordinates)
	private native void drawBox(int left, int top, int right, int bottom, int color, boolean fill,
			boolean screenCoords);

	/**
	 * See https://code.google.com/p/bwapi/wiki/Game#drawBox
	 *
	 * @param screenCoords
	 *            whether to draw at a screen position (true) or a map position
	 *            (false)
	 */
	public void drawBox(Position topLeft, Position bottomRight, BWColor bWColor, boolean fill, boolean screenCoords) {
		drawBox(topLeft.getPX(), topLeft.getPY(), bottomRight.getPX(), bottomRight.getPY(), bWColor.getID(), fill,
				screenCoords);
	}

	private native void drawCircle(int x, int y, int radius, int color, boolean fill, boolean screenCoords);

	/**
	 * See https://code.google.com/p/bwapi/wiki/Game#drawCircle
	 *
	 * @param screenCoords
	 *            whether to draw at a screen position (true) or a map position
	 *            (false)
	 */
	public void drawCircle(Position p, int radius, BWColor bWColor, boolean fill, boolean screenCoords) {
		drawCircle(p.getPX(), p.getPY(), radius, bWColor.getID(), fill, screenCoords);
	}

	private native void drawLine(int x1, int y1, int x2, int y2, int color, boolean screenCoords);

	/**
	 * See https://code.google.com/p/bwapi/wiki/Game#drawLine
	 *
	 * @param screenCoords
	 *            whether to draw at a screen position (true) or a map position
	 *            (false)
	 */
	public void drawLine(Position start, Position end, BWColor bWColor, boolean screenCoords) {
		drawLine(start.getPX(), start.getPY(), end.getPX(), end.getPY(), bWColor.getID(), screenCoords);
	}

	private native void drawDot(int x, int y, int color, boolean screenCoords);

	/**
	 * See https://code.google.com/p/bwapi/wiki/Game#drawDot
	 *
	 * @param screenCoords
	 *            whether to draw at a screen position (true) or a map position
	 *            (false)
	 */
	public void drawDot(Position p, BWColor bWColor, boolean screenCoords) {
		drawDot(p.getPX(), p.getPY(), bWColor.getID(), screenCoords);
	}

	private native void drawText(int x, int y, String msg, boolean screenCoords);

	/**
	 * See https://code.google.com/p/bwapi/wiki/Game#drawText
	 *
	 * @param screenCoords
	 *            whether to draw at a screen position (true) or a map position
	 *            (false)
	 */
	public void drawText(Position a, String msg, boolean screenCoords) {
		drawText(a.getPX(), a.getPY(), msg, screenCoords);
	}

	// Extended Commands
	private native boolean isVisible(int tileX, int tileY);

	/** See https://code.google.com/p/bwapi/wiki/Game#isVisible */
	public boolean isVisible(Position p) {
		return isVisible(p.getBX(), p.getBY());
	}

	private native boolean isExplored(int tileX, int tileY);

	/** See https://code.google.com/p/bwapi/wiki/Game#isExplored */
	public boolean isExplored(Position p) {
		return isExplored(p.getBX(), p.getBY());
	}

	private native boolean isBuildable(int tx, int ty, boolean includeBuildings);

	/** See https://code.google.com/p/bwapi/wiki/Game#isBuildable */
	public boolean isBuildable(Position p, boolean includeBuildings) {
		return isBuildable(p.getBX(), p.getBY(), includeBuildings);
	}

	private native boolean hasCreep(int tileX, int tileY);

	/** See https://code.google.com/p/bwapi/wiki/Game#hasCreep */
	public boolean hasCreep(Position p) {
		return hasCreep(p.getBX(), p.getBY());
	}

	private native boolean hasPower(int tileX, int tileY, int unitTypeID);

	/** See https://code.google.com/p/bwapi/wiki/Game#hasPower */
	public boolean hasPower(Position p) {
		return hasPower(p, UnitTypes.None);
	}

	/** See https://code.google.com/p/bwapi/wiki/Game#hasPower */
	public boolean hasPower(Position p, UnitType ut) {
		return hasPower(p.getBX(), p.getBY(), ut.getID());
	}

	private native boolean hasPower(int tileX, int tileY, int tileWidth, int tileHeight, int unitTypeID);

	/** See https://code.google.com/p/bwapi/wiki/Game#hasPower */
	public boolean hasPower(Position p, int tileWidth, int tileHeight) {
		return hasPower(p, tileWidth, tileHeight, UnitTypes.None);
	}

	/** See https://code.google.com/p/bwapi/wiki/Game#hasPower */
	public boolean hasPower(Position p, int tileWidth, int tileHeight, UnitType ut) {
		return hasPower(p.getBX(), p.getBY(), tileWidth, tileHeight, ut.getID());
	}

	private native boolean hasPowerPrecise(int x, int y, int unitTypeID);

	/** See https://code.google.com/p/bwapi/wiki/Game#hasPowerPrecise */
	public boolean hasPowerPrecise(Position p) {
		return hasPowerPrecise(p, UnitTypes.None);
	}

	/** See https://code.google.com/p/bwapi/wiki/Game#hasPowerPrecise */
	public boolean hasPowerPrecise(Position p, UnitType ut) {
		return hasPowerPrecise(p.getPX(), p.getPY(), ut.getID());
	}

	private native boolean hasPath(int fromX, int fromY, int toX, int toY);

	/** See https://code.google.com/p/bwapi/wiki/Game#hasPath */
	public boolean hasPath(Position from, Position to) {
		return hasPath(from.getPX(), from.getPY(), to.getPX(), to.getPY());
	}

	protected native boolean hasPath(int unitID, int targetID);

	protected native int[] getLoadedUnits(int unitID);

	protected native int[] getInterceptors(int unitID);

	protected native int[] getLarva(int unitID);

	private native boolean canBuildHere(int tileX, int tileY, int unitTypeID, boolean checkExplored);

	public boolean canBuildHere(Position p, UnitType ut, boolean checkExplored) {
		return canBuildHere(p.getBX(), p.getBY(), ut.getID(), checkExplored);
	}

	private native boolean canBuildHere(int unitID, int tileX, int tileY, int unitTypeID, boolean checkExplored);

	/** See https://code.google.com/p/bwapi/wiki/Game#canBuildHere */
	public boolean canBuildHere(Unit u, Position p, UnitType ut, boolean checkExplored) {
		return canBuildHere(u == null ? -1 : u.getID(), p.getBX(), p.getBY(), ut.getID(), checkExplored);
	}

	private native boolean canMake(int unitTypeID);

	/** See https://code.google.com/p/bwapi/wiki/Game#canMake */
	public boolean canMake(UnitType ut) {
		return canMake(ut.getID());
	}

	private native boolean canMake(int unitID, int unitTypeID);

	/** See https://code.google.com/p/bwapi/wiki/Game#canMake */
	public boolean canMake(Unit u, UnitType ut) {
		return canMake(u.getID(), ut.getID());
	}

	private native boolean canResearch(int techTypeID);

	/** See https://code.google.com/p/bwapi/wiki/Game#canResearch */
	public boolean canResearch(TechType tt) {
		return canResearch(tt.getID());
	}

	private native boolean canResearch(int unitID, int techTypeID);

	/** See https://code.google.com/p/bwapi/wiki/Game#canResearch */
	public boolean canResearch(Unit u, TechType tt) {
		return canResearch(u.getID(), tt.getID());
	}

	private native boolean canUpgrade(int upgradeTypeID);

	/** See https://code.google.com/p/bwapi/wiki/Game#canUpgrade */
	public boolean canUpgrade(UpgradeType ut) {
		return canUpgrade(ut.getID());
	}

	private native boolean canUpgrade(int unitID, int upgradeTypeID);

	/** See https://code.google.com/p/bwapi/wiki/Game#canUpgrade */
	public boolean canUpgrade(Unit u, UpgradeType ut) {
		return canUpgrade(u.getID(), ut.getID());
	}

	/** See https://code.google.com/p/bwapi/wiki/Game#printf */
	public native void printText(String message);

	/** See https://code.google.com/p/bwapi/wiki/Game#sendText */
	public native void sendText(String message);

	/** See https://code.google.com/p/bwapi/wiki/Game#setLatCom */
	public native void setLatCom(boolean enabled);

	/**
	 * See https://code.google.com/p/bwapi/wiki/Game#setCommandOptimizationLevel
	 */
	public native void setCommandOptimizationLevel(int level);

	/** See https://code.google.com/p/bwapi/wiki/Game#isReplay */
	public native boolean isReplay();

	protected native boolean isVisibleToPlayer(int unitID, int playerID);

	/**
	 * See https://code.google.com/p/bwapi/wiki/Game#getLastError<br>
	 * Error codes are defined in {@link ErrorCode}
	 */
	public native int getLastError();

	/**
	 * See https://code.google.com/p/bwapi/wiki/Game#getRemainingLatencyFrames
	 */
	public native int getRemainingLatencyFrames();

	// ID Lookup Methods (should not usually be needed)
	/** See https://code.google.com/p/bwapi/wiki/Game#getPlayer */
	public Player getPlayer(int playerID) {
		return this.players.get(playerID);
	}

	/** See https://code.google.com/p/bwapi/wiki/Game#getUnit */
	public Unit getUnit(int unitID) {
		return this.units.get(unitID);
	}

	// game state accessors
	/** See https://code.google.com/p/bwapi/wiki/Game#getFrameCount */
	public int getFrameCount() {
		return this.gameFrame;
	}

	/** See https://code.google.com/p/bwapi/wiki/Game#self */
	public Player getSelf() {
		return this.self;
	}

	/** See https://code.google.com/p/bwapi/wiki/Game#neutral */
	public Player getNeutralPlayer() {
		return this.neutralPlayer;
	}

	/** See https://code.google.com/p/bwapi/wiki/Game#getPlayers */
	public Collection<Player> getPlayers() {
		return Collections.unmodifiableCollection(this.players.values());
	}

	/** See https://code.google.com/p/bwapi/wiki/Game#allies */
	public Set<Player> getAllies() {
		return Collections.unmodifiableSet(this.allies);
	}

	/** See https://code.google.com/p/bwapi/wiki/Game#enemies */
	public Set<Player> getEnemies() {
		return Collections.unmodifiableSet(this.enemies);
	}

	/** See https://code.google.com/p/bwapi/wiki/Game#getAllUnits */
	public Collection<Unit> getAllUnits() {
		return Collections.unmodifiableCollection(this.units.values());
	}

	/** Retrieve the cached list of the current player's units */
	public List<Unit> getMyUnits() {
		return Collections.unmodifiableList(this.playerUnits);
	}

	/** Retrieve the cached list of allied players' visible units */
	public List<Unit> getAlliedUnits() {
		return Collections.unmodifiableList(this.alliedUnits);
	}

	/** Retrieve the cached list of enemy players' visible units */
	public List<Unit> getEnemyUnits() {
		return Collections.unmodifiableList(this.enemyUnits);
	}

	/**
	 * Retrieve the cached list of visible neutral units<br>
	 * See https://code.google.com/p/bwapi/wiki/Game#getNeutralUnits
	 */
	public List<Unit> getNeutralUnits() {
		return Collections.unmodifiableList(this.neutralUnits);
	}

	/**
	 * Retrieve the cached list of visible static neutral units<br>
	 * See https://code.google.com/p/bwapi/wiki/Game#getStaticNeutralUnits
	 */
	public List<Unit> getStaticNeutralUnits() {
		return Collections.unmodifiableList(this.staticNeutralUnits);
	}

	/** See https://code.google.com/p/bwapi/wiki/Game#getUnitsOnTile */
	public List<Unit> getUnitsOnTile(Position p) {
		List<Unit> units = new LinkedList<>();
		for (int id : getUnitIdsOnTile(p.getBX(), p.getBY())) {
			units.add(getUnit(id));
		}
		return units;
	}

	/**
	 * Returns the map.
	 */
	public Map getMap() {
		return this.map;
	}

	/**
	 * Loads type data from BWAPI.
	 */
	private void loadTypeData() {
		// race types
		int[] raceTypeData = getRaceTypes();
		for (int index = 0; index < raceTypeData.length; index += RaceType.numAttributes) {
			int id = raceTypeData[index];
			RaceTypes.getRaceType(id).initialize(raceTypeData, index, getRaceTypeName(id));
		}

		// unit types
		int[] unitTypeData = getUnitTypes();
		for (int index = 0; index < unitTypeData.length; index += UnitType.numAttributes) {
			int id = unitTypeData[index];
			UnitTypes.getUnitType(id).initialize(unitTypeData, index, getUnitTypeName(id), getRequiredUnits(id));
		}

		// tech types
		int[] techTypeData = getTechTypes();
		for (int index = 0; index < techTypeData.length; index += TechType.numAttributes) {
			int id = techTypeData[index];
			TechTypes.getTechType(id).initialize(techTypeData, index, getTechTypeName(id));
		}

		// upgrade types
		int[] upgradeTypeData = getUpgradeTypes();
		for (int index = 0; index < upgradeTypeData.length; index += UpgradeType.numAttributes) {
			int id = upgradeTypeData[index];
			UpgradeTypes.getUpgradeType(id).initialize(upgradeTypeData, index, getUpgradeTypeName(id));
		}

		// weapon types
		int[] weaponTypeData = getWeaponTypes();
		for (int index = 0; index < weaponTypeData.length; index += WeaponType.numAttributes) {
			int id = weaponTypeData[index];
			WeaponTypes.getWeaponType(id).initialize(weaponTypeData, index, getWeaponTypeName(id));
		}

		// unit size types
		int[] unitSizeTypeData = getUnitSizeTypes();
		for (int index = 0; index < unitSizeTypeData.length; index += UnitSizeType.numAttributes) {
			int id = unitSizeTypeData[index];
			UnitSizeTypes.getUnitSizeType(id).initialize(unitSizeTypeData, index, getUnitSizeTypeName(id));
		}

		// bullet types
		int[] bulletTypeData = getBulletTypes();
		for (int index = 0; index < bulletTypeData.length; index += BulletType.numAttributes) {
			int id = bulletTypeData[index];
			BulletTypes.getBulletType(id).initialize(bulletTypeData, index, getBulletTypeName(id));
		}

		// damage types
		int[] damageTypeData = getDamageTypes();
		for (int index = 0; index < damageTypeData.length; index += DamageType.numAttributes) {
			int id = damageTypeData[index];
			DamageTypes.getDamageType(id).initialize(damageTypeData, index, getDamageTypeName(id));
		}

		// explosion types
		int[] explosionTypeData = getExplosionTypes();
		for (int index = 0; index < explosionTypeData.length; index += ExplosionType.numAttributes) {
			int id = explosionTypeData[index];
			ExplosionTypes.getExplosionType(id).initialize(explosionTypeData, index, getExplosionTypeName(id));
		}

		// unitCommand types
		int[] unitCommandTypeData = getUnitCommandTypes();
		for (int index = 0; index < unitCommandTypeData.length; index += UnitCommandType.numAttributes) {
			int id = unitCommandTypeData[index];
			UnitCommandTypes.getUnitCommandType(id).initialize(unitCommandTypeData, index, getUnitCommandTypeName(id));
		}

		// order types
		int[] orderTypeData = getOrderTypes();
		for (int index = 0; index < orderTypeData.length; index += OrderType.numAttributes) {
			int id = orderTypeData[index];
			OrderTypes.getOrderType(id).initialize(orderTypeData, index, getOrderTypeName(id));
		}

		// event types - no extra data to load
	}

	/**
	 * Loads map data and (if enableBWTA is true) BWTA data.
	 *
	 * TODO: figure out how to use BWTA's internal map storage
	 */
	private void loadMapData() {
		String mapName = new String(getMapName(), this.charset);
		this.map = new Map(getMapWidth(), getMapHeight(), mapName, getMapFileName(), getMapHash(), getHeightData(),
				getBuildableData(), getWalkableData());
		if (this.BWTAdir == null) {
			return;
		}

		// get region and choke point data
		File bwtaFile = new File(this.BWTAdir.getPath() + File.separator + this.map.getHash() + ".jbwta");
		String mapHash = this.map.getHash();
		File mapDir = bwtaFile.getParentFile();
		if (mapDir != null) {
			mapDir.mkdirs();
		}
		boolean analyzed = bwtaFile.exists();
		int[] regionMapData = null;
		int[] regionData = null;
		int[] chokePointData = null;
		int[] baseLocationData = null;
		HashMap<Integer, int[]> polygons = new HashMap<>();

		// run BWTA
		if (!analyzed) {
			analyzeTerrain();
			regionMapData = getRegionMap();
			regionData = getRegions();
			chokePointData = getChokePoints();
			baseLocationData = getBaseLocations();
			for (int index = 0; index < regionData.length; index += Region.numAttributes) {
				int id = regionData[index];
				polygons.put(id, getPolygon(id));
			}

			// sometimes BWTA seems to crash on analyse. Make sure we are
			// definitely in the same map
			if (!mapHash.equals(this.map.getHash())) {
				System.err.println("Error: Map changed during analysis! BWTA file not saved.");
				System.exit(1);
			}

			// store the results to a local file (bwta directory)
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(bwtaFile));

				writeMapData(writer, regionMapData);
				writeMapData(writer, regionData);
				writeMapData(writer, chokePointData);
				writeMapData(writer, baseLocationData);
				for (int id : polygons.keySet()) {
					writer.write("" + id + ",");
					writeMapData(writer, polygons.get(id));
				}

				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// load from file
		else {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(bwtaFile));

				regionMapData = readMapData(reader);
				regionData = readMapData(reader);
				chokePointData = readMapData(reader);
				baseLocationData = readMapData(reader);
				// polygons (first integer is ID)
				int[] polygonData;
				while ((polygonData = readMapData(reader)) != null) {
					int[] coordinateData = Arrays.copyOfRange(polygonData, 1, polygonData.length);

					polygons.put(polygonData[0], coordinateData);
				}

				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.map.initialize(regionMapData, regionData, polygons, chokePointData, baseLocationData);
	}

	/**
	 * Convenience method to write out each part of BWTA map data to a stream
	 */
	private static void writeMapData(BufferedWriter writer, int[] data) throws IOException {
		boolean first = true;
		for (int val : data) {
			if (first) {
				first = false;
				writer.write("" + val);
			} else {
				writer.write("," + val);
			}
		}
		writer.write("\n");
	}

	/**
	 * Convenience method to read each part of BWTA map data from a stream
	 *
	 * @return null when end of stream is reached, otherwise an int array
	 *         (possibly empty)
	 */
	private static int[] readMapData(BufferedReader reader) throws IOException {
		int[] data = new int[0];
		String line = reader.readLine();
		if (line == null) {
			return null;
		}
		String[] stringData = line.split(",");
		if (stringData.length > 0 && !stringData[0].equals("")) {
			data = new int[stringData.length];
			for (int i = 0; i < stringData.length; i++) {
				data[i] = Integer.parseInt(stringData[i]);
			}
		}
		return data;
	}

	/**
	 * C++ callback function.<br>
	 *
	 * Utility function for printing to the java console from C++.
	 */
	private void javaPrint(String msg) {
		try {
			System.out.println("Bridge: " + msg);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * C++ callback function.<br>
	 *
	 * Notifies the client and event listener that a connection has been formed
	 * to the bridge.
	 */
	private void connected() {
		try {
			loadTypeData();
			this.listener.connected();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * C++ callback function.<br>
	 *
	 * Notifies the client that a game has started. Not passed on to the event
	 * listener.<br>
	 *
	 * Note: this is always called before the matchStarted event, and is meant
	 * as a way of notifying the AI client to clear up state.
	 */
	private void gameStarted() {
		try {
			// get the players
			this.self = null;
			this.allies.clear();
			this.enemies.clear();
			this.players.clear();

			int[] playerData = getPlayersData();
			for (int index = 0; index < playerData.length; index += Player.numAttributes) {
				String name = new String(getPlayerName(playerData[index]), this.charset);
				Player player = new Player(playerData, index, name);

				this.players.put(player.getID(), player);

				if (player.isSelf()) {
					this.self = player;
				} else if (player.isAlly()) {
					this.allies.add(player);
				} else if (player.isEnemy()) {
					this.enemies.add(player);
				} else if (player.isNeutral()) {
					this.neutralPlayer = player;
				}
			}

			// get unit data
			this.units.clear();
			this.playerUnits.clear();
			this.alliedUnits.clear();
			this.enemyUnits.clear();
			this.neutralUnits.clear();
			int[] unitData = getAllUnitsData();

			for (int index = 0; index < unitData.length; index += Unit.numAttributes) {
				int id = unitData[index];
				Unit unit = new Unit(id, this);
				unit.update(unitData, index);

				this.units.put(id, unit);
				if (this.self != null && unit.getPlayer() == this.self) {
					this.playerUnits.add(unit);
				} else if (this.allies.contains(unit.getPlayer())) {
					this.alliedUnits.add(unit);
				} else if (this.enemies.contains(unit.getPlayer())) {
					this.enemyUnits.add(unit);
				} else {
					this.neutralUnits.add(unit);
				}
			}
			this.staticNeutralUnits.clear();
			unitData = getStaticNeutralUnitsData();
			for (int index = 0; index < unitData.length; index += Unit.numAttributes) {
				int id = unitData[index];

				// Ensure we don't have duplicate units
				Unit unit = this.units.get(id);
				if (unit == null) {
					unit = new Unit(id, this);
					unit.update(unitData, index);
				}

				this.staticNeutralUnits.add(unit);
			}

			this.gameFrame = getFrame();
			loadMapData();

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * C++ callback function.<br>
	 *
	 * Notifies the client that game data has been updated. Not passed on to the
	 * event listener.<br>
	 *
	 * Note: this is always called before the events each frame, and is meant as
	 * a way of notifying the AI client to update state.
	 */
	private void gameUpdate() {
		try {
			// update game state
			this.gameFrame = getFrame();
			if (!isReplay()) {
				this.self.update(getPlayerUpdate(this.self.getID()));
				this.self.updateResearch(getResearchStatus(this.self.getID()), getUpgradeStatus(this.self.getID()));
			} else {
				for (Integer playerID : this.players.keySet()) {
					this.players.get(playerID).update(getPlayerUpdate(playerID));
					this.players.get(playerID).updateResearch(getResearchStatus(playerID), getUpgradeStatus(playerID));
				}
			}
			// update units
			int[] unitData = getAllUnitsData();
			Set<Integer> deadUnits = new HashSet<>(this.units.keySet());
			List<Unit> playerList = new LinkedList<>();
			List<Unit> alliedList = new LinkedList<>();
			List<Unit> enemyList = new LinkedList<>();
			List<Unit> neutralList = new LinkedList<>();
			for (int index = 0; index < unitData.length; index += Unit.numAttributes) {
				int id = unitData[index];
				deadUnits.remove(id);

				Unit unit = this.units.get(id);
				if (unit == null) {
					unit = new Unit(id, this);
					this.units.put(id, unit);
				}

				unit.update(unitData, index);

				if (this.self != null) {
					if (unit.getPlayer() == this.self) {
						playerList.add(unit);
					} else if (this.allies.contains(unit.getPlayer())) {
						alliedList.add(unit);
					} else if (this.enemies.contains(unit.getPlayer())) {
						enemyList.add(unit);
					} else {
						neutralList.add(unit);
					}
				} else if (this.allies.contains(unit.getPlayer())) {
					alliedList.add(unit);
				} else if (this.enemies.contains(unit.getPlayer())) {
					enemyList.add(unit);
				} else {
					neutralList.add(unit);
				}
			}

			// update the unit lists
			this.playerUnits = playerList;
			this.alliedUnits = alliedList;
			this.enemyUnits = enemyList;
			this.neutralUnits = neutralList;
			for (Integer unitID : deadUnits) {
				this.units.get(unitID).setDestroyed();
				this.units.remove(unitID);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * C++ callback function.<br>
	 *
	 * Notifies the event listener that the game has terminated.<br>
	 *
	 * Note: this is always called after the matchEnded event, and is meant as a
	 * way of notifying the AI client to clear up state.
	 */
	private void gameEnded() {
	}

	/**
	 * C++ callback function.<br>
	 *
	 * Sends BWAPI callback events to the event listener.
	 */
	private void eventOccurred(int eventTypeID, int param1, int param2, String param3) {
		try {
			EventType event = EventType.getEventType(eventTypeID);
			switch (event) {
			case MatchStart:
				this.listener.matchStart();
				break;
			case MatchEnd:
				this.listener.matchEnd(param1 == 1);
				break;
			case MatchFrame:
				this.listener.matchFrame();
				break;
			case MenuFrame:
				// Unused?
				break;
			case SendText:
				this.listener.sendText(param3);
				break;
			case ReceiveText:
				this.listener.receiveText(param3);
				break;
			case PlayerLeft:
				this.listener.playerLeft(param1);
				break;
			case NukeDetect:
				if (param1 == -1) {
					this.listener.nukeDetect();
				} else {
					this.listener.nukeDetect(new Position(param1, param2));
				}
				break;
			case UnitDiscover:
				this.listener.unitDiscover(param1);
				break;
			case UnitEvade:
				this.listener.unitEvade(param1);
				break;
			case UnitShow:
				this.listener.unitShow(param1);
				break;
			case UnitHide:
				this.listener.unitHide(param1);
				break;
			case UnitCreate:
				this.listener.unitCreate(param1);
				break;
			case UnitDestroy:
				this.listener.unitDestroy(param1);
				break;
			case UnitMorph:
				this.listener.unitMorph(param1);
				break;
			case UnitRenegade:
				this.listener.unitRenegade(param1);
				break;
			case SaveGame:
				this.listener.saveGame(param3);
				break;
			case UnitComplete:
				this.listener.unitComplete(param1);
				break;
			case PlayerDropped:
				this.listener.playerDropped(param1);
				break;
			case None:
				// Unused?
				break;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * C++ callback function.<br>
	 *
	 * Notifies the event listener that a key was pressed.
	 */
	public void keyPressed(int keyCode) {
		try {
			this.listener.keyPressed(keyCode);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
