package com.softuni.gamestore.services;

import com.softuni.gamestore.domain.entities.Role;

public class UserSession {
    private String username;
    private Role role;
    private static UserSession userSession = null;

    public static UserSession getInstance() {
        if (userSession == null) {
            userSession = new UserSession();
        }
        return userSession;
    }

    private UserSession() {
        this.username = null;
        this.role = Role.USER;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
