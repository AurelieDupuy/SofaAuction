package fr.eni.encheres.dal;

import java.util.Map;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {

	public Utilisateur selectById(int id) throws BusinessException;
	public void modifierUtilisateur(Utilisateur u) throws BusinessException;
	public void deleteUtilisateur(int id) throws BusinessException;
	public Utilisateur loginPseudo(String pseudo, String password) throws BusinessException;
	public Utilisateur loginEmail(String email, String password) throws BusinessException;
	public void addUser(Utilisateur utilisateur, Adresse adresse) throws BusinessException;
	public Map<Integer,String> selectAllEmail() throws BusinessException;
	public Map<Integer,String> selectAllPseudo() throws BusinessException;
}

