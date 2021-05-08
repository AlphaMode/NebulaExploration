package alphamode.core.nebula.items.Throwables;

import alphamode.core.nebula.entitys.NebulaEntities;
import alphamode.core.nebula.items.NebulaItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class MustardBomb extends ThrownItemEntity {
    public MustardBomb(World level, LivingEntity livingEntity) {
        super(NebulaEntities.MUSTARD_BOMB, livingEntity, level);
    }
    public MustardBomb(EntityType<? extends MustardBomb> entityEntityType, World level) {
        super(entityEntityType,level);
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte b) {

        if (b == 3) {
            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }

    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().damage(DamageSource.thrownProjectile(this, this.getOwner()), 2.0F);
    }
    private void makeAreaOfEffectCloud() {
        AreaEffectCloudEntity areaEffectCloud = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            areaEffectCloud.setOwner((LivingEntity)entity);
        }

        areaEffectCloud.setRadius(10.0F);
        areaEffectCloud.setRadiusOnUse(-0.5F);
        areaEffectCloud.setWaitTime(20);
        areaEffectCloud.setRadiusGrowth(-areaEffectCloud.getRadius() / (float)areaEffectCloud.getDuration());
        areaEffectCloud.setColor(0xF3F300);

        this.world.spawnEntity(areaEffectCloud);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 2.F, Explosion.DestructionType.BREAK);
            this.makeAreaOfEffectCloud();

            this.world.sendEntityStatus(this, (byte)3);
            this.remove();
        }

    }

    @Override
    protected Item getDefaultItem() {
        return NebulaItems.MUSTARD_BOMB;
    }
}
