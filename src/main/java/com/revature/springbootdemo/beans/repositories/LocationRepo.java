package com.revature.springbootdemo.beans.repositories;

<<<<<<< HEAD
import com.revature.springbootdemo.beans.models.ReviewsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepo extends JpaRepository<ReviewsModel, Integer>{

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
import com.revature.springbootdemo.beans.models.LocationModel;
import com.revature.springbootdemo.beans.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepo extends JpaRepository<LocationModel, Integer> {
>>>>>>> 904af3f59de6c097d994f61b9632070e444861ac

}
