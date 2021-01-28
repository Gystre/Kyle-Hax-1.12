package main.util.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;

public class HostileMob extends MobType {

    @Override
    public boolean isMobType(Entity entity) {
        return entity.isCreatureType(EnumCreatureType.MONSTER, false);
    }

    @Override
    protected MobTypeEnum getMobTypeUnchecked(Entity entity) {
        return MobTypeEnum.HOSTILE;
    }
}