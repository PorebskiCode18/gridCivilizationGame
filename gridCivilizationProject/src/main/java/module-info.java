module com.example.generaltemplate {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.generaltemplate to javafx.fxml;
    exports com.example.generaltemplate;
}