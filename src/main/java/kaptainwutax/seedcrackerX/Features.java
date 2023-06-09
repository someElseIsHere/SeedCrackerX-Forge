package kaptainwutax.seedcrackerX;

import com.seedfinding.mccore.state.Dimension;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mcfeature.decorator.DesertWell;
import com.seedfinding.mcfeature.decorator.EndGateway;
import com.seedfinding.mcfeature.structure.*;
import kaptainwutax.seedcrackerX.cracker.decorator.Dungeon;
import kaptainwutax.seedcrackerX.cracker.decorator.EmeraldOre;
import kaptainwutax.seedcrackerX.cracker.decorator.WarpedFungus;

public class Features {

	public static BastionRemnant BASTION_REMNANT;
	public static BuriedTreasure BURIED_TREASURE;
	public static DesertPyramid DESERT_PYRAMID;
	public static EndCity END_CITY;
	public static Fortress FORTRESS;
	public static Igloo IGLOO;
	public static JunglePyramid JUNGLE_PYRAMID;
	public static Mansion MANSION;
	public static Mineshaft MINESHAFT;
	public static Monument MONUMENT;
	public static NetherFossil NETHER_FOSSIL;
	public static OceanRuin OCEAN_RUIN;
	public static PillagerOutpost PILLAGER_OUTPOST;
	public static RuinedPortal RUINED_PORTAL_OVERWORLD;
	public static RuinedPortal RUINED_PORTAL_NETHER;
	public static Shipwreck SHIPWRECK;
	public static Stronghold STRONGHOLD;
	public static SwampHut SWAMP_HUT;
	public static Village VILLAGE;

	public static EndGateway END_GATEWAY;
	public static DesertWell DESERT_WELL;
	public static EmeraldOre EMERALD_ORE;
	public static Dungeon DUNGEON;
	public static WarpedFungus WARPED_FUNGUS;
	
	public static void init(MCVersion version) {
		safe(() -> BASTION_REMNANT = new BastionRemnant(version));
		safe(() -> BURIED_TREASURE = new BuriedTreasure(version));
		safe(() -> DESERT_PYRAMID = new DesertPyramid(version));
		safe(() -> END_CITY = new EndCity(version));
		safe(() -> FORTRESS = new Fortress(version));
		safe(() -> IGLOO = new Igloo(version));
		safe(() -> JUNGLE_PYRAMID = new JunglePyramid(version));
		safe(() -> MANSION = new Mansion(version));
		safe(() -> MINESHAFT = new Mineshaft(version));
		safe(() -> MONUMENT = new Monument(version));
		safe(() -> NETHER_FOSSIL = new NetherFossil(version));
		safe(() -> OCEAN_RUIN = new OceanRuin(version));
		safe(() -> PILLAGER_OUTPOST = new PillagerOutpost(version));
		safe(() -> RUINED_PORTAL_OVERWORLD = new RuinedPortal(Dimension.OVERWORLD, version));
		safe(() -> RUINED_PORTAL_NETHER = new RuinedPortal(Dimension.NETHER, version));
		safe(() -> SHIPWRECK = new Shipwreck(version));
		safe(() -> STRONGHOLD = new Stronghold(version));
		safe(() -> SWAMP_HUT = new SwampHut(version));
		safe(() -> VILLAGE = new Village(version));

		safe(() -> END_GATEWAY = new EndGateway(version));
		safe(() -> DESERT_WELL = new DesertWell(version));
		safe(() -> EMERALD_ORE = new EmeraldOre(version));
		safe(() -> DUNGEON = new Dungeon(version));
		safe(() -> WARPED_FUNGUS = new WarpedFungus(version));
	}

	private static void safe(Runnable runnable) {
		try {runnable.run();} catch(Exception e) {}
	}

}
