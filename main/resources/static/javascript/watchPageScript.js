// if plot exceeds hint length, plot becomes expandable/retractable
var hintLength = 300;

// if plot < 300 characters, reveal full plot
// if plot >= 300 characters, reveal plot hint and "show full plot" button
function setupPlot(){
	// create plot-button and append it to body
	var button = document.createElement("BUTTON");
	button.id = "plot-button";
	document.body.appendChild(button);
	button.classList.add("btn");
	
	var hiddenText = document.getElementById("plot-hidden").innerText;
	if(hiddenText.length >= hintLength){	
		// if plot is too big, show hint and button
		showPlotHint();
		document.getElementById("plot").appendChild(button);
	}else{
		// if plot not too big, hide button and show full plot
		button.classList.add("plot-button-hide");		
		showPlotFull();
	}
}

function showPlotFull() {
	// move plot text from plot-hidden div to plot div
	var hiddenText = document.getElementById("plot-hidden").innerText;
	document.getElementById("plot-text").innerText = hiddenText;	
	// set button onclick to retract plot
	button = document.getElementById("plot-button");
	button.onclick = function(){showPlotHint();};
	button.innerText = "Hide Full Plot";
}

function showPlotHint() {
	// move first 300 characters of plot text from plot-hidden div to plot div
	var hiddenText = document.getElementById("plot-hidden").innerText;
	var plotHintText = hiddenText.substring(0, hintLength) + "...";
	document.getElementById("plot-text").innerText = plotHintText;
	// set button onclick to expand plot	
	var button = document.getElementById('plot-button');			
	button.onclick = function(){showPlotFull();};
	button.innerText = "Show Full Plot"
}

function saveHistory(rating, movieID) {
		
			// save rating to history table in db
			$.ajax({
				url : "/saveHistory?rating=" + rating + "&movieId=" + movieID,
				type : "get",
				success : function() {}
			});
			
			if(rating == 1){
				// change like button to green and dislike button to white
				document.getElementById("like").style.backgroundImage = "url(/images/krita-up-thumb-color.png)";
				document.getElementById("dislike").style.backgroundImage = "url(/images/krita-down-thumb-white.png)";
			}else{
				// change like button to white and dislike button to red
				document.getElementById("like").style.backgroundImage = "url(/images/krita-up-thumb-white.png)";
				document.getElementById("dislike").style.backgroundImage = "url(/images/krita-down-thumb-color.png)";
			}
}

function setupPlayer(movieID) { 
	// get embed from hidden html element and use it to setup jw player
	var embed = document.getElementById("hiddenEmbed").innerText;
	jwplayer("player").setup({
    	title:"",
        file:embed,
		width:"100%",
		aspectratio: "16:9"
    });

	//count video as liked if it finishes playing
    jwplayer().on('complete', function(){
		saveHistory("1", movieID);		
    });

}