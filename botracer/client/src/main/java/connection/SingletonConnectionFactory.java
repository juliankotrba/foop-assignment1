package connection;

import connection.impl.ObjectStreamReader;
import connection.impl.ObjectStreamWriter;
import connection.impl.SocketConnection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * Factory class for a connection
 *
 * @author Julian Kotrba
 */
public class SingletonConnectionFactory {

	private static Connection connection;

	private SingletonConnectionFactory() {
	}

	/**
	 * Creates a connection
	 *
	 * @return the created connection instance
	 */
	public static synchronized Connection getInstance() {
		if (connection == null) {
			connection = new SocketConnection(
					new Socket(),
					new ObjectStreamWriter(),
					new ObjectStreamReader(),
					getProperties()
			);
		}
		return connection;
	}

    /*public static synchronized Connection getDummyInstance() {
        if (connection == null) {
            connection = new DummyConnection();
        }
        return connection;
    }*/

	private static Properties getProperties() {
		try {
			String encodedPath = SingletonConnectionFactory.class.getResource("/").getPath();
			String path = URLDecoder.decode(encodedPath, "utf-8");

			InputStream fileInputStream = new FileInputStream(path + "config.properties");

			Properties properties = new Properties();
			properties.load(fileInputStream);

			return properties;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Properties file not found.", e);
		} catch (IOException e) {
			throw new RuntimeException("Failed reading properties file.", e);
		}
	}
}
