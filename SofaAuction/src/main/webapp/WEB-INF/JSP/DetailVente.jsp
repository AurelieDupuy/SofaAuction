<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page	import="fr.eni.encheres.bo.Utilisateur,fr.eni.encheres.messages.LecteurMessage"%>
	
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
		<title>ENI Enchères</title>
		<link rel="icon" href="${pageContext.request.contextPath}/images/icon.png" type="image/icon type">
		
		<!-- Bootstrap core CSS -->
		<link href="${pageContext.request.contextPath}/BOOTSTRAP/css/bootstrap.min.css"	rel="stylesheet">
	
		<!-- Css encheres -->
		<link href="${pageContext.request.contextPath}/css/PageAccueil.css" rel="stylesheet">
	
	</head>

<body>
	<!--  En tête -->
	<jsp:include page="EnTete.jsp"></jsp:include>

<section id="corps">

	<!-- affichage erreurs s'il y en a eu -->
	<div class="alert">
		<c:if test="${!empty listeCodesErreur}">
			<div class="alert alert-danger" role="alert">
				<strong>Erreur!</strong>
				<ul>
					<c:forEach var="code" items="${listeCodesErreur}">
						<li>${LecteurMessage.getMessageErreur(code)}</li>
					</c:forEach>
				</ul>
			</div>
		</c:if>
	</div>
	
	<div class="container text-center" id="page-top">
		<div class="row justify-content-center">
			<h1>Détail vente</h1>
		</div>
	</div>
	

	<section id="conteneur">
		<aside class="detail">
			<c:if test="${article.photo==null}"><img src="${pageContext.request.contextPath}/images/A_vendre.jfif" class="detail-img" alt="photo de l'article"></c:if>
			<c:if test="${article.photo!=null}"><img src="${pageContext.request.contextPath}/images/${article.photo}" class="detail-img" alt="photo de l'article"></c:if>
		</aside>

		<article class="detail">
			<div class="element">
				<div>
					<h4>${article.nomArticle}</h4>
				</div>
			</div>
			<div class="element">
				<div class="flex-1">
					<h5>Description : </h5>
				</div>
				<div class="flex-2">
					<p>${article.description}</p>
				</div>
			</div>
			<div class="element">
				<div class="flex-1">
					<h5>Catégorie : </h5>
				</div>
				<div class="flex-2">
					<p>${article.categorie}</p>
				</div>
			</div>
			<div class="element">
				<div class="flex-1">
					<h5>Meilleur offre : </h5>
				</div>
				<div class="flex-2">
					<p><fmt:formatNumber type="number" value="${article.prixVente}" maxFractionDigits="0"></fmt:formatNumber> points par ${article.acheteur.pseudo}</p>
				</div>
			</div>
			<div class="element">
				<div class="flex-1">
					<h5>Mise à prix :</h5>
				</div>
				<div class="flex-2">
					<p><fmt:formatNumber type="number" value="${article.miseAPrix}" maxFractionDigits="0"></fmt:formatNumber> points</p>
				</div>
			</div>
			<div class="element">
				<div class="flex-1">
					<h5>Fin de l'enchère :</h5>
				</div>
				<div class="flex-2">
					<p><fmt:formatDate value="${article.finEncheres}" pattern="dd/MM/yyyy"/></p>
				</div>
			</div>
			<div class="element">
				<div class="flex-1">
					<h5>Retrait :</h5>
				</div>
				<div class="flex-2">
					<p>${article.retrait.rue}<br>${article.retrait.codePostal} ${article.retrait.ville}</p>
				</div>
			</div>
			<div class="element">
				<div class="flex-1">
					<h5>Vendeur :</h5>
				</div>
				<div class="flex-2">
					<p><a class="link" href="AffichageProfil?id=${article.vendeur.utilisateurId}">${article.vendeur.pseudo}</a></p>
				</div>
			</div>
			<div class="element">
				<div class="flex-1 my-2">
					<h5>Ma proposition :</h5>
				</div>
				<div class="flex-2">
					<form action="DetailVente" method="POST">
						<input type="hidden" name="articleEnVente" value="${article.articleId}">
  						<input type="number" id="enchere" name="enchere" 
  							<c:if test="${article.prixVente==0}">min="${article.miseAPrix}"</c:if>
  							<c:if test="${article.prixVente!=0}">min="${article.prixVente + 1}"</c:if>>
  						<input type="submit" class="btn btn-secondary btn-lg ml-5"	value="Valider">
					</form>
				</div>
			</div>
			
		</article>
	</section>	

	



</section>

	<!-- Footer -->
	<jsp:include page="Footer.jsp"></jsp:include>

</body>


</html>