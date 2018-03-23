package connection;

import dto.messages.Message;
import exception.WriterException;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Object output stream writer
 *
 * @author Julian Kotrba
 */
public class ObjectStreamWriter implements StreamWriter {

    private ObjectOutputStream outputStream;

    @Override
    public void openStream(OutputStream out) throws IOException {
        this.outputStream = new ObjectOutputStream(out);
    }

    @Override
    public void close() {
        if (this.outputStream != null) {
            try {
                this.outputStream.close();
            } catch (IOException e) {
                System.out.println("I don't care.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void write(Message message) throws WriterException {
        try {
            this.outputStream.writeObject(message);
        } catch (IOException e) {
            throw new WriterException("Writing message failed.", e);
        }
    }
}
