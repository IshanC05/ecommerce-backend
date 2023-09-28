package com.myapps.ecommerce.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myapps.ecommerce.entity.Users;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddressDto {

    private Integer id;

    @NotNull(message = "Street cannot be null")
    @Size(min = 3, message = "Street should be atleast 3 characters")
    private String street;

    @NotNull(message = "City cannot be null")
    @Size(min = 3, message = "City should be atleast 3 characters")
    private String city;

    @NotNull(message = "Country cannot be null")
    @Size(min = 3, message = "Country should be atleast 3 characters")
    private String country;

    @JsonIgnore
    private Users user;

    public AddressDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
