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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClearServiceTest {
    private static MemoryUserDAO userDAO = new MemoryUserDAO();
    private static MemoryGameDAO gameDAO = new MemoryGameDAO();
    private static MemoryAuthDAO authDAO = new MemoryAuthDAO();

    @BeforeAll
    static void init(){
        try {
            userDAO.createUser(new UserData("Hi", "pass", "@boy.cool"));
            gameDAO.createGame(new GameData(321, "bro", "other", "game", new ChessGame()));
            authDAO.createAuth(new AuthData("boy", "brass"));
        } catch (Exception ignored) {}
    }

    @Test
    @Order(1)
    @DisplayName("clear Succeed Test")
    void clearSucceed() throws DataAccessException {
        ClearService service = new ClearService(gameDAO, authDAO, userDAO);
        service.clear();
        Assertions.assertNull(userDAO.getUser("Hi"), "User was removed");
        Assertions.assertNull(gameDAO.getGame(321), "Games were removed");
        Assertions.assertNull(authDAO.getAuth("boy"), "Token was removed");
    }
}