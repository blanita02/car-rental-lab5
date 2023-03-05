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

public class jdbcRents extends jdbcRepo<Integer, RentCar> {

    public jdbcRents(String path) {
        super(path);
        createSchema();
    }

    @Override
    protected void createSchema() {
        openConnection();
        try {
            final Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS rents(rID int primary key, carID int, clientName String, startDate String, endDate String, Price String);");

            stmt.close();
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
        closeConnection();
    }

    @Override
    protected IRepository readData() {
        openConnection();
        IRepository<Integer, RentCar> rentsRepo = new MemoryRepository<>();
        try {
            PreparedStatement statement1 = conn.prepareStatement(
                    "SELECT * FROM rents"
            );

            ResultSet rs = statement1.executeQuery();

            while (rs.next()) {
                RentCar rentCar = new RentCar(rs.getInt("rID"),rs.getInt("carID"),rs.getString("clientName"),rs.getString("startDate"),rs.getString("endDate"),rs.getString("Price"));
                rentsRepo.add(rentCar.getID(),rentCar);
            }

            statement1.close();

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
        return rentsRepo;
    }


    @Override
    protected void deleteData(Integer id) {
        openConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(
                    String.format("DELETE FROM rents where rID=%b",id)
            );
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnection();
    }

    @Override
    protected void addData(Integer id, RentCar elem) {
        openConnection();

        try {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO rents VALUES (?, ?, ?, ?, ?, ?)"
            );
            statement.setInt(1, elem.getID());
            statement.setInt(2, elem.getCarId());
            statement.setString(3, elem.getClientName());
            statement.setString(4, elem.getStartDate());
            statement.setString(5, elem.getEndDate());
            statement.setString(6, elem.getPrice());
            statement.executeUpdate();
            statement.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection();
    }

    @Override
    protected void modifyData(Integer id, RentCar elem) {
        try {
            openConnection();
            conn.setAutoCommit(false);
            PreparedStatement modifyPatient = conn.prepareStatement(
                    String.format("UPDATE cars set carID=?, clientName=?, startDate=?, endDate=?, Price=?, where rID=%b",id)
            );
            modifyPatient.setInt(1, elem.getID());
            modifyPatient.setInt(2, elem.getCarId());
            modifyPatient.setString(3, elem.getClientName());
            modifyPatient.setString(4, elem.getStartDate());
            modifyPatient.setString(5, elem.getEndDate());
            modifyPatient.setString(6, elem.getPrice());

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
