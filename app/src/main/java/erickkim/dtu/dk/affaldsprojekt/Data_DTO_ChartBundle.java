package erickkim.dtu.dk.affaldsprojekt;

import java.util.Date;

public class Data_DTO_ChartBundle {
    String date;
    TYPE type;
    int amount;



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

    public int getAmount(){
        return this.amount;
    }

    public void setAmount(int amount){
        this.amount=amount;
    }
}

