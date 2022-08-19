package fr.eni.encheres.dal;



import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;

public interface EnchereDAO {
	public int creditAvantEnchere(int idUtilisateur) throws BusinessException;
	
	public void encherir(Article article, int prixEnchere, Utilisateur user) throws BusinessException;
}
