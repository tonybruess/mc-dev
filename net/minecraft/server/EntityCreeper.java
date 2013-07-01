package net.minecraft.server;

public class EntityCreeper extends EntityMonster {

    private int bp;
    private int fuseTicks;
    private int maxFuseTicks = 30;
    private int explosionRadius = 3;

    public EntityCreeper(World world) {
        super(world);
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalSwell(this));
        this.goalSelector.a(3, new PathfinderGoalAvoidPlayer(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, false));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.8D));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
        this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false));
    }

    protected void ax() {
        super.ax();
        this.a(GenericAttributes.d).a(0.25D);
    }

    public boolean bb() {
        return true;
    }

    public int aq() {
        return this.getGoalTarget() == null ? 3 : 3 + (int) (this.getHealth() - 1.0F);
    }

    protected void b(float f) {
        super.b(f);
        this.fuseTicks = (int) ((float) this.fuseTicks + f * 1.5F);
        if (this.fuseTicks > this.maxFuseTicks - 5) {
            this.fuseTicks = this.maxFuseTicks - 5;
        }
    }

    protected void a() {
        super.a();
        this.datawatcher.a(16, Byte.valueOf((byte) -1));
        this.datawatcher.a(17, Byte.valueOf((byte) 0));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        if (this.datawatcher.getByte(17) == 1) {
            nbttagcompound.setBoolean("powered", true);
        }

        nbttagcompound.setShort("Fuse", (short) this.maxFuseTicks);
        nbttagcompound.setByte("ExplosionRadius", (byte) this.explosionRadius);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.datawatcher.watch(17, Byte.valueOf((byte) (nbttagcompound.getBoolean("powered") ? 1 : 0)));
        if (nbttagcompound.hasKey("Fuse")) {
            this.maxFuseTicks = nbttagcompound.getShort("Fuse");
        }

        if (nbttagcompound.hasKey("ExplosionRadius")) {
            this.explosionRadius = nbttagcompound.getByte("ExplosionRadius");
        }
    }

    public void l_() {
        if (this.isAlive()) {
            this.bp = this.fuseTicks;
            int i = this.bR();

            if (i > 0 && this.fuseTicks == 0) {
                this.makeSound("random.fuse", 1.0F, 0.5F);
            }

            this.fuseTicks += i;
            if (this.fuseTicks < 0) {
                this.fuseTicks = 0;
            }

            if (this.fuseTicks >= this.maxFuseTicks) {
                this.fuseTicks = this.maxFuseTicks;
                if (!this.world.isStatic) {
                    boolean flag = this.world.getGameRules().getBoolean("mobGriefing");

                    if (this.isPowered()) {
                        this.world.explode(this, this.locX, this.locY, this.locZ, (float) (this.explosionRadius * 2), flag);
                    } else {
                        this.world.explode(this, this.locX, this.locY, this.locZ, (float) this.explosionRadius, flag);
                    }

                    this.die();
                }
            }
        }

        super.l_();
    }

    protected String aK() {
        return "mob.creeper.say";
    }

    protected String aL() {
        return "mob.creeper.death";
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (damagesource.getEntity() instanceof EntitySkeleton) {
            int i = Item.RECORD_1.id + this.random.nextInt(Item.RECORD_12.id - Item.RECORD_1.id + 1);

            this.b(i, 1);
        }
    }

    public boolean m(Entity entity) {
        return true;
    }

    public boolean isPowered() {
        return this.datawatcher.getByte(17) == 1;
    }

    protected int getLootId() {
        return Item.SULPHUR.id;
    }

    public int bR() {
        return this.datawatcher.getByte(16);
    }

    public void a(int i) {
        this.datawatcher.watch(16, Byte.valueOf((byte) i));
    }

    public void a(EntityLightning entitylightning) {
        super.a(entitylightning);
        this.datawatcher.watch(17, Byte.valueOf((byte) 1));
    }
}
