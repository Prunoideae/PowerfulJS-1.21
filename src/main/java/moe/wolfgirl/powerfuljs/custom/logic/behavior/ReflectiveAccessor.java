package moe.wolfgirl.powerfuljs.custom.logic.behavior;

import dev.latvian.mods.rhino.util.HideFromJS;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@HideFromJS
public class ReflectiveAccessor {
    private final Field field;
    private final Method getter;
    private final Method setter;

    public ReflectiveAccessor(Field field, Method getter, Method setter) {
        this.field = field;
        this.getter = getter;
        this.setter = setter;
    }

    public Object get(Object object) throws InvocationTargetException, IllegalAccessException {
        if (field != null) return field.get(object);
        if (getter != null) return getter.invoke(object);
        return null;
    }

    public void set(Object object, Object value) throws IllegalAccessException, InvocationTargetException {
        if (field != null) field.set(object, value);
        else if (setter != null) setter.invoke(object, value);
    }

    public boolean isReadable() {
        return field != null || getter != null;
    }

    public boolean isWritable() {
        return field != null || setter != null;
    }

    public static ReflectiveAccessor of(Class<?> clazz, String accessor) throws NoSuchFieldException, NoSuchMethodException {
        if (accessor.contains("/")) {
            String[] getSet = accessor.split("/");
            Method getter = getSet[0].equals("-") ? null : clazz.getDeclaredMethod(getSet[0]);
            Method setter = getSet[1].equals("-") ? null : clazz.getDeclaredMethod(getSet[1]);
            if (getter != null) getter.setAccessible(true);
            if (setter != null) setter.setAccessible(true);
            return new ReflectiveAccessor(null, getter, setter);
        } else {
            Field field = clazz.getDeclaredField(accessor);
            field.setAccessible(true);
            return new ReflectiveAccessor(field, null, null);
        }
    }
}
