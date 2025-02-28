package tn.hive.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ErrorController {

    @FXML
    private VBox error;

    @FXML
    private Label message;

    public void setMessage(String message){
        this.message.setText(message);
    }
}
