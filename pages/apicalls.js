


// Setting up event listener for the testing page
window.onload = (event) => {
	console.log("Page is loaded...");
	// submit button handler
	document.getElementById('button-search').addEventListener('click', (event) => {
		// TODO: Take out preventDefault if we want the form to be submitted (this is debug) *********
		event.preventDefault();
		const searchTerm = document.getElementById("searchbox").value;
		console.log("Searching for: " + searchTerm);
		//doTakeoff();
		getData(searchTerm);
	});

	document.getElementById('button-register').addEventListener('click', (event) => {
		event.preventDefault();
		const userInfo = {
			firstName: document.getElementById("first-name").value,
			lastName: document.getElementById("last-name").value,
			email: document.getElementById("email").value,
			username: document.getElementById("username").value,
			password: document.getElementById("password").value
		}
		console.log(userInfo);
	});

	document.getElementById('button-login').addEventListener('click', (event) => {
		event.preventDefault();
		const userInfo = {
			username: document.getElementById("login-username").value,
			password: document.getElementById("login-password").value
		}
		console.log(userInfo);
	});

	document.getElementById('button-review').addEventListener('click', (event) => {
		event.preventDefault();
		const reviewText = event.target.value;
		console.log("Comment: " + reviewText);
	});

	const reportButtons = document.getElementsByClassName("button-report");
	// change to for... of?
	for(let i=0; i<reportButtons.length; i++) {
		reportButtons[i].addEventListener("click", (event) => {
			event.preventDefault();
			const messageNumber = event.target.id.slice(7);
			const messageText = document.getElementById("review-" + messageNumber).innerText;
			console.log("message #" + messageNumber + " to be reported");
			console.log(messageText);
		});
	}

	document.getElementById("testbutton").addEventListener('click', (event) => {
		// Test button ,only works locally.. maybe
		getCard();
	})
}

async function getData(city) {
	const url = "https://api.api-ninjas.com/v1/city&name=" + city;
	const origin = "https://api-ninjas.com";
	// Set api key here + delete when done
	let key = "MI4XECM9nuLCPKhZIq+MRQ==i8CXEw4Kc7fi9Nec";
	if (key === "") {
		key = "insert-api-key-here"
	}
	console.log("Your API Key is: " + key);

	let response = await fetch(url, {
		method: "GET",
		headers: {
			"Content-Type": "application/json",
			"Access-Control-Allow-Origin": "https://api.api-ninjas.com",
			"Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept",
			"X-Api-Key": key,
			"Vary": "Origin"
		},
	})
		.then((response) => {
			console.log(response.text())
		});
}

async function getCard() {
	const url = "http://localhost:8080/card&card=1";
	/* const origin = "https://api.api-ninjas.com";
	// Set api key here + delete when done
	let key = "MI4XECM9nuLCPKhZIq+MRQ==i8CXEw4Kc7fi9Nec";
	if (key === "") {
		key = "insert-api-key-here"
	}
	console.log("Your API Key is: " + key); */

	let response = await fetch(url, {
		method: "GET",
		headers: {
			"Content-Type": "application/json",
			"Access-Control-Allow-Origin": "*",
			"Vary": "Origin"
		}
	});
	let text = await response.text();
	console.log(text);
}