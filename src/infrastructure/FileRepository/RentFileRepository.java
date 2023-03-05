package infrastructure.FileRepository;

import infrastructure.IRepository;
import domain.*;
import infrastructure.MemoryRepository.RentRepository;

import java.io.*;
import java.util.ArrayList;

public class RentFileRepository extends FileRepository<Integer, RentCar> {


    @Override
    protected IRepository<Integer, RentCar> readFromFile() throws FileNotFoundException {
        RentRepository rentRepository = new RentRepository();

        try {
            in = new BufferedReader(new FileReader(Path));

            String Line = in.readLine();
            while (Line != null) {
                RentCar rentCar = null;
                ArrayList<String> elements = tokenize(Line);

                if(elements.get(0).equals("RentCar")) {
                    rentCar = new RentCar(Integer.parseInt(elements.get(1)), Integer.parseInt(elements.get(2)), elements.get(3), elements.get(4), elements.get(5), elements.get(6));
                    rentRepository.add(rentCar.getID(), rentCar);
                }

                Line = in.readLine();
            }

            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return rentRepository;
    }

    @Override
    protected void writeToFile(IRepository<Integer, RentCar> objects) throws IOException {
        try {
            out = new FileWriter(Path);

            for (RentCar car : objects.getAll()) {
                out.append(printData(car));
            }

            out.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public RentFileRepository(String path) {
        super(path);
    }
}

