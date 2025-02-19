package tn.hive.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.hive.entities.Match;
import tn.hive.entities.Tournoi;
import tn.hive.services.TournoiService;

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

    TournoiService tournoiService = new TournoiService();

    @FXML
    void goToAjout(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutTournoi.fxml"));
        try {
            Parent parent = loader.load();
            tableview_tournoi.getScene().setRoot(parent);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void modifierTournoi(ActionEvent event) {
        try{
            int selected_tournoi_id = tableview_tournoi.getSelectionModel().getSelectedItem().getId_tournoi();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierTournoi.fxml"));
            try {
                Parent parent = loader.load();
                ModifierTournoiController modifierTournoiController = loader.getController();
                modifierTournoiController.setId_tournoi(selected_tournoi_id);
                tableview_tournoi.getScene().setRoot(parent);
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Slectionner une tournoi");
            alert.setContentText("Vous devez sélectionner une correspondance pour la modifier");
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
            alert.setContentText("Vous devez sélectionner une correspondance pour la supprimer");
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

}
