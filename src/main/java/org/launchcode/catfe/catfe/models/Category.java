package org.launchcode.catfe.catfe.models;

public enum Category {
    BREAKFAST("Breakfast"),
    BRUNCH("Brunch"),
    LUNCH("Lunch"),
    LUNCHCOMBO("Lunch Combo"),
    SNACK("Snack"),
    DINNER("Dinner"),
    DINNERCOMBO("Dinner Combo"),
    DESERT("Dessert"),
    BEVERAGES("Beverages");

    private final String categories;

    Category(String categories) {
        this.categories = categories;
    }

    public String getCategories() {
        return categories;
    }
}
