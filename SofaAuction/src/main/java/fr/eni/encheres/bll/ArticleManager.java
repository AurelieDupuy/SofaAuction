package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.AdresseDAO;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.DAOFactory;

public class ArticleManager {
	private ArticleDAO articleDao;
	private AdresseDAO adresseDao; 
	private CategorieDAO categorieDAO; 
	
	// constructeur
	public ArticleManager() {
		this.articleDao = DAOFactory.getArticleDAO();
		this.adresseDao = DAOFactory.getAdresseDAO(); 
		this.categorieDAO = DAOFactory.getCategorieDAO(); 
	}

	public List<Article> listeArticles(String articleContient, String categorieLibelle){
		return articleDao.listeVentesEnCours(articleContient, categorieLibelle) ;
	}
	
	public List<Article> mesEncheresEnCours(int idUser, String articleContient, String categorieLibelle){
		return articleDao.mesEncheresEnCoursDAO(idUser, articleContient, categorieLibelle);
	}
	
	public List<Article> mesEncheresRemportees(int idUser,String articleContient,String categorieLibelle){
		return articleDao.mesEncheresRemporteesDAO(idUser, articleContient, categorieLibelle) ;
	}
	
	public List<Article> mesVentesEnCours(int idUser,String articleContient,String categorieLibelle){
		return articleDao.mesVentesEnCoursDAO(idUser, articleContient, categorieLibelle) ;
	}
	
	public List<Article> ventesNonDebutees(int idUser,String articleContient,String categorieLibelle){
		return articleDao.ventesNonDebuteesDAO(idUser, articleContient, categorieLibelle) ;
	}
	
	public List<Article> ventesTerminees(int idUser,String articleContient,String categorieLibelle){
		return articleDao.ventesTermineesDAO(idUser, articleContient, categorieLibelle) ;
	}
	
	public Article ArtcileEnVente(int articleId) {
		return articleDao.ArtcileEnVenteDAO(articleId);
	}
	
	public void ajouterEnchere(Article article, int prixEnchere, Utilisateur user) throws BusinessException{
		EnchereManager enchereManager = new EnchereManager();
		enchereManager.ajouterEnchere(article, prixEnchere, user);
	}
	
	//AJOUTER ARTICLE 
	public void addArticle (Article article) throws BusinessException{
		//vérification
		BusinessException businessException = new BusinessException();
        this.verifierPasNull(article, businessException); 
        int idAdresse = this.recuperIDAdresse(article, businessException);
        int idCategorie = this.recuepereIDCategorie(article, businessException);
       
        //Ajouter Article
        if (!businessException.hasErreurs()) {
            this.articleDao.addArticle (article, idAdresse, idCategorie); 
        } else {
            throw businessException;
        }
    }
	//VERIF NULL
	private void verifierPasNull(Article article, BusinessException businessException) {
		if (article.getNomArticle() == null || article.getNomArticle().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.INSERTION_NOM_ARTICLE_INCORRECTE); 
		}
		if (article.getDescription() == null || article.getDescription().trim().length() > 300) {
			businessException.ajouterErreur(CodesResultatBLL.INSERTION_DESCRIPTION_ARTICLE_INCORRECTE); 
		}
		if (article.getDateDebutEncheres() == null) {
			businessException.ajouterErreur(CodesResultatBLL.INSERTION_DATE_ARTICLE_INCORRECTE); 
		}
		if (article.getFinEncheres() == null) {
			businessException.ajouterErreur(CodesResultatBLL.INSERTION_DATE_ARTICLE_INCORRECTE); 
		}
		if (article.getRetrait() == null) {
			businessException.ajouterErreur(CodesResultatBLL.INSERTION_RETRAIT_ARTICLE_INCORRECTE); 
		}
		if (article.getCategorie() == null) {
			businessException.ajouterErreur(CodesResultatBLL.INSERTION_CATEGORIE_ARTICLE_INCORRECTE); 
		}
		if (article.getPrixVente() <0) {
			businessException.ajouterErreur(CodesResultatBLL.INSERTION_PRIX_ARTICLE_INCORRECTE); 
		}
		if (article.getRetrait().getRue()== null || article.getRetrait().getRue().trim().length()<= 0|| article.getRetrait().getRue().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.INSERTION_RUE_ARTICLE_INCORRECTE); 
		}
		if (article.getRetrait().getCodePostal()== null|| article.getRetrait().getCodePostal().trim().length()<= 0|| article.getRetrait().getCodePostal().trim().length() > 15) {
			businessException.ajouterErreur(CodesResultatBLL.INSERTION_CP_ARTICLE_INCORRECTE); 
		}
		if (article.getRetrait().getVille()== null|| article.getRetrait().getVille().trim().length()<= 0|| article.getRetrait().getVille().trim().length() > 30) {
			businessException.ajouterErreur(CodesResultatBLL.INSERTION_VILLE_ARTICLE_INCORRECTE); 
		}
	
	}

	// RECUPERER ID ADRESSE
	private int recuperIDAdresse(Article article, BusinessException businessException) {
		
		 int id=adresseDao.recupererIDAdresse(article.getRetrait().getRue(), article.getRetrait().getCodePostal(), article.getRetrait().getVille());		 
		 return id ;
	}	
	
	// RECUPERER ID CATEGORIE
	private int recuepereIDCategorie(Article article, BusinessException businessException) {
		
		int id	= categorieDAO.recupereID(article.getCategorie());	    
		return id ;
	}	
}
