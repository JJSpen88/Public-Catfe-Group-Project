package org.launchcode.catfe.catfe.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class UserReview {

    @Id
    @GeneratedValue
    private int id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("{userReviews, favCafes, favCats}")
    private User user;


    @ManyToOne
    @JoinColumn(name = "cafe_id")
    @JsonIgnoreProperties("userReviews")
    Cafe cafe;


    private String reviewTitle;


    private String userReview;


    private int userRating;



    public UserReview(User user, String reviewTitle, String userReview, int userRating) {
        this.user = user;
        this.reviewTitle = reviewTitle;
        this.userReview = userReview;
        this.userRating = userRating;

    }

    public UserReview() {}


    public int getId() {
        return id;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getUserReview() {
        return userReview;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }





}
