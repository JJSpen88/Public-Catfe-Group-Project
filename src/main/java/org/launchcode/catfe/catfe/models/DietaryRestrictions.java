package org.launchcode.catfe.catfe.models;

public enum DietaryRestrictions {
    NONE(" "),
    VEGETARIAN("Vegetarian"),
    VEGAN("Vegan"),
    LACTOSE("Lactose Intolerant"),
    PEANUT("Peanut Allergies"),
    KOSHER("Kosher"),
    HALAL("Halal");

    private final String dietaryRestrictions;

    DietaryRestrictions(String dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public String getDietaryRestrictions() {
        return dietaryRestrictions;
    }
}
