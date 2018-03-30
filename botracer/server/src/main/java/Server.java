


import dto.messages.s2c.GameStartMessage;
import dto.messages.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class  Server {
    /**
     * The port that the server listens on.
     */
    private static final int PORT = 9001;


    /**
     * The set of all the print writers for all the clients.  This
     * set is kept so we can easily broadcast messages.
     */
    private static HashSet<ObjectOutputStream> writers = new HashSet<ObjectOutputStream>();

    /**
     * The appplication main method, which just listens on a port and
     * spawns handler threads.
     */
    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running.");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                //new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }



}
