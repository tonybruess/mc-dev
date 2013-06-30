package net.minecraft.server;

public class EntityIronGolem extends EntityGolem {

    private int bq;
    Village goalTarget;
    private int equipment;
    private int canPickUpLoot;

    public EntityIronGolem(World world) {
        super(world);
        this.a(1.4F, 2.9F);
        this.getNavigation().a(true);
        this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, 1.0D, true));
        this.goalSelector.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.9D, 32.0F));
        this.goalSelector.a(3, new PathfinderGoalMoveThroughVillage(this, 0.6D, true));
        this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(5, new PathfinderGoalOfferFlower(this));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.6D));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalDefendVillage(this));
        this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityLivingBase.class, 0, false, true, IMonster.a));
    }

    protected void a() {
        super.a();
        this.datawatcher.a(16, Byte.valueOf((byte) 0));
    }

    public boolean bb() {
        return true;
    }

    protected void bg() {
        if (--this.bq <= 0) {
            this.bq = 70 + this.random.nextInt(50);
            this.goalTarget = this.world.villages.getClosestVillage(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ), 32);
            if (this.goalTarget == null) {
                this.bN();
            } else {
                ChunkCoordinates chunkcoordinates = this.goalTarget.getCenter();

                this.b(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, (int) ((float) this.goalTarget.getSize() * 0.6F));
            }
        }

        super.bg();
    }

    protected void ax() {
        super.ax();
        this.a(ItemHayStack.a).a(100.0D);
        this.a(ItemHayStack.d).a(0.25D);
    }

    protected int h(int i) {
        return i;
    }

    protected void n(Entity entity) {
        if (entity instanceof IMonster && this.aB().nextInt(20) == 0) {
            this.c((EntityLiving) entity);
        }

        super.n(entity);
    }

    public void c() {
        super.c();
        if (this.equipment > 0) {
            --this.equipment;
        }

        if (this.canPickUpLoot > 0) {
            --this.canPickUpLoot;
        }

        if (this.motX * this.motX + this.motZ * this.motZ > 2.500000277905201E-7D && this.random.nextInt(5) == 0) {
            int i = MathHelper.floor(this.locX);
            int j = MathHelper.floor(this.locY - 0.20000000298023224D - (double) this.height);
            int k = MathHelper.floor(this.locZ);
            int l = this.world.getTypeId(i, j, k);

            if (l > 0) {
                this.world.addParticle("tilecrack_" + l + "_" + this.world.getData(i, j, k), this.locX + ((double) this.random.nextFloat() - 0.5D) * (double) this.width, this.boundingBox.b + 0.1D, this.locZ + ((double) this.random.nextFloat() - 0.5D) * (double) this.width, 4.0D * ((double) this.random.nextFloat() - 0.5D), 0.5D, ((double) this.random.nextFloat() - 0.5D) * 4.0D);
            }
        }
    }

    public boolean a(Class oclass) {
        return this.bS() && EntityHuman.class.isAssignableFrom(oclass) ? false : super.a(oclass);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setBoolean("PlayerCreated", this.bS());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setPlayerCreated(nbttagcompound.getBoolean("PlayerCreated"));
    }

    public boolean m(Entity entity) {
        this.equipment = 10;
        this.world.broadcastEntityEffect(this, (byte) 4);
        boolean flag = entity.damageEntity(DamageSource.a((EntityLiving) this), (float) (7 + this.random.nextInt(15)));

        if (flag) {
            entity.motY += 0.4000000059604645D;
        }

        this.makeSound("mob.irongolem.throw", 1.0F, 1.0F);
        return flag;
    }

    public Village bP() {
        return this.goalTarget;
    }

    public void a(boolean flag) {
        this.canPickUpLoot = flag ? 400 : 0;
        this.world.broadcastEntityEffect(this, (byte) 11);
    }

    protected String r() {
        return "none";
    }

    protected String aK() {
        return "mob.irongolem.hit";
    }

    protected String aL() {
        return "mob.irongolem.death";
    }

    protected void a(int i, int j, int k, int l) {
        this.makeSound("mob.irongolem.walk", 1.0F, 1.0F);
    }

    protected void dropDeathLoot(boolean flag, int i) {
        int j = this.random.nextInt(3);

        int k;

        for (k = 0; k < j; ++k) {
            this.b(Block.RED_ROSE.id, 1);
        }

        k = 3 + this.random.nextInt(3);

        for (int l = 0; l < k; ++l) {
            this.b(Item.IRON_INGOT.id, 1);
        }
    }

    public int bR() {
        return this.canPickUpLoot;
    }

    public boolean bS() {
        return (this.datawatcher.getByte(16) & 1) != 0;
    }

    public void setPlayerCreated(boolean flag) {
        byte b0 = this.datawatcher.getByte(16);

        if (flag) {
            this.datawatcher.watch(16, Byte.valueOf((byte) (b0 | 1)));
        } else {
            this.datawatcher.watch(16, Byte.valueOf((byte) (b0 & -2)));
        }
    }

    public void die(DamageSource damagesource) {
        if (!this.bS() && this.aS != null && this.goalTarget != null) {
            this.goalTarget.a(this.aS.getName(), -5);
        }

        super.a(damagesource);
    }
}
