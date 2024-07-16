package model.race.penalite;

import model.race.etape.Etape;
import model.race.equipe.Equipe;
import model.race.course.Course;
import com.connection.database.BddObject;
import java.sql.Connection;
import java.sql.Time;
import java.time.LocalTime;

public class Penalite extends BddObject {

    Time valeur;
    Course course;
    Equipe equipe;
    Etape etape;
    Integer etat;

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }
    
    public Penalite() {
        setTable("penalite");
        setConnection("PostgreSQL");
        setPrefix("PEN");
        setPrimaryKeyName("id_penalite");
        setFunctionPK("nextval('seq_penalite')");
        setCountPK(7);
    }
    
    public void setValeur(String valeur) {
        if (valeur.isEmpty()) {
            throw new IllegalArgumentException("Valeur du pénalite est vide");
        }
        this.setValeur(Time.valueOf(LocalTime.parse(valeur)));
    }

    public void setValeur(Time valeur) {
        this.valeur = valeur;
    }

    public Time getValeur(){
        return this.valeur;
    }

    public void setCourse(Course course){
        this.course = course;
    }

    public Course getCourse(){
        return this.course;
    }

    public void setEquipe(Equipe equipe){
        this.equipe = equipe;
    }

    public Equipe getEquipe(){
        return this.equipe;
    }

    public void setEtape(Etape etape){
        this.etape = etape;
    }

    public Etape getEtape(){
        return this.etape;
    }
    
    public void supprimer(Connection connection) throws Exception {
        boolean connect = false;
        try {
            if (connection == null) {
                connection = this.getConnection();
                connect = true;
            }
            this.setEtat(-10);
            this.update(connection);
            if (connect) {
                connection.commit();
            }
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connect && connection != null) {
                connection.close();
            }
        }
    }
    
    public void supprimer(String[] penalites, Connection connection) throws Exception {
        if (penalites == null) {
            return;
        }
        boolean connect = false;
        try {
            if (connection == null) {
                connection = this.getConnection();
                connect = true;
            }
            for (String penalite : penalites) {
                Penalite p = new Penalite();
                p.setId(penalite);
                p.supprimer(connection);
            }
            if (connect) {
                connection.commit();
            }
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connect && connection != null) {
                connection.close();
            }
        }
    }

}