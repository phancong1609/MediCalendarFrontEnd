package com.example.medicalendarfrontend.models;
import java.util.List;

public class Office {
    private Long id;

    private String logo;

    private String name;

    private String address;

    private String description;

    private List<Service> serviceList;

    public Office(Long id, String logo, String name, String address, String description, List<Service> serviceList) {
        this.id = id;
        this.logo = logo;
        this.name = name;
        this.address = address;
        this.description = description;
        this.serviceList = serviceList;
    }

    public Office() {

    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
