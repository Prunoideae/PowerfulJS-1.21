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
import moe.wolfgirl.probejs.lang.typescript.code.member.ClassDecl;
import moe.wolfgirl.probejs.lang.typescript.code.member.MethodDecl;
import moe.wolfgirl.probejs.lang.typescript.code.member.ParamDecl;
import moe.wolfgirl.probejs.lang.typescript.code.type.Types;
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

        var classDecl = scriptFile.findCode(ClassDecl.class).orElseThrow();
        List<MethodDecl> added = new ArrayList<>();
        for (MethodDecl method : classDecl.methods) {
            // This is so cursed because I can't type a Map<ResourceLocation, CapabilityBuilder<?, ?>>
            if (method.name.equals("registerBlock")) {
                added.addAll(patch(method, converter, BlockCapabilityRegistry.BLOCKS));
            }
            if (method.name.equals("registerBlockEntity")) {
                added.addAll(patch(method, converter, BlockCapabilityRegistry.BLOCK_ENTITIES));
            }
            if (method.name.equals("registerItem")) {
                added.addAll(patch(method, converter, ItemCapabilityRegistry.ITEM));
            }
            if (method.name.equals("registerEntity")) {
                added.addAll(patch(method, converter, EntityCapabilityRegistry.ENTITY));
            }
        }
        added.stream().map(MethodDecl::getUsedImports)
                .flatMap(Collection::stream)
                .forEach(scriptFile.declaration::addClass);
        classDecl.methods.removeIf(methodDecl -> REMOVED.contains(methodDecl.name));
        classDecl.methods.addAll(added);
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
