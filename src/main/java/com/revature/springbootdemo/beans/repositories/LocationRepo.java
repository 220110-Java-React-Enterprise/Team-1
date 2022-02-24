package com.revature.springbootdemo.beans.repositories;

import com.revature.springbootdemo.beans.models.LocationModel;
import com.revature.springbootdemo.beans.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepo extends JpaRepository<LocationModel, Integer> {

}
