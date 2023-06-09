package kaptainwutax.seedcrackerX.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.seedfinding.mccore.version.MCVersion;
import kaptainwutax.seedcrackerX.Features;
import kaptainwutax.seedcrackerX.SeedCracker;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TextFormatting;

import static net.minecraft.command.Commands.literal;

public class VersionCommand extends ClientCommand {

	@Override
	public String getName() {
		return "version";
	}

	@Override
	public void build(LiteralArgumentBuilder<CommandSource> builder) {
		for(MCVersion version: MCVersion.values()) {
			if(version.isOlderThan(MCVersion.v1_8))continue;
			builder.then(literal(version.name).executes(context -> this.setVersion(version)));
		}
	}

	private int setVersion(MCVersion version) {
		SeedCracker.MC_VERSION = version;
		Features.init(SeedCracker.MC_VERSION);
		ClientCommand.sendFeedback("Changed version to " + version + ".", TextFormatting.AQUA, true);
		return 0;
	}

}
