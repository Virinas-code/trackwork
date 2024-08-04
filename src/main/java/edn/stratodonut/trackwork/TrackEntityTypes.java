package edn.stratodonut.trackwork;

import com.tterrag.registrate.util.entry.EntityEntry;
import edn.stratodonut.trackwork.tracks.TrackBeltEntity;
import edn.stratodonut.trackwork.tracks.render.TrackBeltEntityRenderer;
import edn.stratodonut.trackwork.wheel.WheelEntity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.MobCategory;
import org.valkyrienskies.mod.client.EmptyRenderer;

import static edn.stratodonut.trackwork.TrackworkMod.REGISTRATE;

public class TrackEntityTypes {

	public static final EntityEntry<WheelEntity> WHEEL =
		REGISTRATE.entity("wheel_entity", WheelEntity::new, MobCategory.MISC)
			.properties(b -> b.trackRangeBlocks(10)
				.trackedUpdateRate(1)
				.dimensions(EntityDimensions.fixed(.3f, .3f))
				.fireImmune()
			)
			.renderer(() -> EmptyRenderer::new)
			.register();

	public static final EntityEntry<TrackBeltEntity> BELT =
		REGISTRATE.entity("track_belt_entity", TrackBeltEntity::new, MobCategory.MISC)
			.properties(b -> b.trackRangeBlocks(10)
				.trackedUpdateRate(1)
				.dimensions(EntityDimensions.fixed(1f, 1f))
				.fireImmune()
			)
			.renderer(() -> TrackBeltEntityRenderer::new)
			.register();

	public static void register() {
	}
}
