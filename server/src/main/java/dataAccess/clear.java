package dataAccess;

public class clear {
    String clearData(MemoryData data){
        data.authTable.clear();
        data.gameTable.clear();
        data.userTable.clear();
        return ("[200]");
    }
}
