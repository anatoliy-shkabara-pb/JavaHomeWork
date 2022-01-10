package com.pb.shkabara.hw14;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 1234);
        Scanner scan = new Scanner(System.in);

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("Введите сообщение: ");
        new Thread(() -> {
            while (true) {
                String message = scan.nextLine();
                writer.println(message);
            }
        }).start();

        String fromServer;
        while ((fromServer = reader.readLine()) != null) {
            System.out.println(ANSI_GREEN + fromServer + ANSI_RESET);
            System.out.println("Введите сообщение: ");
        }

        System.out.println("Соединение с сервером прервано");
        System.exit(0);
    }
}
