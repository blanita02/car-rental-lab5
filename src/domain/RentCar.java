package domain;

import java.io.Serializable;

public class RentCar implements Entity<Integer>, Serializable {
    private Integer rentId;
    private Integer carId;
    private String clientName;
    private String startDate;
    private String endDate;
    private String price;

    public RentCar(Integer id, Integer carId, String clientName, String startDate, String endDate, String price) {
        this.rentId = id;
        this.carId = carId;
        this.clientName = clientName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public Integer getID() {
        return rentId;
    }

    public void setID(Integer id) {
        this.rentId = id;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientId(String clientName) {
        this.clientName = clientName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "RentCar{" +
                "rentId=" + rentId +
                ", carId=" + carId +
                ", clientName='" + clientName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", price='" + price + '\'' +
                '}' + "\n";
    }
}
