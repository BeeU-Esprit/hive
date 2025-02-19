package tn.hive.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class NavigationController {

    @FXML
    private Button page_navigator;

    @FXML
    private ComboBox<String> page_select;

    @FXML
    void goTo(ActionEvent event) {
        String selected_page = page_select.getValue();

        if (selected_page != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/tournoi_match/"+selected_page+".fxml"));
            try {
                Parent parent = loader.load();
                page_navigator.getScene().setRoot(parent);
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void initialize(){
        page_select.setItems(FXCollections.observableArrayList("AfficheTournois", "AfficheMatchs"));
    }

}
