package dev.sebastianb.wackyvessels.block;

import dev.sebastianb.wackyvessels.Constants;
import dev.sebastianb.wackyvessels.block.decoration.Console;
import dev.sebastianb.wackyvessels.block.helm.VesselHelm;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WackyVesselsBlocks {

    public static final ItemGroup BLOCKS_GROUP = FabricItemGroupBuilder.create(
            new Identifier(Constants.MOD_ID, "blocks"))
            .icon(() -> new ItemStack(WackyVesselsBlocks.VESSEL_HELM)).build();

    public static final Block CONSOLE =
            registerBlock(new Console(
                            FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).sounds(BlockSoundGroup.WOOD)),
                    Constants.Blocks.CONSOLE);

    public static final Block VESSEL_HELM =
            registerBlock(new VesselHelm(
                    FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).sounds(BlockSoundGroup.WOOD)),
                    Constants.Blocks.VESSEL_HELM);

    public static final Block VESSEL_CHAIR =
            registerBlock(new VesselChair(
                            FabricBlockSettings.of(Material.WOOD).strength(2.0F, 2.0F).sounds(BlockSoundGroup.WOOD)),
                            Constants.Blocks.VESSEL_CHAIR
                    );

    public static void register() {}

    private static <T extends Block> T registerBlock(T block, String id) {
        return registerBlock(block, id, BLOCKS_GROUP);
    }

    private static <T extends Block> T registerBlock(T block, String id, ItemGroup group) {
        Identifier identifier = new Identifier(Constants.MOD_ID, id);
        Registry.register(Registry.BLOCK, identifier, block);
        BlockItem item = Registry.register(Registry.ITEM, identifier, new BlockItem(block, new Item.Settings().group(group)));
        item.appendBlocks(Item.BLOCK_ITEMS, item);
        return block;
    }
}
