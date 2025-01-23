package moe.wolfgirl.powerfuljs.plugins;

import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;

public interface PowerfulJS {

    @SuppressWarnings("unchecked")
    static <T> T cast(Context context, Class<T> typeClass, Object data) {
        return (T) context.jsToJava(data, TypeInfo.of(typeClass));
    }
}
