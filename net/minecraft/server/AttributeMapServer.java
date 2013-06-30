package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AttributeMapServer extends AttributeMapBase {

    private final Set d = Sets.newHashSet();
    protected final Map c = new InsensitiveStringMap();

    public AttributeMapServer() {}

    public AttributeModifiable c(Attribute attribute) {
        return (AttributeModifiable) super.a(attribute);
    }

    public AttributeModifiable b(String s) {
        AttributeInstance attributeinstance = super.a(s);

        if (attributeinstance == null) {
            attributeinstance = (AttributeInstance) this.c.get(s);
        }

        return (AttributeModifiable) attributeinstance;
    }

    public AttributeInstance b(Attribute attribute) {
        if (this.b.containsKey(attribute.a())) {
            throw new IllegalArgumentException("Attribute is already registered!");
        } else {
            AttributeModifiable attributemodifiable = new AttributeModifiable(this, attribute);

            this.b.put(attribute.a(), attributemodifiable);
            if (attribute instanceof AttributeRanged && ((AttributeRanged) attribute).f() != null) {
                this.c.put(((AttributeRanged) attribute).f(), attributemodifiable);
            }

            this.a.put(attribute, attributemodifiable);
            return attributemodifiable;
        }
    }

    public void a(AttributeModifiable attributemodifiable) {
        if (attributemodifiable.a().c()) {
            this.d.add(attributemodifiable);
        }
    }

    public Set b() {
        return this.d;
    }

    public Collection c() {
        HashSet hashset = Sets.newHashSet();
        Iterator iterator = this.a().iterator();

        while (iterator.hasNext()) {
            AttributeInstance attributeinstance = (AttributeInstance) iterator.next();

            if (attributeinstance.a().c()) {
                hashset.add(attributeinstance);
            }
        }

        return hashset;
    }

    public AttributeInstance a(String s) {
        return this.b(s);
    }

    public AttributeInstance a(Attribute attribute) {
        return this.c(attribute);
    }
}
