package net.minecraft.server;

public class EntitySilverfish extends EntityMonster {

    private int goalTarget;

    public EntitySilverfish(World world) {
        super(world);
        this.a(0.3F, 0.7F);
    }

    protected void ax() {
        super.ax();
        this.a(ItemHayStack.a).a(8.0D);
        this.a(ItemHayStack.d).a(0.6000000238418579D);
        this.a(ItemHayStack.e).a(1.0D);
    }

    protected boolean e_() {
        return false;
    }

    protected Entity findTarget() {
        double d0 = 8.0D;

        return this.world.findNearbyVulnerablePlayer(this, d0);
    }

    protected String r() {
        return "mob.silverfish.say";
    }

    protected String aK() {
        return "mob.silverfish.hit";
    }

    protected String aL() {
        return "mob.silverfish.kill";
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (this.isInvulnerable()) {
            return false;
        } else {
            if (this.goalTarget <= 0 && (damagesource instanceof EntityDamageSource || damagesource == DamageSource.MAGIC)) {
                this.goalTarget = 20;
            }

            return super.damageEntity(damagesource, f);
        }
    }

    protected void a(Entity entity, float f) {
        if (this.aC <= 0 && f < 1.2F && entity.boundingBox.e > this.boundingBox.b && entity.boundingBox.b < this.boundingBox.e) {
            this.aC = 20;
            this.m(entity);
        }
    }

    protected void a(int i, int j, int k, int l) {
        this.makeSound("mob.silverfish.step", 0.15F, 1.0F);
    }

    protected int getLootId() {
        return 0;
    }

    public void l_() {
        this.aN = this.yaw;
        super.l_();
    }

    protected void bh() {
        super.bh();
        if (!this.world.isStatic) {
            int i;
            int j;
            int k;
            int l;

            if (this.goalTarget > 0) {
                --this.goalTarget;
                if (this.goalTarget == 0) {
                    i = MathHelper.floor(this.locX);
                    j = MathHelper.floor(this.locY);
                    k = MathHelper.floor(this.locZ);
                    boolean flag = false;

                    for (l = 0; !flag && l <= 5 && l >= -5; l = l <= 0 ? 1 - l : 0 - l) {
                        for (int i1 = 0; !flag && i1 <= 10 && i1 >= -10; i1 = i1 <= 0 ? 1 - i1 : 0 - i1) {
                            for (int j1 = 0; !flag && j1 <= 10 && j1 >= -10; j1 = j1 <= 0 ? 1 - j1 : 0 - j1) {
                                int k1 = this.world.getTypeId(i + i1, j + l, k + j1);

                                if (k1 == Block.MONSTER_EGGS.id) {
                                    if (!this.world.getGameRules().getBoolean("mobGriefing")) {
                                        int l1 = this.world.getData(i + i1, j + l, k + j1);
                                        Block block = Block.STONE;

                                        if (l1 == 1) {
                                            block = Block.COBBLESTONE;
                                        }

                                        if (l1 == 2) {
                                            block = Block.SMOOTH_BRICK;
                                        }

                                        this.world.setTypeIdAndData(i + i1, j + l, k + j1, block.id, 0, 3);
                                    } else {
                                        this.world.setAir(i + i1, j + l, k + j1, false);
                                    }

                                    Block.MONSTER_EGGS.postBreak(this.world, i + i1, j + l, k + j1, 0);
                                    if (this.random.nextBoolean()) {
                                        flag = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (this.target == null && !this.bI()) {
                i = MathHelper.floor(this.locX);
                j = MathHelper.floor(this.locY + 0.5D);
                k = MathHelper.floor(this.locZ);
                int i2 = this.random.nextInt(6);

                l = this.world.getTypeId(i + Facing.b[i2], j + Facing.c[i2], k + Facing.d[i2]);
                if (BlockMonsterEggs.d(l)) {
                    this.world.setTypeIdAndData(i + Facing.b[i2], j + Facing.c[i2], k + Facing.d[i2], Block.MONSTER_EGGS.id, BlockMonsterEggs.e(l), 3);
                    this.q();
                    this.die();
                } else {
                    this.bG();
                }
            } else if (this.target != null && !this.bI()) {
                this.target = null;
            }
        }
    }

    public float a(int i, int j, int k) {
        return this.world.getTypeId(i, j - 1, k) == Block.STONE.id ? 10.0F : super.a(i, j, k);
    }

    protected boolean i_() {
        return true;
    }

    public boolean canSpawn() {
        if (super.canSpawn()) {
            EntityHuman entityhuman = this.world.findNearbyPlayer(this, 5.0D);

            return entityhuman == null;
        } else {
            return false;
        }
    }

    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.ARTHROPOD;
    }
}
