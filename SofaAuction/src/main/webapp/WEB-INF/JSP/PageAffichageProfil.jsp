<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="fr.eni.encheres.bo.Utilisateur,fr.eni.encheres.bo.Adresse" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<link href="BOOTSTRAP/css/bootstrap.min.css" rel="stylesheet">
<meta charset="UTF-8">
<title>PageAffichageProfil</title>
</head>
<body>
<h1>ENI-Enchères</h1>
<section>
<form>
  <div class="row ml-5">
    <div class="col-3">
    	<label> Pseudo : </label><input type="text" class="form-control" value="${utilisateur.pseudo}">
    </div>
    <div class="col-3">
    <label> Nom : </label>
      <input type="text" class="form-control"  value="${utilisateur.nom}">
    </div>
  </div>
    <div class="row ml-5">
    <div class="col-3">
    	<label> Prénom : </label><input type="text" class="form-control"  value="${utilisateur.prenom}">
    </div>
     <div class="col-3">
    	<label> Email : </label><input type="text" class="form-control" value="${utilisateur.email}">
    </div>
  </div>
   <div class="row ml-5">
    <div class="col-3">
    	<label> Téléphone: </label><input type="text" class="form-control" value="${utilisateur.telephone}" >
    </div>
    <div class="col-3">
    <label> Rue : </label>
      <input type="text" class="form-control" value="${adresse.rue}">
    </div>
  </div>
   <div class="row ml-5">
    <div class="col-3">
    <label> Code Postal : </label>
      <input type="text" class="form-control" value="${adresse.codePostal}" >
    </div>
    <div class="col-3">
    <label> Ville : </label>
      <input type="text" class="form-control" value="${adresse.ville}" >
    </div>
  </div>
</form>
<br>
</section>
<section>
<div class="container">
 <div class="row">
    <div class="col align-self-center">
<c:if test="${utilisateur.utilisateurId==user.utilisateurId }">
<a href="/modifierProfil?id=utilisateurId" class="btn btn-secondary btn-lg active" role="button" aria-pressed="true">Modifier</a>
</c:if>
</div>
</div>
</div>
</section>
</body>
</html>