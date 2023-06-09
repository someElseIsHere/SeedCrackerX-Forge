package kaptainwutax.seedcrackerX.profile.config;


import com.seedfinding.mccore.version.MCVersion;
import kaptainwutax.seedcrackerX.command.ClientCommand;
import kaptainwutax.seedcrackerX.finder.FinderQueue.RenderType;
import net.minecraft.util.text.TextFormatting;

public class ConfigObj{
    protected boolean BURIED_TREASURE = true;
    protected boolean DESERT_TEMPLE = true;
    protected boolean END_CITY = true;
    protected boolean JUNGLE_TEMPLE = true;
    protected boolean MONUMENT = true;
    protected boolean SWAMP_HUT = true;
    protected boolean SHIPWRECK = true;
    protected boolean END_PILLARS = true;
    protected boolean END_GATEWAY = true;
    protected boolean DUNGEON = true;
    protected boolean EMERALD_ORE = true;
    protected boolean DESERT_WELL = true;
    protected boolean WARPED_FUNGUS = true;
    protected boolean BIOME = true;
    protected MCVersion VERSION = MCVersion.v1_16_5;
    protected RenderType RENDER = RenderType.XRAY;
    protected boolean Active = true;

    public boolean isActive() {
        return this.Active;
    }

    public boolean isDEBUG() {
        return DEBUG;
    }

    public void setDEBUG(boolean DEBUG) {
        this.DEBUG = DEBUG;
        if(this.DEBUG) {
            ClientCommand.sendFeedback("Debug is active.", TextFormatting.GREEN, true);
        } else {
            ClientCommand.sendFeedback("Debug is not active.", TextFormatting.RED, true);
        }

    }

    private boolean DEBUG = false;

    public boolean isBURIED_TREASURE() {
        return this.BURIED_TREASURE;
    }

    public boolean isDESERT_TEMPLE() {
        return this.DESERT_TEMPLE;
    }

    public boolean isEND_CITY() {
        return this.END_CITY;
    }

    public boolean isJUNGLE_TEMPLE() {
        return this.JUNGLE_TEMPLE;
    }

    public boolean isMONUMENT() {
        return this.MONUMENT;
    }

    public boolean isSWAMP_HUT() {
        return this.SWAMP_HUT;
    }

    public boolean isSHIPWRECK() {
        return this.SHIPWRECK;
    }

    public boolean isEND_PILLARS() {
        return this.END_PILLARS;
    }

    public boolean isEND_GATEWAY() {
        return this.END_GATEWAY;
    }

    public boolean isDUNGEON() {
        return this.DUNGEON;
    }

    public boolean isEMERALD_ORE() {
        return this.EMERALD_ORE;
    }

    public boolean isDESERT_WELL() {
        return this.DESERT_WELL;
    }

    public boolean isWARPED_FUNGUS() {
        return WARPED_FUNGUS;
    }

    public boolean isBIOME() {
        return this.BIOME;
    }

    public MCVersion getVERSION() {
        return this.VERSION;
    }

    public RenderType getRENDER() {
        return this.RENDER;
    }
}
