package connection;

import dto.Message;

/**
 * Callback for received message
 *
 * @author  Julian Kotrba
 */
public interface OnMessageReceivedListener {
    void onMessageReceived(Message message);
}
