// Setting up event listener for the testing page
window.onload = (event) => {
	console.log("Page is loaded...");
	// submit button handler
	const searchButtons = document.getElementsByClassName("search-button");
	for (let i = 0; i < searchButtons.length; i++) {
		searchButtons[i].addEventListener("click", (event) => {
			// TODO: Take out preventDefault if we want the form to be submitted (this is debug) *********
			event.preventDefault();
			const searchTerm = document.getElementById("searchbox").value;
			console.log("Searching for: " + searchTerm);
			//doTakeoff();
			getData(searchTerm);
		});
	}

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
			email: document.getElementById("login-username").value,
			password: document.getElementById("login-password").value,
		};
		doLogin(user);
		console.log(user);
	});

	document
		.getElementById("button-review")
		.addEventListener("click", (event) => {
			event.preventDefault();
			const reviewText = event.target.value;
			console.log("Comment: " + reviewText);
		});

	const reportButtons = document.getElementsByClassName("button-report");
	// change to for... of?
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
	console.log("Single element test");
	console.log(stuff[0].country);
}

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
	console.log("raw: ");
	console.log(stuff);
}

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
			//.then((response) => response.json())
			.then((response) => console.log(response.text))
			.then((result) => (stuff = result));
	} catch (error) {
		console.log("Error: \n" + error);
		//console.log("Response: \n" + response);
	}
	console.log("raw: ");
	console.log(stuff);
}

/*
	fetch(SWAPI_URL)
		.then(response => response.json())
		.then(result => {
			console.log(result)
		});
	*/
/*
	let response = await fetch(url, {
		method: "GET",
		headers: {
			"Content-Type": "application/json",
		},
	});
	//let text = response.json();
	console.log(response);
	console.log(text.value);
	//console.log(response.json());
	//console.log("Full response:");
	//console.log(response);
	*/
