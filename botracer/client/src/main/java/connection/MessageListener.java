package connection;

import dto.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Objects;

/**
 * Listener for incoming socket messages
 *
 * @author  Julian Kotrba
 */
public class MessageListener implements Runnable {

    private ObjectInputStream objectInputStream;
    private List<OnMessageReceivedListener> onMessageReceivedListeners;

    public MessageListener(ObjectInputStream objectInputStream, List<OnMessageReceivedListener> onMessageReceivedListeners) {
        this.objectInputStream= objectInputStream;
        this.onMessageReceivedListeners = onMessageReceivedListeners;
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {

                Message message = (Message) objectInputStream.readObject();

                this.notifyListeners(message);
            } catch (IOException | ClassNotFoundException e) {
                // TODO: Proper thread killing
                Thread.currentThread().interrupt();
                System.out.println("Shutting down message listener thread.");
            }
        }
    }

    private void notifyListeners(Message message) {
        this.onMessageReceivedListeners
                .stream()
                .filter(Objects::nonNull)
                .forEach(listener -> listener.onMessageReceived(message));
    }
}
