package main.util.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityWolf;

public class WolfMob extends MobType {

    @Override
    protected PriorityEnum getPriority() {
        return PriorityEnum.LOW;
    }

    @Override
    protected MobTypeEnum getMobTypeUnchecked(Entity entity) {
        EntityWolf wolf = (EntityWolf) entity;
        return wolf.isAngry() ? MobTypeEnum.HOSTILE : MobTypeEnum.NEUTRAL;
    }

    @Override
    public boolean isMobType(Entity entity) {
        return entity instanceof EntityWolf;
    }
}
