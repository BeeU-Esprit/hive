package tn.hive.controllers.tournoi_match;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import tn.hive.entities.tournoi_match.Tournoi;
import tn.hive.services.tournoi_match.TournoiService;

import java.io.IOException;
import java.sql.Date;

public class AjoutTournoiController {

    @FXML
    private DatePicker date_tournoi;

    @FXML
    private ComboBox<String> liste_type;

    @FXML
    private TextField nom_tournoi;

    @FXML
    private TextField trounoi_description;

    @FXML
    private ImageView terrain_bg;

    @FXML
    private VBox error;

    @FXML
    private Label message;

    TournoiService tournoiService = new TournoiService();

    public void showError(String message, String color){
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        this.message.setText(message);
        error.setStyle("-fx-background-color: " + color);
        error.setVisible(true);
        pause.setOnFinished(event -> {
            error.setVisible(false);
            if (color.equals("#66ffcc")) {
                annulerAjout(new ActionEvent());
            }
        });
        pause.play();
    }

    @FXML
    void annulerAjout(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/tournoi_match/AfficheTournois.fxml"));
        try {
            Parent parent = loader.load();
            date_tournoi.getScene().setRoot(parent);
        } catch (IOException e) {
            showError("Echec de navigation", "#F05A5A");
        }
    }

    @FXML
    void confirmerAjout(ActionEvent event) {
        try {
            if(nom_tournoi.getText().isEmpty()){
                showError("Le nom ne peut pas être vide", "#F05A5A");
            } else if (date_tournoi.getValue()==null) {
                showError("Veuillez choisir une date", "#F05A5A");
            } else if (liste_type.getValue()==null) {
                showError("Veuillez choisir une type de tournoi", "#F05A5A");
            } else {
                Tournoi tournoi = new Tournoi(nom_tournoi.getText(), liste_type.getValue(), Date.valueOf(date_tournoi.getValue()), trounoi_description.getText());
                tournoiService.addEntity(tournoi);
                showError("Tournoi ajoutée avec succès", "#66ffcc");
            }
        } catch (Exception e) {
            showError("Veuillez remplir correctement le formulaire", "#F05A5A");
        }
    }

    @FXML
    public void refreshImageTerrain(ActionEvent event){
        try {
            Image image = new Image(getClass().getResource("/images/backgrounds/" + liste_type.getValue() + ".png").toExternalForm());
            terrain_bg.setImage(image);
        }catch (Exception e) {
            Image image = new Image(getClass().getResource("/images/backgrounds/Placeholder.png").toExternalForm());
            terrain_bg.setImage(image);
        }
    }

    public void initialize(){
        liste_type.setItems(FXCollections.observableArrayList("Football", "Basketball", "Volley-ball", "Baseball", "Rugby", "Handball","Tennis", "Badminton", "Tennis de table", "Squash","Golf", "Bowling", "Bocce", "Croquet","Water-polo", "Dodgeball", "Sepak Takraw", "Lacrosse"));
    }

}
