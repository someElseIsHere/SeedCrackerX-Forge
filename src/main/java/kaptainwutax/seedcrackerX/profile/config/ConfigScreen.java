package kaptainwutax.seedcrackerX.profile.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import com.google.gson.Gson;


import com.seedfinding.mccore.version.MCVersion;
import kaptainwutax.seedcrackerX.cracker.HashedSeedData;
import kaptainwutax.seedcrackerX.finder.FinderQueue;
import kaptainwutax.seedcrackerX.finder.FinderQueue.RenderType;
import kaptainwutax.seedcrackerX.Features;
import kaptainwutax.seedcrackerX.SeedCracker;

import kaptainwutax.seedcrackerX.finder.Finder;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;


public class ConfigScreen {

    private boolean resetToDefault = false;
    boolean dataClear = false;
    private static ConfigObj config = new ConfigObj();

    private static File file = new File(Minecraft.getInstance().gameDirectory,"seedcracker.json");

    public Screen getConfigScreenByCloth(Screen parent) {
        
        ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(new TranslationTextComponent("key.seedcracker.category"));
        ConfigEntryBuilder eb = builder.entryBuilder();
        // ConfigCategory info = builder.getOrCreateCategory(new TranslationTextComponent("config.category.info"));

        //     info.addEntry(eb.startTextDescription(new StringTextComponent("some info")).build());
        //     if(SeedCracker.get().getDataStorage().getTimeMachine().worldSeeds != null) {
        //         info.addEntry(eb.startLongList(new StringTextComponent("longlist"), SeedCracker.get().getDataStorage().getTimeMachine().worldSeeds).build());
        //     } else {
        //         info.addEntry(eb.startTextDescription(new StringTextComponent("no worldseeds")).build());
        //     }

        ConfigCategory general = builder.getOrCreateCategory(new TranslationTextComponent("config.category.general"));

                general.addEntry(eb.startTextDescription(new StringTextComponent("This settings-page only only sets sets settings for one ingame session. Go to \"Profile\" tab to change settings longterm")).build());

            general.addEntry(eb.startTextDescription(new StringTextComponent("==============")).build());
                general.addEntry(eb.startBooleanToggle(new StringTextComponent("Active"), SeedCracker.get().isActive()).setSaveConsumer(val -> SeedCracker.get().setActive(val)).build());
                general.addEntry(eb.startTextDescription(new StringTextComponent("Version(1.8-1.16 Support for old chunks; versions before 1.16 are only tested for dungeon-shortcutting)")).build());
                general.addEntry(eb.startEnumSelector(new StringTextComponent("Version"), MCVersion.class, SeedCracker.MC_VERSION).setSaveConsumer(val -> {SeedCracker.MC_VERSION = val; Features.init(SeedCracker.MC_VERSION);}).build());

            general.addEntry(eb.startTextDescription(new StringTextComponent("==============")).build());

                general.addEntry(eb.startTextDescription(new StringTextComponent("Visuals")).build());
                general.addEntry(eb.startEnumSelector(new StringTextComponent("rendertype"), RenderType.class,FinderQueue.get().renderType).setSaveConsumer(val -> FinderQueue.get().renderType = val).build());

            general.addEntry(eb.startTextDescription(new StringTextComponent("==============")).build());

                general.addEntry(eb.startTextDescription(new StringTextComponent("Finder toggles")).build());
                for(Finder.Type finderType: Finder.Type.values()) {
                    general.addEntry(eb.startBooleanToggle(new StringTextComponent(String.valueOf(finderType)), FinderQueue.get().finderProfile.getActive(finderType)).setDefaultValue(FinderQueue.get().finderProfile.getActive(finderType)).setSaveConsumer(val -> FinderQueue.get().finderProfile.setActive(finderType, val)).build());
                }

        ConfigCategory config = builder.getOrCreateCategory(new TranslationTextComponent("config.category.profile"));

                config.addEntry(eb.startTextDescription(new StringTextComponent("the seedcracker will reset to this state after /seed data clear relogging or pressing \"reset to this profile after pressing save & quit\"")).build());
                config.addEntry(eb.startBooleanToggle(new StringTextComponent("Active"), ConfigScreen.config.isActive()).setSaveConsumer(val -> ConfigScreen.config.Active = val).build());
                config.addEntry(eb.startEnumSelector(new StringTextComponent("Version"), MCVersion.class, ConfigScreen.config.VERSION).setSaveConsumer(val -> ConfigScreen.config.VERSION = val).build());

            config.addEntry(eb.startTextDescription(new StringTextComponent("==============")).build());
            
                config.addEntry(eb.startTextDescription(new StringTextComponent("Visuals")).build());
                config.addEntry(eb.startEnumSelector(new StringTextComponent("rendertype"), RenderType.class, ConfigScreen.config.RENDER).setSaveConsumer(val -> ConfigScreen.config.RENDER = val).build());

            config.addEntry(eb.startTextDescription(new StringTextComponent("==============")).build());

                config.addEntry(eb.startTextDescription(new StringTextComponent("Finder toggles")).build());
                config.addEntry(eb.startBooleanToggle(new StringTextComponent("Buried treasure"), ConfigScreen.config.BURIED_TREASURE).setSaveConsumer(val -> ConfigScreen.config.BURIED_TREASURE = val).build());
                config.addEntry(eb.startBooleanToggle(new StringTextComponent("Desert temple"), ConfigScreen.config.DESERT_TEMPLE).setSaveConsumer(val -> ConfigScreen.config.DESERT_TEMPLE = val).build());
                config.addEntry(eb.startBooleanToggle(new StringTextComponent("End city"), ConfigScreen.config.END_CITY).setSaveConsumer(val -> ConfigScreen.config.END_CITY = val).build());
                config.addEntry(eb.startBooleanToggle(new StringTextComponent("Jungle temple"), ConfigScreen.config.JUNGLE_TEMPLE).setSaveConsumer(val -> ConfigScreen.config.JUNGLE_TEMPLE = val).build());
                config.addEntry(eb.startBooleanToggle(new StringTextComponent("Ocean monument"), ConfigScreen.config.MONUMENT).setSaveConsumer(val -> ConfigScreen.config.MONUMENT = val).build());
                config.addEntry(eb.startBooleanToggle(new StringTextComponent("Witch hut"), ConfigScreen.config.SWAMP_HUT).setSaveConsumer(val -> ConfigScreen.config.SWAMP_HUT = val).build());
                config.addEntry(eb.startBooleanToggle(new StringTextComponent("Shipwreck"), ConfigScreen.config.SHIPWRECK).setSaveConsumer(val -> ConfigScreen.config.SHIPWRECK = val).build());
                config.addEntry(eb.startBooleanToggle(new StringTextComponent("End pillars"), ConfigScreen.config.END_PILLARS).setSaveConsumer(val -> ConfigScreen.config.END_PILLARS = val).build());
                config.addEntry(eb.startBooleanToggle(new StringTextComponent("End gateway"), ConfigScreen.config.END_GATEWAY).setSaveConsumer(val -> ConfigScreen.config.END_GATEWAY = val).build());
                config.addEntry(eb.startBooleanToggle(new StringTextComponent("Dungeon"), ConfigScreen.config.DUNGEON).setSaveConsumer(val -> ConfigScreen.config.DUNGEON = val).build());
                config.addEntry(eb.startBooleanToggle(new StringTextComponent("Emerald ore"), ConfigScreen.config.EMERALD_ORE).setSaveConsumer(val -> ConfigScreen.config.EMERALD_ORE = val).build());
                config.addEntry(eb.startBooleanToggle(new StringTextComponent("Desert well"), ConfigScreen.config.DESERT_WELL).setSaveConsumer(val -> ConfigScreen.config.DESERT_WELL = val).build());
                config.addEntry(eb.startBooleanToggle(new StringTextComponent("Warped fungus"),ConfigScreen.config.WARPED_FUNGUS).setSaveConsumer(val -> ConfigScreen.config.WARPED_FUNGUS = val).build());
                config.addEntry(eb.startBooleanToggle(new StringTextComponent("Biome"), ConfigScreen.config.BIOME).setSaveConsumer(val -> ConfigScreen.config.BIOME = val).build());


            config.addEntry(eb.startTextDescription(new StringTextComponent("==============")).build());

                config.addEntry(eb.startBooleanToggle(new StringTextComponent("reset to this profile after pressing save & quit\""), resetToDefault).setSaveConsumer(val -> resetToDefault = val).build());
        ConfigCategory info = builder.getOrCreateCategory(new TranslationTextComponent("config.category.info"));
        //Clear data
        info.addEntry(eb.startBooleanToggle(new StringTextComponent("Clear all data"),dataClear).setSaveConsumer(val -> dataClear = val).build());
        //List worldseeds
        List<Long> worldSeeds = SeedCracker.get().getDataStorage().getTimeMachine().worldSeeds;
        if(worldSeeds != null) {
            SubCategoryBuilder world = eb.startSubCategory(new StringTextComponent("Worldseeds"));
            for(long worldSeed:worldSeeds) {
                world.add(eb.startTextField(new StringTextComponent(""), String.valueOf(worldSeed)).build());
            }
            info.addEntry(world.setExpanded(true).build());
        } else {
            info.addEntry(eb.startTextDescription(new StringTextComponent("No worldseeds found")).build());
        }
        //List structureseeds
        List<Long> structureSeeds = SeedCracker.get().getDataStorage().getTimeMachine().structureSeeds;
        if(structureSeeds != null) {
            SubCategoryBuilder struc = eb.startSubCategory(new StringTextComponent("Structureseeds"));
            for(long structureSeed:structureSeeds) {
                struc.add(eb.startTextField(new StringTextComponent(""),String.valueOf(structureSeed)).build());
            }
            info.addEntry(struc.setExpanded(true).build());
        } else {
            info.addEntry(eb.startTextDescription(new StringTextComponent("No structureseeds found")).build());
        }



        if(getConfig().isDEBUG()) {
            //List Dungeonseeds von 1.12 Dungeons
            List<Long> structure12Seeds = SeedCracker.get().getDataStorage().dungeon12StructureSeeds;
            if(structure12Seeds.size() != 0) {
                SubCategoryBuilder dungeon12 = eb.startSubCategory(new StringTextComponent("Structureseeds of dungeons that need to be checked against another dungeon to get the right one(for 1.12 and below"));
                for(long structure12Seed:structure12Seeds) {
                    dungeon12.add(eb.startTextField(new StringTextComponent(""),String.valueOf(structure12Seed)).build());
                }
                info.addEntry(dungeon12.setExpanded(true).build());
            } else {
                info.addEntry(eb.startTextDescription(new StringTextComponent("No structureseeds of old dungeons found")).build());
            }
            //List pillarseeds
            List<Integer> pillarSeeds = SeedCracker.get().getDataStorage().getTimeMachine().pillarSeeds;
            if(pillarSeeds != null) {
                SubCategoryBuilder pillar = eb.startSubCategory(new StringTextComponent("Pillarseeds"));
                for(long structureSeed:pillarSeeds) {
                    pillar.add(eb.startTextField(new StringTextComponent(""),String.valueOf(structureSeed)).build());
                }
                info.addEntry(pillar.setExpanded(true).build());
            } else {
                info.addEntry(eb.startTextDescription(new StringTextComponent("No Pillarseeds found")).build());
            }
            HashedSeedData hashedSeed = SeedCracker.get().getDataStorage().hashedSeedData;
            if(hashedSeed != null) {
                info.addEntry(eb.startTextField(new StringTextComponent("Hashed seed"),String.valueOf(hashedSeed.getHashedSeed())).build());
            } else {
                info.addEntry(eb.startTextDescription(new StringTextComponent("no hashed seed found")).build());
            }
        }

        builder.setSavingRunnable(() -> {
            saveConfig(file);
            loadConfig();
            if(dataClear) {
                SeedCracker.get().getDataStorage().clear();
                dataClear = false;
            }
            if(resetToDefault) {
                Features.init(getConfig().VERSION);
                SeedCracker.get().setActive(getConfig().Active);
                FinderQueue.get().clear();
                resetToDefault = false;
            }
        });
        return builder.build();
        
    }

    public static void saveConfig(File file) {
        Gson gson = new Gson();

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            System.out.println("seedcracker could't save config");
            e.printStackTrace();
        }
    }
    
    public static void loadConfig() {
        Gson gson = new Gson();

        try (Reader reader = new FileReader(file)) {
            config = gson.fromJson(reader, ConfigObj.class);
        } catch (IOException e){
            System.out.println("seedcracker couldn't find/load config");
        }
    }

    public static ConfigObj getConfig() {
        return config;
    }
}
