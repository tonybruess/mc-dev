package net.minecraft.server;

public class AttributeMapClient extends AttributeMapBase {

    public AttributeMapClient() {}

    public AttributeReadonly c(Attribute attribute) {
        return (AttributeReadonly) super.a(attribute);
    }

    public AttributeReadonly b(String s) {
        return (AttributeReadonly) super.a(s);
    }

    public AttributeReadonly d(Attribute attribute) {
        if (this.b.containsKey(attribute.a())) {
            throw new IllegalArgumentException("Attribute is already registered!");
        } else {
            AttributeReadonly attributereadonly = new AttributeReadonly(attribute);

            this.b.put(attribute.a(), attributereadonly);
            this.a.put(attribute, attributereadonly);
            return attributereadonly;
        }
    }

    public AttributeInstance b(Attribute attribute) {
        return this.d(attribute);
    }

    public AttributeInstance a(String s) {
        return this.b(s);
    }

    public AttributeInstance a(Attribute attribute) {
        return this.c(attribute);
    }
}
