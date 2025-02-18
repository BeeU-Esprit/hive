package tn.hive.services;

import tn.hive.entities.Match;
import tn.hive.interfaces.IService;
import tn.hive.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatchService implements IService<Match> {

    @Override
    public void addEntity(Match match) throws SQLException {
        String query = "INSERT INTO `MATCH`(id_tournoi, id_equipe1, id_equipe2, date_match, id_terrain, score_equipe1, score_equipe2, statut_match) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)){
        pst.setInt(1, match.getId_tournoi());
        pst.setInt(2, match.getId_equipe1());
        pst.setInt(3, match.getId_equipe2());
        pst.setDate(4, new java.sql.Date(match.getDate_match().getTime()));
        pst.setInt(5, match.getId_terrain());
        pst.setInt(6, match.getScore_equipe1());
        pst.setInt(7, match.getScore_equipe2());
        pst.setString(8, match.getStatut_match());
        pst.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteEntity(int id_match) {
        String query = "DELETE FROM `MATCH` WHERE id_match = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id_match);
            pst.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEntity(int id, Match match) {
        String query = "UPDATE `MATCH` SET id_tournoi=?, id_equipe1=?, id_equipe2=?, date_match=?, id_terrain=?, score_equipe1=?, score_equipe2=?, statut_match=? WHERE id_match=?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, match.getId_terrain());
            pst.setInt(2, match.getId_equipe1());
            pst.setInt(3, match.getId_equipe2());
            pst.setDate(4, new java.sql.Date(match.getDate_match().getTime()));
            pst.setInt(5, match.getId_terrain());
            pst.setInt(6, match.getScore_equipe1());
            pst.setInt(7, match.getScore_equipe2());
            pst.setString(8, match.getStatut_match());
            pst.setInt(9, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Match> getAllData() {
        List<Match> matchList = new ArrayList<>();
        String query = "SELECT * FROM `MATCH`";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            ResultSet rs = pst.executeQuery(query);
            while (rs.next()){
                Match match = new Match(
                        rs.getInt("id_match"),
                        rs.getInt("id_tournoi"),
                        rs.getInt("id_equipe1"),
                        rs.getInt("id_equipe2"),
                        rs.getDate("date_match"),
                        rs.getInt("id_terrain"),
                        rs.getInt("score_equipe1"),
                        rs.getInt("score_equipe2"),
                        rs.getString("statut_match")
                );
                matchList.add(match);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return matchList;
    }
}
