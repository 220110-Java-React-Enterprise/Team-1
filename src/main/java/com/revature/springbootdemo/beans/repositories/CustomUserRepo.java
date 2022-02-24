package com.revature.springbootdemo.beans.repositories;

import com.revature.springbootdemo.beans.models.UserModel;

import javax.servlet.http.HttpServletRequest;

/**
 * Custom User: where information (email, password) is retrieve for the user.
 */

public interface CustomUserRepo {

    UserModel findByName(String email, String password);

}
