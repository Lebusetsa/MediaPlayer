module com.example.moorosimedia {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.moorosimedia to javafx.fxml;
    exports com.example.moorosimedia;
}