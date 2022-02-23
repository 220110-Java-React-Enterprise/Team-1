// Setting up event listener for the index page
// registration/login prompt on page load
window.onload = (event) => {
	console.log("Page is loaded.. register?");
	// submit button handler
	const searchButtons = document.getElementsByClassName("search-button");
	for (let i = 0; i < searchButtons.length; i++) {
		searchButtons[i].addEventListener("click", (event) => {
			// TODO: Take out preventDefault if we want the form to be submitted (this is debug) *********
			event.preventDefault();
			const searchTerm = document.getElementById("searchbox").value;
			console.log("Searching for: " + searchTerm);
			doTakeoff();
			getData(searchTerm);
		});
	}

	// Add click handlers to temperature control
	const tempControls = document.getElementsByClassName("temp-control");
	for (let i = 0; i < tempControls.length; i++) {
		tempControls[i].addEventListener("click", (event) => {
			console.log(event);
			console.log(`${event.target.textContent} was clicked`);
			if (event.target.textContent === "Farenheit") {
				// get all temps - node ID's should all start with weather-temp-*
				const tempValueNodes =
					document.querySelectorAll(`[id^="weather-temp"]`);
				for (let k = 0; k < tempValueNodes.length; k++) {
					let newTemp = (tempValueNodes[k].textContent * (9 / 5) + 32).toFixed(
						1
					);
					tempValueNodes[k].textContent = newTemp;
				}

				// Change all temp types to F
				const tempTypeNodes = document.getElementsByClassName("weather-type");
				for (let j = 0; j < tempTypeNodes.length; j++) {
					tempTypeNodes[j].textContent = "F";
				}
			}
			// Celcius was clicked
			else if (event.target.textContent === "Celsius") {
				// get all temps - node ID's should all start with weather-temp-*
				const tempValueNodes =
					document.querySelectorAll(`[id^="weather-temp"]`);
				for (let k = 0; k < tempValueNodes.length; k++) {
					let newTemp =
						(tempValueNodes[k].textContent - 32) * (5 / 9).toFixed(1);
					tempValueNodes[k].textContent = newTemp.toFixed(1);
				}

				// Change all temp types to F
				const tempTypeNodes = document.getElementsByClassName("weather-type");
				for (let j = 0; j < tempTypeNodes.length; j++) {
					tempTypeNodes[j].textContent = "C";
				}
			}
		});
	}

	// Add event handler to registration/login buttons to dismiss login screen
	// re-enable this later
	/*
	const loginButtons = document.getElementsByClassName("login-button");
	for (let i = 0; i < loginButtons.length; i++) {
		loginButtons[i].addEventListener("click", hideLoginOverlay);
	} */
	// delay showing login for 3 seconds
	// Should probably add a check if the person has previously logged in here
	//setTimeout(showLoginOverlay, 1500);
};

function doTakeoff() {
	document.getElementsByTagName("body")[0].style.overflowX = "hidden";
	const buddyLogoNode = document.getElementById("buddy-logo");
	buddyLogoNode.classList.add("takeoff-animation");
	// Make sure this is after the animation
	setTimeout(removeSplash, 2400);
}

function showLoginOverlay() {
	console.log("Login overlay displayed");
	const loginNode = document.getElementById("login");
	loginNode.classList.remove("hidden");
	loginNode.classList.add("login-boxes-visible");
}

function hideLoginOverlay() {
	//TODO: don't forget to remove this later - this is for testing *****************************
	event.preventDefault();
	console.log("Login overlay hidden");
	document.getElementById("login").classList.remove("login-boxes-visible");
	document.getElementById("login").classList.add("hidden");
}

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

async function getData(city) {
	//localhost:8080/register/search?userEnteredCity=Pasadena
	const url = "http://localhost:8080/register/search?userEnteredCity=" + city;

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

	populate("city-header", city + ", " + country_data.name);
	//populate("statistics-population", country_data.population);
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
	populate("weather-wind-speed", weather_data.wind_degrees);
}

function populate(idName, value) {
	document.getElementById(idName).textContent = value;
}
