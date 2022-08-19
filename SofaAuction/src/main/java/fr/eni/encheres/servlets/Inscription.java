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
import javax.servlet.http.HttpSession;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class Inscription
 */
@WebServlet("/Inscription")
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Vérification s'il y a une liste d'erreur en paramètre
		List<Integer> listeCodesErreur = null;

		if (request.getAttribute("listeCodesErreur") != null) {
			// Transmission de la liste d'erreur à la JSP
			listeCodesErreur = (ArrayList<Integer>)request.getAttribute("listeCodesErreur");
			request.setAttribute("listeCodesErreur", listeCodesErreur);
		}
		
		getServletContext().getRequestDispatcher("/WEB-INF/JSP/Inscription.jsp").forward(request, response);
	}
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BusinessException businessException = new BusinessException();
		Utilisateur user = null;
		UtilisateurManager utilisateurManager = new UtilisateurManager();	
		Adresse ad;
		
		// Vérification que la confirmation du mot de passe est correcte
		try {
			String ps = request.getParameter("password");
			String cps = request.getParameter("confPassword");
			
		if (!cps.equals(ps)) {
			businessException.ajouterErreur(CodesResultatServlets.MOT_DE_PASSE_CONFIRMATION_INCORRECTE);
			throw businessException;
		} else {
		
			// création d'un objet utilisateur avec les nouvelles informations sur le profil
			user=new Utilisateur(request.getParameter("pseudo"),
					          request.getParameter("nom"),
					          request.getParameter("prenom"),
					          request.getParameter("email"),
					          request.getParameter("telephone"),
					          request.getParameter("password"),
					          0);
			
			ad = new Adresse(
				request.getParameter("rue"),
		        request.getParameter("CodePostal"),
		        request.getParameter("ville")	
		        );
	
			utilisateurManager.addUser(user, ad);
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/PageAccueil");
			rd.forward(request, response);
			}
		}catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			doGet(request, response);
		}
			
		}
		
	}


