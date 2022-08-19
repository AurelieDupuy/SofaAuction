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

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletModifierProfil
 */
@WebServlet(urlPatterns = { "/modifierProfil", "/supprimer" })
public class ModifierProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// Déclaration de variable
		Utilisateur u = new Utilisateur();
		UtilisateurManager utilisateurManager = new UtilisateurManager();
		int id = 0;
		List<Integer> listeCodesErreur = null;

		// Vérification s'il y a une liste d'erreur en attribut et récupération de celle-ci
		if (request.getAttribute("listeCodesErreur") != null) {
			listeCodesErreur = (ArrayList<Integer>) request.getAttribute("listeCodesErreur");
		} else {
			listeCodesErreur = new ArrayList<Integer>();
		}
		
		// Récuppération de l'Id de l'utilisateur loggé
		try {
			id = ((Utilisateur) (request.getSession().getAttribute("user"))).getUtilisateurId();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			listeCodesErreur.add(CodesResultatServlets.FORMAT_ID_UTILISATEUR_ERREUR);
		}
		
		//Renvoie de la liste d'erreur à la JSP si présente
		if (listeCodesErreur.size() > 0) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			getServletContext().getRequestDispatcher("/WEB-INF/JSP/PageModifierProfil.jsp").forward(request, response);
		}
		
		try {
			u = utilisateurManager.selectionnerUtilisateur(id);
			request.setAttribute("utilisateur", u);
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		}
		
		getServletContext().getRequestDispatcher("/WEB-INF/JSP/PageModifierProfil.jsp").forward(request, response);
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		// Modification du profil utilisateur
		if (request.getParameter("action").equalsIgnoreCase("enregistrer")) {
			// Déclaration de variable
			Utilisateur u = null;
			Adresse adresse;
			String motDePasse = null;
			UtilisateurManager utilisateurManager = new UtilisateurManager();
			BusinessException businessException = new BusinessException();

			try {
				// Vérifier que la confirmation du mot de passe est correcte
				String passwordNew = request.getParameter("passwordNew");
				String confirmation = request.getParameter("confirmation");

				if (confirmation.trim().equalsIgnoreCase("") || passwordNew.trim().equalsIgnoreCase("")) {
					motDePasse = ((Utilisateur) request.getSession().getAttribute("user")).getMotDePasse();
				} else if (confirmation.equals(passwordNew)) {
					motDePasse = request.getParameter("passwordNew");
				} else {
					// cas où la confirmation est incorrect
					businessException.ajouterErreur(CodesResultatServlets.CONFIRMATION_MOT_DE_PASSE_INCORRECTE);
					u=(Utilisateur)(request.getSession().getAttribute("user"));
					throw businessException;
				}
				
				// Création d'un objet Utilisateur avec les modification apporté sur le profil
				adresse = new Adresse(request.getParameter("rue"), request.getParameter("codePostal"),
						request.getParameter("ville"));
				u = new Utilisateur(request.getParameter("pseudo"), request.getParameter("nom"),
						request.getParameter("prenom"), request.getParameter("email"),
						request.getParameter("telephone"), adresse, motDePasse,
						((Utilisateur) (request.getSession().getAttribute("user"))).getCredit());

				u.setUtilisateurId(((Utilisateur) (request.getSession().getAttribute("user"))).getUtilisateurId());

				// Passage à la BLL pour modification dans la BDD
				utilisateurManager.modifierUtilisateur(u);
				
				// Retour à la page "AffichageProfil"
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/AffichageProfil");
				rd.forward(request, response);

			} catch (BusinessException e) {
				// cas où il y a des erreurs : passage en attribut de la liste d'erreur et retour sur le doGet pour ré-afficher la page Modifier profil avec La liste d'erreur
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				request.setAttribute("utilisateur", u);
				doGet(request, response);
			}
		}

		// Suppression du compte
		if (request.getParameter("action").equalsIgnoreCase("supprimer mon compte")) {

			UtilisateurManager utilisateurManager = new UtilisateurManager();
			int id;
			id = ((Utilisateur) (request.getSession().getAttribute("user"))).getUtilisateurId();

			try {
				utilisateurManager.supprimerUtilisateur(id);
				request.getSession().invalidate();
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/PageAccueil");
				rd.forward(request, response);
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				getServletContext().getRequestDispatcher("/WEB-INF/JSP/PageModifierProfil.jsp").forward(request,
						response);
			}
		}
	}
}
