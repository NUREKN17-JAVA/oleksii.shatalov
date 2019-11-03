package ua.nure.cs.shatalov.usermanagement.domain.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.cs.shatalov.usermanagement.domain.User;

public class HsqldbUserDao implements UserDao {

	private static final String SELECT_ALL_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users";
	private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
	private ConnectionFactory connectionFactory;
	
	public HsqldbUserDao(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	@Override
	public User create(User user) throws DatabaseException {
		// TODO Auto-generated method stub
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
			int n = statement.executeUpdate();
			if (n != 1) {
				throw new DatabaseException("Number of the inserted rows: " + n);
			}
			CallableStatement callabelStatement = connection.prepareCall("call IDENTITY()");
			ResultSet keys = callabelStatement.executeQuery();
			if (keys.next()) {
				user.setId(new Long(keys.getLong(1)));
			}
			keys.close();
			callabelStatement.close();
			statement.close();
			connection.close();
			return user;
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException(e);
		}
	}

	@Override
	public void update(User user) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(User user) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public User find(Long id) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection findAll() throws DatabaseException {
		// TODO Auto-generated method stub
		Collection result = new LinkedList();
		
		try {
			Connection connection = connectionFactory.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
			while (resultSet.next()) {
				User user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirth(resultSet.getDate(4));
				result.add(user);
			}
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException(e);
		}
		
		return result;
	}

}
