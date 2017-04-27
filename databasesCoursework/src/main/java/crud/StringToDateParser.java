package crud;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringToDateParser {

    public static Date parse(String string) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        Date date;
        try {
            date = dateFormat.parse(string);
        } catch (ParseException e) {
            System.out.println("Can not parse string to date");
            return null;
        }
        return date;
    }

}
