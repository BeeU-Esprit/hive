package tn.hive.entities.tournoi_match;

public class Equipe {
    int id_equipe;
    String nom_equipe;

    public Equipe() {
    }

    public Equipe(String nom_equipe) {
        this.nom_equipe = nom_equipe;
    }

    public Equipe(int id_equipe, String nom_equipe) {
        this.id_equipe = id_equipe;
        this.nom_equipe = nom_equipe;
    }

    public int getId_equipe() {
        return id_equipe;
    }

    public String getNom_equipe() {
        return nom_equipe;
    }

    @Override
    public String toString() {
        return "Equipe{" +
                "id_equipe=" + id_equipe +
                ", nom_equipe='" + nom_equipe + '\'' +
                '}';
    }
}
