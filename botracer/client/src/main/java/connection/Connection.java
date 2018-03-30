package connection;

import dto.messages.Message;
import exception.connection.ConnectionException;
import exception.connection.MessageException;
import gui.UIManager;

/**
 * Connection
 *
 * @author  Julian Kotrba
 */
public interface Connection {
    void connect() throws ConnectionException;
    void setUIManager(UIManager uiManager);
    void disconnect();
    void send(Message message) throws MessageException, ConnectionException;

    boolean isConnected();

}
