package infrastructure.FileRepository;

import domain.*;
import infrastructure.IRepository;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public abstract class FileRepository<ID, T> implements IRepository<ID, T>, Serializable {
    protected BufferedReader in = null;
    protected FileWriter out = null;
    protected String Path;

    protected abstract IRepository<ID,T> readFromFile() throws IOException;
    protected abstract void writeToFile(IRepository<ID,T> objects) throws IOException;

    public FileRepository(String path) {
        Path = path;
    }

    protected ArrayList<String> tokenize(String line) {
        ArrayList<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(line, " | ");
        while (tokenizer.hasMoreElements())
            tokens.add(tokenizer.nextToken());

        return tokens;
    }

    protected String printData(T elem) {
        String element = "";
        if (elem instanceof Car) {
            element += "Car" + " | "
                    + ((Car) elem).getID().toString() + " | "
                    + ((Car) elem).getModel() + " | "
                    + ((Car) elem).getManufacturer() + " | "
                    + ((Car) elem).getColor() + " | "
                    + ((Car) elem).getYear() + " | "
                    + ((Car) elem).getPrice() + " | "
                    + ((Car) elem).isAvailable() + "\n";

        } else if (elem instanceof RentCar) {
            element += "RentCar" + " | "
                    + ((RentCar) elem).getID().toString() + " | "
                    + ((RentCar) elem).getCarId() + " | "
                    + ((RentCar) elem).getClientName() + " | "
                    + ((RentCar) elem).getStartDate() + " | "
                    + ((RentCar) elem).getEndDate() + " | "
                    + ((RentCar) elem).getPrice() + "\n";

        }

        return element;
    }

    @Override
    public void add(ID id, T elem) throws IOException, RuntimeException, SQLException {
        IRepository<ID,T> objectsRepository = readFromFile();

        objectsRepository.add(id, elem);

        writeToFile(objectsRepository);
    }

    @Override
    public void delete(ID id) throws IOException, SQLException {
        IRepository<ID, T> objectsRepository = readFromFile();

        objectsRepository.delete(id);

        writeToFile(objectsRepository);
    }

    @Override
    public void modify(ID id, T elem) throws IOException, SQLException {
        IRepository<ID, T> objectsRepository = readFromFile();

        objectsRepository.modify(id, elem);

        writeToFile(objectsRepository);
    }

    @Override
    public T findById(ID id) throws IOException {
        IRepository<ID, T> objectsRepository = readFromFile();

        return objectsRepository.findById(id);
    }

    @Override
    public Iterable<T> getAll() throws IOException {
        IRepository<ID, T> objectsRepository = readFromFile();

        return objectsRepository.getAll();
    }
}
