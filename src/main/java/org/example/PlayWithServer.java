package org.example;

import java.io.*;
import java.lang.invoke.MutableCallSite;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.IllegalFormatException;
import java.util.Random;
import java.util.SortedMap;

public class PlayWithServer extends Thread {
    private int ClientNumber;
    private int SecretNbre;
    private boolean fin;
    private String Winner;
    public static void main(String[] args) {
        new PlayWithServer().start();
    }

    @Override
    public void run(){
        try {
            ServerSocket ss = new ServerSocket(1234);
            SecretNbre = new Random().nextInt(100);
            System.out.println(SecretNbre);
            System.out.println("The server tries to start ....");
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
        public void run() {
            try {
                InputStream is = null; //octet
                try {
                    is = s.getInputStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                InputStreamReader isr = new InputStreamReader(is); //caractere
                BufferedReader br = new BufferedReader(isr); //Chaîne de caractére
                OutputStream os = null;
                try {
                    os = s.getOutputStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String Ip = s.getRemoteSocketAddress().toString();
                System.out.println("The client number :" + ClientNumber + " And his IP :" + Ip);
                PrintWriter pw = new PrintWriter(os, true); //caractere
                pw.println("You are the client" + ClientNumber);
                pw.println("Guess the secret number .......  :D");
                while (true) {
                    String UserRequest = null;
                    try {
                        UserRequest = br.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    boolean RequestFormat = false;
                    int UserNbre = 0;
                    try {
                        UserNbre = Integer.parseInt(UserRequest);
                        RequestFormat = true;
                    } catch (NumberFormatException e) {
                        pw.println("My friend Please give me a number not a String of characters");
                    }
                    if (RequestFormat) {
                        System.out.println("The client : " + ClientNumber + "Has sent the number " + UserNbre);
                        if (!fin) {
                            if (UserNbre > SecretNbre)
                                pw.println("Your number is superior to the Secret Number");
                            else if (UserNbre < SecretNbre) pw.println("Your number is inferior to the secret Number");
                            else {
                                pw.println("Congratulations You get it !!!");
                                System.out.println("The winner is the client with number :" + ClientNumber + " And his IP :" + Ip);
                                fin = true;
                            }
                        } else {
                            pw.println("Game Over | The winner is : " + ClientNumber);
                        }


                    }


                }
            } finally {

            }
        }
}}
