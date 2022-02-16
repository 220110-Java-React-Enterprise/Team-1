


// Setting up event listener for the index page
//const arrowElements = document.getElementsByClassName('arrow');

// registration/login prompt on page load
window.onload = (event) => {
	console.log("Page is loaded.. register?");
	// submit button handler
	const submitButtonNode = document.getElementById('submit');
	submitButtonNode.addEventListener('click', (event) => {
		// Take out preventDefault if we want the form to be submitted (this is debug)
		event.preventDefault();
		console.log('it was clicked');
	})
}


/* for (let i=0; i<arrowElements.length; i++) {
	arrowElements[i].addEventListener('click', showEstimates);
	// disabled for now since it is spamming the console
	//arrowElements[i].addEventListener('mouseover', showEstimates);
} */
