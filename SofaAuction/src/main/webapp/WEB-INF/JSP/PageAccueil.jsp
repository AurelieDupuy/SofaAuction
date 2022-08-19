<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	
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

	<div class="container text-center" id="page-top">
		<div class="row justify-content-center">
			<h1>Liste des enchères</h1>
		</div>
	</div>

	<!-- Section ""Filtre" -->
			<form action="PageAccueil" method="GET" name="formRecherche" class="filtre-contenant">
				<div class="filtre-element">
						<!-- Filtre "le nom de l'article copntient : -->
					<div class="filtre-sous-element">
						<div class="filtre-sous-sous-element"><label for="filtreMot">Filtres :</label></div>
						<div class="filtre-sous-sous-element">
							<input type="text"	class="form-control" id="filtreMot" name="filtreMot" 
								<c:if test="${filtreMot!=null}"> value="${filtreMot}" </c:if>
								<c:if test="${filtreMot==null}"> placeholder="Le nom de l'article contient..."</c:if>>
						</div>
					</div>
						
						<!-- Filtre sur la catégorie: -->
					<div class="filtre-sous-element">
						<div class="filtre-sous-sous-element"><label for="categorie">Catégorie : </label></div>
						<div class="filtre-sous-sous-element">	
							<select	class="form-control form-control-sm" id="categorie"	name="categorie">
									<c:if test="${categorie!=null}" ><option selected>${categorie}</option></c:if>
								<option>Toutes</option>
								<c:forEach items="${listeCategorie}" var="c">
									<option>${c.libelle}</option>
								</c:forEach>
							</select>
						</div>
					</div>
						
						<!-- Filtres supplementaire si connecté -->
					<c:if test="${user!=null}">
					<div class="filtre-sous-element">
						<div class="filtre-sous-sous-element">
								<!--  Mes Achats Filtres -->
							<div class="custom-control custom-radio custom-control-inline">
				  				<input type="radio" id="Achats" name="AchatVentes" class="custom-control-input" value="achats" ${CheckedAchat} onClick="document.formRecherche.submit()">
				  				<label class="custom-control-label" for="Achats">Achats</label>			
							</div>
							<ul>
								<li>
									<input type="radio" class="custom-control-input" id="ouvertes" name="Achats" value="ouvertes" ${FiltreVente} ${CheckedEncheresOuvertes} onClick="document.formRecherche.submit()">						
				    				<label class="custom-control-label" for="ouvertes">Enchères ouvertes</label>
				    			</li>
				    			<li>
									<input type="radio" class="custom-control-input" id="enCours" name="Achats" value="enCours" ${FiltreVente} ${CheckedEncheresEnCours} onClick="document.formRecherche.submit()">
				    				<label class="custom-control-label" for="enCours">Mes enchères en cours</label>
				    			</li>
				    			<li>
									<input type="radio" class="custom-control-input" id="remportees"  name="Achats" value="remportees"${FiltreVente} ${CheckedEncheresRemportees} onClick="document.formRecherche.submit()">
				    				<label class="custom-control-label" for="remportees">Mes enchères remportées</label>
				    			</li>
				    		</ul>
		    			</div>
								<!--  Mes Ventes Filtres -->
					   <div class="filtre-sous-sous-element">
							<div class="custom-control custom-radio custom-control-inline">
								<input type="radio" id="Ventes" name="AchatVentes" class="custom-control-input" value="ventes" ${CheckedVente} onClick="document.formRecherche.submit()">
								<label class="custom-control-label" for="Ventes">Mes ventes</label>
							</div>
							<ul>
								<li>
									<input type="radio" class="custom-control-input" id="mesEnCours" name="Ventes" value="mesEnCours" ${FiltreAchat} ${CheckedMesenCours} onClick="document.formRecherche.submit()">							
				    				<label class="custom-control-label" for="mesEnCours">Mes ventes en cours</label>
				    			</li>
				    			<li>
									<input type="radio" class="custom-control-input" id="debutees" name="Ventes" value="debutees" ${FiltreAchat} ${CheckedDebutees} onClick="document.formRecherche.submit()">
				    				<label class="custom-control-label" for="debutees">Mes ventes non débutées</label>
				    			</li>
				    			<li>
									<input type="radio" class="custom-control-input" id="terminees" name="Ventes" value="terminees" ${FiltreAchat} ${CheckedTerminees} onClick="document.formRecherche.submit()">
				    				<label class="custom-control-label" for="terminees">Mes ventes terminées</label>
				    			</li>
				    		</ul>
						</div>
					</div>
					</c:if>	
				</div>
						<!-- Bouton Rechercher -->
				<div class="filtre-element">
					<div class="filtre-sous-element">
							<input type="submit" class="btn btn-secondary btn-lg"	value="Rechercher">
					</div>			
				</div>
			</form>

	
	<!--  Section affichant la liste des enchères en cours (filtré ou non)-->
	<section class="liste-enchere">
		<div class="row justify-content-space-around">
			
		<!-- Cartes des articles filtrés -->
			<!-- Message si aucun article -->
			<div class="col-12">
				<c:if test="${empty listeVentesEnCours}">
					<h2 class="text-center">
						<fmt:bundle basename="fr.eni.encheres.messages.mes_messages">
							<fmt:message key="msg_pas_d'article"></fmt:message>
						</fmt:bundle>
					</h2>
				</c:if>
			</div>
			
			<!-- Affichage des cartes -->
			<c:forEach items="${listeVentesEnCours}" var="a">
				<div class="col my-4 ">
					<div class="card" style="width: 18rem;">
						<a href="DetailVente?article=${a.articleId }">
						<div class="card-image">
							<c:if test="${a.photo==null}"><img src="${pageContext.request.contextPath}/images/A_vendre.jfif" class="card-img-top" alt="photo de l'article"></c:if>
							<c:if test="${a.photo!=null}"><img src="${pageContext.request.contextPath}/images/${a.photo}" class="card-img-top" alt="photo de l'article"></c:if>
						</div>
						</a>
						<div class="card-body">
							<h5 class="card-title"><a class="link" href="DetailVente?article=${a.articleId }">${a.nomArticle}</a></h5>
							<p class="card-text">Prix :
								<c:if test="${a.prixVente==0}"><fmt:formatNumber type="number" value="${a.miseAPrix}" maxFractionDigits="0"></fmt:formatNumber></c:if>
								<c:if test="${a.prixVente!=0}"><fmt:formatNumber type="number" value="${a.prixVente}" maxFractionDigits="0"></fmt:formatNumber></c:if>
								points</p>
							<p class="card-text">Fin de l'enchère : <fmt:formatDate value="${a.finEncheres}" pattern="dd/MM/yyyy"/></p>
							<p class="card-text">Vendeur : ${a.vendeur.pseudo}</p>
						</div>
					</div>
				</div>
			</c:forEach>			
		</div>
	</section>
</section>

	<!-- Footer -->
	<jsp:include page="Footer.jsp"></jsp:include>

</body>


</html>