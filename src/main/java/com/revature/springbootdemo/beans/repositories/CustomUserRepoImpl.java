package com.revature.springbootdemo.beans.repositories;

import com.revature.springbootdemo.beans.models.UserModel;
import com.revature.springbootdemo.beans.utils.FileLogger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManager;


@Repository
public class CustomUserRepoImpl implements CustomUserRepo {

    @PersistenceContext
    EntityManager entityManager;
    EntityManagerFactory emf;
    private static String Logpath = "logs/" + LocalDate.now();
    static FileLogger fileLogger;

    public CustomUserRepoImpl() {

        File f = new File(Logpath);

        if (!f.exists()) {
            try {
                System.out.println("log file path: " + f.getAbsolutePath());
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fileLogger = FileLogger.getFileLogger();
        fileLogger.log("started: " + LocalDateTime.now());
    }

    //from where I found about CreateQuery and CreateNativeQuery
    //https://docs.oracle.com/javaee/6/tutorial/doc/bnbrg.html
    //https://dzone.com/articles/add-custom-functionality-to-a-spring-data-reposito

    /*** findByName: method returns a userModel object having email and password. used for logging
     *
     * @param email: email of user
     * @param password: password of user
     * @return: UserModel object.
     */
    public UserModel findByName(String email, String password) {

        try {
            Properties props = new Properties();
            props.load(new FileInputStream("src/main/resources/META-INF/persistence.xml"));
            emf = Persistence.createEntityManagerFactory("myPersistenceUnit", props);
            try {
                if (emf == null) {
                    fileLogger.log("emf is null");
                } else {
                    entityManager = emf.createEntityManager();
                    //Note:
                    //  using parameter didn't work. but using string for sql statement and concatenation worked.
                    //  added the following statements to check for SQL injection manually
                    if ((email.contains("OR") || email.contains(" ")) || (password.contains("OR") || password.contains(" "))) {
                        fileLogger.log("email and password included \"or\"/space character attempting to do SQL injection (CustomUserRepoImpl)");
                        return null;
                    }
                    String SQL = "SELECT * FROM user_model WHERE email='" + email + "' and password='" + password + "';";

                    javax.persistence.Query query = entityManager.createNativeQuery(SQL);

                    if (entityManager == null) {
                        fileLogger.log("entity manager is null");
                        if (10 == 10) return null;
                    }

                    if (query == null) {
                        fileLogger.log("Query is null");
                        return null;
                    } else {
                        fileLogger.log("query is NOT NULL:\n" + query);
                    }

                    List<UserModel> Users = query.getResultList();
                    if (Users.size() >= 1) //user exists
                    {
                        //System.out.println("query returned a result. user exists");
                        System.out.println("list size is: " + Users.size());
                        //for (int i = 0 ; i < Users.size() ; i++)
                        //    System.out.println("ith element is: " + ((UserModel)Users.get(0)).getEmail());
                        //UserModel u = Users.get(0);
                        //System.out.println(u.getID() + "," + u.getFirstName() +"," + u.getLastName() +"," + u.getPassword() +"," + u.getEmail());
                        return new UserModel("", "", "", ""); //return dummy object (not null the user exits)
                    } else {
                        fileLogger.log("query returned no results. user doesn't exist");
                        return null;
                    }

                }
            } catch (Exception exc) {
                fileLogger.log(exc);
            }

        } catch (Exception exc) {
            fileLogger.log(exc);
        }
        return null;
    }

}
