package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.*;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SQLGameDAOTest {

    private static SQLGameDAO gameDAO;

    static {
        try {
            gameDAO = new SQLGameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void init() {
        try {
            gameDAO.clear();
        } catch (Exception ignored) { }
    }

    @Test
    @Order(1)
    @DisplayName("Clear Games Succeed Test")
    void clearGameSucceed() throws DataAccessException {
        gameDAO.createGame(new GameData(1, "hi", null, "guy", new ChessGame()));
        gameDAO.createGame(new GameData(5, null, null, "bru", new ChessGame()));
        gameDAO.createGame(new GameData(34, null, "yo", "hmm", new ChessGame()));
        gameDAO.clear();
        Assertions.assertNull(gameDAO.getGame(1), "No game");
        Assertions.assertNull(gameDAO.getGame(5), "No game");
        Assertions.assertNull(gameDAO.getGame(34), "No game");
    }

    @Test
    @Order(2)
    @DisplayName("Create Game Succeed Test")
    void createGameSucceed() throws DataAccessException {
        gameDAO.createGame(new GameData(1, "hi", null, "guy", new ChessGame()));
        Assertions.assertInstanceOf(GameData.class, gameDAO.getGame(1), "Created game data");
    }

    @Test
    @Order(3)
    @DisplayName("Create Game Fail Test")
    void createGameFail() throws DataAccessException {
        try {
            gameDAO.createGame(new GameData(1, "hi", null, "guy", null));
        } catch (DataAccessException e) {
            Assertions.assertEquals("Column 'game' cannot be null", e.getMessage(), "Failed to create game");
        }
    }

    @Test
    @Order(4)
    @DisplayName("Get Game Succeed Test")
    void getGameSucceed() throws DataAccessException {
        gameDAO.createGame(new GameData(1, "hi", null, "guy", new ChessGame()));
        GameData game = gameDAO.getGame(1);
        Assertions.assertInstanceOf(GameData.class, game, "Got game");
    }

    @Test
    @Order(5)
    @DisplayName("Get Game Fail Test")
    void getGameFail() throws DataAccessException {
        gameDAO.createGame(new GameData(1, "hi", null, "guy", new ChessGame()));
        GameData game = gameDAO.getGame(7);
        Assertions.assertNull(game, "failed to find user");
    }

    @Test
    @Order(6)
    @DisplayName("List Games Succeed Test")
    void listGamesSucceed() throws DataAccessException {
        gameDAO.createGame(new GameData(231, null, null, "cool", new ChessGame()));
        gameDAO.createGame(new GameData(321, null, null, "game", new ChessGame()));
        gameDAO.createGame(new GameData(444, null, null, "bro", new ChessGame()));
        gameDAO.createGame(new GameData(1, "hi", null, "guy", new ChessGame()));
        Collection<GameData> games = gameDAO.listGames();
        Assertions.assertNotEquals(0, games.size(), "Listed games");
        Assertions.assertEquals(4, games.size(), "Listed games");
    }

    @Test
    @Order(7)
    @DisplayName("List Games Fail Test")
    void listGamesFail() throws DataAccessException {
        Collection<GameData> games = gameDAO.listGames();
        Assertions.assertEquals(0, games.size(), "No games");
    }

    @Test
    @Order(8)
    @DisplayName("Update Game Succeed Test")
    void updateGameSucceed() throws DataAccessException {
        gameDAO.createGame(new GameData(1, "hi", null, "guy", new ChessGame()));
        gameDAO.updateGame(new GameData(1, "yo", "oh hi", "guy", new ChessGame()));
        Assertions.assertEquals("oh hi", gameDAO.getGame(1).blackUsername(), "Updated black user");
    }

    @Test
    @Order(9)
    @DisplayName("Update Game Fail Test")
    void updateGameFail() throws DataAccessException {
        gameDAO.createGame(new GameData(1, "hi", null, "guy", new ChessGame()));
        gameDAO.updateGame(new GameData(7, "yo", "oh hi", "guy", new ChessGame()));
        Assertions.assertNull(gameDAO.getGame(7), "No game 7");
    }
}