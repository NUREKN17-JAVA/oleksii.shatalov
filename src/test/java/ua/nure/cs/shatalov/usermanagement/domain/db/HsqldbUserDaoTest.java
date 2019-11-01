package ua.nure.cs.shatalov.usermanagement.domain.db;

import java.util.Date;

import junit.framework.TestCase;
import ua.nure.cs.shatalov.usermanagement.domain.User;

public class HsqldbUserDaoTest extends TestCase {

	HsqldbUserDao dao;
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		dao = new HsqldbUserDao();
	}

	public void testCreate() {
		// fail("Not yet implemented");
		try {
			User user = new User();
			user.setFirstName("John");
			user.setLastName("Doe");
			user.setDateOfBirth(new Date());
			assertNull(user.getId());
			user = dao.create(user);
			assertNotNull(user);
			assertNotNull(user.getId());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());
		}
	}

}
