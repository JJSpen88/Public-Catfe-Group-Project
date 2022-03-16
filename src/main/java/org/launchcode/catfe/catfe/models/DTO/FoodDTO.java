package org.launchcode.catfe.catfe.models.DTO;

import org.launchcode.catfe.catfe.models.Category;
import org.launchcode.catfe.catfe.models.DietaryRestrictions;


import java.math.BigDecimal;



public class FoodDTO {
    private Integer id;
    private String FoodName;
    private String FoodDescription;
    private Category foodCategory;
    private BigDecimal price;
    private DietaryRestrictions foodDiet;
    private String photo;

    public FoodDTO(String foodName, String foodDescription, Category foodCategory,BigDecimal price, DietaryRestrictions foodDiet) {

    }

    public FoodDTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodDescription() {
        return FoodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        FoodDescription = foodDescription;
    }

    public Category getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(Category foodCategory) {
        this.foodCategory = foodCategory;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public DietaryRestrictions getFoodDiet() {
        return foodDiet;
    }

    public void setFoodDiet(DietaryRestrictions foodDiet) {
        this.foodDiet = foodDiet;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
