package moe.wolfgirl.powerfuljs.utils;

import com.google.common.base.Suppliers;
import net.neoforged.fml.ModList;

import java.util.function.Supplier;

public class ModUtils {

    public static void whenLoaded(String modId, Runnable runnable) {
        if (ModList.get().isLoaded(modId)) runnable.run();
    }
}
