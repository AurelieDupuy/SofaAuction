package fr.eni.encheres.dal;

/**
 * Les codes disponibles sont entre 10000 et 19999
 */
public abstract class CodesResultatDAL {
	
	/**
	 * Echec général quand tentative d'ajouter un objet null
	 */
	//public static final int INSERT_OBJET_NULL=10000;
	
	/**
	 * Echec général quand erreur non gérée à l'insertion 
	 */
	//public static final int INSERT_OBJET_ECHEC=10001;

	/**
	 * Echec de la lecture des emails
	 */
	public static final int LECTURE_EMAILS_ECHEC = 10001;
	/**
	 * Echec de la lecture des emails
	 */
	public static final int LECTURE_PSEUDOS_ECHEC = 10002;
	/**
	 * Echec de la lecture d'un utilisateur
	 */
	public static final int LECTURE_UTILISATEUR_ECHEC = 10003;
	/**
	 * Liste de course inexistante
	 */
	//public static final int LECTURE_LISTE_INEXISTANTE = 10004;
	/**
	 * Erreur à la suppression d'un utilisateur
	 */
	public static final int SUPPRESSION_UTILISATEUR_ERREUR = 10005;
	/**
	 * Erreur à la suppression d'une liste
	 */
	//public static final int SUPPRESSION_LISTE_ERREUR = 10006;
	/**
	 * Erreur à la mise à jour d'un utilisateur
	 */
	public static final int MODIFICATION_UTILISATEUR_ET_ADRESSE_ERREUR = 10007;
	/**
	 * Erreur au décochage d'un article
	 */
	//public static final int DECOCHE_ARTICLE_ERREUR = 10008;
	/**
	 * Erreur au décochage de tous les articles d'une liste
	 */
	//public static final int DECOCHE_ARTICLES_ERREUR = 10009;
	
	/**
	 * Erreur de connection à la base de données
	 */
	public static final int ERREUR_CONNECTION= 10010;
	/**
	 * Erreur de connection à la base de données
	 */
	public static final int ECHEC_ENREGISTREMENT_ENCHERE= 10011;
	/** 
	 * Echec de l'insertion du nouvel article
	 */
	public static final int INSERTION_ARTICLE_ECHEC = 10012;
	/** 
	 * Echec de l'insertion du nouvel article
	 */
	public static final int RECUPERATION_ADRESSE_ECHEC = 10013;
	/** 
	 * Echec de l'insertion du nouvel article
	 */
	public static final int INSERTION_ADRESSE_ECHEC = 10014;
	/** 
	 * Echec de l'insertion du nouvel article
	 */
	public static final int SELECTION_CATEGORIE_ECHEC = 10015;
	
	
}











