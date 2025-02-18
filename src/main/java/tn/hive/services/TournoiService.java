package tn.hive.services;

import tn.hive.entities.Tournoi;
import tn.hive.interfaces.IService;
import tn.hive.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TournoiService implements IService<Tournoi> {
    @Override
    public void addEntity(Tournoi tournoi) throws SQLException {
        String query = "INSERT INTO TOURNOI(nom_tournoi, type_tournoi, date_tournoi, description_tournoi) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
            pst.setString(1,tournoi.getNom_tournoi());
            pst.setString(2,tournoi.getType_tournoi());
            pst.setDate(3, new java.sql.Date(tournoi.getDate_tournoi().getTime()));
            pst.setString(4, tournoi.getDescription_tournoi());
            pst.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteEntity(int id_tournoi) {
        String query = "DELETE FROM TOURNOI WHERE id_tournoi = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id_tournoi);
            pst.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEntity(int id, Tournoi tournoi) {

    }

    @Override
    public List<Tournoi> getAllData() {
        List<Tournoi> tournoiList = new ArrayList<>();
        String query = "SELECT * FROM TOURNOI";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            ResultSet rs = pst.executeQuery(query);
            while (rs.next()){
                Tournoi tournoi = new Tournoi(
                        rs.getInt("id_tournoi"),
                        rs.getString("nom_tournoi"),
                        rs.getString("type_tournoi"),
                        rs.getDate("date_tournoi"),
                        rs.getString("description_tournoi")
                );
                tournoiList.add(tournoi);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tournoiList;
    }
}
