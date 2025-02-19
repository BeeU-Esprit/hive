package tn.hive.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.hive.entities.Tournoi;
import tn.hive.services.TournoiService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class AjoutTournoiController {

    @FXML
    private DatePicker date_tournoi;

    @FXML
    private ComboBox<String> liste_type;

    @FXML
    private TextField nom_tournoi;

    @FXML
    private TextField trounoi_description;

    TournoiService tournoiService = new TournoiService();

    @FXML
    void annulerAjout(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheTournois.fxml"));
        try {
            Parent parent = loader.load();
            date_tournoi.getScene().setRoot(parent);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void confirmerAjout(ActionEvent event) {
        try {
            tournoiService.addEntity(new Tournoi(nom_tournoi.getText() ,liste_type.getValue(), Date.valueOf(date_tournoi.getValue()), trounoi_description.getText()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void initialize(){
        liste_type.setItems(FXCollections.observableArrayList("Football", "Basketball", "Volley-ball", "Baseball", "Rugby", "Handball","Tennis", "Badminton", "Tennis de table", "Squash","Golf", "Bowling", "Bocce", "Croquet","Water-polo", "Dodgeball", "Sepak Takraw", "Lacrosse"));

    }

}
