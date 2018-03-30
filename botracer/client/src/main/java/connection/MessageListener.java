package connection;

import dto.messages.Message;
import dto.messages.OnMessageReceivedListener;
import exception.connection.ReaderException;
import gui.GameMap.receivers.MessageReceiver;
import gui.MainController;

import java.util.List;
import java.util.Objects;

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
