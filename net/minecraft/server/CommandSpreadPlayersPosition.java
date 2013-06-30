package net.minecraft.server;

import java.util.Random;

class CommandSpreadPlayersPosition {

    double a;
    double b;

    CommandSpreadPlayersPosition() {}

    CommandSpreadPlayersPosition(double d0, double d1) {
        this.a = d0;
        this.b = d1;
    }

    double a(CommandSpreadPlayersPosition commandspreadplayersposition) {
        double d0 = this.a - commandspreadplayersposition.a;
        double d1 = this.b - commandspreadplayersposition.b;

        return Math.sqrt(d0 * d0 + d1 * d1);
    }

    void a() {
        double d0 = (double) this.b();

        this.a /= d0;
        this.b /= d0;
    }

    float b() {
        return MathHelper.sqrt(this.a * this.a + this.b * this.b);
    }

    public void b(CommandSpreadPlayersPosition commandspreadplayersposition) {
        this.a -= commandspreadplayersposition.a;
        this.b -= commandspreadplayersposition.b;
    }

    public boolean a(double d0, double d1, double d2, double d3) {
        boolean flag = false;

        if (this.a < d0) {
            this.a = d0;
            flag = true;
        } else if (this.a > d2) {
            this.a = d2;
            flag = true;
        }

        if (this.b < d1) {
            this.b = d1;
            flag = true;
        } else if (this.b > d3) {
            this.b = d3;
            flag = true;
        }

        return flag;
    }

    public int a(World world) {
        int i = MathHelper.floor(this.a);
        int j = MathHelper.floor(this.b);

        for (int k = 256; k > 0; --k) {
            int l = world.getTypeId(i, k, j);

            if (l != 0) {
                return k + 1;
            }
        }

        return 257;
    }

    public boolean b(World world) {
        int i = MathHelper.floor(this.a);
        int j = MathHelper.floor(this.b);

        for (int k = 256; k > 0; --k) {
            int l = world.getTypeId(i, k, j);

            if (l != 0) {
                Material material = Block.byId[l].material;

                return !material.isLiquid() && material != Material.FIRE;
            }
        }

        return false;
    }

    public void a(Random random, double d0, double d1, double d2, double d3) {
        this.a = MathHelper.a(random, d0, d2);
        this.b = MathHelper.a(random, d1, d3);
    }
}
