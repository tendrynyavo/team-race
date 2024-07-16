package model.csv;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import com.connection.database.BddObject;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Statement;

public class ImportCsv {

    List<String> errors = new ArrayList<>();
    List<CsvObject> detailCsv;

    public List<CsvObject> getDetailCsv() {
        return detailCsv;
    }

    public void setDetailCsv(List<CsvObject> detailCsv) {
        this.detailCsv = detailCsv;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    
    public void addError(String error) {
        this.getErrors().add(error);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends CsvObject> ImportCsv createImportTo(String path, Class<T> cls) throws IOException {
        FileReader file = new FileReader(path, StandardCharsets.UTF_8);
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(file)
                .withIgnoreLeadingWhiteSpace(true)
                .withType(cls)
                .withSeparator(',')
                .build();
        ImportCsv importCsv = new ImportCsv();
        importCsv.setDetailCsv((List<CsvObject>) csvToBean.parse());
        return importCsv;
    }
    
    public void importation(Connection connection) throws Exception {
        if (this.getDetailCsv().isEmpty()) {
            return;
        }
        CsvObject csv = this.getDetailCsv().get(0);
        boolean connect = false;
        Statement statement = null;
        try {
            if (connection == null) {
                connection = BddObject.getPostgreSQL();
                connect = true;
            }
            statement = connection.createStatement();
            statement.executeUpdate(csv.getTemporaryTable());
            for (CsvObject details : this.getDetailCsv()) {
                details.insert(connection);
            }
            for (String query : csv.getQuerys()) {
                statement.addBatch(query);
            }
            statement.executeBatch();
            if (connect) {
                connection.commit();
            }
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null && connect) {
                connection.close();
            }
        }
    }
    
}