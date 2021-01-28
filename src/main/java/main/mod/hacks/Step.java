package main.mod.hacks;

import main.KyleHax;
import main.mod.Mod;
import main.settings.Setting;
import main.settings.SettingFloat;
import net.minecraft.client.entity.EntityPlayerSP;

/*
Description:
Sets the step height of the player to something larger so they will be able to climb up blocks instantly
 */

public class Step extends Mod {
    private final SettingFloat stepHeight = new SettingFloat("step-height", "Sets the height the player will step up", 2f);
    private static final float DEFAULT_STEP_HEIGHT = 0.6f;


    public Step(KyleHax kyleHax){
        super(kyleHax, "step", -1);

        addSetting(stepHeight);
    }

    @Override
    public void onLivingUpdate(EntityPlayerSP player){
        player.stepHeight = player.onGround ? stepHeight.getValue() : DEFAULT_STEP_HEIGHT;
    }

    @Override
    public void onDisable(){
        kyleHax.mc.player.stepHeight = DEFAULT_STEP_HEIGHT;
    }
}
