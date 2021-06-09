package ru.svetlov.model.configuration;

public class ShipConfiguration {
    public final ShipType TYPE;
    public final int HP;
    public final int DAMAGE;
    public final float VELOCITY;
    public final float SIZE;
    public final String TEXTURE_NAME;

    public ShipConfiguration(ShipType TYPE, int HP, int DAMAGE, float VELOCITY, float SIZE, String TEXTURE_NAME) {
        this.TYPE = TYPE;
        this.HP = HP;
        this.DAMAGE = DAMAGE;
        this.VELOCITY = VELOCITY;
        this.SIZE = SIZE;
        this.TEXTURE_NAME = TEXTURE_NAME;
    }
}
