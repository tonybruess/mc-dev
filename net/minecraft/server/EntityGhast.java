package net.minecraft.server;

public class EntityGhast extends EntityFlying implements IMonster {

    public int lookController;
    public double moveController;
    public double jumpController;
    public double senses;
    private Entity target;
    private int equipment;
    public int navigation;
    public int goalTarget;
    private int explosionPower = 1;

    public EntityGhast(World world) {
        super(world);
        this.a(4.0F, 4.0F);
        this.fireProof = true;
        this.b = 5;
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (this.isInvulnerable()) {
            return false;
        } else if ("fireball".equals(damagesource.n()) && damagesource.getEntity() instanceof EntityHuman) {
            super.damageEntity(damagesource, 1000.0F);
            ((EntityHuman) damagesource.getEntity()).a((Statistic) AchievementList.y);
            return true;
        } else {
            return super.damageEntity(damagesource, f);
        }
    }

    protected void a() {
        super.a();
        this.datawatcher.a(16, Byte.valueOf((byte) 0));
    }

    protected void ax() {
        super.ax();
        this.a(ItemHayStack.a).a(10.0D);
    }

    protected void bh() {
        if (!this.world.isStatic && this.world.difficulty == 0) {
            this.die();
        }

        this.bk();
        this.navigation = this.goalTarget;
        double d0 = this.moveController - this.locX;
        double d1 = this.jumpController - this.locY;
        double d2 = this.senses - this.locZ;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;

        if (d3 < 1.0D || d3 > 3600.0D) {
            this.moveController = this.locX + (double) ((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.jumpController = this.locY + (double) ((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.senses = this.locZ + (double) ((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        }

        if (this.lookController-- <= 0) {
            this.lookController += this.random.nextInt(5) + 2;
            d3 = (double) MathHelper.sqrt(d3);
            if (this.a(this.moveController, this.jumpController, this.senses, d3)) {
                this.motX += d0 / d3 * 0.1D;
                this.motY += d1 / d3 * 0.1D;
                this.motZ += d2 / d3 * 0.1D;
            } else {
                this.moveController = this.locX;
                this.jumpController = this.locY;
                this.senses = this.locZ;
            }
        }

        if (this.target != null && this.target.dead) {
            this.target = null;
        }

        if (this.target == null || this.equipment-- <= 0) {
            this.target = this.world.findNearbyVulnerablePlayer(this, 100.0D);
            if (this.target != null) {
                this.equipment = 20;
            }
        }

        double d4 = 64.0D;

        if (this.target != null && this.target.e((Entity) this) < d4 * d4) {
            double d5 = this.target.locX - this.locX;
            double d6 = this.target.boundingBox.b + (double) (this.target.length / 2.0F) - (this.locY + (double) (this.length / 2.0F));
            double d7 = this.target.locZ - this.locZ;

            this.aN = this.yaw = -((float) Math.atan2(d5, d7)) * 180.0F / 3.1415927F;
            if (this.o(this.target)) {
                if (this.goalTarget == 10) {
                    this.world.a((EntityHuman) null, 1007, (int) this.locX, (int) this.locY, (int) this.locZ, 0);
                }

                ++this.goalTarget;
                if (this.goalTarget == 20) {
                    this.world.a((EntityHuman) null, 1008, (int) this.locX, (int) this.locY, (int) this.locZ, 0);
                    EntityLargeFireball entitylargefireball = new EntityLargeFireball(this.world, this, d5, d6, d7);

                    entitylargefireball.justCreated = this.explosionPower;
                    double d8 = 4.0D;
                    Vec3D vec3d = this.j(1.0F);

                    entitylargefireball.locX = this.locX + vec3d.c * d8;
                    entitylargefireball.locY = this.locY + (double) (this.length / 2.0F) + 0.5D;
                    entitylargefireball.locZ = this.locZ + vec3d.e * d8;
                    this.world.addEntity(entitylargefireball);
                    this.goalTarget = -40;
                }
            } else if (this.goalTarget > 0) {
                --this.goalTarget;
            }
        } else {
            this.aN = this.yaw = -((float) Math.atan2(this.motX, this.motZ)) * 180.0F / 3.1415927F;
            if (this.goalTarget > 0) {
                --this.goalTarget;
            }
        }

        if (!this.world.isStatic) {
            byte b0 = this.datawatcher.getByte(16);
            byte b1 = (byte) (this.goalTarget > 10 ? 1 : 0);

            if (b0 != b1) {
                this.datawatcher.watch(16, Byte.valueOf(b1));
            }
        }
    }

    private boolean a(double d0, double d1, double d2, double d3) {
        double d4 = (this.moveController - this.locX) / d3;
        double d5 = (this.jumpController - this.locY) / d3;
        double d6 = (this.senses - this.locZ) / d3;
        AxisAlignedBB axisalignedbb = this.boundingBox.clone();

        for (int i = 1; (double) i < d3; ++i) {
            axisalignedbb.d(d4, d5, d6);
            if (!this.world.getCubes(this, axisalignedbb).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    protected String r() {
        return "mob.ghast.moan";
    }

    protected String aK() {
        return "mob.ghast.scream";
    }

    protected String aL() {
        return "mob.ghast.death";
    }

    protected int getLootId() {
        return Item.SULPHUR.id;
    }

    protected void dropDeathLoot(boolean flag, int i) {
        int j = this.random.nextInt(2) + this.random.nextInt(1 + i);

        int k;

        for (k = 0; k < j; ++k) {
            this.b(Item.GHAST_TEAR.id, 1);
        }

        j = this.random.nextInt(3) + this.random.nextInt(1 + i);

        for (k = 0; k < j; ++k) {
            this.b(Item.SULPHUR.id, 1);
        }
    }

    protected float aW() {
        return 10.0F;
    }

    public boolean canSpawn() {
        return this.random.nextInt(20) == 0 && super.canSpawn() && this.world.difficulty > 0;
    }

    public int br() {
        return 1;
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("ExplosionPower", this.explosionPower);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.hasKey("ExplosionPower")) {
            this.explosionPower = nbttagcompound.getInt("ExplosionPower");
        }
    }
}
