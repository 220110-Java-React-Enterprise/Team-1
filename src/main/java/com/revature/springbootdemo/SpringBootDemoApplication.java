package com.revature.springbootdemo;

import com.fasterxml.jackson.databind.JsonNode;
import com.revature.springbootdemo.beans.models.UserModel;
import com.revature.springbootdemo.beans.repositories.UserRepo;
import com.revature.springbootdemo.beans.utils.ApplicationContextProvider;
import com.revature.springbootdemo.beans.utils.FileLogger;
import org.apache.coyote.Response;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.MalformedURLException;
import java.util.*;

//for api
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages={"com.revature.springbootdemo.beans"})
//@RestController
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SpringBootDemoApplication {




	static FileLogger fileLogger;
	private static String PropertiesPath = "src/main/resources/Keys.properties";
	static UserRepo userRepo;
	//C:\Users\ahmed\IdeaProjects\Ahmad\Team-1\src\main\resources\Keys.properties


	//private UsersDAO userRepository;

	/*** METHODS ****
	 */
	//main method: print a testing message, configure the port of the server to 8080
	public static void main(String[] args) {
		//System.out.println("printing");
		SpringApplication app = new SpringApplication(SpringBootDemoApplication.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.port", "8080"));
		InitializeLogger(); //initialize fileLogger variable.
		app.run(args);

		/**
		 * David's user persistence check
		 *
		 *
		 * */

////		SpringApplication.run(SpringBootDemoApplication.class, args);
//		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
//		userRepo = context.getBean(UserRepo.class);
//
//		UserModel user = new UserModel("David", "Alvarado", "abc123", "fake123@gmail.com");
//		userRepo.save(user);


	}

	public static void InitializeLogger()
	{
		fileLogger = FileLogger.getFileLogger();
	}

	//login method, get username and password and verify they exists
//	@GetMapping ("/register")
//	@ResponseBody
//	public String Login(@RequestParam(value = "myFirstname", defaultValue = "userFirst") String FirstName,
//						@RequestParam(value = "myLastname", defaultValue = "userLast") String LastName,
//						@RequestParam(value = "myPassword", defaultValue = "1111") String Password,
//						@RequestParam(value = "myEmail", defaultValue = "user@gmail.com") String Email) {
//
//		UserModel user = new UserModel(FirstName, LastName, Password, Email);
//		userRepo.save(user);
//		return String.format("have added the user  %s and %s and %s nad %s successfully", FirstName, LastName, Password, Email);
//
//		//Response r = new Response();
//		//r.setMessage("testing");
//	}

	//login method, get username and password and verify they exists
	@GetMapping ("/login")
	@ResponseBody
	public String Login(@RequestParam(value = "myUsername", defaultValue = "user1") String userName,
						@RequestParam(value = "myPassword", defaultValue = "password1") String Password) {
		return String.format("Hello %s and %s", userName, Password);
		//Response r = new Response();
		//r.setMessage("testing");
	}

	public List<String> ReadKeys()
	{
		try {
			Properties props = new Properties();
			FileReader fr = new FileReader(PropertiesPath);
			ClassLoader cl = SpringBootDemoApplication.class.getClassLoader();
			try (InputStream stream = cl.getResourceAsStream("Keys.properties")) {
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(PropertiesPath));
				props.load(bis);
				String NinjaKey = props.getProperty("NinjaKey");
				String MapKey = props.getProperty("MapKey");
				ArrayList<String> keys  =new ArrayList<String>();
				keys.add(NinjaKey);
				keys.add(MapKey);
				return keys;
			}
			catch(Exception exc)
			{
				System.out.println("Error in reading Kye's properties file: " + exc.getMessage());
				fileLogger.log(exc);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	//api: for cities information. get city name from the CityForm and returns information about it (longitude, latitude, population, etc)
	@GetMapping ("/api")
	public String useAPI(@RequestParam(value = "myCity", defaultValue = "San Francisco") String cityName) {
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
		MapKey = keys.get(1);

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
			//String filepath = "data.html";
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
		//result += mapHTML;
		//*****************
		//return result + mapHTML;

		//***** For testing *********
		FilePath= new File("result.html").getAbsolutePath();
		File f = new File(FilePath);
		try {
			//String filepath = "data.html";
			FileWriter sw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(sw);
			bw.write(mapHTML);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Response r= new Response();
		//r.setMessage("done");
		r.setMessage(mapHTML);
		return mapHTML;
	}


	//hello: test reading/getting a value from form field and displaying it in a message
	@GetMapping ("/hello")
	public String sayHello(@RequestParam(value = "myName", defaultValue = "world") String name) {
		return String.format("Hello %s!", name);
	}

}