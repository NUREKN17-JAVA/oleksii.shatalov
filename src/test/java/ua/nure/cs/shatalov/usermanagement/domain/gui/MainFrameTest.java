package ua.nure.cs.shatalov.usermanagement.domain.gui;

import java.awt.Component;
import java.awt.Window;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.cs.shatalov.usermanagement.domain.User;
import ua.nure.cs.shatalov.usermanagement.domain.db.DaoFactory;
import ua.nure.cs.shatalov.usermanagement.domain.db.DaoFactoryImpl;
import ua.nure.cs.shatalov.usermanagement.domain.db.MockDaoFactory;
import ua.nure.cs.shatalov.usermanagement.domain.db.MockUserDao;
import com.mockobjects.dynamic.Mock;

public class MainFrameTest extends JFCTestCase {

	private static final String DELETE_COMMAND = "delete";

	private static final String EDIT_PANEL = "editPanel";

	private static final String UPDATE_COMMAND = "update";

	private static final String CANCEL_BUTTON = "cancelButton";

	private static final String OK_BUTTON = "okButton";

	private static final String DATE_OF_BIRTH_FIELD = "dateOfBirthField";

	private static final String LAST_NAME_FIELD = "lastNameField";

	private static final String FIRST_NAME_FIELD = "firstNameField";

	private static final String ADD_PANEL = "addPanel";

	private static final String FIND_ALL_COMMAND = "findAll";

	private static final String CREATE_COMMAND = "create";

	private static final String DETAILS_BUTTON = "detailsButton";

	private static final String DELETE_BUTTON = "deleteButton";

	private static final String EDIT_BUTTON = "editButton";

	private static final String ADD_BUTTON = "addButton";

	private static final String SURNAME_COLUMN_NAME = "Surname";

	private static final String NAME_COLUMN_NAME = "Name";

	private static final String ID_COLUMN_NAME = "ID";

	private static final String USER_TABLE = "userTable";

	private static final String BROWSE_PANEL = "browsePanel";

	private static final String SURNAME = "John";

	private static final String NAME = "Doe";

	private static final String DEFAULT_NAME = "Sam";
	
	private static final String DEFAULT_SURNAME = "Willson";
	
	private Window mainFrame;
	
	private List<User> users;

	private Mock mockUserDao;
	protected void setUp() throws Exception {
			super.setUp();
			
			try {
				Properties properties = new Properties();
				//properties.setProperty("ua.nure.cs.shatalov.usermanagement.domain.db.UserDao", 
				//					MockUserDao.class.getName());
				properties.setProperty("dao.factory", MockDaoFactory.class.getName());
				DaoFactory.getInstance().init(properties);
				mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
				mockUserDao.expectAndReturn(FIND_ALL_COMMAND, new ArrayList());
				setHelper(new JFCTestHelper());
				mainFrame = new MainFrame();
			} catch (Exception e) {
				e.printStackTrace();
			}
			User expectedUser = new User(new Long(1000),DEFAULT_NAME,DEFAULT_SURNAME,new Date());
			users = new ArrayList<User>();
			users.add(expectedUser);
			mainFrame.setVisible(true);
	}

	protected void tearDown() throws Exception {
			try {
				mockUserDao.verify();
				mainFrame.setVisible(false);
				getHelper().cleanUp(this);
				super.tearDown();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
	
	private Component find (Class componentClass, String name) {
			NamedComponentFinder finder;
			finder = new NamedComponentFinder(componentClass, name);
			finder.setWait(0);
			Component component = finder.find(mainFrame, 0);
			assertNotNull("Could not find component '" + name + "'", component);
			return component;
	}
	
	public void testBrowseControls() {
			find(JPanel.class, BROWSE_PANEL);
			JTable table = (JTable) find(JTable.class, USER_TABLE);
			assertEquals(3, table.getColumnCount());
			assertEquals(ID_COLUMN_NAME, table.getColumnName(0));
			assertEquals(NAME_COLUMN_NAME, table.getColumnName(1));
			assertEquals(SURNAME_COLUMN_NAME, table.getColumnName(2));
			
			find(JButton.class, ADD_BUTTON);
			find(JButton.class, EDIT_BUTTON);
			find(JButton.class, DELETE_BUTTON);
			find(JButton.class, DETAILS_BUTTON);
	}
	
	public void testAddUser() {
			String lastName = NAME;
			String firstName = SURNAME;
			Date now = new Date();
			
			User user = new User(firstName, lastName, now);
			
			User expectedUser = new User(new Long(1), firstName, lastName, now);
			mockUserDao.expectAndRun(CREATE_COMMAND, user, expectedUser);
			
			ArrayList<User> users = new ArrayList<User>(this.users);
			users.add(expectedUser);
			mockUserDao.expectAndRun(FIND_ALL_COMMAND, users);
			
			JTable table = (JTable) find(JTable.class, USER_TABLE);
			assertEquals(0, table.getRowCount());
			
			JButton addButton = (JButton) find(JButton.class, ADD_BUTTON);
			getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
			
			find(JPanel.class, ADD_PANEL);
			
			
			JTextField firstNameField = (JTextField) find(JTextField.class, FIRST_NAME_FIELD);
			JTextField lastNameField = (JTextField) find(JTextField.class, LAST_NAME_FIELD);
			JTextField dateOfBirthField = (JTextField) find(JTextField.class, DATE_OF_BIRTH_FIELD);
			JButton okButton = (JButton) find(JButton.class, OK_BUTTON);
			find(JButton.class, CANCEL_BUTTON);
			
			getHelper().sendString(new StringEventData(this, firstNameField, firstName));
			getHelper().sendString(new StringEventData(this, lastNameField, lastName));
			DateFormat formatter = DateFormat.getDateInstance();
			String date = formatter.format(now);
			getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
			
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
			
			find(JPanel.class, BROWSE_PANEL);
			table = (JTable) find(JTable.class, USER_TABLE);
			assertEquals(1, table.getRowCount());
	}
	
	public void testCancelAddUser() {
		
			User user = new User(NAME,SURNAME, new Date());
			User expectedUser = new User(new Long(1),NAME,SURNAME,new Date());
			mockUserDao.expectAndReturn(CREATE_COMMAND, user, expectedUser);
			ArrayList users = new ArrayList (this.users);
			users.add(expectedUser);
			mockUserDao.expectAndReturn(FIND_ALL_COMMAND,users);
			JTable table = (JTable) find(JTable.class, USER_TABLE);
			assertEquals(0,table.getRowCount());
			
			JButton addButton = (JButton) find(JButton.class, ADD_BUTTON);
			getHelper().enterClickAndLeave(new MouseEventData(this,addButton));
			find(JPanel.class, ADD_PANEL);
			fillFields(NAME, SURNAME, new Date());
			
			JButton cancelButton = (JButton) find(JButton.class, CANCEL_BUTTON);
			
			getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
			find(JPanel.class, BROWSE_PANEL);
			table = (JTable) find(JTable.class, USER_TABLE);
			assertEquals(1, table.getRowCount());
		
	}
	
	private void fillFields(String firstName, String lastName, Date dateOfBirth) {
			// TODO Auto-generated method stub
			JTextField firtNameField = (JTextField) find(JTextField.class, FIRST_NAME_FIELD);
			JTextField lastNameField = (JTextField) find(JTextField.class, LAST_NAME_FIELD);
			JTextField dateOfBirthField = (JTextField) find(JTextField.class, DATE_OF_BIRTH_FIELD);
			
			getHelper().sendString(new StringEventData(this, firtNameField, firstName));
			getHelper().sendString(new StringEventData(this, lastNameField, lastName));
			DateFormat formatter = DateFormat.getDateInstance();
			String date = formatter.format(dateOfBirth);
			getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
	}

	public void testEditUser() { 
			find(JPanel.class, BROWSE_PANEL);

            User expectedUser = new User(new Long(2), NAME, SURNAME, new Date());
            mockUserDao.expect(UPDATE_COMMAND, expectedUser);
            List users = new ArrayList(this.users);
            users.add(expectedUser);

            mockUserDao.expectAndReturn(FIND_ALL_COMMAND, users);

            JTable userTable = (JTable) find(JTable.class, USER_TABLE);
            assertEquals(1, userTable.getRowCount());
            JButton editButton = (JButton) find(JButton.class, EDIT_BUTTON);
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, userTable, 0, 0, 1));
            getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
            
            find(JPanel.class, EDIT_PANEL);
            JTextField firstNameField = (JTextField) find(JTextField.class, FIRST_NAME_FIELD);
            JTextField lastNameField = (JTextField) find(JTextField.class, LAST_NAME_FIELD);
            getHelper().sendString(new StringEventData(this, firstNameField, "1"));
            getHelper().sendString(new StringEventData(this, lastNameField, "1"));

            JButton okButton = (JButton) find(JButton.class, OK_BUTTON);
            getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
            

            find(JPanel.class, BROWSE_PANEL);
            userTable = (JTable) find(JTable.class, USER_TABLE);
            assertEquals(2, userTable.getRowCount());
    }
    
    public void testCancelEditUser() { 
			find(JPanel.class, BROWSE_PANEL);

            User expectedUser = new User(new Long(2), NAME, SURNAME, new Date());
            mockUserDao.expect(UPDATE_COMMAND, expectedUser);
            List users = new ArrayList(this.users);
            users.add(expectedUser);

            mockUserDao.expectAndReturn(FIND_ALL_COMMAND, users);

            JTable userTable = (JTable) find(JTable.class, USER_TABLE);
            assertEquals(1, userTable.getRowCount());
            JButton editButton = (JButton) find(JButton.class, EDIT_BUTTON);
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, userTable, 0, 0, 1));
            getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
            
            find(JPanel.class, EDIT_PANEL);
            JTextField firstNameField = (JTextField) find(JTextField.class, FIRST_NAME_FIELD);
            JTextField lastNameField = (JTextField) find(JTextField.class, LAST_NAME_FIELD);
            getHelper().sendString(new StringEventData(this, firstNameField, "1"));
            getHelper().sendString(new StringEventData(this, lastNameField, "1"));

            JButton cancelButton = (JButton) find(JButton.class, CANCEL_BUTTON);
            getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
            

            find(JPanel.class, BROWSE_PANEL);
            userTable = (JTable) find(JTable.class, USER_TABLE);
            assertEquals(2, userTable.getRowCount());
    }
	
	public void testDeleteUser() {
			User expectedUser = new User(new Long(1000),NAME,SURNAME,new Date());
	        mockUserDao.expect(DELETE_COMMAND, expectedUser);
	        ArrayList<User> users = new ArrayList<User>();
	        mockUserDao.expectAndReturn(FIND_ALL_COMMAND, users);
	        JTable table = (JTable) find(JTable.class, USER_TABLE);
	        assertEquals(1, table.getRowCount());
	        JButton deleteButton = (JButton) find(JButton.class, DELETE_BUTTON);
	        getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
	        getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
	        find(JPanel.class, BROWSE_PANEL);
	        table = (JTable) find(JTable.class, USER_TABLE);
	        assertEquals(0, table.getRowCount());
	}
	
}
