package exception.connection;

/**
 * Message writer exception
 *
 * @author Julian Kotrba
 */
public class WriterException extends Exception {

	public WriterException() {
	}

	public WriterException(String message) {
		super(message);
	}

	public WriterException(String message, Throwable cause) {
		super(message, cause);
	}

	public WriterException(Throwable cause) {
		super(cause);
	}

	public WriterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
