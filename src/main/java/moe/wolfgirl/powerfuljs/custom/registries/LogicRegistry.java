package moe.wolfgirl.powerfuljs.custom.registries;

import dev.latvian.mods.kubejs.block.state.BlockStatePredicate;
import moe.wolfgirl.powerfuljs.custom.registries.logic.Effect;
import moe.wolfgirl.powerfuljs.custom.registries.logic.Rule;
import moe.wolfgirl.powerfuljs.custom.registries.logic.effects.ToggleEnable;
import moe.wolfgirl.powerfuljs.custom.registries.logic.effects.energy.DrainEnergyEffect;
import moe.wolfgirl.powerfuljs.custom.registries.logic.effects.energy.FillEnergyEffect;
import moe.wolfgirl.powerfuljs.custom.registries.logic.effects.fluid.DrainFluidEffect;
import moe.wolfgirl.powerfuljs.custom.registries.logic.effects.fluid.FillFluidEffect;
import moe.wolfgirl.powerfuljs.custom.registries.logic.effects.item.ExtractItemEffect;
import moe.wolfgirl.powerfuljs.custom.registries.logic.effects.item.InsertItemEffect;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.MatchBlockRule;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.NBTRule;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.TickRateRule;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.energy.CanExtractEnergy;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.energy.CanReceiveEnergy;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.energy.HasEnergyRule;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.fluid.CanExtractFluid;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.fluid.CanReceiveFluid;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.fluid.HasFluidRule;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.item.CanExtractItem;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.item.CanInsertItem;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.item.HasItemRule;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.logic.AlwaysRule;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.logic.AndRule;
import moe.wolfgirl.powerfuljs.custom.registries.logic.rules.logic.OrRule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LogicRegistry {
    public static class Rules {
        public static Rule always(boolean state) {
            return new AlwaysRule(state);
        }

        public static Rule and(Rule... rules) {
            return new AndRule(List.of(rules));
        }

        public static Rule or(Rule... rules) {
            return new OrRule(List.of(rules));
        }

        /* World stuffs */

        public static Rule nbt(CompoundTag tag) {
            return new NBTRule(tag);
        }

        public static Rule matchBlock(BlockStatePredicate blockState) {
            return new MatchBlockRule(blockState);
        }

        public static Rule every(int ticks) {
            return new TickRateRule(ticks);
        }

        /* Fluid handling */
        public static Rule hasFluid(SizedFluidIngredient fluidIngredient, @Nullable Direction direction) {
            return new HasFluidRule(fluidIngredient, direction);
        }

        public static Rule canExtractFluid(FluidStack fluidStack, @Nullable Direction direction) {
            return new CanExtractFluid(fluidStack, direction);
        }

        public static Rule canInsertFluid(FluidStack fluidStack, @Nullable Direction direction) {
            return new CanReceiveFluid(fluidStack, direction);
        }

        /* Energy handling */
        public static Rule hasEnergy(int energy, @Nullable Direction direction) {
            return new HasEnergyRule(energy, direction);
        }

        public static Rule canExtractEnergy(int energy, @Nullable Direction direction) {
            return new CanExtractEnergy(energy, direction);
        }

        public static Rule canReceiveEnergy(int energy, @Nullable Direction direction) {
            return new CanReceiveEnergy(energy, direction);
        }

        /* Item handling */
        public static Rule hasItem(Ingredient item, int count, @Nullable Direction direction) {
            return new HasItemRule(item, count, direction);
        }

        public static Rule hasItem(SizedIngredient item, @Nullable Direction direction) {
            return hasItem(item.ingredient(), item.count(), direction);
        }

        public static Rule canExtractItem(ItemStack itemStack, @Nullable Direction direction) {
            return new CanExtractItem(itemStack, direction);
        }

        public static Rule canInsertItem(ItemStack itemStack, @Nullable Direction direction) {
            return new CanInsertItem(itemStack, direction);
        }
    }

    public static class Effects {

        public static Effect toggleEnable() {
            return new ToggleEnable();
        }

        /* Fluid handling */
        public static Effect fillFluid(FluidStack fluidStack, @Nullable Direction direction) {
            return new FillFluidEffect(fluidStack, direction);
        }

        public static Effect drainFluid(FluidStack fluidStack, @Nullable Direction direction) {
            return new DrainFluidEffect(fluidStack, direction);
        }

        /* Energy handling */
        public static Effect fillEnergy(int energy, @Nullable Direction direction) {
            return new FillEnergyEffect(energy, direction);
        }

        public static Effect drainEnergy(int energy, @Nullable Direction direction) {
            return new DrainEnergyEffect(energy, direction);
        }

        /* Item handling */
        public static Effect insertItem(ItemStack itemStack, @Nullable Direction context) {
            return new InsertItemEffect(itemStack, context);
        }

        public static Effect extractItem(ItemStack itemStack, @Nullable Direction context) {
            return new ExtractItemEffect(itemStack, context);
        }
    }
}
