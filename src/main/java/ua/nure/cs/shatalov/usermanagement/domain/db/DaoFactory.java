package ua.nure.cs.shatalov.usermanagement.domain.db;

import java.io.IOException;
import java.util.Properties;

import javax.management.RuntimeErrorException;

public class DaoFactory {
	private static final String USER_DAO = "dao.ua.nure.cs.shatalov.usermanagement.domain.db.UserDao";
	private final Properties properties;
	
	public DaoFactory() {
		properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	private ConnectionFactory getConnectionFactory() {
		String user = properties.getProperty("connection.user");
		String password = properties.getProperty("connection.password");
		String url = properties.getProperty("connection.url");
		String driver = properties.getProperty("connection.driver");
		return new ConnectionFactoryImpl(driver, url, user, password);
	}
	
	public UserDao getUserDao() {
		UserDao result = null;
		try {
			Class clazz = Class.forName(properties.getProperty(USER_DAO));
			UserDao userDao = (UserDao) clazz.newInstance();
			userDao.setConnectionFactory(getConnectionFactory());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return result;
	}
}
