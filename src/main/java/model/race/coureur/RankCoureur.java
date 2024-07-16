package model.race.coureur;

import model.race.coureur.CoureurComplet;

public class RankCoureur extends CoureurComplet {
    
    double total;
    int rang;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }
    
    public RankCoureur() {
        super();
        setTable("v_classement_lib");
    }
    
}
