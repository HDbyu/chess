package service;

import dataaccess.*;
import requestresult.ClearResult;

public class ClearService {

    private SQLUserDAO userDAO;
    private SQLGameDAO gameDAO;
    private SQLAuthDAO authDAO;

    public ClearService(SQLGameDAO gameDAO, SQLAuthDAO authDAO, SQLUserDAO userDAO) {
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
