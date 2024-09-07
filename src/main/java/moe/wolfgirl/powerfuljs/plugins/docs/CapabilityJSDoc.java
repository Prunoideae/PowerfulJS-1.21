package moe.wolfgirl.powerfuljs.plugins.docs;

import moe.wolfgirl.powerfuljs.custom.CapabilityJS;
import moe.wolfgirl.powerfuljs.custom.CapabilityWrapper;
import moe.wolfgirl.probejs.lang.java.clazz.ClassPath;
import moe.wolfgirl.probejs.lang.typescript.ScriptDump;
import moe.wolfgirl.probejs.lang.typescript.TypeScriptFile;
import moe.wolfgirl.probejs.lang.typescript.code.member.InterfaceDecl;
import moe.wolfgirl.probejs.lang.typescript.code.member.MethodDecl;
import moe.wolfgirl.probejs.lang.typescript.code.member.ParamDecl;
import moe.wolfgirl.probejs.lang.typescript.code.member.TypeDecl;
import moe.wolfgirl.probejs.lang.typescript.code.type.BaseType;
import moe.wolfgirl.probejs.lang.typescript.code.type.Types;
import moe.wolfgirl.probejs.lang.typescript.code.type.js.JSObjectType;
import moe.wolfgirl.probejs.plugin.ProbeJSPlugin;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CapabilityJSDoc extends ProbeJSPlugin {

    @Override
    public void modifyClasses(ScriptDump scriptDump, Map<ClassPath, TypeScriptFile> globalClasses) {
        TypeScriptFile scriptFile = globalClasses.get(new ClassPath(CapabilityWrapper.class));
        if (scriptFile == null) return;

        generateMappedTypes("BlockCapabilityMap", "BlockCapabilities",
                CapabilityJS.BLOCK, BlockCapability.class, scriptFile);
        generateMappedTypes("EntityCapabilityMap", "EntityCapabilities",
                CapabilityJS.ENTITY, EntityCapability.class, scriptFile);
        generateMappedTypes("ItemCapabilityMap", "ItemCapabilities",
                CapabilityJS.ITEM, ItemCapability.class, scriptFile);

        var classDecl = scriptFile.findCode(InterfaceDecl.class).orElseThrow();
        for (MethodDecl method : classDecl.methods) {
            if (method.name.equals("item")) {
                patchMethod(method, "ItemCapabilities", "ItemCapabilityMap");
            }
            if (method.name.equals("block")) {
                patchMethod(method, "BlockCapabilities", "BlockCapabilityMap");
            }
            if (method.name.equals("entity")) {
                patchMethod(method, "EntityCapabilities", "EntityCapabilityMap");
            }
        }
    }

    private void patchMethod(MethodDecl methodDecl, String flagName, String mapName) {
        methodDecl.variableTypes.add(Types.generic("T", Types.primitive(flagName)));
        methodDecl.params.set(0, new ParamDecl("capability", Types.generic("T"), false, false));
        methodDecl.returnType = Types.primitive("%s[T]".formatted(mapName));
    }

    private <O extends BaseCapability<?, ?>> void addCapabilityClasses(CapabilityJS<O> capabilityJS, Set<Class<?>> allClass) {
        capabilityJS.getCapabilities().forEach(o -> {
            if (o.contextClass() != void.class) allClass.add(o.contextClass());
            allClass.add(o.typeClass());
        });
    }

    @Override
    public Set<Class<?>> provideJavaClass(ScriptDump scriptDump) {
        Set<Class<?>> classes = new HashSet<>();
        addCapabilityClasses(CapabilityJS.BLOCK, classes);
        addCapabilityClasses(CapabilityJS.ENTITY, classes);
        addCapabilityClasses(CapabilityJS.ITEM, classes);
        return classes;
    }

    private <O extends BaseCapability<?, ?>> void generateMappedTypes(String mapName, String flagName,
                                                                      CapabilityJS<O> capabilityJS,
                                                                      Class<?> capClass,
                                                                      TypeScriptFile typeScriptFile) {
        JSObjectType.Builder typeDict = Types.object();
        BaseType capType = Types.type(capClass);

        capabilityJS.getCapabilities().forEach(cap -> {
            ResourceLocation key = cap.name();
            Class<?> typeClass = cap.typeClass();
            Class<?> contextClass = cap.contextClass();
            typeDict.member(key.toString(),
                    Types.ignoreContext(Types.parameterized(
                            capType,
                            Types.type(typeClass),
                            contextClass == void.class ? Types.VOID : Types.type(contextClass)
                    ), BaseType.FormatType.RETURN)
            );
        });

        typeScriptFile.addCode(new TypeDecl(mapName, typeDict.buildIndexed()));
        typeScriptFile.addCode(new TypeDecl(flagName, Types.primitive("keyof %s".formatted(mapName))));
    }
}
