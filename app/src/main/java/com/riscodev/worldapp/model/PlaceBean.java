package com.riscodev.worldapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceBean {

    @Expose
    @SerializedName("images")
    private List<String> images;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("building")
    private List<BuildingEntity> building;
    @Expose
    @SerializedName("location")
    private List<LocationEntity> location;
    @Expose
    @SerializedName("short_description")
    private String short_description;
    @Expose
    @SerializedName("place")
    private String place;
    @SerializedName("id")
    private String id;

    public static class BuildingEntity {
        @Expose
        @SerializedName("area")
        private String area;
        @Expose
        @SerializedName("length")
        private String length;
        @Expose
        @SerializedName("height")
        private String height;

        public String getArea() {
            return area;
        }

        public String getLength() {
            return length;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class LocationEntity {
        @Expose
        @SerializedName("address")
        private String address;
        @Expose
        @SerializedName("longitude")
        private String longitude;
        @Expose
        @SerializedName("latitude")
        private String latitude;

        public String getAddress() {
            return address;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getLatitude() {
            return latitude;
        }
    }

    public List<String> getImages() {
        return images;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public List<BuildingEntity> getBuilding() {
        return building;
    }

    public List<LocationEntity> getLocation() {
        return location;
    }

    public String getShort_description() {
        return short_description;
    }

    public String getPlace() {
        return place;
    }

    public String getId() {
        return id;
    }
}
