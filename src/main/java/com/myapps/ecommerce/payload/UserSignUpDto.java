package com.myapps.ecommerce.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserSignUpDto {

    @Email(message = "Email address is not valid")
    private String email;

    @NotEmpty
    @Size(min = 5, message = "Password must be atleast 5 chars long")
    private String password;

    @NotEmpty
    @Size(min = 3, message = "Name must be atleast 3 chars long")
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
