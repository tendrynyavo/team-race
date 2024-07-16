package model.race.course;

import model.race.coureur.RankCoureur;
import com.connection.database.BddObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import model.race.equipe.Equipe;
import model.race.etape.Etape;
import model.race.equipe.RankEquipe;
import model.race.equipe.ResultatEquipe;

public class Course extends BddObject {

    String nom;

    public Course() {
        setTable("course");
        setConnection("PostgreSQL");
        setPrefix("COU");
        setPrimaryKeyName("id_course");
        setFunctionPK("nextval('seq_course')");
        setCountPK(7);
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return this.nom;
    }
    
    public Etape[] getEtape(String order, Connection connection) throws Exception {
        Etape etape = new Etape();
        etape.setTable(String.format("v_etape_course WHERE id_course = '%s'", this.getId()));
        return (Etape[]) etape.findAll(null, order, null);
    }
    
    public Etape[] getEtape(String idEtape, String idEquipe, String order, Connection connection) throws Exception {
        Etape etape = new Etape();
        etape.setTable(String.format("v_etape_nombre_coureur_equipe_initialisation_complet_lib WHERE id_course = '%s' and id_equipe = '%s' and id_etape like '%s'", this.getId(), idEquipe, "%" + idEtape + "%"));
        return (Etape[]) etape.findAll(null, order, null);
    }
    
    public RankCoureur[] getRankCoureur(Connection connection) throws Exception {
        return this.getRankCoureur(null, connection);
    }
    
    public RankCoureur[] getRankCoureur(String idEquipe, Connection connection) throws Exception {
        RankCoureur rankCoureur = new RankCoureur();
        rankCoureur.setCourse(this);
        if (idEquipe != null && !idEquipe.isEmpty()) {
            Equipe equipe = new Equipe();
            equipe.setId(idEquipe);
            rankCoureur.setEquipe(equipe);
        }
        return (RankCoureur[]) rankCoureur.findAll(connection, "(rang, nom)", null);
    }
    
    public RankEquipe[] getRankEquipe(Connection connection) throws Exception {
        RankEquipe rankEtape = new RankEquipe();
        rankEtape.setCourse(this);
        return (RankEquipe[]) rankEtape.findAll(connection, "(rang, nom)", null);
    }
    
    public ResultatEquipe getRankEquipeCategorie(String categorie, Connection connection) throws Exception {
        if (categorie.isEmpty()) {
            RankEquipe[] result = this.getRankEquipe(connection);
            ResultatEquipe resultatEquipe = new ResultatEquipe();
            resultatEquipe.setEquipes(result);
            return resultatEquipe;
        }
        RankEquipe rankEtape = new RankEquipe();
        String table = """
            select * from statistique_categorie('%s', '%s')
        """;
        RankEquipe[] result = (RankEquipe[]) rankEtape.getData(String.format(table, categorie, this.getId()), connection);
        ResultatEquipe resultatEquipe = new ResultatEquipe();
        resultatEquipe.setEquipes(result);
        return resultatEquipe;
    }
    
    public int reset() throws IOException {
        URL url = new URL("http://127.0.0.1:5000/reset_database");
        HttpURLConnection con = null;
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/html");
        con.setDoOutput(true);
        con.setDoInput(true);
        return con.getResponseCode();
    }
    
}