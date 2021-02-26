package com.androiddev;

import java.time.LocalDateTime;

public class Logs {

    static int logNum;
    private int logID;
    private LocalDateTime timeStamp;
    private double latitude;
    private double longitude;
    private String addressLine;

    public Logs(){

    }

    public Logs(LocalDateTime timeStamp, double latitude, double longitude, String addressLine){

        this.setLogID(logNum);
        logNum = logNum + 1;
        this.setTimeStamp(timeStamp);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setAddressLine(addressLine);
    }

    public String toString(){
        return "ID=" + getLogID() + "DateTime= " + getTimeStamp() + "Lat=" + getLatitude() + " Long=" + getLongitude();
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
