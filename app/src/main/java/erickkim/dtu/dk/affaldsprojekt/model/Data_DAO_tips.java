package erickkim.dtu.dk.affaldsprojekt.model;

import erickkim.dtu.dk.affaldsprojekt.TEST_Data_Backend.TEST_Database;

public class Data_DAO_tips {

    public String getTip(int tipnr) {
        return TEST_Database.getInstance().getTip(tipnr);
    }
}
