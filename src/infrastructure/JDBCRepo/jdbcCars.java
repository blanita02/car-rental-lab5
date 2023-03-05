package infrastructure.JDBCRepo;

import domain.*;
import infrastructure.IRepository;
import infrastructure.MemoryRepository.MemoryRepository;
import infrastructure.MemoryRepository.CarRepository;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbcCars extends jdbcRepo<Integer, Car> {

    public jdbcCars(String path) {
        super(path);
        createSchema();
    }

    @Override
    protected void createSchema() {
        openConnection();
        try {
            final Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS cars(cID int primary key, Model varchar(100), Manufacturer String, Color String, Year int, Price int, Available bool);");
            stmt.close();
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
        closeConnection();
    }

    @Override
    protected IRepository readData() {
        openConnection();
        IRepository<Integer, Car> carsRepo = new MemoryRepository<>();
        try {
            PreparedStatement statement1 = conn.prepareStatement(
                    "SELECT * FROM cars"
            );

            ResultSet rs = statement1.executeQuery();

            while (rs.next()) {
                Car car = new Car(rs.getInt("cID"),rs.getString("Model"),rs.getString("Manufacturer"),rs.getString("Color"),rs.getInt("Year"),rs.getInt("Price"),rs.getBoolean("Available"));
                carsRepo.add(car.getID(),car);
            }

            statement1.close();

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
        return carsRepo;
    }


    @Override
    protected void deleteData(Integer id) {
        openConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(
                    String.format("DELETE FROM cars where cID=%b",id)
            );
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
    }

    @Override
    protected void addData(Integer id, Car elem) {
        openConnection();

        try {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO cars VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            statement.setInt(1, elem.getID());
            statement.setString(2, elem.getModel());
            statement.setString(3, elem.getManufacturer());
            statement.setString(4, elem.getColor());
            statement.setInt(5, elem.getYear());
            statement.setInt(6, elem.getPrice());
            statement.setBoolean(7, elem.isAvailable());
            statement.executeUpdate();
            statement.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection();
    }

    @Override
    protected void modifyData(Integer id, Car elem) {
        try {
            openConnection();
            conn.setAutoCommit(false);
            PreparedStatement modifyPatient = conn.prepareStatement(
                    String.format("UPDATE cars set Model=?, Manufacturer=?, Color=?, Year=?, Price=?, Available=? where cID=%b",id)
            );
            modifyPatient.setString(1, elem.getModel());
            modifyPatient.setString(2, elem.getManufacturer());
            modifyPatient.setString(3, elem.getColor());
            modifyPatient.setInt(4, elem.getYear());
            modifyPatient.setInt(5, elem.getPrice());
            modifyPatient.setBoolean(6, elem.isAvailable());
            modifyPatient.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);
            modifyPatient.close();

            closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
