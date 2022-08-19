package fr.eni.encheres.bo;



public class Categorie {
	private int categorieId;
	private String libelle;
	
	//Constructeurs
	public Categorie() {
	}
	
	public Categorie(int categorieId, String libelle) {
		this.categorieId = categorieId;
		this.libelle = libelle;
	}

	//Getter et Setter
	public int getCategorieId() {
		return categorieId;
	}

	public void setCategorieId(int categorieId) {
		this.categorieId = categorieId;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
