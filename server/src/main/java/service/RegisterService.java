package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;

public class RegisterService {
    MemoryUserDAO userDAO;
    MemoryGameDAO gameDAO;
    MemoryAuthDAO authDAO;

    public RegisterService(MemoryGameDAO gameDAO, MemoryAuthDAO authDAO, MemoryUserDAO userDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public void register(String username, String password) {}
}
