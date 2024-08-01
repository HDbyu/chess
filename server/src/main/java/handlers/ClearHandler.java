package handlers;

import com.google.gson.Gson;
import dataaccess.*;
import requestresult.*;
import service.*;
import spark.*;

public class ClearHandler implements Route{
    private MemoryAuthDAO authDAO;
    private SQLGameDAO gameDAO;
    private SQLUserDAO userDAO;

    public ClearHandler(MemoryAuthDAO authDAO, SQLGameDAO gameDAO, SQLUserDAO userDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var gson = new Gson();
        ClearService service = new ClearService(gameDAO, authDAO, userDAO);
        ClearResult result = service.clear();
        if (result.message() == null) {
            response.status(200);
        } else {
            response.status(500);
        }
        return gson.toJson(result);
    }
}
