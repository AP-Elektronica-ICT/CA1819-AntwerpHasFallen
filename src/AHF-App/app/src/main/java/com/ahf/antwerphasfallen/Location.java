package com.ahf.antwerphasfallen;

public class Location {
    private int id;
    private double lat;
    private double lon;
    private String name;

    public Location() {}

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public double getLat() { return lat;}

    public void setLat(double lat) {this.lat = lat;}

    public double getLon() { return lon;}

    public void setLon(double lat) {this.lon = lon;}

    public String getName() { return name;}

    public void setName(String name) {this.name = name;}

}