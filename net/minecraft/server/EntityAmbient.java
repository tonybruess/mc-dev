package net.minecraft.server;

public abstract class EntityAmbient extends EntityLivingBase implements IAnimal {

    public EntityAmbient(World world) {
        super(world);
    }

    public boolean bC() {
        return false;
    }

    protected boolean a(EntityHuman entityhuman) {
        return false;
    }
}
