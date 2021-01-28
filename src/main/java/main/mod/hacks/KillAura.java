package main.mod.hacks;

import main.util.Angle;
import main.util.AngleHelper;
import main.util.EntityUtils;
import main.KyleHax;
import main.mod.Mod;
import main.settings.SettingBoolean;
import main.settings.SettingFloat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.Optional;

import static main.mod.hacks.KillAura.Selector.CROSSHAIR;

public class KillAura extends Mod {
    private static Entity target = null;

    public static Entity getTarget() {
        return target;
    }

    public static void setTarget(Entity target) {
        KillAura.target = target;
    }

    //TODO: implement setting for selection type
    enum Selector {
        CROSSHAIR,
        DISTANCE,
    }

    private final SettingBoolean silent = new SettingBoolean("silent", "Won't look at the enemy while attacking", true);
    private final SettingBoolean auto_attack = new SettingBoolean("auto-attack", "Automatically attack when a target is found", true);
    private final SettingBoolean hold_target = new SettingBoolean("hold-target", "Keep attacking the first target found until it becomes no longer valid", false);
    private final SettingBoolean vis_check = new SettingBoolean("vis-check", "Check if the target is visible before aquiring", false);
    private final SettingBoolean target_players = new SettingBoolean("target-players", "Target players", true);
    private final SettingBoolean target_mobs_hostile = new SettingBoolean("target-hostile-mobs", "Target hostile mobs", true);
    private final SettingBoolean target_mobs_friendly = new SettingBoolean("target-friendly-mobs", "Target friendly mobs", false);
    private final SettingBoolean lag_compensation = new SettingBoolean("lag-comp", "Compensate for server lag", true); //TODO: implement later
    private final SettingFloat fov = new SettingFloat("fov", "Kill aura field of view  (0-180)", 180);
    private final SettingFloat range = new SettingFloat("range", "Kill aura range", 4.5f);
    private final SettingFloat cooldown_percent = new SettingFloat("cooldown-percent", "Minimum cooldown percent for the next strike", 100);

    public KillAura(KyleHax kyleHax){
        super(kyleHax, "killaura", Keyboard.KEY_R);

        //TODO: find a way to do this on setting constructor
        addSetting(silent);
        addSetting(auto_attack);
        addSetting(hold_target);
        addSetting(vis_check);
        addSetting(target_players);
        addSetting(target_mobs_hostile);
        addSetting(target_mobs_friendly);
        addSetting(lag_compensation);
        addSetting(fov);
        addSetting(range);
        addSetting(cooldown_percent);
    }

    private boolean canAttack(EntityPlayer localPlayer, Entity target) {
        final float cdRatio = cooldown_percent.getValue() / 100F;
        final float cdOffset = cdRatio <= 1F ? 0F : -(localPlayer.getCooldownPeriod() * (cdRatio - 1F));
        return localPlayer.getCooledAttackStrength((float) 0 + cdOffset) //this where you calculate lag compensation
                >= (Math.min(1F, cdRatio))
                && (auto_attack.getValue() || kyleHax.mc.gameSettings.keyBindAttack.isKeyDown());
    }

    private boolean isVisible(Entity target) {
        return !vis_check.getValue() || kyleHax.mc.player.canEntityBeSeen(target);
    }

    private Vec3d getAttackPosition(Entity entity) {
        return EntityUtils.getInterpolatedPos(entity, 1).addVector(0, entity.getEyeHeight() / 2, 0);
    }

    private boolean isFiltered(Entity entity) {
        switch (EntityUtils.getRelationship(entity)) {
            case PLAYER:
                return target_players.getValue();
            case FRIENDLY:
            case NEUTRAL:
                return target_mobs_friendly.getValue();
            case HOSTILE:
                return target_mobs_hostile.getValue();
            case INVALID:
            default:
                return false;
        }
    }

    private boolean isInRange(Vec3d from, Vec3d to) {
        double dist = range.getValue();
        return dist <= 0 || from.distanceTo(to) <= dist;
    }

    private boolean isInFov(Angle angle, Vec3d pos) {
        double fov = this.fov.getValue();
        if (fov >= 180) {
            return true;
        } else {
            Angle look = AngleHelper.getAngleFacingInDegrees(pos);
            Angle diff = angle.sub(look.getPitch(), look.getYaw()).normalize();
            return Math.abs(diff.getPitch()) <= fov && Math.abs(diff.getYaw()) <= fov;
        }
    }

    private double selecting(final Vec3d pos, final Vec3d viewNormal, final Angle angles, final Entity entity) {
//        switch (selector.get()) {
//            case Selector.DISTANCE:
//                return getAttackPosition(entity).subtract(pos).lengthSquared();
//            case Selector.CROSSHAIR:
//            default:
//                return getAttackPosition(entity)
//                        .subtract(pos)
//                        .normalize()
//                        .subtract(viewNormal)
//                        .lengthSquared();
//        }

        //defaulting to crosshair for now
        return getAttackPosition(entity)
        .subtract(pos)
        .normalize()
        .subtract(viewNormal)
        .lengthSquared();
    }

    //check if entity is a valid target
    private boolean filterTarget(Vec3d pos, Vec3d viewNormal, Angle angles, Entity entity) {
        final Vec3d tpos = getAttackPosition(entity);
        return Optional.of(entity)
                .filter(EntityUtils::isLiving)
                .filter(EntityUtils::isAlive)
                .filter(EntityUtils::isValidEntity)
                .filter(ent -> !ent.equals(kyleHax.mc.player))
                .filter(this::isFiltered)
                .filter(ent -> isInRange(tpos, pos))
                .filter(ent -> isInFov(angles, tpos.subtract(pos)))
                .filter(this::isVisible)
                .isPresent();
    }

    private Entity findTarget(final Vec3d pos, final Vec3d viewNormal, final Angle angles){
        return Minecraft.getMinecraft().world.loadedEntityList
                .stream()
                .filter(entity -> filterTarget(pos, viewNormal, angles, entity))
                .min(Comparator.comparingDouble(entity -> selecting(pos, viewNormal, angles, entity)))
                .orElse(null);
    }

    @Override
    public void onLivingUpdate(EntityPlayerSP player){
        Vec3d pos = EntityUtils.getEyePos(player);
        Vec3d look = player.getLookVec();

        Angle angles = AngleHelper.getAngleFacingInDegrees(look);

        Entity entity = getTarget();
        if(!hold_target.getValue() || entity == null || !filterTarget(pos, look.normalize(), angles, getTarget())){
            setTarget(entity = findTarget(pos, look.normalize(), angles));
        }

        if(entity == null){
            return;
        }

        final Entity target = entity;

        Angle viewAngles = EntityUtils.getLookAtAngles(target).normalize();
        //TODO: need to make mixin api to hook minecraft functions so can set client view angles
//        state.setViewAngles(va, silent.getValue());

        if(canAttack(player, target)){
            Minecraft.getMinecraft().playerController.attackEntity(player, target);
            player.swingArm(EnumHand.MAIN_HAND);
        }
    }


}
