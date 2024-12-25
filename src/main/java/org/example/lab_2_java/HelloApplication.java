package org.example.lab_2_java;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.BOTTOM_CENTER);

        Canvas canvas = new Canvas(600, 600);
        vBox.getChildren().add(canvas);

        Button buttonSelect = new Button("Cелектор");
        vBox.getChildren().add(buttonSelect);
        VBox.setMargin(buttonSelect, new Insets(30,20,20,30));

        Scene scene = new Scene(vBox);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.fill();

        buttonSelect.setOnAction(e ->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serialized", "*.csv"));

            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                CSVReader.reader(file, canvas.getHeight(), canvas.getWidth(), gc, canvas);
            }
        });
    }


    public static void main(String[] args) {
        launch();
    }
}