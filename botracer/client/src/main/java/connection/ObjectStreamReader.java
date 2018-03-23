package connection;

import dto.messages.Message;
import exception.ReaderException;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * Object output stream reader
 *
 * @author Julian Kotrba
 */
public class ObjectStreamReader implements StreamReader {

    private ObjectInputStream inputStream;

    @Override
    public void openStream(InputStream in) throws IOException {
        this.inputStream = new ObjectInputStream(in);
    }

    @Override
    public void close() {
        if (this.inputStream != null) {
            try {
                this.inputStream.close();
            } catch (IOException e) {
                System.out.println("I don't care.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public Message read() throws ReaderException {
        try {
            return (Message) this.inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new ReaderException("Reading message failed.", e);
        }
    }
}
