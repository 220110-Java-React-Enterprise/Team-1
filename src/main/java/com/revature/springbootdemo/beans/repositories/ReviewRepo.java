package com.revature.springbootdemo.beans.repositories;

import com.revature.springbootdemo.beans.models.ReviewsModel;
import com.revature.springbootdemo.beans.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepo extends JpaRepository<ReviewsModel, Integer> {
}
