package net.minecraft.server;

import java.util.UUID;

public abstract class EntityCreature extends EntityLivingBase {

    public static final UUID lookController = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
    public static final AttributeModifier moveController = (new AttributeModifier(lookController, "Fleeing speed bonus", 2.0D, 2)).a(false);
    private PathEntity goalTarget;
    protected Entity target;
    protected boolean senses;
    protected int navigation;
    private ChunkCoordinates bq = new ChunkCoordinates(0, 0, 0);
    private float equipment = -1.0F;
    private PathfinderGoal canPickUpLoot = new PathfinderGoalMoveTowardsRestriction(this, 1.0D);
    private boolean persistent;

    public EntityCreature(World world) {
        super(world);
    }

    protected boolean bF() {
        return false;
    }

    protected void bh() {
        this.world.methodProfiler.a("ai");
        if (this.navigation > 0 && --this.navigation == 0) {
            AttributeInstance attributeinstance = this.a(ItemHayStack.d);

            attributeinstance.b(moveController);
        }

        this.senses = this.bF();
        float f11 = 16.0F;

        if (this.target == null) {
            this.target = this.findTarget();
            if (this.target != null) {
                this.goalTarget = this.world.findPath(this, this.target, f11, true, false, false, true);
            }
        } else if (this.target.isAlive()) {
            float f1 = this.target.d((Entity) this);

            if (this.o(this.target)) {
                this.a(this.target, f1);
            }
        } else {
            this.target = null;
        }

        this.world.methodProfiler.b();
        if (!this.senses && this.target != null && (this.goalTarget == null || this.random.nextInt(20) == 0)) {
            this.goalTarget = this.world.findPath(this, this.target, f11, true, false, false, true);
        } else if (!this.senses && (this.goalTarget == null && this.random.nextInt(180) == 0 || this.random.nextInt(120) == 0 || this.navigation > 0) && this.aV < 100) {
            this.bG();
        }

        int i = MathHelper.floor(this.boundingBox.b + 0.5D);
        boolean flag = this.G();
        boolean flag1 = this.I();

        this.pitch = 0.0F;
        if (this.goalTarget != null && this.random.nextInt(100) != 0) {
            this.world.methodProfiler.a("followpath");
            Vec3D vec3d = this.goalTarget.a((Entity) this);
            double d0 = (double) (this.width * 2.0F);

            while (vec3d != null && vec3d.d(this.locX, vec3d.d, this.locZ) < d0 * d0) {
                this.goalTarget.a();
                if (this.goalTarget.b()) {
                    vec3d = null;
                    this.goalTarget = null;
                } else {
                    vec3d = this.goalTarget.a((Entity) this);
                }
            }

            this.bd = false;
            if (vec3d != null) {
                double d1 = vec3d.c - this.locX;
                double d2 = vec3d.e - this.locZ;
                double d3 = vec3d.d - (double) i;
                float f2 = (float) (Math.atan2(d2, d1) * 180.0D / 3.1415927410125732D) - 90.0F;
                float f3 = MathHelper.g(f2 - this.yaw);

                this.bf = (float) this.a(ItemHayStack.d).e();
                if (f3 > 30.0F) {
                    f3 = 30.0F;
                }

                if (f3 < -30.0F) {
                    f3 = -30.0F;
                }

                this.yaw += f3;
                if (this.senses && this.target != null) {
                    double d4 = this.target.locX - this.locX;
                    double d5 = this.target.locZ - this.locZ;
                    float f4 = this.yaw;

                    this.yaw = (float) (Math.atan2(d5, d4) * 180.0D / 3.1415927410125732D) - 90.0F;
                    f3 = (f4 - this.yaw + 90.0F) * 3.1415927F / 180.0F;
                    this.be = -MathHelper.sin(f3) * this.bf * 1.0F;
                    this.bf = MathHelper.cos(f3) * this.bf * 1.0F;
                }

                if (d3 > 0.0D) {
                    this.bd = true;
                }
            }

            if (this.target != null) {
                this.a(this.target, 30.0F, 30.0F);
            }

            if (this.positionChanged && !this.bI()) {
                this.bd = true;
            }

            if (this.random.nextFloat() < 0.8F && (flag || flag1)) {
                this.bd = true;
            }

            this.world.methodProfiler.b();
        } else {
            super.bh();
            this.goalTarget = null;
        }
    }

    protected void bG() {
        this.world.methodProfiler.a("stroll");
        boolean flag = false;
        int i = -1;
        int j = -1;
        int k = -1;
        float f = -99999.0F;

        for (int l = 0; l < 10; ++l) {
            int i1 = MathHelper.floor(this.locX + (double) this.random.nextInt(13) - 6.0D);
            int j1 = MathHelper.floor(this.locY + (double) this.random.nextInt(7) - 3.0D);
            int k1 = MathHelper.floor(this.locZ + (double) this.random.nextInt(13) - 6.0D);
            float f1 = this.a(i1, j1, k1);

            if (f1 > f) {
                f = f1;
                i = i1;
                j = j1;
                k = k1;
                flag = true;
            }
        }

        if (flag) {
            this.goalTarget = this.world.a(this, i, j, k, 10.0F, true, false, false, true);
        }

        this.world.methodProfiler.b();
    }

    protected void a(Entity entity, float f) {}

    public float a(int i, int j, int k) {
        return 0.0F;
    }

    protected Entity findTarget() {
        return null;
    }

    public boolean canSpawn() {
        int i = MathHelper.floor(this.locX);
        int j = MathHelper.floor(this.boundingBox.b);
        int k = MathHelper.floor(this.locZ);

        return super.canSpawn() && this.a(i, j, k) >= 0.0F;
    }

    public boolean bI() {
        return this.goalTarget != null;
    }

    public void setPathEntity(PathEntity pathentity) {
        this.goalTarget = pathentity;
    }

    public Entity bJ() {
        return this.target;
    }

    public void setTarget(Entity entity) {
        this.target = entity;
    }

    public boolean bK() {
        return this.b(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
    }

    public boolean b(int i, int j, int k) {
        return this.equipment == -1.0F ? true : this.bq.e(i, j, k) < this.equipment * this.equipment;
    }

    public void b(int i, int j, int k, int l) {
        this.bq.b(i, j, k);
        this.equipment = (float) l;
    }

    public ChunkCoordinates bL() {
        return this.bq;
    }

    public float bM() {
        return this.equipment;
    }

    public void bN() {
        this.equipment = -1.0F;
    }

    public boolean bO() {
        return this.equipment != -1.0F;
    }

    protected void bB() {
        super.bB();
        if (this.bD() && this.bE() != null && this.bE().world == this.world) {
            Entity entity = this.bE();

            this.b((int) entity.locX, (int) entity.locY, (int) entity.locZ, 5);
            float f = this.d(entity);

            if (this instanceof EntityTameableAnimal && ((EntityTameableAnimal) this).isSitting()) {
                if (f > 10.0F) {
                    this.i(true);
                }

                return;
            }

            if (!this.persistent) {
                this.goalSelector.a(2, this.canPickUpLoot);
                this.getNavigation().a(false);
                this.persistent = true;
            }

            if (f > 4.0F) {
                this.getNavigation().a(entity, 1.0D);
            }

            if (f > 6.0F) {
                double d0 = (entity.locX - this.locX) / (double) f;
                double d1 = (entity.locY - this.locY) / (double) f;
                double d2 = (entity.locZ - this.locZ) / (double) f;

                this.motX += d0 * Math.abs(d0) * 0.4D;
                this.motY += d1 * Math.abs(d1) * 0.4D;
                this.motZ += d2 * Math.abs(d2) * 0.4D;
            }

            if (f > 10.0F) {
                this.i(true);
            }
        } else if (!this.bD() && this.persistent) {
            this.persistent = false;
            this.goalSelector.a(this.canPickUpLoot);
            this.getNavigation().a(true);
            this.bN();
        }
    }
}
