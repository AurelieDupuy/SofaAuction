package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {
	private static final String SELECT_BY_UTILISATEURID = "select no_utilisateur,pseudo,nom,prenom,email,telephone,mot_de_passe,credit,administrateur,rue,code_postal,ville from UTILISATEURS INNER JOIN ADRESSES on adresses.no_adresse=UTILISATEURS.no_adresse where UTILISATEURS.no_utilisateur=?";
	private static final String UPDATE_UTILISATEUR = "update UTILISATEURS set pseudo=?,nom=?,prenom=?,email=?,telephone=?,mot_de_passe=? where no_utilisateur=?";
	private static final String UPDATE_ADRESSE = "update ADRESSES set rue=?,code_postal=?,ville=? where no_adresse=?";
	private static final String DELETE_UTILISATEUR = "update UTILISATEURS set date_suppression=? where no_utilisateur=?";
	private static final String LOGIN_PSEUDO = "SELECT * FROM Utilisateurs INNER JOIN ADRESSES on adresses.no_adresse=UTILISATEURS.no_adresse where pseudo=? and mot_de_passe=?";
	private static final String LOGIN_EMAIL = "SELECT * FROM Utilisateurs INNER JOIN ADRESSES on adresses.no_adresse=UTILISATEURS.no_adresse where email=? and mot_de_passe=?";
	private static final String ADD_UTILISATEUR = "INSERT INTO Utilisateurs (pseudo,nom,prenom,email,telephone,no_adresse,mot_de_passe,credit, administrateur) VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String ADD_ADRESSE = "INSERT INTO Adresses VALUES (?,?,?)";
	private static final String SELECT_ALL_EMAIL = "SELECT no_utilisateur,email FROM UTILISATEURS";
	private static final String SELECT_ALL_PSEUDO = "SELECT no_utilisateur,pseudo FROM UTILISATEURS";

	@Override
	public Utilisateur selectById(int id) throws BusinessException {
		Utilisateur utilisateur = new Utilisateur();
		Adresse adresse = new Adresse();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_UTILISATEURID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				utilisateur.setUtilisateurId(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
				utilisateur.setCredit(rs.getInt("credit"));
				if (rs.getInt("administrateur") == 0) {
					utilisateur.setAdministration(false);
				} else {
					utilisateur.setAdministration(true);
				}
				adresse.setRue(rs.getString("rue"));
				adresse.setCodePostal(rs.getString("code_postal"));
				adresse.setVille(rs.getString("ville"));

				utilisateur.setAdresse(adresse);

			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_UTILISATEUR_ECHEC);
			throw businessException;
		}
		return utilisateur;
	}

	//Enregistrement des modification du profil utilisateur dans la BDD
	@Override
	public void modifierUtilisateur(Utilisateur u) throws BusinessException {

		// Recupération du numéro de l'adresse
		AdresseDAO adresseDAO = new AdresseDAOJdbcImpl();
		int adresseId;
		adresseId=adresseDAO.recupererIDAdresseById(u.getUtilisateurId());
		
		try (Connection cnx = ConnectionProvider.getConnection()) {

			// Modification de la table Adresses
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_ADRESSE);
			pstmt.setString(1, u.getAdresse().getRue());
			pstmt.setString(2, u.getAdresse().getCodePostal());
			pstmt.setString(3, u.getAdresse().getVille());
			pstmt.setInt(4, adresseId);
			pstmt.executeUpdate();
			pstmt.close();

			// Modification de la table Utilisateurs
			PreparedStatement pstm;
			pstm = cnx.prepareStatement(UPDATE_UTILISATEUR);
			pstm.setString(1, u.getPseudo());
			pstm.setString(2, u.getNom());
			pstm.setString(3, u.getPrenom());
			pstm.setString(4, u.getEmail());
			pstm.setString(5, u.getTelephone());
			pstm.setString(6, u.getMotDePasse());
			pstm.setInt(7, u.getUtilisateurId());
			pstm.executeUpdate();

			cnx.close();

		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.MODIFICATION_UTILISATEUR_ET_ADRESSE_ERREUR);
			throw businessException;
		}
	}

	
	@Override
	public void deleteUtilisateur(int id) throws BusinessException {
		Date date = new Date();
		long timeInMilliSeconds = date.getTime();
		java.sql.Date today = new java.sql.Date(timeInMilliSeconds);
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_UTILISATEUR);
			pstmt.setDate(1, today);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SUPPRESSION_UTILISATEUR_ERREUR);
			throw businessException;
		}
	}

	@Override
	public Utilisateur loginPseudo(String pseudo, String password) throws BusinessException {//TODO
		Utilisateur utilisateur = null;
		Adresse adresse = new Adresse();
		try (Connection cnx = ConnectionProvider.getConnection()) {

			PreparedStatement pstmt = cnx.prepareStatement(LOGIN_PSEUDO);
			{
				pstmt.setString(1,pseudo);
			}
			
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				utilisateur = new Utilisateur();
				utilisateur.setUtilisateurId(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
				utilisateur.setCredit(rs.getInt("credit"));
				if (rs.getInt("administrateur") == 0) {
					utilisateur.setAdministration(false);
				} else {
					utilisateur.setAdministration(true);
				}
				adresse.setRue(rs.getString("rue"));
				adresse.setCodePostal(rs.getString("code_postal"));
				adresse.setVille(rs.getString("ville"));
				
				utilisateur.setAdresse(adresse);
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return utilisateur;
	}
	

	
	public Utilisateur loginEmail(String email, String password) throws BusinessException {//TODO
		Utilisateur utilisateur = null;
		Adresse adresse = new Adresse();
		try (Connection cnx = ConnectionProvider.getConnection()) {

			PreparedStatement pstmt = cnx.prepareStatement(LOGIN_EMAIL);
			{
				pstmt.setString(1,email);
			}
			
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				utilisateur = new Utilisateur();
				utilisateur.setUtilisateurId(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
				utilisateur.setCredit(rs.getInt("credit"));
				if (rs.getInt("administrateur") == 0) {
					utilisateur.setAdministration(false);
				} else {
					utilisateur.setAdministration(true);
				}
				adresse.setRue(rs.getString("rue"));
				adresse.setCodePostal(rs.getString("code_postal"));
				adresse.setVille(rs.getString("ville"));
				
				utilisateur.setAdresse(adresse);
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return utilisateur;
	}

	public void addUser(Utilisateur utilisateur, Adresse adresse) throws BusinessException {

		try (Connection cnx = ConnectionProvider.getConnection()) {

			int idAdresse = 0;

			PreparedStatement pstmt = cnx.prepareStatement(ADD_ADRESSE, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, adresse.getRue());
			pstmt.setString(2, adresse.getCodePostal());
			pstmt.setString(3, adresse.getVille());

			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next())
				idAdresse = rs.getInt(1);
			rs.close();
			pstmt.close();

			PreparedStatement pstm = cnx.prepareStatement(ADD_UTILISATEUR);
			pstm.setString(1, utilisateur.getPseudo());
			pstm.setString(2, utilisateur.getNom());
			pstm.setString(3, utilisateur.getPrenom());
			pstm.setString(4, utilisateur.getEmail());
			pstm.setString(5, utilisateur.getTelephone());
			pstm.setInt(6, idAdresse);
			pstm.setString(7, utilisateur.getMotDePasse());  // Rajouter le hachage du mots de passe
			pstm.setInt(8, 0);
			pstm.setInt(9, 0);

			pstm.executeUpdate();
			cnx.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<Integer, String> selectAllEmail() throws BusinessException {
		Map<Integer, String> listeEmails = new HashMap<Integer, String>();

		try (Connection cnx = ConnectionProvider.getConnection()) {
			Statement pstmt = cnx.createStatement();
			ResultSet rs = pstmt.executeQuery(SELECT_ALL_EMAIL);
			while (rs.next()) {
				listeEmails.put(rs.getInt("no_utilisateur"), rs.getString("email"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_EMAILS_ECHEC);
			throw businessException;
		}
		return listeEmails;
	}

	@Override
	public Map<Integer, String> selectAllPseudo() throws BusinessException {
		Map<Integer, String> listePseudos = new HashMap<Integer, String>();

		try (Connection cnx = ConnectionProvider.getConnection()) {
			Statement pstmt = cnx.createStatement();
			ResultSet rs = pstmt.executeQuery(SELECT_ALL_PSEUDO);
			while (rs.next()) {
				listePseudos.put(rs.getInt("no_utilisateur"), rs.getString("pseudo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_PSEUDOS_ECHEC);
			throw businessException;
		}
		return listePseudos;
	}

}
