package ru.svetlov.base;

import java.util.Dictionary;
import java.util.Hashtable;

import ru.svetlov.screen.BaseScreen;
import ru.svetlov.screen.MenuScreen;
import ru.svetlov.user.controller.BasicUserInputProcessor;
import ru.svetlov.user.controller.UserInputEventProvider;

public class InputEventProviderFactory {
    private final static Object lock = new Object();
    private static InputEventProviderFactory factory = null;
    private final FactoryStorage storage;

    public static InputEventProviderFactory getFactory() {
        if (factory != null) return factory;
        synchronized (lock) {
            factory = new InputEventProviderFactory();
        }
        return factory;
    }

    private InputEventProviderFactory() {
        storage = new FactoryStorage();
    }

    public UserInputEventProvider getClassUserEventProvider(Object type) {
        if (!(type instanceof BaseScreen)) return null;
        UserInputEventProvider result = storage.providers.get(type);
        if (result == null)
            if (type instanceof MenuScreen){
                result = new BasicUserInputProcessor();
                storage.providers.put(MenuScreen.class, result);
            }
        return result;
    }

    private static class FactoryStorage {
        protected final Dictionary<Class<? extends BaseScreen>, UserInputEventProvider> providers = new Hashtable<>();
    }

}
