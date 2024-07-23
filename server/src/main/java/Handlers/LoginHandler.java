package Handlers;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import requestresult.*;
import service.LoginService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginHandler implements Route {
    private MemoryUserDAO userDAO;
    private MemoryAuthDAO authDAO;

    public LoginHandler(MemoryAuthDAO authDAO, MemoryUserDAO userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var gson = new Gson();
        LoginRequest req = gson.fromJson(request.body(), LoginRequest.class);
        LoginService service = new LoginService(authDAO, userDAO);
        LoginResult result = service.login(req);
        if (result.message() == null) {
            response.status(200);
        } else if (result.message().equals("Error: unauthorized")) {
            response.status(401);
        } else response.status(500);
        return gson.toJson(result);
    }
}
