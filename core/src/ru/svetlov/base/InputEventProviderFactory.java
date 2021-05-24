package ru.svetlov.base;

import java.util.Dictionary;
import java.util.Hashtable;

import ru.svetlov.screen.MenuScreen;
import ru.svetlov.user.controller.BasicUserInputProcessor;
import ru.svetlov.user.controller.events.UserInputEventProvider;

public class InputEventProviderFactory {
    private final static Object lock = new Object();
    private static InputEventProviderFactory factory = null;
    protected final Dictionary<Class<? extends BaseScreen>, UserInputEventProvider> storage = new Hashtable<>();

    public static InputEventProviderFactory getFactory() {
        if (factory != null) return factory;
        synchronized (lock) {
            factory = new InputEventProviderFactory();
        }
        return factory;
    }

    private InputEventProviderFactory() {}

    public UserInputEventProvider getClassUserEventProvider(Object type) {
        if (!(type instanceof BaseScreen)) return null;
        UserInputEventProvider result = storage.get(type);
        if (result == null){
            if (type instanceof MenuScreen)
                result = new BasicUserInputProcessor();
            storage.put(MenuScreen.class, result);
        }
        return result;
    }
}
