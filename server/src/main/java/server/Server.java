package server;

import Handlers.*;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import spark.*;

public class Server {
    private MemoryUserDAO userDAO = new MemoryUserDAO();
    private MemoryGameDAO gameDAO = new MemoryGameDAO();
    private MemoryAuthDAO authDAO = new MemoryAuthDAO();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();
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
