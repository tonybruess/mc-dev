package net.minecraft.server;

public class ControllerJump {

    private EntityLivingBase a;
    private boolean b;

    public ControllerJump(EntityLivingBase entitylivingbase) {
        this.a = entitylivingbase;
    }

    public void a() {
        this.b = true;
    }

    public void b() {
        this.a.f(this.b);
        this.b = false;
    }
}
