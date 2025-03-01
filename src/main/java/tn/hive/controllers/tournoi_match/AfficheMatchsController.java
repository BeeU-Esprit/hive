package tn.hive.controllers.tournoi_match;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import tn.hive.entities.tournoi_match.Match;
import tn.hive.services.navigation.NavigationService;
import tn.hive.services.tournoi_match.MatchService;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

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

    @FXML
    private Label titre_liste_matchs;

    @FXML
    private VBox error;

    @FXML
    private Label message;

    private final MatchService matchService = new MatchService();
    private final NavigationService navigationService = new NavigationService();

    @FXML
    void goToAjout(ActionEvent event) {
        navigationService.goToPage("/pages/tournoi_match/AjoutMatch.fxml", titre_liste_matchs);
    }

    @FXML
    void modifierMatch(ActionEvent event) {

        try{
            int selected_match_id = tableview_match.getSelectionModel().getSelectedItem().getId_match();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/tournoi_match/ModifierMatch.fxml"));
            try {
                Parent parent = loader.load();
                ModifierMatchController modifierMatchController = loader.getController();
                modifierMatchController.setId_match(selected_match_id);
                tableview_match.getScene().setRoot(parent);
            } catch (IOException e){
                showError("Echec de navigation", "#F05A5A");

            }
        } catch(Exception e) {
            showError("Slectionner un match", "#F05A5A");
        }

    }

    public void showError(String message, String color){
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        this.message.setText(message);
        error.setStyle("-fx-background-color: " + color);
        error.setVisible(true);
        pause.setOnFinished(event -> {
            error.setVisible(false);
        });

        pause.play();
    }

    @FXML
    void supprimerMatch(ActionEvent event) {
        try {
            if(tableview_match.getSelectionModel().getSelectedItem() != null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Confirmation");
                alert.setContentText("Es-tu sûr de vouloir supprimer cette match?");
                Optional<ButtonType> result = alert.showAndWait();
                ButtonType button = result.orElse(ButtonType.CANCEL);

                if (button == ButtonType.OK) {
                    matchService.deleteEntity(tableview_match.getSelectionModel().getSelectedItem().getId_match());
                    tableview_match.getItems().clear();
                    refreshTableviewMatch();
                } else {
                    System.out.println("canceled");
                }
            }
            else {
                showError("Vous devez sélectionner un match pour la supprimer", "#F05A5A");
            }
        }catch (Exception e) {
            showError(e.getMessage(), "#F05A5A");
        }
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

    @FXML
    void goToAfficheMatchs(MouseEvent event) {
        refreshTableviewMatch();
    }

    @FXML
    void goToAfficheTournois(MouseEvent event) {
        navigationService.goToPage("/pages/tournoi_match/AfficheTournois.fxml", titre_liste_matchs);
    }

    @FXML
    void goToAfficheEquipes(MouseEvent event) {
        navigationService.goToPage("/pages/equipe/AfficheEquipes.fxml", titre_liste_matchs);
    }
}
