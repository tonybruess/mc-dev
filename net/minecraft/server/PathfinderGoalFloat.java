package net.minecraft.server;

public class PathfinderGoalFloat extends PathfinderGoal {

    private EntityLivingBase a;

    public PathfinderGoalFloat(EntityLivingBase entitylivingbase) {
        this.a = entitylivingbase;
        this.a(4);
        entitylivingbase.getNavigation().e(true);
    }

    public boolean a() {
        return this.a.G() || this.a.I();
    }

    public void e() {
        if (this.a.aB().nextFloat() < 0.8F) {
            this.a.getControllerJump().a();
        }
    }
}
