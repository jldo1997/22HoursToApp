package com.salesianostriana.jldominguez.righttomoveapp.model;

public class Property {

    private String id;
    private String title;
    private String description;
    private Double price;
    private int rooms;
    private int size;
    private String address;
    private String zipcode;
    private String city;
    private String loc;

    public Property() {
    }

    public Property(String id, String title, String description, Double price, int rooms, int size, String address, String zipcode, String city, String loc) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.rooms = rooms;
        this.size = size;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.loc = loc;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public int getRooms() {
        return rooms;
    }

    public int getSize() {
        return size;
    }

    public String getAddress() {
        return address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }

    public String getLoc() {
        return loc;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", rooms=" + rooms +
                ", size=" + size +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                ", loc='" + loc + '\'' +
                '}';
    }
}
