package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyFirstServer {
    public static void main(String[] args) throws IOException, IOException {
        ServerSocket ss = new ServerSocket(1234);
        System.out.println("I am waiting for the client to connect :D");
        Socket s = ss.accept(); // Instruction bloquante
        InputStream is = s.getInputStream(); //I want to read
        OutputStream os = s.getOutputStream(); //I want to write
        System.out.println("Sent me the number :D");
        int number = is.read();
        System.out.println("The client gives me the number" + number);
        int calc = number * 100;
        System.out.println("I calculated and I will sent the calculation");
        os.write(calc);
        s.close();

    }
}
