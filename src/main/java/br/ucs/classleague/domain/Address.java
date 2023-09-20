package br.ucs.classleague.domain;

import java.io.Serializable;

/**
 *
 * @author Ezequiel
 */
public class Address implements Serializable {

    private Long id;
    private String state;
    private String city;
    private String district;
    private String street;
    private String complement;
    private String zipCode;
    private Integer number;

    public Address() {
    }

    public Address(String state, String city, String district, String street, String complement, String zipCode, Integer number) {
        this.state = state;
        this.city = city;
        this.district = district;
        this.street = street;
        this.complement = complement;
        this.zipCode = zipCode;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
