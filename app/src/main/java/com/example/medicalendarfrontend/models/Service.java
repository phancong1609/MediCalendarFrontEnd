package com.example.medicalendarfrontend.models;

import java.util.List;


public class Service {

    private Long id;

    private String name;

    private String serviceType;

    public Service() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String description;

    private int remind;
    private List<Doctor> doctorList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRemind() {
        return remind;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }

    public List<Doctor> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    public Service(Long id, String name, String serviceType, String description, int remind, List<Doctor> doctorList) {
        this.id = id;
        this.name = name;
        this.serviceType = serviceType;
        this.description = description;
        this.remind = remind;
        this.doctorList = doctorList;
    }

    public String getServiceType() {
        return serviceType;
    }

}
