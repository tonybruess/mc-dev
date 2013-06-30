package net.minecraft.server;

public class PathfinderGoalOwnerHurtByTarget extends PathfinderGoalTarget {

    EntityTameableAnimal a;
    EntityLiving b;

    public PathfinderGoalOwnerHurtByTarget(EntityTameableAnimal entitytameableanimal) {
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
                this.b = entityliving.aC();
                return this.a(this.b, false);
            }
        }
    }

    public void c() {
        this.c.c(this.b);
        super.c();
    }
}
