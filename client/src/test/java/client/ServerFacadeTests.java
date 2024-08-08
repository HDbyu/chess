package client;

import chess.ChessGame;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import requestresult.*;
import server.Server;
import serveraccess.ServerFacade;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @AfterEach
    void reset() throws Exception {
        URI uri = new URI("http://localhost:0/db");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("DELETE");
        http.setDoOutput(true);
        http.connect();
    }

    @Test
    public void loginSuccess() throws Exception {
        facade.register("bro", "pablo", "@me");
        LoginResult result = facade.login("bro", "pablo");
        Assertions.assertEquals("bro", result.username(), "Logged in user");
        facade.logout(result.authToken());
    }

    @Test
    public void loginFail() throws Exception {
        facade.register("bra", "pablo", "@me");
        LoginResult result = facade.login("bra", "juan");
        Assertions.assertNull(result, "Failed to log in");
    }

    @Test
    public void registerSuccess() throws Exception {
        RegisterResult result = facade.register("bra", "pablo", "@me");
        Assertions.assertNotNull(result.authToken(), "Correctly registered");
    }

    @Test
    public void registerFail() throws Exception {
        try {
            RegisterResult result = facade.register("bra", null, "@me");
        } catch (Exception e) {
            Assertions.assertNull(e.getMessage(), "Failed to register");
        }
    }

    @Test
    public void logoutSuccess() throws Exception {
        RegisterResult result = facade.register("bro", "pablo", "@me");
        LogoutResult logoutResult = facade.logout(result.authToken());
        Assertions.assertNull(logoutResult.message(), "Successfully logged out");
    }

    @Test
    public void logoutFail() throws Exception {
        RegisterResult result = facade.register("brom", "pablo", "@me");
        try {
            LogoutResult logoutResult = facade.logout("not right");
        } catch (Exception e) {
            Assertions.assertNotNull(e.getMessage(), "Failed to logout");
        }
    }

    @Test
    public void createGameSuccess() throws Exception {
        RegisterResult result = facade.register("brome", "pablo", "@me");
        CreateGameResult createGameResult = facade.createGame(result.authToken(), "tim");
        Assertions.assertNotNull(createGameResult.gameID(), "Created game ID");
    }

    @Test
    public void createGameFail() throws Exception {
        try {
            CreateGameResult createGameResult = facade.createGame("bad", "tim");
        } catch (Exception e) {
            Assertions.assertNotNull(e.getMessage(), "Failed to create game");
        }
    }

    @Test
    public void listSuccess() throws Exception {
        RegisterResult result = facade.register("broefe", "pablo", "@me");
        facade.createGame(result.authToken(), "tim");
        facade.createGame(result.authToken(), "john");
        ListGamesResult listGamesResult = facade.listGames(result.authToken());
        Assertions.assertNotNull(listGamesResult.games(), "Retrieved list of games");
    }

    @Test
    public void listFail() throws Exception {
        RegisterResult result = facade.register("brak", "pablo", "@me");
        facade.createGame(result.authToken(), "timr");
        facade.createGame(result.authToken(), "johny");
        try {
            ListGamesResult listGamesResult = facade.listGames("wrong");
        } catch (Exception e) {
            Assertions.assertNotNull(e.getMessage(), "No games found");
        }
    }

    @Test
    public void joinGameSuccess() throws Exception {
        RegisterResult result = facade.register("brome", "pablo", "@me");
        CreateGameResult createGameResult = facade.createGame(result.authToken(), "timy");
        JoinGameResult joinGameResult = facade.joinGame(result.authToken(), ChessGame.TeamColor.WHITE,
                createGameResult.gameID());
        Assertions.assertNull(joinGameResult.message(), "Joined game correctly");
    }

    @Test
    public void joinGameFail() throws Exception {
        RegisterResult result = facade.register("ben", "pablo", "@me");
        facade.createGame(result.authToken(), "timer");
        try {
            JoinGameResult joinGameResult = facade.joinGame(result.authToken(), ChessGame.TeamColor.WHITE,
                    0302);
        } catch (Exception e) {
            Assertions.assertNotNull(e.getMessage(), "Failed join game correctly");
        }
    }

    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

}
