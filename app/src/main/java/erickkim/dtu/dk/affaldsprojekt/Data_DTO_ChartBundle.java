package erickkim.dtu.dk.affaldsprojekt;

import java.util.Date;

public class Data_DTO_ChartBundle {

    String type;
    String amount;
    private String gold;

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount(){
        return this.amount;
    }

    public void setAmount(String amount){
        this.amount=amount;
    }
}

