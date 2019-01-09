package erickkim.dtu.dk.affaldsprojekt;

import java.util.Date;

public class Data_DTO_ChartBundle {
    String date;
    TYPE type;
    String amount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public String getAmount(){
        return this.amount;
    }

    public void setAmount(String amount){
        this.amount=amount;
    }
}

