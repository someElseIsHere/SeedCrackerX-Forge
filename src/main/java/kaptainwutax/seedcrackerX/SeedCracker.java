package kaptainwutax.seedcrackerX;

import com.seedfinding.mccore.version.MCVersion;
import kaptainwutax.seedcrackerX.command.ClientCommand;
import kaptainwutax.seedcrackerX.cracker.storage.DataStorage;
import kaptainwutax.seedcrackerX.finder.FinderQueue;
import kaptainwutax.seedcrackerX.profile.config.ConfigScreen;
import kaptainwutax.seedcrackerX.render.RenderQueue;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Mod;

import static kaptainwutax.seedcrackerX.SeedCracker.modid;

@Mod(modid)
public class SeedCracker{
    public static final String modid = "seedcrackerx";

    public static MCVersion MC_VERSION = MCVersion.v1_16_5;

    private static final SeedCracker INSTANCE = new SeedCracker();
    private final DataStorage dataStorage = new DataStorage();
    private static boolean active;

    public void SeedCracker() {
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
