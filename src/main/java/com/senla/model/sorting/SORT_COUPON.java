package com.senla.model.sorting;

public enum SORT_COUPON {

    START_DATE("startDate"), END_DATE("endDate"), DISCOUNT("discount"), TRADEMARK("trademark_id");

    private String description;
    SORT_COUPON(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return description;
    }
}
