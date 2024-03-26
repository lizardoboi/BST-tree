module com.example.bsttree {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bsttree to javafx.fxml;
    exports com.example.bsttree;
}