package serveraccess;

import com.google.gson.Gson;
import requestresult.LoginRequest;
import requestresult.LoginResult;
import requestresult.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

public class ServerFacade {

    int port;
    public ServerFacade(int port) {
        this.port = port;
    }
    public LoginResult login(String username, String password) throws Exception {
        // Specify the desired endpoint
        URI uri = new URI("http://localhost:" + port +"/session");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        var body = new LoginRequest(username, password);
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(body);
            outputStream.write(jsonBody.getBytes());
        }


        // Make the request
        http.connect();

        // Output the response body
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            return new Gson().fromJson(inputStreamReader, LoginResult.class);
        }
    }

    public RegisterResult register(String username, String password, String email) throws Exception {
        // Specify the desired endpoint
        URI uri = new URI("http://localhost:" + port +"/user");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        var body = new RegisterRequest(username, password, email);
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(body);
            outputStream.write(jsonBody.getBytes());
        }

        // Make the request
        http.connect();

        // Output the response body
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            return new Gson().fromJson(inputStreamReader, RegisterResult.class);
        }
    }
}
