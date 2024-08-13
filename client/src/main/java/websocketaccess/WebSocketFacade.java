package websocketaccess;

import com.google.gson.Gson;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import javax.websocket.ContainerProvider;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

public class WebSocketFacade {

    private Session session;

    public WebSocketFacade(String url, GameHandler handler) throws Exception {
        URI uri = new URI("ws://"+ url +"/ws");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                Gson gson = new Gson();
                ServerMessage msg = gson.fromJson(message, ServerMessage.class);
                if (msg.getServerMessageType().equals(ServerMessage.ServerMessageType.LOAD_GAME)) {
                    LoadGameMessage load = gson.fromJson(message, LoadGameMessage.class);
                    handler.updateGame(load.getGame());
                } else if (msg.getServerMessageType().equals(ServerMessage.ServerMessageType.ERROR)) {
                    ErrorMessage error = gson.fromJson(message, ErrorMessage.class);
                    handler.printError(error.getErrorMessage());
                } else if (msg.getServerMessageType().equals(ServerMessage.ServerMessageType.NOTIFICATION)) {
                    NotificationMessage notification = gson.fromJson(message, NotificationMessage.class);
                    handler.printError(notification.getMessage());
                }
            }
        });
    }

    public void send(UserGameCommand command) throws IOException {
        Gson gson = new Gson();
        this.session.getBasicRemote().sendText(gson.toJson(command));
    }
}
