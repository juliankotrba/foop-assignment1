package connection;

import dto.messages.Message;
import exception.WriterException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Interface for a stream reader
 *
 * @author Julian Kotrba
 */
public interface StreamWriter {
    void write(Message message) throws WriterException;
    void openStream(OutputStream out) throws IOException;
    void close();
}
