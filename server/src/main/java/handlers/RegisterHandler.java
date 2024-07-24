package handlers;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import requestresult.RegisterRequest;
import requestresult.RegisterResult;
import service.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterHandler implements Route {
    private MemoryUserDAO userDAO;
    private MemoryAuthDAO authDAO;

    public RegisterHandler(MemoryAuthDAO authDAO, MemoryUserDAO userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        var gson = new Gson();
        RegisterRequest req = gson.fromJson(request.body(), RegisterRequest.class);
        RegisterService service = new RegisterService(authDAO, userDAO);
        RegisterResult result = service.register(req);
        if (result.message() == null) {
            response.status(200);
        } else if (result.message().equals("Error: bad request")) {
            response.status(400);
        } else if (result.message().equals("Error: already taken")) {
            response.status(403);
        } else {response.status(500);}
        return gson.toJson(result);
    }
}
