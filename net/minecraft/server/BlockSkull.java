package net.minecraft.server;

import java.util.Random;

public class BlockSkull extends BlockContainer {

    protected BlockSkull(int i) {
        super(i, Material.ORIENTABLE);
        this.a(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
    }

    public int d() {
        return -1;
    }

    public boolean c() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
        int l = iblockaccess.getData(i, j, k) & 7;

        switch (l) {
        case 1:
        default:
            this.a(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
            break;

        case 2:
            this.a(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
            break;

        case 3:
            this.a(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
            break;

        case 4:
            this.a(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
            break;

        case 5:
            this.a(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
        }
    }

    public AxisAlignedBB b(World world, int i, int j, int k) {
        this.updateShape(world, i, j, k);
        return super.b(world, i, j, k);
    }

    public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
        int l = MathHelper.floor((double) (entityliving.yaw * 4.0F / 360.0F) + 2.5D) & 3;

        world.setData(i, j, k, l, 2);
    }

    public TileEntity b(World world) {
        return new TileEntitySkull();
    }

    public int getDropData(World world, int i, int j, int k) {
        TileEntity tileentity = world.getTileEntity(i, j, k);

        return tileentity != null && tileentity instanceof TileEntitySkull ? ((TileEntitySkull) tileentity).getSkullType() : super.getDropData(world, i, j, k);
    }

    public int getDropData(int i) {
        return i;
    }

    public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1) {}

    public void a(World world, int i, int j, int k, int l, EntityHuman entityhuman) {
        if (entityhuman.abilities.canInstantlyBuild) {
            l |= 8;
            world.setData(i, j, k, l, 4);
        }

        super.a(world, i, j, k, l, entityhuman);
    }

    public void remove(World world, int i, int j, int k, int l, int i1) {
        if (!world.isStatic) {
            if ((i1 & 8) == 0) {
                ItemStack itemstack = new ItemStack(Item.SKULL.id, 1, this.getDropData(world, i, j, k));
                TileEntitySkull tileentityskull = (TileEntitySkull) world.getTileEntity(i, j, k);

                if (tileentityskull.getSkullType() == 3 && tileentityskull.getExtraType() != null && tileentityskull.getExtraType().length() > 0) {
                    itemstack.setTag(new NBTTagCompound());
                    itemstack.getTag().setString("SkullOwner", tileentityskull.getExtraType());
                }

                this.b(world, i, j, k, itemstack);
            }

            super.remove(world, i, j, k, l, i1);
        }
    }

    public int getDropType(int i, Random random, int j) {
        return Item.SKULL.id;
    }

    public void a(World world, int i, int j, int k, TileEntitySkull tileentityskull) {
        if (tileentityskull.getSkullType() == 1 && j >= 2 && world.difficulty > 0 && !world.isStatic) {
            int l = Block.SOUL_SAND.id;

            int i1;
            EntityWither entitywither;
            int j1;

            for (i1 = -2; i1 <= 0; ++i1) {
                if (world.getTypeId(i, j - 1, k + i1) == l && world.getTypeId(i, j - 1, k + i1 + 1) == l && world.getTypeId(i, j - 2, k + i1 + 1) == l && world.getTypeId(i, j - 1, k + i1 + 2) == l && this.d(world, i, j, k + i1, 1) && this.d(world, i, j, k + i1 + 1, 1) && this.d(world, i, j, k + i1 + 2, 1)) {
                    world.setData(i, j, k + i1, 8, 2);
                    world.setData(i, j, k + i1 + 1, 8, 2);
                    world.setData(i, j, k + i1 + 2, 8, 2);
                    world.setTypeIdAndData(i, j, k + i1, 0, 0, 2);
                    world.setTypeIdAndData(i, j, k + i1 + 1, 0, 0, 2);
                    world.setTypeIdAndData(i, j, k + i1 + 2, 0, 0, 2);
                    world.setTypeIdAndData(i, j - 1, k + i1, 0, 0, 2);
                    world.setTypeIdAndData(i, j - 1, k + i1 + 1, 0, 0, 2);
                    world.setTypeIdAndData(i, j - 1, k + i1 + 2, 0, 0, 2);
                    world.setTypeIdAndData(i, j - 2, k + i1 + 1, 0, 0, 2);
                    if (!world.isStatic) {
                        entitywither = new EntityWither(world);
                        entitywither.setPositionRotation((double) i + 0.5D, (double) j - 1.45D, (double) (k + i1) + 1.5D, 90.0F, 0.0F);
                        entitywither.aN = 90.0F;
                        entitywither.bP();
                        world.addEntity(entitywither);
                    }

                    for (j1 = 0; j1 < 120; ++j1) {
                        world.addParticle("snowballpoof", (double) i + world.random.nextDouble(), (double) (j - 2) + world.random.nextDouble() * 3.9D, (double) (k + i1 + 1) + world.random.nextDouble(), 0.0D, 0.0D, 0.0D);
                    }

                    world.update(i, j, k + i1, 0);
                    world.update(i, j, k + i1 + 1, 0);
                    world.update(i, j, k + i1 + 2, 0);
                    world.update(i, j - 1, k + i1, 0);
                    world.update(i, j - 1, k + i1 + 1, 0);
                    world.update(i, j - 1, k + i1 + 2, 0);
                    world.update(i, j - 2, k + i1 + 1, 0);
                    return;
                }
            }

            for (i1 = -2; i1 <= 0; ++i1) {
                if (world.getTypeId(i + i1, j - 1, k) == l && world.getTypeId(i + i1 + 1, j - 1, k) == l && world.getTypeId(i + i1 + 1, j - 2, k) == l && world.getTypeId(i + i1 + 2, j - 1, k) == l && this.d(world, i + i1, j, k, 1) && this.d(world, i + i1 + 1, j, k, 1) && this.d(world, i + i1 + 2, j, k, 1)) {
                    world.setData(i + i1, j, k, 8, 2);
                    world.setData(i + i1 + 1, j, k, 8, 2);
                    world.setData(i + i1 + 2, j, k, 8, 2);
                    world.setTypeIdAndData(i + i1, j, k, 0, 0, 2);
                    world.setTypeIdAndData(i + i1 + 1, j, k, 0, 0, 2);
                    world.setTypeIdAndData(i + i1 + 2, j, k, 0, 0, 2);
                    world.setTypeIdAndData(i + i1, j - 1, k, 0, 0, 2);
                    world.setTypeIdAndData(i + i1 + 1, j - 1, k, 0, 0, 2);
                    world.setTypeIdAndData(i + i1 + 2, j - 1, k, 0, 0, 2);
                    world.setTypeIdAndData(i + i1 + 1, j - 2, k, 0, 0, 2);
                    if (!world.isStatic) {
                        entitywither = new EntityWither(world);
                        entitywither.setPositionRotation((double) (i + i1) + 1.5D, (double) j - 1.45D, (double) k + 0.5D, 0.0F, 0.0F);
                        entitywither.bP();
                        world.addEntity(entitywither);
                    }

                    for (j1 = 0; j1 < 120; ++j1) {
                        world.addParticle("snowballpoof", (double) (i + i1 + 1) + world.random.nextDouble(), (double) (j - 2) + world.random.nextDouble() * 3.9D, (double) k + world.random.nextDouble(), 0.0D, 0.0D, 0.0D);
                    }

                    world.update(i + i1, j, k, 0);
                    world.update(i + i1 + 1, j, k, 0);
                    world.update(i + i1 + 2, j, k, 0);
                    world.update(i + i1, j - 1, k, 0);
                    world.update(i + i1 + 1, j - 1, k, 0);
                    world.update(i + i1 + 2, j - 1, k, 0);
                    world.update(i + i1 + 1, j - 2, k, 0);
                    return;
                }
            }
        }
    }

    private boolean d(World world, int i, int j, int k, int l) {
        if (world.getTypeId(i, j, k) != this.id) {
            return false;
        } else {
            TileEntity tileentity = world.getTileEntity(i, j, k);

            return tileentity != null && tileentity instanceof TileEntitySkull ? ((TileEntitySkull) tileentity).getSkullType() == l : false;
        }
    }
}
