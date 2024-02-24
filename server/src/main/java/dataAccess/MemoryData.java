package dataAccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Map;

public class MemoryData {
    public Map<String, UserData> userTable;
    public Map<String, GameData> gameTable; //String is an int in disguise.
    public Map<String, AuthData> authTable;
}
