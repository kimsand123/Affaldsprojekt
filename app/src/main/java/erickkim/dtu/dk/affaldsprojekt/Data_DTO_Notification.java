package erickkim.dtu.dk.affaldsprojekt;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Data_DTO_Notification implements /*Comparator<Data_DTO_Notification>,*/ Comparable<Data_DTO_Notification> {
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

    @Override
    public int compareTo(@NonNull Data_DTO_Notification that) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if (this == that)
            return EQUAL;

        if (this.date.after(that.getDate()))
            return AFTER;
        if (this.date.before(that.getDate()))
            return BEFORE;

        return EQUAL;
    }

    /* @Override
    public int compare(Data_DTO_Notification o1, Data_DTO_Notification o2) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if (o1 == o2)
            return EQUAL;

        if (o1.date.after(o2.getDate()))
            return AFTER;
        if (o1.date.before(o2.getDate()))
            return BEFORE;

        return EQUAL;
    }
    */
}
