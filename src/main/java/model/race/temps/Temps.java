package model.race.temps;

import model.race.etape.Etape;
import model.race.course.Course;
import model.race.coureur.Coureur;
import com.connection.database.BddObject;
import connection.annotation.ColumnName;
import java.sql.Date;
import java.sql.Time;


public class Temps extends BddObject {

    @ColumnName(value = "date_temps")
    Date dateTemps;
    @ColumnName(value = "heure_depart")
    Time heureDepart;
    @ColumnName(value = "heure_arrive")
    Time heureArrive;
    Coureur coureur;
    Course course;
    Etape etape;
    TypeTemps typeTemps;

    public Temps(Date dateTemps, Time heureDepart, Time heureArrive, Coureur coureur, Course course, Etape etape, TypeTemps typeTemps) {
        this();
        this.setDateTemps(dateTemps);
        this.setHeureDepart(heureDepart);
        this.setHeureArrive(heureArrive);
        this.setCoureur(coureur);
        this.setCourse(course);
        this.setEtape(etape);
        this.setTypeTemps(typeTemps);
    }

    public Temps() {
        setTable("temps");
        setConnection("PostgreSQL");
        setPrefix("TEM");
        setPrimaryKeyName("id_temps");
        setFunctionPK("nextval('seq_temps')");
        setCountPK(7);
    }

    public void setDateTemps(Date dateTemps){
        this.dateTemps = dateTemps;
    }

    public Date getDateTemps(){
        return this.dateTemps;
    }

    public void setHeureDepart(Time heureDepart){
        this.heureDepart = heureDepart;
    }

    public Time getHeureDepart(){
        return this.heureDepart;
    }

    public void setHeureArrive(Time heureArrive){
        this.heureArrive = heureArrive;
    }

    public Time getHeureArrive(){
        return this.heureArrive;
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


    public void setTypeTemps(TypeTemps typeTemps){
        this.typeTemps = typeTemps;
    }

    public TypeTemps getTypeTemps(){
        return this.typeTemps;
    }

}