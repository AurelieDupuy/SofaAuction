package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.DAOFactory;

public class CategorieManager {
	private CategorieDAO categorieDao;
		
	// constructeur
	public CategorieManager() {
		this.categorieDao = DAOFactory.getCategorieDAO();
	}

	public List<Categorie> listeDesCategorie() {
		
		return categorieDao.listCategorie() ;
	}
	
}
