package com.example.trading.user.model;

import jakarta.persistence.Column;

public class SaveUserDTO {

    private String username;
    private String email;
    private String fullName;
    private String password;
    private Boolean isActive = true;

    public SaveUserDTO(String username, String email, String fullName, String password, Boolean isActive) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.isActive = isActive;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
