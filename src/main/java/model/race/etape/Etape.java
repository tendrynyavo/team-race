package model.race.etape;

import model.race.coureur.Participant;
import model.race.equipe.Equipe;
import model.race.course.Course;
import model.race.coureur.Coureur;
import com.connection.database.BddObject;
import connection.annotation.ColumnName;
import controller.url.HelperUrl;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class Etape extends BddObject {

    String nom;
    @ColumnName(value = "longeur_km")
    Double longeurKm;
    @ColumnName(value = "nbr_coureur_equipe")
    Integer nbrCoureurEquipe;
    Integer rang;
    @ColumnName(value = "heure_depart")
    Time heureDepart;
    @ColumnName(value = "date_depart")
    Date dateDepart;
    int nombre;
    Course course;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public Time getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(Time heureDepart) {
        this.heureDepart = heureDepart;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Etape() {
        setTable("etape");
        setConnection("PostgreSQL");
        setPrefix("ETA");
        setPrimaryKeyName("id_etape");
        setFunctionPK("nextval('seq_etape')");
        setCountPK(7);
    }

    public Integer getRang() {
        return rang;
    }

    public void setRang(Integer rang) {
        this.rang = rang;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return this.nom;
    }


    public void setLongeurKm(Double longeurKm){
        this.longeurKm = longeurKm;
    }

    public Double getLongeurKm(){
        return this.longeurKm;
    }
    
    public String getLongeurKmString() {
        return HelperUrl.format(this.getLongeurKm());
    }

    public void setNbrCoureurEquipe(Integer nbrCoureurEquipe){
        this.nbrCoureurEquipe = nbrCoureurEquipe;
    }

    public Integer getNbrCoureurEquipe() {
        return this.nbrCoureurEquipe;
    }
    
    public void affecterCoureurs(Course course, Coureur[] coureurs, Connection connection) throws Exception {
        if (coureurs.length == 0) {
            return;
        }
        boolean connect = false;
        try {
            if (connection == null) {
                connection = this.getConnection();
                connect = true;
            }
            Equipe equipe = coureurs[0].getEquipe();
            this.setTable("v_etape_course");
            this.setCourse(course);
            Etape etape = (Etape) this.getById(connection);
            Coureur[] participants = equipe.getCoureurs(course, this, false, connection);
            equipe.setParticipants(participants);
            List<Coureur> restes = equipe.contains(coureurs);
            if (etape.getNbrCoureurEquipe() < restes.size() + participants.length) {
                throw new IllegalArgumentException(String.format("Nombre doit être à %s coureur(s)", etape.getNbrCoureurEquipe()));
            }
            for (Coureur coureur : restes) {
                Participant participant = new Participant();
                participant.setSerial(false);
                participant.setCoureur(coureur);
                participant.setCourse(course);
                participant.setEtape(this);
                participant.insert(connection);
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
    
    public void affecterCoureurs(String idEquipe, String idCourse, String[] coureurs) throws Exception {
        Coureur[] participants = new Coureur[coureurs.length];
        for (int i = 0; i < coureurs.length; i++) {
            participants[i] = new Coureur();
            participants[i].setId(coureurs[i]);
            Equipe equipe = new Equipe();
            equipe.setId(idEquipe);
            participants[i].setEquipe(equipe);
        }
        Course course = new Course();
        course.setId(idCourse);
        this.affecterCoureurs(course, participants, null);
    }
    
    public void affecterTemps(String temps, String idCourse, Connection connection) throws Exception {
        boolean connect = false;
        String[] coureursTemps = temps.split(";");
        Coureur[] coureurs = new Coureur[coureursTemps.length];
        try {
            if (connection == null) {
                connection = this.getConnection();
                connect = true;
            }
            Course course = new Course();
            course.setId(idCourse);
            this.setTable("v_etape_course");
            this.setCourse(course);
            Etape etape = (Etape) this.getById(connection);
            for (int i = 0; i < coureurs.length; i++) {
                String[] values = coureursTemps[i].split(",");
                if (values.length > 1) {
                    coureurs[i] = new Coureur();
                    coureurs[i].setId(values[0]);
                    coureurs[i].affecterTemps(values[1], etape, course, connection);
                }
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