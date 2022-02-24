package com.revature.springbootdemo.beans.repositories;

import com.revature.springbootdemo.beans.models.ReviewsModel;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;

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

=======
import com.revature.springbootdemo.beans.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepo extends JpaRepository<ReviewsModel, Integer> {
>>>>>>> 904af3f59de6c097d994f61b9632070e444861ac
}
