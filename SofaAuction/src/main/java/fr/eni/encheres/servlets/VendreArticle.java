package fr.eni.encheres.servlets;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class VendreArticle
 */
@WebServlet("/VendreArticle")
public class VendreArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	/**
	 * DOGET : redirige vers la JSP : affiche les catégories
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Integer> listeCodesErreur = null;

		if (request.getAttribute("listeCodesErreur") != null) {
			listeCodesErreur = (ArrayList<Integer>) request.getAttribute("listeCodesErreur");
		} else {
			listeCodesErreur = new ArrayList<Integer>();
		}

		// Initialisation des catégories
		CategorieManager categorieManager = new CategorieManager();
		List<Categorie> categories = new ArrayList<Categorie>();
		categories = categorieManager.listeDesCategorie();
		request.setAttribute("listeCategorie", categories);

		// Initialisation de l'adresse du vendeur
		int id = 0;
		Utilisateur u = new Utilisateur();
		UtilisateurManager utilisateurManager = new UtilisateurManager();

		try {
			//id = Integer.parseInt(request.getParameter("id"));
			id = ((Utilisateur) (request.getSession().getAttribute("user"))).getUtilisateurId(); // A COMMIT
		} catch (NumberFormatException e) {
			e.printStackTrace();
			listeCodesErreur.add(CodesResultatServlets.ADRESSE_PAR_DEFAULT_ERREUR);
		}

		if (listeCodesErreur.size() > 0) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/VendreArticle.jsp");
			rd.forward(request, response);
		}

		try {
			u = utilisateurManager.selectionnerUtilisateur(id);
			request.setAttribute("utilisateur", u);
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/VendreArticle.jsp");
		rd.forward(request, response);
	}


	/**
	 * DOPOST : enregristre nouvel article à vendre
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Article art = new Article();
		Adresse retrait = new Adresse();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {

			// Adresse
			retrait.setRue(request.getParameter("SaisieRuenom"));
			retrait.setCodePostal(request.getParameter("SaisieCodepostal"));
			retrait.setVille(request.getParameter("SaisieVille"));
			art.setRetrait(retrait);

			// Article
			art.setNomArticle(request.getParameter("SaisieArticlenom"));
			art.setDescription(request.getParameter("SaisieDescriptionArticle"));
			art.setCategorie(request.getParameter("Saisiecategorie"));
			art.setMiseAPrix(Integer.parseInt(request.getParameter("SaisiePrix")));
			art.setVendeur((Utilisateur) (request.getSession().getAttribute("user")));
			try {
				art.setDateDebutEncheres(sdf.parse(request.getParameter("SaisiedateDebut")));
				art.setFinEncheres(sdf.parse(request.getParameter("SaisiedateFin")));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// Ajouter
			ArticleManager articlemanager = new ArticleManager();
			articlemanager.addArticle(art);

			RequestDispatcher rd = request.getRequestDispatcher("/AddPhoto");
			rd.forward(request, response);

		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());// LIGNE RAJOUTER POUR ERREUR
			doGet(request, response); // LIGNE RAJOUTER POUR ERREUR

		}
		
		
	}

}
