package dataaccess;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SQLAuthDAOTest {

    private static SQLAuthDAO authDAO;

    static {
        try {
            authDAO = new SQLAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void init() {
        try {
            authDAO.clear();
        } catch (Exception ignored) { }
    }

    @Test
    @Order(1)
    @DisplayName("Clear Auths Succeed Test")
    void clearAuthSucceed() throws DataAccessException {
        authDAO.createAuth(new AuthData("bruby", "me"));
        authDAO.createAuth(new AuthData("cubu", "you"));
        authDAO.createAuth(new AuthData("mubru", "us"));
        authDAO.clear();
        Assertions.assertNull(authDAO.getAuth("bruby"), "No token");
        Assertions.assertNull(authDAO.getAuth("cubu"), "No token");
        Assertions.assertNull(authDAO.getAuth("mubru"), "No token");
    }

    @Test
    @Order(2)
    @DisplayName("Create Auth Succeed Test")
    void createAuthSucceed() throws DataAccessException {
        authDAO.createAuth(new AuthData("pass", "boby"));
        Assertions.assertInstanceOf(AuthData.class, authDAO.getAuth("pass"), "Created auth data");
    }

    @Test
    @Order(3)
    @DisplayName("Create Auth Fail Test")
    void createAuthFail() throws DataAccessException {
        try {
            authDAO.createAuth(new AuthData(null, "boby"));
        } catch (DataAccessException e) {
            Assertions.assertEquals("Column 'auth' cannot be null", e.getMessage(), "Failed to create user");
        }
    }

    @Test
    @Order(4)
    @DisplayName("Get Auth Succeed Test")
    void getAuthSucceed() throws DataAccessException {
        authDAO.createAuth(new AuthData("pass", "boby"));
        AuthData token = authDAO.getAuth("pass");
        Assertions.assertInstanceOf(AuthData.class, token, "Got token");
    }

    @Test
    @Order(5)
    @DisplayName("Get Auth Fail Test")
    void getAuthFail() throws DataAccessException {
        authDAO.createAuth(new AuthData("pass", "boby"));
        AuthData token = authDAO.getAuth("broke");
        Assertions.assertNull(token, "failed to find user");
    }

    @Test
    @Order(6)
    @DisplayName("Delete Auth Succeed Test")
    void deleteAuthSucceed() throws DataAccessException {
        authDAO.createAuth(new AuthData("pass", "boby"));
        authDAO.deleteAuth("pass");
        Assertions.assertNull(authDAO.getAuth("pass"), "No token");
    }

    @Test
    @Order(6)
    @DisplayName("Delete Auth Fail Test")
    void deleteAuthFail() throws DataAccessException {
        authDAO.createAuth(new AuthData("pass", "boby"));
        authDAO.deleteAuth("bro");
        Assertions.assertNotNull(authDAO.getAuth("pass"), "Still token");
    }

}