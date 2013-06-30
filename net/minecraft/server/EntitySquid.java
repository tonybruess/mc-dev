package net.minecraft.server;

public class EntitySquid extends EntityWaterAnimal {

    public float goalTarget;
    public float bq;
    public float equipment;
    public float canPickUpLoot;
    public float persistent;
    public float bu;
    public float bv;
    public float bw;
    private float bx;
    private float by;
    private float bz;
    private float bA;
    private float bB;
    private float bC;

    public EntitySquid(World world) {
        super(world);
        this.a(0.95F, 0.95F);
        this.by = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
    }

    protected void ax() {
        super.ax();
        this.a(ItemHayStack.a).a(10.0D);
    }

    protected String r() {
        return null;
    }

    protected String aK() {
        return null;
    }

    protected String aL() {
        return null;
    }

    protected float aW() {
        return 0.4F;
    }

    protected int getLootId() {
        return 0;
    }

    protected boolean e_() {
        return false;
    }

    protected void dropDeathLoot(boolean flag, int i) {
        int j = this.random.nextInt(3 + i) + 1;

        for (int k = 0; k < j; ++k) {
            this.a(new ItemStack(Item.INK_SACK, 1, 0), 0.0F);
        }
    }

    public boolean G() {
        return this.world.a(this.boundingBox.grow(0.0D, -0.6000000238418579D, 0.0D), Material.WATER, (Entity) this);
    }

    public void c() {
        super.c();
        this.bq = this.goalTarget;
        this.canPickUpLoot = this.equipment;
        this.bu = this.persistent;
        this.bw = this.bv;
        this.persistent += this.by;
        if (this.persistent > 6.2831855F) {
            this.persistent -= 6.2831855F;
            if (this.random.nextInt(10) == 0) {
                this.by = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
            }
        }

        if (this.G()) {
            float f;

            if (this.persistent < 3.1415927F) {
                f = this.persistent / 3.1415927F;
                this.bv = MathHelper.sin(f * f * 3.1415927F) * 3.1415927F * 0.25F;
                if ((double) f > 0.75D) {
                    this.bx = 1.0F;
                    this.bz = 1.0F;
                } else {
                    this.bz *= 0.8F;
                }
            } else {
                this.bv = 0.0F;
                this.bx *= 0.9F;
                this.bz *= 0.99F;
            }

            if (!this.world.isStatic) {
                this.motX = (double) (this.bA * this.bx);
                this.motY = (double) (this.bB * this.bx);
                this.motZ = (double) (this.bC * this.bx);
            }

            f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            this.aN += (-((float) Math.atan2(this.motX, this.motZ)) * 180.0F / 3.1415927F - this.aN) * 0.1F;
            this.yaw = this.aN;
            this.equipment += 3.1415927F * this.bz * 1.5F;
            this.goalTarget += (-((float) Math.atan2((double) f, this.motY)) * 180.0F / 3.1415927F - this.goalTarget) * 0.1F;
        } else {
            this.bv = MathHelper.abs(MathHelper.sin(this.persistent)) * 3.1415927F * 0.25F;
            if (!this.world.isStatic) {
                this.motX = 0.0D;
                this.motY -= 0.08D;
                this.motY *= 0.9800000190734863D;
                this.motZ = 0.0D;
            }

            this.goalTarget = (float) ((double) this.goalTarget + (double) (-90.0F - this.goalTarget) * 0.02D);
        }
    }

    public void e(float f, float f1) {
        this.move(this.motX, this.motY, this.motZ);
    }

    protected void bh() {
        ++this.aV;
        if (this.aV > 100) {
            this.bA = this.bB = this.bC = 0.0F;
        } else if (this.random.nextInt(50) == 0 || !this.inWater || this.bA == 0.0F && this.bB == 0.0F && this.bC == 0.0F) {
            float f = this.random.nextFloat() * 3.1415927F * 2.0F;

            this.bA = MathHelper.cos(f) * 0.2F;
            this.bB = -0.1F + this.random.nextFloat() * 0.2F;
            this.bC = MathHelper.sin(f) * 0.2F;
        }

        this.bk();
    }

    public boolean canSpawn() {
        return this.locY > 45.0D && this.locY < 63.0D && super.canSpawn();
    }
}
