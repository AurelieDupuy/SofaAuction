package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;



public class EnchereDAOJdbcImpl implements EnchereDAO{
	private static final String CREDIT_AVANT_ENCHERE="SELECT credit FROM UTILISATEURS WHERE no_utilisateur=?";
	private static final String AJOUTER_ENCHERE="INSERT INTO ENCHERES VALUES (?, ?, ?, ?)";
	private static final String MODIFIER_CREDIT="UPDATE UTILISATEURS SET credit=? WHERE no_utilisateur=?";
	private static final String MODIFIER_MEILLEUR_OFFRE="UPDATE ARTICLES SET prix_vente= ? WHERE no_article= ?";
	
	
	@Override
	public int creditAvantEnchere(int idUtilisateur) throws BusinessException{
		int credit=0;
		
		try (Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement stmt = cnx.prepareStatement(CREDIT_AVANT_ENCHERE);
			stmt.setInt(1, idUtilisateur);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				credit=rs.getInt("credit");
			}
			
		} catch (SQLException e) {
			// problème connection BDD
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.ERREUR_CONNECTION);
			e.printStackTrace();
			throw businessException;
		}
		return credit;
	}
	
	@Override
	public void encherir(Article article, int prixEnchere, Utilisateur user) throws BusinessException {
		Date date = new Date();
		long timeInMilliSeconds = date.getTime();
		java.sql.Date today = new java.sql.Date(timeInMilliSeconds);
		
		BusinessException businessException = new BusinessException();
		
		try (Connection cnx = ConnectionProvider.getConnection();){
			try {
			cnx.setAutoCommit(false);
			
				// modification du crédit de l'enchérisseur
				PreparedStatement stmt = cnx.prepareStatement(MODIFIER_CREDIT);
				stmt.setInt(2, user.getUtilisateurId());
				stmt.setInt(1, this.creditAvantEnchere(user.getUtilisateurId()) - prixEnchere);
				stmt.executeUpdate();
				
				// re-créditation du précédant enchérisseur
				stmt = cnx.prepareStatement(MODIFIER_CREDIT);
				stmt.setInt(2, article.getAcheteur().getUtilisateurId());
				stmt.setInt(1, this.creditAvantEnchere(article.getAcheteur().getUtilisateurId()) + prixEnchere);
				stmt.executeUpdate();
				
				// ajout de l'enchère dans la table enchère
				stmt = cnx.prepareStatement(AJOUTER_ENCHERE);
				stmt.setInt(1, user.getUtilisateurId());
				stmt.setInt(2, article.getArticleId());
				stmt.setDate(3, today);
				stmt.setInt(4, prixEnchere);
				stmt.executeUpdate();
				
				// modification dans la table article du prix de vente
				stmt = cnx.prepareStatement(MODIFIER_MEILLEUR_OFFRE);
				stmt.setInt(2, article.getArticleId());
				stmt.setInt(1, prixEnchere);
				stmt.executeUpdate();
				
				cnx.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				// enchère non enregistrée
				businessException.ajouterErreur(CodesResultatDAL.ECHEC_ENREGISTREMENT_ENCHERE);
				if (cnx != null) {
						try {
							cnx.rollback();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
				}
				throw businessException;
			}
		} catch (SQLException e2) {
			// problème connection BDD
			businessException.ajouterErreur(CodesResultatDAL.ERREUR_CONNECTION);
			e2.printStackTrace();
			throw businessException;
		}

	}
}