package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.*;
import requestresult.CreateGameRequest;
import requestresult.CreateGameResult;
import requestresult.JoinGameRequest;
import requestresult.JoinGameResult;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JoinGameServiceTest {
    private static SQLGameDAO gameDAO;

    static {
        try {
            gameDAO = new SQLGameDAO();
            authDAO = new SQLAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static SQLAuthDAO authDAO;

    @BeforeEach
    void init(){
        try {
            new ClearService(gameDAO, authDAO, new SQLUserDAO()).clear();
            authDAO.createAuth(new AuthData("boy", "brass"));
            gameDAO.createGame(new GameData(231, "bro", null, "cool", new ChessGame()));
        } catch (Exception ignored) {}
    }

    @Test
    @Order(1)
    @DisplayName("Join Game Succeed Test")
    void joinGameSucceed() throws DataAccessException {
        JoinGameService service = new JoinGameService(gameDAO, authDAO);
        JoinGameResult game = service.joinGame(new JoinGameRequest(ChessGame.TeamColor.BLACK, 231, "boy"));
        Assertions.assertEquals("brass", gameDAO.getGame(231).blackUsername(), "Player added to game");
    }

    @Test
    @Order(2)
    @DisplayName("Join Game Fail Test")
    void joinGameFail() throws DataAccessException {
        JoinGameService service = new JoinGameService(gameDAO, authDAO);
        JoinGameResult game = service.joinGame(new JoinGameRequest(ChessGame.TeamColor.WHITE, 231, "boy"));
        Assertions.assertEquals("Error: already taken", game.message(), "Color already taken");
    }

}