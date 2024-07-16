package model.csv;

import com.opencsv.bean.CsvBindByName;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class PointCsv implements CsvObject {

    @CsvBindByName(column = "classement")
    String classement;
    @CsvBindByName(column = "points")
    String points;

    public String getClassement() {
        return classement;
    }

    public void setClassement(String classement) {
        this.classement = classement;
    }

    public String getPoints() {
        return points.trim();
    }

    public void setPoints(String points) {
        this.points = points;
    }
    
    @Override
    public String getTemporaryTable() {
        return """
            create temporary table import_point(
               classement INTEGER,
               points INTEGER
            )
        """;
    }

    @Override
    public String[] getQuerys() {
        return new String[] {
            """
                insert into point
            	select 
                    'POI' || LPAD(nextval('seq_point')::text, 4,'0'), 
                    classement, 
                    points
            	from (
            		select 
                            classement, 
                            points
            		from import_point i
                            left join point p on p.rang = i.classement and p.valeur = i.points
            		where p.id_point is null
            		group by classement, points
            	) c;
            """,
            """
                insert into point_course
                select 
                    'COURS0001' as id_course,
                    p.id_point,
                    p.rang,
                    p.valeur
                from point p
                    left join point_course pc on pc.id_course = id_course 
                        and pc.id_point = p.id_point 
                        and p.rang = pc.rang and p.valeur = pc.valeur
                where pc.id_course is null
            """
        };
    }
    
    @Override
    public void insert(Connection connection) throws Exception {
        String sql = "insert into import_point values (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(this.getClassement()));
            statement.setInt(2, Integer.parseInt(this.getPoints()));
            statement.execute();
        }
    }
    
}