package com.revature.springbootdemo;

import com.revature.springbootdemo.DAOs.UsersDAO;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Collections;

//for api
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import javax.activation.DataSource;

import static org.junit.Assert.assertThat;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages={"com.revature.springbootdemo","com.revature.springbootdemo.DAOs",
		"com.revature.springbootdemo.utils", "com.revature.springbootdemo.Annotations"})
@RestController
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SpringBootDemoApplication {

	/*
	//@Autowired (required = true)
	@Autowired
	private UserRepository userRepository;

	//Repository
	@Repository
	public interface UserRepository extends CrudRepository<UsersDAO, Long> {
		public List<UsersDAO> findAll();
	}

	@Before
	public void initialize() {
		//mvc = MockMvcBuilders.standaloneSetup(myController).build();
	}


	//configuration is accomplished using a pom.xml and could be also done here
	@Bean
	public DataSource getDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		//dataSourceBuilder.username("SA");
		//dataSourceBuilder.password("");
		return (DataSource) dataSourceBuilder.build();
	}



	@Test
	public void whenCalledSave_thenCorrectNumberOfUsers() {
		userRepository.save(new UsersDAO(9,"Bob", "BobLast", "2222", "bob@domain.com"));
		List<UsersDAO> users = (List<UsersDAO>) userRepository.findAll();
		//assertThat(users.size()).isEqualTo(1);
	}




	//@Bean (type = "com.revature.springbootdemo.SpringBootDemoApplication$UserRepositor")
	public CommandLineRunner run(UserRepository userRepository) throws Exception {
		return (String[] args) -> {
			UsersDAO user1 = new UsersDAO(9,"Bob", "BobLast", "2222", "bob@domain.com");
			UsersDAO user2 = new UsersDAO(9,"Harley", "Harley", "3333", "Harley@domain.com");
			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.findAll().forEach(user -> System.out.println(user));
		};
	}
	*/


	/*
	@RequestMapping(method=RequestMethod.GET)
	public List<UsersDAO> getAllUsers(){
		return (List<UsersDAO>) userRepository.findAll();
	}
*/


	//private UsersDAO userRepository;

	/*** METHODS ****
	*/
	//main method: print a testing message, configure the port of the server to 8080
	public static void main(String[] args) {
		//System.out.println("printing");
		SpringApplication app = new SpringApplication(SpringBootDemoApplication.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.port", "8080"));
		app.run(args);
		//userRepository = new UserRepository();
		//SpringApplication.run(SpringBootDemoApplication.class, args);
	}


	//login method, get username and password and verify they exists
	@GetMapping ("/register")
	@ResponseBody
	public String Login(@RequestParam(value = "myFirstname", defaultValue = "userFirst") String FirstName,
						@RequestParam(value = "LastnameField", defaultValue = "userLast") String LastName,
						@RequestParam(value = "myPassword", defaultValue = "1111") String Password,
						@RequestParam(value = "myEmail", defaultValue = "user@gmail.com") String Email) {
		return String.format("Hello %s and %s and %s nad %s", FirstName, LastName, Password, Email);

		//Response r = new Response();
		//r.setMessage("testing");
	}

	//login method, get username and password and verify they exists
	@GetMapping ("/login")
	@ResponseBody
	public String Login(@RequestParam(value = "myUsername", defaultValue = "user1") String userName,
						@RequestParam(value = "myPassword", defaultValue = "password1") String Password) {
		return String.format("Hello %s and %s", userName, Password);
		//Response r = new Response();
		//r.setMessage("testing");
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

		try {
			String filepath = "data.html";
			File f = new File(filepath);
			FileWriter sw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(sw);
			bw.write("<h1>Data about " + cityName +"</h1></br>" + result);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


		String mapHTML =
				"<frameset cols = \"30%,70%\">" +
				"<iframe" +
				"width=\"450\"" +
				"height=\"250\"" +
				"frameborder=\"0\" style=\"border:0\"" +
				"src=\"https://www.google.com/maps/embed/v1/place?key=" + "MapKey&q=" + cityName + "\" allowfullscreen>" +
				"</iframe>";

						/*
						"<iframe " +
						"width=\"450\"" +
						"height=\"250\"" +
						"src =\"data.html\">" +
						"</iframe>" +
						"</frameset>";
*/
		//result += mapHTML;
		//*****************
		return result + mapHTML;
	}


	//hello: test reading/getting a value from form field and displaying it in a message
	@GetMapping ("/hello")
	public String sayHello(@RequestParam(value = "myName", defaultValue = "world") String name) {
		return String.format("Hello %s!", name);
	}

}
