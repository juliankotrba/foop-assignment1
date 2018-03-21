package connection;

import dto.Message;
import exception.ConnectionException;
import exception.MessageException;
import exception.WriterException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

/**
 * Socket connection
 *
 * @author Julian Kotrba
 */
public class SocketConnection implements Connection {

    private Socket socket;
    private StreamWriter streamWriter;
    private StreamReader streamReader;

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private List<OnMessageReceivedListener> onMessageReceivedListeners;
    private Thread messageListenerThread;
    private Properties properties;
    private boolean isConnected;

    /**
     * SocketConnection constructor
     *
     * @param socket an unconnected socket instance
     * @param streamWriter implementation of a {@link StreamWriter}. Must not be null.
     * @param streamReader implementation of a {@link StreamReader}. Must not be null.
     * @param properties a properties instance with the loaded client config file. Must not be null.
     */
    SocketConnection(Socket socket, StreamWriter streamWriter, StreamReader streamReader, Properties properties) {

        this.socket = socket;
        this.streamWriter = streamWriter;
        this.properties = properties;
        this.streamReader = streamReader;

        this.isConnected = false;
        this.onMessageReceivedListeners = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void connect() throws ConnectionException {
        if (!this.isConnected) {

            final String host = this.properties.getProperty("host");
            final int port = Integer.parseInt(this.properties.getProperty("port"));
            try {

                this.setupSocket(new InetSocketAddress(host, port));
                this.startListenerThread();

                this.isConnected = true;
            } catch (IOException e) {
                throw new ConnectionException("Connecting to server failed.", e);
            }
        }
    }

    @Override
    public void connectAndListen(OnMessageReceivedListener onMessageReceivedListener) throws ConnectionException {
        this.connect();
        this.setMessageListener(onMessageReceivedListener);
    }

    @Override
    public void disconnect() {
        this.closeObjectOutputStream();
        this.closeObjectInputStream();
        this.closeSocket();

        this.onMessageReceivedListeners.clear();
        this.isConnected = false;
    }

    @Override
    public void send(Message message) throws MessageException, ConnectionException {
        if (!this.isConnected) {
            throw new ConnectionException("Not connected.");
        }

        try {
            this.streamWriter.write(message);
            //this.objectOutputStream.writeObject(message);
        } catch (WriterException e) {
            throw new MessageException("Sending message failed.", e);
        }
    }

    @Override
    public void setMessageListener(OnMessageReceivedListener onMessageReceivedListener) {
        if (this.onMessageReceivedListeners != null) {
            this.onMessageReceivedListeners.add(onMessageReceivedListener);
        }
    }

    @Override
    public boolean isConnected() {
        return this.isConnected;
    }

    private void setupSocket(InetSocketAddress address) throws IOException {
        this.socket.connect(address);

        this.streamWriter.openStream(socket.getOutputStream());
        this.streamReader.openStream(socket.getInputStream());

        //this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        //this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    private void startListenerThread() {
        this.messageListenerThread = new Thread(
                new MessageListener(this.streamReader, this.onMessageReceivedListeners)
        );

        this.messageListenerThread.start();
    }

    private void closeObjectOutputStream() {

        this.streamWriter.close();
        /*if (this.objectOutputStream != null) {
            try {
                this.objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    private void closeObjectInputStream() {

        this.streamReader.close();

        /* if (this.objectInputStream != null) {
            try {
                this.objectInputStream.close();
                this.messageListenerThread.join();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } */
    }

    private void closeSocket() {
        if (this.socket != null) {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
