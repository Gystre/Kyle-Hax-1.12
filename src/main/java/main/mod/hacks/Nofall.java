package main.mod.hacks;

import main.KyleHax;
import main.mod.Mod;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;

/*
Description:
This hack negates fall damage
 */

public class Nofall extends Mod {
    public Nofall(KyleHax kyleHax){
        super(kyleHax, "nofall", -1);
    }

    @Override
    public void onLivingUpdate(EntityPlayerSP player){
        if(player.fallDistance > 2){
            kyleHax.mc.getConnection().sendPacket(new CPacketPlayer(true));
        }
    }
}
