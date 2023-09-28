module com.example.guessthenumber {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.guessthenumber to javafx.fxml;
    exports com.example.guessthenumber;
}