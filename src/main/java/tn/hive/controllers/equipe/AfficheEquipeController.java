package tn.hive.controllers.equipe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tn.hive.entities.equipe.Equipe;
import tn.hive.services.equipe.EquipeService;
import tn.hive.services.navigation.NavigationService;


public class AfficheEquipeController {

    @FXML
    private TableColumn<Equipe, Integer> tableview_equipe_id;

    @FXML
    private TableColumn<Equipe, String> tableview_equipe_nom;

    @FXML
    private TableView<Equipe> tableview_equipes;

    @FXML
    private Label titre_liste_equipes;

    EquipeService equipeService = new EquipeService();
    NavigationService navigationService = new NavigationService();

    @FXML
    void goToAfficheEquipes(MouseEvent event) {
        refreshTableviewEquipe();
    }

    @FXML
    void goToAfficheMatchs(MouseEvent event) {
        navigationService.goToPage("/pages/tournoi_match/AfficheMatchs.fxml", titre_liste_equipes);
    }

    @FXML
    void goToAfficheTournois(MouseEvent event) {
        navigationService.goToPage("/pages/tournoi_match/AfficheTournois.fxml", titre_liste_equipes);
    }

    @FXML
    void goToAjout(ActionEvent event) {
        navigationService.goToPage("/pages/equipe/AjoutEquipe.fxml", titre_liste_equipes);
    }

    @FXML
    void modifierEquipe(ActionEvent event) {

    }

    @FXML
    void supprimerEquipe(ActionEvent event) {

    }

    public void refreshTableviewEquipe(){
        ObservableList<Equipe> equipeObservableList = FXCollections.observableArrayList(
                equipeService.getAllData()
        );

        tableview_equipe_id.setCellValueFactory(new PropertyValueFactory<Equipe, Integer>("id_equipe"));
        tableview_equipe_nom.setCellValueFactory(new PropertyValueFactory<Equipe, String>("nom_equipe"));
        tableview_equipes.setItems(equipeObservableList);
    }

    public void initialize(){
        refreshTableviewEquipe();
    }

}
