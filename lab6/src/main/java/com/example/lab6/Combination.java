package com.example.lab6;

public enum Combination {
    GENERAL("Генерал"),
    FOUR("Чотири"),
    FULL_HOUSE("Фулл хауз"),
    STRAIGHT("Стрейт"),
    NONE("Нічого");

    private final String title;

    Combination(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
