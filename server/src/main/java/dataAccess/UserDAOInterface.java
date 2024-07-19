package dataaccess;

import model.UserData;

interface UserDAOInterface {
    void createUser(UserData u) throws DataAccessException;

    UserData getUser(String u) throws DataAccessException;

    void clear() throws DataAccessException;

}
