package tn.hive.services.tournoi_match;

import tn.hive.entities.tournoi_match.Equipe;
import tn.hive.interfaces.IService;
import tn.hive.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipeService implements IService<Equipe> {
    @Override
    public void addEntity(Equipe equipe) throws SQLException {
        String query = "INSERT INTO EQUIPE(nom_equipe) VALUES (?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
            pst.setString(1,equipe.getNom_equipe());
            pst.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteEntity(int id_equipe) {
        String query = "DELETE FROM EQUIPE WHERE id_equipe = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id_equipe);
            pst.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEntity(int id, Equipe equipe) {
        String query = "UPDATE EQUIPE SET nom_equipe=? WHERE id_equipe=?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, equipe.getNom_equipe());
            pst.setInt(2, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Equipe> getAllData() {
        List<Equipe> equipeList = new ArrayList<>();
        String query = "SELECT * FROM EQUIPE";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            ResultSet rs = pst.executeQuery(query);
            while (rs.next()){
                Equipe equipe = new Equipe(
                        rs.getInt("id_equipe"),
                        rs.getString("nom_equipe")
                );
                equipeList.add(equipe);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return equipeList;
    }
}
