package com.aircondition.refrigerationsys.Model;

public enum Role {
    USER("Client"),
    ADMIN("Admin");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}