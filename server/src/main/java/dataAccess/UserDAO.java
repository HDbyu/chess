package dataaccess;

import model.UserData;

interface UserDAO {
    void createUser(UserData u) throws DataAccessException;

    UserData getUser(String name) throws DataAccessException;

    void clear() throws DataAccessException;

}
