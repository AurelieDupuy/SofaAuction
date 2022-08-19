package fr.eni.encheres.bll;

import java.util.Map;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;

public class UtilisateurManager {
	private UtilisateurDAO utilisateurDAO;

	public UtilisateurManager() {
		this.utilisateurDAO = DAOFactory.getUtilisateurDAO();
	}
	
	// Modifie les information sur un utilisateur dans la base de données 
	public void modifierUtilisateur(Utilisateur u) throws BusinessException {
		BusinessException businessException = new BusinessException();
		
		// contrôle de la comformité des données avant la modification de la BDD
		this.verifierPasNull(u,businessException);		// Vérification que les données obligatoires ne sont pas null
		this.verificationPseudo(u, businessException);	// Contrôle de l'unicité du pseudo
		this.verificationEmail(u, businessException);	// Contrôle de l'unicité de l'email

		if (!businessException.hasErreurs()) {
			this.utilisateurDAO.modifierUtilisateur(u);	// Modification de la BDD
		} else {
			throw businessException;
		}
	}
	
	// Vérification que les données obligatoires ne sont pas null
	private void verifierPasNull(Utilisateur u, BusinessException businessException) {
		if (u.getPseudo() == null || u.getPseudo().trim().length()<= 0 || u.getPseudo().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.PSEUDO_INCORRECT);
		}
		if (u.getNom() == null || u.getNom().trim().length()<= 0|| u.getNom().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.NOM_INCORRECT);
		}
		if (u.getPrenom() == null || u.getPrenom().trim().length()<= 0|| u.getPrenom().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.PRENOM_INCORRECT);
		}
		if (u.getEmail() == null || u.getEmail().trim().length()<= 0||u.getEmail().trim().length() > 60) {
			businessException.ajouterErreur(CodesResultatBLL.EMAIL_INCORRECT);
		}
		if (u.getTelephone().trim().length() > 15) {
			businessException.ajouterErreur(CodesResultatBLL.TELEPHONE_INCORRECT);
		}
	
		if (u.getMotDePasse() != null) {
			if(u.getMotDePasse().trim().length()<= 0||u.getMotDePasse().trim().length() > 30) {
				businessException.ajouterErreur(CodesResultatBLL.MOT_DE_PASSE_INCORRECT);
			}
		}
		if (u.getAdresse().getRue() == null || u.getAdresse().getRue().trim().length()<= 0|| u.getAdresse().getRue().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.RUE_INCORRECTE);
		}
		if (u.getAdresse().getCodePostal() == null || u.getAdresse().getCodePostal().trim().length()<= 0|| u.getAdresse().getCodePostal().trim().length() > 15) {
			businessException.ajouterErreur(CodesResultatBLL.CODE_POSTAL_INCORRECT);
		}
		if (u.getAdresse().getVille() == null || u.getAdresse().getVille().trim().length()<= 0|| u.getAdresse().getVille().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.VILLE_INCORRECTE);
			}
		}
	
	// Contrôle de l'unicité du pseudo
	private void verificationPseudo(Utilisateur u, BusinessException businessException) {
		Map<Integer, String> listePseudos = null;
		try {
			listePseudos = this.utilisateurDAO.selectAllPseudo();
		} catch (BusinessException e1) {
			e1.printStackTrace();
			for (int i : e1.getListeCodesErreur())
				businessException.ajouterErreur(i);
		}
		if (u.getUtilisateurId() > 0)
			listePseudos.remove(u.getUtilisateurId());

		if (listePseudos.containsValue(u.getPseudo())) {
			businessException.ajouterErreur(CodesResultatBLL.PSEUDO_DEJA_UTILISE);
		}
	}
	
	// Contrôle de l'unicité de l'email
	private void verificationEmail(Utilisateur u, BusinessException businessException) {
		Map<Integer, String> listeEmails = null;
		try {
			listeEmails = this.utilisateurDAO.selectAllEmail();
		} catch (BusinessException e1) {
			e1.printStackTrace();
			for (int i : e1.getListeCodesErreur())
				businessException.ajouterErreur(i);
		}
		if (u.getUtilisateurId() > 0)
			listeEmails.remove(u.getUtilisateurId());

		if (listeEmails.containsValue(u.getEmail())) {
			businessException.ajouterErreur(CodesResultatBLL.EMAIL_DEJA_UTILISE);
		}
	}
	
	
	private void verifierPasNullInscription(Utilisateur u, Adresse adresse,BusinessException businessException) {
		if (u.getPseudo() == null || u.getPseudo().trim().length()<= 0 || u.getPseudo().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.PSEUDO_INCORRECT);
		}
		if (u.getNom() == null || u.getNom().trim().length()<= 0|| u.getNom().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.NOM_INCORRECT);
		}
		if (u.getPrenom() == null || u.getPrenom().trim().length()<= 0|| u.getPrenom().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.PRENOM_INCORRECT);
		}
		if (u.getEmail().trim().length()<= 0 || u.getEmail().trim().length() > 60) {
			businessException.ajouterErreur(CodesResultatBLL.EMAIL_INCORRECT);
		}
		if (u.getTelephone().trim().length() > 15) {
			businessException.ajouterErreur(CodesResultatBLL.TELEPHONE_INCORRECT);
		}
		if (u.getMotDePasse() != null) {
			if(u.getMotDePasse().trim().length()<= 0||u.getMotDePasse().trim().length() > 30) {
				businessException.ajouterErreur(CodesResultatBLL.MOT_DE_PASSE_INCORRECT);
			}
		}
		if (adresse.getRue() == null || adresse.getRue().trim().length()<= 0|| adresse.getRue().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.RUE_INCORRECTE);
		}
		if (adresse.getCodePostal() == null || adresse.getCodePostal().trim().length()<= 0|| adresse.getCodePostal().trim().length() > 15) {
			businessException.ajouterErreur(CodesResultatBLL.CODE_POSTAL_INCORRECT);
		}
		if (adresse.getVille() == null || adresse.getVille().trim().length()<= 0|| adresse.getVille().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.VILLE_INCORRECTE);
			}
		}
	


	public void supprimerUtilisateur(int id) throws BusinessException {
		this.utilisateurDAO.deleteUtilisateur(id);
	}


	public Utilisateur selectionnerUtilisateur(int id) throws BusinessException {
		return this.utilisateurDAO.selectById(id);
	}
	
	public Utilisateur loginPseudo(String pseudo, String password) throws BusinessException {//TODO
		BusinessException businessException = new BusinessException();

		if (!businessException.hasErreurs()) {
			this.utilisateurDAO.loginPseudo(pseudo, password);
		} else {
			throw businessException;
		}

		return utilisateurDAO.loginPseudo(pseudo, password);
	}
	
	
	public Utilisateur loginEmail(String email, String password) throws BusinessException {//TODO
		BusinessException businessException = new BusinessException();

		if (!businessException.hasErreurs()) {
			this.utilisateurDAO.loginEmail(email, password);
		} else {
			throw businessException;
		}

		return utilisateurDAO.loginEmail(email, password);
	}

	
	public void addUser(Utilisateur utilisateur, Adresse adresse) throws BusinessException { //TODO 

		BusinessException businessException = new BusinessException();
		this.verifierPasNullInscription(utilisateur, adresse,businessException);
		this.verificationPseudo(utilisateur, businessException);
		this.verificationEmail(utilisateur, businessException);

		if (!businessException.hasErreurs()) {
			this.utilisateurDAO.addUser(utilisateur, adresse);
		} else {
			throw businessException;
		}
	}
}
