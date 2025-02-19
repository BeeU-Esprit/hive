package tn.hive.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.hive.entities.Equipe;
import tn.hive.entities.Match;
import tn.hive.entities.Terrain;
import tn.hive.entities.Tournoi;
import tn.hive.services.EquipeService;
import tn.hive.services.MatchService;
import tn.hive.services.TerrainService;
import tn.hive.services.TournoiService;

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

    MatchService matchService = new MatchService();
    TournoiService tournoiService = new TournoiService();
    TerrainService terrainService = new TerrainService();
    EquipeService equipeService = new EquipeService();

    @FXML
    void annulerModification(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheMatchs.fxml"));
        try {
            Parent parent = loader.load();
            date_match.getScene().setRoot(parent);
        } catch (IOException e) {
            System.out.println(e.getMessage());
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

        } catch (SQLException e) {
            System.out.println(e.getMessage());
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

        //statut
        liste_statut.setItems(FXCollections.observableArrayList("terminé","annulé","en cours", "pas commencé"));
    }
}
