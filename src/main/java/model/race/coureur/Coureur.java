package model.race.coureur;

import model.race.categorie.Categorie;
import com.connection.database.BddObject;
import com.connection.database.Column;
import connection.annotation.ColumnName;
import controller.url.HelperUrl;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import model.race.course.Course;
import model.race.equipe.Equipe;
import model.race.etape.Etape;
import model.race.temps.Temps;
import model.race.temps.TypeTemps;
import org.postgresql.util.PSQLException;

public class Coureur extends BddObject {

    String nom;
    @ColumnName(value="numero_dossard")
    String numeroDossard;
    String genre;
    @ColumnName(value="date_naissance")
    Date dateNaissance;
    Equipe equipe;
    
    public Coureur() {
        setTable("coureur");
        setConnection("PostgreSQL");
        setPrefix("COUS");
        setPrimaryKeyName("id_coureur");
        setFunctionPK("nextval('seq_coureur')");
        setCountPK(7);
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return this.nom;
    }

    public void setNumeroDossard(String numeroDossard){
        this.numeroDossard = numeroDossard;
    }

    public String getNumeroDossard(){
        return this.numeroDossard;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public String getGenre(){
        return this.genre;
    }

    public void setDateNaissance(Date dateNaissance){
        this.dateNaissance = dateNaissance;
    }

    public Date getDateNaissance(){
        return this.dateNaissance;
    }


    public void setEquipe(Equipe equipe){
        this.equipe = equipe;
    }

    public Equipe getEquipe(){
        return this.equipe;
    }
    
    public int getAge() {
        return calculateAge(this.getDateNaissance().toLocalDate(), new Date(System.currentTimeMillis()).toLocalDate());
    }
    
    public Categorie getAgeCategorie() {
        Categorie categorie = new Categorie();
        String id = (this.getAge() < 18) ? "CAT0003" : "CAT0004";
        categorie.setId(id);
        return categorie;
    }
    
    public Categorie getGenreCategorie() {
        Categorie categorie = new Categorie();
        String id = (this.getGenre().equals("F")) ? "CAT0002" : "CAT0001";
        categorie.setId(id);
        return categorie;
    }
    
    public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }
    
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() == this.getClass()) {
            Coureur other = (Coureur) o;
            return other.getId().equals(this.getId());
        } else {
            return false;
        }
    }
    
    public Coureur[] getCoureurs(String idCourse, String idEtape, Connection connection) throws Exception {
        Course course = new Course();
        course.setId(idCourse);
        Etape etape = new Etape();
        etape.setId(idEtape);
        return this.getCoureurs(course, etape, connection);
    }
    
    public Coureur[] getCoureurs(Course course, Etape etape, Connection connection) throws Exception {
        CoureurComplet coureur = new CoureurComplet();
        coureur.setCourse(course);
        coureur.setEtape(etape);
        coureur.setTable("v_participant_temps_chrono_penalite_rank_lib");
        return (CoureurComplet[]) coureur.findAll("rang", null);
    }
    
    public void affecterTemps(String time, Etape etape, Course course, Connection connection) throws Exception {
        String[] values = time.split("T");
        Temps[] temps = this.affecterTemps(etape.getHeureDepart(), Time.valueOf(LocalTime.parse(values[1])), etape, course, Date.valueOf(values[0]), connection);
        for (Temps temp : temps) {
            temp.insert(connection);
        }
    }
    
    public Temps[] affecterTemps(Time depart, Time arrive, Etape etape, Course course, Date date, Connection connection) throws Exception {
        Timestamp etapeTime = HelperUrl.toTimestamp(depart, etape.getDateDepart());
        Timestamp arriveTime = HelperUrl.toTimestamp(arrive, date);
        if (etapeTime.after(arriveTime)) {
            throw new IllegalArgumentException("Temps invalide");
        }
        return new Temps[] {new Temps(date, depart, arrive, this, course, etape, new TypeTemps("TYP0001"))};
    }
    
    public Categorie[] generer() {
        return new Categorie[] {this.getAgeCategorie(), this.getGenreCategorie()};
    }
    
    public void generer(Connection connection) throws Exception {
        boolean connect = false;
        try {
            if (connection == null) {
                connection = this.getConnection();
                connect = true;
            }
            Coureur[] coureurs = (Coureur[]) new Coureur().findAll(connection, null, null);
            for (int i = 0; i < coureurs.length; i++) {
                for (Categorie categorie : coureurs[i].generer()) {
                    categorie.setTable("coureur_categorie");
                    categorie.setSerial(false);
                    categorie.insert(connection, new Column("id_coureur", coureurs[i].getId()));
                }
            }
            if (connect) {
                connection.commit();
            }
        } catch (PSQLException e) {
            if (e.getMessage().equals("ERREUR: la valeur d'une clé dupliquée rompt la contrainte unique")) {
                throw new IllegalArgumentException("Il y a des catégorie qui existe déjà dans le coureur");
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