package erickkim.dtu.dk.affaldsprojekt;

import erickkim.dtu.dk.affaldsprojekt.TEST_Data_Backend.TEST_Database;

public class Data_DAO_deliveryCode {

    // Get a deliverycode from the testDatabase. Deliverycode DTO contains an integer and a date.
    public Data_DTO_deliveryCode getAvailableDeliveryCode () {
        Data_DTO_deliveryCode tempCode = new Data_DTO_deliveryCode();
        tempCode = TEST_Database.getInstance().getDeliveryCode();
        return tempCode;
    }
}
