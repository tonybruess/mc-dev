package net.minecraft.server;

import java.util.Random;

public class WorldGenLakes extends WorldGenerator {

    private int a;

    public WorldGenLakes(int i) {
        this.a = i;
    }

    public boolean a(World world, Random random, int i, int j, int k) {
        i -= 8;

        for (k -= 8; j > 5 && world.isEmpty(i, j, k); --j) {
            ;
        }

        if (j <= 4) {
            return false;
        } else {
            j -= 4;
            boolean[] aboolean = new boolean[2048];
            int l = random.nextInt(4) + 4;

            int i1;

            for (i1 = 0; i1 < l; ++i1) {
                double d0 = random.nextDouble() * 6.0D + 3.0D;
                double d1 = random.nextDouble() * 4.0D + 2.0D;
                double d2 = random.nextDouble() * 6.0D + 3.0D;
                double d3 = random.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
                double d4 = random.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
                double d5 = random.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

                for (int j1 = 1; j1 < 15; ++j1) {
                    for (int k1 = 1; k1 < 15; ++k1) {
                        for (int l1 = 1; l1 < 7; ++l1) {
                            double d6 = ((double) j1 - d3) / (d0 / 2.0D);
                            double d7 = ((double) l1 - d4) / (d1 / 2.0D);
                            double d8 = ((double) k1 - d5) / (d2 / 2.0D);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;

                            if (d9 < 1.0D) {
                                aboolean[(j1 * 16 + k1) * 8 + l1] = true;
                            }
                        }
                    }
                }
            }

            int i2;
            int j2;
            boolean flag;

            for (i1 = 0; i1 < 16; ++i1) {
                for (j2 = 0; j2 < 16; ++j2) {
                    for (i2 = 0; i2 < 8; ++i2) {
                        flag = !aboolean[(i1 * 16 + j2) * 8 + i2] && (i1 < 15 && aboolean[((i1 + 1) * 16 + j2) * 8 + i2] || i1 > 0 && aboolean[((i1 - 1) * 16 + j2) * 8 + i2] || j2 < 15 && aboolean[(i1 * 16 + j2 + 1) * 8 + i2] || j2 > 0 && aboolean[(i1 * 16 + (j2 - 1)) * 8 + i2] || i2 < 7 && aboolean[(i1 * 16 + j2) * 8 + i2 + 1] || i2 > 0 && aboolean[(i1 * 16 + j2) * 8 + (i2 - 1)]);
                        if (flag) {
                            Material material = world.getMaterial(i + i1, j + i2, k + j2);

                            if (i2 >= 4 && material.isLiquid()) {
                                return false;
                            }

                            if (i2 < 4 && !material.isBuildable() && world.getTypeId(i + i1, j + i2, k + j2) != this.a) {
                                return false;
                            }
                        }
                    }
                }
            }

            for (i1 = 0; i1 < 16; ++i1) {
                for (j2 = 0; j2 < 16; ++j2) {
                    for (i2 = 0; i2 < 8; ++i2) {
                        if (aboolean[(i1 * 16 + j2) * 8 + i2]) {
                            world.setTypeIdAndData(i + i1, j + i2, k + j2, i2 >= 4 ? 0 : this.a, 0, 2);
                        }
                    }
                }
            }

            for (i1 = 0; i1 < 16; ++i1) {
                for (j2 = 0; j2 < 16; ++j2) {
                    for (i2 = 4; i2 < 8; ++i2) {
                        if (aboolean[(i1 * 16 + j2) * 8 + i2] && world.getTypeId(i + i1, j + i2 - 1, k + j2) == Block.DIRT.id && world.b(EnumSkyBlock.SKY, i + i1, j + i2, k + j2) > 0) {
                            BiomeBase biomebase = world.getBiome(i + i1, k + j2);

                            if (biomebase.A == Block.MYCEL.id) {
                                world.setTypeIdAndData(i + i1, j + i2 - 1, k + j2, Block.MYCEL.id, 0, 2);
                            } else {
                                world.setTypeIdAndData(i + i1, j + i2 - 1, k + j2, Block.GRASS.id, 0, 2);
                            }
                        }
                    }
                }
            }

            if (Block.byId[this.a].material == Material.LAVA) {
                for (i1 = 0; i1 < 16; ++i1) {
                    for (j2 = 0; j2 < 16; ++j2) {
                        for (i2 = 0; i2 < 8; ++i2) {
                            flag = !aboolean[(i1 * 16 + j2) * 8 + i2] && (i1 < 15 && aboolean[((i1 + 1) * 16 + j2) * 8 + i2] || i1 > 0 && aboolean[((i1 - 1) * 16 + j2) * 8 + i2] || j2 < 15 && aboolean[(i1 * 16 + j2 + 1) * 8 + i2] || j2 > 0 && aboolean[(i1 * 16 + (j2 - 1)) * 8 + i2] || i2 < 7 && aboolean[(i1 * 16 + j2) * 8 + i2 + 1] || i2 > 0 && aboolean[(i1 * 16 + j2) * 8 + (i2 - 1)]);
                            if (flag && (i2 < 4 || random.nextInt(2) != 0) && world.getMaterial(i + i1, j + i2, k + j2).isBuildable()) {
                                world.setTypeIdAndData(i + i1, j + i2, k + j2, Block.STONE.id, 0, 2);
                            }
                        }
                    }
                }
            }

            if (Block.byId[this.a].material == Material.WATER) {
                for (i1 = 0; i1 < 16; ++i1) {
                    for (j2 = 0; j2 < 16; ++j2) {
                        byte b0 = 4;

                        if (world.x(i + i1, j + b0, k + j2)) {
                            world.setTypeIdAndData(i + i1, j + b0, k + j2, Block.ICE.id, 0, 2);
                        }
                    }
                }
            }

            return true;
        }
    }
}
