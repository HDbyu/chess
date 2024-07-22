package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import requestresult.RegisterRequest;
import requestresult.RegisterResult;

import java.util.UUID;

public class RegisterService {
    MemoryUserDAO userDAO;
    MemoryGameDAO gameDAO;
    MemoryAuthDAO authDAO;

    public RegisterService(MemoryGameDAO gameDAO, MemoryAuthDAO authDAO, MemoryUserDAO userDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public RegisterResult register(RegisterRequest request) {
        UserData user = null;
        String authToken = null;
        try {
            user = userDAO.getUser(request.username());

            if(user == null) {
                userDAO.createUser(new UserData(request.username(), request.password(), request.email()));
                authToken = UUID.randomUUID().toString();
                authDAO.createAuth(new AuthData(authToken, request.username()));
            }
        } catch (Exception e) {
            return new RegisterResult(null, null, "Error: bad request");
        }
        return new RegisterResult(request.username(), authToken, null);
    }
}
