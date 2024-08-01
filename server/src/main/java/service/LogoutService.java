package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.SQLAuthDAO;
import model.AuthData;
import model.UserData;
import requestresult.*;

import java.util.UUID;

public class LogoutService {
    private SQLAuthDAO authDAO;

    public LogoutService(SQLAuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    public LogoutResult logout(LogoutRequest request) {
        AuthData authToken = null;
        try {
            authToken = authDAO.getAuth(request.authorization());

            if(authToken != null) {
                authDAO.deleteAuth(request.authorization());
            } else {return new LogoutResult("Error: unauthorized");}
        } catch (Exception e) {
            return new LogoutResult("Error: database error");
        }
        return new LogoutResult(null);
    }
}
