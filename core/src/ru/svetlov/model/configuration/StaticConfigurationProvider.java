package ru.svetlov.model.configuration;

import java.util.HashMap;
import java.util.Map;

public class StaticConfigurationProvider implements ShipConfigurationProvider {
    private final Map<ShipType, ShipConfiguration> config;

    @Override
    public ShipConfiguration getConfiguration(ShipType type) {
        return config.get(type);
    }

    public StaticConfigurationProvider() {
        config = new HashMap<>();
        config.put(ShipType.SmallAlien,
                new ShipConfiguration(
                        ShipType.SmallAlien,
                        10,
                        1,
                        0.1f,
                        0.1f,
                        "enemy0"));
        config.put(ShipType.MiddleAlien,
                new ShipConfiguration(
                        ShipType.MiddleAlien,
                        20,
                        5,
                        0.07f,
                        0.12f,
                        "enemy1"));
        config.put(ShipType.BigAlien,
                new ShipConfiguration(
                        ShipType.BigAlien,
                        30,
                        10,
                        0.05f,
                        0.15f,
                        "enemy2"));
    }


}
