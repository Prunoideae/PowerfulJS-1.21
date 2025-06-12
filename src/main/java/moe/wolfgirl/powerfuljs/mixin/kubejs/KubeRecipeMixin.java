package moe.wolfgirl.powerfuljs.mixin.kubejs;

import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import moe.wolfgirl.powerfuljs.GameStates;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashSet;
import java.util.List;

@Mixin(KubeRecipe.class)
@RemapPrefixForJS("pjs$")
public abstract class KubeRecipeMixin {
    @Shadow
    public abstract ResourceLocation getOrCreateId();

    @Unique
    @Info("PowerfulJS: Tags a recipe to be used in `rules.doingRecipeTag`")
    public void pjs$recipeTags(ResourceLocation... tags) {
        GameStates.RECIPE_TAGS.computeIfAbsent(getOrCreateId(), id -> new HashSet<>())
                .addAll(List.of(tags));
    }
}
