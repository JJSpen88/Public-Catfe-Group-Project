package org.launchcode.catfe.catfe.models;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Entity
public class Food{
    @ManyToOne
    @JoinColumn(name="cafe_id",nullable = false)
    private Cafe cafe;




    @Id
    @GeneratedValue
    private Integer id;

    private String FoodName;
    private String FoodDescription;
    private Category foodCategory;
    @DecimalMin("0.00")
    private BigDecimal price;
    private DietaryRestrictions foodDiet;
    private String photo;


    @Transient
    public String getPhotosImagePath(){
        if (photo == null || id == null) return null;

        return "resources/images/food-photos" + id + "/" + photo;
    }

    //constructors

    public Food() {

    }


    public Food(String foodName, String foodDescription, Category foodCategory, BigDecimal price, DietaryRestrictions foodDiet) {
        this.FoodName = foodName;
        this.FoodDescription = foodDescription;
        this.foodCategory = foodCategory;
        this.price = price;
        this.foodDiet = foodDiet;
    }




    //Getters and setters


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

    public Cafe getCafe() {
        return cafe;
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
