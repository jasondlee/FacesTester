package com.steeplsoft.jsf.sample;

public class ZipCodeMapperPage {
    private String zipCode;
    private String city = "Santa Clara";

    public String lookupCityForZip() {
        if ("95054".equals(zipCode)) {
            city = "Santa Clara";
        } else {
            city = "Unknown";
        }
        return "success";
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
