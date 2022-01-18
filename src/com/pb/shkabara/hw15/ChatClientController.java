package com.pb.shkabara.hw15;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class ChatClientController implements Initializable {

    @FXML
    private TextArea chatText;
    @FXML
    private TextField sendText;

    private PrintWriter writer;

    @FXML
    public void sendButtonAction() {
        String text = sendText.getText();
        writer.println(text);
        sendText.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Socket socket = new Socket("localhost", 1234);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            Thread t = new Thread(() -> {
                try {
                    String text;
                    while ((text = reader.readLine()) != null) {
                        chatText.appendText(text + "\n");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            t.setDaemon(true);
            t.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
