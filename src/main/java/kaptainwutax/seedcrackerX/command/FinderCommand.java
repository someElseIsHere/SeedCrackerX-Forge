package kaptainwutax.seedcrackerX.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import kaptainwutax.seedcrackerX.finder.Finder;
import kaptainwutax.seedcrackerX.finder.FinderQueue;
import kaptainwutax.seedcrackerX.finder.ReloadFinders;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TextFormatting;

import static net.minecraft.command.Commands.literal;

public class FinderCommand extends ClientCommand {
    ReloadFinders reloadFinders = new ReloadFinders();

    @Override
    public String getName() {
        return "finder";
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        for(Finder.Type finderType: Finder.Type.values()) {
            builder.then(literal("type")
                    .then(literal(finderType.toString())
                            .then(literal("ON").executes(context -> this.setFinderType(finderType, true)))
                            .then(literal("OFF").executes(context -> this.setFinderType(finderType, false))))
                    .executes(context -> this.printFinderType(finderType))
            );
        }

        for(Finder.Category finderCategory: Finder.Category.values()) {
            builder.then(literal("category")
                    .then(literal(finderCategory.toString())
                            .then(literal("ON").executes(context -> this.setFinderCategory(finderCategory, true)))
                            .then(literal("OFF").executes(context -> this.setFinderCategory(finderCategory, false))))
                    .executes(context -> this.printFinderCategory(finderCategory))
            );
        }
        builder.then(literal("reload").executes(context -> this.reload()));
    }

    private int printFinderCategory(Finder.Category finderCategory) {
        Finder.Type.getForCategory(finderCategory).forEach(this::printFinderType);
        return 0;
    }

    private int printFinderType(Finder.Type finderType) {
        sendFeedback("Finder " + finderType + " is set to [" + String.valueOf(FinderQueue.get().finderProfile.getActive(finderType)).toUpperCase() + "].", TextFormatting.AQUA,false);
        return 0;
    }

    private int setFinderCategory(Finder.Category finderCategory, boolean flag) {
        Finder.Type.getForCategory(finderCategory).forEach(finderType -> this.setFinderType(finderType, flag));
        return 0;
    }

    private int setFinderType(Finder.Type finderType, boolean flag) {
        if(FinderQueue.get().finderProfile.setActive(finderType, flag)) {
            sendFeedback("Finder " + finderType + " has been set to [" + String.valueOf(flag).toUpperCase() + "].", TextFormatting.AQUA, false);
        } else {
            sendFeedback("Your current finder profile is locked and cannot be modified. Please make a copy first.", TextFormatting.RED, false);
        }

        return 0;
    }

    private int reload() {
        reloadFinders.reload();
        return 0;
    }

}
