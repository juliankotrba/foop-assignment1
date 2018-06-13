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

    /**
     * Connects to a server
     *
     * @throws ConnectionException If connection fails
     */
    void connect() throws ConnectionException;

    /**
     * Set UIManager to register for callbacks
     * @param uiManager The UIManager who receives the callbacks
     */
    void setUIManager(UIManager uiManager);

    /**
     * Disconnects from a server
     */
    void disconnect();

    /**
     * Sends a message to a server
     * @param message the message to be sent
     * @throws MessageException If sending the message fails
     * @throws ConnectionException If not connected to a server
     */
    void send(Message message) throws MessageException, ConnectionException;

    /**
     * Checks if the connection is established
     * @return true if connection is established, false otherwise
     */
    boolean isConnected();

}
