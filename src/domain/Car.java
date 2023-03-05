package domain;

import java.io.Serializable;

public class Car implements Entity<Integer>, Serializable {
    private Integer carId;
    private String model;
    private String manufacturer;
    private String color;
    private int year;
    private int price;
    private boolean available;
    public Car(Integer id, String model, String manufacturer, String color, int year, int price, boolean available) {
        this.carId = id;
        this.model = model;
        this.manufacturer = manufacturer;
        this.color = color;
        this.year = year;
        if(year < 0)
            throw new IllegalArgumentException("Year must be positive");
        this.price = price;
        if(price < 0)
            throw new IllegalArgumentException("Price cannot be negative");
        this.available = available;
    }
    public Integer getID() {
        return carId;
    }
    public void setID(Integer id) {
        this.carId = id;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
    @Override
    public String toString() {
        return "Car{" +
                "id=" + carId +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", color='" + color + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", available=" + available +
                '}' + "\n";
    }
}