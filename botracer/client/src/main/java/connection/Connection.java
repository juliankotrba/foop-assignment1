package connection;

import dto.messages.Message;
import dto.messages.OnMessageReceivedListener;
import exception.connection.ConnectionException;
import exception.connection.MessageException;
import gui.MainController;

/**
 * Connection
 *
 * @author  Julian Kotrba
 */
public interface Connection {
    void connect() throws ConnectionException;
    void setMainController(MainController maincontroller);
    void disconnect();
    void send(Message message) throws MessageException, ConnectionException;

    boolean isConnected();
}
