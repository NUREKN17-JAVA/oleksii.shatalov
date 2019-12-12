package ua.nure.cs.shatalov.usermanagement.domain.web;

import java.text.DateFormat;
import java.util.Date;

import ua.nure.cs.shatalov.usermanagement.domain.User;

public class AddServletTest extends MockServletTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		createServlet(AddServlet.class);
	}
	
	public void testAdd() {
		Date date = new Date();
		User newUser = new User("John", "Doe", new Date());
		User user = new User(new Long(1000), "John", "Doe", new Date());
		getMockUserDao().expectAndReturn("create", newUser, user);
		
		addRequestParameter("firstName", "John");
		addRequestParameter("lastName", "Doe");
		addRequestParameter("date", DateFormat.getInstance().format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
	}
	
	public void testAddEmptyFirstName() {
		Date date = new Date();
		addRequestParameter("lastName", "Doe");
		addRequestParameter("date", DateFormat.getInstance().format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String)getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}
	
	public void testEditEmptyLastName() {
		Date date = new Date();
		addRequestParameter("firstName", "John");
		addRequestParameter("date", DateFormat.getInstance().format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String)getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}
	
	public void testAddEmptyDate() {
		Date date = new Date();
		addRequestParameter("firstName", "John");
		addRequestParameter("lastName", "Doe");
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String)getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}
	
	public void testAddDateIncorrect() {
		Date date = new Date();
		addRequestParameter("firstName", "John");
		addRequestParameter("lastName", "Doe");
		addRequestParameter("date", "lkjghdsfkhdfkjghfdl");
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String)getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error message in session scope", errorMessage);
	}
}
