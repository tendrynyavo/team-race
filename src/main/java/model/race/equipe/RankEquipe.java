package model.race.equipe;

import model.race.equipe.Equipe;
import model.race.course.Course;
import controller.url.HelperUrl;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RankEquipe extends Equipe {
    
    double total;
    Course course;
    int rang;
    ResultatEquipe resultatEquipe;

    public ResultatEquipe getResultatEquipe() {
        return resultatEquipe;
    }

    public void setResultatEquipe(ResultatEquipe resultatEquipe) {
        this.resultatEquipe = resultatEquipe;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }
    
    public RankEquipe() {
        super();
        setTable("v_classement_equipe_lib");
    }
    
    public String getHTMLPrestation() throws FileNotFoundException {
        URL root = Thread.currentThread().getContextClassLoader().getResource("certification");
        String certification = HelperUrl.readInputStream(new FileInputStream(root.getFile()));
        return certification
                .replace("@Equipe", this.getNom())
                .replace("@point", Integer.toString((int) this.getTotal()));
    }
    
    public void viewPDF() throws Exception {
        URL url = new URL("http://127.0.0.1:5000/view_pdf?name=" + this.getClass().getSimpleName());
        HttpURLConnection con = null;
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/html");
        con.setDoOutput(true);
        con.setDoInput(true);
        String htmlContent = this.getHTMLPrestation();

        try (OutputStream outputStream = con.getOutputStream()) {
            byte[] input = htmlContent.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        int responseCode = con.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_OK && responseCode != 201) {
            throw new IllegalArgumentException("API request failed with response code: " + responseCode);
        }
    }
    
    public String getColor() {
        return (this.getResultatEquipe().checkDoublon(this)) ? "rgb(255 204 203 / 84%)" : "none";
    }
    
}