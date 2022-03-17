package com.example.desafio9.model;

public class Password {
    public Password(String value, PasswordStrength strength) {
        this.value = value;
        this.strength = strength;
    }

    private String value;
    private PasswordStrength strength;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PasswordStrength getStrength() {
        return strength;
    }

    public void setStrength(PasswordStrength strength) {
        this.strength = strength;
    }
}
