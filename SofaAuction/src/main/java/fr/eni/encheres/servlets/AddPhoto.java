package fr.eni.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class AddPhoto
 */
@WebServlet("/AddPhoto")
public class AddPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * DOGET : Ajouter Photo
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/** CODE POUR INSERER PHOTO !
		 * 		 
		String disposition  = "";
		String fileName  = "";
		String extension  = "";
		
		for (Part part : request.getParts()) {
			disposition = part.getHeader("Content-Disposition");
			extension = disposition.substring(disposition.lastIndexOf("."), disposition.length()-1);
			fileName= "upload" + extension;
			part.write("C:\\Workspace\\Projet Enchère\\Encheres Perso\\src\\main\\webapp\\images\\" + fileName);
		}
		response.getWriter().print("Le fichier a été télécharhé !");
		*/
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/EnConstruction.jsp"); 
		rd.forward(request, response);
	}

	/**
	 * DOPOST : redirige vers JSP
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/JSP/AjoutPhoto.jsp"); 
		rd.forward(request, response);
	}

}
