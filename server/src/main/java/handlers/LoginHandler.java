package handlers;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLUserDAO;
import requestresult.*;
import service.LoginService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginHandler implements Route {
    private SQLUserDAO userDAO;
    private SQLAuthDAO authDAO;

    public LoginHandler(SQLAuthDAO authDAO, SQLUserDAO userDAO) {
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
        } else {response.status(500);}
        return gson.toJson(result);
    }
}
