<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<title>FreeFlick</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

<link rel="stylesheet" href="/styles/bootstrapSearch.css">
<link rel="stylesheet" href="/styles/bootstrapDescription.css">
<link rel="stylesheet" href="/publicStyles/bootstrapCommon.css">
<link rel="stylesheet" href="/styles/bootstrapNavBar.css">
<link rel="stylesheet" href="/styles/bootstrapSearchMoviePreview.css">

</head>

<body>
	<!-- Advanced Search Form -->
	<form action="/search" class="searchForm">

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
				<input type="submit" style="display: none" />
				<div id="search-bar">
					<input class="form-control searchBar" type="search"
						placeholder="Search.."
						value=<%="\"" + request.getParameter("search") + "\""%>
						name="search">
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

		<div class="container-fluid">
			<div class="row">

				<!-- Genre Filter -->
				<div id="genre-filter">
					<select class="form-control genreFilter" id="sel1" name="genre">
						<option value="" selected disabled>Genres</option>
						<option value="action">Action</option>
						<option value="documentary">Documentary</option>
						<option value="romance">Romance</option>
						<option value="adventure">Adventure</option>
						<option value="drama">Drama</option>
						<option value="thriller">Thriller</option>
						<option value="fantasy">Fantasy</option>
						<option value="crime">Crime</option>
						<option value="western">Western</option>
					</select>
				</div>

				<!-- Start Year Filter -->
				<div id="start-year">
					<input type="number" placeholder="Start Year"
						value=<%=request.getParameter("startYear")%> name="startYear"
						class="form-control">
				</div>

				<!-- End Year Filter -->
				<div id="end-year">
					<input type="number" placeholder="End Year"
						value=<%=request.getParameter("endYear")%> name="endYear"
						class="form-control">
				</div>

				<!-- Submit Button -->
				<div id=submit>
					<input id=submit-button type="submit" class="btn btn-info" value="Search">
				</div>
			</div>
		</div>
	</form>


	<!-- Search results -->
	<div id="results">
		<c:forEach var="movie" items="${results}">
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