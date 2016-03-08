package fr.insa.lyon.tc.elp.streams;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by pvienne on 07/03/2016.
 */
public class SocketServer {
    private static int count = 1;
    public static void main(String... args){
        try {
            ServerSocket connection = new ServerSocket(4567);
            while(connection.isBound()) {
                Socket socket = connection.accept();
                new Thread(()->{
                    BufferedReader reader = null;
                    final int id = count++;
                    try {
                        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(new OutputStreamWriter( socket.getOutputStream() ), true );
                        out.println("Hello client "+id+", I am an echo server !");
                        String s;
                        while ((s = reader.readLine()) != null) {
                            System.out.println(String.format("[Client %d] %s", id, s));
                            out.println(s);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
