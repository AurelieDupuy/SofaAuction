package fr.eni.encheres.bo;

public class Utilisateur {
	private int utilisateurId;
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private Adresse adresse;
	private String motDePasse;
	private int credit;
	private boolean administration;
	
	// constructeurs
	public Utilisateur() {
	}
	
	public Utilisateur(int utilisateurId, String pseudo) {
		this.utilisateurId = utilisateurId;
		this.pseudo = pseudo;
	}
	
	public Utilisateur(int utilisateurId, String pseudo, String nom, String prenom, String email, String telephone,
			Adresse adresse, String motDePasse, int credit, boolean administration) {
		super();
		this.utilisateurId = utilisateurId;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.adresse = adresse;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administration = administration;
	}
	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone,
			Adresse adresse, String motDePasse, int credit) {
		super();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.adresse = adresse;
		this.motDePasse = motDePasse;
		this.credit = credit;
	}
	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone,
		 String motDePasse, int credit, boolean administration) {
		super();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administration = administration;
	}
	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone,
			 String motDePasse, int credit) {
			super();
			this.pseudo = pseudo;
			this.nom = nom;
			this.prenom = prenom;
			this.email = email;
			this.telephone = telephone;
			this.motDePasse = motDePasse;
			this.credit = credit;
		}

	public int getUtilisateurId() {
		return utilisateurId;
	}

	public void setUtilisateurId(int utilisateurId) {
		this.utilisateurId = utilisateurId;
	}

	public String getPseudo() {
		return pseudo;
	}


	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Adresse getAdresse() {
		return adresse;
	}
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}


	public boolean isAdministration() {
		return administration;
	}

	public void setAdministration(boolean administration) {
		this.administration = administration;
	}
}
