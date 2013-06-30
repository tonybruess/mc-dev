package net.minecraft.server;

public class EntityBlaze extends EntityMonster {

    private float goalTarget = 0.5F;
    private int bq;
    private int equipment;

    public EntityBlaze(World world) {
        super(world);
        this.fireProof = true;
        this.b = 10;
    }

    protected void ax() {
        super.ax();
        this.a(ItemHayStack.e).a(6.0D);
    }

    protected void a() {
        super.a();
        this.datawatcher.a(16, new Byte((byte) 0));
    }

    protected String r() {
        return "mob.blaze.breathe";
    }

    protected String aK() {
        return "mob.blaze.hit";
    }

    protected String aL() {
        return "mob.blaze.death";
    }

    public float d(float f) {
        return 1.0F;
    }

    public void c() {
        if (!this.world.isStatic) {
            if (this.F()) {
                this.damageEntity(DamageSource.DROWN, 1.0F);
            }

            --this.bq;
            if (this.bq <= 0) {
                this.bq = 100;
                this.goalTarget = 0.5F + (float) this.random.nextGaussian() * 3.0F;
            }

            if (this.bJ() != null && this.bJ().locY + (double) this.bJ().getHeadHeight() > this.locY + (double) this.getHeadHeight() + (double) this.goalTarget) {
                this.motY += (0.30000001192092896D - this.motY) * 0.30000001192092896D;
            }
        }

        if (this.random.nextInt(24) == 0) {
            this.world.makeSound(this.locX + 0.5D, this.locY + 0.5D, this.locZ + 0.5D, "fire.fire", 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F);
        }

        if (!this.onGround && this.motY < 0.0D) {
            this.motY *= 0.6D;
        }

        for (int i = 0; i < 2; ++i) {
            this.world.addParticle("largesmoke", this.locX + (this.random.nextDouble() - 0.5D) * (double) this.width, this.locY + this.random.nextDouble() * (double) this.length, this.locZ + (this.random.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);
        }

        super.c();
    }

    protected void a(Entity entity, float f) {
        if (this.aC <= 0 && f < 2.0F && entity.boundingBox.e > this.boundingBox.b && entity.boundingBox.b < this.boundingBox.e) {
            this.aC = 20;
            this.m(entity);
        } else if (f < 30.0F) {
            double d0 = entity.locX - this.locX;
            double d1 = entity.boundingBox.b + (double) (entity.length / 2.0F) - (this.locY + (double) (this.length / 2.0F));
            double d2 = entity.locZ - this.locZ;

            if (this.aC == 0) {
                ++this.equipment;
                if (this.equipment == 1) {
                    this.aC = 60;
                    this.a(true);
                } else if (this.equipment <= 4) {
                    this.aC = 6;
                } else {
                    this.aC = 100;
                    this.equipment = 0;
                    this.a(false);
                }

                if (this.equipment > 1) {
                    float f1 = MathHelper.c(f) * 0.5F;

                    this.world.a((EntityHuman) null, 1009, (int) this.locX, (int) this.locY, (int) this.locZ, 0);

                    for (int i = 0; i < 1; ++i) {
                        EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.world, this, d0 + this.random.nextGaussian() * (double) f1, d1, d2 + this.random.nextGaussian() * (double) f1);

                        entitysmallfireball.locY = this.locY + (double) (this.length / 2.0F) + 0.5D;
                        this.world.addEntity(entitysmallfireball);
                    }
                }
            }

            this.yaw = (float) (Math.atan2(d2, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
            this.senses = true;
        }
    }

    protected void b(float f) {}

    protected int getLootId() {
        return Item.BLAZE_ROD.id;
    }

    public boolean isBurning() {
        return this.bP();
    }

    protected void dropDeathLoot(boolean flag, int i) {
        if (flag) {
            int j = this.random.nextInt(2 + i);

            for (int k = 0; k < j; ++k) {
                this.b(Item.BLAZE_ROD.id, 1);
            }
        }
    }

    public boolean bP() {
        return (this.datawatcher.getByte(16) & 1) != 0;
    }

    public void a(boolean flag) {
        byte b0 = this.datawatcher.getByte(16);

        if (flag) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 &= -2;
        }

        this.datawatcher.watch(16, Byte.valueOf(b0));
    }

    protected boolean i_() {
        return true;
    }
}
