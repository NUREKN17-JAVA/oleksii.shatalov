package ua.nure.cs.shatalov.usermanagement.domain.web;

import java.util.Date;

import ua.nure.cs.shatalov.usermanagement.domain.User;

public class DeleteServletTest extends MockServletTestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testDelete() {
		User user = new User(new Long(1000), "John", "Doe", new Date());
        getMockUserDao().expect("delete", user);
        setSessionAttribute("user", user);
        addRequestParameter("ok", "Ok");
        doPost();
	}
}
