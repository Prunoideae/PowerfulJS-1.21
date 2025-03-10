package moe.wolfgirl.powerfuljs.custom.registries;

import dev.latvian.mods.kubejs.block.state.BlockStatePredicate;
import dev.latvian.mods.kubejs.typings.Info;
import moe.wolfgirl.powerfuljs.custom.logic.Effect;
import moe.wolfgirl.powerfuljs.custom.logic.Rule;
import moe.wolfgirl.powerfuljs.custom.logic.effects.CompositeEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.EffectJS;
import moe.wolfgirl.powerfuljs.custom.logic.effects.machine.*;
import moe.wolfgirl.powerfuljs.custom.logic.effects.energy.DrainEnergyEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.energy.FillEnergyEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.fluid.DrainFluidEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.fluid.FillFluidEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.item.ExtractItemEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.item.InsertItemEffect;
import moe.wolfgirl.powerfuljs.custom.logic.effects.reflective.ReflectiveAddProgress;
import moe.wolfgirl.powerfuljs.custom.logic.effects.reflective.ReflectiveMultiProgress;
import moe.wolfgirl.powerfuljs.custom.logic.rules.AttachmentRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.Chanced;
import moe.wolfgirl.powerfuljs.custom.logic.rules.RuleJS;
import moe.wolfgirl.powerfuljs.custom.logic.rules.energy.CanExtractEnergy;
import moe.wolfgirl.powerfuljs.custom.logic.rules.energy.CanReceiveEnergy;
import moe.wolfgirl.powerfuljs.custom.logic.rules.energy.HasEnergyRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.fluid.*;
import moe.wolfgirl.powerfuljs.custom.logic.rules.item.CanExtractItem;
import moe.wolfgirl.powerfuljs.custom.logic.rules.item.CanInsertItem;
import moe.wolfgirl.powerfuljs.custom.logic.rules.item.HasItemRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.logic.AlwaysRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.logic.AndRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.logic.OrRule;
import moe.wolfgirl.powerfuljs.custom.logic.rules.machine.*;
import moe.wolfgirl.powerfuljs.custom.logic.rules.reflective.ReflectiveAboutToFinish;
import moe.wolfgirl.powerfuljs.custom.logic.rules.reflective.ReflectiveRunning;
import moe.wolfgirl.powerfuljs.custom.logic.rules.world.*;
import moe.wolfgirl.powerfuljs.serde.SpeedModifiers;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class LogicRegistry {

    @SuppressWarnings("unused")
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

        public Rule yHigherThan(int min) {
            return yInRange(min, Integer.MAX_VALUE);
        }

        public Rule yLowerThan(int max) {
            return yInRange(Integer.MIN_VALUE, max);
        }

        public Rule yInRange(int min, int max) {
            return new YPosRule(min, max);
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

        public Rule powered() {
            return new RedstoneRule();
        }

        public Rule onSide(SidedRule.Side side) {
            return new SidedRule(side);
        }

        /* Fluid handling */
        public Rule hasFluid(SizedFluidIngredient fluidIngredient, @Nullable Direction direction) {
            return hasFluid(fluidIngredient, 0, direction);
        }

        /* Fluid handling */
        public Rule hasFluid(SizedFluidIngredient fluidIngredient, int tank, @Nullable Direction direction) {
            return new HasFluidRule(fluidIngredient, tank, direction);
        }

        public Rule canExtractFluid(FluidStack fluidStack, @Nullable Direction direction) {
            return new CanExtractFluid(fluidStack, direction);
        }

        public Rule canInsertFluid(FluidStack fluidStack, @Nullable Direction direction) {
            return new CanReceiveFluid(fluidStack, direction);
        }

        public Rule fluidEmpty(int tank, @Nullable Direction direction) {
            return new FluidEmptyRule(tank, direction);
        }

        public Rule fluidFull(int tank, @Nullable Direction direction) {
            return new FluidFullRule(tank, direction);
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

        /* Machine */
        public Rule aboutToFinish() {
            return new MachineAboutToFinish(1);
        }

        public Rule running() {
            return new MachineRunning();
        }

        public Rule doingRecipe(ResourceLocation... recipes) {
            return new MachineRunningRecipe(Set.of(recipes));
        }

        /* Reflective access of all simple blocks*/
        public Rule genericRunning(Class<BlockEntity> machineClass, String progress) throws NoSuchFieldException, NoSuchMethodException {
            return new ReflectiveRunning(machineClass, progress);
        }

        public Rule genericAboutToFinish(Class<BlockEntity> machineClass, String progress, String maxProgress) throws NoSuchFieldException, NoSuchMethodException {
            return new ReflectiveAboutToFinish(machineClass, 1, progress, maxProgress);
        }

        /* Attachments */
        public <T extends Comparable<T>> Rule testData(AttachmentType<T> attachmentType, T criteria, AttachmentRule.CompareAction action) {
            return new AttachmentRule.NativeComparable<>(attachmentType, criteria, action);
        }

        public <T> Rule customTestData(AttachmentType<T> attachmentType, Predicate<T> test) {
            return new AttachmentRule.Custom<>(attachmentType, test);
        }

        public Rule hasData(AttachmentType<?> attachmentType) {
            return new AttachmentRule.Present(attachmentType);
        }

        public Rule chanced(double chance) {
            return new Chanced(chance);
        }

        public Rule difficulty(Difficulty difficulty) {
            return new DifficultyRule(difficulty);
        }

        public Rule packMode(String packMode) {
            return new PackModeRule(packMode);
        }

        @Info("Get a value from the block entity, then test the value to meet a certain criteria. The value is cached and test won't happen if unchanged.")
        public <T> Rule custom(Function<BlockEntity, T> getter, Predicate<T> test) {
            return new RuleJS<>(getter, test);
        }
    }

    @SuppressWarnings("unused")
    public static class Effects {
        public static final Effects INSTANCE = new Effects();

        public Effect toggleEnable() {
            return new ToggleEnable();
        }

        @Info("Changes the machine ticking speed. An id is needed to prevent operation conflicts. `addProgress` is better for many BEs for the performance sake.")
        public Effect modifySpeed(SpeedModifiers.SpeedModifier tickSpeed) {
            return new TickRate(tickSpeed);
        }

        /* Fluid handling */
        public Effect fillFluid(FluidStack fluidStack, @Nullable Direction direction) {
            return fillFluid(fluidStack, false, direction);
        }

        @Info("Fills the fluid in the content, behavior depends on internal flag and actual container. If the container is a normal tank and internal, then it will fill/drain without limit on rate. If the container is a processing tank, then it will fill/drain the other than instead of \"normal\" tank that should be operated, e.g. draining the input, or filling the output.")
        public Effect fillFluid(FluidStack fluidStack, boolean internal, @Nullable Direction direction) {
            return new FillFluidEffect(fluidStack, internal, direction);
        }

        public Effect drainFluid(int amount, @Nullable Direction direction) {
            return drainFluid(amount, false, direction);
        }

        @Info("Drains the fluid in the content, behavior depends on internal flag and actual container. If the container is a normal tank and internal, then it will fill/drain without limit on rate. If the container is a processing tank, then it will fill/drain the other than instead of \"normal\" tank that should be operated, e.g. draining the input, or filling the output.")
        public Effect drainFluid(int amount, boolean internal, @Nullable Direction direction) {
            return new DrainFluidEffect(amount, internal, direction);
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

        /* Machine */
        public Effect addFuel(int fuelTicks) {
            return new MachineAddFuel(fuelTicks);
        }

        public Effect addProgress(float progressTicks) {
            return new MachineAddProgress(progressTicks);
        }

        public Effect clearProgress() {
            return new MachineClearProgress();
        }

        @Info("Add progress for a machine regardless the max progress of current work.")
        public Effect genericProgress(Class<BlockEntity> machineClass, String progress, int ticks) throws NoSuchFieldException, NoSuchMethodException {
            return genericProgress(machineClass, progress, null, ticks);
        }

        @Info("Add progress for a machine, this is aware of the case when a machine uses == check instead of >= check. Like minecraft furnaces.")
        public Effect genericProgress(Class<BlockEntity> machineClass, String progress, String maxProgress, int ticks) throws NoSuchFieldException, NoSuchMethodException {
            return new ReflectiveAddProgress(machineClass, ticks, progress, maxProgress);
        }

        public Effect genericMultiProgress(Class<BlockEntity> machineClass, String progress, int ticks) throws NoSuchFieldException, NoSuchMethodException {
            return genericMultiProgress(machineClass, progress, null, ticks);
        }

        public Effect genericMultiProgress(Class<BlockEntity> machineClass, String progress, String maxProgress, int ticks) throws NoSuchFieldException, NoSuchMethodException {
            return new ReflectiveMultiProgress(machineClass, ticks, progress, maxProgress);
        }

        @Info("Note that Rhino might be 10 or 100x slower than Java, so you shall not call this often for performance reason.")
        public Effect custom(EffectJS.Apply callback) {
            return new EffectJS(callback);
        }

        @Info("Test all the rules contained and execute effects if true. No shortcuts like `or` or `and`.")
        public Effect composite(Rule... rules) {
            return new CompositeEffect(List.of(rules));
        }
    }
}
