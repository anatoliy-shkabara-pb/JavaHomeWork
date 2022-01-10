package com.pb.shkabara.hw14;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatServer {

    public static void main(String[] args) throws Exception {
        int port = 1234;
        ServerSocket serverSocket = new ServerSocket(port);

        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<ClientHandler> handlers = Collections.synchronizedList(new ArrayList<>());

        System.out.println("Сервер запустился на порту " + port);

        while (true) {
            Socket socket = serverSocket.accept();
            ClientHandler handler = new ClientHandler(socket, handlers);
            handlers.add(handler);
            System.out.println("Клиент " + handler.getCurrentIndex() + " подключился");
            pool.submit(handler);
        }
    }

    static class ClientHandler implements Runnable {

        private static final AtomicInteger INDEX = new AtomicInteger(0);

        private final Socket socket;
        private final BufferedReader reader;
        private final PrintWriter writer;
        private final List<ClientHandler> clientHandlers;
        private final int currentIndex;

        public int getCurrentIndex() {
            return currentIndex;
        }

        public ClientHandler(Socket socket, List<ClientHandler> clientHandlers) {
            this.currentIndex = INDEX.incrementAndGet();
            this.socket = socket;
            this.clientHandlers = clientHandlers;
            try {
                this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.writer = new PrintWriter(socket.getOutputStream(), true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        @Override
        public void run() {
            try {
                String message;
                while((message = reader.readLine()) != null) {
                    for(ClientHandler handler: clientHandlers) {
                        handler.sendMessage(message, currentIndex);
                    }
                }
                clientHandlers.remove(this);
                System.out.println("Клиент " + currentIndex + " отключился");
                socket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void sendMessage(String message, int clientIndex) {
            try {
                writer.println("Клиент " + clientIndex + ": " + message);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
