package moe.wolfgirl.powerfuljs.custom.logic.behavior;

import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

@HideFromJS
public interface MultiRecipeProvider {
    List<Optional<ResourceLocation>> pjs$getRunningRecipe();
}
