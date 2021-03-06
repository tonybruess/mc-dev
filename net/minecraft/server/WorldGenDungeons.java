package net.minecraft.server;

import java.util.Random;

public class WorldGenDungeons extends WorldGenerator {

    private static final StructurePieceTreasure[] a = new StructurePieceTreasure[] { new StructurePieceTreasure(Item.SADDLE.id, 0, 1, 1, 10), new StructurePieceTreasure(Item.IRON_INGOT.id, 0, 1, 4, 10), new StructurePieceTreasure(Item.BREAD.id, 0, 1, 1, 10), new StructurePieceTreasure(Item.WHEAT.id, 0, 1, 4, 10), new StructurePieceTreasure(Item.SULPHUR.id, 0, 1, 4, 10), new StructurePieceTreasure(Item.STRING.id, 0, 1, 4, 10), new StructurePieceTreasure(Item.BUCKET.id, 0, 1, 1, 10), new StructurePieceTreasure(Item.GOLDEN_APPLE.id, 0, 1, 1, 1), new StructurePieceTreasure(Item.REDSTONE.id, 0, 1, 4, 10), new StructurePieceTreasure(Item.RECORD_1.id, 0, 1, 1, 10), new StructurePieceTreasure(Item.RECORD_2.id, 0, 1, 1, 10), new StructurePieceTreasure(Item.ci.id, 0, 1, 1, 10), new StructurePieceTreasure(Item.cf.id, 0, 1, 1, 2), new StructurePieceTreasure(Item.ce.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.cg.id, 0, 1, 1, 1)};

    public WorldGenDungeons() {}

    public boolean a(World world, Random random, int i, int j, int k) {
        byte b0 = 3;
        int l = random.nextInt(2) + 2;
        int i1 = random.nextInt(2) + 2;
        int j1 = 0;

        int k1;
        int l1;
        int i2;

        for (k1 = i - l - 1; k1 <= i + l + 1; ++k1) {
            for (l1 = j - 1; l1 <= j + b0 + 1; ++l1) {
                for (i2 = k - i1 - 1; i2 <= k + i1 + 1; ++i2) {
                    Material material = world.getMaterial(k1, l1, i2);

                    if (l1 == j - 1 && !material.isBuildable()) {
                        return false;
                    }

                    if (l1 == j + b0 + 1 && !material.isBuildable()) {
                        return false;
                    }

                    if ((k1 == i - l - 1 || k1 == i + l + 1 || i2 == k - i1 - 1 || i2 == k + i1 + 1) && l1 == j && world.isEmpty(k1, l1, i2) && world.isEmpty(k1, l1 + 1, i2)) {
                        ++j1;
                    }
                }
            }
        }

        if (j1 >= 1 && j1 <= 5) {
            for (k1 = i - l - 1; k1 <= i + l + 1; ++k1) {
                for (l1 = j + b0; l1 >= j - 1; --l1) {
                    for (i2 = k - i1 - 1; i2 <= k + i1 + 1; ++i2) {
                        if (k1 != i - l - 1 && l1 != j - 1 && i2 != k - i1 - 1 && k1 != i + l + 1 && l1 != j + b0 + 1 && i2 != k + i1 + 1) {
                            world.setAir(k1, l1, i2);
                        } else if (l1 >= 0 && !world.getMaterial(k1, l1 - 1, i2).isBuildable()) {
                            world.setAir(k1, l1, i2);
                        } else if (world.getMaterial(k1, l1, i2).isBuildable()) {
                            if (l1 == j - 1 && random.nextInt(4) != 0) {
                                world.setTypeIdAndData(k1, l1, i2, Block.MOSSY_COBBLESTONE.id, 0, 2);
                            } else {
                                world.setTypeIdAndData(k1, l1, i2, Block.COBBLESTONE.id, 0, 2);
                            }
                        }
                    }
                }
            }

            k1 = 0;

            while (k1 < 2) {
                l1 = 0;

                while (true) {
                    if (l1 < 3) {
                        label101: {
                            i2 = i + random.nextInt(l * 2 + 1) - l;
                            int j2 = k + random.nextInt(i1 * 2 + 1) - i1;

                            if (world.isEmpty(i2, j, j2)) {
                                int k2 = 0;

                                if (world.getMaterial(i2 - 1, j, j2).isBuildable()) {
                                    ++k2;
                                }

                                if (world.getMaterial(i2 + 1, j, j2).isBuildable()) {
                                    ++k2;
                                }

                                if (world.getMaterial(i2, j, j2 - 1).isBuildable()) {
                                    ++k2;
                                }

                                if (world.getMaterial(i2, j, j2 + 1).isBuildable()) {
                                    ++k2;
                                }

                                if (k2 == 1) {
                                    world.setTypeIdAndData(i2, j, j2, Block.CHEST.id, 0, 2);
                                    StructurePieceTreasure[] astructurepiecetreasure = StructurePieceTreasure.a(a, new StructurePieceTreasure[] { Item.ENCHANTED_BOOK.b(random)});
                                    TileEntityChest tileentitychest = (TileEntityChest) world.getTileEntity(i2, j, j2);

                                    if (tileentitychest != null) {
                                        StructurePieceTreasure.a(random, astructurepiecetreasure, (IInventory) tileentitychest, 8);
                                    }
                                    break label101;
                                }
                            }

                            ++l1;
                            continue;
                        }
                    }

                    ++k1;
                    break;
                }
            }

            world.setTypeIdAndData(i, j, k, Block.MOB_SPAWNER.id, 0, 2);
            TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner) world.getTileEntity(i, j, k);

            if (tileentitymobspawner != null) {
                tileentitymobspawner.a().a(this.a(random));
            } else {
                System.err.println("Failed to fetch mob spawner entity at (" + i + ", " + j + ", " + k + ")");
            }

            return true;
        } else {
            return false;
        }
    }

    private String a(Random random) {
        int i = random.nextInt(4);

        return i == 0 ? "Skeleton" : (i == 1 ? "Zombie" : (i == 2 ? "Zombie" : (i == 3 ? "Spider" : "")));
    }
}
