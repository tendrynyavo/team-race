package model.race.equipe;

import model.race.course.Course;
import model.race.coureur.RankCoureur;
import model.race.coureur.CoureurComplet;
import model.race.coureur.Coureur;
import model.race.admin.Admin;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.race.etape.Etape;
import model.race.penalite.Penalite;

public class Equipe extends Admin {

    String nom;
    Coureur[] participants;

    public Coureur[] getParticipants() {
        return participants;
    }

    public void setParticipants(Coureur[] participants) {
        this.participants = participants;
    }
    
    public Equipe() {
        setTable("equipe");
        setConnection("PostgreSQL");
        setPrefix("EQU");
        setPrimaryKeyName("id_equipe");
        setFunctionPK("nextval('seq_equipe')");
        setCountPK(7);
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return this.nom;
    }

    public Coureur[] getCoureurs(String categorie, Connection connection) throws Exception {
        Coureur coureur = new Coureur();
        coureur.setEquipe(this);
        return (Coureur[]) coureur.findAll("nom", null);
    }
    
    public Coureur[] getCoureurs(String idCourse, String idEtape, boolean reste, Connection connection) throws Exception {
        Course course = new Course();
        course.setId(idCourse);
        Etape etape = new Etape();
        etape.setId(idEtape);
        return this.getCoureurs(course, etape, reste, connection);
    }
    
    public Coureur[] getCoureurs(Course course, Etape etape, boolean reste, Connection connection) throws Exception {
        CoureurComplet coureur = new CoureurComplet();
        coureur.setEquipe(this);
        coureur.setCourse(course);
        coureur.setEtape(etape);
        coureur.setTable((reste) ? "v_reste_participant_coureur" : "v_coureur_etape_equipe");
        return (Coureur[]) coureur.findAll("nom", null);
    }
    
    public Coureur[] getParticipants(String idCourse, String idEtape, Connection connection) throws Exception {
        Course course = new Course();
        course.setId(idCourse);
        Etape etape = new Etape();
        etape.setId(idEtape);
        return this.getParticipants(course, etape, connection);
    }
    
    public Coureur[] getParticipants(Course course, Etape etape, Connection connection) throws Exception {
        CoureurComplet coureur = new CoureurComplet();
        coureur.setEquipe(this);
        coureur.setCourse(course);
        coureur.setEtape(etape);
        coureur.setTable("v_participant_temps_chrono_penalite_lib");
        return (Coureur[]) coureur.findAll("nom", null);
    }
    
    public boolean contains(Coureur coureur) {
        List<Coureur> coureurs = Arrays.asList(this.getParticipants());
        return coureurs.contains(coureur);
    }
    
    public List<Coureur> contains(Coureur[] coureurs) {
        List<Coureur> set = new ArrayList<>(Arrays.asList(coureurs));
        set.removeAll(Arrays.asList(this.getParticipants()));
        return set;
    }
    
    public Penalite penaliser(String temps, String idCourse, String idEtape, Connection connection) {
        Penalite penalite = new Penalite();
        Course course = new Course();
        course.setId(idCourse);
        penalite.setCourse(course);
        Etape etape = new Etape();
        etape.setId(idEtape);
        penalite.setEtape(etape);
        penalite.setEquipe(this);
        penalite.setValeur(temps);
        penalite.setEtat(10);
        return penalite;
    }
    
    public void checkNombreCoureur(String idCourse, String idEtape, Connection connection) throws Exception {
        Course course = new Course();
        course.setId(idCourse);
        Etape[] etape = course.getEtape(idEtape, this.getId(), null, connection);
        if (etape[0].getNombre() == etape[0].getNbrCoureurEquipe()) {
            throw new IllegalArgumentException("Etape déja complet");
        }
    }
    
    public RankCoureur[] getRankCoureurs(String idCourse, Connection connection) throws Exception {
        Course course = new Course();
        course.setId(idCourse);
        return course.getRankCoureur(this.getId(), connection);
    }
    
    public Etape[] getEtape(String idCourse, String order, Connection connection) throws Exception {
        Course course = new Course();
        course.setId(idCourse);
        return course.getEtape("", this.getId(), order, connection);
    }
    
}