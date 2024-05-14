package com.raghad.sales_management_system.logging;

public record ResourceUpdatedField(String name, Object oldValue, Object newValue) {
    @Override
    public String toString() {
        return "Field Name: " + this.name + ", Old Value: " + this.oldValue + ", New Value: " + this.newValue;
    }
}
