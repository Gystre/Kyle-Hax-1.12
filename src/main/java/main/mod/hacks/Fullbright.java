package main.mod.hacks;

import main.KyleHax;
import main.mod.Mod;
import net.minecraft.client.entity.EntityPlayerSP;

/*
Description:
This will make the ingame brightness very high so that you can see everything
 */

public class Fullbright extends Mod {
    public Fullbright(KyleHax kyleHax){
        super(kyleHax, "fullbright", -1);
    }

    @Override
    public void onLivingUpdate(EntityPlayerSP player){
        kyleHax.mc.gameSettings.gammaSetting = 16;
    }

    @Override
    public void onDisable(){
        kyleHax.mc.gameSettings.gammaSetting = 1;
    }
}
