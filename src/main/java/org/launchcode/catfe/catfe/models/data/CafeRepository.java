package org.launchcode.catfe.catfe.models.data;

import org.launchcode.catfe.catfe.models.Cafe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeRepository extends CrudRepository<Cafe, Integer> {

    Cafe findByUsername(String username);

}
