package erickkim.dtu.dk.affaldsprojekt;

import erickkim.dtu.dk.affaldsprojekt.TEST_Data_Backend.TEST_Database;

public class Data_DAO_tips {

    public String getTip() {
        return TEST_Database.getInstance().getTip();
    }
}
