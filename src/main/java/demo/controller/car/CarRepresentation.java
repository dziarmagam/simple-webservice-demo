package demo.controller.car;

import com.fasterxml.jackson.annotation.JsonRootName;
import demo.entity.Car;
import demo.exception.InvalidUuidException;
import demo.util.FieldUtils;
import demo.util.UuidUtils;

import java.util.Objects;

/**
 * Representation of the {@link Car} entity
 */
@JsonRootName("carRepresentation")
public class CarRepresentation {

    private String uuid;
    private String vendor;
    private String color;
    private String type;

    public CarRepresentation(String uuid, String vendor, String color, String type) {
        this.uuid = uuid;
        this.vendor = vendor;
        this.color = color;
        this.type = type;
    }

    public CarRepresentation() {
    }

    static CarRepresentation covertCarToRepresentation(Car car){
        Objects.requireNonNull(car);
        return new CarRepresentation(car.uuid.toString(), car.vendor, car.color, FieldUtils.getValueOrNull(car.type, Enum::name));
    }

    static Car convertRepresentationToCar(CarRepresentation carRepresentation) throws InvalidUuidException {
        return new Car(UuidUtils.checkAndConvert(carRepresentation.getUuid()), carRepresentation.getVendor(),
                carRepresentation.getColor(), Car.CarType.valueOf(carRepresentation.getType()));
    }

    public String getUuid() {
        return uuid;
    }

    Car toCar(){
        return convertRepresentationToCar(this);
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarRepresentation that = (CarRepresentation) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(vendor, that.vendor) &&
                Objects.equals(color, that.color) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, vendor, color, type);
    }
}
