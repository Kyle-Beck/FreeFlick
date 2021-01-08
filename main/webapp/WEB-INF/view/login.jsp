<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
	<head>
		<title>FreeFlick</title>
  		<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
		
<link rel="stylesheet" href="/publicStyles/login.css">

	</head>
	
	<body>
	
	<div class= "contentRow centeredContent">
		<div>				
			<h1>FreeFlick</h1>
		</div>
	</div>
		<div class=contentRow>
		
			<div class="centeredContent">
			
				<div class = buttonDiv>
					<a href="/oauth2/authorization/github">
						<img class=loginButton src="/images/signin_github.png" height=59.5 width=285.5/>
					</a>
				</div>
						
				<div class = buttonDiv>
					<a href="/oauth2/authorization/google">
						<img class=loginButton src="/images/signin_google.png" height=59.5 width=285.5/>
					</a>
				</div>
				
				<div id=text1>
					<h2>100s Of Public Domain Films. 100% Free</h2>
				</div>
				
				<p>
					<a class="btn" id=dropDownButton data-toggle="collapse" href="#whySignIn" role="button" aria-expanded="false" aria-controls="whySignIn">
				    	Why Sign In?
				  	</a>
				</p>
				
				<div class="collapse" id="whySignIn">
				  	<div class="card card-body">
				    	By signing in with an identity provider, you enable FreeFlick to provide personalized features including recommendations. FreeFlick will not share or sell any of your data except as required by law. FreeFlick does not store, analyze, or otherwise use any data obtained from Google or Github other than the unique subject identifier. This identifier is used to distinguish your viewing activity from that of other viewers. FreeFlick's privacy policy, terms of service, accountability disclaimer, and contact information can be found here:  
				  		<a href="${pageContext.request.contextPath}/legal?doc=base" target="_blank" class="dropdown-item"><p style="color:blue;">Legal</p></a>
				  	</div>
				</div>	
							
			</div>
			
		</div>				
		
<!--			
 		<div class=movieImages>
			<div><img class=moviesImage src=/images/movies.png></img></div>
			<div><img class=moviesImage src=/images/movies2.png></img></div>
			<div><img class=moviesImage src=/images/movies3.png></img></div>
		</div>
-->
			
	</body>
</html>