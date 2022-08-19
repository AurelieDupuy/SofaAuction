package fr.eni.encheres.bll;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.dal.AdresseDAO;
import fr.eni.encheres.dal.DAOFactory;


public class AdresseManager {
	private AdresseDAO adresseDAO;
	
	public AdresseManager() {
		this.adresseDAO = DAOFactory.getAdresseDAO();
	}

	public Adresse selectionnerAdresse(int adresseId) throws BusinessException{
		return this.adresseDAO.selectById(adresseId);
	}

	public void modifierAdresse(Adresse adr) throws BusinessException{
		this.adresseDAO.modifierAdresse(adr);
	}

	public void supprimerAdresse(int adresseId) throws BusinessException {
		this.adresseDAO.deleteAdresse(adresseId);
	}

}
