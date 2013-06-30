package net.minecraft.server;

public interface ICommandListener {

    String getName();

    void ICommandListener(ChatMessageComponent chatmessagecomponent);

    boolean a(int i, String s);

    ChunkCoordinates b();

    World f_();
}
