package Test;
import domain.Car;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class  TestCar {
    @Test
    public void testCar() {
        Car car = new Car(0, "X5", "BMW", "Red", 2003, 10000, true);
        assertEquals("X5", car.getModel());
        assertEquals("BMW", car.getManufacturer());
        assertEquals("Red", car.getColor());
        assertEquals(2003, car.getYear());
        assertEquals(10000, car.getPrice());
        assertTrue(car.isAvailable());
    }

    @Test
    public void testCarSetters() {
        Car car = new Car(0, "X5", "BMW", "Red", 2003, 10000, true);
        car.setID(1);
        car.setModel("A6");
        car.setManufacturer("Audi");
        car.setColor("Blue");
        car.setYear(2004);
        car.setPrice(20000);
        car.setAvailable(false);
        assertEquals("A6", car.getModel());
        assertEquals("Audi", car.getManufacturer());
        assertEquals("Blue", car.getColor());
        assertEquals(2004, car.getYear());
        assertEquals(20000, car.getPrice());
        assertFalse(car.isAvailable());


}
    @Test
    public void testCarGetters() {
        Car car = new Car(0, "X5", "BMW", "Red", 2003, 10000, true);
        assertEquals("X5", car.getModel());
        assertEquals("BMW", car.getManufacturer());
        assertEquals("Red", car.getColor());
        assertEquals(2003, car.getYear());
        assertEquals(10000, car.getPrice());
        assertTrue(car.isAvailable());
    }
}
