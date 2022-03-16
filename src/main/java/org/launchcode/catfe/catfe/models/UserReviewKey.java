package org.launchcode.catfe.catfe.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserReviewKey implements Serializable {

    @Column(name = "user_id", unique = true)
    int userId;


    @Column(name = "cafe_id", unique = true)
    int cafeId;


    @Override
    public int hashCode (){
        return Objects.hash(userId, cafeId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserReviewKey) {
            UserReviewKey key = (UserReviewKey) obj;
            if (key.userId == this.userId && key.cafeId == this.cafeId) {
                return true;
            }
        }
        return false;
    }
}
