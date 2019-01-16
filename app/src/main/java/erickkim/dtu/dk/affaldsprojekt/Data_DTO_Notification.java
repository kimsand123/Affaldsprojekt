package erickkim.dtu.dk.affaldsprojekt;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Data_DTO_Notification {
    public String title;
    public String text;
    public Date date;

    public String getFormatDate() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        String formatDate = sdf.format(date);
        return formatDate;
    }

}
