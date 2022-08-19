package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Adresse;

public class AdresseDAOJdbcImpl implements AdresseDAO {
	private static final String SELECT_BY_ADRESSEID = "select * from ADRESSES where no_adresse=?";
	private static final String UPDATE_ADRESSE = " update ADRESSES set rue=?, code_postal=?, ville=? where no_adresse=?";
	private static final String DELETE_ADRESSE = "delete from ADRESSES where no_adresse=?";
	private static final String SELECT_RECUPEREID = "select no_adresse from ADRESSES where rue=? AND code_postal=? AND ville=?"; // ATTENTION A REVOIR
	private static final String ADD_ADRESSE = "insert into Adresses (rue, code_postal,ville) VALUES (?,?,?)";
	private static final String SELECT_IDADRESSE = "select no_adresse from UTILISATEURS where no_utilisateur=?";

	@Override
	public Adresse selectById(int adresseId) throws BusinessException {
		Adresse adresse = new Adresse();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ADRESSEID);
			pstmt.setInt(1, adresseId);
			ResultSet rs = pstmt.executeQuery();
			boolean premiereLigne = true;
			while (rs.next()) {
				if (premiereLigne) {
					adresse.setAdresseId(rs.getInt("no_adresse"));
					adresse.setRue(rs.getString("rue"));
					adresse.setCodePostal(rs.getString("code_postal"));
					adresse.setVille(rs.getString("ville"));
					premiereLigne = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adresse;
	}

	@Override
	public void modifierAdresse(Adresse adr) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_ADRESSE);
			pstmt.setString(1, adr.getRue());
			pstmt.setString(2, adr.getCodePostal());
			pstmt.setString(3, adr.getVille());
			pstmt.setInt(4, adr.getAdresseId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAdresse(int adresseId) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_ADRESSE);
			pstmt.setInt(1, adresseId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// AJOUTER ARTICLE
	@Override
	public int recupererIDAdresse(String rue, String codePostal, String ville) {

		// récupère ID
		int id = 0;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_RECUPEREID);
			pstmt.setString(1, rue);
			pstmt.setString(2, codePostal);
			pstmt.setString(3, ville);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				id = rs.getInt("no_adresse");
			}
			rs.close();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.RECUPERATION_ADRESSE_ECHEC);
		}

		// Si n'existe pas INSERT
		if (id == 0) {
			try (Connection cnx = ConnectionProvider.getConnection()) {

				PreparedStatement pstmt = cnx.prepareStatement(ADD_ADRESSE, PreparedStatement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, rue);
				pstmt.setString(2, codePostal);
				pstmt.setString(3, ville);

				pstmt.executeUpdate();
				pstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
				BusinessException businessException = new BusinessException();
				businessException.ajouterErreur(CodesResultatDAL.INSERTION_ADRESSE_ECHEC);
			}
		}
		return id;
	}

	@Override
	public int recupererIDAdresseById(int utilisateurId) {
		int id=0;
		
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(SELECT_IDADRESSE);
			stmt.setInt(1, utilisateurId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				id=rs.getInt("no_adresse");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.ERREUR_CONNECTION);
		}	
			
		return id;
	}

}
