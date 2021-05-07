package alphamode.core.nebula.items.Throwables;

import alphamode.core.nebula.entitys.NebulaEntities;
import alphamode.core.nebula.items.NebulaItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class MustardBomb extends ThrowableItemProjectile {
    public MustardBomb(Level level, LivingEntity livingEntity) {
        super(NebulaEntities.MUSTARD_BOMB, livingEntity, level);
    }
    public MustardBomb(EntityType<? extends MustardBomb> entityEntityType, Level level) {
        super(entityEntityType,level);
    }

    @Environment(EnvType.CLIENT)
    public void handleEntityEvent(byte b) {

        if (b == 3) {
            for(int i = 0; i < 8; ++i) {
                this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }

    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        entityHitResult.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 2.0F);
    }
    private void makeAreaOfEffectCloud() {
        AreaEffectCloud areaEffectCloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            areaEffectCloud.setOwner((LivingEntity)entity);
        }

        areaEffectCloud.setRadius(10.0F);
        areaEffectCloud.setRadiusOnUse(-0.5F);
        areaEffectCloud.setWaitTime(20);
        areaEffectCloud.setRadiusPerTick(-areaEffectCloud.getRadius() / (float)areaEffectCloud.getDuration());
        areaEffectCloud.setFixedColor(0xF3F300);

        this.level.addFreshEntity(areaEffectCloud);
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level.isClientSide) {
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2.F, Explosion.BlockInteraction.BREAK);
            this.makeAreaOfEffectCloud();

            this.level.broadcastEntityEvent(this, (byte)3);
            this.remove();
        }

    }

    @Override
    protected Item getDefaultItem() {
        return NebulaItems.MUSTARD_BOMB;
    }
}
