package erickkim.dtu.dk.affaldsprojekt;

import erickkim.dtu.dk.affaldsprojekt.TEST_Data_Backend.TEST_Database;

public class Data_DAO_trashCoins {

    public int getTrashCoins(int personId) {
        return TEST_Database.getSomeTrashCoins(personId);
    }

}
