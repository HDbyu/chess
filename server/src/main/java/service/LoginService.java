package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import requestresult.*;


import java.util.UUID;

public class LoginService {
    private SQLUserDAO userDAO;
    private SQLAuthDAO authDAO;

    public LoginService(SQLAuthDAO authDAO, SQLUserDAO userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public LoginResult login(LoginRequest request) {
        UserData user = null;
        String authToken = null;
        try {
            user = userDAO.getUser(request.username());

            if(user != null && BCrypt.checkpw(request.password(), user.password())) {
                authToken = UUID.randomUUID().toString();
                authDAO.createAuth(new AuthData(authToken, request.username()));
            } else {return new LoginResult(null, null, "Error: unauthorized");}
        } catch (Exception e) {
            return new LoginResult(null, null, "Error: " + e.getMessage());
        }
        return new LoginResult(request.username(), authToken, null);
    }
}
