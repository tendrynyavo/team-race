package controller.csv;

import com.framework.FileUpload;
import com.framework.ModelView;
import com.framework.annontation.url;
import controller.url.HelperUrl;
import java.sql.Timestamp;
import model.csv.CsvObject;
import model.csv.ImportCsv;

public class CsvController {

    FileUpload fileCsv;

    public FileUpload getFileCsv() {
        return fileCsv;
    }

    public void setFileCsv(FileUpload fileCsv) {
        this.fileCsv = fileCsv;
    }
    
    @url("upload/formulaire.do")
    public ModelView formulaire() {
        return new ModelView("import/import-csv");
    }
    
    @url("upload.do")
    public ModelView upload(String file) throws Exception {
        fileCsv.setPath("assets\\");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        fileCsv.setName(timestamp.toString()
                .replace(" ", "")
                .replace(":", "")
                .replace(".", "")
                .replace("-", "") + ".csv");
        fileCsv.upload();
        Class<CsvObject> cls = (Class<CsvObject>) Class.forName(String.format("model.csv.%sCsv", file));
        ImportCsv importCsv = new ImportCsv().createImportTo(fileCsv.getSource(), cls);
        importCsv.importation(null);
        return new ModelView()
                .sendRedirect(HelperUrl.baseUrl("upload/formulaire.do"));
    }
    
}