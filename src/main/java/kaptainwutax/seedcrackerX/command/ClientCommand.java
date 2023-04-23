package kaptainwutax.seedcrackerX.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import kaptainwutax.seedcrackerX.init.ClientCommands;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import static net.minecraft.command.Commands.literal;

public abstract class ClientCommand {

    public abstract String getName();

    public abstract void build(LiteralArgumentBuilder<CommandSource> builder);

    public static void sendFeedback(String message, TextFormatting color, boolean overlay) {
        try {
            Minecraft.getInstance().player.displayClientMessage(new StringTextComponent(message).withStyle(color), overlay);
        }catch (Exception e) {
        }
    }

    public final void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = literal(this.getName());
        this.build(builder);
        dispatcher.register(literal(ClientCommands.PREFIX).then(builder));
    }

}
