package main.util.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityPigZombie;

public class PigZombieMob extends MobType {

    @Override
    protected PriorityEnum getPriority() {
        return PriorityEnum.LOW;
    }

    @Override
    public boolean isMobType(Entity entity) {
        return entity instanceof EntityPigZombie;
    }

    @Override
    protected MobTypeEnum getMobTypeUnchecked(Entity entity) {
        EntityPigZombie zombie = (EntityPigZombie) entity;
        return (zombie.isArmsRaised() || zombie.isAngry()) ? MobTypeEnum.HOSTILE : MobTypeEnum.NEUTRAL;
    }
}