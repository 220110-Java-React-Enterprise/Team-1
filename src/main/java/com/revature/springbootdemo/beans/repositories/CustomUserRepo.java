package com.revature.springbootdemo.beans.repositories;

import com.revature.springbootdemo.beans.models.UserModel;

//from where I found that this interface shouldn't extend the UserRepo interface
//https://stackoverflow.com/questions/11880924/how-to-add-custom-method-to-spring-data-jpa

public interface CustomUserRepo {
     UserModel findByName(String email, String password);
}
