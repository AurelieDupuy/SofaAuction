package fr.eni.encheres.bo;

public class Adresse {
	private int adresseId;
	private String rue;
	private String codePostal;
	private String ville;
	
	
	public Adresse() {
		super();
	
	}
	public Adresse(int adresseId, String rue, String codePostal, String ville) {
		super();
		this.adresseId = adresseId;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}
	public Adresse(String rue, String codePostal, String ville) {
		super();
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public int getAdresseId() {
		return adresseId;
	}

	public void setAdresseId(int adresseId) {
		this.adresseId = adresseId;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}
	
	
	
	
}
