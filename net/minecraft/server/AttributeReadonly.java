package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.UUID;

public class AttributeReadonly implements AttributeInstance {

    private final Attribute a;
    private double b;

    public AttributeReadonly(Attribute attribute) {
        this.a = attribute;
        this.b = attribute.b();
    }

    public Attribute a() {
        return this.a;
    }

    public double b() {
        return this.b;
    }

    public void a(double d0) {}

    public Collection c() {
        return Sets.newHashSet();
    }

    public AttributeModifier a(UUID uuid) {
        return null;
    }

    public void a(AttributeModifier attributemodifier) {
        throw new UnsupportedOperationException("Cannot modify readonly attribute");
    }

    public void b(AttributeModifier attributemodifier) {
        throw new UnsupportedOperationException("Cannot modify readonly attribute");
    }

    public double e() {
        return this.b;
    }
}
