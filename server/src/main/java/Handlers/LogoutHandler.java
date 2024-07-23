package Handlers;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import requestresult.*;
import service.*;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
    private MemoryAuthDAO authDAO;

    public LogoutHandler(MemoryAuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var gson = new Gson();
        LogoutRequest req = gson.fromJson(request.headers("authorization"), LogoutRequest.class);
        LogoutService service = new LogoutService(authDAO);
        LogoutResult result = service.logout(req);
        if (result.message().equals("Error: unauthorized")) {
            response.status(401);
        } else if (result.message().equals("Error: database error")) {
            response.status(500);
        } else response.status(200);
        if(result.message() == null) {
            return "{}";
        }
        return gson.toJson(result);
    }
}