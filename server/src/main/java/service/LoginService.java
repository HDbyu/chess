package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.SQLUserDAO;
import model.AuthData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCrypt;
import requestresult.*;


import java.util.UUID;

public class LoginService {
    private SQLUserDAO userDAO;
    private MemoryAuthDAO authDAO;

    public LoginService(MemoryAuthDAO authDAO, SQLUserDAO userDAO) {
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
            return new LoginResult(null, null, "Error: database error");
        }
        return new LoginResult(request.username(), authToken, null);
    }
}
