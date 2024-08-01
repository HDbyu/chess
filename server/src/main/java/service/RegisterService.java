package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCrypt;
import requestresult.RegisterRequest;
import requestresult.RegisterResult;

import java.util.UUID;

public class RegisterService {
    private MemoryUserDAO userDAO;
    private MemoryAuthDAO authDAO;

    public RegisterService(MemoryAuthDAO authDAO, MemoryUserDAO userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public RegisterResult register(RegisterRequest request) {
        if (request.username() == null || request.password() == null || request.email() == null) {
            return new RegisterResult(null, null, "Error: bad request");
        }
        UserData user = null;
        String authToken = null;
        try {
            user = userDAO.getUser(request.username());

            if(user == null) {
                userDAO.createUser(new UserData(request.username(),
                        BCrypt.hashpw(request.password(), BCrypt.gensalt()), request.email()));
                authToken = UUID.randomUUID().toString();
                authDAO.createAuth(new AuthData(authToken, request.username()));
            } else {return new RegisterResult(null, null, "Error: already taken");}
        } catch (Exception e) {
            return new RegisterResult(null, null, "Error: database error");
        }
        return new RegisterResult(request.username(), authToken, null);
    }
}
