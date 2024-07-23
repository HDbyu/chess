package Handlers;

import com.google.gson.Gson;
import dataaccess.*;
import requestresult.*;
import service.*;
import spark.*;

public class ClearHandler implements Route{
    private MemoryAuthDAO authDAO;
    private MemoryGameDAO gameDAO;
    private MemoryUserDAO userDAO;

    public ClearHandler(MemoryAuthDAO authDAO, MemoryGameDAO gameDAO, MemoryUserDAO userDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var gson = new Gson();
        ClearService service = new ClearService(gameDAO, authDAO, userDAO);
        ClearResult result = service.clear();
        if (result.message().equals("Error: database error")) {
            response.status(500);
        } else {
            response.status(200);
            return "{}";
        }
        return gson.toJson(result);
    }
}
