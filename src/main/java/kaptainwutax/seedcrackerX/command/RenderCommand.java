package kaptainwutax.seedcrackerX.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import kaptainwutax.seedcrackerX.finder.FinderQueue;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TextFormatting;

import static net.minecraft.command.Commands.literal;

public class RenderCommand extends ClientCommand {

    @Override
    public String getName() {
        return "render";
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("outlines")
                .executes(context -> this.printRenderMode())
        );

        for(FinderQueue.RenderType renderType: FinderQueue.RenderType.values()) {
            builder.then(literal("outlines")
                    .then(literal(renderType.toString()).executes(context -> this.setRenderMode(renderType)))
            );
        }
    }

    private int printRenderMode() {
        sendFeedback("Current render mode is set to [" + FinderQueue.get().renderType + "].", TextFormatting.AQUA, false);
        return 0;
    }

    private int setRenderMode(FinderQueue.RenderType renderType) {
        FinderQueue.get().renderType = renderType;
        sendFeedback("Set render mode to [" + FinderQueue.get().renderType + "].", TextFormatting.AQUA, false);
        return 0;
    }

}
