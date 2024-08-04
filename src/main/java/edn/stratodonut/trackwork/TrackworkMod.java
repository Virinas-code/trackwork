package edn.stratodonut.trackwork;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrackworkMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Trackwork Mod");

	public static final String MOD_ID = "trackwork";
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());

		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TrackCreativeTabs.CUSTOM_ITEM_GROUP_KEY, TrackCreativeTabs.CUSTOM_ITEM_GROUP);
	}

	public static ResourceLocation getResource(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
