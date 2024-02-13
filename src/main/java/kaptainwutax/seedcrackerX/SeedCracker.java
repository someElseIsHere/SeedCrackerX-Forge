package kaptainwutax.seedcrackerX;

import kaptainwutax.seedcrackerX.command.ClientCommand;
import kaptainwutax.seedcrackerX.cracker.storage.DataStorage;
import kaptainwutax.seedcrackerX.finder.FinderQueue;
import kaptainwutax.seedcrackerX.profile.config.ConfigScreen;
import kaptainwutax.seedcrackerX.render.RenderQueue;
import kaptainwutax.seedutils.mc.MCVersion;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Mod;

@Mod("seedcrackerx")
public class SeedCracker{

	public static MCVersion MC_VERSION = MCVersion.v1_16_2;

    private static final SeedCracker INSTANCE = new SeedCracker();
    private final DataStorage dataStorage = new DataStorage();
	private static boolean active;

	public SeedCracker() {
		ConfigScreen.loadConfig();
		active = ConfigScreen.getConfig().isActive();
		Features.init(MC_VERSION);
		RenderQueue.get().add("hand", FinderQueue.get()::renderFinders);
	}

	public static SeedCracker get() {
	    return INSTANCE;
    }

	public DataStorage getDataStorage() {
		return this.dataStorage;
	}

	public boolean isActive() {
		return active;
	}

    public void setActive(boolean active) {
		if(SeedCracker.active == active) return;
		SeedCracker.active = active;

	    if(active) {
		    ClientCommand.sendFeedback("SeedCracker is active.", TextFormatting.GREEN, true);
	    } else {
		    ClientCommand.sendFeedback("SeedCracker is not active.", TextFormatting.RED, true);
	    }
    }

	public void reset() {
		SeedCracker.get().getDataStorage().clear();
		FinderQueue.get().clear();
	}
}
