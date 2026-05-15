package moe.wolfgirl.powerfuljs.plugins.docs;

import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.registries.BlockCapabilityRegistry;
import moe.wolfgirl.powerfuljs.custom.registries.EntityCapabilityRegistry;
import moe.wolfgirl.powerfuljs.custom.registries.ItemCapabilityRegistry;
import moe.wolfgirl.powerfuljs.events.PowerfulRegisterCapabilitiesEvent;
import moe.wolfgirl.probejs.plugin.ProbeJSPlugin;
import moe.wolfgirl.probejs.typescript.ClassPath;
import moe.wolfgirl.probejs.typescript.Documents;
import moe.wolfgirl.probejs.typescript.base.DocumentRegistrar;
import moe.wolfgirl.probejs.typescript.document.ClassDecl;
import moe.wolfgirl.probejs.typescript.document.Members;
import moe.wolfgirl.probejs.typescript.document.TypeDecl;
import moe.wolfgirl.probejs.typescript.document.Types;
import moe.wolfgirl.probejs.typescript.document.base.Code;
import moe.wolfgirl.probejs.typescript.document.base.KindAware;
import moe.wolfgirl.probejs.typescript.document.members.MethodDecl;
import moe.wolfgirl.probejs.typescript.document.members.ParamDecl;
import moe.wolfgirl.probejs.typescript.document.types.special.NamespacedType;
import moe.wolfgirl.probejs.typescript.transpiler.TypeConverter;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RegisterCapabilityEvent extends ProbeJSPlugin {
    public static final ClassPath CAPABILITY_EVENT_TYPE = ClassPath.special("types.powerfuljs.CapabilityEventTypes");
    public static final NamespacedType BLOCK_ENTITY_BUILDERS = Types.namespaced(CAPABILITY_EVENT_TYPE, "BlockEntityBuilders");
    public static final NamespacedType BLOCK_BUILDERS = Types.namespaced(CAPABILITY_EVENT_TYPE, "BlockBuilders");
    public static final NamespacedType ENTITY_BUILDERS = Types.namespaced(CAPABILITY_EVENT_TYPE, "EntityBuilders");
    public static final NamespacedType ITEM_BUILDERS = Types.namespaced(CAPABILITY_EVENT_TYPE, "ItemBuilders");

    @Override
    public void modifyClasses(Documents.ClassAccessor classDocuments) {
        var document = classDocuments.getDocument(PowerfulRegisterCapabilitiesEvent.class);
        if (document instanceof ClassDecl classDecl) {
            for (Code member : classDecl.members) {
                if (member instanceof MethodDecl methodDecl) {
                    if (methodDecl.name.equals("registerBlock")) patchMethod(methodDecl, BLOCK_BUILDERS);
                    if (methodDecl.name.equals("registerBlockEntity")) patchMethod(methodDecl, BLOCK_ENTITY_BUILDERS);
                    if (methodDecl.name.equals("registerItem")) patchMethod(methodDecl, ITEM_BUILDERS);
                    if (methodDecl.name.equals("registerEntity")) patchMethod(methodDecl, ENTITY_BUILDERS);
                }
            }
        }
    }

    private void patchMethod(MethodDecl methodDecl, NamespacedType mapType) {
        methodDecl.typeParams.add(Types.variable("T", Types.wrapped("keyof %s", mapType)));
        methodDecl.params.set(0, new ParamDecl("builderKey", Types.variable("T")));
        methodDecl.params.set(1, new ParamDecl("configuration", Types.wrapped("%s[T]", mapType)));
    }

    @Override
    public void addSpecialDocuments(DocumentRegistrar registrar) {
        var converter = new TypeConverter();
        var classBuilder = Members.clazz(CAPABILITY_EVENT_TYPE).kind(KindAware.Kind.NAMESPACE);
        classBuilder.member(getMappedType("BlockEntityBuilders", BlockCapabilityRegistry.BLOCK_ENTITIES, converter));
        classBuilder.member(getMappedType("BlockBuilders", BlockCapabilityRegistry.BLOCKS, converter));
        classBuilder.member(getMappedType("EntityBuilders", EntityCapabilityRegistry.ENTITY, converter));
        classBuilder.member(getMappedType("ItemBuilders", ItemCapabilityRegistry.ITEM, converter));
        registrar.addDocument(CAPABILITY_EVENT_TYPE, classBuilder.build());
    }

    private <O> TypeDecl getMappedType(String mapName, Map<ResourceLocation, CapabilityBuilder<O, ?>> registry, TypeConverter converter) {
        return new TypeDecl(CAPABILITY_EVENT_TYPE.append(mapName), Types.object(builder -> {
            for (Map.Entry<ResourceLocation, CapabilityBuilder<O, ?>> entry : registry.entrySet()) {
                ResourceLocation key = entry.getKey();
                CapabilityBuilder<O, ?> capBuilder = entry.getValue();
                builder.param(key.toString(), converter.convertType(capBuilder.typeInfo()).markAsInput());
            }
        }), false);
    }

    @Override
    public Set<Class<?>> provideClassForDiscovery() {
        Set<Class<?>> classes = new HashSet<>();
        addBuilderClasses(BlockCapabilityRegistry.BLOCK_ENTITIES, classes);
        addBuilderClasses(BlockCapabilityRegistry.BLOCKS, classes);
        addBuilderClasses(ItemCapabilityRegistry.ITEM, classes);
        addBuilderClasses(EntityCapabilityRegistry.ENTITY, classes);
        return classes;
    }

    private <O> void addBuilderClasses(Map<ResourceLocation, CapabilityBuilder<O, ?>> builders, Set<Class<?>> allClass) {
        for (CapabilityBuilder<O, ?> value : builders.values()) allClass.add(value.typeInfo().asClass());
    }
}
