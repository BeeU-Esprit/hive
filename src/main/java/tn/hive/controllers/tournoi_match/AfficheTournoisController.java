package tn.hive.controllers.tournoi_match;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tn.hive.entities.tournoi_match.Tournoi;
import tn.hive.services.navigation.NavigationService;
import tn.hive.services.tournoi_match.TournoiService;

import java.io.IOException;
import java.sql.Date;

public class AfficheTournoisController {

    @FXML
    private TableView<Tournoi> tableview_tournoi;

    @FXML
    private TableColumn<Tournoi, Date> tableview_tournoi_date;

    @FXML
    private TableColumn<Tournoi, String> tableview_tournoi_description;

    @FXML
    private TableColumn<Tournoi, String> tableview_tournoi_type;

    @FXML
    private TableColumn<Tournoi, Integer> tableview_tournoi_id;

    @FXML
    private TableColumn<Tournoi, String> tableview_tournoi_nom;

    @FXML
    private Label titre_liste_tournois;

    TournoiService tournoiService = new TournoiService();
    NavigationService navigationService = new NavigationService();

    @FXML
    void goToAjout(ActionEvent event) {
        navigationService.goToPage("/pages/tournoi_match/AjoutTournoi.fxml", titre_liste_tournois);
    }

    @FXML
    void modifierTournoi(ActionEvent event) {
        try{
            int selected_tournoi_id = tableview_tournoi.getSelectionModel().getSelectedItem().getId_tournoi();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/tournoi_match/ModifierTournoi.fxml"));
            try {
                Parent parent = loader.load();
                ModifierTournoiController modifierTournoiController = loader.getController();
                modifierTournoiController.setId_tournoi(selected_tournoi_id);
                tableview_tournoi.getScene().setRoot(parent);
            } catch (IOException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Echec de navigation");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Slectionner une tournoi");
            alert.setContentText("Vous devez sélectionner une tournoi pour la modifier");
            alert.show();
        }
    }

    @FXML
    void supprimerTournoi(ActionEvent event) {
        try{
            tournoiService.deleteEntity(tableview_tournoi.getSelectionModel().getSelectedItem().getId_tournoi());
            tableview_tournoi.getItems().clear();
            refreshTableviewTournoi();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Slectionner une tournoi");
            alert.setContentText("Vous devez sélectionner une tournoi pour la supprimer");
            alert.show();
        }
    }

    public void refreshTableviewTournoi(){
        ObservableList<Tournoi> tournoiObservableList = FXCollections.observableArrayList(
                tournoiService.getAllData()
        );

        tableview_tournoi_id.setCellValueFactory(new PropertyValueFactory<Tournoi, Integer>("id_tournoi"));
        tableview_tournoi_nom.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("nom_tournoi"));
        tableview_tournoi_type.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("type_tournoi"));
        tableview_tournoi_date.setCellValueFactory(new PropertyValueFactory<Tournoi, Date>("date_tournoi"));
        tableview_tournoi_description.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("description_tournoi"));
        tableview_tournoi.setItems(tournoiObservableList);
    }

    public void initialize() {
        refreshTableviewTournoi();
    }

    @FXML
    void goToAfficheMatchs(MouseEvent event) {
        navigationService.goToPage("/pages/tournoi_match/AfficheMatchs.fxml", titre_liste_tournois);
    }

    @FXML
    void goToAfficheTournois(MouseEvent event) {
        refreshTableviewTournoi();
    }

    @FXML
    void goToAfficheEquipes(MouseEvent event) {
        navigationService.goToPage("/pages/equipe/AfficheEquipes.fxml", titre_liste_tournois);
    }
}
