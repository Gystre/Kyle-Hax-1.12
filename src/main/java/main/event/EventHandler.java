package main.event;

import main.Helper;
import main.KyleHax;
import main.handler.Handler;
import main.mod.Mod;
import main.settings.SettingFloat;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class EventHandler implements Handler {
    private KyleHax kyleHax;

    public void init(KyleHax kyleHax) {
        this.kyleHax = kyleHax;
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() == kyleHax.mc.player) {
            for (Mod mod : kyleHax.modHandler.getMods().values()) {
                if (mod.isEnabled()) {
                    mod.onLivingUpdate((EntityPlayerSP) event.getEntity());
                }
            }
        }
    }

    @SubscribeEvent
    public void onKeyPressed(InputEvent.KeyInputEvent event) {
        if (!Keyboard.getEventKeyState())
            return;
        int keyCode = Keyboard.getEventKey();

        for (Mod mod : kyleHax.modHandler.getMods().values()) {
            if (mod.getToggleKey() == keyCode) {
                mod.setEnabled(!mod.isEnabled());

                if (!mod.isEnabled()) {
                    mod.onDisable();
                }
            }
        }
    }

    @SubscribeEvent
    public void onClientSentMessage(ClientChatEvent event){
        String msg = event.getMessage().trim();

        //ignore messages that don't start with prefix
        if(!msg.startsWith("."))
            return;

        event.setMessage("");
        event.setCanceled(true); //cancel the event so the player doesn't see it in the chat
        kyleHax.mc.ingameGUI.getChatGUI().addToSentMessages(msg); //allow for player to press up and down keys to recall command

        msg.toLowerCase();
        msg = msg.substring(1); //get rid of '.'
        String[] parts = msg.split(" "); //[0] modname, [1] args

        if(kyleHax.commandHandler.getCommands().containsKey(parts[0])){
            kyleHax.commandHandler.getCommands().get(parts[0]).call(parts);
        }else{
            Helper.outputError("Unrecognized command");
        }
    }
}