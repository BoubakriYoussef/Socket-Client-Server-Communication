package org.example;

import java.io.*;
import java.lang.invoke.MutableCallSite;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.SortedMap;

public class MySecondServerMultiThread extends Thread {
    private int ClientNumber;
    public static void main(String[] args) {
        new MySecondServerMultiThread().start();
    }

    @Override
        public void run(){
        try {
            ServerSocket ss = new ServerSocket(1234);
            System.out.println("The server tries to start");
            while(true){
                Socket s = ss.accept(); //Accept client
                ++ClientNumber;
                new Communication(s,ClientNumber).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Classe interne
    public class Communication extends Thread{
        private final int ClientNumber;
        private Socket s;
        Communication(Socket s, int ClientNumber){
            this.s = s;
            this.ClientNumber = ClientNumber;
        }
        @Override
        public void run(){
            try {
                InputStream is = s.getInputStream(); //octet
                InputStreamReader isr = new InputStreamReader(is); //caractere
                BufferedReader br = new BufferedReader(isr); //Chaîne de caractére
                OutputStream os = s.getOutputStream();
                String Ip = s.getRemoteSocketAddress().toString();
                System.out.println("The client number "+ClientNumber+"And his IP"+Ip);
                PrintWriter pw = new PrintWriter(os, true); //caractere
                pw.println("You are the client"+ClientNumber);
                while(true){
                    String UserRequest = br.readLine();
                    pw.println("The size of your string is :" + UserRequest.length());
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
