package org.launchcode.catfe.catfe.models.data;

import org.launchcode.catfe.catfe.models.Cat;
import org.springframework.data.repository.CrudRepository;

public interface CatRepository extends CrudRepository <Cat, Integer>{

}
