package connection;

import dto.messages.Message;
import exception.connection.ConnectionException;
import exception.connection.MessageException;

/**
 * Connection
 *
 * @author  Julian Kotrba
 */
public interface Connection {
    void connect() throws ConnectionException;

    void connectAndListen(OnMessageReceivedListener<Message> onMessageReceivedListener) throws ConnectionException;
    void disconnect();
    void send(Message message) throws MessageException, ConnectionException;

    void setMessageListener(OnMessageReceivedListener<Message> onMessageReceivedListener);
    boolean isConnected();
}
