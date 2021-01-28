package main.util;

import main.util.entity.MobType;
import main.util.entity.MobTypeEnum;
import main.util.entity.MobTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class EntityUtils {

    public static Vec3d getEyePos(Entity entity) {
        return new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
    }

    public static MobTypeEnum getRelationship(Entity entity) {
        if (entity instanceof AbstractClientPlayer) {
            return MobTypeEnum.PLAYER;
        } else {
            // check special cases first
            for (MobType type : MobTypeRegistry.getSortedSpecialMobTypes()) {
                if (type.isMobType(entity)) {
                    return type.getMobType(entity);
                }
            }
            // this code will continue if no special was found
            if (MobTypeRegistry.HOSTILE.isMobType(entity)) {
                return MobTypeEnum.HOSTILE;
            } else if (MobTypeRegistry.FRIENDLY.isMobType(entity)) {
                return MobTypeEnum.FRIENDLY;
            } else {
                return MobTypeEnum.HOSTILE; // default to hostile
            }
        }
    }

    public static boolean isLiving(Entity entity) {
        return entity instanceof EntityLivingBase;
    }

    public static boolean isAlive(Entity entity) {
        return isLiving(entity) && !entity.isDead && ((EntityLivingBase) (entity)).getHealth() > 0;
    }

    public static boolean isFakeLocalPlayer(Entity entity) {
        return entity != null && entity.getEntityId() == -100;
    }

    public static boolean isValidEntity(Entity entity) {
        Entity riding = Minecraft.getMinecraft().player.getRidingEntity();
        return entity.ticksExisted > 1
                && !isFakeLocalPlayer(entity)
                && (riding == null || !riding.equals(entity));
    }

    //get entities interpolated amount
    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return new Vec3d(
                (entity.posX - entity.lastTickPosX) * x,
                (entity.posY - entity.lastTickPosY) * y,
                (entity.posZ - entity.lastTickPosZ) * z);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, Vec3d vec) {
        return getInterpolatedAmount(entity, vec.x, vec.y, vec.z);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    //interpolated position
    public static Vec3d getInterpolatedPos(Entity entity, double ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ)
                .add(getInterpolatedAmount(entity, ticks));
    }

    //interpolated eye position
    public static Vec3d getInterpolatedEyePos(Entity entity, double ticks) {
        return getInterpolatedPos(entity, ticks).addVector(0, entity.getEyeHeight(), 0);
    }

    public static Angle getLookAtAngles(Vec3d start, Vec3d end) {
        return AngleHelper.getAngleFacingInDegrees(end.subtract(start)).normalize();
    }

    public static Angle getLookAtAngles(Vec3d end) {
        return getLookAtAngles(EntityUtils.getEyePos(Minecraft.getMinecraft().player), end);
    }

    //get the center of the hit box
    public static Vec3d getOBBCenter(Entity entity) {
        AxisAlignedBB obb = entity.getEntityBoundingBox();
        return new Vec3d(
                (obb.maxX + obb.minX) / 2.D, (obb.maxY + obb.minY) / 2.D, (obb.maxZ + obb.minZ) / 2.D);
    }


    public static Angle getLookAtAngles(Entity entity) {
        return getLookAtAngles(getOBBCenter(entity));
    }
}
