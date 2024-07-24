package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
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
    private static MemoryUserDAO userDAO = new MemoryUserDAO();
    private static MemoryAuthDAO authDAO = new MemoryAuthDAO();

    @BeforeEach
    void init(){
        try {
            new ClearService(new MemoryGameDAO(), authDAO, userDAO).clear();
            userDAO.createUser(new UserData("boy", "goo", "@boyo"));
        } catch (Exception ignored) {}
    }

    @Test
    @Order(1)
    @DisplayName("Login Succeed Test")
    void loginSucceed() throws DataAccessException {
        LoginService service = new LoginService(authDAO, userDAO);
        LoginResult result = service.login(new LoginRequest("boy", "goo"));
        Assertions.assertNotNull(result.authToken(),"Contains token");
    }

    @Test
    @Order(2)
    @DisplayName("List Game Fail Test")
    void listGameFail() throws DataAccessException {
        LoginService service = new LoginService(authDAO, userDAO);
        LoginResult result = service.login(new LoginRequest("boy", "notIt"));
        Assertions.assertEquals("Error: unauthorized", result.message(),"Wrong password");
    }

}