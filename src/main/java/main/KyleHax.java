package main;

import main.commands.CommandHandler;
import main.event.EventHandler;
import main.handler.Handler;
import main.mod.ModHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = KyleHax.MOD_ID, name = KyleHax.MOD_NAME, version = KyleHax.MOD_VERSION)
public class KyleHax {
    public static final String MOD_NAME = "KyleHax";
    public static final String MOD_ID = "kylehax";
    public static final String MOD_VERSION = "1.0";
    public Minecraft mc = Minecraft.getMinecraft();

    private List<Handler> handlers = new ArrayList<Handler>();
    public ModHandler modHandler;
    public EventHandler eventHandler;
    public CommandHandler commandHandler;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        if(event.getSide() == Side.CLIENT){
            System.out.println("2 ez");
            addHandler(modHandler = new ModHandler());
            addHandler(eventHandler = new EventHandler());
            addHandler(commandHandler = new CommandHandler());
            FMLCommonHandler.instance().bus().register(eventHandler);
            MinecraftForge.EVENT_BUS.register(eventHandler);
        }
    }

    public void addHandler(Handler handler){
        getHandlers().add(handler);
        handler.init(this);
    }

    public List<Handler> getHandlers(){
        return handlers;
    }
}
