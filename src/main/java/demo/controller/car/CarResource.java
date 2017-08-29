package demo.controller.car;


import demo.entity.Car;
import demo.exception.CarNotFoundException;
import demo.exception.InvalidRequestInputException;
import demo.service.base.CarService;
import demo.util.FieldUtils;
import demo.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("car")
class CarResource {

    @Autowired
    CarService carService;

    @RequestMapping(method = RequestMethod.PUT)
    public UUID addCar(@RequestBody CarRepresentation carRepresentation){
        return carService.addCar(carRepresentation.getVendor(),
                FieldUtils.getValueOrNull(carRepresentation.getType(), Car.CarType::valueOf),
                carRepresentation.getColor()).uuid;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateCar(@RequestBody CarRepresentation carRepresentation){
        FieldUtils.checkForNullField(carRepresentation.getUuid(), "uuid");
        carService.updateCar(carRepresentation.toCar());

        return ResponseEntity.ok()
                .build();
    }

    @RequestMapping(path = "{uuid}", method = RequestMethod.DELETE)
    public ResponseEntity removeCar(@PathVariable String uuid){
        carService.removeCar(UuidUtils.checkAndConvert(uuid));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "{uuid}", method = RequestMethod.GET)
    public CarRepresentation findCar(@PathVariable String uuid){
        Car car = carService.findByUUID(UuidUtils.checkAndConvert(uuid));
        return CarRepresentation.covertCarToRepresentation(car);
    }

    @ExceptionHandler(InvalidRequestInputException.class)
    public ResponseEntity handleFieldIsRequiredException(InvalidRequestInputException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity handleCarNotFound(CarNotFoundException exception){
        return ResponseEntity.noContent().build();
    }

}
