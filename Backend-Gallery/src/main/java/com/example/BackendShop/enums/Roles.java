package com.example.BackendShop.enums;

public enum Roles {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    Roles(String input) {
        this.value = input;
    }
    public String getValue() {
        return value;
    }
}
