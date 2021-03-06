package com.revature.springbootdemo.beans.Controller;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.springbootdemo.SpringBootDemoApplication;
import com.revature.springbootdemo.beans.models.LocationModel;
import com.revature.springbootdemo.beans.models.UserModel;
import com.revature.springbootdemo.CityAPIService;
import com.revature.springbootdemo.CountryAPIService;
import com.revature.springbootdemo.WeatherAPIService;
import com.revature.springbootdemo.beans.models.*;
import com.revature.springbootdemo.beans.repositories.CustomUserRepoImpl;
import com.revature.springbootdemo.beans.repositories.LocationRepo;
import com.revature.springbootdemo.beans.repositories.ReviewRepo;
import com.revature.springbootdemo.beans.repositories.UserRepo;
import com.revature.springbootdemo.beans.utils.FileLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@RestController
@RequestMapping("/controller")
public class UserController {

    public final UserRepo userRepo;
    public final ReviewRepo reviewRepo;
    public final LocationRepo locationRepo;
    static FileLogger fileLogger;

    @PersistenceContext
    EntityManager entityManager;
    EntityManagerFactory emf;

    private static String PropertiesPath = "src/main/resources/Keys.properties"; //keys properties file
    private static String Logpath = "logs/" + LocalDate.now();

    @Autowired
    public UserController(UserRepo userRepo, ReviewRepo reviewRepo, LocationRepo locationRepo) {
        this.userRepo = userRepo;
        this.reviewRepo = reviewRepo;
        this.locationRepo = locationRepo;
        fileLogger = FileLogger.getFileLogger();
        fileLogger.log("UserRepo started: " + LocalDateTime.now());
    }


    //register new user
    @ResponseBody
    @PostMapping(
            value = "/register",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> RegisterNewUser(@RequestBody UserModel newUser) {
        try {
            UserModel user = userRepo.save(newUser);

        }catch (Exception e){
            fileLogger.log(e);
            return ResponseEntity.badRequest().body("{\"result\":false}");
        }

        return ResponseEntity.accepted().body("{\"result\":true}");


    }

    @ResponseBody
    @PostMapping(
            value = "/login",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> Login(@RequestBody SessionModel userSession, HttpServletRequest request) {


        //verify user exists
        CustomUserRepoImpl CustomRepoImp = new CustomUserRepoImpl();
        UserModel u = CustomRepoImp.findByName(userSession.getEmail(), userSession.getPassword());
        try {
            if (u != null) {
                //returns all users registered and return them
                List<UserModel> users = userRepo.findAll();
                String jsonText = "";
                for (UserModel r : users) {
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(r);
                    jsonText += jsonString;
                }
                //create a new session and session variable
                HttpSession session = request.getSession();
                session.setAttribute("Email", u.getEmail());
                session.setAttribute("ID", u.getID());
                session.setAttribute("location_id", userSession.getLocation_id());
                session.setMaxInactiveInterval(50000);
                fileLogger.log("a new session has been created for user with ID: " + u.getID() + " and email " + userSession.getEmail() + ". creation time is: " +
                        session.getCreationTime() + " session inactive Interval is: 50000");
                return ResponseEntity.accepted().body("{\"result\":true}");
            } else
            return ResponseEntity.badRequest().body("{\"result\":false}");
        } catch (Exception exc) {
            fileLogger.log(exc);
        }
        return ResponseEntity.badRequest().body("{\"result\":false}");
    }




    /***
     *  api: for cities information. retrieve city name from the CityForm and returns information about it (longitude, latitude, population, etc)
     * @param cityName
     * @param request
     * @return returns all reviews about that city as well
     *               return a new form html element to allow the logged in user to create a comment about the city
     */
    @GetMapping("/api")
    public String useAPI(@RequestParam(value = "myCity", defaultValue = "San Francisco") String cityName, HttpServletRequest request) {
        String result = "Results:\n";
        String WelcomeMessage = "Welcome ";
        String CityInfo = "Displaying information about \"" + cityName + "\" city\n";

        //Location information (assigned from Ninja City API and persisted in database)
        String city = "", Country = "", state = "";
        double latitude = 0, longitude = 0;
        int population = 0;
        boolean is_capital = false;

        URL url = null;

        //*********** the API keys *****************
        String NinjaCityKey = ""; //ninjacity city key
        String MapKey = ""; //google map keygit
        List<String> keys = ReadKeys();
        if (keys != null) {
        }else {
            fileLogger.log("Error in reading key's property file");
            return "Error in reading key's property file";
        }

        //the two keys assigned from a property file
        NinjaCityKey = keys.get(0);
        MapKey = keys.get(1);

        try {
            cityName = cityName.replace(" ", "%20");
            url = new URL("https://api.api-ninjas.com/v1/city?name=" + cityName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");

            //add headers
            Map<String, String> headers = new HashMap<>();
            headers.put("X-Api-Key", NinjaCityKey);
            headers.put("content-type", "application/json");
            for (String headerKey : headers.keySet()) {
                connection.setRequestProperty(headerKey, headers.get(headerKey));
            }


            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseStream);
            CityInfo += root.path("fact").asText();
            CityInfo += root.asText();
            CityInfo += root.toString();

            //manual extraction of City JSON object keys and values
            String JSON = CityInfo;
            if (CityInfo.indexOf(",") != -1) {
                //get all "key":"value" pairs as an array
                String[] elements =
                        CityInfo.replace("[", "").replace("{", "").replace("}", "").replace("]", "").split(",");
                city = elements[0].split(":")[1];
                latitude = Double.parseDouble(elements[1].split(":")[1]);
                longitude = Double.parseDouble(elements[2].split(":")[1]);
                Country = elements[3].split(":")[1];
                population = Integer.parseInt(elements[4].split(":")[1]);
                is_capital = Boolean.parseBoolean(elements[5].split(":")[1]);
            }

        } catch (MalformedURLException e) {
            fileLogger.log(e);
        } catch (IOException e) {
            fileLogger.log(e);
        }


        //read the email and the id of the currently logged-in user
        //create a session variable
        HttpSession session = request.getSession(false);
        if (session != null) {
            String UserEmail = ((String) session.getAttribute("Email"));
            int UserId = ((Integer) session.getAttribute("ID"));
            fileLogger.log("active session. Logged in user has ID: " + UserId + " and email: " + UserEmail + ". creation time is: " +
                    session.getCreationTime());
            WelcomeMessage = "Welcome " + UserEmail + " to the TravelBuddy!";
        } else {
            fileLogger.log("you must be logged in before searching a city");
            return "you must be logged in before searching a city";

        }

        String LocationAddedResult = "Location:\n";

        List<LocationModel> list = locationRepo.findAll();
        boolean found = false;
        for (LocationModel li : list) {
            if (li.getname().equals(city)) {
                //add the Location_id to the current session. store the location_id in the session
                session = request.getSession(false);
                if (session != null) {
                    session.setAttribute("location_id", li.getLocationID());
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            LocationModel l = new LocationModel(city, Country, state, latitude, longitude, population, is_capital);
            LocationModel newLocation = locationRepo.save(l);
            LocationAddedResult = String.format("have added the location %s and %s and %s and %s and %s and %s and %s " +
                    " successfully", newLocation.getLocationID(), city, Country, state, latitude, longitude, population, is_capital);
            //add the Location_id to the current session. store the location_id in the session
            session = request.getSession();
            if (session != null) {
                session.setAttribute("location_id", newLocation.getLocationID());
            }
        }
        result = WelcomeMessage + "\n<hr>" + CityInfo + "\n<hr>" + LocationAddedResult + "\n<hr>";
        return result;
    }


    /***
     * Delete user (not implemented yet)
     * @param Password
     * @param Email
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String DeleteUser(@RequestParam(value = "myPassword", defaultValue = "1111") String Password,
                             @RequestParam(value = "myEmail", defaultValue = "user@gmail.com") String Email) {

        UserModel user = new UserModel("", "", Password, Email);
        userRepo.delete(user);
        return String.format("user has been deleted. user email was %s and password was %s", Password, Email);

    }

    //search method, retrieve all search results 
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public List<Object> Search(@RequestParam(value = "userEnteredCity") String userEnteredCity, HttpServletRequest request){
        List<Object> resultList = new ArrayList<>();
        HttpSession session = null;
        String LocationAddedResult = null;
        String result = null;
        LocationModel newLocation = null;

        try {

            ObjectMapper mapper = new ObjectMapper();

            InputStream stream = CityAPIService.getCityInfo(userEnteredCity);
            List<LocationModel> locationModelList = Arrays.asList(mapper.readValue(stream, LocationModel[].class));
            //serach for the city in the db
            List<LocationModel> list = locationRepo.findAll();
            boolean found = false;
            for (LocationModel li : list) {
                if (li.getname().equals(userEnteredCity)) {
                    //add the Location_id to the current session. store the location_id in the session
                    session = request.getSession(false);
                    if (session != null) {
                        session.setAttribute("location_id", li.getLocationID());
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                LocationModel l = new LocationModel(locationModelList.get(0).getname(),
                        locationModelList.get(0).getCountry(),
                        locationModelList.get(0).getState(),
                        locationModelList.get(0).getLatitude(),
                        locationModelList.get(0).getLongitude(),
                        locationModelList.get(0).getPopulation(),
                        locationModelList.get(0).getIs_capital());
                newLocation = locationRepo.save(l);
                //add the Location_id to the current session. store the location_id in the session
                session = request.getSession();
                if (session != null) {
                    session.setAttribute("location_id", newLocation.getLocationID());
                }
            }

            stream = WeatherAPIService.getCityWeather(userEnteredCity);
            WeatherAPIModel weatherAPIModel = mapper.readValue(stream, WeatherAPIModel.class);
            resultList.add(weatherAPIModel);

            stream = CountryAPIService.getCountryInfo(locationModelList.get(0).getCountry());
            List<CountryAPIModel> countryAPIModels = Arrays.asList(mapper.readValue(stream, CountryAPIModel[].class));
            resultList.add(countryAPIModels.get(0));

            if (!found){
                resultList.add(newLocation);
               List<ReviewsModel> reviewsModelList = GetAllReviews(newLocation.getLocationID(), request);
               for (ReviewsModel model : reviewsModelList){
                   resultList.add(model.toString());
               }
            }else{
                resultList.add(locationRepo.getById((Integer) session.getAttribute("location_id")));
                List<ReviewsModel> reviewsModelList = GetAllReviews((Integer) session.getAttribute("location_id"), request);
                for (ReviewsModel model : reviewsModelList){
                    resultList.add(model.toString());
                }
            }

            return resultList;

        } catch (IOException e) {
            fileLogger.log(e);
        }

        return null;

    }

    public List<ReviewsModel> GetAllReviews(Integer id,HttpServletRequest request ) {

        boolean hasReviews = false;

        List<ReviewsModel> reviews = reviewRepo.findAll();

        HttpSession session = request.getSession(false);
        if (session != null) {

            int UserId = ((Integer) session.getAttribute("ID"));
            int LocationId = ((Integer) session.getAttribute("location_id"));

            //get location information
            List<LocationModel> locs = locationRepo.findAll();
            for (LocationModel l : locs) {
                if (l.getLocationID() == LocationId) {
                    break;
                }
            }

            List<UserModel> users = userRepo.findAll();
            for (UserModel u : users) {
                if (u.getID() == UserId) {
                    break;
                }
            }
            
            return reviews;
        } else {
            fileLogger.log("You must be logged in to view reviews");
            return null;
        }
    }
    
    /***
     * method ReadKeys() helper method: read keys of Ninja City API and Google Maps API from the "Keys.properties" file
     * parameters: none
     * @return: a list of two values, the first is the Ninja City API keys and the second is the Google Map API
     */
    public List<String> ReadKeys() {
        try {
            Properties props = new Properties();
            FileReader fr = new FileReader(PropertiesPath);
            ClassLoader cl = SpringBootDemoApplication.class.getClassLoader();
            try (InputStream stream = cl.getResourceAsStream("Keys.properties")) {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(PropertiesPath));
                props.load(bis);
                String NinjaKey = props.getProperty("NinjaKey");
                String MapKey = props.getProperty("MapKey");
                ArrayList<String> keys = new ArrayList<String>();
                keys.add(NinjaKey);
                keys.add(MapKey);
                return keys;
            } catch (Exception exc) {
                fileLogger.log(exc);
            }
        } catch (FileNotFoundException e) {
            fileLogger.log(e);
        }
        return null;
    }
}