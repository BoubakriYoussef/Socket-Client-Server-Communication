package org.example;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MyFirstClient {
    public static void main(String[] args) throws IOException {
        System.out.println("I will connect to the server");
        Socket s = new Socket("localhost",1234);
        InputStream is = s.getInputStream(); //I want to read
        OutputStream os = s.getOutputStream(); //I want to write
        Scanner sc = new Scanner(System.in);
        System.out.println("Give us a number");
        int nbre = sc.nextInt();
        System.out.println("I will send to the server the number "+ nbre);
        os.write(nbre);
        System.out.println("I am waiting for the server to answer my request :D");
        int answer = is.read();
        System.out.println("The answer of the server is :D "+ answer);

    }
}
