package ua.nure.cs.shatalov.usermanagement.domain.db;

import ua.nure.cs.shatalov.usermanagement.domain.User;

public interface UserDao {
	User create(User user) throws DatabaseException;
}
