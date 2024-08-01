package handlers;

import com.google.gson.Gson;
import dataaccess.*;
import requestresult.*;
import service.*;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
    private SQLAuthDAO authDAO;

    public LogoutHandler(SQLAuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var gson = new Gson();
        LogoutRequest req = new LogoutRequest(request.headers("authorization"));
        LogoutService service = new LogoutService(authDAO);
        LogoutResult result = service.logout(req);
        if (result.message() == null) {
            response.status(200);
        } else if (result.message().equals("Error: unauthorized")) {
            response.status(401);
        } else {response.status(500);}
        return gson.toJson(result);
    }
}
