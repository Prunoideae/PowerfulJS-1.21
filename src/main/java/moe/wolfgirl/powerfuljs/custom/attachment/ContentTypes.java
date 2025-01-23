package moe.wolfgirl.powerfuljs.custom.attachment;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.function.Supplier;

/**
 * Stores content, e.g. item, fluid, enchantment...
 */
public class ContentTypes {
    public static class ItemStackType extends AttachmentBuilder<ItemStack> {

        public ItemStackType(ResourceLocation id) {
            super(id);
            typeClass = ItemStack.class;
            defaultGetter = () -> ItemStack.EMPTY;
            codec = ItemStack.OPTIONAL_CODEC;
        }

        @Override
        public AttachmentBuilder<ItemStack> defaultGetter(Supplier<ItemStack> defaultGetter) {
            return super.defaultGetter(defaultGetter);
        }
    }

    public static class FluidStackType extends AttachmentBuilder<FluidStack> {

        public FluidStackType(ResourceLocation id) {
            super(id);
            typeClass = FluidStack.class;
            defaultGetter = () -> FluidStack.EMPTY;
            codec = FluidStack.OPTIONAL_CODEC;
        }

        @Override
        public AttachmentBuilder<FluidStack> defaultGetter(Supplier<FluidStack> defaultGetter) {
            return super.defaultGetter(defaultGetter);
        }
    }

    public static class PotionType extends AttachmentBuilder<PotionContents> {

        public PotionType(ResourceLocation id) {
            super(id);
            typeClass = PotionContents.class;
            defaultGetter = () -> PotionContents.EMPTY;
            codec = PotionContents.CODEC;
        }

        @Override
        public AttachmentBuilder<PotionContents> defaultGetter(Supplier<PotionContents> defaultGetter) {
            return super.defaultGetter(defaultGetter);
        }
    }

    public static class EnchantmentType extends AttachmentBuilder<ItemEnchantments> {

        public EnchantmentType(ResourceLocation id) {
            super(id);
            typeClass = ItemEnchantments.class;
            defaultGetter = () -> ItemEnchantments.EMPTY;
            codec = ItemEnchantments.CODEC;
        }

        @Override
        public AttachmentBuilder<ItemEnchantments> defaultGetter(Supplier<ItemEnchantments> defaultGetter) {
            return super.defaultGetter(defaultGetter);
        }
    }
}
