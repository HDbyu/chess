package serveraccess;

import chess.ChessGame;
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
        } catch (Exception e) {
            int statusCode = http.getResponseCode();
            return new LoginResult(null, null, Integer.toString(statusCode));
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
        } catch (Exception e) {
            int statusCode = http.getResponseCode();
            return new RegisterResult(null, null, Integer.toString(statusCode));
        }
    }

    public ListGamesResult listGames(String auth) throws Exception {
        // Specify the desired endpoint
        URI uri = new URI("http://localhost:" + port +"/game");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("GET");
        http.setDoOutput(true);
        http.addRequestProperty("Authorization", auth);

        // Make the request
        http.connect();

        // Output the response body
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            return new Gson().fromJson(inputStreamReader, ListGamesResult.class);
        } catch (Exception e) {
            int statusCode = http.getResponseCode();
            return new ListGamesResult(null, Integer.toString(statusCode));
        }
    }

    public CreateGameResult createGame(String auth, String gameName) throws Exception {
        // Specify the desired endpoint
        URI uri = new URI("http://localhost:" + port +"/game");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.addRequestProperty("Authorization", auth);
        var body = new CreateGameRequest(gameName, auth);
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(body);
            outputStream.write(jsonBody.getBytes());
        }

        // Make the request
        http.connect();

        // Output the response body
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            return new Gson().fromJson(inputStreamReader, CreateGameResult.class);
        } catch (Exception e) {
            int statusCode = http.getResponseCode();
            return new CreateGameResult(null, Integer.toString(statusCode));
        }
    }

    public JoinGameResult joinGame(String auth, ChessGame.TeamColor color, int gameID) throws Exception {
        // Specify the desired endpoint
        URI uri = new URI("http://localhost:" + port +"/game");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("PUT");
        http.setDoOutput(true);
        http.addRequestProperty("Authorization", auth);
        var body = new JoinGameRequest(color, gameID, auth);
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(body);
            outputStream.write(jsonBody.getBytes());
        }
        http.connect();

        // Output the response body
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            return new Gson().fromJson(inputStreamReader, JoinGameResult.class);
        } catch (Exception e) {
            int statusCode = http.getResponseCode();
            return new JoinGameResult(Integer.toString(statusCode));
        }
    }

    public LogoutResult logout(String auth) throws Exception {
        // Specify the desired endpoint
        URI uri = new URI("http://localhost:" + port +"/session");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("DELETE");
        http.setDoOutput(true);
        http.addRequestProperty("Authorization", auth);

        http.connect();

        // Output the response body
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            return new Gson().fromJson(inputStreamReader, LogoutResult.class);
        }catch (Exception e) {
            int statusCode = http.getResponseCode();
            return new LogoutResult(Integer.toString(statusCode));
        }
    }
}
