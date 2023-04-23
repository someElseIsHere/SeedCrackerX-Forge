package kaptainwutax.seedcrackerX.mixin;

import kaptainwutax.seedcrackerX.finder.Finder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DimensionType.class)
public class DimensionTypeMixin implements Finder.DimensionTypeCaller {

	@Shadow @Final private ResourceLocation infiniburn;

	@Override
	public ResourceLocation getInfiniburn() {
		return this.infiniburn;
	}

}
