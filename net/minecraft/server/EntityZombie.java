package net.minecraft.server;

import java.util.Calendar;
import java.util.UUID;

public class EntityZombie extends EntityMonster {

    protected static final Attribute goalTarget = (new AttributeRanged("zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D)).a("Spawn Reinforcements Chance");
    private static final UUID bq = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
    private static final AttributeModifier equipment = new AttributeModifier(bq, "Baby speed boost", 0.5D, 0);
    private int canPickUpLoot;

    public EntityZombie(World world) {
        super(world);
        this.getNavigation().b(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalBreakDoor(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
        this.goalSelector.a(3, new PathfinderGoalMeleeAttack(this, EntityVillager.class, 1.0D, true));
        this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(5, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, 0, false));
    }

    protected void ax() {
        super.ax();
        this.a(ItemHayStack.b).a(40.0D);
        this.a(ItemHayStack.d).a(0.23000000417232513D);
        this.a(ItemHayStack.e).a(3.0D);
        this.aT().b(goalTarget).a(this.random.nextDouble() * 0.10000000149011612D);
    }

    protected void a() {
        super.a();
        this.getDataWatcher().a(12, Byte.valueOf((byte) 0));
        this.getDataWatcher().a(13, Byte.valueOf((byte) 0));
        this.getDataWatcher().a(14, Byte.valueOf((byte) 0));
    }

    public int aM() {
        int i = super.aM() + 2;

        if (i > 20) {
            i = 20;
        }

        return i;
    }

    protected boolean bb() {
        return true;
    }

    public boolean isBaby() {
        return this.getDataWatcher().getByte(12) == 1;
    }

    public void setBaby(boolean flag) {
        this.getDataWatcher().watch(12, Byte.valueOf((byte) (flag ? 1 : 0)));
        if (this.world != null && !this.world.isStatic) {
            AttributeInstance attributeinstance = this.a(ItemHayStack.d);

            attributeinstance.b(equipment);
            if (flag) {
                attributeinstance.a(equipment);
            }
        }
    }

    public boolean isVillager() {
        return this.getDataWatcher().getByte(13) == 1;
    }

    public void setVillager(boolean flag) {
        this.getDataWatcher().watch(13, Byte.valueOf((byte) (flag ? 1 : 0)));
    }

    public void c() {
        if (this.world.v() && !this.world.isStatic && !this.isBaby()) {
            float f = this.d(1.0F);

            if (f > 0.5F && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.l(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ))) {
                boolean flag = true;
                ItemStack itemstack = this.getEquipment(4);

                if (itemstack != null) {
                    if (itemstack.g()) {
                        itemstack.setData(itemstack.j() + this.random.nextInt(2));
                        if (itemstack.j() >= itemstack.l()) {
                            this.a(itemstack);
                            this.setEquipment(4, (ItemStack) null);
                        }
                    }

                    flag = false;
                }

                if (flag) {
                    this.setOnFire(8);
                }
            }
        }

        super.c();
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (!super.damageEntity(damagesource, f)) {
            return false;
        } else {
            EntityLiving entityliving = this.m();

            if (entityliving == null && this.bJ() instanceof EntityLiving) {
                entityliving = (EntityLiving) this.bJ();
            }

            if (entityliving == null && damagesource.getEntity() instanceof EntityLiving) {
                entityliving = (EntityLiving) damagesource.getEntity();
            }

            if (entityliving != null && this.world.difficulty >= 3 && (double) this.random.nextFloat() < this.a(goalTarget).e()) {
                int i = MathHelper.floor(this.locX);
                int j = MathHelper.floor(this.locY);
                int k = MathHelper.floor(this.locZ);
                EntityZombie entityzombie = new EntityZombie(this.world);

                for (int l = 0; l < 50; ++l) {
                    int i1 = i + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
                    int j1 = j + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
                    int k1 = k + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);

                    if (this.world.w(i1, j1 - 1, k1) && this.world.getLightLevel(i1, j1, k1) < 10) {
                        entityzombie.setPosition((double) i1, (double) j1, (double) k1);
                        if (this.world.b(entityzombie.boundingBox) && this.world.getCubes(entityzombie, entityzombie.boundingBox).isEmpty() && !this.world.containsLiquid(entityzombie.boundingBox)) {
                            this.world.addEntity(entityzombie);
                            entityzombie.c(entityliving);
                            entityzombie.a((EntityLivingData) null);
                            this.a(goalTarget).a(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
                            entityzombie.a(goalTarget).a(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
                            break;
                        }
                    }
                }
            }

            return true;
        }
    }

    public void l_() {
        if (!this.world.isStatic && this.bR()) {
            int i = this.bT();

            this.canPickUpLoot -= i;
            if (this.canPickUpLoot <= 0) {
                this.bS();
            }
        }

        super.l_();
    }

    public boolean m(Entity entity) {
        boolean flag = super.m(entity);

        if (flag && this.aV() == null && this.isBurning() && this.random.nextFloat() < (float) this.world.difficulty * 0.3F) {
            entity.setOnFire(2 * this.world.difficulty);
        }

        return flag;
    }

    protected String r() {
        return "mob.zombie.say";
    }

    protected String aK() {
        return "mob.zombie.hurt";
    }

    protected String aL() {
        return "mob.zombie.death";
    }

    protected void a(int i, int j, int k, int l) {
        this.makeSound("mob.zombie.step", 0.15F, 1.0F);
    }

    protected int getLootId() {
        return Item.ROTTEN_FLESH.id;
    }

    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.UNDEAD;
    }

    protected void l(int i) {
        switch (this.random.nextInt(3)) {
        case 0:
            this.b(Item.IRON_INGOT.id, 1);
            break;

        case 1:
            this.b(Item.CARROT.id, 1);
            break;

        case 2:
            this.b(Item.POTATO.id, 1);
        }
    }

    protected void bs() {
        super.bs();
        if (this.random.nextFloat() < (this.world.difficulty == 3 ? 0.05F : 0.01F)) {
            int i = this.random.nextInt(3);

            if (i == 0) {
                this.setEquipment(0, new ItemStack(Item.IRON_SWORD));
            } else {
                this.setEquipment(0, new ItemStack(Item.IRON_SPADE));
            }
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        if (this.isBaby()) {
            nbttagcompound.setBoolean("IsBaby", true);
        }

        if (this.isVillager()) {
            nbttagcompound.setBoolean("IsVillager", true);
        }

        nbttagcompound.setInt("ConversionTime", this.bR() ? this.canPickUpLoot : -1);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.getBoolean("IsBaby")) {
            this.setBaby(true);
        }

        if (nbttagcompound.getBoolean("IsVillager")) {
            this.setVillager(true);
        }

        if (nbttagcompound.hasKey("ConversionTime") && nbttagcompound.getInt("ConversionTime") > -1) {
            this.a(nbttagcompound.getInt("ConversionTime"));
        }
    }

    public void a(EntityLiving entityliving) {
        super.a(entityliving);
        if (this.world.difficulty >= 2 && entityliving instanceof EntityVillager) {
            if (this.world.difficulty == 2 && this.random.nextBoolean()) {
                return;
            }

            EntityZombie entityzombie = new EntityZombie(this.world);

            entityzombie.j(entityliving);
            this.world.kill(entityliving);
            entityzombie.a((EntityLivingData) null);
            entityzombie.setVillager(true);
            if (entityliving.g_()) {
                entityzombie.setBaby(true);
            }

            this.world.addEntity(entityzombie);
            this.world.a((EntityHuman) null, 1016, (int) this.locX, (int) this.locY, (int) this.locZ, 0);
        }
    }

    public EntityLivingData a(EntityLivingData entitylivingdata) {
        entitylivingdata = super.a(entitylivingdata);
        float f = this.world.b(this.locX, this.locY, this.locZ);

        this.h(this.random.nextFloat() < 0.55F * f);
        if (this.world.random.nextFloat() < 0.05F) {
            this.setVillager(true);
        }

        this.bs();
        this.bt();
        if (this.getEquipment(4) == null) {
            Calendar calendar = this.world.W();

            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.random.nextFloat() < 0.25F) {
                this.setEquipment(4, new ItemStack(this.random.nextFloat() < 0.1F ? Block.JACK_O_LANTERN : Block.PUMPKIN));
                this.dropChances[4] = 0.0F;
            }
        }

        this.a(ItemHayStack.c).a(new AttributeModifier("Random spawn bonus", this.random.nextDouble() * 0.05000000074505806D, 0));
        this.a(ItemHayStack.b).a(new AttributeModifier("Random zombie-spawn bonus", this.random.nextDouble() * 1.5D, 2));
        if (this.random.nextFloat() < f * 0.05F) {
            this.a(goalTarget).a(new AttributeModifier("Leader zombie bonus", this.random.nextDouble() * 0.25D + 0.5D, 0));
            this.a(ItemHayStack.a).a(new AttributeModifier("Leader zombie bonus", this.random.nextDouble() * 3.0D + 1.0D, 2));
        }

        return entitylivingdata;
    }

    public boolean a(EntityHuman entityhuman) {
        ItemStack itemstack = entityhuman.bt();

        if (itemstack != null && itemstack.getItem() == Item.GOLDEN_APPLE && itemstack.getData() == 0 && this.isVillager() && this.a(MobEffectList.WEAKNESS)) {
            if (!entityhuman.abilities.canInstantlyBuild) {
                --itemstack.count;
            }

            if (itemstack.count <= 0) {
                entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack) null);
            }

            if (!this.world.isStatic) {
                this.a(this.random.nextInt(2401) + 3600);
            }

            return true;
        } else {
            return false;
        }
    }

    protected void a(int i) {
        this.canPickUpLoot = i;
        this.getDataWatcher().watch(14, Byte.valueOf((byte) 1));
        this.k(MobEffectList.WEAKNESS.id);
        this.d(new MobEffect(MobEffectList.INCREASE_DAMAGE.id, i, Math.min(this.world.difficulty - 1, 0)));
        this.world.broadcastEntityEffect(this, (byte) 16);
    }

    protected boolean isTypeNotPersistent() {
        return !this.bR();
    }

    public boolean bR() {
        return this.getDataWatcher().getByte(14) == 1;
    }

    protected void bS() {
        EntityVillager entityvillager = new EntityVillager(this.world);

        entityvillager.j(this);
        entityvillager.a((EntityLivingData) null);
        entityvillager.bT();
        if (this.isBaby()) {
            entityvillager.setAge(-24000);
        }

        this.world.kill(this);
        this.world.addEntity(entityvillager);
        entityvillager.d(new MobEffect(MobEffectList.CONFUSION.id, 200, 0));
        this.world.a((EntityHuman) null, 1017, (int) this.locX, (int) this.locY, (int) this.locZ, 0);
    }

    protected int bT() {
        int i = 1;

        if (this.random.nextFloat() < 0.01F) {
            int j = 0;

            for (int k = (int) this.locX - 4; k < (int) this.locX + 4 && j < 14; ++k) {
                for (int l = (int) this.locY - 4; l < (int) this.locY + 4 && j < 14; ++l) {
                    for (int i1 = (int) this.locZ - 4; i1 < (int) this.locZ + 4 && j < 14; ++i1) {
                        int j1 = this.world.getTypeId(k, l, i1);

                        if (j1 == Block.IRON_FENCE.id || j1 == Block.BED.id) {
                            if (this.random.nextFloat() < 0.3F) {
                                ++i;
                            }

                            ++j;
                        }
                    }
                }
            }
        }

        return i;
    }
}
