package main.mod.hacks;

import main.KyleHax;
import main.mod.Mod;
import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.input.Keyboard;

/*
Description:
This hack will make the player automatically jump once they hit the ground
 */

public class BunnyHop extends Mod {
    public BunnyHop(KyleHax kyleHax){
        super(kyleHax, "bhop", Keyboard.KEY_B);
    }

    private boolean shouldSprint(EntityPlayerSP player){
        return player.moveForward > 0 && !player.isSneaking() && player.getFoodStats().getFoodLevel() > 6;
    }

    @Override
    public void onLivingUpdate(EntityPlayerSP player){
        if((player.moveForward != 0 || player.moveStrafing != 0)
                && !player.isSneaking() && player.onGround){
            player.setSprinting(shouldSprint(player));
            player.jump();
        }
    }
}
