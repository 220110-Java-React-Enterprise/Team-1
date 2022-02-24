package com.revature.springbootdemo.beans.Controller;

import com.revature.springbootdemo.beans.models.LocationModel;
import com.revature.springbootdemo.beans.models.ReviewsModel;
import com.revature.springbootdemo.beans.models.UserModel;
import com.revature.springbootdemo.beans.repositories.LocationRepo;
import com.revature.springbootdemo.beans.repositories.ReviewRepo;
import com.revature.springbootdemo.beans.repositories.UserRepo;
import com.revature.springbootdemo.beans.utils.FileLogger;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.http.HttpStatus;
=======
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.DelegatingServerHttpResponse;
>>>>>>> 904af3f59de6c097d994f61b9632070e444861ac
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
<<<<<<< HEAD
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
=======
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
>>>>>>> 904af3f59de6c097d994f61b9632070e444861ac
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
<<<<<<< HEAD
import java.util.List;
=======
import java.util.*;
>>>>>>> 904af3f59de6c097d994f61b9632070e444861ac

@RestController
@RequestMapping("/Reviews")
public class ReviewController {
<<<<<<< HEAD

=======
>>>>>>> 904af3f59de6c097d994f61b9632070e444861ac
    @PersistenceContext
    EntityManager entityManager;
    EntityManagerFactory emf;

    public  final ReviewRepo reviewRepo;
    public final UserRepo userRepo;
    public final LocationRepo locationRepo;

    static FileLogger fileLogger;
    private static String PropertiesPath = "src/main/resources/Keys.properties"; //keys properties file
    private static String Logpath = "logs/" + LocalDate.now();

    @Autowired
<<<<<<< HEAD
    public  ReviewController(ReviewRepo reviewRepo, UserRepo userRepo, LocationRepo locatinoRepo) {

=======
    public  ReviewController(ReviewRepo reviewRepo, UserRepo userRepo, LocationRepo locatinoRepo)
    {
>>>>>>> 904af3f59de6c097d994f61b9632070e444861ac
        this.reviewRepo = reviewRepo;
        this.userRepo = userRepo;
        this.locationRepo = locatinoRepo;
        fileLogger = FileLogger.getFileLogger();
        fileLogger.log("reviewRepo started: " + LocalDateTime.now());
    }

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
<<<<<<< HEAD
    public String CreateReview(@RequestParam(value = "ReviewContent", defaultValue = "dummy review") String content, HttpServletRequest request) {
=======
    public String CreateReview(@RequestParam(value = "ReviewContent", defaultValue = "dummy review") String content, HttpServletRequest request ) {
        System.out.println("I am here");
       // HttpServletRequest request = null;
>>>>>>> 904af3f59de6c097d994f61b9632070e444861ac
        try
        {
            //get id of the current logged user
            //get the email of the currently logged-in user
            //create a session variable
            String UserEmail = "";
            int UserId = 1; //dummy value
            int LocationId = 1;
            HttpSession session = request.getSession(false);
            if (session != null) {
                UserEmail = ((String) session.getAttribute("Email"));
                UserId = ((Integer)session.getAttribute("ID"));
                LocationId = ((Integer)session.getAttribute("location_id"));
            }
            else
            {
                fileLogger.log("you must be logged in to review content");
                return "you must be logged in to review content";
            }

            ReviewsModel r = new ReviewsModel(UserId,  content, 0 , LocationId,
                    Time.valueOf(LocalTime.now()), 0);
            reviewRepo.save(r);
            return "<a href=\"#\" onclick=\"history.back()\"><img SRC=\"images\\back_button.jpg\"></a></br></br>" +
                    String.format("review by user %s as %s with rating %s and location id %s and time %s and reply to review %s has been successfully created", UserId,
<<<<<<< HEAD
                            content, 0, LocationId, LocalTime.now(), 1);
=======
                    content, 0, LocationId, LocalTime.now(), 1);
>>>>>>> 904af3f59de6c097d994f61b9632070e444861ac
        }
        catch(Exception exc)
        {
            fileLogger.log(exc);
        }
        return null;
    }

    @GetMapping("/View")
    public String DisplayReviews(@RequestParam(value = "ReviewContent", defaultValue = "Dummy Review") String content, HttpServletRequest request) {
<<<<<<< HEAD
=======
       // HttpServletRequest request = null;
>>>>>>> 904af3f59de6c097d994f61b9632070e444861ac
        String result = "<a href=\"#\" onclick=\"history.back()\"><img SRC=\"images\\back_button.jpg\"></a></br></br>";
        result += "Reviews ";
        boolean hasReviews = false;

        List<ReviewsModel> reviews = reviewRepo.findAll();

        HttpSession session = request.getSession(false);
        if (session != null) {

            String UserEmail = ((String) session.getAttribute("Email"));
            int UserId = ((Integer)session.getAttribute("ID"));
            int LocationId = ((Integer)session.getAttribute("location_id"));
            result += " for city with id " + LocationId + " and which has the following information</br>";
            //get location information
            List<LocationModel> locs = locationRepo.findAll();
            for( LocationModel l: locs)
            {
                if (l.getLocationID() == LocationId)
                {
<<<<<<< HEAD
                    result += " name: " + l.getCity() + ", in " + l.getState() +" state in " +
                            l.getCountry() + " country with " + l.getPopulation() + " population and " +
                            l.getLatitude() + "," + l.getLongitude() + " latitude and longitude and is capital (" + l.isIs_capital() + ")</br></br>";
=======
                    result += " name: " + l.getname() + ", in " + l.getState() +" state in " +
                            l.getCountry() + " country with " + l.getPopulation() + " population and " +
                            l.getLatitude() + "," + l.getLongitude() + " latitude and longitude and is capital (" + l.getIs_capital() + ")</br></br>";
>>>>>>> 904af3f59de6c097d994f61b9632070e444861ac
                    break;
                }
            }
            //get user information
            result += "<TABLE border=\"1\" summary=\"User info\"> " +
                    "<CAPTION><EM>user Info Table</EM></CAPTION>"+
                    "<TR><TH>User_id<TH>first Name<TH>Last Name<TH>Password<TH>Email</TR>";

            List<UserModel> users =  userRepo.findAll();
            for (UserModel u: users) {
                if (u.getID() == UserId)
                {
                    result += "Made by user: " +  "</br>";
                    result += "<TR><TD>" + u.getID() + "<TD>" + u.getFirstName() +
<<<<<<< HEAD
                            "<TD>" +  u.getLastName() + "<TD>" + u.getPassword()  + "<TD>" + u.getEmail() + "</TR>";
=======
                              "<TD>" +  u.getLastName() + "<TD>" + u.getPassword()  + "<TD>" + u.getEmail() + "</TR>";
>>>>>>> 904af3f59de6c097d994f61b9632070e444861ac

                    break;
                }
            }
            result += "</TABLE></br>";

            result += "<TABLE border=\"1\" summary=\"Review info\"> " +
                    "<CAPTION><EM>Review Info Table</EM></CAPTION>" +
                    "<TR><TH>Review_Id<TH>Made by user<TH>content<TH>rating<TH>time<TH>Location_ID<TH>Reply to Review</TR>";



            for (ReviewsModel rm : reviews) {
                if (LocationId == rm.getLocationID()) {

                    result += "<TR><TD>" + rm.getReviewD();

                    for (UserModel u: users) {
                        if (u.getID() == rm.getID())
                        {
                            result += "<TD>ID: " + u.getID() + "</br> " +
                                    "FirstName: " +  u.getFirstName() + "</br>" +
                                    "LastName: " +  u.getLastName();
                            break;
                        }
                    }
                    result += "<TD>"  + rm.getContent();
                    result += "<TD>";
                    int rat = rm.getRating();
<<<<<<< HEAD
                    result += "<form action=\"/Reviews/rating\" method=\"post\">" +
                            "<select name=\"ratingMenu\" id=\"ratingMenu\">";
                    if (rat == 0)
                        rat = 3;
                    if (rat == 1)
                        result +=  "<option value=\"1\" selected>1</option>";
                    else
                        result +=  "<option value=\"1\">1</option>";
                    if (rat == 2)
                        result +="<option value=\"2\" selected>2</option>" ;
                    else
                        result +="<option value=\"2\">2</option>";
                    if (rat == 3)
                        result +="<option value=\"3\" selected>3</option>" ;
                    else
                        result +="<option value=\"3\">3</option>" ;
                    if (rat == 4)
                        result +="<option value=\"4\" selected>4</option>" ;
                    else
                        result +="<option value=\"4\">4</option>" ;
                    if (rat == 5)
                        result += "<option value=\"5\" selected>5</option>" ;
                    else
                        result +="<option value=\"5\">5</option>";
                    result += "</select>" +
                            "<input type=\"submit\" value=\"Update\">" +
                            "</form>" +
=======
                            result += "<form action=\"/Reviews/rating\" method=\"post\">" +
                                      "<select name=\"ratingMenu\" id=\"ratingMenu\">";
                            if (rat == 0)
                                rat = 3;
                            if (rat == 1)
                                result +=  "<option value=\"1\" selected>1</option>";
                            else
                                result +=  "<option value=\"1\">1</option>";
                            if (rat == 2)
                                result +="<option value=\"2\" selected>2</option>" ;
                            else
                                result +="<option value=\"2\">2</option>";
                            if (rat == 3)
                                result +="<option value=\"3\" selected>3</option>" ;
                            else
                                result +="<option value=\"3\">3</option>" ;
                            if (rat == 4)
                                result +="<option value=\"4\" selected>4</option>" ;
                            else
                                result +="<option value=\"4\">4</option>" ;
                            if (rat == 5)
                                result += "<option value=\"5\" selected>5</option>" ;
                            else
                                result +="<option value=\"5\">5</option>";
                    result += "</select>" +
                              "<input type=\"submit\" value=\"Update\">" +
                              "</form>" +
>>>>>>> 904af3f59de6c097d994f61b9632070e444861ac

                            "<TD>" + rm.getTime()  +
                            "<TD>" + rm.getLocationID() +
                            "<TD>" +  rm.getReplyToReview() +
                            "</TR>";

                    hasReviews = true;
                }
            }
            result += "</table></br>";
        }
        else
        {
            fileLogger.log("You must be logged in to view reviews");
            return "you must be logged in to view reviews";
        }
        if (!hasReviews)
            result += "no Reviews for this city";
        return result;
    }

    //not used yet
    @GetMapping("/rating")
    public String rating(@RequestParam(value = "ratingMenu", defaultValue = "Dummy Review") String rating, HttpServletRequest request) {
<<<<<<< HEAD
        String result = "reivew rating has been updated";


        return result;
    }
}
=======
           String result = "reivew rating has been updated";



            return result;
           }
    }
>>>>>>> 904af3f59de6c097d994f61b9632070e444861ac
