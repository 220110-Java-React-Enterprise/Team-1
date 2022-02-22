package com.revature.springbootdemo.beans.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.springbootdemo.CityAPIService;
import com.revature.springbootdemo.CountryAPIService;
import com.revature.springbootdemo.SpringBootDemoApplication;
import com.revature.springbootdemo.WeatherAPIService;
import com.revature.springbootdemo.beans.models.SearchResultModel;
import com.revature.springbootdemo.beans.models.UserModel;
import com.revature.springbootdemo.beans.repositories.CustomUserRepoImpl;
import com.revature.springbootdemo.beans.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/register")
public class UserController {

    public  final UserRepo userRepo;

    @Autowired
    public  UserController(UserRepo userRepo)
    {
        this.userRepo = userRepo;
    }

    //register new user
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String Login(@RequestParam(value = "myFirstname", defaultValue = "userFirst") String FirstName,
                        @RequestParam(value = "myLastname", defaultValue = "userLast") String LastName,
                        @RequestParam(value = "myPassword", defaultValue = "1111") String Password,
                        @RequestParam(value = "myEmail", defaultValue = "user@gmail.com") String Email) {

        UserModel user = new UserModel( FirstName, LastName, Password, Email);
        userRepo.save(user);
        return String.format("have added the user  %s and %s and %s nad %s successfully", FirstName, LastName, Password, Email);

    }


    //login method, get username and password and verify the user exists
    @RequestMapping(value ="/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public String Login(@RequestParam(value = "myEmail", defaultValue = "email") String Email,
                        @RequestParam(value = "myPassword", defaultValue = "password1") String Password) {

        CustomUserRepoImpl CustomRepoImp = new CustomUserRepoImpl();
        UserModel u = CustomRepoImp.findByName(Email, Password);

        if (u != null)
            return "Logged in successful. \nHello " + Email + " with password: " +  Password;
        else
            return "User doesn't exist or wrong credentials";

    }
    
    //search method, retrieve all search results 
    @RequestMapping(value="/search", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public String Search(@RequestParam(value = "userEnteredCity") String userEnteredCity) {
        try {
            String cityResult = CityAPIService.getCityInfo(userEnteredCity);
            String weatherResult = WeatherAPIService.getCityWeather(userEnteredCity);
            System.out.println(userEnteredCity);

            ObjectMapper mapper = new ObjectMapper();
            List<SearchResultModel> searchResultModels = Arrays.asList(mapper.readValue(cityResult, SearchResultModel[].class));
            String countryResult = CountryAPIService.getCountryInfo(searchResultModels.get(0).getCountry());
            
            String total = cityResult + weatherResult + countryResult;
            return total;
        } catch (Exception e) {
            SpringBootDemoApplication.fileLogger.log(e);
        }
        
        return null;
    }
}
