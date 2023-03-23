package com.senla.model.sorting;

public enum SORT_CARD {
    NUMBER("number"), DISCOUNT("discount");

    private String description;
    SORT_CARD(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return description;
    }
}
