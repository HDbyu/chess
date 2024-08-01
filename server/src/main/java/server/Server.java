package server;

import dataaccess.*;
import handlers.*;
import spark.*;

public class Server {
    private SQLUserDAO userDAO;
    private SQLGameDAO gameDAO;
    private MemoryAuthDAO authDAO = new MemoryAuthDAO();
    public Server() {
        try {
            userDAO = new SQLUserDAO();
            gameDAO = new SQLGameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();
        Spark.delete("/db", (req, res) -> new ClearHandler(authDAO, gameDAO, userDAO).handle(req, res));
        Spark.post("/user", (req, res) -> new RegisterHandler(authDAO, userDAO).handle(req, res));
        Spark.post("/session", (req, res) -> new LoginHandler(authDAO, userDAO).handle(req, res));
        Spark.delete("/session", (req, res) -> new LogoutHandler(authDAO).handle(req, res));
        Spark.get("/game", (req, res) -> new ListGamesHandler(authDAO, gameDAO).handle(req, res));
        Spark.post("/game", (req, res) -> new CreateGameHandler(authDAO, gameDAO).handle(req, res));
        Spark.put("/game", (req, res) -> new JoinGameHandler(authDAO, gameDAO).handle(req, res));
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
