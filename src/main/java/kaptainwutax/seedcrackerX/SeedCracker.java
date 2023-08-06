package kaptainwutax.seedcrackerX;

import kaptainwutax.seedcrackerX.api.SeedCrackerAPI;
import kaptainwutax.seedcrackerX.config.Config;
import kaptainwutax.seedcrackerX.cracker.storage.DataStorage;
import kaptainwutax.seedcrackerX.finder.FinderQueue;
import kaptainwutax.seedcrackerX.init.ClientCommands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod("seedcracker")
public class SeedCracker {
    public static final ArrayList<SeedCrackerAPI> entrypoints = new ArrayList<>();
    private static SeedCracker INSTANCE;
    private final DataStorage dataStorage = new DataStorage();

    public static SeedCracker get() {
        return INSTANCE;
    }

    public SeedCracker() {
        INSTANCE = this;
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> {
            Config.load();
            Features.init(Config.get().getVersion());
            MinecraftForge.EVENT_BUS.addListener(this::onRegisterClientCommands);
        });
    }

    public void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        ClientCommands.registerCommands(event.getDispatcher());
    }

    public DataStorage getDataStorage() {
        return this.dataStorage;
    }

    public void reset() {
        SeedCracker.get().getDataStorage().clear();
        FinderQueue.get().finderControl.deleteFinders();
    }
}
