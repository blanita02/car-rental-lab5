package infrastructure.FileRepository;

import infrastructure.IRepository;
import domain.*;
import infrastructure.MemoryRepository.CarRepository;

import java.io.*;
import java.util.ArrayList;

public class CarFileRepository extends FileRepository<Integer, Car> {


    @Override
    protected IRepository<Integer, Car> readFromFile() throws FileNotFoundException {
        CarRepository carRepository = new CarRepository();

        try {
            in = new BufferedReader(new FileReader(Path));

            String Line = in.readLine();
            while (Line != null) {
                Car car = null;
                ArrayList<String> elements = tokenize(Line);

                if(elements.get(0).equals("Car")) {
                    car = new Car(Integer.parseInt(elements.get(1)), elements.get(2), elements.get(3), elements.get(4), Integer.parseInt(elements.get(5)), Integer.parseInt(elements.get(6)), Boolean.parseBoolean(elements.get(7)));
                    carRepository.add(car.getID(), car);
                }

                Line = in.readLine();
            }

            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return carRepository;
    }

    @Override
    protected void writeToFile(IRepository<Integer, Car> objects) throws IOException {
        try {
            out = new FileWriter(Path);

            for (Car car : objects.getAll()) {
                out.append(printData(car));
            }

            out.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public CarFileRepository(String path) {
        super(path);
    }
}

