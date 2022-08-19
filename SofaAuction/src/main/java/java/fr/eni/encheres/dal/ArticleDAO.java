package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;

public interface ArticleDAO {
	public List<Article> listeVentesEnCours(String articleContient, String categorieLibelle);
	
	public List<Article> mesEncheresEnCoursDAO(int idUser,String articleContient, String categorieLibelle);
	
	public List<Article> mesEncheresRemporteesDAO(int idUser,String articleContient, String categorieLibelle);
	
	public List<Article> mesVentesEnCoursDAO(int idUser, String articleContient, String categorieLibelle);
	
	public List<Article> ventesNonDebuteesDAO(int idUser, String articleContient, String categorieLibelle);
	
	public List<Article> ventesTermineesDAO(int idUser, String articleContient, String categorieLibelle);
	
	public Article ArtcileEnVenteDAO(int articleId);

	public void addArticle (Article article, int idAdresse, int idCategorie) throws BusinessException;
}
