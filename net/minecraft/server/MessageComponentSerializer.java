package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

public class MessageComponentSerializer implements JsonDeserializer, JsonSerializer {

    public MessageComponentSerializer() {}

    public ChatMessageComponent a(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) {
        ChatMessageComponent chatmessagecomponent = new ChatMessageComponent();
        JsonObject jsonobject = (JsonObject) jsonelement;
        JsonElement jsonelement1 = jsonobject.get("text");
        JsonElement jsonelement2 = jsonobject.get("translate");
        JsonElement jsonelement3 = jsonobject.get("color");
        JsonElement jsonelement4 = jsonobject.get("bold");
        JsonElement jsonelement5 = jsonobject.get("italic");
        JsonElement jsonelement6 = jsonobject.get("underlined");
        JsonElement jsonelement7 = jsonobject.get("obfuscated");

        if (jsonelement3 != null && jsonelement3.isJsonPrimitive()) {
            EnumChatFormat enumchatformat = EnumChatFormat.b(jsonelement3.getAsString());

            if (enumchatformat == null || !enumchatformat.c()) {
                throw new JsonParseException("Given color (" + jsonelement3.getAsString() + ") is not a valid selection");
            }

            chatmessagecomponent.a(enumchatformat);
        }

        if (jsonelement4 != null && jsonelement4.isJsonPrimitive()) {
            chatmessagecomponent.a(Boolean.valueOf(jsonelement4.getAsBoolean()));
        }

        if (jsonelement5 != null && jsonelement5.isJsonPrimitive()) {
            chatmessagecomponent.b(Boolean.valueOf(jsonelement5.getAsBoolean()));
        }

        if (jsonelement6 != null && jsonelement6.isJsonPrimitive()) {
            chatmessagecomponent.c(Boolean.valueOf(jsonelement6.getAsBoolean()));
        }

        if (jsonelement7 != null && jsonelement7.isJsonPrimitive()) {
            chatmessagecomponent.d(Boolean.valueOf(jsonelement7.getAsBoolean()));
        }

        if (jsonelement1 != null) {
            if (jsonelement1.isJsonArray()) {
                JsonArray jsonarray = jsonelement1.getAsJsonArray();
                Iterator iterator = jsonarray.iterator();

                while (iterator.hasNext()) {
                    JsonElement jsonelement8 = (JsonElement) iterator.next();

                    if (jsonelement8.isJsonPrimitive()) {
                        chatmessagecomponent.a(jsonelement8.getAsString());
                    } else if (jsonelement8.isJsonObject()) {
                        chatmessagecomponent.a(this.a(jsonelement8, type, jsondeserializationcontext));
                    }
                }
            } else if (jsonelement1.isJsonPrimitive()) {
                chatmessagecomponent.a(jsonelement1.getAsString());
            }
        } else if (jsonelement2 != null && jsonelement2.isJsonPrimitive()) {
            JsonElement jsonelement9 = jsonobject.get("using");

            if (jsonelement9 != null) {
                if (jsonelement9.isJsonArray()) {
                    ArrayList arraylist = Lists.newArrayList();
                    Iterator iterator1 = jsonelement9.getAsJsonArray().iterator();

                    while (iterator1.hasNext()) {
                        JsonElement jsonelement10 = (JsonElement) iterator1.next();

                        if (jsonelement10.isJsonPrimitive()) {
                            arraylist.add(jsonelement10.getAsString());
                        } else if (jsonelement10.isJsonObject()) {
                            arraylist.add(this.a(jsonelement10, type, jsondeserializationcontext));
                        }
                    }

                    chatmessagecomponent.a(jsonelement2.getAsString(), arraylist.toArray());
                } else if (jsonelement9.isJsonPrimitive()) {
                    chatmessagecomponent.a(jsonelement2.getAsString(), new Object[] { jsonelement9.getAsString()});
                }
            } else {
                chatmessagecomponent.b(jsonelement2.getAsString());
            }
        }

        return chatmessagecomponent;
    }

    public JsonElement a(ChatMessageComponent chatmessagecomponent, Type type, JsonSerializationContext jsonserializationcontext) {
        JsonObject jsonobject = new JsonObject();

        if (chatmessagecomponent.a() != null) {
            jsonobject.addProperty("color", chatmessagecomponent.a().d());
        }

        if (chatmessagecomponent.b() != null) {
            jsonobject.addProperty("bold", chatmessagecomponent.b());
        }

        if (chatmessagecomponent.c() != null) {
            jsonobject.addProperty("italic", chatmessagecomponent.c());
        }

        if (chatmessagecomponent.d() != null) {
            jsonobject.addProperty("underlined", chatmessagecomponent.d());
        }

        if (chatmessagecomponent.e() != null) {
            jsonobject.addProperty("obfuscated", chatmessagecomponent.e());
        }

        if (chatmessagecomponent.f() != null) {
            jsonobject.addProperty("text", chatmessagecomponent.f());
        } else if (chatmessagecomponent.g() != null) {
            jsonobject.addProperty("translate", chatmessagecomponent.g());
            if (chatmessagecomponent.h() != null && !chatmessagecomponent.h().isEmpty()) {
                jsonobject.add("using", this.b(chatmessagecomponent, type, jsonserializationcontext));
            }
        } else if (chatmessagecomponent.h() != null && !chatmessagecomponent.h().isEmpty()) {
            jsonobject.add("text", this.b(chatmessagecomponent, type, jsonserializationcontext));
        }

        return jsonobject;
    }

    private JsonArray b(ChatMessageComponent chatmessagecomponent, Type type, JsonSerializationContext jsonserializationcontext) {
        JsonArray jsonarray = new JsonArray();
        Iterator iterator = chatmessagecomponent.h().iterator();

        while (iterator.hasNext()) {
            ChatMessageComponent chatmessagecomponent1 = (ChatMessageComponent) iterator.next();

            if (chatmessagecomponent1.f() != null) {
                jsonarray.add(new JsonPrimitive(chatmessagecomponent1.f()));
            } else {
                jsonarray.add(this.a(chatmessagecomponent1, type, jsonserializationcontext));
            }
        }

        return jsonarray;
    }

    public Object deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) {
        return this.a(jsonelement, type, jsondeserializationcontext);
    }

    public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonserializationcontext) {
        return this.a((ChatMessageComponent) object, type, jsonserializationcontext);
    }
}
