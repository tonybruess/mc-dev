package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Iterator;
import java.util.List;

public class ChatMessageComponent {

    private static final Gson a = (new GsonBuilder()).registerTypeAdapter(ChatMessageComponent.class, new MessageComponentSerializer()).create();
    private EnumChatFormat b;
    private Boolean c;
    private Boolean d;
    private Boolean e;
    private Boolean f;
    private String g;
    private String h;
    private List i;

    public ChatMessageComponent() {}

    public ChatMessageComponent(ChatMessageComponent chatmessagecomponent) {
        this.b = chatmessagecomponent.b;
        this.c = chatmessagecomponent.c;
        this.d = chatmessagecomponent.d;
        this.e = chatmessagecomponent.e;
        this.f = chatmessagecomponent.f;
        this.g = chatmessagecomponent.g;
        this.h = chatmessagecomponent.h;
        this.i = chatmessagecomponent.i == null ? null : Lists.newArrayList(chatmessagecomponent.i);
    }

    public ChatMessageComponent a(EnumChatFormat enumchatformat) {
        if (enumchatformat != null && !enumchatformat.c()) {
            throw new IllegalArgumentException("Argument is not a valid color!");
        } else {
            this.b = enumchatformat;
            return this;
        }
    }

    public EnumChatFormat a() {
        return this.b;
    }

    public ChatMessageComponent a(Boolean obool) {
        this.c = obool;
        return this;
    }

    public Boolean b() {
        return this.c;
    }

    public ChatMessageComponent b(Boolean obool) {
        this.d = obool;
        return this;
    }

    public Boolean c() {
        return this.d;
    }

    public ChatMessageComponent c(Boolean obool) {
        this.e = obool;
        return this;
    }

    public Boolean d() {
        return this.e;
    }

    public ChatMessageComponent d(Boolean obool) {
        this.f = obool;
        return this;
    }

    public Boolean e() {
        return this.f;
    }

    protected String f() {
        return this.g;
    }

    protected String g() {
        return this.h;
    }

    protected List h() {
        return this.i;
    }

    public ChatMessageComponent a(ChatMessageComponent chatmessagecomponent) {
        if (this.g == null && this.h == null) {
            if (this.i != null) {
                this.i.add(chatmessagecomponent);
            } else {
                this.i = Lists.newArrayList(new ChatMessageComponent[] { chatmessagecomponent});
            }
        } else {
            this.i = Lists.newArrayList(new ChatMessageComponent[] { new ChatMessageComponent(this), chatmessagecomponent});
            this.g = null;
            this.h = null;
        }

        return this;
    }

    public ChatMessageComponent a(String s) {
        if (this.g == null && this.h == null) {
            if (this.i != null) {
                this.i.add(d(s));
            } else {
                this.g = s;
            }
        } else {
            this.i = Lists.newArrayList(new ChatMessageComponent[] { new ChatMessageComponent(this), d(s)});
            this.g = null;
            this.h = null;
        }

        return this;
    }

    public ChatMessageComponent b(String s) {
        if (this.g == null && this.h == null) {
            if (this.i != null) {
                this.i.add(e(s));
            } else {
                this.h = s;
            }
        } else {
            this.i = Lists.newArrayList(new ChatMessageComponent[] { new ChatMessageComponent(this), e(s)});
            this.g = null;
            this.h = null;
        }

        return this;
    }

    public ChatMessageComponent a(String s, Object... aobject) {
        if (this.g == null && this.h == null) {
            if (this.i != null) {
                this.i.add(b(s, aobject));
            } else {
                this.h = s;
                this.i = Lists.newArrayList();
                Object[] aobject1 = aobject;
                int i = aobject.length;

                for (int j = 0; j < i; ++j) {
                    Object object = aobject1[j];

                    if (object instanceof ChatMessageComponent) {
                        this.i.add((ChatMessageComponent) object);
                    } else {
                        this.i.add(d(object.toString()));
                    }
                }
            }
        } else {
            this.i = Lists.newArrayList(new ChatMessageComponent[] { new ChatMessageComponent(this), b(s, aobject)});
            this.g = null;
            this.h = null;
        }

        return this;
    }

    public String toString() {
        return this.a(false);
    }

    public String a(boolean flag) {
        return this.a(flag, (EnumChatFormat) null, false, false, false, false);
    }

    public String a(boolean flag, EnumChatFormat enumchatformat, boolean flag1, boolean flag2, boolean flag3, boolean flag4) {
        StringBuilder stringbuilder = new StringBuilder();
        EnumChatFormat enumchatformat1 = this.b == null ? enumchatformat : this.b;
        boolean flag5 = this.c == null ? flag1 : this.c.booleanValue();
        boolean flag6 = this.d == null ? flag2 : this.d.booleanValue();
        boolean flag7 = this.e == null ? flag3 : this.e.booleanValue();
        boolean flag8 = this.f == null ? flag4 : this.f.booleanValue();

        if (this.h != null) {
            if (flag) {
                a(stringbuilder, enumchatformat1, flag5, flag6, flag7, flag8);
            }

            if (this.i != null) {
                String[] astring = new String[this.i.size()];

                for (int i = 0; i < this.i.size(); ++i) {
                    astring[i] = ((ChatMessageComponent) this.i.get(i)).toString();
                }

                stringbuilder.append(LocaleI18n.get(this.h, astring));
            } else {
                stringbuilder.append(LocaleI18n.get(this.h));
            }
        } else if (this.g != null) {
            if (flag) {
                a(stringbuilder, enumchatformat1, flag5, flag6, flag7, flag8);
            }

            stringbuilder.append(this.g);
        } else {
            ChatMessageComponent chatmessagecomponent;

            if (this.i != null) {
                for (Iterator iterator = this.i.iterator(); iterator.hasNext(); stringbuilder.append(chatmessagecomponent.toString())) {
                    chatmessagecomponent = (ChatMessageComponent) iterator.next();
                    if (flag) {
                        a(stringbuilder, enumchatformat1, flag5, flag6, flag7, flag8);
                    }
                }
            }
        }

        return stringbuilder.toString();
    }

    private static void a(StringBuilder stringbuilder, EnumChatFormat enumchatformat, boolean flag, boolean flag1, boolean flag2, boolean flag3) {
        if (enumchatformat != null) {
            stringbuilder.append(enumchatformat);
        } else if (flag || flag1 || flag2 || flag3) {
            stringbuilder.append(EnumChatFormat.RESET);
        }

        if (flag) {
            stringbuilder.append(EnumChatFormat.BOLD);
        }

        if (flag1) {
            stringbuilder.append(EnumChatFormat.ITALIC);
        }

        if (flag2) {
            stringbuilder.append(EnumChatFormat.UNDERLINE);
        }

        if (flag3) {
            stringbuilder.append(EnumChatFormat.RANDOM);
        }
    }

    public static ChatMessageComponent d(String s) {
        ChatMessageComponent chatmessagecomponent = new ChatMessageComponent();

        chatmessagecomponent.a(s);
        return chatmessagecomponent;
    }

    public static ChatMessageComponent e(String s) {
        ChatMessageComponent chatmessagecomponent = new ChatMessageComponent();

        chatmessagecomponent.b(s);
        return chatmessagecomponent;
    }

    public static ChatMessageComponent b(String s, Object... aobject) {
        ChatMessageComponent chatmessagecomponent = new ChatMessageComponent();

        chatmessagecomponent.a(s, aobject);
        return chatmessagecomponent;
    }

    public String i() {
        return a.toJson(this);
    }
}
