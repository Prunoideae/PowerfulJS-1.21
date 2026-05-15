package moe.wolfgirl.powerfuljs.plugins.docs;

import moe.wolfgirl.powerfuljs.custom.CapabilityJS;
import moe.wolfgirl.powerfuljs.custom.CapabilityWrapper;
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
import moe.wolfgirl.probejs.typescript.document.base.Type;
import moe.wolfgirl.probejs.typescript.document.builders.ClassBuilder;
import moe.wolfgirl.probejs.typescript.document.members.MethodDecl;
import moe.wolfgirl.probejs.typescript.document.members.ParamDecl;
import moe.wolfgirl.probejs.typescript.document.types.special.NamespacedType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

import java.util.HashSet;
import java.util.Set;

public class CapabilityJSDoc extends ProbeJSPlugin {
    public static final ClassPath CAPABILITY_TYPES = ClassPath.special("types.powerfuljs.CapabilityTypes");
    public static final NamespacedType ITEM_MAP = Types.namespaced(CAPABILITY_TYPES, "ItemMap");
    public static final NamespacedType BLOCK_MAP = Types.namespaced(CAPABILITY_TYPES, "BlockMap");
    public static final NamespacedType ENTITY_MAP = Types.namespaced(CAPABILITY_TYPES, "EntityMap");


    @Override
    public void modifyClasses(Documents.ClassAccessor classDocuments) {
        var capabilityWrapper = classDocuments.getDocument(CapabilityWrapper.class);
        if (capabilityWrapper instanceof ClassDecl classDecl) {
            for (Code member : classDecl.members) {
                if (member instanceof MethodDecl methodDecl) {
                    if (methodDecl.name.equals("item")) patchMethod(methodDecl, ITEM_MAP);
                    if (methodDecl.name.equals("block")) patchMethod(methodDecl, BLOCK_MAP);
                    if (methodDecl.name.equals("entity")) patchMethod(methodDecl, ENTITY_MAP);
                }
            }
        }
    }

    private void patchMethod(MethodDecl methodDecl, Type mapType) {
        methodDecl.typeParams.add(Types.variable("T", Types.wrapped("keyof %s", mapType)));
        methodDecl.params.set(0, new ParamDecl("capability", Types.variable("T")));
        methodDecl.returnType = Types.wrapped("%s[T]", mapType);
    }


    @Override
    public void addSpecialDocuments(DocumentRegistrar registrar) {
        ClassBuilder capabilityTypes = Members.clazz(CAPABILITY_TYPES).kind(KindAware.Kind.NAMESPACE);
        capabilityTypes.member(getMappedType("ItemMap", CapabilityJS.ITEM, ItemCapability.class));
        capabilityTypes.member(getMappedType("BlockMap", CapabilityJS.BLOCK, BlockCapability.class));
        capabilityTypes.member(getMappedType("EntityMap", CapabilityJS.ENTITY, EntityCapability.class));
        registrar.addDocument(CAPABILITY_TYPES, capabilityTypes.build());
    }

    public <O extends BaseCapability<?, ?>> TypeDecl getMappedType(String mapName, CapabilityJS<O> capabilityJS, Class<?> capClass) {
        return new TypeDecl(CAPABILITY_TYPES.append(mapName), Types.object(builder -> {
            capabilityJS.getCapabilities().forEach(cap -> {
                ResourceLocation key = cap.name();
                Class<?> typeClass = cap.typeClass();
                Class<?> contextClass = cap.contextClass();
                builder.param(key.toString(), Types.clazz(capClass).withParams(
                        Types.clazz(typeClass),
                        contextClass == void.class ? Types.VOID : Types.clazz(contextClass)
                ));
            });
        }), false);
    }


    private <O extends BaseCapability<?, ?>> void addCapabilityClasses(CapabilityJS<O> capabilityJS, Set<Class<?>> allClass) {
        capabilityJS.getCapabilities().forEach(o -> {
            if (o.contextClass() != void.class) allClass.add(o.contextClass());
            allClass.add(o.typeClass());
        });
    }

    @Override
    public Set<Class<?>> provideClassForDiscovery() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(CapabilityWrapper.class);
        addCapabilityClasses(CapabilityJS.BLOCK, classes);
        addCapabilityClasses(CapabilityJS.ENTITY, classes);
        addCapabilityClasses(CapabilityJS.ITEM, classes);
        return classes;
    }
}
