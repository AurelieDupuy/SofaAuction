package fr.eni.encheres.bll;

/**
 * Les codes disponibles sont entre 20000 et 29999
 */
public abstract class CodesResultatBLL {
	
	/**
	 * Echec le pseudo est nul ou trop long.
	 */
	public static final int PSEUDO_INCORRECT = 20000;
	/**
	 * Echec le nom est nul ou trop long.
	 */
	public static final int NOM_INCORRECT = 20001;
	/**
	 * Echec le prénom est nul ou trop long.
	 */
	public static final int PRENOM_INCORRECT = 20002;
	/**
	 * Echec l'email est nul ou trop long.
	 */
	public static final int EMAIL_INCORRECT = 20003;
	/**
	 * Echec le numéro de téléphone est trop long.
	 */
	public static final int TELEPHONE_INCORRECT = 20004;
	/**
	 * Echec le mot de passe est nul ou trop long.
	 */
	public static final int MOT_DE_PASSE_INCORRECT = 20005;
	/**
	 * Echec la rue est nulle ou trop longue.
	 */
	public static final int RUE_INCORRECTE = 20006;
	/**
	 * Echec le code postal est nul ou trop long.
	 */
	public static final int CODE_POSTAL_INCORRECT = 20007;
	/**
	 * Echec la ville est nulle ou trop longue.
	 */
	public static final int VILLE_INCORRECTE = 20008;
	/**
	 * Echec l'email est déjà utilisé
	 */
	public static final int EMAIL_DEJA_UTILISE=20009;
	/**
	 * Echec le pseudo est déjà utilisé
	 */
	public static final int PSEUDO_DEJA_UTILISE = 20010;
	
	/**
	 * Echec votre crédit de point n'est pas suffisant pour couvrir l'enchère
	 */
	public static final int CREDIT_INSUFFISANT = 20011;
	/**
	 * Echec la vente est cloturée
	 */
	public static final int VENTE_CLOSE = 20012;
	/**
	 * Echec le montant de l'enchère n'est pas suffisant
	 */
	public static final int MONTANT_ENCHERE_INSUFFISANT = 20013;
	/**
	 * Echec Insertion article : nom
	 */
	public static final int INSERTION_NOM_ARTICLE_INCORRECTE = 20014;
	/**
	 * Echec Insertion article : description
	 */
	public static final int INSERTION_DESCRIPTION_ARTICLE_INCORRECTE = 20015;
	/**
	 * Echec Insertion article : date
	 */
	public static final int INSERTION_DATE_ARTICLE_INCORRECTE = 20016;
	/**
	 * Echec Insertion article : retrait
	 */
	public static final int INSERTION_RETRAIT_ARTICLE_INCORRECTE = 20017;
	/**
	 * Echec Insertion article : catégorie
	 */
	public static final int INSERTION_CATEGORIE_ARTICLE_INCORRECTE = 20018;
	/**
	 * Echec Insertion article : prix
	 */
	public static final int INSERTION_PRIX_ARTICLE_INCORRECTE = 20019;
	/**
	 * Echec Insertion article : rue
	 */
	public static final int INSERTION_RUE_ARTICLE_INCORRECTE = 20020;
	/**
	 * Echec Insertion article : cp
	 */
	public static final int INSERTION_CP_ARTICLE_INCORRECTE = 20021;
	/**
	 * Echec Insertion article : ville
	 */
	public static final int INSERTION_VILLE_ARTICLE_INCORRECTE = 20022;
		
}
