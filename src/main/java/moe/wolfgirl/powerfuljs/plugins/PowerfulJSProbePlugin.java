package moe.wolfgirl.powerfuljs.plugins;

import moe.wolfgirl.powerfuljs.plugins.docs.CapabilityJSDoc;
import moe.wolfgirl.powerfuljs.plugins.docs.RegisterCapabilityEvent;
import moe.wolfgirl.probejs.lang.java.clazz.ClassPath;
import moe.wolfgirl.probejs.lang.typescript.ScriptDump;
import moe.wolfgirl.probejs.lang.typescript.TypeScriptFile;
import moe.wolfgirl.probejs.plugin.ProbeJSPlugin;

import java.util.*;
import java.util.function.Supplier;

public class PowerfulJSProbePlugin extends ProbeJSPlugin {
    private final List<Supplier<ProbeJSPlugin>> PLUGINS = List.of(
            RegisterCapabilityEvent::new,
            CapabilityJSDoc::new
    );

    @Override
    public void modifyClasses(ScriptDump scriptDump, Map<ClassPath, TypeScriptFile> globalClasses) {
        for (Supplier<ProbeJSPlugin> plugin : PLUGINS) {
            plugin.get().modifyClasses(scriptDump, globalClasses);
        }
    }

    @Override
    public Set<Class<?>> provideJavaClass(ScriptDump scriptDump) {
        Set<Class<?>> classes = new HashSet<>();
        for (Supplier<ProbeJSPlugin> plugin : PLUGINS) {
            classes.addAll(plugin.get().provideJavaClass(scriptDump));
        }
        return classes;
    }
}
