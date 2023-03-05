package gui;

import domain.Car;
import domain.RentCar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import service.ManageRent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;


public class carRentsGUI {

    @FXML
    private ListView carsView;
    @FXML
    private ListView rentsView;
    @FXML
    private ListView customView;
    @FXML
    private TextField model;
    @FXML
    private TextField manuf;
    @FXML
    private TextField color;
    @FXML
    private TextField year;
    @FXML
    private TextField price;
    @FXML
    private TextField carId;
    @FXML
    private TextField clName;
    @FXML
    private TextField stDate;
    @FXML
    private TextField enDate;
    @FXML
    private TextField priceR;
    @FXML
    private CheckBox OlderThen;
    @FXML
    private CheckBox SortedPrice;





    private ObservableList<Car> cars = FXCollections.observableArrayList();
    private ObservableList<RentCar> rents = FXCollections.observableArrayList();

    private ManageRent manageRents;

    public carRentsGUI(ManageRent manageRents) {
        this.manageRents = manageRents;
    }

    private void setCars(Iterable<Car> newCars) throws IOException {
        cars.clear();
        Iterable<Car> carrs = newCars;

        for (Car c: carrs) {
            cars.add(c);
        }

        carsView.setItems(cars);
    }

    private void setRents(Iterable<RentCar> newRents) throws IOException {
        rents.clear();
        Iterable<RentCar> rentss = newRents;

        for (RentCar r: rentss) {
            rents.add(r);
        }

        rentsView.setItems(rents);
    }

    private void setCustom(Iterable<Car> newCars) throws IOException {
        cars.clear();
        Iterable<Car> carrs = newCars;

        for (Car c: carrs) {
            cars.add(c);
        }

        customView.setItems(cars);
    }

    public void initialize() throws IOException{
        setCars((Iterable<Car>) manageRents.getCars().getAll());
        setRents((Iterable<RentCar>) manageRents.getRents().getAll());
    }

    public void onKeyAdd(ActionEvent event) throws IOException, SQLException {
        if(Objects.equals(carId.getText(), "")){
            try {
                manageRents.createCar(model.getText(), manuf.getText(), color.getText(), Integer.parseInt(year.getText()), Integer.parseInt(price.getText()), true);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                manageRents.createRent(Integer.parseInt(carId.getText()), clName.getText(), stDate.getText(), enDate.getText(), priceR.getText());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        setCars((Iterable<Car>) manageRents.getCars().getAll());
        setRents((Iterable<RentCar>) manageRents.getRents().getAll());


    }

    public void onKeyDelete(ActionEvent event) throws IOException, SQLException {
        try {
            int ind = carsView.getSelectionModel().getSelectedIndex();
            if (ind != -1) {
                Car car = (Car) carsView.getItems().get(ind);
                manageRents.deleteCar(car.getID());
            }
            int ind1 = rentsView.getSelectionModel().getSelectedIndex();
            if (ind1 != -1) {
                RentCar rent = (RentCar) rentsView.getItems().get(ind1);
                manageRents.deleteRent(rent.getID());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }


            setCars((Iterable<Car>) manageRents.getCars().getAll());
            setRents((Iterable<RentCar>) manageRents.getRents().getAll());

    }

    public void onKeyUpdate(ActionEvent event) throws IOException, SQLException {
        try {
            int ind = carsView.getSelectionModel().getSelectedIndex();
            if (ind != -1) {
                Car car = (Car) carsView.getItems().get(ind);
                manageRents.updateCar(car.getID(), model.getText(), manuf.getText(), color.getText(), Integer.parseInt(year.getText()), Integer.parseInt(price.getText()), true);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        setCars((Iterable<Car>) manageRents.getCars().getAll());
        setRents((Iterable<RentCar>) manageRents.getRents().getAll());
    }

    public void onKeyFilter(ActionEvent event) throws IOException {
        try {
            if(OlderThen.isSelected()){
                setCustom(manageRents.carsOlderThen(Integer.parseInt(year.getText())));
            }
            else{
                setCustom(manageRents.getCars().getAll());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onKeyFilter2(ActionEvent event) throws  IOException {
        try {
            if(SortedPrice.isSelected()){
                setCustom((Iterable<Car>) manageRents.carsSortedByPrice(Integer.parseInt(price.getText())));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    public EventHandler<? super KeyEvent> eventHandler;
}
