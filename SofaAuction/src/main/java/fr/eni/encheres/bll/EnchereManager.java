package fr.eni.encheres.bll;


import java.util.Date;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;

import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EnchereDAO;

public class EnchereManager {
	private EnchereDAO enchereDao;
	
	// constructeur
	public EnchereManager() {
		this.enchereDao = DAOFactory.getEnchereDAO();
	}
	
	public void ajouterEnchere(Article article, int prixEnchere, Utilisateur user) throws BusinessException {
		BusinessException businessException = new BusinessException();
		
		this.validerMontantEnchere(article, prixEnchere, businessException);
		this.validerDate(article.getFinEncheres(), businessException);
		this.validerCredit(user.getUtilisateurId(), prixEnchere, businessException);
		
		if (!businessException.hasErreurs()) {
				enchereDao.encherir(article, prixEnchere, user);			
		}else {
			throw businessException;
		}
		
	}

	private void validerCredit(int utilisateurId, int prixEnchere, BusinessException businessException) throws BusinessException {
		int credit;
		credit = enchereDao.creditAvantEnchere(utilisateurId);

		if(credit-prixEnchere<0) {
			businessException.ajouterErreur(CodesResultatBLL.CREDIT_INSUFFISANT);
		}
	}

	private void validerDate(Date finEncheres, BusinessException businessException) {
		Date maintenant= new Date();
		if(maintenant.compareTo(finEncheres)>0) {
			businessException.ajouterErreur(CodesResultatBLL.VENTE_CLOSE);
		}
	}

	private void validerMontantEnchere(Article article, int prixEnchere, BusinessException businessException) {
		if(prixEnchere<=article.getPrixVente() || prixEnchere<article.getMiseAPrix() ) {
			businessException.ajouterErreur(CodesResultatBLL.MONTANT_ENCHERE_INSUFFISANT);
		}
		
	}
}
