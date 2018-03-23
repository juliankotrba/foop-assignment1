package connection;

import dto.messages.Message;
import exception.connection.ReaderException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Interface for a stream reader
 *
 * @author Julian Kotrba
 */
public interface StreamReader {
    Message read() throws ReaderException;

    void openStream(InputStream in) throws IOException;

    void close();
}
