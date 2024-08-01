package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import requestresult.LoginRequest;
import requestresult.LoginResult;
import requestresult.LogoutRequest;
import requestresult.LogoutResult;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LogoutServiceTest {
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
            authDAO.clear();
            authDAO.createAuth(new AuthData("tolk", "me"));
        } catch (Exception ignored) {}
    }

    @Test
    @Order(1)
    @DisplayName("Logout Succeed Test")
    void logoutSucceed() throws DataAccessException {
        LogoutService service = new LogoutService(authDAO);
        LogoutResult result = service.logout(new LogoutRequest("tolk"));
        Assertions.assertNull(authDAO.getAuth("tolk"),"Deleted token");
    }

    @Test
    @Order(2)
    @DisplayName("Logout Fail Test")
    void logoutFail() throws DataAccessException {
        LogoutService service = new LogoutService(authDAO);
        LogoutResult result = service.logout(new LogoutRequest("book"));
        Assertions.assertEquals("Error: unauthorized", result.message(),"Wrong token");
    }
}