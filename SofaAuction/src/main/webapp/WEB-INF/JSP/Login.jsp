<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Bootstrap core CSS -->
    <link href="BOOTSTRAP/css/bootstrap.min.css" rel="stylesheet">
    
<!-- CSS Style-->
	<link rel="stylesheet" href= "${pageContext.request.contextPath}/css/Login.css"/>
<title>Connexion</title>
</head>
<body>
	<container>
		<header>
			<h1>ENI-Enchères</h1>
		</header>
		
		<section class=" row justify-content-center" id="login">
				<form method="POST" action="Login"  class="col-lg-6 col-md-6 col-sm-6 col-6" >
				  <div class="form-group"   >
				    <label for="Identifiant" >Identifiant</label>
				    <input name="identifiant" type="text" class="form-control" id="Identifiant" value="${cookie.lastLogin.value }">
				  </div>
				  <div class="form-group">
				    <label for="password" >Mot de passe :</label>
				    <input name="password" type="password" class="form-control" id="Password">
				  </div>
	
			  	 <div class="form-group form-check">
				    <input type="checkbox" class="form-check-input" id="exampleCheck1">
				    <!-- UN COOKIE D'UNE DURÉE DE 1 AN - SE SOUVENIR DE MOI --> 
				    <label name="remember" class="form-check-label" for="remember">Se souvenir de moi</label> 	    
				  </div>
				  
				  <div>
				  	<a href="##">Mot de passe oublié</a>
				  </div>
				  
				  <div>
				 	 <button type="submit" class="btn btn-primary">Connexion</button>
				 	  <a href="Inscription"><button type="button" class="btn btn-outline-dark btn-lg bouton" id="btn_creer">Créer un compte</button> </a>
				  </div>
				</form>
		</section>
	</container>
</body>
</html>