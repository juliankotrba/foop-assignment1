package connection;

import dto.messages.Message;
import dto.messages.OnMessageReceivedListener;
import exception.connection.ReaderException;

/**
 * Listener for incoming socket messages
 *
 * @author  Julian Kotrba
 */
public class MessageListener implements Runnable {

    private StreamReader streamReader;
    private OnMessageReceivedListener onMessageReceivedListener;

    public MessageListener(StreamReader streamReader, OnMessageReceivedListener onMessageReceivedListener) {
        this.streamReader = streamReader;
        this.onMessageReceivedListener = onMessageReceivedListener;
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message message = this.streamReader.read();
                message.accept(onMessageReceivedListener);

            } catch (ReaderException e) {
                Thread.currentThread().interrupt();

                //e.printStackTrace();
                System.out.println("Shutting down message listener thread: \n" + e.getLocalizedMessage());
            }
        }
    }
}
