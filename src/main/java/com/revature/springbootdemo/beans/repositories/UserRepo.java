package com.revature.springbootdemo.beans.repositories;

import com.revature.springbootdemo.beans.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * User: utilize the Jpa repository in order to implement and to extend the rest CRUD presented below.
 */

@Repository
public interface UserRepo extends JpaRepository<UserModel, Integer> {

        /*
    Extending the CrudRepository should implement the following CRUD operations for us:
    save()
    saveAll()
    findById()
    existsById()
    findAll()
    findAllById()
    count()
    deleteById()
    delete()
    deleteAll()
     */

}
