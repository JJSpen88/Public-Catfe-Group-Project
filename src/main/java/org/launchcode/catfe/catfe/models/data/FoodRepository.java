package org.launchcode.catfe.catfe.models.data;

import org.launchcode.catfe.catfe.models.Food;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends CrudRepository <Food, Integer> {

}
