package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Categorie;

public class CategorieDaoJdbcImpl implements CategorieDAO {
	private static final String SELECT_ALL_CATEGORIE = "SELECT * from CATEGORIES";
	private static final String SELECT_ID = "SELECT no_categorie from CATEGORIES where libelle=?";

	@Override
	public List<Categorie> listCategorie() {
		List<Categorie> categories = new ArrayList<Categorie>();
		Categorie categorie;
		try {
			Connection cnx = ConnectionProvider.getConnection();
			Statement stmt = cnx.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT_ALL_CATEGORIE);
			 while(rs.next()) {
				 categorie = new Categorie();
				 categorie.setCategorieId(rs.getInt("no_categorie"));
				 categorie.setLibelle(rs.getString("libelle"));
				 categories.add(categorie);
			 }
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return categories;
	}
	
	//AJOUTER ARTICLE
	@Override 
	public int recupereID (String libelle) {

		//récupère ID
		int id=0;
		try (Connection cnx = ConnectionProvider.getConnection()) 
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ID);
			pstmt.setString(1, libelle);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				id=rs.getInt("no_categorie");
			}
			rs.close();
			pstmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();            
			businessException.ajouterErreur(CodesResultatDAL.SELECTION_CATEGORIE_ECHEC);
		}
		
		return id;
	}

}
