package handlers;

import com.google.gson.Gson;
import dataaccess.*;
import requestresult.*;
import service.*;
import spark.*;

public class CreateGameHandler implements Route{
    private SQLAuthDAO authDAO;
    private SQLGameDAO gameDAO;

    public CreateGameHandler(SQLAuthDAO authDAO, SQLGameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var gson = new Gson();
        String name = gson.fromJson(request.body(), CreateGameRequest.class).gameName();
        String token = request.headers("authorization");
        CreateGameRequest req = new CreateGameRequest(name, token);
        CreateGameService service = new CreateGameService(gameDAO, authDAO);
        CreateGameResult result = service.createGame(req);
        if (result.message() == null) {
            response.status(200);
        } else if (result.message().equals("Error: bad request")) {
            response.status(400);
        } else if (result.message().equals("Error: unauthorized")) {
            response.status(401);
        } else {response.status(500);}
        return gson.toJson(result);
    }
}
