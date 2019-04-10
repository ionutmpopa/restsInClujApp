package com.boioio.restsincluj.domain;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

public class Restaurant extends AbstractModel implements Comparable<Restaurant> {

    @NotEmpty(message = "{name.notempty}")
    private String name;

    private String address;

    private int capacity;

    private String description;

    private RestType restType;


    public String getDescription() {
        return description;
    }

    public void setDescription(String decription) {
        this.description = decription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public RestType getRestType() {
        return restType;
    }

    public void setRestType(RestType restType) {
        this.restType = restType;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", capacity=" + capacity +
                ", decription='" + description + '\'' +
                ", restType=" + restType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Integer.compare(that.capacity, capacity) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(description, that.description) &&
                restType == that.restType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, address, capacity, description, restType);
    }

    @Override
    public int compareTo(Restaurant o) {
        return this.getRestType().toString().compareTo(o.getRestType().toString());
    }
}
