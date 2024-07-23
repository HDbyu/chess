package Handlers;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import requestresult.RegisterRequest;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterHandler implements Route {
    private MemoryUserDAO userDAO;
    private MemoryGameDAO gameDAO;
    private MemoryAuthDAO authDAO;

    public RegisterHandler(MemoryGameDAO gameDAO, MemoryAuthDAO authDAO, MemoryUserDAO userDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var gson = new Gson();
        RegisterRequest req = gson.fromJson(request.body(), RegisterRequest.class);
        response.status(200);
        return gson.toJson();
    }
}
