 //return true if mobile
function checkMobile() { 
	  let check = false;
	  (function(a){if(/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino|android|ipad|playbook|silk/i.test(a)||/1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0,4))) check = true;})(navigator.userAgent||navigator.vendor||window.opera);
	  return check;
};

// construct home page based on wether or not device is mobile
function populateHomePage() { 
	 if(checkMobile()){
		 // FOR MOBILE
	
		// Each hidden batch contains a title, a dividing line, and all the movie previews for the batch
		var hiddenBatches = document.getElementById("batches").children;
		
		// for each hidden batch, create a scrollable and append all the previews to the scrollable
		for (var i = 0; i < hiddenBatches.length; i++) {
			
  			var hiddenBatch = hiddenBatches[i];
  			
  			// create scrollable elements and append to hiddenBatch
  			scrollable = document.createElement("div"); 
  			scrollable.classList.add("continer-fluid", "mobilePreviews");
  			hiddenBatch.appendChild(scrollable);
			
			// create a row inside the scrollable
  			row = document.createElement("div");
  			row.classList.add("row", "no-gutters");
  			scrollable.appendChild(row);
  			
  			// get hidden previews by scanning hidden batch for the hidden-previews div
  			var hiddenPreviews;
  			var batchChildren = hiddenBatch.children;
  			for (var j = 0; j < batchChildren.length; j++) {
  				if (batchChildren[j].className == "hiddenPreviews") {
  			      hiddenPreviews = batchChildren[j];
  			      break;
  			    }  
  			}
  	
  			// for each preview in hidden previews, add preview to row inside scrollable
  			var previews = hiddenPreviews.children;  			
  			for (let k = 0; previews.length > 0; k++) {
  				previews[0].classList.add("mobilePoster");
  				//TODO: this class shouldnt be here in the first place. it should be added for desktop
  				previews[0].classList.remove("col-sm");
  				row.appendChild(previews[0]);
  			}
  			
			// reveal hiddenBatch
  			hiddenBatch.classList.remove("hiddenBatch");
  			hiddenBatch.classList.add("notHiddenBatch");	
		}
	 } else {
		// FOR DESKTOP

		// Each hidden batch contains a title, a dividing line, and all the movie previews for the batch
		var hiddenBatches = document.getElementById("batches").children;
		
		// for each hidden batch, create a bootstrap carousel and append all the previews to the carousel
		for (var i = 0; i < hiddenBatches.length; i++) {
			
  			var hiddenBatch = hiddenBatches[i];
	
  			//Make carousel outer and append it to hidden batch
  			var carouselID = "my_" + i;
  			var carouselOuter = document.createElement("div");
  			carouselOuter.setAttribute("id", carouselID);
  			carouselOuter.setAttribute("class", "carousel slide");
  			carouselOuter.setAttribute("data-ride", "carousel");
  			carouselOuter.setAttribute("data-interval", "false");
  			hiddenBatch.appendChild(carouselOuter);
  				
  			//Make carousel inner and append it to carousel outer
  			var carouselInner = document.createElement("div"); 
  			carouselInner.setAttribute("class", "carousel-inner");
  			carouselOuter.appendChild(carouselInner);
  			
  			// get hidden previews by scanning hidden batch for the hidden-previews div
  			var hiddenPreviews;
  			var batchChildren = hiddenBatch.children;
  			for (var j = 0; j < batchChildren.length; j++) {
  				if (batchChildren[j].className == "hiddenPreviews") {
  			      hiddenPreviews = batchChildren[j];
  			      break;
  			    }  
  			}
  	
			var margins;
			
  			// for each preview in hidden previews, add preview to bootstrap carousel
			// make a new carousel item every 8 previews
  			var previews = hiddenPreviews.children;
  			var batchSize = previews.length;
  			for (let k = 0; previews.length > 0; k++) {
  				if(k == 0){
  	  				//before adding first preview, create active carousel item
  					var carouselItem = document.createElement("div"); 
  					carouselInner.appendChild(carouselItem);
  					carouselItem.setAttribute("class", "carousel-item active");
  					var container = document.createElement("div"); 
  					container.classList.add("container-fluid");
  					carouselItem.appendChild(container);
  					margins = document.createElement("div");
  					margins.classList.add("carousel-margins", "row", "no-gutters");
  					container.appendChild(margins);
  				} else if (k % 8 == 0){
  					// for every 8 previews, create a new inactive carousel item
  					var carouselItem = document.createElement("div"); 
  					carouselInner.appendChild(carouselItem);
  					carouselItem.setAttribute("class", "carousel-item");
  					var container = document.createElement("div"); 
  					container.classList.add("container-fluid");
  					carouselItem.appendChild(container);
  					margins = document.createElement("div");
  					margins.classList.add("carousel-margins", "row", "no-gutters");
  					container.appendChild(margins);
  				}
  				// add previews to carousel item (margins)
  				margins.appendChild(previews[0]);
  			}
  			
  			//append controls to ith carousel outer
  			if(batchSize > 8){
	
				// previous button
				var prev = document.createElement("a");
				prev.setAttribute("class", "carousel-control-prev");
				var hrefString = "#my_" + i
				prev.setAttribute("href", hrefString);
				prev.setAttribute("data-slide", "prev");
				
				var spanPrev = document.createElement("span");
				spanPrev.setAttribute("class", "carousel-control-prev-icon");
				prev.appendChild(spanPrev);
				
				carouselOuter.appendChild(prev);
				
				// next button
				var next = document.createElement("a");
				next.setAttribute("class", "carousel-control-next");
				next.setAttribute("href", hrefString);
				next.setAttribute("data-slide", "next");
				
				var spanNext = document.createElement("span");
				spanNext.setAttribute("class", "carousel-control-next-icon");
				next.appendChild(spanNext);
				
				carouselOuter.appendChild(next);
			}
  			
			// reveal hidden batch
  			hiddenBatch.classList.remove("hiddenBatch");
  			hiddenBatch.classList.add("notHiddenBatch");
		}
	 }
};

// similar to populateHomePage, but only for the one "similar titles" category on the watch page
// TODO: this and populateHomePage() could be the same function
function populateSimilarTitles() { 
	 if(checkMobile()){
		 // for mobile
		var hiddenBatch = document.getElementById("similar");

 		// create scrollable elements and append to hiddenBatch
  		scrollable = document.createElement("div");
  		scrollable.classList.add("continer-fluid", "mobilePreviews");
  		hiddenBatch.appendChild(scrollable);
			
  		row = document.createElement("div");
  		row.classList.add("row", "no-gutters");
  		scrollable.appendChild(row);
  			
  		// get hidden previews
  		var hiddenPreviews;
  		var batchChildren = hiddenBatch.children;
  		for (var j = 0; j < batchChildren.length; j++) {
  			if (batchChildren[j].className == "hiddenPreviews") {
  				hiddenPreviews = batchChildren[j];
  			    break;
  			}  
  		}
  	
  		//for each preview in hidden previews
  		var previews = hiddenPreviews.children;  			
  		for (let k = 0; previews.length > 0; k++) {
  			previews[0].classList.add("mobilePoster");
  			//TODO: this class shouldnt be here in the first place. it should be added for desktop
  			previews[0].classList.remove("col-sm");

  			row.appendChild(previews[0]);
  		}
  			
  		hiddenBatch.classList.remove("hiddenBatch");
  		hiddenBatch.classList.add("notHiddenBatch");	
	 } else {
		//for desktop

		var hiddenBatch = document.getElementById("similar");

  		//Make carousel outer and append it to HB
  		var carouselID = "my_1";
  		var carouselOuter = document.createElement("div");
  		carouselOuter.setAttribute("id", carouselID);
  		carouselOuter.setAttribute("class", "carousel slide");
  			
  		carouselOuter.setAttribute("data-ride", "carousel");
  		carouselOuter.setAttribute("data-interval", "false");
  		hiddenBatch.appendChild(carouselOuter);
  				
  		//Make carousel inner and append it to outer
  		var carouselInner = document.createElement("div"); 
  		carouselInner.setAttribute("class", "carousel-inner");
  		carouselOuter.appendChild(carouselInner);
  			
  		//get hiddenPreviews
  		var hiddenPreviews;
  		var batchChildren = hiddenBatch.children;
  		for (var j = 0; j < batchChildren.length; j++) {
  			if (batchChildren[j].className == "hiddenPreviews") {
  		      hiddenPreviews = batchChildren[j];
  		      break;
  		    }  
  		}
  	
		var margins;
  		//for each preview in hidden previews
  		var previews = hiddenPreviews.children;
  		var batchSize = previews.length;
  		for (let k = 0; previews.length > 0; k++) {
  				
  			if(k == 0){
  	  			//before adding first preview, create active carousel item
  				var carouselItem = document.createElement("div"); 
  				carouselInner.appendChild(carouselItem);
  				carouselItem.setAttribute("class", "carousel-item active");
  				var container = document.createElement("div"); 
  				container.classList.add("container-fluid");
  				carouselItem.appendChild(container);
  				margins = document.createElement("div");
  				margins.classList.add("carousel-margins", "row", "no-gutters");
  				container.appendChild(margins);
  			} else if (k % 8 == 0){
  				//before adding previews divisible by 8, create inactive carousel item
  				var carouselItem = document.createElement("div"); 
  				carouselInner.appendChild(carouselItem);
  				carouselItem.setAttribute("class", "carousel-item");
  				var container = document.createElement("div"); 
  				container.classList.add("container-fluid");
  				carouselItem.appendChild(container);
  				margins = document.createElement("div");
  				margins.classList.add("carousel-margins", "row", "no-gutters");
  				container.appendChild(margins);
  			}
  			// add previews to carousel item (margins)
  			margins.appendChild(previews[0]);
  				
  		}
  			
  		//append controls to ith carousel outer
  		if(batchSize > 8){
			var prev = document.createElement("a");
			prev.setAttribute("class", "carousel-control-prev");
			var hrefString = "#my_1";
			prev.setAttribute("href", hrefString);
			prev.setAttribute("data-slide", "prev");
			
			var spanPrev = document.createElement("span");
			spanPrev.setAttribute("class", "carousel-control-prev-icon");
			prev.appendChild(spanPrev);
				
			carouselOuter.appendChild(prev);
				
				
			var next = document.createElement("a");
			next.setAttribute("class", "carousel-control-next");
			next.setAttribute("href", hrefString);
			next.setAttribute("data-slide", "next");
				
			var spanNext = document.createElement("span");
			spanNext.setAttribute("class", "carousel-control-next-icon");
			next.appendChild(spanNext);
				
			carouselOuter.appendChild(next);
		}
  			
  		hiddenBatch.classList.remove("hiddenBatch");
  		hiddenBatch.classList.add("notHiddenBatch");
	}
};