package erickkim.dtu.dk.affaldsprojekt;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Data_DTO_Notification {
    private String title;
    private String text;
    private Date date;
    private int status;

    public String getFormatDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formatDate = sdf.format(date);
        return formatDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
