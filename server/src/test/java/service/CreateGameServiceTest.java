package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import requestresult.CreateGameRequest;
import requestresult.CreateGameResult;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CreateGameServiceTest {

    private static SQLGameDAO gameDAO;
    private static SQLAuthDAO authDAO;
    static {
        try {
            gameDAO = new SQLGameDAO();
            authDAO = new SQLAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void init(){
        try {
            authDAO.createAuth(new AuthData("boy", "brass"));
        } catch (Exception ignored) {}
    }

    @Test
    @Order(1)
    @DisplayName("Create Game Succeed Test")
    void createGameSucceed() throws DataAccessException {
        CreateGameService service = new CreateGameService(gameDAO, authDAO);
        CreateGameResult game = service.createGame(new CreateGameRequest("game", "boy"));
        Assertions.assertInstanceOf(GameData.class, gameDAO.getGame(game.gameID()), "Created game");
    }

    @Test
    @Order(2)
    @DisplayName("Create Game Fail Test")
    void createGameFail() throws DataAccessException {
        new ClearService(gameDAO, authDAO, new SQLUserDAO()).clear();
        CreateGameService service = new CreateGameService(gameDAO, authDAO);
        CreateGameResult game = service.createGame(new CreateGameRequest("game", ""));
        Assertions.assertEquals("Error: unauthorized", game.message(), "No Data provided to create game");
    }
}