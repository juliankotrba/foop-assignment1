package connection;

import dto.messages.Message;
import exception.ReaderException;

import java.util.List;
import java.util.Objects;

/**
 * Listener for incoming socket messages
 *
 * @author  Julian Kotrba
 */
public class MessageListener implements Runnable {

    private StreamReader streamReader;
    private List<OnMessageReceivedListener<Message>> onMessageReceivedListeners;

    public MessageListener(StreamReader streamReader, List<OnMessageReceivedListener<Message>> onMessageReceivedListeners) {
        this.streamReader = streamReader;
        this.onMessageReceivedListeners = onMessageReceivedListeners;
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message message = this.streamReader.read();
                this.notifyListeners(message);
            } catch (ReaderException e) {
                // TODO: Proper thread killing
                Thread.currentThread().interrupt();

                //e.printStackTrace();
                System.out.println("Shutting down message listener thread: \n" + e.getLocalizedMessage());
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
