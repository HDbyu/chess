package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
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
    private static MemoryAuthDAO authDAO = new MemoryAuthDAO();

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