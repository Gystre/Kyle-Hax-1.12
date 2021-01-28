package main.mod.hacks;

import main.KyleHax;
import main.mod.Mod;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;

/*
Description:
Makes you move faster
 */

public class Speed extends Mod {
    private double speed = 5;

    public Speed(KyleHax kyleHax){
        super(kyleHax, "speed", Keyboard.KEY_Z);
    }

    @Override
    public void onLivingUpdate(EntityPlayerSP player){
        if(kyleHax.mc.gameSettings.keyBindForward.isPressed()){
            if(player.onGround){
                speed = 5;
            }else{
                speed = 2;
            }
            float yaw = player.rotationYaw * 0.017453292F; // pi / 180 (translate to radians)
            player.motionX -= MathHelper.sin(yaw) * (speed / 5);
            player.motionZ += MathHelper.cos(yaw) * (speed / 5);
        }
    }
}
