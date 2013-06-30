package net.minecraft.server;

import java.util.Iterator;
import java.util.List;

public class ItemHay extends Item {

    public ItemHay(int i) {
        super(i);
        this.a(CreativeModeTab.i);
    }

    public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
        int i1 = world.getTypeId(i, j, k);

        if (Block.byId[i1] != null && Block.byId[i1].d() == 11) {
            if (world.isStatic) {
                return true;
            } else {
                a(entityhuman, world, i, j, k);
                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean a(EntityHuman entityhuman, World world, int i, int j, int k) {
        EntityLeashKnot entityleashknot = EntityLeashKnot.b(world, i, j, k);
        boolean flag = false;
        double d0 = 7.0D;
        List list = world.a(EntityLivingBase.class, AxisAlignedBB.a().a((double) i - d0, (double) j - d0, (double) k - d0, (double) i + d0, (double) j + d0, (double) k + d0));

        if (list != null) {
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityLivingBase entitylivingbase = (EntityLivingBase) iterator.next();

                if (entitylivingbase.bD() && entitylivingbase.bE() == entityhuman) {
                    if (entityleashknot == null) {
                        entityleashknot = EntityLeashKnot.a(world, i, j, k);
                    }

                    entitylivingbase.b(entityleashknot, true);
                    flag = true;
                }
            }
        }

        return flag;
    }
}
