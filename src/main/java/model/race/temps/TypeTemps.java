package model.race.temps;

import com.connection.database.BddObject;


public class TypeTemps extends BddObject {

    String nom;

    public TypeTemps(String id) {
        this();
        this.setId(id);
    }

    public TypeTemps() {
        setTable("type_temps");
        setConnection("PostgreSQL");
        setPrefix("TYP");
        setPrimaryKeyName("id_type");
        setFunctionPK("nextval('seq_type_temps')");
        setCountPK(7);
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return this.nom;
    }

}