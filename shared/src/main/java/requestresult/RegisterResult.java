package requestresult;

import model.AuthData;

public record RegisterResult(String username, AuthData auth, String error) {
}
