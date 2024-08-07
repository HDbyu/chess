package serveraccess;

import com.google.gson.Gson;
import requestresult.LoginResult;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

public class ServerFacade {
    public LoginResult login(String username, String password) throws Exception {
        // Specify the desired endpoint
        URI uri = new URI("http://localhost:8080/name");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("POST");

        // Make the request
        http.connect();

        // Output the response body
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            return new Gson().fromJson(inputStreamReader, LoginResult.class);
        }
    }
}
