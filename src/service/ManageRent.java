package service;
import domain.*;
import infrastructure.IRepository;
import infrastructure.MemoryRepository.*;
import infrastructure.FunctionsInter.CarsOlderThen;

import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.*;

public class ManageRent {
    IRepository cars;
    IRepository rents;

    static Integer carId;
    static Integer rentId;

    public ManageRent(IRepository cars, IRepository rents) throws IOException {
        this.cars = cars;
        this.rents = rents;
        int countAppID = 0, countPID = 0;
        for (Object o : cars.getAll()) {
            countAppID++;
            Entity entity = (Entity) o;
        }
        for (Object o : rents.getAll()) {
            countPID++;
            Entity entity = (Entity) o;
        }
        carId = countAppID;
        rentId = countPID;
    }

    public CarRepository getAvailableCars() throws IOException {
        CarRepository availableCars = new CarRepository();
        for (Object o : cars.getAll()) {
            Car car = (Car) o;
            if (car.isAvailable()) {
                availableCars.add(car.getID(), car);
            }
        }
        return availableCars;
    }

    public void setRents(RentRepository rents) {
        this.rents = rents;
    }

    public Car createCar( String model, String manufacturer, String color, int year, int price, boolean available) throws IOException, SQLException {
        carId++;
        Car car = new Car(carId, model, manufacturer, color, year, price, available);
        cars.add(car.getID(), car);
        return car;
    }

    public void updateCar(Integer id, String model, String manufacturer, String color, int year, int price, boolean available) throws IOException, SQLException {
        Car car = new Car(id, model, manufacturer, color, year, price, available);
        cars.modify(id, car);
    }

    public void deleteCar(Integer id) throws IOException, SQLException {
        cars.delete(id);
    }

    public IRepository getCars() throws IOException {
        CarRepository allCars = new CarRepository();
        for (Object o : cars.getAll()) {
            Car car = (Car) o;
            allCars.add(car.getID(), car);
        }
        return allCars;
    }

    public Car getCar(Integer id) throws IOException {
        return (Car) cars.findById(id);
    }

    public RentCar createRent(Integer carId, String clientName, String startDate, String endDate, String price) throws IOException, SQLException {
        rentId++;
        RentCar rent = new RentCar(rentId, carId, clientName, startDate, endDate, price);
        Car car = (Car) cars.findById(carId);
        car.setAvailable(false);
        cars.modify(carId, car);
        rents.add(rent.getID(), rent);
        return rent;
    }

    public IRepository getRents() throws IOException {
        RentRepository allRents = new RentRepository();
        for (Object o : rents.getAll()) {
            RentCar rent = (RentCar) o;
            allRents.add(rent.getID(), rent);
        }
        return allRents;
    }

    public CarRepository getAllCars() throws IOException {
        CarRepository allCars = new CarRepository();
        Iterable<Car> car = cars.getAll();
        for (Car c : car) {
            allCars.add(c.getID(), c);
        }
        return allCars;
    }

    public void deleteRent(Integer id) throws IOException, SQLException {
        RentCar rent = (RentCar) rents.findById(id);
        Car car = (Car) cars.findById(rent.getCarId());
        car.setAvailable(true);
        cars.modify(rent.getCarId(), car);
        rents.delete(id);
    }

    public CarRepository printCarsByPrice() throws IOException {
        CarRepository sortedCars = new CarRepository();
        Iterable<Car> car = cars.getAll();
        for (Car c : car) {
            sortedCars.add(c.getID(), c);
        }
        sortedCars.sortByPrice();
        return sortedCars;

    }

    public Car mostExpensiveCar() throws IOException {
        CarRepository sortedCars = new CarRepository();
        Iterable<Car> car = cars.getAll();
        for (Car c : car) {
            sortedCars.add(c.getID(), c);
        }
        sortedCars.sortByPrice();
        return sortedCars.findById(sortedCars.getSize() - 1);
    }

    public ArrayList<Car> carsOlderThen(Integer year) throws IOException {
        Iterable<Car> carsF = cars.getAll();
        ArrayList<Car> cars = new ArrayList<>();
        carsF.iterator().forEachRemaining(car -> {
            if (new CarsOlderThen(year).test(car)) {
                cars.add(car);
            }
        });
        return cars;

    }

    public ArrayList<Car> carsSortedByPrice(int price) throws IOException {
        ArrayList<Car> carsT = new ArrayList<>();
        Iterable<Car> carsF = cars.getAll();

        carsF.iterator().forEachRemaining(carsT::add);

        carsT = carsT.stream().filter(car -> car.getPrice() > price).sorted(Comparator.comparing(Car::getPrice)).collect(Collectors.toCollection(ArrayList::new));

        return carsT;

    }



}
