package moe.wolfgirl.powerfuljs.utils;

import net.neoforged.fml.ModList;
import org.spongepowered.asm.service.MixinService;

import java.io.IOException;

public class ModUtils {

    public static void whenLoaded(String modId, Runnable runnable) {
        if (ModList.get().isLoaded(modId)) runnable.run();
    }

    public static boolean isModPresent(String modId, String modClass) {
        if (ModList.get() != null) {
            return ModList.get().isLoaded(modId);
        } else {
            try {
                MixinService.getService().getBytecodeProvider().getClassNode(modClass);
                return true;
            } catch (ClassNotFoundException | IOException e) {
                return false;
            }
        }
    }
}
