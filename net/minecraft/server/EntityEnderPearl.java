package net.minecraft.server;

public class EntityEnderPearl extends EntityProjectile {

    public EntityEnderPearl(World world) {
        super(world);
    }

    public EntityEnderPearl(World world, EntityLiving entityliving) {
        super(world, entityliving);
    }

    protected void a(MovingObjectPosition movingobjectposition) {
        if (movingobjectposition.entity != null) {
            movingobjectposition.entity.damageEntity(DamageSource.projectile(this, this.h()), 0.0F);
        }

        for (int i = 0; i < 32; ++i) {
            this.world.addParticle("portal", this.locX, this.locY + this.random.nextDouble() * 2.0D, this.locZ, this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
        }

        if (!this.world.isStatic) {
            if (this.h() != null && this.h() instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) this.h();

                if (!entityplayer.playerConnection.disconnected && entityplayer.world == this.world) {
                    if (this.h().ae()) {
                        this.h().mount((Entity) null);
                    }

                    this.h().a(this.locX, this.locY, this.locZ);
                    this.h().fallDistance = 0.0F;
                    this.h().damageEntity(DamageSource.FALL, 5.0F);
                }
            }

            this.die();
        }
    }
}
