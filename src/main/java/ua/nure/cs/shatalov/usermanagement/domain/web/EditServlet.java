package ua.nure.cs.shatalov.usermanagement.domain.web;

import java.io.IOException;
import java.text.DateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import ua.nure.cs.shatalov.usermanagement.domain.User;
import ua.nure.cs.shatalov.usermanagement.domain.db.DaoFactory;
import ua.nure.cs.shatalov.usermanagement.domain.db.DatabaseException;

public class EditServlet extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		if (req.getParameter("addButton") != null) {
			doOk(req, resp);
		} else if (req.getParameter("cancelButton") != null) {
			doCancel(req, resp);
		} else {
			showPage();
		}
	}

	private void showPage() {
		// TODO Auto-generated method stub
		
	}

	private void doCancel(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}

	private void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		// TODO Auto-generated method stub
		User user = getUser(req);
		try {
			processUser(user);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new ServletException(e);
		}
		req.getRequestDispatcher("/browse").forward(req, resp);
	}

	private User getUser(HttpServletRequest req) {
		// TODO Auto-generated method stub
		User user = new User();
		String idStr = req.getParameter("id");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String date = req.getParameter("date");
		
		if (idStr != null) {
			user,setId(new Long(idStr));
		}
		user.setFirstName(firstName);
		user.setLastName(lastName);
		try {
			user.setDateOfBirth(DateFormat.getDateInstance().parse(date));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return user;
	}

	private void processUser(User user) throws DatabaseException {
		// TODO Auto-generated method stub
		DaoFactory.getInstance().getUserDao().update(user);
	}
}
