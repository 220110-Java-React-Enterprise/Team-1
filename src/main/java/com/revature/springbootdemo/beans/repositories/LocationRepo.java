package com.revature.springbootdemo.beans.repositories;

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

}
