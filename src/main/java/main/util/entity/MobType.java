package main.util.entity;

import net.minecraft.entity.Entity;

public abstract class MobType implements Comparable<MobType> {

    protected PriorityEnum getPriority() {
        return PriorityEnum.LOWEST;
    }

    public boolean isNeutralMob(Entity entity) {
        return false;
    }

    public abstract boolean isMobType(Entity entity);

    protected abstract MobTypeEnum getMobTypeUnchecked(Entity entity);

    public MobTypeEnum getMobType(Entity entity) {
        try {
            return getMobTypeUnchecked(entity);
        } catch (Throwable t) {
            return MobTypeEnum.HOSTILE;
        }
    }

    @Override
    public int compareTo(MobType o) {
        return getPriority().compareTo(o.getPriority());
    }
}