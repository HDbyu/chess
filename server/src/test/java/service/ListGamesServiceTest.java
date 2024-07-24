package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
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
    private static MemoryGameDAO gameDAO = new MemoryGameDAO();
    private static MemoryAuthDAO authDAO = new MemoryAuthDAO();

    @BeforeEach
    void init(){
        try {
            new ClearService(gameDAO, authDAO, new MemoryUserDAO()).clear();
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