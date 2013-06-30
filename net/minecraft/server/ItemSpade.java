package net.minecraft.server;

public class ItemSpade extends ItemTool {

    private static Block[] craftingResult = new Block[] { Block.GRASS, Block.DIRT, Block.SAND, Block.GRAVEL, Block.SNOW, Block.SNOW_BLOCK, Block.CLAY, Block.SOIL, Block.SOUL_SAND, Block.MYCEL};

    public ItemSpade(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 1.0F, enumtoolmaterial, craftingResult);
    }

    public boolean canDestroySpecialBlock(Block block) {
        return block == Block.SNOW ? true : block == Block.SNOW_BLOCK;
    }
}
