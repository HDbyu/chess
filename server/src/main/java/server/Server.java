package server;

import dataAccess.MemoryData;
import spark.*;

import java.util.UUID;

public class Server {

    private MemoryData Data;
    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.init();
        //Spark.delete("/db")
        //Spark.post("/user", (req, res) -> RegisterHandler().read(req, res));
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
//UUID