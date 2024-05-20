package com.example.medicalendarfrontend.models;

public class Doctor {
    private Long id;

    private String name;

    private String gender;

    private int bookingPrice;

    private String speciality;

    private String shiftDay;

    private String shiftDuration;

    public Doctor() {

    }

    public Doctor(Long id, String name, String gender, int bookingPrice, String speciality, String shiftDay, String shiftDuration) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.bookingPrice = bookingPrice;
        this.speciality = speciality;
        this.shiftDay = shiftDay;
        this.shiftDuration = shiftDuration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(int bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
