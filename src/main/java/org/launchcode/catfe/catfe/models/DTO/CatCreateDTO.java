package org.launchcode.catfe.catfe.models.DTO;

import java.math.BigDecimal;

public class CatCreateDTO {
    private int id;
    private String catName;
    private Integer catAge;
    private String catBreed;
    private String catGender;
    private BigDecimal catWeightLbs;
    private BigDecimal catHeightInches;
    private String catDescription;
    private String catPhotos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public Integer getCatAge() {
        return catAge;
    }

    public void setCatAge(Integer catAge) {
        this.catAge = catAge;
    }

    public String getCatBreed() {
        return catBreed;
    }

    public void setCatBreed(String catBreed) {
        this.catBreed = catBreed;
    }

    public String getCatGender() {
        return catGender;
    }

    public void setCatGender(String catGender) {
        this.catGender = catGender;
    }

    public BigDecimal getCatWeightLbs() {
        return catWeightLbs;
    }

    public void setCatWeightLbs(BigDecimal catWeightLbs) {
        this.catWeightLbs = catWeightLbs;
    }

    public BigDecimal getCatHeightInches() {
        return catHeightInches;
    }

    public void setCatHeightInches(BigDecimal catHeightInches) {
        this.catHeightInches = catHeightInches;
    }

    public String getCatDescription() {
        return catDescription;
    }

    public void setCatDescription(String catDescription) {
        this.catDescription = catDescription;
    }

    public String getCatPhotos() {
        return catPhotos;
    }

    public void setCatPhotos(String catPhotos) {
        this.catPhotos = catPhotos;
    }
}
