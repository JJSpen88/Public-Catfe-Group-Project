package org.launchcode.catfe.catfe.models.DTO;


import org.launchcode.catfe.catfe.models.Cafe;
import org.launchcode.catfe.catfe.models.User;
import org.launchcode.catfe.catfe.models.UserReview;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class UserReviewDTO{

    private int id;

    User user;

    Cafe cafe;

    private String reviewTitle;


    private String userReview;


    private int userRating;



    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cafe getCafe() {
        return cafe;
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
    }

    public String getReviewTitle() {
        return reviewTitle;
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

