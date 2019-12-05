package ua.nure.cs.shatalov.usermanagement.domain.web;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import ua.nure.cs.shatalov.usermanagement.domain.db.DaoFactory;
import ua.nure.cs.shatalov.usermanagement.domain.db.DatabaseException;

public class BrowseServlet extends HttpServlet {
	protected void service (HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		browse(req, resp);
	}

	private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Collection users;
		try {
			users = DaoFactory.getInstance().getUserDao().findAll();
			req.getSession().setAttribute("users", users);
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			throw new ServletException(e);
		}
	}
}
