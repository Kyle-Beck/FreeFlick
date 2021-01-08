<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>FreeFlick</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- imports -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<!-- Bootstrap's Style Sheet -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

<!-- Custom Style Sheets -->
<link rel="stylesheet" href="/styles/bootstrapCarousel.css">
<link rel="stylesheet" href="/publicStyles/bootstrapCommon.css">
<link rel="stylesheet" href="/styles/bootstrapNavBar.css">
<link rel="stylesheet" href="/styles/bootstrapMoviePreview.css">

<!-- Custom js  -->
<script src="/javascript/constructCategories.js"></script>

<script src="/javascript/jwPlayer.js"></script>
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


	<div id="batches">
	<c:forEach var="batch" items="${recs}">
		<!-- make a hidden element for each batch including title, line, and previews -->
		<div class="hiddenBatch">
			<!-- Batch title -->
			<h2>${batch.title}</h2>
			<!-- Dividing line for batch-->
			<hr class="line">

			<!-- add all the movies into a hidden list -->
			<div class="hiddenPreviews">
					<c:forEach var="movie" items="${batch.movies}">
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
	</c:forEach>
	</div>

	<!-- Construct appropriate parent for previews inside each hidden batch (depending on touch capability) -->
	<!-- Move previews from hidden list to constructed parent -->
	<!-- Reveal hidden batches -->
	<script>populateHomePage();</script>

</body>
</html> 