package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;

public class ClearService {

    MemoryUserDAO userDAO;
    MemoryGameDAO gameDAO;
    MemoryAuthDAO authDAO;

    public ClearService(MemoryGameDAO gameDAO, MemoryAuthDAO authDAO, MemoryUserDAO userDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public void clear() {
        try {
            userDAO.clear();
            gameDAO.clear();
            authDAO.clear();
        } catch (Exception e) {

        }
    }
}
