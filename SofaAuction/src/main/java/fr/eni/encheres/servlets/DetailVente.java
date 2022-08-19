package fr.eni.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;



/**
 * Servlet implementation class DetailVente
 */
@WebServlet("/DetailVente")
public class DetailVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArticleManager articleManager = new ArticleManager();
		Article a;
		
		a=articleManager.ArtcileEnVente(Integer.valueOf(request.getParameter("article")));
		request.setAttribute("article", a);
		
		//Redirection vers la JSP
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/DetailVente.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArticleManager articleManager = new ArticleManager();
		
		Article article = articleManager.ArtcileEnVente(Integer.valueOf(request.getParameter("articleEnVente")));
		int enchere=Integer.valueOf(request.getParameter("enchere"));
		Utilisateur user= (Utilisateur)(request.getSession().getAttribute("user"));
		
		try {
			articleManager.ajouterEnchere(article, enchere, user);
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			e.printStackTrace();
		}
		
		// changer le credit dans user
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		Utilisateur utilisateur=null;
		try {
			utilisateur = utilisateurManager.selectionnerUtilisateur(user.getUtilisateurId());
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		request.getSession().setAttribute("user", utilisateur);
		
		// renvoie de la fiche article modifier
		Article articleAJours = articleManager.ArtcileEnVente(article.getArticleId());
		request.setAttribute("article", articleAJours);
		
		//Redirection vers la JSP
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/DetailVente.jsp");
		rd.forward(request, response);
		
	}

}
