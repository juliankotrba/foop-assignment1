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

    /**
     * Reads a message from a stream
     *
     * @return the read message
     * @throws ReaderException if reading from the stream fails
     */
    Message read() throws ReaderException;

    /**
     * Opens a stream
     * @param in any input stream
     * @throws IOException if opening the stream fails
     */
    void openStream(InputStream in) throws IOException;

    /**
     * Closes the stream
     */
    void close();
}
