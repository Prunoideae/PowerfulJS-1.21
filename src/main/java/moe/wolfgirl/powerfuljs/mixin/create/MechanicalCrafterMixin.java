package moe.wolfgirl.powerfuljs.mixin.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.crafter.MechanicalCrafterBlock;
import com.simibubi.create.content.kinetics.crafter.MechanicalCrafterBlockEntity;
import com.simibubi.create.content.kinetics.crafter.MechanicalCraftingInput;
import com.simibubi.create.content.kinetics.crafter.RecipeGridHandler;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.infrastructure.config.AllConfigs;
import moe.wolfgirl.powerfuljs.custom.logic.behavior.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MechanicalCrafterBlockEntity.class)
public abstract class MechanicalCrafterMixin implements RecipeProvider {

    @Unique
    private MechanicalCrafterBlockEntity pjs$self() {
        return (MechanicalCrafterBlockEntity) (Object) this;
    }

    @Override
    public @Nullable ResourceLocation pjs$getRunningRecipe() {
        MechanicalCrafterBlockEntity self = pjs$self();
        if (self.phase != MechanicalCrafterBlockEntity.Phase.ASSEMBLING) return null;

        Level level = self.getLevel();
        CraftingInput craftingInput = MechanicalCraftingInput.of(self.groupedItems);
        ResourceLocation recipe = null;
        if (AllConfigs.server().recipes.allowRegularCraftingInCrafter.get()) {
            recipe = level.getRecipeManager()
                    .getRecipeFor(RecipeType.CRAFTING, craftingInput, level)
                    .filter(r -> RecipeGridHandler.isRecipeAllowed(r, craftingInput))
                    .map(RecipeHolder::id)
                    .orElse(null);
        }
        if (recipe == null) {
            recipe = AllRecipeTypes.MECHANICAL_CRAFTING.find(craftingInput, level)
                    .map(RecipeHolder::id)
                    .orElse(null);
        }

        return recipe;
    }
}
