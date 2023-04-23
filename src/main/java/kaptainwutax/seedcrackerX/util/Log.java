package kaptainwutax.seedcrackerX.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import java.util.regex.Pattern;

public class Log {

    public static void debug(String message) {
        PlayerEntity player = getPlayer();

        if(player != null) {
            schedule(() -> player.displayClientMessage(new StringTextComponent(message), false));
        }
    }

    public static void warn(String message) {
        PlayerEntity player = getPlayer();

        if(player != null) {
            schedule(() -> player.displayClientMessage(new StringTextComponent(message).withStyle(TextFormatting.GREEN), false));
        }
    }

    public static void error(String message) {
        PlayerEntity player = getPlayer();

        if(player != null) {
            schedule(() -> player.displayClientMessage(new StringTextComponent(message).withStyle(TextFormatting.RED), false));
        }
    }

    public static void printSeed(String message, long seedValue) {
        String[] data = message.split(Pattern.quote("${SEED}"));
        String seed = String.valueOf(seedValue);
        ITextComponent text = TextComponentUtils.wrapInSquareBrackets((new StringTextComponent(seed)).withStyle(style -> style.withColor(TextFormatting.GREEN).withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, seed)).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("chat.copy.click"))).withInsertion(seed)));

        PlayerEntity player = getPlayer();

        if(player != null) {
            schedule(() -> player.displayClientMessage(new StringTextComponent(data[0]).append(text).append(new StringTextComponent(data[1])), false));
        }
    }
    public static void printDungeonInfo(String message) {
        ITextComponent text = TextComponentUtils.wrapInSquareBrackets((new StringTextComponent(message)).withStyle(style -> style.withColor(TextFormatting.GREEN).withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, message)).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("chat.copy.click"))).withInsertion(message)));

        PlayerEntity player = getPlayer();

        if(player != null) {
            schedule(() -> player.displayClientMessage(text,false));
        }
    }

    private static void schedule(Runnable runnable) {
        Minecraft.getInstance().execute(runnable);
    }

    private static PlayerEntity getPlayer() {
        return Minecraft.getInstance().player;
    }

}
