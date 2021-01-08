<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<title>FreeFlick</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!-- This script calls the saveHistory controller which persists the user rating in the History table -->
<!-- 
<script>
		function saveHistory(rating) {
			
			var id = ${movie.movieID};
		
			$.ajax({
				url : "/saveHistory?rating=" + rating + "&movieId=" + id,
				type : "get",
				success : function() {}
			});
		}
</script>
 -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

<link rel="stylesheet" href="/styles/bootstrapCarousel.css">
<link rel="stylesheet" href="/publicStyles/bootstrapCommon.css">
<link rel="stylesheet" href="/styles/bootstrapNavBar.css">
<link rel="stylesheet" href="/styles/bootstrapMoviePreview.css">
<link rel="stylesheet" href="/styles/bootstrapDescription.css">

<script src="/javascript/constructCategories.js"></script>
<script src="/javascript/watchPageScript.js"></script>

<script src="https://archive.org/jw/8/jwplayer.js"></script>


</head>

<body>

	<!-- Nav Bar -->
	<div class="container-fluid">
		<div class="row navBarRow">
		
			<!-- Site Title -->
			<div class="col, title">
				<a href="${pageContext.request.contextPath}/">
					<h1>FreeFlick</h1>
				</a>
			</div>
			
			<!-- Search Bar -->
			<div id="search-bar">
				<form action="/search">
					<input class="form-control searchBar" type="search"
						placeholder="Search" name="search">
				</form>
			</div>

			<!-- More Dropdown -->
			<div class="btn-group pull-left">
				<button type="button" class="btn more-button dropdown-toggle rounded-pill"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					More
				</button>
				<div class="dropdown-menu">
					<a href="${pageContext.request.contextPath}/random"
						class="dropdown-item">Random Movie</a> 
					<a href="${pageContext.request.contextPath}/watchHistory"
						class="dropdown-item">Watch History</a> 
					<a href="${pageContext.request.contextPath}/search?search="
						class="dropdown-item">Genres</a> 
					<div class="dropdown-divider"></div>
					<a href="${pageContext.request.contextPath}/legal?doc=base"
						target="_blank" class="dropdown-item">Legal</a>
				</div>
			</div>
		</div>
	</div>

	<!-- Movie + details -->
	<div class=movieAndDescription>

		<!-- Movie and like/dislike buttons -->
		<div id=movieAndButtons>
		
			<span id=hiddenEmbed>${movie.embed}</span>
			<div id=playerContainer>
				<div id="player"></div>
			</div>
			<script>setupPlayer(${movie.movieID});</script>
			
			<c:if test = "${existingRating == 1}">
				<button id="like" style="background-image: url(/images/krita-up-thumb-color.png);" class="ratingButton" type="button" onclick="saveHistory('1', ${movie.movieID})"></button>
				<button id="dislike" style="background-image: url(/images/krita-down-thumb-white.png);" class="ratingButton" type="button" onclick="saveHistory('0', ${movie.movieID})"></button>
			</c:if>
			
			<c:if test = "${existingRating == -1}">
				<button id="like" style="background-image: url(/images/krita-up-thumb-white.png);" class="ratingButton" type="button" onclick="saveHistory('1', ${movie.movieID})"></button>
				<button id="dislike" style="background-image: url(/images/krita-down-thumb-white.png);" class="ratingButton" type="button" onclick="saveHistory('0', ${movie.movieID})"></button>
			</c:if>
			
			<c:if test = "${existingRating == 0}">
				<button id="like" style="background-image: url(/images/krita-up-thumb-white.png);" class="ratingButton" type="button" onclick="saveHistory('1', ${movie.movieID})"></button>
				<button id="dislike" style="background-image: url(/images/krita-down-thumb-color.png);" class="ratingButton" type="button" onclick="saveHistory('0', ${movie.movieID})"></button>
			</c:if>
		</div>

		<!-- Movie description -->
		<div id="description">

			<!-- Title/Year -->
			<div id=title>${movie.title}</div>
			<div id=year>(${movie.year})</div>

			<!-- Dividing Line -->
			<hr class="line">
			<br>

			<!-- Directors -->
			<div>
				<span class=descriptionSection>Director:&nbsp;</span>

				<c:forEach var="cast" items="${movie.tags}">
					<c:choose>
						<c:when test="${cast.type=='director'}">
							<span class=castName>${cast.name} &nbsp;&nbsp;</span>
						</c:when>
					</c:choose>
				</c:forEach>
			</div>

			<!-- Actors -->
			<div>
				<span class=descriptionSection>Starring:&nbsp;</span>

				<c:forEach var="cast" items="${movie.tags}">
					<c:choose>
						<c:when test="${cast.type=='actor'}">
							<span class=castName>${cast.name} &nbsp;&nbsp;</span>
						</c:when>
					</c:choose>
				</c:forEach>
			</div>

			<!-- Writers -->
			<div>
				<span class=descriptionSection>Written by:&nbsp;</span>

				<c:forEach var="cast" items="${movie.tags}">
					<c:choose>
						<c:when test="${cast.type=='writer'}">
							<span class=castName>${cast.name} &nbsp;&nbsp;</span>
						</c:when>
					</c:choose>
				</c:forEach>
			</div>

			<br>

			<!-- Plot -->
			<div class=descriptionSection>Plot:</div>
			
		
			<div id="plot">
				<div id = plot-text></div>
			</div>
			
			<div id = "plot-hidden">${movie.plot}</div>
			<script>setupPlot();</script>
			
			<!-- Genres -->
			<div id=genres>
				<span class=descriptionSection>Genres:&nbsp;&nbsp;</span>
				<c:forEach var="tag" items="${movie.tags}">
					<c:choose>
						<c:when test="${tag.type=='genre'}">
							<span class=genreName>${tag.name} &nbsp;&nbsp;</span>
						</c:when>
					</c:choose>
				</c:forEach>
			</div>
		</div>
	</div>

	<!-- Similar Titles -->
	<!-- Sometimes movieBatch will be null if a problem occurs while fetching similar titles -->
	<c:if test="${not empty recommendations.title}">
		<div class="hiddenBatch" id="similar">
			<!-- Batch title -->
			<h2>${recommendations.title}</h2>
			<!-- Dividing line for batch-->
			<hr class="line">

			<!-- add all the movies into a hidden list -->
			<div class="hiddenPreviews">
					<c:forEach var="movie" items="${recommendations.movies}">
						<!-- create a movie preview -->
						<div class="col-sm h-20">
							<!-- the wrapper contains the poster and the on-mouse-hover text -->
							<div class="preview">
								<a
									href="${pageContext.request.contextPath}/watch?id=${movie.movieID}">
									<img class="poster" alt="Could not load image"
									src="${movie.posterUrl}">
								</a>
								<!-- title and year are displayed on mouse hover -->
								<div class="hidden">
									<p class="previewDetails">
										${movie.title} <br> (${movie.year})
									</p>
								</div>
							</div>
						</div>
					</c:forEach>
			</div>
		</div>
		
		<script>populateSimilarTitles();</script>
		
	</c:if>

</body>

</html>

