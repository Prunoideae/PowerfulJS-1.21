package moe.wolfgirl.powerfuljs.plugins;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.misc.PotionBuilder;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.BuilderTypeRegistry;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import dev.latvian.mods.kubejs.script.ScriptManager;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.script.TypeWrapperRegistry;
import moe.wolfgirl.powerfuljs.custom.CapabilityJS;
import moe.wolfgirl.powerfuljs.custom.CapabilityWrapper;
import moe.wolfgirl.powerfuljs.custom.attachment.ContentTypes;
import moe.wolfgirl.powerfuljs.custom.attachment.PrimitiveTypes;
import moe.wolfgirl.powerfuljs.events.PowerfulEvents;
import moe.wolfgirl.powerfuljs.events.PowerfulInterceptTickingEvent;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class PowerfulJSPlugin implements KubeJSPlugin {

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(PowerfulEvents.GROUP);
    }

    @Override
    public void registerBindings(BindingRegistry bindings) {
        bindings.add("Capabilities", CapabilityWrapper.class);
        bindings.add("PowerfulJS", PowerfulJS.class);
    }

    @Override
    public void registerBuilderTypes(BuilderTypeRegistry registry) {
        registry.of(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, reg -> {
            reg.add("int", PrimitiveTypes.IntegerType.class, PrimitiveTypes.IntegerType::new);
            reg.add("float", PrimitiveTypes.FloatType.class, PrimitiveTypes.FloatType::new);
            reg.add("string", PrimitiveTypes.StringType.class, PrimitiveTypes.StringType::new);
            reg.add("boolean", PrimitiveTypes.BoolType.class, PrimitiveTypes.BoolType::new);
            reg.add("custom", PrimitiveTypes.ObjectType.class, PrimitiveTypes.ObjectType::new);

            reg.add("itemstack", ContentTypes.ItemStackType.class, ContentTypes.ItemStackType::new);
            reg.add("fluidstack", ContentTypes.FluidStackType.class, ContentTypes.FluidStackType::new);
            reg.add("potion_contents", ContentTypes.PotionType.class, PotionBuilder::new);
            reg.add("enchantments", ContentTypes.EnchantmentType.class, ContentTypes.EnchantmentType::new);
        });
    }

    @Override
    public void afterScriptsLoaded(ScriptManager manager) {
        if (manager.scriptType == ScriptType.SERVER) {
            PowerfulEvents.INTERCEPT_TICKING.post(new PowerfulInterceptTickingEvent());
        }
    }

    @Override
    public void registerTypeWrappers(TypeWrapperRegistry registry) {
        CapabilityJS.init();
        registry.register(BlockCapability.class, CapabilityJS.BLOCK::wrap);
        registry.register(ItemCapability.class, CapabilityJS.ITEM::wrap);
        registry.register(EntityCapability.class, CapabilityJS.ENTITY::wrap);
    }
}
