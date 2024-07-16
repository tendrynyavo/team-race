package model.race.coureur;

import model.race.course.Course;
import model.race.coureur.Coureur;
import com.connection.database.BddObject;
import model.race.etape.Etape;

public class Participant extends BddObject {
    
    Coureur coureur;
    Course course;
    Etape etape;

    public Participant() {
        setTable("participant");
        setConnection("PostgreSQL");
        setPrefix("PAR");
        setPrimaryKeyName("");
        setFunctionPK("nextval('seq_participant')");
        setCountPK(7);
    }

    public void setCoureur(Coureur coureur){
        this.coureur = coureur;
    }

    public Coureur getCoureur(){
        return this.coureur;
    }

    public void setCourse(Course course){
        this.course = course;
    }

    public Course getCourse(){
        return this.course;
    }

    public void setEtape(Etape etape){
        this.etape = etape;
    }

    public Etape getEtape(){
        return this.etape;
    }
    
}