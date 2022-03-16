package org.launchcode.catfe.catfe.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;


@Entity
public class User {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    @Id
    @GeneratedValue
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;


    @Size(min = 8, max = 16)
    private String password;

    private String verifyPassword;

    private String pwHash;
    
    private int avatar;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"userFavCats" , "userFavCafes", "userReviews", "cafe"})
    private Set<Cat> favCats;


    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"userFavCats" , "userFavCafes", "userReviews"})
    private Set<Cafe> favCafes;

    @OneToMany(targetEntity = UserReview.class, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"})
    private Set<UserReview> userReviews;



    public User(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.avatar = avatarSelect();

    }

    public int getAvatar() {
        return avatar;
    }

    private int avatarSelect() {
        Random r = new Random();
        int low = 0;
        int high = 20;
        int result = r.nextInt(high-low) + low;
        return result;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Set<UserReview> getUserReviews() {
        return userReviews;
    }

    public void setUserReview(UserReview userReview) {
        this.userReviews.add(userReview);
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public String getPwHash() {
        return pwHash;
    }

    public boolean isMatchingPassword(String password){
        return encoder.matches(password, pwHash);
    }

    public Set<Cat> getFavCats() {
        return favCats;
    }

    public void addFavCat(Cat cat) {
        this.favCats.add(cat);
    }

    public void removeFavCat(Cat cat) {
        this.favCats.remove(cat);
    }

    public Set<Cafe> getFavCafes() {
        return favCafes;
    }

    public void addFavCafe(Cafe cafe){
        this.favCafes.add(cafe);
    }

    public void removeFavCafe(Cafe cafe){
        this.favCafes.remove(cafe);
    }



    @Override
    public String toString() {
        return "User{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", pwHash='" + pwHash + '\'' +
                '}';
    }
}
