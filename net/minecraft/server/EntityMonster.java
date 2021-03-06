package net.minecraft.server;

public abstract class EntityMonster extends EntityCreature implements IMonster {

    public EntityMonster(World world) {
        super(world);
        this.b = 5;
    }

    public void c() {
        this.aS();
        float f = this.d(1.0F);

        if (f > 0.5F) {
            this.aV += 2;
        }

        super.c();
    }

    public void l_() {
        super.l_();
        if (!this.world.isStatic && this.world.difficulty == 0) {
            this.die();
        }
    }

    protected Entity findTarget() {
        EntityHuman entityhuman = this.world.findNearbyVulnerablePlayer(this, 16.0D);

        return entityhuman != null && this.o(entityhuman) ? entityhuman : null;
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (this.isInvulnerable()) {
            return false;
        } else if (super.damageEntity(damagesource, f)) {
            Entity entity = damagesource.getEntity();

            if (this.passenger != entity && this.vehicle != entity) {
                if (entity != this) {
                    this.target = entity;
                }

                return true;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean m(Entity entity) {
        float f = (float) this.a(ItemHayStack.e).e();
        int i = 0;

        if (entity instanceof EntityLiving) {
            f += EnchantmentManager.a((EntityLiving) this, (EntityLiving) entity);
            i += EnchantmentManager.b(this, (EntityLiving) entity);
        }

        boolean flag = entity.damageEntity(DamageSource.a((EntityLiving) this), f);

        if (flag) {
            if (i > 0) {
                entity.g((double) (-MathHelper.sin(this.yaw * 3.1415927F / 180.0F) * (float) i * 0.5F), 0.1D, (double) (MathHelper.cos(this.yaw * 3.1415927F / 180.0F) * (float) i * 0.5F));
                this.motX *= 0.6D;
                this.motZ *= 0.6D;
            }

            int j = EnchantmentManager.a((EntityLiving) this);

            if (j > 0) {
                entity.setOnFire(j * 4);
            }

            if (entity instanceof EntityLiving) {
                EnchantmentThorns.a(this, (EntityLiving) entity, this.random);
            }
        }

        return flag;
    }

    protected void a(Entity entity, float f) {
        if (this.aC <= 0 && f < 2.0F && entity.boundingBox.e > this.boundingBox.b && entity.boundingBox.b < this.boundingBox.e) {
            this.aC = 20;
            this.m(entity);
        }
    }

    public float a(int i, int j, int k) {
        return 0.5F - this.world.q(i, j, k);
    }

    protected boolean i_() {
        int i = MathHelper.floor(this.locX);
        int j = MathHelper.floor(this.boundingBox.b);
        int k = MathHelper.floor(this.locZ);

        if (this.world.b(EnumSkyBlock.SKY, i, j, k) > this.random.nextInt(32)) {
            return false;
        } else {
            int l = this.world.getLightLevel(i, j, k);

            if (this.world.P()) {
                int i1 = this.world.j;

                this.world.j = 10;
                l = this.world.getLightLevel(i, j, k);
                this.world.j = i1;
            }

            return l <= this.random.nextInt(8);
        }
    }

    public boolean canSpawn() {
        return this.world.difficulty > 0 && this.i_() && super.canSpawn();
    }

    protected void ax() {
        super.ax();
        this.aT().b(ItemHayStack.e);
    }
}
