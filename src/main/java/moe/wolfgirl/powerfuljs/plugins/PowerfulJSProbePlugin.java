package moe.wolfgirl.powerfuljs.plugins;

import moe.wolfgirl.powerfuljs.plugins.docs.CapabilityJSDoc;
import moe.wolfgirl.powerfuljs.plugins.docs.RegisterCapabilityEvent;
import moe.wolfgirl.probejs.plugin.ProbeJSPlugin;
import moe.wolfgirl.probejs.typescript.Documents;
import moe.wolfgirl.probejs.typescript.base.DocumentRegistrar;

import java.util.*;
import java.util.function.Supplier;

public class PowerfulJSProbePlugin extends ProbeJSPlugin {
    private final List<Supplier<ProbeJSPlugin>> PLUGINS = List.of(
            RegisterCapabilityEvent::new,
            CapabilityJSDoc::new
    );

    @Override
    public void modifyClasses(Documents.ClassAccessor classDocuments) {
        for (Supplier<ProbeJSPlugin> plugin : PLUGINS) {
            plugin.get().modifyClasses(classDocuments);
        }
    }

    @Override
    public void addSpecialDocuments(DocumentRegistrar registrar) {
        for (Supplier<ProbeJSPlugin> plugin : PLUGINS) {
            plugin.get().addSpecialDocuments(registrar);
        }
    }

    @Override
    public Set<Class<?>> provideClassForDiscovery() {
        Set<Class<?>> classes = new HashSet<>();
        for (Supplier<ProbeJSPlugin> plugin : PLUGINS) {
            classes.addAll(plugin.get().provideClassForDiscovery());
        }
        return classes;
    }
}
