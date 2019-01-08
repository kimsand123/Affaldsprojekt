package erickkim.dtu.dk.affaldsprojekt;

import java.util.Date;

public class Data_DTO_ChartBundle {
    Date date;
    TYPE type;
    int amount;

    public Data_DTO_ChartBundle{

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }
}

