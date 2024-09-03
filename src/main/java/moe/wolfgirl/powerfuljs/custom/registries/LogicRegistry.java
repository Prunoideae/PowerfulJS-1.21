package moe.wolfgirl.powerfuljs.custom.registries;

import dev.latvian.mods.kubejs.block.state.BlockStatePredicate;
import dev.latvian.mods.kubejs.typings.Info;
import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import moe.wolfgirl.powerfuljs.custom.logic.effects.TickRate;
import moe.wolfgirl.powerfuljs.custom.logic.effects.ToggleEnable;
import moe.wolfgirl.powerfuljs.custom.logic.effects.energy.DrainEnergyEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.energy.FillEnergyEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.fluid.DrainFluidEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.fluid.FillFluidEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.machine.FurnaceFuel;
import moe.wolfgirl.powerfuljs.custom.logic.effects.machine.FurnaceProgress;
import moe.wolfgirl.powerfuljs.custom.logic.effects.item.ExtractItemEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.item.InsertItemEffect;
import moe.wolfgirl.powerfuljs.custom.logic.rules.energy.CanExtractEnergy;
import moe.wolfgirl.powerfuljs.custom.logic.rules.energy.CanReceiveEnergy;
import moe.wolfgirl.powerfuljs.custom.logic.rules.energy.HasEnergyRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.fluid.CanExtractFluid;
import moe.wolfgirl.powerfuljs.custom.logic.rules.fluid.CanReceiveFluid;
import moe.wolfgirl.powerfuljs.custom.logic.rules.fluid.HasFluidRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.item.CanExtractItem;
import moe.wolfgirl.powerfuljs.custom.logic.rules.item.CanInsertItem;
import moe.wolfgirl.powerfuljs.custom.logic.rules.item.HasItemRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.logic.AlwaysRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.logic.AndRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.logic.OrRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.machine.FurnaceAboutToFinishRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.world.*;
import moe.wolfgirl.powerfuljs.custom.logic.rules.machine.FurnaceRunningRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.machine.TickRateRule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LogicRegistry {
    public static class Rules {
        public static Rule always() {
            return always(true);
        }

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

        public static Rule higherThan(int y) {
            return new YPosRule(y);
        }

        public static Rule brighterThan(int brightness) {
            return new LightRule(brightness);
        }

        public static Rule inBiome(TagKey<Biome> biomeTag) {
            return new InBiomeRule(biomeTag);
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

        /* Furnace */
        public static Rule furnaceRunning() {
            return new FurnaceRunningRule();
        }

        @Info("Test if the furnace is **exactly** one tick away from producing results. Must be run after any tick modification effect.")
        public static Rule furnaceAboutToFinish() {
            return new FurnaceAboutToFinishRule(1);
        }
    }

    public static class Effects {

        public static Effect toggleEnable() {
            return new ToggleEnable();
        }

        @Info("Changes the tick speed to Nx of the original, e.g. 0.1 will make it 90% slower, and 2 will make it 2x faster.")
        public static Effect changeTickSpeed(float tickSpeed) {
            return new TickRate(tickSpeed);
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

        /* Furnace, it's the only 'machine' in minecraft lol */
        public static Effect addFurnaceFuel(int fuelTicks) {
            return new FurnaceFuel(fuelTicks);
        }

        public static Effect addFurnaceProgress(int progressTicks) {
            return new FurnaceProgress(progressTicks);
        }
    }
}
