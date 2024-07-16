package model.csv;

import com.opencsv.bean.CsvBindByName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ResultatCsv implements CsvObject {
    
    @CsvBindByName(column = "etape_rang")
    String etapeRang;
    @CsvBindByName(column = "numero dossard")
    String numeroDossard;
    @CsvBindByName(column = "nom")
    String nom;
    @CsvBindByName(column = "genre")
    String genre;
    @CsvBindByName(column = "date naissance")
    String dateNaissance;
    @CsvBindByName(column = "equipe")
    String equipe;
    @CsvBindByName(column = "arrivée")
    String arrivee;

    public String getEtapeRang() {
        return etapeRang;
    }

    public void setEtapeRang(String etapeRang) {
        this.etapeRang = etapeRang;
    }

    public String getNumeroDossard() {
        return numeroDossard;
    }

    public void setNumeroDossard(String numeroDossard) {
        this.numeroDossard = numeroDossard;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public String getArrivee() {
        return arrivee;
    }

    public void setArrivee(String arrivee) {
        this.arrivee = arrivee;
    }
    
    @Override
    public String getTemporaryTable() {
        return """
            create temporary table import_resultat(
                etape_rang INTEGER,
                numero_dossard VARCHAR(50),
                nom VARCHAR(50),
                genre VARCHAR(50),
                date_naissance DATE,
                equipe VARCHAR(50),
                arrivee TIMESTAMP
            )
        """;
    }
    
    @Override
    public String[] getQuerys() {
        return new String[] {
            """
                insert into equipe
                select 
                    'EQU' || LPAD(nextval('seq_equipe')::text, 4,'0'),
                    equipe,
                    equipe,
                    equipe
                from (
                    select 
                        equipe
                    from import_resultat i
                            left join equipe e on e.nom = i.equipe
                    where e.id_equipe is null
                    group by equipe
                ) c
            """,
            """
                insert into coureur
                select 
                    'COUR' || LPAD(nextval('seq_coureur')::text, 4,'0'),
                    nom,
                    numero_dossard,
                    genre,
                    date_naissance,
                    id_equipe
                from (
                    select 
                        i.nom,
                        i.numero_dossard,
                        i.genre,
                        i.date_naissance,
                        e.id_equipe
                    from import_resultat i
                            join equipe e on e.nom = i.equipe
                            left join coureur c on c.nom = i.nom and c.numero_dossard = i.numero_dossard and c.genre = i.genre and c.date_naissance = i.date_naissance and c.id_equipe = e.id_equipe
                    where c.id_coureur is null
                    group by i.nom, i.numero_dossard, i.genre, i.date_naissance, e.id_equipe
                ) c
            """,
            """
                insert into participant
                select 
                    'COURS0001',
                    e.id_etape,
                    c.id_coureur
                from import_resultat i
                	join v_etape_course e on e.rang = i.etape_rang and e.id_course = 'COURS0001'
                	join coureur c on c.nom = i.nom
                	left join participant p on p.id_course = 'COURS0001' and e.id_etape = p.id_etape and c.id_coureur = p.id_coureur
                where p.id_course is null
            """,
            """
                insert into temps
                select 
                    'TEM' || LPAD(nextval('seq_temps')::text, 4,'0'),
                    date_temps,
                    heure_depart,
                    heure_arrive,
                    id_course,
                    id_type,
                    id_coureur,
                    id_etape
                from (
                select distinct 
                    i.arrivee::date as date_temps,
                    e.heure_depart,
                    i.arrivee::time as heure_arrive,
                    'COURS0001' as id_course,
                    'TYP0001' as id_type,
                    c.id_coureur,
                    e.id_etape
                from import_resultat i
                	join coureur c on c.nom = i.nom
                	join v_etape_course e on e.rang = i.etape_rang and e.id_course = 'COURS0001'
                	left join temps t on t.date_temps = i.arrivee::date and t.heure_arrive = i.arrivee::time and c.id_coureur = t.id_coureur and e.id_etape = t.id_etape
                where t.id_temps is null) as c
            """
        };
    }
    
    @Override
    public void insert(Connection connection) throws Exception {
        String sql = "insert into import_resultat values (?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(this.getEtapeRang()));
            statement.setString(2, this.getNumeroDossard());
            statement.setString(3, this.getNom());
            statement.setString(4, this.getGenre());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            java.util.Date date = formatter.parse(this.getDateNaissance());
            statement.setDate(5, new java.sql.Date(date.getTime()));
            statement.setString(6, this.getEquipe());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            java.util.Date parsedDate = dateFormat.parse(this.getArrivee());
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            statement.setTimestamp(7, timestamp);
            statement.execute();
        }
    }
    
}