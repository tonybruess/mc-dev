package net.minecraft.server;

public class ControllerMove {

    private EntityLivingBase a;
    private double b;
    private double c;
    private double d;
    private double e;
    private boolean f;

    public ControllerMove(EntityLivingBase entitylivingbase) {
        this.a = entitylivingbase;
        this.b = entitylivingbase.locX;
        this.c = entitylivingbase.locY;
        this.d = entitylivingbase.locZ;
    }

    public boolean a() {
        return this.f;
    }

    public double b() {
        return this.e;
    }

    public void a(double d0, double d1, double d2, double d3) {
        this.b = d0;
        this.c = d1;
        this.d = d2;
        this.e = d3;
        this.f = true;
    }

    public void c() {
        this.a.n(0.0F);
        if (this.f) {
            this.f = false;
            int i = MathHelper.floor(this.a.boundingBox.b + 0.5D);
            double d0 = this.b - this.a.locX;
            double d1 = this.d - this.a.locZ;
            double d2 = this.c - (double) i;
            double d3 = d0 * d0 + d2 * d2 + d1 * d1;

            if (d3 >= 2.500000277905201E-7D) {
                float f = (float) (Math.atan2(d1, d0) * 180.0D / 3.1415927410125732D) - 90.0F;

                this.a.yaw = this.a(this.a.yaw, f, 30.0F);
                this.a.i((float) (this.e * this.a.a(ItemHayStack.d).e()));
                if (d2 > 0.0D && d0 * d0 + d1 * d1 < 1.0D) {
                    this.a.getControllerJump().a();
                }
            }
        }
    }

    private float a(float f, float f1, float f2) {
        float f3 = MathHelper.g(f1 - f);

        if (f3 > f2) {
            f3 = f2;
        }

        if (f3 < -f2) {
            f3 = -f2;
        }

        return f + f3;
    }
}
