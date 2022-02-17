


// Setting up event listener for the testing page
window.onload = (event) => {
	console.log("Page is loaded...");
	// submit button handler
	document.getElementById('button-search').addEventListener('click', (event) => {
		// TODO: Take out preventDefault if we want the form to be submitted (this is debug) *********
		event.preventDefault();
		//doTakeoff();
	});

	document.getElementById('button-register').addEventListener('click', (event) => {
		event.preventDefault();
	});

	document.getElementById('button-login').addEventListener('click', (event) => {
		event.preventDefault();
	});

	document.getElementById('button-review').addEventListener('click', (event) => {
		event.preventDefault();
	});

	document.getElementById('button-report').addEventListener('click', (event) => {
		event.preventDefault();
	});
}