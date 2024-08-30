package moe.wolfgirl.powerfuljs.plugins;

import moe.wolfgirl.powerfuljs.custom.base.CapabilityBuilder;
import moe.wolfgirl.powerfuljs.custom.registries.BlockCapabilityRegistry;
import moe.wolfgirl.powerfuljs.custom.registries.EntityCapabilityRegistry;
import moe.wolfgirl.powerfuljs.custom.registries.ItemCapabilityRegistry;
import moe.wolfgirl.powerfuljs.events.PowerfulRegisterCapabilitiesEvent;
import moe.wolfgirl.probejs.lang.java.clazz.ClassPath;
import moe.wolfgirl.probejs.lang.transpiler.TypeConverter;
import moe.wolfgirl.probejs.lang.typescript.ScriptDump;
import moe.wolfgirl.probejs.lang.typescript.TypeScriptFile;
import moe.wolfgirl.probejs.lang.typescript.code.Code;
import moe.wolfgirl.probejs.lang.typescript.code.member.ClassDecl;
import moe.wolfgirl.probejs.lang.typescript.code.member.MethodDecl;
import moe.wolfgirl.probejs.lang.typescript.code.member.ParamDecl;
import moe.wolfgirl.probejs.lang.typescript.code.member.TypeDecl;
import moe.wolfgirl.probejs.lang.typescript.code.ts.Statements;
import moe.wolfgirl.probejs.lang.typescript.code.type.Types;
import moe.wolfgirl.probejs.lang.typescript.code.type.js.JSObjectType;
import moe.wolfgirl.probejs.plugin.ProbeJSPlugin;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PowerfulJSProbePlugin extends ProbeJSPlugin {
    private static final Set<String> REMOVED = Set.of(
            "registerBlock",
            "registerBlockEntity",
            "registerItem",
            "registerEntity"
    );

    @Override
    public void modifyClasses(ScriptDump scriptDump, Map<ClassPath, TypeScriptFile> globalClasses) {
        TypeConverter converter = scriptDump.transpiler.typeConverter;
        TypeScriptFile scriptFile = globalClasses.get(new ClassPath(PowerfulRegisterCapabilitiesEvent.class));
        if (scriptFile == null) return;

        generateMappedTypes("BlockEntityCapabilityMap", "BlockEntityCapabilities", BlockCapabilityRegistry.BLOCK_ENTITIES, converter, scriptFile);
        generateMappedTypes("BlockCapabilityMap", "BlockCapabilities", BlockCapabilityRegistry.BLOCKS, converter, scriptFile);
        generateMappedTypes("EntityCapabilityMap", "EntityCapabilities", EntityCapabilityRegistry.ENTITY, converter, scriptFile);
        generateMappedTypes("ItemCapabilityMap", "ItemCapabilities", ItemCapabilityRegistry.ITEM, converter, scriptFile);

        var classDecl = scriptFile.findCode(ClassDecl.class).orElseThrow();
        for (MethodDecl method : classDecl.methods) {
            // This is so cursed because I can't type a Map<ResourceLocation, CapabilityBuilder<?, ?>>
            if (method.name.equals("registerBlock")) {
                patchMethod(method, "BlockCapabilities", "BlockCapabilityMap");
            }
            if (method.name.equals("registerBlockEntity")) {
                patchMethod(method, "BlockEntityCapabilities", "BlockEntityCapabilityMap");
            }
            if (method.name.equals("registerItem")) {
                patchMethod(method, "ItemCapabilities", "ItemCapabilityMap");
            }
            if (method.name.equals("registerEntity")) {
                patchMethod(method, "EntityCapabilities", "EntityCapabilityMap");
            }
        }
    }

    private <O> void generateMappedTypes(String mapName, String flagName, Map<ResourceLocation, CapabilityBuilder<O, ?>> registry, TypeConverter converter, TypeScriptFile typeScriptFile) {
        JSObjectType.Builder typeDict = Types.object();
        for (Map.Entry<ResourceLocation, CapabilityBuilder<O, ?>> entry : registry.entrySet()) {
            ResourceLocation key = entry.getKey();
            CapabilityBuilder<O, ?> builder = entry.getValue();

            typeDict.member(key.toString(), converter.convertType(builder.typeInfo()));
        }
        typeScriptFile.addCode(new TypeDecl(mapName, typeDict.buildIndexed()));
        typeScriptFile.addCode(new TypeDecl(flagName, Types.primitive("keyof %s".formatted(mapName))));
    }

    private void patchMethod(MethodDecl methodDecl, String flagName, String mapName) {
        methodDecl.variableTypes.add(Types.generic("T", Types.primitive(flagName)));
        methodDecl.params.set(0, new ParamDecl("builderKey", Types.generic("T"), false, false));
        methodDecl.params.set(1, new ParamDecl("configuration", Types.primitive("%s[T]".formatted(mapName)), false, false));
    }

    private <O> @NotNull List<MethodDecl> patch(MethodDecl method, TypeConverter converter, Map<ResourceLocation, CapabilityBuilder<O, ?>> registry) {
        List<MethodDecl> methods = new ArrayList<>();
        for (Map.Entry<ResourceLocation, CapabilityBuilder<O, ?>> entry : registry.entrySet()) {
            ResourceLocation key = entry.getKey();
            CapabilityBuilder<O, ?> builder = entry.getValue();

            MethodDecl generated = new MethodDecl(
                    method.name,
                    method.variableTypes,
                    new ArrayList<>(method.params),
                    method.returnType
            );
            generated.params.set(0, new ParamDecl(
                    "builderKey",
                    Types.literal(key.toString()),
                    false,
                    false)
            );
            generated.params.set(1, new ParamDecl(
                    "configuration",
                    converter.convertType(builder.typeInfo()),
                    false,
                    false)
            );

            methods.add(generated);
        }
        return methods;
    }
}
