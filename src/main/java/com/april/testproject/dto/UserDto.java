package com.april.testproject.dto;

public class UserDto {
    private String name;
    private String role;
    private String country;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
