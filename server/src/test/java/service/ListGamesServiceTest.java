package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.*;
import requestresult.JoinGameRequest;
import requestresult.JoinGameResult;
import requestresult.ListGamesRequest;
import requestresult.ListGamesResult;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ListGamesServiceTest {
    private static SQLGameDAO gameDAO;

    static {
        try {
            gameDAO = new SQLGameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static MemoryAuthDAO authDAO = new MemoryAuthDAO();

    @BeforeEach
    void init(){
        try {
            new ClearService(gameDAO, authDAO, new SQLUserDAO()).clear();
            authDAO.createAuth(new AuthData("boy", "brass"));
            gameDAO.createGame(new GameData(231, null, null, "cool", new ChessGame()));
            gameDAO.createGame(new GameData(321, null, null, "game", new ChessGame()));
            gameDAO.createGame(new GameData(444, null, null, "bro", new ChessGame()));
        } catch (Exception ignored) {}
    }

    @Test
    @Order(1)
    @DisplayName("List Game Succeed Test")
    void listGameSucceed() throws DataAccessException {
        ListGamesService service = new ListGamesService(gameDAO, authDAO);
        ListGamesResult game = service.listGames(new ListGamesRequest("boy"));
        Assertions.assertNotNull(game.games(),"Contains games");
    }

    @Test
    @Order(2)
    @DisplayName("List Game Fail Test")
    void listGameFail() throws DataAccessException {
        ListGamesService service = new ListGamesService(gameDAO, authDAO);
        ListGamesResult game = service.listGames(new ListGamesRequest("sam"));
        Assertions.assertEquals("Error: unauthorized",game.message(),"Received wrong token");
    }

}