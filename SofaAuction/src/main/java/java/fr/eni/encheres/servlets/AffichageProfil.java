package fr.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletProfil
 */
@WebServlet("/AffichageProfil")
public class AffichageProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Utilisateur u = new Utilisateur();
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		int id = 0;
		List<Integer> listeCodesErreur = new ArrayList<>();

		try {
			if (request.getParameter("id") == null) {
				id = ((Utilisateur) (request.getSession().getAttribute("user"))).getUtilisateurId();
			} else {
				id = Integer.parseInt(request.getParameter("id"));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			listeCodesErreur.add(CodesResultatServlets.FORMAT_ID_UTILISATEUR_ERREUR);
		}
		if (listeCodesErreur.size() > 0) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
		} else {
			try {
				u = utilisateurManager.selectionnerUtilisateur(id);
				request.setAttribute("utilisateur", u);
				getServletContext().getRequestDispatcher("/WEB-INF/JSP/PageAffichageProfil.jsp").forward(request,
						response);
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
