package moe.wolfgirl.powerfuljs.mixin;

import moe.wolfgirl.powerfuljs.utils.ModUtils;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {
    private static final List<TargetChecker> CHECKERS = List.of(
            new TargetChecker("mekanism", "mekanism.common.Mekanism"),
            new TargetChecker("farmers_delight", "vectorwing.farmersdelight.FarmersDelight"),
            new TargetChecker("create", "com.simibubi.create.Create"),
            new TargetChecker("createaddition", "com.mrh0.createaddition.CreateAddition"),
            new TargetChecker("create_enchantment_industry", "plus.dragons.createenchantmentindustry.common.CEICommon")
    );

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }


    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        for (TargetChecker checker : CHECKERS) if (checker.shouldDeny(mixinClassName)) return false;
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    private record TargetChecker(String modId, String mainClass) {
        private boolean shouldDeny(String mixinClassName) {
            // Add . to prevent name conflict (though I doubt if it's necessary
            return mixinClassName.contains(modId + ".") && !ModUtils.isModPresent(modId, mainClass);
        }
    }
}
