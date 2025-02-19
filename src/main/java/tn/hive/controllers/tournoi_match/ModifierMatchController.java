package tn.hive.controllers.tournoi_match;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
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
import java.util.ArrayList;
import java.util.List;

public class ModifierMatchController {

    @FXML
    private DatePicker date_match;

    @FXML
    private Label id_match_label;

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

    private int id_match;

    public void setId_match(int id) {
        this.id_match = id;
        refreshModifierMatch();
    }

    @FXML
    void annulerModification(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/tournoi_match/AfficheMatchs.fxml"));
        try {
            Parent parent = loader.load();
            id_match_label.getScene().setRoot(parent);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void confirmerModification(ActionEvent event) {
        try {
            Match match = new Match(liste_tournoi.getValue(), liste_equipe1.getValue(), liste_equipe2.getValue(), Date.valueOf(date_match.getValue()), liste_terrain.getValue(), Integer.parseInt(score1.getText()), Integer.parseInt(score2.getText()), liste_statut.getValue());
            matchService.updateEntity(id_match, match);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Match modifiée avec succès");
            alert.setContentText(match.toString());
            alert.show();
            annulerModification(event);
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Match modifiée avec succès");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }


    public void initialize(){

    }

    public void refreshModifierMatch(){
        Match match = matchService.getMatchById(id_match);
        //titre
        id_match_label.setText(String.valueOf(id_match));

        //tournoi
        List<Integer> tournoiList = new ArrayList<>();

        for(Tournoi tournoi : tournoiService.getAllData()){
            tournoiList.add(tournoi.getId_tournoi());
        }

        liste_tournoi.setItems(FXCollections.observableArrayList(tournoiList));
        liste_tournoi.setValue(match.getId_tournoi());

        //date
        date_match.setValue(match.getDate_match().toLocalDate());

        //terrain
        List<Integer> terrainList = new ArrayList<>();

        for(Terrain terrain : terrainService.getAllData()){
            terrainList.add(terrain.getId_terrain());
        }

        liste_terrain.setItems(FXCollections.observableArrayList(terrainList));
        liste_terrain.setValue(match.getId_terrain());

        //equipes
        List<Integer> equipeList = new ArrayList<>();

        for(Equipe equipe : equipeService.getAllData()){
            equipeList.add(equipe.getId_equipe());
        }

        liste_equipe1.setItems(FXCollections.observableArrayList(equipeList));
        liste_equipe2.setItems(FXCollections.observableArrayList(equipeList));

        liste_equipe1.setValue(match.getId_equipe1());
        liste_equipe2.setValue(match.getId_equipe2());

        //scores
        score1.setText(String.valueOf(match.getScore_equipe1()));
        score2.setText(String.valueOf(match.getScore_equipe2()));

        //statut
        liste_statut.setItems(FXCollections.observableArrayList("terminé","annulé","en cours", "pas commencé"));
        liste_statut.setValue(match.getStatut_match());
    }
}