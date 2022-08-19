package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class PageAccueil
 */
@WebServlet(urlPatterns={"/PageAccueil",""})
public class PageAccueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Initialisation des catégories
		CategorieManager categorieManager = new CategorieManager();
		List<Categorie> categories =new ArrayList<Categorie>();
		categories = categorieManager.listeDesCategorie();
		request.setAttribute("listeCategorie", categories);
		
		// Filtres + réaffichage dans la JSP
		ArticleManager articleManager = new ArticleManager();
		List<Article> articles = new ArrayList<Article>();
		
		String contient;
		if (request.getParameter("filtreMot")==null || request.getParameter("filtreMot")=="") {
			contient="";
		}else{
			contient=request.getParameter("filtreMot");
			request.setAttribute("filtreMot", contient);
		}
		
		String filtreCategorie;
		if (request.getParameter("categorie")==null) {
			filtreCategorie="Toutes";
		}else {
			filtreCategorie = request.getParameter("categorie");
			request.setAttribute("categorie", filtreCategorie);
		}
		
		// Boutons radio Achats/Ventes (Connecté)
		if(request.getSession().getAttribute("user")!=null) {
			if (request.getParameter("AchatVentes")==null || request.getParameter("AchatVentes").equalsIgnoreCase("achats")){
				request.setAttribute("FiltreAchat", "disabled");
				request.setAttribute("CheckedAchat", "checked");
			}else {
				request.setAttribute("FiltreVente", "disabled");
				request.setAttribute("CheckedVente", "checked");
			}
		}
		
		// Récupération de la liste d'articles après filtrage
		if (request.getParameter("AchatVentes")!=null && request.getParameter("AchatVentes").equalsIgnoreCase("achats")) {
			if(request.getParameter("Achats")!=null) {
				if(request.getParameter("Achats").equalsIgnoreCase("ouvertes")){
					articles = articleManager.listeArticles(contient, filtreCategorie);
					request.setAttribute("CheckedEncheresOuvertes", "checked");
				}
				
				if(request.getParameter("Achats").equalsIgnoreCase("enCours")){
					articles = articleManager.mesEncheresEnCours(((Utilisateur)(request.getSession().getAttribute("user"))).getUtilisateurId(), contient, filtreCategorie);
					request.setAttribute("CheckedEncheresEnCours", "checked");
				}
			
				if(request.getParameter("Achats").equalsIgnoreCase("remportees")){
					articles = articleManager.mesEncheresRemportees(((Utilisateur)(request.getSession().getAttribute("user"))).getUtilisateurId(), contient, filtreCategorie);
					request.setAttribute("CheckedEncheresRemportees", "checked");
				}
			}else {
				articles = articleManager.listeArticles(contient, filtreCategorie);
				request.setAttribute("CheckedEncheresOuvertes", "checked");
			}
		
		}else if (request.getParameter("AchatVentes")!=null && request.getParameter("AchatVentes").equalsIgnoreCase("ventes")) {
			if (request.getParameter("Ventes")!=null) {
				if(request.getParameter("Ventes").equalsIgnoreCase("mesEnCours")){
					articles = articleManager.mesVentesEnCours(((Utilisateur)(request.getSession().getAttribute("user"))).getUtilisateurId(), contient, filtreCategorie);
					request.setAttribute("CheckedMesenCours", "checked");
				}
				
				if(request.getParameter("Ventes").equalsIgnoreCase("debutees")){
					articles = articleManager.ventesNonDebutees(((Utilisateur)(request.getSession().getAttribute("user"))).getUtilisateurId(), contient, filtreCategorie);
					request.setAttribute("CheckedDebutees", "checked");
				}
			
				if(request.getParameter("Ventes").equalsIgnoreCase("terminees")){
					articles = articleManager.ventesTerminees(((Utilisateur)(request.getSession().getAttribute("user"))).getUtilisateurId(), contient, filtreCategorie);
					request.setAttribute("CheckedTerminees", "checked");
				}
			}else {
				articles = articleManager.mesVentesEnCours(((Utilisateur)(request.getSession().getAttribute("user"))).getUtilisateurId(), contient, filtreCategorie);
				request.setAttribute("CheckedMesenCours", "checked");
			}
		}else {
			articles = articleManager.listeArticles(contient, filtreCategorie);
		}
		request.setAttribute("listeVentesEnCours", articles);	
		
		
		//Redirection vers la JSP
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/PageAccueil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
