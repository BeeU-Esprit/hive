package tn.hive.controllers.tournoi_match;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.hive.entities.tournoi_match.Tournoi;
import tn.hive.services.tournoi_match.TournoiService;

import java.io.IOException;
import java.sql.Date;

public class ModifierTournoiController {

    @FXML
    private Label tournoi_id_label;

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

    private int id_tournoi;

    private TournoiService tournoiService = new TournoiService();

    @FXML
    void annulerAjout(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/tournoi_match/AfficheTournois.fxml"));
        try {
            Parent parent = loader.load();
            date_tournoi.getScene().setRoot(parent);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Echec de navigation");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    void confirmerModification(ActionEvent event) {
        try {
            Tournoi tournoi = new Tournoi(nom_tournoi.getText(), liste_type.getValue(), Date.valueOf(date_tournoi.getValue()), trounoi_description.getText());
            tournoiService.updateEntity(id_tournoi, tournoi);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Tournoi modifiée avec succès");
            alert.setContentText(tournoi.toString());
            alert.show();
            annulerAjout(event);
        }catch ( Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Veuillez remplir correctement le formulaire");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void setId_tournoi(int id_tournoi) {
        this.id_tournoi = id_tournoi;
        refreshModifierTournoi();
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

    public void refreshModifierTournoi(){
        Tournoi tournoi = tournoiService.getTournoiById(id_tournoi);
        //titre
        tournoi_id_label.setText(String.valueOf(id_tournoi));

        //nom
        nom_tournoi.setText(tournoi.getNom_tournoi());


        //date
        date_tournoi.setValue(tournoi.getDate_tournoi().toLocalDate());

        //type
        liste_type.setItems(FXCollections.observableArrayList("Football", "Basketball", "Volley-ball", "Baseball", "Rugby", "Handball","Tennis", "Badminton", "Tennis de table", "Squash","Golf", "Bowling", "Bocce", "Croquet","Water-polo", "Dodgeball", "Sepak Takraw", "Lacrosse"));
        liste_type.setValue(tournoi.getType_tournoi());

        //description
        trounoi_description.setText(tournoi.getDescription_tournoi());

    }
}
