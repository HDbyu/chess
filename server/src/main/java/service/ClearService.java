package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import requestresult.ClearResult;

public class ClearService {

    private MemoryUserDAO userDAO;
    private MemoryGameDAO gameDAO;
    private MemoryAuthDAO authDAO;

    public ClearService(MemoryGameDAO gameDAO, MemoryAuthDAO authDAO, MemoryUserDAO userDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public ClearResult clear() {
        try {
            userDAO.clear();
            gameDAO.clear();
            authDAO.clear();
        } catch (Exception e) {
            return new ClearResult("Error: database error");
        }
        return new ClearResult(null);
    }
}
