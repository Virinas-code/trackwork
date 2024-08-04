package edn.stratodonut.trackwork;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import static edn.stratodonut.trackwork.TrackworkMod.REGISTRATE;

public class TrackCreativeTabs {
	public static final ResourceKey<CreativeModeTab> CUSTOM_ITEM_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.tryBuild(TrackworkMod.MOD_ID, "item_group"));
	public static final CreativeModeTab CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
		.icon(() -> new ItemStack(AllBlocks.BELT))
		.title(Component.translatable("itemGroup.trackwork"))
		.displayItems((itemDisplayParameters, output) -> {
				for (RegistryEntry<Block> entry : REGISTRATE.getAll(Registries.BLOCK)) {
					if (CreateRegistrate.isInCreativeTab(entry, AllCreativeModeTabs.BASE_CREATIVE_TAB.key()))
						output.accept(entry.get().asItem());
				}

				for (RegistryEntry<Item> entry : REGISTRATE.getAll(Registries.ITEM)) {
					if (CreateRegistrate.isInCreativeTab(entry, AllCreativeModeTabs.BASE_CREATIVE_TAB.key()))
						output.accept(entry.get());
				}
		})
		.build();
}

//import com.simibubi.create.AllBlocks;
//import com.simibubi.create.AllCreativeModeTabs;
//import com.simibubi.create.foundation.data.CreateRegistrate;
//import com.simibubi.create.foundation.utility.Components;
//import com.tterrag.registrate.util.entry.RegistryEntry;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.world.item.CreativeModeTab;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.level.block.Block;
//import net.minecraftforge.eventbus.api.IEventBus;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.RegistryObject;
//import static edn.stratodonut.trackwork.TrackworkMod.REGISTRATE;
//
//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
//public class TrackCreativeTabs {
//    private static final DeferredRegister<CreativeModeTab> REGISTER =
//            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TrackworkMod.MOD_ID);
//
//    public static final RegistryObject<CreativeModeTab> BASE_CREATIVE_TAB = REGISTER.register("base",
//            () -> CreativeModeTab.builder()
//                    .title(Components.translatable("itemGroup.trackwork"))
//                    .icon(() -> AllBlocks.BELT.asStack()).displayItems((displayParams, output) -> {
//                        for (RegistryEntry<Block> entry : REGISTRATE.getAll(Registries.BLOCK)) {
//                            if (CreateRegistrate.isInCreativeTab(entry, AllCreativeModeTabs.BASE_CREATIVE_TAB))
//                            output.accept(entry.get().asItem());
//                        }
//
//                        for (RegistryEntry<Item> entry : REGISTRATE.getAll(Registries.ITEM)) {
//                            if (CreateRegistrate.isInCreativeTab(entry, AllCreativeModeTabs.BASE_CREATIVE_TAB))
//                                output.accept(entry.get());
//                        }
//                    })
//                    .build());
//
//    public static void register(IEventBus modEventBus) {
//        REGISTER.register(modEventBus);
//    }
//
//
//}
