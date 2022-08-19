package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;


public class ArticleDAOJdbcImpl implements ArticleDAO{
	private static final String LISTE_VENTES_EN_COURS = "SELECT * "
			+ "FROM (ARTICLES a LEFT JOIN  CATEGORIES c ON a.no_categorie=c.no_categorie) LEFT JOIN UTILISATEURS u ON a.no_utilisateur=u.no_utilisateur "
			+ "WHERE nom_article LIKE '%' + ? + '%' AND c.libelle LIKE ?";
	private static final String MES_ENCHERES_EN_COURS="WITH EnchereArticle AS "
			+ "(SELECT e.no_utilisateur AS acheteur, e.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur as vendeur, a.no_categorie, a.photo "
			+ "FROM ENCHERES e LEFT JOIN ARTICLES a ON e.no_article=a.no_article WHERE e.no_utilisateur=?) "
			+ "SELECT * FROM (EnchereArticle ea LEFT JOIN CATEGORIES c ON ea.no_categorie=c.no_categorie) LEFT JOIN UTILISATEURS u ON ea.vendeur=u.no_utilisateur "
			+ "WHERE nom_article LIKE '%' + ? + '%' AND c.libelle LIKE ?";
	private static final String MES_ENCHERES_REMPORTEES="WITH EnchereArticle AS "
			+ "(SELECT e.no_utilisateur AS acheteur, e.no_article, e.montant_enchere, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur as vendeur, a.no_categorie,a.photo "
			+ "FROM ENCHERES e LEFT JOIN ARTICLES a ON e.no_article=a.no_article WHERE e.no_utilisateur=?) "
			+ "SELECT * FROM (EnchereArticle ea LEFT JOIN CATEGORIES c ON ea.no_categorie=c.no_categorie) LEFT JOIN UTILISATEURS u ON ea.vendeur=u.no_utilisateur "
			+ "WHERE (date_fin_encheres< ?) AND prix_vente = montant_enchere AND nom_article LIKE '%' + ? + '%' AND c.libelle LIKE ?";
	private static final String MES_VENTES_EN_COURS = "SELECT * "
			+ "FROM (ARTICLES a LEFT JOIN  CATEGORIES c ON a.no_categorie=c.no_categorie) LEFT JOIN UTILISATEURS u ON a.no_utilisateur=u.no_utilisateur "
			+ "WHERE (date_debut_encheres <= ? AND date_fin_encheres>= ?) AND nom_article LIKE '%' + ? + '%' AND c.libelle LIKE ? AND a.no_utilisateur=?";
	private static final String MES_VENTES_NON_DEBUTEES = "SELECT * "
			+ "FROM (ARTICLES a LEFT JOIN  CATEGORIES c ON a.no_categorie=c.no_categorie) LEFT JOIN UTILISATEURS u ON a.no_utilisateur=u.no_utilisateur "
			+ "WHERE date_debut_encheres > ? AND nom_article LIKE '%' + ? + '%' AND c.libelle LIKE ? AND a.no_utilisateur=?";
	private static final String MES_VENTES_TERMINEES = "SELECT * "
			+ "FROM (ARTICLES a LEFT JOIN  CATEGORIES c ON a.no_categorie=c.no_categorie) LEFT JOIN UTILISATEURS u ON a.no_utilisateur=u.no_utilisateur "
			+ "WHERE date_fin_encheres < ? AND nom_article LIKE '%' + ? + '%' AND c.libelle LIKE ? AND a.no_utilisateur=?";
	private static final String DETAIL_ARTICLE_EN_VENTE = "WITH ENCHERE_ACHETEUR AS "
			+ "(SELECT e.montant_enchere, u.no_utilisateur as acheteurId, u.pseudo as acheteur FROM ENCHERES e LEFT JOIN UTILISATEURS u ON e.no_utilisateur=u.no_utilisateur WHERE e.no_article=?) "
			+ "SELECT a.no_article, a.nom_article, a.description, c.libelle, a.prix_initial, a.prix_vente, a.date_fin_encheres, a.date_debut_encheres, u.no_utilisateur as vendeurId, u.pseudo as vendeur, d.code_postal, d.rue, d.ville,a.photo,ea.acheteurId, ea.acheteur "
			+ "FROM (((ARTICLES a LEFT JOIN ADRESSES d ON a.no_adresse=d.no_adresse) LEFT JOIN CATEGORIES c ON a.no_categorie=c.no_categorie) LEFT JOIN ENCHERE_ACHETEUR ea ON a.prix_vente=ea.montant_enchere) "
			+ "LEFT JOIN UTILISATEURS u ON a.no_utilisateur=u.no_utilisateur "
			+ "WHERE a.no_article=?";
	private static final String ADD_ARTICLE = "INSERT INTO Articles (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, no_adresse, photo) VALUES (?,?,?,?,?,0,?,?,?,?)";
	
	
	@Override
	public List<Article> listeVentesEnCours(String articleContient, String categorieLibelle) {
		List<Article> articles =new ArrayList<Article>();
		LocalDate maintenant = LocalDate.now();
		
		try (Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(LISTE_VENTES_EN_COURS);
			//stmt.setString(1, maintenant.toString());
			//stmt.setString(2, maintenant.toString());
			stmt.setString(1, articleContient);
			if(categorieLibelle.equalsIgnoreCase("Toutes")) {
				stmt.setString(2, "%");
			}else {
				stmt.setString(2, categorieLibelle);
			}
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Article article = new Article();
				article.setArticleId(rs.getInt("no_article"));
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
				article.setFinEncheres(rs.getDate("date_fin_encheres"));
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				article.setPhoto(rs.getString("photo"));
				article.setVendeur(new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo")));
				articles.add(article);	
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		return articles;
	}

	@Override
	public List<Article> mesEncheresEnCoursDAO(int idUser, String articleContient, String categorieLibelle) {
		List<Article> articles =new ArrayList<Article>();
		LocalDate maintenant = LocalDate.now();
		
		try (Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(MES_ENCHERES_EN_COURS);
			stmt.setInt(1, idUser);
			stmt.setString(2, maintenant.toString());
			stmt.setString(3, maintenant.toString());
			stmt.setString(4, articleContient);
			if(categorieLibelle.equalsIgnoreCase("Toutes")) {
				stmt.setString(5, "%");
			}else {
				stmt.setString(5, categorieLibelle);
			}
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Article article = new Article();
				article.setArticleId(rs.getInt("no_article"));
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
				article.setFinEncheres(rs.getDate("date_fin_encheres"));
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				article.setPhoto(rs.getString("photo"));
				article.setVendeur(new Utilisateur(rs.getInt("vendeur"), rs.getString("pseudo")));
				articles.add(article);	
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		return articles;
	}

	@Override
	public List<Article> mesEncheresRemporteesDAO(int idUser, String articleContient, String categorieLibelle) {
		List<Article> articles =new ArrayList<Article>();
		LocalDate maintenant = LocalDate.now();
		
		try (Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(MES_ENCHERES_REMPORTEES);
			stmt.setInt(1, idUser);
			stmt.setString(2, maintenant.toString());
			stmt.setString(3, articleContient);
			if(categorieLibelle.equalsIgnoreCase("Toutes")) {
				stmt.setString(4, "%");
			}else {
				stmt.setString(4, categorieLibelle);
			}
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Article article = new Article();
				article.setArticleId(rs.getInt("no_article"));
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
				article.setFinEncheres(rs.getDate("date_fin_encheres"));
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				article.setPhoto(rs.getString("photo"));
				article.setVendeur(new Utilisateur(rs.getInt("vendeur"), rs.getString("pseudo")));
				articles.add(article);	
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return articles;
	}

	@Override
	public List<Article> mesVentesEnCoursDAO(int idUser, String articleContient, String categorieLibelle) {
		List<Article> articles =new ArrayList<Article>();
		LocalDate maintenant = LocalDate.now();
		
		try (Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(MES_VENTES_EN_COURS);
			stmt.setString(1, maintenant.toString());
			stmt.setString(2, maintenant.toString());
			stmt.setString(3, articleContient);
			if(categorieLibelle.equalsIgnoreCase("Toutes")) {
				stmt.setString(4, "%");
			}else {
				stmt.setString(4, categorieLibelle);
			}
			stmt.setInt(5, idUser);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Article article = new Article();
				article.setArticleId(rs.getInt("no_article"));
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
				article.setFinEncheres(rs.getDate("date_fin_encheres"));
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				article.setPhoto(rs.getString("photo"));
				article.setVendeur(new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo")));
				articles.add(article);	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return articles;
	}

	@Override
	public List<Article> ventesNonDebuteesDAO(int idUser, String articleContient, String categorieLibelle) {
		List<Article> articles =new ArrayList<Article>();
		LocalDate maintenant = LocalDate.now();
		
		try (Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(MES_VENTES_NON_DEBUTEES);
			stmt.setString(1, maintenant.toString());
			stmt.setString(2, articleContient);
			if(categorieLibelle.equalsIgnoreCase("Toutes")) {
				stmt.setString(3, "%");
			}else {
				stmt.setString(3, categorieLibelle);
			}
			stmt.setInt(4, idUser);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Article article = new Article();
				article.setArticleId(rs.getInt("no_article"));
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
				article.setFinEncheres(rs.getDate("date_fin_encheres"));
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				article.setPhoto(rs.getString("photo"));
				article.setVendeur(new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo")));
				articles.add(article);	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return articles;
	}

	@Override
	public List<Article> ventesTermineesDAO(int idUser, String articleContient, String categorieLibelle) {
			List<Article> articles =new ArrayList<Article>();
			LocalDate maintenant = LocalDate.now();
			
			try (Connection cnx = ConnectionProvider.getConnection()){
				PreparedStatement stmt = cnx.prepareStatement(MES_VENTES_TERMINEES);
				stmt.setString(1, maintenant.toString());
				stmt.setString(2, articleContient);
				if(categorieLibelle.equalsIgnoreCase("Toutes")) {
					stmt.setString(3, "%");
				}else {
					stmt.setString(3, categorieLibelle);
				}
				stmt.setInt(4, idUser);
				ResultSet rs = stmt.executeQuery();
				
				while(rs.next()) {
					Article article = new Article();
					article.setArticleId(rs.getInt("no_article"));
					article.setNomArticle(rs.getString("nom_article"));
					article.setDescription(rs.getString("description"));
					article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
					article.setFinEncheres(rs.getDate("date_fin_encheres"));
					article.setMiseAPrix(rs.getInt("prix_initial"));
					article.setPrixVente(rs.getInt("prix_vente"));
					article.setPhoto(rs.getString("photo"));
					article.setVendeur(new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo")));
					articles.add(article);	
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return articles;

		}

	@Override
	public Article ArtcileEnVenteDAO(int articleId) {
		Article article=new Article();
		try (Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(DETAIL_ARTICLE_EN_VENTE);
			stmt.setInt(1, articleId);
			stmt.setInt(2, articleId);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				article.setArticleId(rs.getInt("no_article"));
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setCategorie(rs.getString("libelle"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
				article.setFinEncheres(rs.getDate("date_fin_encheres"));
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				article.setPhoto(rs.getString("photo"));
				article.setRetrait(new Adresse(rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville")));
				article.setVendeur(new Utilisateur(rs.getInt("vendeurId"), rs.getString("vendeur")));
				article.setAcheteur(new Utilisateur(rs.getInt("acheteurId"), rs.getString("acheteur")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return article;
	}
	
	//AJOUTER ARTICLE 
	@Override
	public void addArticle (Article article, int idAdresse, int idCategorie) throws BusinessException {
		
		try (Connection cnx = ConnectionProvider.getConnection()) 
		{
			
			PreparedStatement pstm = cnx.prepareStatement(ADD_ARTICLE);
			pstm.setString(1, article.getNomArticle());
			pstm.setString(2, article.getDescription());
			pstm.setDate(3, new java.sql.Date(article.getDateDebutEncheres().getTime()));
			pstm.setDate(4, new java.sql.Date(article.getFinEncheres().getTime()));
			pstm.setFloat(5, article.getMiseAPrix());
			pstm.setInt(6, article.getVendeur().getUtilisateurId());
			pstm.setInt(7, idCategorie);
			pstm.setInt(8, idAdresse);
			pstm.setString(9, article.getPhoto()); 

			pstm.executeUpdate();
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();            
			businessException.ajouterErreur(CodesResultatDAL.INSERTION_ARTICLE_ECHEC);
		}
	}
	

}
