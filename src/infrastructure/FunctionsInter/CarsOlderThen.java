package infrastructure.FunctionsInter;

import domain.Car;

import java.util.function.Predicate;

public class CarsOlderThen implements Predicate<Car> {
    private Integer year;

    public CarsOlderThen(Integer year) {
        this.year = year;
    }

    @Override
    public boolean test(Car car) {
        if (car.getYear() > year)
            return true;

        return false;
    }
}

