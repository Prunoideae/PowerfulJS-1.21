package moe.wolfgirl.powerfuljs.custom.logic.behavior;

import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

@HideFromJS
public interface RecipeProvider {

    @Nullable
    ResourceLocation pjs$getRunningRecipe();
}
