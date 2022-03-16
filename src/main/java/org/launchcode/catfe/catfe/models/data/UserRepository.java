package org.launchcode.catfe.catfe.models.data;


import org.launchcode.catfe.catfe.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

}
