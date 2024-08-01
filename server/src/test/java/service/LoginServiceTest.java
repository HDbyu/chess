package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import requestresult.ListGamesRequest;
import requestresult.ListGamesResult;
import requestresult.LoginRequest;
import requestresult.LoginResult;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginServiceTest {
    private static SQLUserDAO userDAO;

    static {
        try {
            userDAO = new SQLUserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static SQLAuthDAO authDAO;

    static {
        try {
            authDAO = new SQLAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void init(){
        try {
            authDAO.clear();
            userDAO.clear();
            userDAO.createUser(new UserData("boy", "brov", "@bo"));
        } catch (Exception ignored) {}
    }

    @Test
    @Order(1)
    @DisplayName("Login Succeed Test")
    void loginSucceed() throws DataAccessException {
        LoginService service = new LoginService(authDAO, userDAO);
        LoginResult result = service.login(new LoginRequest("boy", "brov"));
        Assertions.assertNotNull(result.authToken(),"Contains token");
    }

    @Test
    @Order(2)
    @DisplayName("Login Fail Test")
    void loginFail() throws DataAccessException {
        LoginService service = new LoginService(authDAO, userDAO);
        LoginResult result = service.login(new LoginRequest("boy", "notIt"));
        Assertions.assertEquals("Error: unauthorized", result.message(),"Wrong password");
    }

}