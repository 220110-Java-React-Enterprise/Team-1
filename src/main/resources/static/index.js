// Setting up event listeners for the index page
window.onload = (event) => {
	// submit button handler
	const searchButtons = document.querySelectorAll(`[id^="button-search"]`);
	for (let i = 0; i < searchButtons.length; i++) {
		searchButtons[i].addEventListener("click", (event) => {
			// TODO: Take out preventDefault if we want the form to be submitted (this is debug) *********
			event.preventDefault();

			// Identify which search box 1 or 2 is it
			let idName = "searchbox" + event.target.id.slice(-1);
			const searchTerm = document.getElementById(idName).value;
			console.log("Searching for: " + searchTerm);
			if (event.target.id.slice(-1) == 2) {
				console.log("2!");
				doTakeoffSmol();
			} else {
				console.log("1!");
				doTakeoff();
			}
			getData(searchTerm);
		});
	}

	// Add click handlers to temperature control
	const tempControls = document.getElementsByClassName("temp-control");
	for (let i = 0; i < tempControls.length; i++) {
		tempControls[i].addEventListener("click", (event) => {
			const tempTypeNodes = document.getElementsByClassName("weather-type");

			if (
				event.target.textContent === "Farenheit" &&
				tempTypeNodes[0].textContent === "C"
			) {
				// get all temps - node ID's should all start with weather-temp-*
				const tempValueNodes =
					document.querySelectorAll(`[id^="weather-temp"]`);
				for (let k = 0; k < tempValueNodes.length; k++) {
					let newTemp = ((tempValueNodes[k].textContent / 5) * 9 + 32).toFixed(
						1
					);
					tempValueNodes[k].textContent = newTemp;
				}

				// Change all temp types to F
				for (let j = 0; j < tempTypeNodes.length; j++) {
					tempTypeNodes[j].textContent = "F";
				}
			}
			// Celcius was clicked
			else if (
				event.target.textContent === "Celsius" &&
				tempTypeNodes[0].textContent === "F"
			) {
				// get all temps - node ID's should all start with weather-temp-*
				const tempValueNodes =
					document.querySelectorAll(`[id^="weather-temp"]`);
				for (let k = 0; k < tempValueNodes.length; k++) {
					let newTemp = (
						((tempValueNodes[k].textContent - 32) * 5) /
						9
					).toFixed(1);
					tempValueNodes[k].textContent = newTemp;
				}

				// Change all temp types to F
				const tempTypeNodes = document.getElementsByClassName("weather-type");
				for (let j = 0; j < tempTypeNodes.length; j++) {
					tempTypeNodes[j].textContent = "C";
				}
			}
		});
	}

	// listeners for login/registration
	document
		.getElementById("button-register")
		.addEventListener("click", (event) => {
			event.preventDefault();
			const user = {
				firstName: document.getElementById("first-name").value,
				lastName: document.getElementById("last-name").value,
				email: document.getElementById("email").value,
				//username: document.getElementById("username").value,
				password: document.getElementById("password").value,
			};
			console.log(user);
			doRegister(user);
		});
	document.getElementById("button-login").addEventListener("click", (event) => {
		event.preventDefault();
		const user = {
			email: document.getElementById("login-email").value,
			password: document.getElementById("login-password").value,
		};
		doLogin(user);
		console.log(user);
	});

	// listener for posting a review
	document
		.getElementById("button-review")
		.addEventListener("click", (event) => {
			event.preventDefault();
			//const reviewText = event.target.value;
			const reviewText = document.getElementById("review-input-box").value;
			console.log("Comment: " + reviewText);
		});

	// unused - this tells you which review was reported
	const reportButtons = document.getElementsByClassName("button-report");

	for (let i = 0; i < reportButtons.length; i++) {
		reportButtons[i].addEventListener("click", (event) => {
			event.preventDefault();
			const messageNumber = event.target.id.slice(7);
			const messageText = document.getElementById(
				"review-" + messageNumber
			).innerText;
			console.log("message #" + messageNumber + " to be reported");
			console.log(messageText);
		});
	}
};

// Triggers takeoff animation for Buddy Sr.
function doTakeoff() {
	document.getElementsByTagName("body")[0].style.overflowX = "hidden";
	const buddyLogoNode = document.getElementById("buddy-logo");
	buddyLogoNode.classList.add("takeoff-animation");
	// Make sure this is after the animation
	setTimeout(removeSplash, 2500);
}

// Triggers takeoff animation for Buddy Jr.
function doTakeoffSmol() {
	document.getElementsByTagName("body")[0].style.overflowX = "hidden";
	const buddyLogoNode = document.getElementById("buddy-logo-smol");
	buddyLogoNode.classList.add("takeoff-animation-smol");
	// Make sure this is after the animation
	setTimeout(resetBuddySmol, 2500);
}

//  Triggers when user clicks register / login
function doSignin() {
	// For testing - sessionStorage, but for actual use - localStorage
	//sessionStorage.setItem("email");
	hideLoginOverlay();
	document.getElementById("overlay").classList.add("hidden");
	document.getElementById("overlay-login").classList.remove("flexed");
	document.getElementById("overlay-login").classList.add("hidden");
	//document.getElementById("review-write-container").display = "block";
}

// If we add some sort of sign out option, this will do stuff
function doSignOut() {
	// same as dosignin
	showLoginOverlay();
}

// Shows the login overlay
function showLoginOverlay() {
	console.log("Login overlay displayed");
	const loginNode = document.getElementById("login-container");
	loginNode.classList.remove("hidden");
	loginNode.classList.add("login-boxes-visible");
}

// Hides the login overlay
function hideLoginOverlay() {
	//TODO: don't forget to remove this later - this is for testing *****************************

	console.log("Login overlay hidden");
	document
		.getElementById("login-container")
		.classList.remove("login-boxes-visible");
	document.getElementById("login-container").classList.add("hidden");

	// reveal comment section
	document.getElementById("review-write-container").classList.remove("hidden");
	//document.getElementById("review-write-container")
	//review - write - container;
}

// This is a callback function used after the first animation completes
function removeSplash() {
	// Undo the hiding from the animation
	document.getElementsByTagName("body")[0].style.overflowX = "visible";

	// set main to .data-layout-container
	document.getElementsByTagName("main")[0].classList.remove("splash-layout");
	document
		.getElementsByTagName("main")[0]
		.classList.add("data-layout-container");

	// hide #splash-search
	document.getElementById("splash-search").style.display = "none";

	// remove 100% height from html tag
	// this is required to fix vertical overflow in result layout
	document.getElementsByTagName("html")[0].style.height = "auto";

	// remove hidden from .result-page
	const resultNodes = document.getElementsByClassName("result-page");
	for (let i = 0; i < resultNodes.length; i++) {
		resultNodes[i].classList.remove("hidden");
	}
}

// Callback function used after second animation and resets position
function resetBuddySmol() {
	// Undo the hiding from the animation
	document.getElementsByTagName("body")[0].style.overflowX = "visible";

	// remove the flight animation class to prepare for the next press
	const buddyLogoNode = document.getElementById("buddy-logo-smol");
	buddyLogoNode.classList.remove("takeoff-animation-smol");
}

// Makes the api call to our backend when search hit
async function getData(city) {
	//localhost:8080/register/search?userEnteredCity=Pasadena
	const url = "http://localhost:8080/controller/search?userEnteredCity=" + city;

	let stuff;
	try {
		let promise = await fetch(url, {
			method: "GET",
			headers: {
				"Content-Type": "application/json",
			},
		})
			.then((response) => response.json())
			.then((result) => (stuff = result));
	} catch (error) {
		console.log("Error: \n" + error);
		//console.log("Response: \n" + response);
	}
	console.log("raw: ");
	console.log(stuff);
	//console.log(result);
	console.log("First element: ");
	console.log(stuff[0]);
	console.log("Second element: ");
	console.log(stuff[1]);
	let weather_data = stuff[0];
	let country_data = stuff[1];
	let city_data = stuff[2];

	populate("city-header", city + ", " + country_data.name);
	populate("statistics-population", city_data.population);
	populate("statistics-currency", city_data.currency);
	populate("statistics-gdp", country_data.gdp);
	populate("statistics-unemployment", country_data.unemployment);
	populate("statistics-crime-rate", country_data.homicide_rate);
	populate("weather-temp", weather_data.temp);
	populate("weather-temp-min", weather_data.min_temp);
	populate("weather-temp-max", weather_data.max_temp);
	populate("weather-cloud", weather_data.cloud_pct);
	populate("weather-temp-feels", weather_data.feels_like);
	populate("weather-humidity", weather_data.humidity);
	populate("weather-wind-speed", weather_data.wind_speed);
	populate("weather-wind-degrees", weather_data.wind_degrees);
	/* console.log(
		`latitude: ${city_data.latitude} longitude: ${city_data.longitude}`
	);
	console.log(city_data); */
	updateMap(city_data.latitude, city_data.longitude);
}

// Helper function to add values
function populate(idName, value) {
	document.getElementById(idName).textContent = value;
}

// Makes api call to do login
async function doLogin(user) {
	//localhost:8080/register/search?userEnteredCity=Pasadena
	const url = "http://localhost:8080/controller/login";

	let stuff;
	try {
		let promise = await fetch(url, {
			method: "POST",
			body: JSON.stringify(user),
			headers: {
				"Content-Type": "application/json",
			},
		})
			.then((response) => response.json())
			.then((result) => (stuff = result));
	} catch (error) {
		console.log("Error: \n" + error);
		//console.log("Response: \n" + response);
	}
	console.log("raw result: ");
	console.log(stuff);
	if (stuff.result == true) {
		doSignin();
	} else {
		alert("Sorry, wrong user ID and password!");
	}
}

// Makes api call to do registration
async function doRegister(user) {
	//localhost:8080/register/search?userEnteredCity=Pasadena
	const url = `http://localhost:8080/controller/register`;

	let stuff;
	try {
		let promise = await fetch(url, {
			method: "POST",
			body: JSON.stringify(user),
			headers: {
				"Content-Type": "application/json",
			},
		})
			.then((response) => response.json())
			//.then((response) => console.log(response))
			.then((result) => (stuff = result));
	} catch (error) {
		console.log("Error: \n" + error);
		//console.log("Response: \n" + response);
	}
	console.log("raw result: ");
	console.log(stuff);

	// is this valid?
	if (stuff) {
		doSignin();
	} else {
		alert("Sorry, registration failed!");
	}
}

// Updates the google map, hopefully
function updateMap(latitude, longitude) {
	const mapNode = document.getElementById("google-map");
	// src="https://www.google.com/maps/embed/v1/view?zoom=10&center=40.7128%2C-74.0060&key=AIzaSyD74AJ6FnwjE0_qnAkhIEwRuIGYLPh1R1I
	let mapSrc = mapNode.src;
	console.log(mapSrc);
	mapSrc.contentWindow.location.reload();
}
