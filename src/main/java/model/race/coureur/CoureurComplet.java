package model.race.coureur;

import model.race.coureur.Coureur;
import connection.annotation.ColumnName;
import java.sql.Time;
import java.sql.Timestamp;
import model.race.course.Course;
import model.race.etape.Etape;

public class CoureurComplet extends Coureur {

    int rang;
    Course course;
    Etape etape;
    Timestamp arrive;
    Time chrono;
    Time penalite;
    @ColumnName("temps_final")
    Time tempsFinale;

    public Time getTempsFinale() {
        return tempsFinale;
    }
    
    public String getTempsFinaleString() {
        return (this.getTempsFinale() == null) ? "-" : this.getTempsFinale().toString();
    }

    public void setTempsFinale(Time tempsFinale) {
        this.tempsFinale = tempsFinale;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }
    
    public Time getChrono() {
        return chrono;
    }
    
    public String getChronoString() {
        return (this.getChrono() == null) ? "-" : this.getChrono().toString();
    }

    public void setChrono(Time chrono) {
        this.chrono = chrono;
    }

    public Time getPenalite() {
        return penalite;
    }
    
    public String getPenaliteString() {
        return (this.getPenalite() == null) ? "-" : this.getPenalite().toString();
    }

    public void setPenalite(Time penalite) {
        this.penalite = penalite;
    }

    public Timestamp getArrive() {
        return arrive;
    }
    
    public String getArriveString() {
        return (this.getArrive() == null) ? "-" : this.getArrive().toString();
    }

    public void setArrive(Timestamp arrive) {
        this.arrive = arrive;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Etape getEtape() {
        return etape;
    }

    public void setEtape(Etape etape) {
        this.etape = etape;
    }
    
}