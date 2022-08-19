package fr.eni.encheres.bo;


import java.util.Date;


public class Article {
	private int ArticleId;
	private String nomArticle;
	private String description;
	private Date dateDebutEncheres;
	private Date finEncheres;
	private int miseAPrix;
	private int prixVente;
	private String categorie;
	private Utilisateur vendeur;
	private Adresse retrait;
	private String photo;
	private Utilisateur acheteur;
	
	// Constructeurs
	public Article() {
	}
	
	public Article(String nomArticle, String description, Date dateDebutEncheres, Date finEncheres,	int miseAPrix, String categorie, Utilisateur vendeur, Adresse retrait) {
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.finEncheres = finEncheres;
		this.miseAPrix = miseAPrix;
		this.categorie = categorie;
		this.vendeur = vendeur;
		this.retrait=retrait;
	}
	
	public Article(int articleId, String nomArticle, String description, Date dateDebutEncheres, Date finEncheres,	int miseAPrix, int prixVente, String categorie, Utilisateur vendeur,String photo) {
		this.ArticleId = articleId;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.finEncheres = finEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.categorie = categorie;
		this.vendeur = vendeur;
		this.photo = photo;

	}
	
	public Article(String nomArticle, String description, Date dateDebutEncheres, Date finEncheres,	int miseAPrix, int prixVente, String categorie,
			Utilisateur vendeur, Adresse retrait) {
		this(nomArticle, description, dateDebutEncheres, finEncheres, miseAPrix, categorie, vendeur, retrait);
		this.prixVente = prixVente;

	}

	// getter et setter
	public String getNomArticle() {
		return nomArticle;
	}


	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getDateDebutEncheres() {
		return dateDebutEncheres;
	}


	public void setDateDebutEncheres(Date dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}


	public Date getFinEncheres() {
		return finEncheres;
	}


	public void setFinEncheres(Date finEncheres) {
		this.finEncheres = finEncheres;
	}


	public float getMiseAPrix() {
		return miseAPrix;
	}


	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}


	public float getPrixVente() {
		return prixVente;
	}


	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public Utilisateur getVendeur() {
		return vendeur;
	}


	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public Adresse getRetrait() {
		return retrait;
	}

	public void setRetrait(Adresse retrait) {
		this.retrait = retrait;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getArticleId() {
		return ArticleId;
	}

	public void setArticleId(int articleId) {
		ArticleId = articleId;
	}

	public Utilisateur getAcheteur() {
		return acheteur;
	}

	public void setAcheteur(Utilisateur acheteur) {
		this.acheteur = acheteur;
	}
	
	
	
}
