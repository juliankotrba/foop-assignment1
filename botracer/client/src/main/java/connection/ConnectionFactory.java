package connection;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * Factory class for a connection
 *
 * @author  Julian Kotrba
 */
public class ConnectionFactory {

    private static Connection connection;

    private ConnectionFactory() { }

    public static synchronized Connection getInstance() {
        if (connection == null) {
           connection = new SocketConnection(new Socket(), getProperties());
        }
        return connection;
    }

    private static Properties getProperties() {
        try {
            String encodedPath = ConnectionFactory.class.getResource("/").getPath();
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
