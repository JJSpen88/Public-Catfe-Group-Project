package org.launchcode.catfe.catfe.models.DTO;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.security.PrivateKey;

public class CafeRegisterFormDTO{
    private int id;
    @Size(min = 3, max = 20, message = "Invalid username. Must be between 3 and 20 characters.")
    private String username;

    @Size(min = 8, max = 16, message = "Invalid password. Must be between 8 and 16 characters.")
    private String password;

    private String verifyPassword;
    private String cafeName;
    private String streetAddress;
    private String stateLocation;
    private String zipCode;
    private String phoneNum;
    private String emailAddress;
    private String cafeDescription;
    private String cafeRules;
    private String instaLink;
    private String fbLink;
    private String twitterLink;
    private BigDecimal admissionPrice;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getCafeName() {
        return cafeName;
    }

    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getStateLocation() {
        return stateLocation;
    }

    public void setStateLocation(String stateLocation) {
        this.stateLocation = stateLocation;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCafeDescription() {
        return cafeDescription;
    }

    public void setCafeDescription(String cafeDescription) {
        this.cafeDescription = cafeDescription;
    }

    public String getCafeRules() {
        return cafeRules;
    }

    public void setCafeRules(String cafeRules) {
        this.cafeRules = cafeRules;
    }

    public String getInstaLink() {
        return instaLink;
    }

    public void setInstaLink(String instaLink) {
        this.instaLink = instaLink;
    }

    public String getFbLink() {
        return fbLink;
    }

    public void setFbLink(String fbLink) {
        this.fbLink = fbLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public BigDecimal getAdmissionPrice() {
        return admissionPrice;
    }

    public void setAdmissionPrice(BigDecimal admissionPrice) {
        this.admissionPrice = admissionPrice;
    }
}