package org.launchcode.catfe.catfe.models.data;

import org.launchcode.catfe.catfe.models.UserReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReviewRepository extends CrudRepository<UserReview, Integer> {

}
