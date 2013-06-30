package net.minecraft.server;

public class ItemPickaxe extends ItemTool {

    private static Block[] craftingResult = new Block[] { Block.COBBLESTONE, Block.DOUBLE_STEP, Block.STEP, Block.STONE, Block.SANDSTONE, Block.MOSSY_COBBLESTONE, Block.IRON_ORE, Block.IRON_BLOCK, Block.COAL_ORE, Block.GOLD_BLOCK, Block.GOLD_ORE, Block.DIAMOND_ORE, Block.DIAMOND_BLOCK, Block.ICE, Block.NETHERRACK, Block.LAPIS_ORE, Block.LAPIS_BLOCK, Block.REDSTONE_ORE, Block.GLOWING_REDSTONE_ORE, Block.RAILS, Block.DETECTOR_RAIL, Block.GOLDEN_RAIL, Block.ACTIVATOR_RAIL};

    protected ItemPickaxe(int i, EnumToolMaterial enumtoolmaterial) {
        super(i, 2.0F, enumtoolmaterial, craftingResult);
    }

    public boolean canDestroySpecialBlock(Block block) {
        return block == Block.OBSIDIAN ? this.durability.d() == 3 : (block != Block.DIAMOND_BLOCK && block != Block.DIAMOND_ORE ? (block != Block.EMERALD_ORE && block != Block.EMERALD_BLOCK ? (block != Block.GOLD_BLOCK && block != Block.GOLD_ORE ? (block != Block.IRON_BLOCK && block != Block.IRON_ORE ? (block != Block.LAPIS_BLOCK && block != Block.LAPIS_ORE ? (block != Block.REDSTONE_ORE && block != Block.GLOWING_REDSTONE_ORE ? (block.material == Material.STONE ? true : (block.material == Material.ORE ? true : block.material == Material.HEAVY)) : this.durability.d() >= 2) : this.durability.d() >= 1) : this.durability.d() >= 1) : this.durability.d() >= 2) : this.durability.d() >= 2) : this.durability.d() >= 2);
    }

    public float getDestroySpeed(ItemStack itemstack, Block block) {
        return block != null && (block.material == Material.ORE || block.material == Material.HEAVY || block.material == Material.STONE) ? this.a : super.getDestroySpeed(itemstack, block);
    }
}
