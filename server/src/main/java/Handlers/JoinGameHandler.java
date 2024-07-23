package Handlers;

import com.google.gson.Gson;
import dataaccess.*;
import requestresult.*;
import service.*;
import spark.*;

public class JoinGameHandler implements Route{
    private MemoryAuthDAO authDAO;
    private MemoryGameDAO gameDAO;

    public JoinGameHandler(MemoryAuthDAO authDAO, MemoryGameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var gson = new Gson();
        JoinGameRequest temp = gson.fromJson(request.body(), JoinGameRequest.class);
        String token = gson.fromJson(request.headers("authorization"), JoinGameRequest.class).authorization();
        JoinGameRequest req = new JoinGameRequest(temp.playerColor(), temp.gameID(), token);
        JoinGameService service = new JoinGameService(gameDAO, authDAO);
        JoinGameResult result = service.joinGame(req);
        if (result.message().equals("Error: bad request")) {
            response.status(400);
        } else if (result.message().equals("Error: unauthorized")) {
            response.status(401);
        } else if (result.message().equals("Error: already taken")) {
            response.status(403);
        } else if (result.message().equals("Error: database error")) {
            response.status(500);
        } else response.status(200);
        if (result.message() == null) {
            return "{}";
        }
        return gson.toJson(result);
    }
}