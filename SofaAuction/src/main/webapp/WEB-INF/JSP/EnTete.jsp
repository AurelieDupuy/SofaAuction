<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<body>
<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg fixed-top"	id="mainNav">
		<div class="container">
			
			<a class="navbar-brand js-scroll-trigger" href="PageAccueil"><img src="${pageContext.request.contextPath}/images/logo2.png" alt="logo ENI Enchères" id="logo"></a>
			<button class="navbar-toggler navbar-toggler-right font-weight-bold text-white rounded"	type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNavDropdown">
				<ul class="navbar-nav ml-auto">
					<!--   cas déconnecté  -->
					<c:if test="${user==null}">
						<li class="nav-item mx-0 mx-lg-1"><a class="nav-link py-3 px-0 px-lg-3 rounded js-scroll-trigger" href="Login">S'inscrire - Se connecter</a></li>
					</c:if>
					<!--   cas connecté  -->
					<c:if test="${user!=null}">
						<li class="nav-item mx-0 mx-lg-1"><a class="nav-link py-3 px-0 px-lg-3 rounded js-scroll-trigger" href="#">Enchères</a></li>
						<li class="nav-item mx-0 mx-lg-1"><a class="nav-link py-3 px-0 px-lg-3 rounded js-scroll-trigger" href="#">Vendre un article</a></li>
						<li class="nav-item mx-0 mx-lg-1"><a class="nav-link py-3 px-0 px-lg-3 rounded js-scroll-trigger" href="AffichageProfil?id=${user.utilisateurId }">Mon profil</a></li>
						<li class="nav-item mx-0 mx-lg-1"><a class="nav-link py-3 px-0 px-lg-3 rounded js-scroll-trigger" href="deconnexion">Déconnexion</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>
	
</body>
</html>

