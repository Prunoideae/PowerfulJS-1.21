package moe.wolfgirl.powerfuljs.custom.registries;

import dev.latvian.mods.kubejs.block.state.BlockStatePredicate;
import dev.latvian.mods.kubejs.typings.Info;
import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import moe.wolfgirl.powerfuljs.custom.logic.effects.EffectJS;
import moe.wolfgirl.powerfuljs.custom.logic.effects.cooker.CampfireProgress;
import moe.wolfgirl.powerfuljs.custom.logic.effects.machine.TickRate;
import moe.wolfgirl.powerfuljs.custom.logic.effects.machine.ToggleEnable;
import moe.wolfgirl.powerfuljs.custom.logic.effects.energy.DrainEnergyEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.energy.FillEnergyEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.fluid.DrainFluidEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.fluid.FillFluidEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.cooker.FurnaceFuel;
import moe.wolfgirl.powerfuljs.custom.logic.effects.cooker.FurnaceProgress;
import moe.wolfgirl.powerfuljs.custom.logic.effects.item.ExtractItemEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.item.InsertItemEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.reflective.ReflectiveAddProgress;
import moe.wolfgirl.powerfuljs.custom.logic.effects.reflective.ReflectiveMultiProgress;
import moe.wolfgirl.powerfuljs.custom.logic.rules.Chanced;
import moe.wolfgirl.powerfuljs.custom.logic.rules.cooker.CampfireAboutToFinishRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.cooker.CampfireRunningRule;
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
import moe.wolfgirl.powerfuljs.custom.logic.rules.cooker.FurnaceAboutToFinishRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.machine.StageRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.reflective.ReflectiveAboutToFinish;
import moe.wolfgirl.powerfuljs.custom.logic.rules.reflective.ReflectiveRunning;
import moe.wolfgirl.powerfuljs.custom.logic.rules.world.*;
import moe.wolfgirl.powerfuljs.custom.logic.rules.cooker.FurnaceRunningRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.machine.TickRateRule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LogicRegistry {
    public static class Rules {
        public static final Rules INSTANCE = new Rules();

        public Rule always() {
            return always(true);
        }

        public Rule always(boolean state) {
            return new AlwaysRule(state);
        }

        public Rule and(Rule... rules) {
            return new AndRule(List.of(rules));
        }

        public Rule or(Rule... rules) {
            return new OrRule(List.of(rules));
        }

        /* World stuffs */

        public Rule nbt(CompoundTag tag) {
            return new NBTRule(tag);
        }

        public Rule matchBlock(BlockStatePredicate blockState) {
            return new MatchBlockRule(blockState);
        }

        public Rule every(int ticks) {
            return new TickRateRule(ticks);
        }

        public Rule ownerStage(String stage) {
            return new StageRule(stage);
        }

        public Rule higherThan(int y) {
            return new YPosRule(y);
        }

        public Rule brighterThan(int brightness) {
            return new LightRule(brightness);
        }

        public Rule hasSunlight() {
            return new CanSeeSkyRule();
        }

        public Rule withinTime(int start, int end) {
            return new TimeRule(start, end);
        }

        public Rule inBiome(TagKey<Biome> biomeTag) {
            return new InBiomeRule(biomeTag);
        }

        /* Fluid handling */
        public Rule hasFluid(SizedFluidIngredient fluidIngredient, @Nullable Direction direction) {
            return new HasFluidRule(fluidIngredient, direction);
        }

        public Rule canExtractFluid(FluidStack fluidStack, @Nullable Direction direction) {
            return new CanExtractFluid(fluidStack, direction);
        }

        public Rule canInsertFluid(FluidStack fluidStack, @Nullable Direction direction) {
            return new CanReceiveFluid(fluidStack, direction);
        }

        /* Energy handling */
        public Rule hasEnergy(int energy, @Nullable Direction direction) {
            return new HasEnergyRule(energy, direction);
        }

        public Rule canExtractEnergy(int energy, @Nullable Direction direction) {
            return new CanExtractEnergy(energy, direction);
        }

        public Rule canReceiveEnergy(int energy, @Nullable Direction direction) {
            return new CanReceiveEnergy(energy, direction);
        }

        /* Item handling */
        public Rule hasItem(Ingredient item, int count, @Nullable Direction direction) {
            return new HasItemRule(item, count, direction);
        }

        public Rule hasItem(SizedIngredient item, @Nullable Direction direction) {
            return hasItem(item.ingredient(), item.count(), direction);
        }

        public Rule canExtractItem(ItemStack itemStack, @Nullable Direction direction) {
            return new CanExtractItem(itemStack, direction);
        }

        public Rule canInsertItem(ItemStack itemStack, @Nullable Direction direction) {
            return new CanInsertItem(itemStack, direction);
        }

        /* Furnace */
        public Rule furnaceRunning() {
            return new FurnaceRunningRule();
        }

        @Info("Test if the furnace is **exactly** one tick away from producing results. Must be run after any tick modification effect.")
        public Rule furnaceAboutToFinish() {
            return new FurnaceAboutToFinishRule(1);
        }

        /* Campfire */
        public Rule campfireRunning() {
            return new CampfireRunningRule();
        }

        @Info("Test if the campfire is **exactly** one tick away from producing results. Must be run after any tick modification effect. When cooking multiple things, the slowest item will be counted.")
        public Rule campfireAboutToFinish() {
            return new CampfireAboutToFinishRule(1);
        }

        /* Reflective access of all simple blocks*/
        public Rule genericRunning(Class<BlockEntity> machineClass, String progress) throws NoSuchFieldException {
            return new ReflectiveRunning(machineClass, progress);
        }

        public Rule genericAboutToFinish(Class<BlockEntity> machineClass, String progress, String maxProgress) throws NoSuchFieldException {
            return new ReflectiveAboutToFinish(machineClass, 1, progress, maxProgress);
        }

        public Rule chanced(double chance) {
            return new Chanced(chance);
        }
    }

    public static class Effects {
        public static final Effects INSTANCE = new Effects();

        public Effect toggleEnable() {
            return new ToggleEnable();
        }

        @Info("Changes the tick speed to Nx of the original, e.g. 0.1 will make it 90% slower, and 2 will make it 2x faster.")
        public Effect changeTickSpeed(float tickSpeed) {
            return new TickRate(tickSpeed);
        }

        /* Fluid handling */
        public Effect fillFluid(FluidStack fluidStack, @Nullable Direction direction) {
            return fillFluid(fluidStack, false, direction);
        }

        public Effect fillFluid(FluidStack fluidStack, boolean forced, @Nullable Direction direction) {
            return new FillFluidEffect(fluidStack, forced, direction);
        }

        public Effect drainFluid(FluidStack fluidStack, @Nullable Direction direction) {
            return drainFluid(fluidStack, false, direction);
        }

        public Effect drainFluid(FluidStack fluidStack, boolean forced, @Nullable Direction direction) {
            return new DrainFluidEffect(fluidStack, forced, direction);
        }

        /* Energy handling */
        public Effect fillEnergy(int energy, @Nullable Direction direction) {
            return fillEnergy(energy, false, direction);
        }

        public Effect fillEnergy(int energy, boolean forced, @Nullable Direction direction) {
            return new FillEnergyEffect(energy, forced, direction);
        }

        public Effect drainEnergy(int energy, @Nullable Direction direction) {
            return drainEnergy(energy, false, direction);
        }

        public Effect drainEnergy(int energy, boolean forced, @Nullable Direction direction) {
            return new DrainEnergyEffect(energy, forced, direction);
        }

        /* Item handling */
        public Effect insertItem(ItemStack itemStack, @Nullable Direction context) {
            return new InsertItemEffect(itemStack, context);
        }

        public Effect extractItem(ItemStack itemStack, @Nullable Direction context) {
            return new ExtractItemEffect(itemStack, context);
        }

        /* Furnace, it's the only 'machine' in minecraft lol */
        public Effect addFurnaceFuel(int fuelTicks) {
            return new FurnaceFuel(fuelTicks);
        }

        public Effect addFurnaceProgress(int progressTicks) {
            return new FurnaceProgress(progressTicks);
        }

        /* Well and campfire*/
        public Effect addCampfireProgress(int progressTicks) {
            return new CampfireProgress(progressTicks);
        }

        public Effect genericProgress(Class<BlockEntity> machineClass, String progress, String maxProgress, int ticks) throws NoSuchFieldException {
            return new ReflectiveAddProgress(machineClass, ticks, progress, maxProgress);
        }

        public Effect genericMultiProgress(Class<BlockEntity> machineClass, String progress, String maxProgress, int ticks) throws NoSuchFieldException {
            return new ReflectiveMultiProgress(machineClass, ticks, progress, maxProgress);
        }

        @Info("Note that Rhino might be 10 or 100x slower than Java, so you shall not call this often for performance reason.")
        public Effect custom(EffectJS.Apply callback) {
            return new EffectJS(callback);
        }
    }
}
