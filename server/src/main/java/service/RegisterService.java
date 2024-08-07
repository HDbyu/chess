package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import requestresult.RegisterRequest;
import requestresult.RegisterResult;

import java.util.UUID;

public class RegisterService {
    private SQLUserDAO userDAO;
    private SQLAuthDAO authDAO;

    public RegisterService(SQLAuthDAO authDAO, SQLUserDAO userDAO) {
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
