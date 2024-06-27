package ru.verbitskiy.data;

import java.util.NoSuchElementException;

public enum Role {
    admin("ADMIN"),
    user("USER");

    private final String role;

    Role(String color) {
        this.role = color;
    }

    public static Role fromString(String s) {
        for (Role role : values()) {
            if (role.toString().equals(s)) {
                return role;
            }
        }
        throw new NoSuchElementException("Element with role " + s + " has not been found");
    }

    @Override
    public String toString() {
        return role;
    }
}
