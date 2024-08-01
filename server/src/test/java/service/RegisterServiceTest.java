package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import requestresult.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegisterServiceTest {
    private static SQLUserDAO userDAO;

    static {
        try {
            userDAO = new SQLUserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static SQLAuthDAO authDAO;

    static {
        try {
            authDAO = new SQLAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void init(){
        try {
            new ClearService(new SQLGameDAO(), authDAO, userDAO).clear();
            userDAO.createUser(new UserData("boy", "goo", "@boyo"));
        } catch (Exception ignored) {}
    }

    @Test
    @Order(1)
    @DisplayName("Register Succeed Test")
    void registerSucceed() throws DataAccessException {
        RegisterService service = new RegisterService(authDAO, userDAO);
        RegisterResult result = service.register(new RegisterRequest("sam", "dontsteal", "@house"));
        Assertions.assertNotNull(result.authToken(),"Contains token");
        Assertions.assertEquals("sam", result.username(),"Contains username");
    }

    @Test
    @Order(2)
    @DisplayName("Register Fail Test")
    void registerFail() throws DataAccessException {
        RegisterService service = new RegisterService(authDAO, userDAO);
        RegisterResult result = service.register(new RegisterRequest("boy", "null", "@house"));
        Assertions.assertEquals("Error: already taken", result.message(),"Already in use");
    }
}