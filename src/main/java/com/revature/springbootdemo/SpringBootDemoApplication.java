package com.revature.springbootdemo;

import com.fasterxml.jackson.databind.JsonNode;
import com.revature.springbootdemo.beans.utils.FileLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
//for the api ...
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages={"com.revature.springbootdemo.beans"})
@RestController
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SpringBootDemoApplication {

	static FileLogger fileLogger;
	private static String PropertiesPath = "src/main/resources/Keys.properties"; //keys properties file
	private static String Logpath = "logs/" + LocalDate.now();


	/*** METHODS ****
	*/
	//main method: print a testing message, configure the port of the server to 8080
	public static void main(String[] args) {
		//System.out.println("printing");
		SpringApplication app = new SpringApplication(SpringBootDemoApplication.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.port", "8080"));
		app.run(args);

		File f = new File(Logpath);
		fileLogger = FileLogger.getFileLogger();
		fileLogger.log("started: " + LocalDateTime.now());
	}


}
