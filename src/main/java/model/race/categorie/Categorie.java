package model.race.categorie;

import com.connection.database.BddObject;

public class Categorie extends BddObject {

    String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public Categorie() {
        setTable("categorie");
        setConnection("PostgreSQL");
        setPrefix("CAT");
        setPrimaryKeyName("id_categorie");
        setFunctionPK("nextval('seq_categorie')");
        setCountPK(7);
    }
    
}
