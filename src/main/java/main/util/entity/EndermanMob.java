package main.util.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;

public class EndermanMob extends MobType {

    @Override
    protected PriorityEnum getPriority() {
        return PriorityEnum.LOW;
    }

    @Override
    public boolean isMobType(Entity entity) {
        return entity instanceof EntityEnderman;
    }

    @Override
    protected MobTypeEnum getMobTypeUnchecked(Entity entity) {
        EntityEnderman enderman = (EntityEnderman) entity;
        return enderman.isScreaming() ? MobTypeEnum.HOSTILE : MobTypeEnum.NEUTRAL;
    }
}