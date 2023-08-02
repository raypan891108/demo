package com.example.demo.connection;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkTest {

    public static void testConnection(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            System.out.println("Successfully connected to " + host + " on port " + port);
        } catch (UnknownHostException ex) {
            System.out.println("Unresolved host " + host);
        } catch (IOException ex) {
            System.out.println("Couldn't connect to " + host + " on port " + port);
        }
    }
}
