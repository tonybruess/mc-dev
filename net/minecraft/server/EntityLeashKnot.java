package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class EntityLeashKnot extends EntityHanging {

    public EntityLeashKnot(World world) {
        super(world);
    }

    public EntityLeashKnot(World world, int i, int j, int k) {
        super(world, i, j, k, 0);
        this.setPosition((double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D);
    }

    protected void a() {
        super.a();
    }

    public void setDirection(int i) {}

    public int d() {
        return 9;
    }

    public int e() {
        return 9;
    }

    public void b(Entity entity) {}

    public boolean d(NBTTagCompound nbttagcompound) {
        return false;
    }

    public void b(NBTTagCompound nbttagcompound) {}

    public void a(NBTTagCompound nbttagcompound) {}

    public boolean c(EntityHuman entityhuman) {
        ItemStack itemstack = entityhuman.aV();
        boolean flag = false;

        if (itemstack != null && itemstack.id == Item.ch.id && !this.world.isStatic) {
            double d0 = 7.0D;
            List list = this.world.a(EntityLivingBase.class, AxisAlignedBB.a().a(this.locX - d0, this.locY - d0, this.locZ - d0, this.locX + d0, this.locY + d0, this.locZ + d0));

            if (list != null) {
                Iterator iterator = list.iterator();

                while (iterator.hasNext()) {
                    EntityLivingBase entitylivingbase = (EntityLivingBase) iterator.next();

                    if (entitylivingbase.bD() && entitylivingbase.bE() == entityhuman) {
                        entitylivingbase.b(this, true);
                        flag = true;
                    }
                }
            }
        }

        if (!this.world.isStatic && !flag) {
            this.die();
        }

        return true;
    }

    public boolean survives() {
        int i = this.world.getTypeId(this.x, this.y, this.z);

        return Block.byId[i] != null && Block.byId[i].d() == 11;
    }

    public static EntityLeashKnot a(World world, int i, int j, int k) {
        EntityLeashKnot entityleashknot = new EntityLeashKnot(world, i, j, k);

        entityleashknot.p = true;
        world.addEntity(entityleashknot);
        return entityleashknot;
    }

    public static EntityLeashKnot b(World world, int i, int j, int k) {
        List list = world.a(EntityLeashKnot.class, AxisAlignedBB.a().a((double) i - 1.0D, (double) j - 1.0D, (double) k - 1.0D, (double) i + 1.0D, (double) j + 1.0D, (double) k + 1.0D));
        Object object = null;

        if (list != null) {
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityLeashKnot entityleashknot = (EntityLeashKnot) iterator.next();

                if (entityleashknot.x == i && entityleashknot.y == j && entityleashknot.z == k) {
                    return entityleashknot;
                }
            }
        }

        return null;
    }
}
