package fr.eni.encheres.servlets;

/**
 * Les codes disponibles sont entre 30000 et 39999
 */
public abstract class CodesResultatServlets {
	
	/**
	 * Format id utilisateur incorrect
	 */
	public static final int FORMAT_ID_UTILISATEUR_ERREUR=30000;
	/**
	 * Format utilisateur course incorrect
	 */
	public static final int FORMAT_UTILISATEUR_ERREUR = 30001;
	/**
	 * Le nouveau mot de passe et la confirmation ne sont pas identiques.
	 */
	public static final int CONFIRMATION_MOT_DE_PASSE_INCORRECTE= 30002;	
	/**
	 * Adresse par défault non reconnu
	 */
	public static final int ADRESSE_PAR_DEFAULT_ERREUR = 30003;
	/**
	 * Le mot de passe et la confirmation ne sont pas identiques.
	 */
	public static final int MOT_DE_PASSE_CONFIRMATION_INCORRECTE= 30004;
	
	/**
	 * Le mot de passe et la confirmation ne sont pas identiques.
	 */
	public static final int IDENTIDIANT_MOT_DE_PASSE_INCORRECT= 30005;
}