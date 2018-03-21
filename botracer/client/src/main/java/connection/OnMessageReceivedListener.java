package connection;

import dto.Message;

/**
 * Callback for received message
 *
 * @author  Julian Kotrba
 */
public interface OnMessageReceivedListener<T extends Message> {
    void onMessageReceived(T message);
}
