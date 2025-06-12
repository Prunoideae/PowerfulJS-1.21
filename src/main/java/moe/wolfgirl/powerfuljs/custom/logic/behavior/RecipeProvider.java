package moe.wolfgirl.powerfuljs.custom.logic.behavior;

import dev.latvian.mods.rhino.util.HideFromJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

@RemapPrefixForJS("pjs$")
public interface RecipeProvider {

    @Nullable
    ResourceLocation pjs$getRunningRecipe();
}
