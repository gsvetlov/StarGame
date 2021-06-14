package ru.svetlov.model.configuration;

public interface ShipConfigurationProvider {
    ShipConfiguration getConfiguration(ShipType type);
}
