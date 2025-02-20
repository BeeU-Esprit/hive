package tn.hive.controllers.tournoi_match;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.hive.entities.tournoi_match.Equipe;
import tn.hive.entities.tournoi_match.Match;
import tn.hive.entities.tournoi_match.Terrain;
import tn.hive.entities.tournoi_match.Tournoi;
import tn.hive.services.tournoi_match.EquipeService;
import tn.hive.services.tournoi_match.MatchService;
import tn.hive.services.tournoi_match.TerrainService;
import tn.hive.services.tournoi_match.TournoiService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AjoutMatchController {

    @FXML
    private DatePicker date_match;

    @FXML
    private ComboBox<Integer> liste_equipe1;

    @FXML
    private ComboBox<Integer> liste_equipe2;

    @FXML
    private ComboBox<String> liste_statut;

    @FXML
    private ComboBox<Integer> liste_terrain;

    @FXML
    private ComboBox<Integer> liste_tournoi;

    @FXML
    private TextField score1;

    @FXML
    private TextField score2;

    @FXML
    private ImageView terrain_bg;

    MatchService matchService = new MatchService();
    TournoiService tournoiService = new TournoiService();
    TerrainService terrainService = new TerrainService();
    EquipeService equipeService = new EquipeService();

    @FXML
    void annulerModification(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/tournoi_match/AfficheMatchs.fxml"));
        try {
            Parent parent = loader.load();
            date_match.getScene().setRoot(parent);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Echec de navigation");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    void confirmerAjout(ActionEvent event) {
        try {
            Match match = new Match(liste_tournoi.getValue(), liste_equipe1.getValue(), liste_equipe2.getValue(), Date.valueOf(date_match.getValue()), liste_terrain.getValue(), Integer.parseInt(score1.getText()), Integer.parseInt(score2.getText()), liste_statut.getValue());
            matchService.addEntity(match);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Match ajoutée avec succès");
            alert.setContentText(match.toString());
            alert.show();
            annulerModification(event);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Veuillez remplir correctement le formulaire");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    public void refreshImageTerrain(ActionEvent event){
        try {
            Tournoi tournoi = tournoiService.getTournoiById(liste_tournoi.getValue());
            Image image = new Image(getClass().getResource("/images/backgrounds/" + tournoi.getType_tournoi() + ".png").toExternalForm());
            terrain_bg.setImage(image);
        }catch (Exception e) {
            Image image = new Image(getClass().getResource("/images/backgrounds/Placeholder.png").toExternalForm());
            terrain_bg.setImage(image);
        }
    }

    public void initialize(){
//tournoi
        List<Integer> tournoiList = new ArrayList<>();

        for(Tournoi tournoi : tournoiService.getAllData()){
            tournoiList.add(tournoi.getId_tournoi());
        }

        liste_tournoi.setItems(FXCollections.observableArrayList(tournoiList));

        //terrain
        List<Integer> terrainList = new ArrayList<>();

        for(Terrain terrain : terrainService.getAllData()){
            terrainList.add(terrain.getId_terrain());
        }

        liste_terrain.setItems(FXCollections.observableArrayList(terrainList));

        //equipes
        List<Integer> equipeList = new ArrayList<>();

        for(Equipe equipe : equipeService.getAllData()){
            equipeList.add(equipe.getId_equipe());
        }

        liste_equipe1.setItems(FXCollections.observableArrayList(equipeList));
        liste_equipe2.setItems(FXCollections.observableArrayList(equipeList));

        //score
        score1.setText("0");
        score2.setText("0");

        //statut
        liste_statut.setItems(FXCollections.observableArrayList("terminé","annulé","en cours", "pas commencé"));
    }
}
