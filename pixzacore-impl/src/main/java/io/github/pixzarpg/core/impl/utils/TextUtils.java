package io.github.pixzarpg.core.impl.utils;

import org.bukkit.ChatColor;

public class TextUtils {

    public static final ChatColor DEFAULT_PREFIX_COLOR = ChatColor.BLUE;
    public static final ChatColor DEFAULT_CHAT_COLOR = ChatColor.GRAY;


    public static String generateLoggerMessage(String prefix, String message) {
        return "[" + prefix + "] " + message;
    }

    public static String generatePlayerMessage(String prefix, String message) {
        return generatePlayerMessage(prefix, DEFAULT_PREFIX_COLOR, message, DEFAULT_CHAT_COLOR);
    }

    public static String generatePlayerMessage(String prefix, ChatColor prefixColor, String message, ChatColor messageColor) {
        return prefixColor + prefix + "> " + messageColor + message;
    }

}
