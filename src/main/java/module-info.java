module org.example.lab_2_java {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.lab_2_java to javafx.fxml;
    exports org.example.lab_2_java;
}