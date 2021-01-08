<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<title>FreeFlick</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

<link rel="stylesheet" href="/styles/bootstrapDescription.css">
<link rel="stylesheet" href="/publicStyles/bootstrapCommon.css">
<link rel="stylesheet" href="/styles/bootstrapNavBar.css">
<link rel="stylesheet" href="/styles/bootstrapSearchMoviePreview.css">

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
	
	<!-- User History -->
	<div id="results">
		<c:forEach var="movie" items="${movies}">
			<div class="preview">
				<a
					href="${pageContext.request.contextPath}/watch?id=${movie.movieID}">
					<img class="poster" alt="Could not load image"
					src="${movie.posterUrl}">
				</a>
				<div class="hidden">
					<p class="previewDetails">
						${movie.title} <br> (${movie.year})
					</p>
				</div>
			</div>
		</c:forEach>
	</div>

</body>

</html>