package tn.hive.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.hive.entities.Match;
import tn.hive.services.MatchService;

import java.io.IOException;
import java.sql.Date;

public class AfficheMatchsController {

    @FXML
    private TableView<Match> tableview_match;

    @FXML
    private TableColumn<Match, Date> tableview_match_date;

    @FXML
    private TableColumn<Match, Integer> tableview_match_equipe1;

    @FXML
    private TableColumn<Match, Integer> tableview_match_equipe2;

    @FXML
    private TableColumn<Match, Integer> tableview_match_id;

    @FXML
    private TableColumn<Match, String> tableview_match_statut;

    @FXML
    private TableColumn<Match, Integer> tableview_match_tournoi;

    @FXML
    private TableColumn<Match, Integer> tableview_match_score1;

    @FXML
    private TableColumn<Match, Integer> tableview_match_score2;

    private MatchService matchService = new MatchService();

    @FXML
    void goToAjout(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutMatch.fxml"));
        try {
            Parent parent = loader.load();
            tableview_match.getScene().setRoot(parent);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void modifierMatch(ActionEvent event) {

        int selected_match_id = tableview_match.getSelectionModel().getSelectedItem().getId_match();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierMatch.fxml"));
        try {
            Parent parent = loader.load();
            ModifierMatchController modifierMatchController = loader.getController();
            modifierMatchController.setId_match(selected_match_id);
            tableview_match.getScene().setRoot(parent);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void supprimerMatch(ActionEvent event) {
        matchService.deleteEntity(tableview_match.getSelectionModel().getSelectedItem().getId_match());
        tableview_match.getItems().clear();
        refreshTableviewMatch();
    }

    public void refreshTableviewMatch(){
        ObservableList<Match> matchObservableList = FXCollections.observableArrayList(
                matchService.getAllData()
        );

        tableview_match_id.setCellValueFactory(new PropertyValueFactory<Match, Integer>("id_match"));
        tableview_match_tournoi.setCellValueFactory(new PropertyValueFactory<Match, Integer>("id_tournoi"));
        tableview_match_equipe1.setCellValueFactory(new PropertyValueFactory<Match, Integer>("id_equipe1"));
        tableview_match_equipe2.setCellValueFactory(new PropertyValueFactory<Match, Integer>("id_equipe2"));
        tableview_match_date.setCellValueFactory(new PropertyValueFactory<Match, Date>("date_match"));
        tableview_match_statut.setCellValueFactory(new PropertyValueFactory<Match, String>("statut_match"));
        tableview_match_score1.setCellValueFactory(new PropertyValueFactory<Match, Integer>("score_equipe1"));
        tableview_match_score2.setCellValueFactory(new PropertyValueFactory<Match, Integer>("score_equipe2"));
        tableview_match.setItems(matchObservableList);
    }


    public void initialize() {
        refreshTableviewMatch();
    }
}
