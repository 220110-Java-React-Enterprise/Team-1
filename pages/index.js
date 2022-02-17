


// Setting up event listener for the index page
// registration/login prompt on page load
window.onload = (event) => {
	console.log("Page is loaded.. register?");
	// submit button handler
	document.getElementById('submit').addEventListener('click', (event) => {
		// TODO: Take out preventDefault if we want the form to be submitted (this is debug) *********
		event.preventDefault();
		doTakeoff();
		//alert('Submit was clicked');
	})
	// Add event handler to registration/login buttons to dismiss login screen
	const loginButtons = document.getElementsByClassName("login-button");
	for(let i=0; i<loginButtons.length; i++) {
		loginButtons[i].addEventListener("click", hideLoginOverlay);
	}
	// delay showing login for 3 seconds
	// Should probably add a check if the person has previously logged in here
	//setTimeout(showLoginOverlay, 1500);
}

function doTakeoff() {
	document.getElementsByTagName("body")[0].style.overflowX = "hidden";
	const buddyLogoNode = document.getElementById('buddy-logo');
	buddyLogoNode.classList.add("takeoff-animation");
}

function showLoginOverlay() {
	console.log("Login overlay displayed");
	const loginNode = document.getElementById('login');
	loginNode.classList.remove("hidden");
	loginNode.classList.add("login-boxes-visible")
}

function hideLoginOverlay()
 {
	 //TODO: don't forget to remove this later - this is for testing *****************************
	event.preventDefault();
	console.log("Login overlay hidden");
	document.getElementById('login').classList.remove("login-boxes-visible");
	document.getElementById('login').classList.add("hidden");
}