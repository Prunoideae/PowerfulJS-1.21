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
import moe.wolfgirl.powerfuljs.events.PowerfulModifyBlockEntityEvent;
import moe.wolfgirl.powerfuljs.utils.MCID;
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
    }

    @Override
    public void registerBuilderTypes(BuilderTypeRegistry registry) {
        registry.of(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, reg -> {
            reg.add(MCID.create("int"), PrimitiveTypes.IntegerType.class, PrimitiveTypes.IntegerType::new);
            reg.add(MCID.create("float"), PrimitiveTypes.FloatType.class, PrimitiveTypes.FloatType::new);
            reg.add(MCID.create("string"), PrimitiveTypes.StringType.class, PrimitiveTypes.StringType::new);
            reg.add(MCID.create("boolean"), PrimitiveTypes.BoolType.class, PrimitiveTypes.BoolType::new);
            reg.add(MCID.create("custom"), PrimitiveTypes.ObjectType.class, PrimitiveTypes.ObjectType::new);

            reg.add(MCID.create("itemstack"), ContentTypes.ItemStackType.class, ContentTypes.ItemStackType::new);
            reg.add(MCID.create("fluidstack"), ContentTypes.FluidStackType.class, ContentTypes.FluidStackType::new);
            reg.add(MCID.create("potion_contents"), ContentTypes.PotionType.class, PotionBuilder::new);
            reg.add(MCID.create("enchantments"), ContentTypes.EnchantmentType.class, ContentTypes.EnchantmentType::new);
        });
    }

    @Override
    public void afterScriptsLoaded(ScriptManager manager) {
        if (manager.scriptType == ScriptType.SERVER) {
            PowerfulEvents.MODIFY_BLOCK_ENTITY.post(new PowerfulModifyBlockEntityEvent());
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
