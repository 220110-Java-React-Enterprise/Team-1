package com.revature.springbootdemo.beans.repositories;


import com.revature.springbootdemo.beans.models.ReviewsModel;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Review: utilize the Jpa repository in order to implement and to extend the rest CRUD presented below.
 */
public interface ReviewRepo extends JpaRepository<ReviewsModel, Integer> {

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

