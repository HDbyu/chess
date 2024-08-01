package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import requestresult.CreateGameRequest;
import requestresult.CreateGameResult;
import service.CreateGameService;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SQLUserDAOTest {

    private static SQLUserDAO userDAO;

    static {
        try {
            userDAO = new SQLUserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
     void init() {
        try {
            userDAO.clear();
        } catch (Exception ignored) { }
    }

    @Test
    @Order(1)
    @DisplayName("Create User Succeed Test")
    void createUserSucceed() throws DataAccessException {
        userDAO.createUser(new UserData("bob", "pass", "@you"));
        Assertions.assertInstanceOf(UserData.class, userDAO.getUser("bob"), "Created user");
    }

    @Test
    @Order(2)
    @DisplayName("Create User Fail Test")
    void createUserFail() throws DataAccessException {
        try {
            userDAO.createUser(new UserData("bob", "pass", null));
        } catch (DataAccessException e) {
            Assertions.assertEquals("Column 'email' cannot be null", e.getMessage(), "Failed to create user");
        }
    }

    @Test
    @Order(3)
    @DisplayName("Get User Succeed Test")
    void getUserSucceed() throws DataAccessException {
        userDAO.createUser(new UserData("gogo", "bra", "@youty"));
        UserData user = userDAO.getUser("gogo");
        Assertions.assertInstanceOf(UserData.class, user, "Got user");
    }

    @Test
    @Order(4)
    @DisplayName("Get User Fail Test")
    void getUserFail() throws DataAccessException {
        userDAO.createUser(new UserData("bob", "pass", "@cool"));
        UserData user = userDAO.getUser("sam");
        Assertions.assertNull(user, "failed to find user");
    }

    @Test
    @Order(5)
    @DisplayName("Clear Users Succeed Test")
    void clearUsersSucceed() throws DataAccessException {
        userDAO.createUser(new UserData("gogo", "bra", "@youty"));
        userDAO.createUser(new UserData("susy", "bub", "@gone"));
        userDAO.createUser(new UserData("help", "nono", "@yru"));
        userDAO.clear();
        Assertions.assertNull(userDAO.getUser("gogo"), "No user");
        Assertions.assertNull(userDAO.getUser("susy"), "No user");
        Assertions.assertNull(userDAO.getUser("help"), "No user");
    }
}