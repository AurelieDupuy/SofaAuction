package fr.eni.encheres.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Integer> listeCodesErreur = null;

		if (request.getAttribute("listeCodesErreur") != null) {
			listeCodesErreur = (ArrayList<Integer>) request.getAttribute("listeCodesErreur");
			request.setAttribute("listeCodesErreur", listeCodesErreur);
		}
		getServletContext().getRequestDispatcher("/WEB-INF/JSP/Login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BusinessException businessException = new BusinessException();
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		String identifiant;
		String password;
		Utilisateur user = new Utilisateur();

		try {
			identifiant = request.getParameter("identifiant");
			password = request.getParameter("password");

			if (identifiant.contains("@")) {
				user = utilisateurManager.loginEmail(identifiant, password);
			} else {
				user = utilisateurManager.loginPseudo(identifiant, password);
			}

			if (user != null) {

				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				session.setMaxInactiveInterval(5 * 60);
				response.sendRedirect("PageAccueil");

				// SE SOUVENIR DE MOI, A COMPLETER

				/*
				 * if ("remember" != null) { Cookie cookieId = new Cookie("identifiant",
				 * user.getEmail()); cookieId.setMaxAge(24 * 3600);
				 * response.addCookie(cookieId); }
				 */

			} else {
				/*
				 * HttpSession session = request.getSession();
				 * session.setAttribute("errorLogin", "error"); response.sendRedirect("Login");
				 */
				businessException.ajouterErreur(CodesResultatServlets.IDENTIDIANT_MOT_DE_PASSE_INCORRECT);
				throw businessException;

			}
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			doGet(request, response);

		}
	}
}
