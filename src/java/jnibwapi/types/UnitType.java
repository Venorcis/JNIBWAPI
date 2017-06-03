package jnibwapi.types;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jnibwapi.types.UnitSizeType.UnitSizeTypes;
import jnibwapi.types.WeaponType.WeaponTypes;

/**
 * Represents a StarCraft unit type.
 *
 * For a description of fields see: http://code.google.com/p/bwapi/wiki/UnitType
 */
public class UnitType {
	private static Map<Integer, UnitType> idToUnitType = new HashMap<>();

	public static class UnitTypes {
		public static final UnitType Terran_Marine = new UnitType(0);
		public static final UnitType Terran_Ghost = new UnitType(1);
		public static final UnitType Terran_Vulture = new UnitType(2);
		public static final UnitType Terran_Goliath = new UnitType(3);
		// 4 Goliath Turret
		public static final UnitType Terran_Siege_Tank_Tank_Mode = new UnitType(5);
		// 6 Siege Tank Turret (Tank Mode)
		public static final UnitType Terran_SCV = new UnitType(7);
		public static final UnitType Terran_Wraith = new UnitType(8);
		public static final UnitType Terran_Science_Vessel = new UnitType(9);
		public static final UnitType Hero_Gui_Montag = new UnitType(10);
		public static final UnitType Terran_Dropship = new UnitType(11);
		public static final UnitType Terran_Battlecruiser = new UnitType(12);
		public static final UnitType Terran_Vulture_Spider_Mine = new UnitType(13);
		public static final UnitType Terran_Nuclear_Missile = new UnitType(14);
		public static final UnitType Terran_Civilian = new UnitType(15);
		public static final UnitType Hero_Sarah_Kerrigan = new UnitType(16);
		public static final UnitType Hero_Alan_Schezar = new UnitType(17);
		// 18 Alan Schezar Turret
		public static final UnitType Hero_Jim_Raynor_Vulture = new UnitType(19);
		public static final UnitType Hero_Jim_Raynor_Marine = new UnitType(20);
		public static final UnitType Hero_Tom_Kazansky = new UnitType(21);
		public static final UnitType Hero_Magellan = new UnitType(22);
		public static final UnitType Hero_Edmund_Duke_Tank_Mode = new UnitType(23);
		// 24 Edmund Duke Turret (Tank Mode)
		public static final UnitType Hero_Edmund_Duke_Siege_Mode = new UnitType(25);
		// 26 Edmund Duke Turret (Siege Mode)
		public static final UnitType Hero_Arcturus_Mengsk = new UnitType(27);
		public static final UnitType Hero_Hyperion = new UnitType(28);
		public static final UnitType Hero_Norad_II = new UnitType(29);
		public static final UnitType Terran_Siege_Tank_Siege_Mode = new UnitType(30);
		// 31 Siege Tank Turret (Siege Mode)
		public static final UnitType Terran_Firebat = new UnitType(32);
		public static final UnitType Spell_Scanner_Sweep = new UnitType(33);
		public static final UnitType Terran_Medic = new UnitType(34);
		public static final UnitType Zerg_Larva = new UnitType(35);
		public static final UnitType Zerg_Egg = new UnitType(36);
		public static final UnitType Zerg_Zergling = new UnitType(37);
		public static final UnitType Zerg_Hydralisk = new UnitType(38);
		public static final UnitType Zerg_Ultralisk = new UnitType(39);
		public static final UnitType Zerg_Broodling = new UnitType(40);
		public static final UnitType Zerg_Drone = new UnitType(41);
		public static final UnitType Zerg_Overlord = new UnitType(42);
		public static final UnitType Zerg_Mutalisk = new UnitType(43);
		public static final UnitType Zerg_Guardian = new UnitType(44);
		public static final UnitType Zerg_Queen = new UnitType(45);
		public static final UnitType Zerg_Defiler = new UnitType(46);
		public static final UnitType Zerg_Scourge = new UnitType(47);
		public static final UnitType Hero_Torrasque = new UnitType(48);
		public static final UnitType Hero_Matriarch = new UnitType(49);
		public static final UnitType Zerg_Infested_Terran = new UnitType(50);
		public static final UnitType Hero_Infested_Kerrigan = new UnitType(51);
		public static final UnitType Hero_Unclean_One = new UnitType(52);
		public static final UnitType Hero_Hunter_Killer = new UnitType(53);
		public static final UnitType Hero_Devouring_One = new UnitType(54);
		public static final UnitType Hero_Kukulza_Mutalisk = new UnitType(55);
		public static final UnitType Hero_Kukulza_Guardian = new UnitType(56);
		public static final UnitType Hero_Yggdrasill = new UnitType(57);
		public static final UnitType Terran_Valkyrie = new UnitType(58);
		public static final UnitType Zerg_Cocoon = new UnitType(59);
		public static final UnitType Protoss_Corsair = new UnitType(60);
		public static final UnitType Protoss_Dark_Templar = new UnitType(61);
		public static final UnitType Zerg_Devourer = new UnitType(62);
		public static final UnitType Protoss_Dark_Archon = new UnitType(63);
		public static final UnitType Protoss_Probe = new UnitType(64);
		public static final UnitType Protoss_Zealot = new UnitType(65);
		public static final UnitType Protoss_Dragoon = new UnitType(66);
		public static final UnitType Protoss_High_Templar = new UnitType(67);
		public static final UnitType Protoss_Archon = new UnitType(68);
		public static final UnitType Protoss_Shuttle = new UnitType(69);
		public static final UnitType Protoss_Scout = new UnitType(70);
		public static final UnitType Protoss_Arbiter = new UnitType(71);
		public static final UnitType Protoss_Carrier = new UnitType(72);
		public static final UnitType Protoss_Interceptor = new UnitType(73);
		public static final UnitType Hero_Dark_Templar = new UnitType(74);
		public static final UnitType Hero_Zeratul = new UnitType(75);
		public static final UnitType Hero_Tassadar_Zeratul_Archon = new UnitType(76);
		public static final UnitType Hero_Fenix_Zealot = new UnitType(77);
		public static final UnitType Hero_Fenix_Dragoon = new UnitType(78);
		public static final UnitType Hero_Tassadar = new UnitType(79);
		public static final UnitType Hero_Mojo = new UnitType(80);
		public static final UnitType Hero_Warbringer = new UnitType(81);
		public static final UnitType Hero_Gantrithor = new UnitType(82);
		public static final UnitType Protoss_Reaver = new UnitType(83);
		public static final UnitType Protoss_Observer = new UnitType(84);
		public static final UnitType Protoss_Scarab = new UnitType(85);
		public static final UnitType Hero_Danimoth = new UnitType(86);
		public static final UnitType Hero_Aldaris = new UnitType(87);
		public static final UnitType Hero_Artanis = new UnitType(88);
		public static final UnitType Critter_Rhynadon = new UnitType(89);
		public static final UnitType Critter_Bengalaas = new UnitType(90);
		public static final UnitType Special_Cargo_Ship = new UnitType(91);
		public static final UnitType Special_Mercenary_Gunship = new UnitType(92);
		public static final UnitType Critter_Scantid = new UnitType(93);
		public static final UnitType Critter_Kakaru = new UnitType(94);
		public static final UnitType Critter_Ragnasaur = new UnitType(95);
		public static final UnitType Critter_Ursadon = new UnitType(96);
		public static final UnitType Zerg_Lurker_Egg = new UnitType(97);
		public static final UnitType Hero_Raszagal = new UnitType(98);
		public static final UnitType Hero_Samir_Duran = new UnitType(99);
		public static final UnitType Hero_Alexei_Stukov = new UnitType(100);
		public static final UnitType Special_Map_Revealer = new UnitType(101);
		public static final UnitType Hero_Gerard_DuGalle = new UnitType(102);
		public static final UnitType Zerg_Lurker = new UnitType(103);
		public static final UnitType Hero_Infested_Duran = new UnitType(104);
		public static final UnitType Spell_Disruption_Web = new UnitType(105);
		public static final UnitType Terran_Command_Center = new UnitType(106);
		public static final UnitType Terran_Comsat_Station = new UnitType(107);
		public static final UnitType Terran_Nuclear_Silo = new UnitType(108);
		public static final UnitType Terran_Supply_Depot = new UnitType(109);
		public static final UnitType Terran_Refinery = new UnitType(110);
		public static final UnitType Terran_Barracks = new UnitType(111);
		public static final UnitType Terran_Academy = new UnitType(112);
		public static final UnitType Terran_Factory = new UnitType(113);
		public static final UnitType Terran_Starport = new UnitType(114);
		public static final UnitType Terran_Control_Tower = new UnitType(115);
		public static final UnitType Terran_Science_Facility = new UnitType(116);
		public static final UnitType Terran_Covert_Ops = new UnitType(117);
		public static final UnitType Terran_Physics_Lab = new UnitType(118);
		// 119 Starbase (Unused)
		public static final UnitType Terran_Machine_Shop = new UnitType(120);
		// 121 Repair Bay (Unused)
		public static final UnitType Terran_Engineering_Bay = new UnitType(122);
		public static final UnitType Terran_Armory = new UnitType(123);
		public static final UnitType Terran_Missile_Turret = new UnitType(124);
		public static final UnitType Terran_Bunker = new UnitType(125);
		public static final UnitType Special_Crashed_Norad_II = new UnitType(126);
		public static final UnitType Special_Ion_Cannon = new UnitType(127);
		public static final UnitType Powerup_Uraj_Crystal = new UnitType(128);
		public static final UnitType Powerup_Khalis_Crystal = new UnitType(129);
		public static final UnitType Zerg_Infested_Command_Center = new UnitType(130);
		public static final UnitType Zerg_Hatchery = new UnitType(131);
		public static final UnitType Zerg_Lair = new UnitType(132);
		public static final UnitType Zerg_Hive = new UnitType(133);
		public static final UnitType Zerg_Nydus_Canal = new UnitType(134);
		public static final UnitType Zerg_Hydralisk_Den = new UnitType(135);
		public static final UnitType Zerg_Defiler_Mound = new UnitType(136);
		public static final UnitType Zerg_Greater_Spire = new UnitType(137);
		public static final UnitType Zerg_Queens_Nest = new UnitType(138);
		public static final UnitType Zerg_Evolution_Chamber = new UnitType(139);
		public static final UnitType Zerg_Ultralisk_Cavern = new UnitType(140);
		public static final UnitType Zerg_Spire = new UnitType(141);
		public static final UnitType Zerg_Spawning_Pool = new UnitType(142);
		public static final UnitType Zerg_Creep_Colony = new UnitType(143);
		public static final UnitType Zerg_Spore_Colony = new UnitType(144);
		// 144 Unused Zerg Building 1
		public static final UnitType Zerg_Sunken_Colony = new UnitType(146);
		public static final UnitType Special_Overmind_With_Shell = new UnitType(147);
		public static final UnitType Special_Overmind = new UnitType(148);
		public static final UnitType Zerg_Extractor = new UnitType(149);
		public static final UnitType Special_Mature_Chrysalis = new UnitType(150);
		public static final UnitType Special_Cerebrate = new UnitType(151);
		public static final UnitType Special_Cerebrate_Daggoth = new UnitType(152);
		// 153 Unused Zerg Building 2
		public static final UnitType Protoss_Nexus = new UnitType(154);
		public static final UnitType Protoss_Robotics_Facility = new UnitType(155);
		public static final UnitType Protoss_Pylon = new UnitType(156);
		public static final UnitType Protoss_Assimilator = new UnitType(157);
		// 158 Unused Protoss Building 1
		public static final UnitType Protoss_Observatory = new UnitType(159);
		public static final UnitType Protoss_Gateway = new UnitType(160);
		// 161 Unused Protoss Building 2
		public static final UnitType Protoss_Photon_Cannon = new UnitType(162);
		public static final UnitType Protoss_Citadel_of_Adun = new UnitType(163);
		public static final UnitType Protoss_Cybernetics_Core = new UnitType(164);
		public static final UnitType Protoss_Templar_Archives = new UnitType(165);
		public static final UnitType Protoss_Forge = new UnitType(166);
		public static final UnitType Protoss_Stargate = new UnitType(167);
		public static final UnitType Special_Stasis_Cell_Prison = new UnitType(168);
		public static final UnitType Protoss_Fleet_Beacon = new UnitType(169);
		public static final UnitType Protoss_Arbiter_Tribunal = new UnitType(170);
		public static final UnitType Protoss_Robotics_Support_Bay = new UnitType(171);
		public static final UnitType Protoss_Shield_Battery = new UnitType(172);
		public static final UnitType Special_Khaydarin_Crystal_Form = new UnitType(173);
		public static final UnitType Special_Protoss_Temple = new UnitType(174);
		public static final UnitType Special_XelNaga_Temple = new UnitType(175);
		public static final UnitType Resource_Mineral_Field = new UnitType(176);
		public static final UnitType Resource_Mineral_Field_Type_2 = new UnitType(177);
		public static final UnitType Resource_Mineral_Field_Type_3 = new UnitType(178);
		// 179 Cave (Unused)
		// 180 Cave-in (Unused)
		// 181 Cantina (Unused)
		// 182 Mining Platform (Unused)
		// 183 Independent Command Center (Unused)
		public static final UnitType Special_Independant_Starport = new UnitType(184);
		// 185 Independent Jump Gate (Unused)
		// 186 Ruins (Unused)
		// 187 Khaydarin Crystal Formation (Unused)
		public static final UnitType Resource_Vespene_Geyser = new UnitType(188);
		public static final UnitType Special_Warp_Gate = new UnitType(189);
		public static final UnitType Special_Psi_Disrupter = new UnitType(190);
		// 191 Zerg Marker (Unused)
		// 192 Terran Marker (Unused)
		// 193 Protoss Marker (Unused)
		public static final UnitType Special_Zerg_Beacon = new UnitType(194);
		public static final UnitType Special_Terran_Beacon = new UnitType(195);
		public static final UnitType Special_Protoss_Beacon = new UnitType(196);
		public static final UnitType Special_Zerg_Flag_Beacon = new UnitType(197);
		public static final UnitType Special_Terran_Flag_Beacon = new UnitType(198);
		public static final UnitType Special_Protoss_Flag_Beacon = new UnitType(199);
		public static final UnitType Special_Power_Generator = new UnitType(200);
		public static final UnitType Special_Overmind_Cocoon = new UnitType(201);
		public static final UnitType Spell_Dark_Swarm = new UnitType(202);
		public static final UnitType Special_Floor_Missile_Trap = new UnitType(203);
		public static final UnitType Special_Floor_Hatch = new UnitType(204);
		public static final UnitType Special_Upper_Level_Door = new UnitType(205);
		public static final UnitType Special_Right_Upper_Level_Door = new UnitType(206);
		public static final UnitType Special_Pit_Door = new UnitType(207);
		public static final UnitType Special_Right_Pit_Door = new UnitType(208);
		public static final UnitType Special_Floor_Gun_Trap = new UnitType(209);
		public static final UnitType Special_Wall_Missile_Trap = new UnitType(210);
		public static final UnitType Special_Wall_Flame_Trap = new UnitType(211);
		public static final UnitType Special_Right_Wall_Missile_Trap = new UnitType(212);
		public static final UnitType Special_Right_Wall_Flame_Trap = new UnitType(213);
		public static final UnitType Special_Start_Location = new UnitType(214);
		public static final UnitType Powerup_Flag = new UnitType(215);
		public static final UnitType Powerup_Young_Chrysalis = new UnitType(216);
		public static final UnitType Powerup_Psi_Emitter = new UnitType(217);
		public static final UnitType Powerup_Data_Disk = new UnitType(218);
		public static final UnitType Powerup_Khaydarin_Crystal = new UnitType(219);
		public static final UnitType Powerup_Mineral_Cluster_Type_1 = new UnitType(220);
		public static final UnitType Powerup_Mineral_Cluster_Type_2 = new UnitType(221);
		public static final UnitType Powerup_Protoss_Gas_Orb_Type_1 = new UnitType(222);
		public static final UnitType Powerup_Protoss_Gas_Orb_Type_2 = new UnitType(223);
		public static final UnitType Powerup_Zerg_Gas_Sac_Type_1 = new UnitType(224);
		public static final UnitType Powerup_Zerg_Gas_Sac_Type_2 = new UnitType(225);
		public static final UnitType Powerup_Terran_Gas_Tank_Type_1 = new UnitType(226);
		public static final UnitType Powerup_Terran_Gas_Tank_Type_2 = new UnitType(227);
		public static final UnitType None = new UnitType(228);
		// 229 All Units (BWAPI4)
		// 230 Men (BWAPI4)
		// 231 Buildings (BWAPI4)
		// 232 Factories (BWAPI4)
		public static final UnitType Unknown = new UnitType(233);

		public static UnitType getUnitType(int id) {
			UnitType type = idToUnitType.get(id);
			return (type == null) ? Unknown : type;
		}

		public static Collection<UnitType> getAllUnitTypes() {
			return Collections.unmodifiableCollection(idToUnitType.values());
		}
	}

	public static final int numAttributes = 57;
	public static final double fixedScale = 100.0;
	private final int ID;
	private int raceID;
	private int whatBuildID;
	private int requiredTechID;
	private int armorUpgradeID;
	private int maxHitPoints;
	private int maxShields;
	private int maxEnergy;
	private int armor;
	private int mineralPrice;
	private int gasPrice;
	private int buildTime;
	private int supplyRequired;
	private int supplyProvided;
	private int spaceRequired;
	private int spaceProvided;
	private int buildScore;
	private int destroyScore;
	private int sizeID;
	private int tileWidth;
	private int tileHeight;
	private int dimensionLeft;
	private int dimensionUp;
	private int dimensionRight;
	private int dimensionDown;
	private int seekRange;
	private int sightRange;
	private int groundWeaponID;
	private int maxGroundHits;
	private int airWeaponID;
	private int maxAirHits;
	private double topSpeed;
	private int acceleration;
	private int haltDistance;
	private int turnRadius;
	private boolean produceCapable;
	private boolean attackCapable;
	private boolean canMove;
	private boolean flyer;
	private boolean regenerates;
	private boolean spellcaster;
	private boolean invincible;
	private boolean organic;
	private boolean mechanical;
	private boolean robotic;
	private boolean detector;
	private boolean resourceContainer;
	private boolean refinery;
	private boolean worker;
	private boolean requiresPsi;
	private boolean requiresCreep;
	private boolean burrowable;
	private boolean cloakable;
	private boolean building;
	private boolean addon;
	private boolean flyingBuilding;
	private boolean spell;
	private String name;
	private final Map<Integer, Integer> requiredUnits = new HashMap<>();

	private UnitType(int ID) {
		this.ID = ID;
		idToUnitType.put(ID, this);
	}

	public void initialize(int[] data, int index, String name, int[] requiredUnits) {
		if (this.ID != data[index++]) {
			throw new IllegalArgumentException();
		}
		this.raceID = data[index++];
		this.whatBuildID = data[index++];
		this.requiredTechID = data[index++];
		this.armorUpgradeID = data[index++];
		this.maxHitPoints = data[index++];
		this.maxShields = data[index++];
		this.maxEnergy = data[index++];
		this.armor = data[index++];
		this.mineralPrice = data[index++];
		this.gasPrice = data[index++];
		this.buildTime = data[index++];
		this.supplyRequired = data[index++];
		this.supplyProvided = data[index++];
		this.spaceRequired = data[index++];
		this.spaceProvided = data[index++];
		this.buildScore = data[index++];
		this.destroyScore = data[index++];
		this.sizeID = data[index++];
		this.tileWidth = data[index++];
		this.tileHeight = data[index++];
		this.dimensionLeft = data[index++];
		this.dimensionUp = data[index++];
		this.dimensionRight = data[index++];
		this.dimensionDown = data[index++];
		this.seekRange = data[index++];
		this.sightRange = data[index++];
		this.groundWeaponID = data[index++];
		this.maxGroundHits = data[index++];
		this.airWeaponID = data[index++];
		this.maxAirHits = data[index++];
		this.topSpeed = (data[index++] / fixedScale);
		this.acceleration = data[index++];
		this.haltDistance = data[index++];
		this.turnRadius = data[index++];
		this.produceCapable = (data[index++] == 1);
		this.attackCapable = (data[index++] == 1);
		this.canMove = (data[index++] == 1);
		this.flyer = (data[index++] == 1);
		this.regenerates = (data[index++] == 1);
		this.spellcaster = (data[index++] == 1);
		this.invincible = (data[index++] == 1);
		this.organic = (data[index++] == 1);
		this.mechanical = (data[index++] == 1);
		this.robotic = (data[index++] == 1);
		this.detector = (data[index++] == 1);
		this.resourceContainer = (data[index++] == 1);
		this.refinery = (data[index++] == 1);
		this.worker = (data[index++] == 1);
		this.requiresPsi = (data[index++] == 1);
		this.requiresCreep = (data[index++] == 1);
		this.burrowable = (data[index++] == 1);
		this.cloakable = (data[index++] == 1);
		this.building = (data[index++] == 1);
		this.addon = (data[index++] == 1);
		this.flyingBuilding = (data[index++] == 1);
		this.spell = (data[index++] == 1);
		this.name = name;
		for (int i = 0; i < requiredUnits.length; i += 2) {
			this.requiredUnits.put(requiredUnits[i], requiredUnits[i + 1]);
		}
	}

	public int getID() {
		return this.ID;
	}

	public int getRaceID() {
		return this.raceID;
	}

	public int getWhatBuildID() {
		return this.whatBuildID;
	}

	public int getRequiredTechID() {
		return this.requiredTechID;
	}

	public int getArmorUpgradeID() {
		return this.armorUpgradeID;
	}

	public int getMaxHitPoints() {
		return this.maxHitPoints;
	}

	public int getMaxShields() {
		return this.maxShields;
	}

	public int getMaxEnergy() {
		return this.maxEnergy;
	}

	public int getArmor() {
		return this.armor;
	}

	public int getMineralPrice() {
		return this.mineralPrice;
	}

	public int getGasPrice() {
		return this.gasPrice;
	}

	public int getBuildTime() {
		return this.buildTime;
	}

	public int getSupplyRequired() {
		return this.supplyRequired;
	}

	public int getSupplyProvided() {
		return this.supplyProvided;
	}

	public int getSpaceRequired() {
		return this.spaceRequired;
	}

	public int getSpaceProvided() {
		return this.spaceProvided;
	}

	public int getBuildScore() {
		return this.buildScore;
	}

	public int getDestroyScore() {
		return this.destroyScore;
	}

	public UnitSizeType getSize() {
		return UnitSizeTypes.getUnitSizeType(this.sizeID);
	}

	public int getTileWidth() {
		return this.tileWidth;
	}

	public int getTileHeight() {
		return this.tileHeight;
	}

	public int getDimensionLeft() {
		return this.dimensionLeft;
	}

	public int getDimensionUp() {
		return this.dimensionUp;
	}

	public int getDimensionRight() {
		return this.dimensionRight;
	}

	public int getDimensionDown() {
		return this.dimensionDown;
	}

	public int getSeekRange() {
		return this.seekRange;
	}

	public int getSightRange() {
		return this.sightRange;
	}

	public WeaponType getGroundWeapon() {
		return WeaponTypes.getWeaponType(this.groundWeaponID);
	}

	public int getMaxGroundHits() {
		return this.maxGroundHits;
	}

	public int getAirWeaponID() {
		return this.airWeaponID;
	}

	public int getMaxAirHits() {
		return this.maxAirHits;
	}

	public double getTopSpeed() {
		return this.topSpeed;
	}

	public int getAcceleration() {
		return this.acceleration;
	}

	public int getHaltDistance() {
		return this.haltDistance;
	}

	public int getTurnRadius() {
		return this.turnRadius;
	}

	public boolean isProduceCapable() {
		return this.produceCapable;
	}

	public boolean isAttackCapable() {
		return this.attackCapable;
	}

	public boolean isCanMove() {
		return this.canMove;
	}

	public boolean isFlyer() {
		return this.flyer;
	}

	public boolean isRegenerates() {
		return this.regenerates;
	}

	public boolean isSpellcaster() {
		return this.spellcaster;
	}

	public boolean isInvincible() {
		return this.invincible;
	}

	public boolean isOrganic() {
		return this.organic;
	}

	public boolean isMechanical() {
		return this.mechanical;
	}

	public boolean isRobotic() {
		return this.robotic;
	}

	public boolean isDetector() {
		return this.detector;
	}

	public boolean isResourceContainer() {
		return this.resourceContainer;
	}

	public boolean isRefinery() {
		return this.refinery;
	}

	public boolean isWorker() {
		return this.worker;
	}

	public boolean isRequiresPsi() {
		return this.requiresPsi;
	}

	public boolean isRequiresCreep() {
		return this.requiresCreep;
	}

	public boolean isBurrowable() {
		return this.burrowable;
	}

	public boolean isCloakable() {
		return this.cloakable;
	}

	public boolean isBuilding() {
		return this.building;
	}

	public boolean isAddon() {
		return this.addon;
	}

	public boolean isFlyingBuilding() {
		return this.flyingBuilding;
	}

	public boolean isSpell() {
		return this.spell;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * A map from UnitTypeID to quantity required (usually 1, but 2 for Archons)
	 */
	public Map<Integer, Integer> getRequiredUnits() {
		return Collections.unmodifiableMap(this.requiredUnits);
	}

	public boolean isMineralField() {
		return this == UnitTypes.Resource_Mineral_Field || this == UnitTypes.Resource_Mineral_Field_Type_2
				|| this == UnitTypes.Resource_Mineral_Field_Type_3;
	}

	@Override
	public String toString() {
		return getName() + " (" + getID() + ")";
	}
}
