package main;

import gui.carRentsGUI;
import infrastructure.FileRepository.CarFileRepository;
import infrastructure.FileRepository.RentFileRepository;
import infrastructure.JDBCRepo.jdbcCars;
import infrastructure.JDBCRepo.jdbcRents;
import infrastructure.MemoryRepository.CarRepository;
import infrastructure.MemoryRepository.RentRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.ManageRent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main extends Application {

    public static ManageRent readPropertiesService() {
        ManageRent rents = null;

        Properties properties = new Properties();
        try (InputStream is = new FileInputStream("settings.properties")) // try-with-resources
        {
            properties.load(is);

            String repoType = properties.getProperty("Repository");
            String carsFilePath = properties.getProperty("Cars");
            String rentsFilePath = properties.getProperty("Rents");
            String location = properties.getProperty("Location");

            //if (repoType.equals("binary")) {
            //rents = new ManageRent(new BinaryRepository<Integer, RentCar>(carsFilePath), new BinaryRepository<Integer, Car>(rentsFilePath));
            //}

            if (repoType.equals("text")) {
                rents = new ManageRent(new CarFileRepository(carsFilePath), new RentFileRepository(rentsFilePath));
            }

            if (repoType.equals("memory")) {
                rents = new ManageRent(new CarRepository(), new RentRepository());
            }

            if (repoType.equals("database")) {
                rents = new ManageRent(new jdbcCars(location), new jdbcRents(location));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return rents;
    }

    @Override
    public void start(Stage stage) throws Exception {
        ManageRent manageRents = readPropertiesService();
        carRentsGUI gui = new carRentsGUI(manageRents);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/carRentsGUI.fxml"));
        loader.setController(gui);
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root, 600, 591);
        scene.setOnKeyPressed(gui.eventHandler);
        stage.setTitle("Car Rental");
        stage.setScene(scene);
        stage.show();
    }
}
