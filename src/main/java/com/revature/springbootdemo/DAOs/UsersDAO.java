package com.revature.springbootdemo.DAOs;//package DAOs;

//import Annotations.*;
//import com.revature.springbootdemo.Annotations;
import com.revature.springbootdemo.Annotations.*;

@Entity(tableName = "Users")
public class UsersDAO {

    //Fields
    @PrimaryKey( autoIncrement =  true)
    @Property(fieldName = "id")
    private Integer ID = 9;

    @Property(fieldName = "firstname")
    private String FirstName;

    @Property(fieldName = "lastname")
    private String LastName;

    @Property(fieldName = "password")
    private String Password;

    @Property(fieldName = "email")
    private String Email;

    //Getters and Setters
    @Getter(fieldName = "id")
    public Integer getID() {
        return ID;
    }

    @Setter(fieldName = "id")
    public void setID(Integer ID) {
        this.ID = ID;
    }

    @Getter(fieldName = "firstname")
    public String getFirstName() {
        return FirstName;
    }
    @Setter(fieldName = "firstname")
    public void setFirstName(String firstName) {
        FirstName = firstName;
    }
    @Getter(fieldName = "lastname")
    public String getLastName() {
        return LastName;
    }
    @Setter(fieldName = "lastname")
    public void setLastName(String lastName) {
        LastName = lastName;
    }
    @Getter(fieldName = "password")
    public String getPassword() {
        return Password;
    }

    @Setter(fieldName = "password")
    public void setPassword(String password) {
        Password = password;
    }

    @Getter(fieldName = "email")
    public String getEmail() {
        return Email;
    }

    @Setter(fieldName = "email")
    public void setEmail(String email) {
        Email = email;
    }

    public UsersDAO()
    {

    }
    /*
    @FakeConstructor
    public UsersDAO fakeConstructor()
    {
        return new UsersDAO();
    }*/


    public UsersDAO(int id, String FirstName, String LastName, String Password, String Email)
    {
        this.setID(id);
        this.setFirstName(FirstName);
        this.setLastName(LastName);
        this.setPassword(Password);
        this.setEmail(Email);
        //SaveUser();
    }

}

