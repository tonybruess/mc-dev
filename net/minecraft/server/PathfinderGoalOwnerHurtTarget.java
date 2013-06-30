package net.minecraft.server;

public class PathfinderGoalOwnerHurtTarget extends PathfinderGoalTarget {

    EntityTameableAnimal a;
    EntityLiving b;

    public PathfinderGoalOwnerHurtTarget(EntityTameableAnimal entitytameableanimal) {
        super(entitytameableanimal, false);
        this.a = entitytameableanimal;
        this.a(1);
    }

    public boolean a() {
        if (!this.a.isTamed()) {
            return false;
        } else {
            EntityLiving entityliving = this.a.bR();

            if (entityliving == null) {
                return false;
            } else {
                this.b = entityliving.aD();
                return this.a(this.b, false);
            }
        }
    }

    public void c() {
        this.c.c(this.b);
        super.c();
    }
}
