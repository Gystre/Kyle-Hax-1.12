package main.mod.hacks;

import main.KyleHax;
import main.mod.Mod;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

/*
Description:
This hack will auto mine blocks while you hover over them
 */

public class AutoMine extends Mod {
    public AutoMine(KyleHax kyleHax){
        super(kyleHax, "automine", Keyboard.KEY_V);
    }

    @Override
    public void onLivingUpdate(EntityPlayerSP player){
        BlockPos pos = kyleHax.mc.objectMouseOver.getBlockPos();
        Block mouseOverBlock = kyleHax.mc.world.getBlockState(pos).getBlock();

        if(Block.getIdFromBlock(mouseOverBlock) != 0){
            KeyBinding.setKeyBindState(kyleHax.mc.gameSettings.keyBindAttack.getKeyCode(), true);
        }else{
            KeyBinding.setKeyBindState(kyleHax.mc.gameSettings.keyBindAttack.getKeyCode(), false);
        }
    }

    @Override
    public void onDisable(){
        KeyBinding.setKeyBindState(kyleHax.mc.gameSettings.keyBindAttack.getKeyCode(), false);
    }
}
