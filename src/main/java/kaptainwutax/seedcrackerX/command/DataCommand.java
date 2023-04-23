package kaptainwutax.seedcrackerX.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import kaptainwutax.seedcrackerX.SeedCracker;
import kaptainwutax.seedcrackerX.cracker.storage.DataStorage;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TextFormatting;

import static net.minecraft.command.Commands.literal;

public class DataCommand extends ClientCommand {

	@Override
	public String getName() {
		return "data";
	}

	@Override
	public void build(LiteralArgumentBuilder<CommandSource> builder) {
		builder.then(literal("clear")
				.executes(this::clear)
		);

		builder.then(literal("bits")
				.executes(this::printBits)
		);
	}

	public int clear(CommandContext<CommandSource> context) {
		SeedCracker.get().reset();
		sendFeedback("Cleared data storage and finders.", TextFormatting.GREEN, false);
		return 0;
	}

	private int printBits(CommandContext<CommandSource> context) {
		DataStorage s = SeedCracker.get().getDataStorage();
		String message = "You currently have collected " + (int)s.getBaseBits() + " bits out of " + (int)s.getWantedBits() + ".";
		sendFeedback(message, TextFormatting.GREEN, false);
		return 0;
	}

}

