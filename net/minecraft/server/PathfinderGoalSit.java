package net.minecraft.server;

public class PathfinderGoalSit extends PathfinderGoal {

    private EntityTameableAnimal entity;
    private boolean willSit;

    public PathfinderGoalSit(EntityTameableAnimal entitytameableanimal) {
        this.entity = entitytameableanimal;
        this.a(5);
    }

    public boolean a() {
        if (!this.entity.isTamed()) {
            return false;
        } else if (this.entity.G()) {
            return false;
        } else if (!this.entity.onGround) {
            return false;
        } else {
            EntityLiving entityliving = this.entity.bR();

            return entityliving == null ? true : (this.entity.e(entityliving) < 144.0D && entityliving.aC() != null ? false : this.willSit);
        }
    }

    public void c() {
        this.entity.getNavigation().h();
        this.entity.setSitting(true);
    }

    public void d() {
        this.entity.setSitting(false);
    }

    public void setSitting(boolean flag) {
        this.willSit = flag;
    }
}
