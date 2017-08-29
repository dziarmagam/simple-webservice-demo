package demo.entity;

import java.util.UUID;

public class Car {
    public final UUID uuid;
    public final String vendor;
    public final String color;
    public final CarType type;

    public Car(UUID uuid, String vendor, String color, CarType type) {
        this.uuid = uuid;
        this.vendor = vendor;
        this.color = color;
        this.type = type;
    }

    public enum CarType{
        SUV,
        MPV,
        SMALL
    }
}
