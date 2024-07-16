package controller.url;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

public class HelperUrl {

    public static String url = "http://localhost:8080/race/";
    
    public static String baseUrl(String url) {
        return HelperUrl.url + url;
    }
    
    public static String format(double number) {
        return String.format("%,.2f", number);
    }
    
    public static Timestamp toTimestamp(Time time, java.sql.Date date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        java.util.Date parsedDate = dateFormat.parse(date.toString() + " " + time.toString());
        return new java.sql.Timestamp(parsedDate.getTime());
    }
    
    public static String readInputStream(InputStream is) {
        String result = new BufferedReader(new InputStreamReader(is))
            .lines()
            .collect(Collectors.joining("\n"));
        return result;
    }
    
}
