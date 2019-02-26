package com.salesianostriana.jldominguez.moveright.model;

import java.util.List;

public class User {

    private String email;
    private String password;
    private String name;
    private String role;
    private String picture;
    private List<Property> properties;

    public User() {
    }

    public User(String email, String password, String name, String role, String picture, List<Property> properties) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.picture = picture;
        this.properties = properties;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", picture='" + picture + '\'' +
                ", properties=" + properties +
                '}';
    }
}
