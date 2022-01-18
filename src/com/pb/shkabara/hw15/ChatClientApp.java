package com.pb.shkabara.hw15;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class ChatClientApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(
                Paths.get("src/com/pb/shkabara/hw15/chat-window.fxml")
                        .toUri()
                        .toURL()
        );

        Scene scene = new Scene(root);

        stage.setTitle("Chat");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
