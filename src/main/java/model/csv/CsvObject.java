package model.csv;

import java.sql.Connection;

public interface CsvObject {

    public void insert(Connection connection) throws Exception;
    public String getTemporaryTable();
    public String[] getQuerys();

}
