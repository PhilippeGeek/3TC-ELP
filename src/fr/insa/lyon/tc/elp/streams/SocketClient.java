package fr.insa.lyon.tc.elp.streams;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by pvienne on 07/03/2016.
 */
public class SocketClient {
    public static void main(String... args){
        try {
            Socket socket = new Socket("localhost",4567);
            System.out.println("Connection accepted!");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(new OutputStreamWriter( socket.getOutputStream() ), true );
            String s;
            new Thread(() -> {
                while(!Thread.interrupted())
                    try {
                        String line = reader.readLine();
                        System.out.println("[SERVER]: "+ line);
                        if(line == null) break;
                    } catch (IOException e) {
                        System.err.println("[SERVER]: Can not read message : ");
                        e.printStackTrace(System.err);
                    }
            }).start();
            while ((s = keyboardInput.readLine()) != null) {
                out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
