package model.csv;

import com.opencsv.bean.CsvBindByName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Locale;

public class EtapeCsv implements CsvObject {
    
    @CsvBindByName(column = "etape")
    String etape;
    @CsvBindByName(column = "longueur")
    String longueur;
    @CsvBindByName(column = "nb coureur")
    String nbCoureur;
    @CsvBindByName(column = "rang")
    String rang;
    @CsvBindByName(column = "date départ")
    String dateDepart;
    @CsvBindByName(column = "heure départ")
    String heureDepart;

    public String getEtape() {
        return etape.trim();
    }

    public void setEtape(String etape) {
        this.etape = etape;
    }

    public String getLongueur() {
        return longueur.replace(",", ".").trim();
    }

    public void setLongueur(String longueur) {
        this.longueur = longueur;
    }

    public String getNbCoureur() {
        return nbCoureur.trim().trim();
    }

    public void setNbCoureur(String nbCoureur) {
        this.nbCoureur = nbCoureur;
    }

    public String getRang() {
        return rang.trim().trim();
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public String getDateDepart() {
        return dateDepart.trim();
    }

    public void setDateDepart(String dateDepart) {
        this.dateDepart = dateDepart;
    }

    public String getHeureDepart() {
        return heureDepart.trim();
    }

    public void setHeureDepart(String heureDepart) {
        this.heureDepart = heureDepart;
    }
    
    public java.sql.Date getDepartToDate() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        java.util.Date date = formatter.parse(this.getDateDepart());
        return new java.sql.Date(date.getTime());
    }
    
    @Override
    public String getTemporaryTable() {
        return """
            create temporary table import_etape(
                etape VARCHAR(50),
                longueur DOUBLE precision,
                nb_coureur INTEGER,
                rang INTEGER,
                date_depart DATE,
                heure_depart TIME
            )
        """;
    }
    
    @Override
    public String[] getQuerys() {
        return new String[] {
            """
                insert into etape
                select 
                    'ETA' || LPAD(nextval('seq_etape')::text, 4,'0'),
                    etape,
                    longueur,
                    nb_coureur
                from (
                    select 
                        etape,
                        longueur,
                        nb_coureur
                    from import_etape i
                            left join etape e on e.nom = i.etape and e.longeur_km = i.longueur and e.nbr_coureur_equipe = i.nb_coureur
                    where e.id_etape is null
                    group by etape, longueur, nb_coureur
                ) c
            """,
            """
                insert into tournoi
                select 
                    'COURS0001',
                    e.id_etape,
                    e.longeur_km,
                    i.heure_depart,
                    i.date_depart,
                    i.rang,
                    e.nbr_coureur_equipe
                from etape e
                	left join tournoi t on t.id_course = 'COURS0001' and t.id_etape = e.id_etape
                	join import_etape i on e.nom = i.etape and e.longeur_km = i.longueur and e.nbr_coureur_equipe = i.nb_coureur
                where t.id_course is null
            """
        };
    }
    
    @Override
    public void insert(Connection connection) throws Exception {
        String sql = "insert into import_etape values (?, ?, ?, ?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, this.getEtape());
            statement.setDouble(2, Double.parseDouble(this.getLongueur()));
            statement.setInt(3, Integer.parseInt(this.getNbCoureur()));
            statement.setInt(4, Integer.parseInt(this.getRang()));
            statement.setDate(5, this.getDepartToDate());
            statement.setTime(6, Time.valueOf(LocalTime.parse(this.getHeureDepart())));
            statement.execute();
        }
    }
    
}