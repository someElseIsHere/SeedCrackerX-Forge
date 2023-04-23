package kaptainwutax.seedcrackerX.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import kaptainwutax.seedcrackerX.command.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ClientCommands {

    public static final String PREFIX = "seed";
    public static final List<ClientCommand> COMMANDS = new ArrayList<>();

    public static RenderCommand RENDER;
    public static FinderCommand FINDER;
    public static DataCommand DATA;
    public static CrackerCommand CRACKER;
    public static VersionCommand VERSION;

    static {
        COMMANDS.add(RENDER = new RenderCommand());
        COMMANDS.add(FINDER = new FinderCommand());
        COMMANDS.add(DATA = new DataCommand());
        COMMANDS.add(CRACKER = new CrackerCommand());
        COMMANDS.add(VERSION = new VersionCommand());
    }

    public static void registerCommands(CommandDispatcher<CommandSource> dispatcher) {
        COMMANDS.forEach(clientCommand -> clientCommand.register(dispatcher));
    }

    public static boolean isClientSideCommand(String[] args) {
        if(args.length < 2)return false;
        if(!PREFIX.equals(args[0]))return false;

        for(ClientCommand command: COMMANDS) {
            if(command.getName().equals(args[1])) {
                return true;
            }
        }

        return false;
    }

    public static int executeCommand(StringReader reader) {
        ClientPlayerEntity player = Minecraft.getInstance().player;

        try {
            return player.connection.getCommands().execute(reader, new FakeCommandSource(player));
        } catch(CommandException e) {
            ClientCommand.sendFeedback("ur bad, git gud command", TextFormatting.RED, false);
            e.printStackTrace();
        } catch(CommandSyntaxException e) {
            ClientCommand.sendFeedback("ur bad, git gud syntax", TextFormatting.RED, false);
            e.printStackTrace();
        } catch(Exception e) {
            ClientCommand.sendFeedback("ur bad, wat did u do", TextFormatting.RED, false);
            e.printStackTrace();
        }

        return 1;
    }

    /**
     * Magic class by Earthcomputer.
     * https://github.com/Earthcomputer/clientcommands/blob/fabric/src/main/java/net/earthcomputer/clientcommands/command/FakeCommandSource.java
     * */
    public static class FakeCommandSource extends CommandSource {
        public FakeCommandSource(ClientPlayerEntity player) {
            super(player, player.position(), player.getRotationVector(), null, 0, player.getScoreboardName(), player.getName(), null, player);
        }

        @Override
        public Collection<String> getOnlinePlayerNames() {
            return Minecraft.getInstance().getConnection().getOnlinePlayers()
                    .stream().map(e -> e.getProfile().getName()).collect(Collectors.toList());
        }
    }

}
