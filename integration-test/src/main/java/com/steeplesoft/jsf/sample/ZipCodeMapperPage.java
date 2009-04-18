package com.steeplesoft.jsf.sample;

public class ZipCodeMapperPage {
    // Some deafult values, just to make sure we're manipulating the UIComponent
    // tree correctly
    private String zipCode = "12345";
    private String city = "Schenectady";

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
