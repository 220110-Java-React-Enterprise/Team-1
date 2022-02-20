package com.revature.springbootdemo.beans.Controller;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.springbootdemo.SpringBootDemoApplication;
import com.revature.springbootdemo.beans.models.*;
import com.revature.springbootdemo.beans.repositories.CustomUserRepoImpl;
import com.revature.springbootdemo.beans.repositories.UserRepo;
import com.revature.springbootdemo.beans.utils.FileLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


@RestController
@RequestMapping("/register")
public class UserController {

    public  final UserRepo userRepo;
    public final FileLogger fileLogger;
    public final String propertiesPath = "src/main/resources/api-keys.properties";
    public List<CityAPIModel> tempCities = null;
    // List of arrays is used because of the way the API sends its information.
    public List<StateAPIModel[]> tempStates = null;

    @Autowired
    public  UserController(UserRepo userRepo, FileLogger fileLogger)
    {
        this.userRepo = userRepo;
        this.fileLogger = fileLogger;
    }

    //register new user
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String persistNewUserToDB(@RequestParam(value = "myFirstname", defaultValue = "userFirst") String FirstName,
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



//I can retrieve cities and map them with state...
// need to work on 1 method that checks if there are multiple cities with the same name
// and send back the states with each city so user can select which specific city they are talking about.









/**
 * Accepts city get http request but if there are too many
 * cities with the same name, returns STATES that the city
 * belongs to.
 * */




    @GetMapping("/getallinfo")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public String getAllInfo(@RequestParam(value = "myCity") String cityName){
        // check if multiple results exist, if so return results so user can click on the the specific one they want.

        String resultJSON = "";
        List<CityAPIModel> cityList = getFromCityAPI(cityName);
        if (cityList.size() > 1){
        matchStatesWithCity();
        for (int i = 0; i < cityList.size(); i++){
            resultJSON += cityList.get(i).toString();
            if (tempStates.get(i) == null) {
                resultJSON += "";
            }else {
                resultJSON += tempStates.get(i)[0].toString();
            }
        }
        }
        return resultJSON;
    }






//Scenario where city and state are known... and both variables in class are initialized (tempcity and tempstate)

    @GetMapping("/getwithcityandstate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public String getAllKnownInfo(@RequestParam(value = "myCity") String cityName, @RequestParam(value="myState") String stateName){
        StateAPIModel currentState = null;
        String json = "";
        if(tempStates != null){
            for(int i = 0; i < tempStates.size(); i++){
                if (tempStates.get(i)[0].getState().equals(stateName) & tempStates.get(i)[0].getName().equals(cityName)){
                    currentState = tempStates.get(i)[0];
                    break;
                }
            }
        } else{



        }
        json += currentState.toString();
        json += getWeatherInfoUsingLatandLong(currentState.getLatitude(), currentState.getLongitude());
        json+= getCountryStats(currentState.getCountry());


        return json;
    }



public String getCountryStats(String countryName){
    String ninjaKey = ReadKeys().get(0);
    URL url;
    CountryAPIModel[] countryStats = null;

    //StateAPIModel[] jsonHolder = null;
    //tempStates = new LinkedList<>();

    try {
        url = new URL("https://api.api-ninjas.com/v1/country?name=" + countryName);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");

        //adding headers to our url request like the api key and how we want the content.
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Api-Key", ninjaKey);
        headers.put("content-type", "application/json");
        for (String headerKey : headers.keySet()) {
            connection.setRequestProperty(headerKey, headers.get(headerKey));
        }
        InputStream responseStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        countryStats = mapper.readValue(responseStream, CountryAPIModel[].class);

    } catch (IOException e) {
        e.printStackTrace();
    }

    System.out.println(countryStats[0].toString());
    return countryStats[0].toString();
}



//
 public String getWeatherInfoUsingLatandLong(String latitude, String longitude){
        String ninjaKey = ReadKeys().get(0);
        URL url;
        WeatherAPIModel weather = null;

     //StateAPIModel[] jsonHolder = null;
        //tempStates = new LinkedList<>();

     try {
         url = new URL("https://api.api-ninjas.com/v1/weather?lat=" +
                 latitude+"&lon=" + longitude);
         HttpURLConnection connection = (HttpURLConnection) url.openConnection();
         connection.setRequestProperty("accept", "application/json");

         //adding headers to our url request like the api key and how we want the content.
         Map<String, String> headers = new HashMap<>();
         headers.put("X-Api-Key", ninjaKey);
         headers.put("content-type", "application/json");
         for (String headerKey : headers.keySet()) {
             connection.setRequestProperty(headerKey, headers.get(headerKey));
         }
         InputStream responseStream = connection.getInputStream();
         ObjectMapper mapper = new ObjectMapper();
         weather = mapper.readValue(responseStream, WeatherAPIModel.class);

     } catch (IOException e) {
         e.printStackTrace();
     }

     System.out.println(weather.toString());
     return weather.toString();
 }








    /**
     * Retrieves and sends all cities that have the same name.
     * @param cityName
     * @return String to webpage
     */
    @GetMapping("/citystateinfo")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public String retrieveAllCities(@RequestParam(value = "myCity") String cityName){
        String resultString = null;
        List<CityAPIModel> objectListResult = getFromCityAPI(cityName);
        for(CityAPIModel object : objectListResult){
            resultString += object.toString();
        }
        System.out.println(resultString);
;       return resultString;
    }












    public List<StateAPIModel[]> matchStatesWithCity (){
        //function returns one API key at this time, therefore it we retrieve it from index = 0.
        String ninjaKey = ReadKeys().get(0);
        URL url;
        StateAPIModel[] jsonHolder = null;
        tempStates = new LinkedList<>();
        try {
            // building string for city ninja api.
            for (int i = 0; i < tempCities.size(); i++) {
                url = new URL("https://api.api-ninjas.com/v1/reversegeocoding?lat=" +
                                    tempCities.get(i).getLatitude()+"&lon=" +
                                    tempCities.get(i).getLongitude());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("accept", "application/json");

                //adding headers to our url request like the api key and how we want the content.
                Map<String, String> headers = new HashMap<>();
                headers.put("X-Api-Key", ninjaKey);
                headers.put("content-type", "application/json");
                for (String headerKey : headers.keySet()) {
                    connection.setRequestProperty(headerKey, headers.get(headerKey));
                }
                InputStream responseStream = connection.getInputStream();
                ObjectMapper mapper = new ObjectMapper();
                jsonHolder = mapper.readValue(responseStream, StateAPIModel[].class);
                if (jsonHolder == null){
                    System.out.println("JsonHolder is null");
                }else{
                    jsonHolder[0].setLatitude(tempCities.get(i).getLatitude());
                    jsonHolder[0].setLongitude(tempCities.get(i).getLongitude());

                    tempStates.add(jsonHolder);

                }

            }


        }catch (IOException e){
            e.printStackTrace();

        }

        return tempStates;

    }

















    /**
     * Retrieves all cities from the city API hosted by ninja-API (limit 30 on results)
     * Method is not directly accessible from a HTTP request. To see this information through
     * the use of an HTTP request, please use retrieveAllStates method by sending an http request
     * to /register/citystateinfo.
     * @param cityName
     * @return Returns a list of objects of type CityAPIModel who share/contain the same city name.
     */
    public List<CityAPIModel> getFromCityAPI(String cityName){
    String result = "displaying information about \"" + cityName +"\" city";
    //function returns one API key at this time, therefore it we retrieve it from index = 0.
    String ninjaKey = ReadKeys().get(0);
    URL url;
    List<CityAPIModel> listOfCities = null;
    try {
        // building string for city ninja api.
        url = new URL("https://api.api-ninjas.com/v1/city?name=" + cityName + "&limit=30");
        HttpURLConnection connection = (HttpURLConnection)  url.openConnection();
        connection.setRequestProperty("accept", "application/json");

        //adding headers to our url request like the api key and how we want the content.
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Api-Key", ninjaKey);
        headers.put("content-type", "application/json");
        for (String headerKey : headers.keySet()) {
            connection.setRequestProperty(headerKey, headers.get(headerKey));
        }
        //Retrieving info from api.

        InputStream responseStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();

        //This list holds objects of type LocationAPIModel
        listOfCities = Arrays.asList(mapper.readValue(responseStream, CityAPIModel[].class));
//        for (int i = 0; i < listOfCities.size(); i++){
//            System.out.println(listOfCities.get(i).toString());
//        }

    }catch (IOException e){
        e.printStackTrace();

    }

    tempCities = listOfCities;
    return listOfCities;
}


















    //api: for cities information. get city name from the CityForm and returns information about it (longitude, latitude, population, etc)
    @GetMapping ("/api")
    public String useAPI(@RequestParam(value = "myCity", defaultValue = "Houston") String cityName) {
        String result = "displaying information about \"" + cityName +"\" city";
        //****** api **********
        URL url = null;

        //*********** the API keys *****************
        String NinjaCityKey = ""; //ninjacity city key
        String MapKey = ""; //google map keygit
        List<String> keys = ReadKeys();
        if (keys != null)
            System.out.println("success reading keys");
        else
        {
            fileLogger.log("Error in reading key's property file");
            System.out.println("Error in reading key's property file");
            return "Error in reading key's property file";
        }

        NinjaCityKey = keys.get(0);
        //MapKey = keys.get(1);

        try {
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
            result += root.path("fact").asText();
            result += root.asText();
            result += root.toString();
            System.out.println(result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String FilePath= new File("data.html").getAbsolutePath();
        try {
            File f = new File(FilePath);
            FileWriter sw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(sw);
            bw.write("<h1>Data about " + cityName +"</h1></br>" + result);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String mapHTML =
                "<frameset rows = \"30%,70%\">" +
                        "<frame" + " width=\"100%\"" + " height=\"250\"" + " frameborder=\"0\" style=\"border:0\"" +
                        " src=\"https://www.google.com/maps/embed/v1/place?key=" + MapKey + "&q=" + cityName + "\" allowfullscreen />" +
                        "<iframe " +
                        " srcdoc=\"" + "<h1>Data about " + cityName +"</h1></br>" + result.replaceAll("\"", "") + "\" />" +
                        "<noframes>" +

                        "<body>Your browser doens't support frames</body>" +
                        "</noframes>" +
                        "</frameset>";



        FilePath= new File("result.html").getAbsolutePath();
        File f = new File(FilePath);
        try {
            FileWriter sw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(sw);
            bw.write(mapHTML);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }












    public List<String> ReadKeys()
    {
        try {
            Properties props = new Properties();
            FileReader fr = new FileReader(propertiesPath);
            ClassLoader cl = SpringBootDemoApplication.class.getClassLoader();
            try (InputStream stream = cl.getResourceAsStream("api-keys.properties")) {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(propertiesPath));
                props.load(bis);
                String NinjaKey = props.getProperty("ninja-api-key");
                //String MapKey = props.getProperty("MapKey");
                ArrayList<String> keys  =new ArrayList<String>();
                keys.add(NinjaKey);
                //keys.add(MapKey);
                return keys;
            }
            catch(Exception exc)
            {
                System.out.println("Error in reading Key's properties file: " + exc.getMessage());
                fileLogger.log(exc);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
