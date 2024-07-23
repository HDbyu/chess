package Handlers;

import com.google.gson.Gson;
import dataaccess.*;
import requestresult.*;
import service.*;
import spark.Request;
import spark.Response;
import spark.Route;

public class ListGamesHandler implements Route {
    private MemoryGameDAO gameDAO;
    private MemoryAuthDAO authDAO;

    public ListGamesHandler(MemoryAuthDAO authDAO, MemoryGameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var gson = new Gson();
        ListGamesRequest req = gson.fromJson(request.headers("authorization"), ListGamesRequest.class);
        ListGamesService service = new ListGamesService(gameDAO, authDAO);
        ListGamesResult result = service.listGames(req);
        if (result.message() == null) {
            response.status(200);
        } else if (result.message().equals("Error: unauthorized")) {
            response.status(401);
        } else response.status(500);
        return gson.toJson(result);
    }
}
