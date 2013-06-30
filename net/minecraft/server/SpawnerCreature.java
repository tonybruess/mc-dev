package net.minecraft.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public final class SpawnerCreature {

    private HashMap a = new HashMap();

    public SpawnerCreature() {}

    protected static ChunkPosition getRandomPosition(World world, int i, int j) {
        Chunk chunk = world.getChunkAt(i, j);
        int k = i * 16 + world.random.nextInt(16);
        int l = j * 16 + world.random.nextInt(16);
        int i1 = world.random.nextInt(chunk == null ? world.S() : chunk.h() + 16 - 1);

        return new ChunkPosition(k, i1, l);
    }

    public int spawnEntities(WorldServer worldserver, boolean flag, boolean flag1, boolean flag2) {
        if (!flag && !flag1) {
            return 0;
        } else {
            this.a.clear();

            int i;
            int j;

            for (i = 0; i < worldserver.players.size(); ++i) {
                EntityHuman entityhuman = (EntityHuman) worldserver.players.get(i);
                int k = MathHelper.floor(entityhuman.locX / 16.0D);

                j = MathHelper.floor(entityhuman.locZ / 16.0D);
                byte b0 = 8;

                for (int l = -b0; l <= b0; ++l) {
                    for (int i1 = -b0; i1 <= b0; ++i1) {
                        boolean flag3 = l == -b0 || l == b0 || i1 == -b0 || i1 == b0;
                        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(l + k, i1 + j);

                        if (!flag3) {
                            this.a.put(chunkcoordintpair, Boolean.valueOf(false));
                        } else if (!this.a.containsKey(chunkcoordintpair)) {
                            this.a.put(chunkcoordintpair, Boolean.valueOf(true));
                        }
                    }
                }
            }

            i = 0;
            ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
            EnumCreatureType[] aenumcreaturetype = EnumCreatureType.values();

            j = aenumcreaturetype.length;

            for (int j1 = 0; j1 < j; ++j1) {
                EnumCreatureType enumcreaturetype = aenumcreaturetype[j1];

                if ((!enumcreaturetype.d() || flag1) && (enumcreaturetype.d() || flag) && (!enumcreaturetype.e() || flag2) && worldserver.a(enumcreaturetype.a()) <= enumcreaturetype.b() * this.a.size() / 256) {
                    Iterator iterator = this.a.keySet().iterator();

                    label110:
                    while (iterator.hasNext()) {
                        ChunkCoordIntPair chunkcoordintpair1 = (ChunkCoordIntPair) iterator.next();

                        if (!((Boolean) this.a.get(chunkcoordintpair1)).booleanValue()) {
                            ChunkPosition chunkposition = getRandomPosition(worldserver, chunkcoordintpair1.x, chunkcoordintpair1.z);
                            int k1 = chunkposition.x;
                            int l1 = chunkposition.y;
                            int i2 = chunkposition.z;

                            if (!worldserver.u(k1, l1, i2) && worldserver.getMaterial(k1, l1, i2) == enumcreaturetype.c()) {
                                int j2 = 0;
                                int k2 = 0;

                                while (k2 < 3) {
                                    int l2 = k1;
                                    int i3 = l1;
                                    int j3 = i2;
                                    byte b1 = 6;
                                    BiomeMeta biomemeta = null;
                                    EntityLivingData entitylivingdata = null;
                                    int k3 = 0;

                                    while (true) {
                                        if (k3 < 4) {
                                            label103: {
                                                l2 += worldserver.random.nextInt(b1) - worldserver.random.nextInt(b1);
                                                i3 += worldserver.random.nextInt(1) - worldserver.random.nextInt(1);
                                                j3 += worldserver.random.nextInt(b1) - worldserver.random.nextInt(b1);
                                                if (a(enumcreaturetype, worldserver, l2, i3, j3)) {
                                                    float f = (float) l2 + 0.5F;
                                                    float f1 = (float) i3;
                                                    float f2 = (float) j3 + 0.5F;

                                                    if (worldserver.findNearbyPlayer((double) f, (double) f1, (double) f2, 24.0D) == null) {
                                                        float f3 = f - (float) chunkcoordinates.x;
                                                        float f4 = f1 - (float) chunkcoordinates.y;
                                                        float f5 = f2 - (float) chunkcoordinates.z;
                                                        float f6 = f3 * f3 + f4 * f4 + f5 * f5;

                                                        if (f6 >= 576.0F) {
                                                            if (biomemeta == null) {
                                                                biomemeta = worldserver.a(enumcreaturetype, l2, i3, j3);
                                                                if (biomemeta == null) {
                                                                    break label103;
                                                                }
                                                            }

                                                            EntityLivingBase entitylivingbase;

                                                            try {
                                                                entitylivingbase = (EntityLivingBase) biomemeta.b.getConstructor(new Class[] { World.class}).newInstance(new Object[] { worldserver});
                                                            } catch (Exception exception) {
                                                                exception.printStackTrace();
                                                                return i;
                                                            }

                                                            entitylivingbase.setPositionRotation((double) f, (double) f1, (double) f2, worldserver.random.nextFloat() * 360.0F, 0.0F);
                                                            if (entitylivingbase.canSpawn()) {
                                                                ++j2;
                                                                worldserver.addEntity(entitylivingbase);
                                                                entitylivingdata = entitylivingbase.a(entitylivingdata);
                                                                if (j2 >= entitylivingbase.br()) {
                                                                    continue label110;
                                                                }
                                                            }

                                                            i += j2;
                                                        }
                                                    }
                                                }

                                                ++k3;
                                                continue;
                                            }
                                        }

                                        ++k2;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return i;
        }
    }

    public static boolean a(EnumCreatureType enumcreaturetype, World world, int i, int j, int k) {
        if (enumcreaturetype.c() == Material.WATER) {
            return world.getMaterial(i, j, k).isLiquid() && world.getMaterial(i, j - 1, k).isLiquid() && !world.u(i, j + 1, k);
        } else if (!world.w(i, j - 1, k)) {
            return false;
        } else {
            int l = world.getTypeId(i, j - 1, k);

            return l != Block.BEDROCK.id && !world.u(i, j, k) && !world.getMaterial(i, j, k).isLiquid() && !world.u(i, j + 1, k);
        }
    }

    public static void a(World world, BiomeBase biomebase, int i, int j, int k, int l, Random random) {
        List list = biomebase.getMobs(EnumCreatureType.CREATURE);

        if (!list.isEmpty()) {
            while (random.nextFloat() < biomebase.f()) {
                BiomeMeta biomemeta = (BiomeMeta) WeightedRandom.a(world.random, (Collection) list);
                EntityLivingData entitylivingdata = null;
                int i1 = biomemeta.c + random.nextInt(1 + biomemeta.d - biomemeta.c);
                int j1 = i + random.nextInt(k);
                int k1 = j + random.nextInt(l);
                int l1 = j1;
                int i2 = k1;

                for (int j2 = 0; j2 < i1; ++j2) {
                    boolean flag = false;

                    for (int k2 = 0; !flag && k2 < 4; ++k2) {
                        int l2 = world.i(j1, k1);

                        if (a(EnumCreatureType.CREATURE, world, j1, l2, k1)) {
                            float f = (float) j1 + 0.5F;
                            float f1 = (float) l2;
                            float f2 = (float) k1 + 0.5F;

                            EntityLivingBase entitylivingbase;

                            try {
                                entitylivingbase = (EntityLivingBase) biomemeta.b.getConstructor(new Class[] { World.class}).newInstance(new Object[] { world});
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                continue;
                            }

                            entitylivingbase.setPositionRotation((double) f, (double) f1, (double) f2, random.nextFloat() * 360.0F, 0.0F);
                            world.addEntity(entitylivingbase);
                            entitylivingdata = entitylivingbase.a(entitylivingdata);
                            flag = true;
                        }

                        j1 += random.nextInt(5) - random.nextInt(5);

                        for (k1 += random.nextInt(5) - random.nextInt(5); j1 < i || j1 >= i + k || k1 < j || k1 >= j + k; k1 = i2 + random.nextInt(5) - random.nextInt(5)) {
                            j1 = l1 + random.nextInt(5) - random.nextInt(5);
                        }
                    }
                }
            }
        }
    }
}
