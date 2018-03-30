package connection;

import dto.messages.Message;
import exception.connection.ReaderException;
import gui.receivers.MessageReceiver;
import gui.MainController;

/**
 * Listener for incoming socket messages
 *
 * @author  Julian Kotrba
 */
public class MessageListener implements Runnable {

    private StreamReader streamReader;
    private MainController mainController;

    public MessageListener(StreamReader streamReader, MainController mainController) {
        this.streamReader = streamReader;
        this.mainController = mainController;
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message message = this.streamReader.read();
                message.accept(new MessageReceiver(mainController));

            } catch (ReaderException e) {
                // TODO: Proper thread killing
                Thread.currentThread().interrupt();

                //e.printStackTrace();
                System.out.println("Shutting down message listener thread: \n" + e.getLocalizedMessage());
            }
        }
    }
}
