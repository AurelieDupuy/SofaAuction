package fr.eni.encheres.dal;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Adresse;

public interface AdresseDAO {

	public Adresse selectById(int adresseId)throws BusinessException;
	public void modifierAdresse(Adresse adr)throws BusinessException;
	public void deleteAdresse(int adresseId)throws BusinessException;
	public int recupererIDAdresse (String rue, String codePostal, String ville);
	public int recupererIDAdresseById(int utilisateurId);
}
