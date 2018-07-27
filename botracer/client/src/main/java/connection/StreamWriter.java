package connection;

import dto.messages.Message;
import exception.connection.WriterException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Interface for a stream writer
 *
 * @author Julian Kotrba
 */
public interface StreamWriter {

	/**
	 * Writes to a stream
	 *
	 * @param message the message to be written
	 * @throws WriterException if writing to a stream fails
	 */
	void write(Message message) throws WriterException;

	/**
	 * Opens a stream
	 *
	 * @param out any output stream
	 * @throws IOException if opening the stream fails
	 */
	void openStream(OutputStream out) throws IOException;

	/**
	 * Closes a stream
	 */
	void close();
}
