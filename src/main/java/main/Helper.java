package main;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class Helper {

    public static void outputInfo(String text){
        outputMessage(text);
    }

    public static void outputError(String text){
        outputMessage("[ERROR] " + text);
    }

    private static void outputMessage(String text){
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString("[KH] " + text));
    }
}
