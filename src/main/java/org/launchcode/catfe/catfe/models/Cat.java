package org.launchcode.catfe.catfe.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Cat {

    @ManyToOne
    @JoinColumn(name="cafe_id", nullable = false)
    private Cafe cafe;

    @Id
    @GeneratedValue
    private Integer id;

    private String catName;
    private Integer catAge;
    private String catBreed;
    private String catGender;
    private BigDecimal catWeightLbs;
    private BigDecimal catHeightInches;
    private String catDescription;
    private String catPhotos;

    @ManyToMany(mappedBy = "favCats")
    private Set<User> userFavCats;

    @Transient
    public String getPhotosImagePath(){
        if (catPhotos == null || id == null) return null;

        return "/resources/images/Cat-photos" + id + "/" + catPhotos;
    }

    public Cat() {
    }


    public Cat(String catName, Integer catAge, String catBreed, String catGender, BigDecimal catWeightLbs, BigDecimal catHeightInches, String catDescription) {
        this.catName = catName;
        this.catAge = catAge;
        this.catBreed = catBreed;
        this.catGender = catGender;
        this.catWeightLbs = catWeightLbs;
        this.catHeightInches = catHeightInches;
        this.catDescription = catDescription;



    }

    public Integer getId() {
        return id;
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

    public Cafe getCafe() {
        return cafe;
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
    }

    public Set<User> getUserFavCats() {
        return userFavCats;
    }

    public String getCatPhotos() {
        return catPhotos;
    }

    public void setCatPhotos(String catPhotos) {
        this.catPhotos = catPhotos;
    }
}

