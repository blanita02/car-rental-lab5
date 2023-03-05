package infrastructure.MemoryRepository;

import domain.*;
import infrastructure.MemoryRepository.MemoryRepository;

import java.util.ArrayList;
import java.util.Comparator;

public class CarRepository extends MemoryRepository<Integer, Car> {
    public ArrayList<Car> sortByPrice(){
        ArrayList<Car> l = new ArrayList<Car>(repository.values());
        l.sort(Comparator.comparingInt(Car::getPrice));
        return l;

    }

    public Integer getSize() {
        return repository.size();
    }
}

