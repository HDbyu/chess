package requestresult;

import model.GameData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public record ListGamesResult(Collection<GameData> games, String message) {
}
