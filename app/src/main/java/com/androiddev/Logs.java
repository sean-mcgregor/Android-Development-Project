package com.androiddev;

public class Logs {

    static int logNum;
    private int logID;
    private double latitude;
    private double longitude;

    public Logs(double latitude, double longitude){

        this.setLogID(logNum);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    public String toString(){
        return "ID=" + getLogID() + "Lat=" + getLatitude() + " Long=" + getLongitude();
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
