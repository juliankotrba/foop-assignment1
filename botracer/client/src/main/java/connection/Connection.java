package connection;

import dto.Message;
import exception.ConnectionException;
import exception.MessageException;

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
